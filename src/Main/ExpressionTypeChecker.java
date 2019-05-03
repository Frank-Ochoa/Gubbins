package Main;

import ast.*;
import ntree.*;
import symtab.ISymTab;
import types.TypeException;
import types.UndeclaredIdentifer;

import java.util.*;

public class ExpressionTypeChecker implements IASTExpressionVisitor
{
	private Stack<INExpr> estack;
	private ISymTab<IType> typeEnvironment;
	private static Stack<NTypeRecord> records = new Stack<>();

	ExpressionTypeChecker(ISymTab<IType> typeEnvironment)
	{
		this.estack = new Stack<>();
		this.typeEnvironment = typeEnvironment;
	}

	INExpr typecheck(IASTExpr node)
	{
		node.visit(this);
		return estack.pop();
	}

	private INExpr tcheck(IASTExpr node)
	{
		node.visit(this);
		return estack.pop();
	}

	private void ret(INExpr e)
	{
		estack.push(e);
	}

	@Override public void visit(Bool a)
	{
		ret(new NBool(a.getValue()));
	}

	@Override public void visit(Dble a)
	{
		ret(new NDouble(a.getValue()));
	}

	@Override public void visit(Int a)
	{
		ret(new NInt(a.getValue()));
	}

	@Override public void visit(Array a)
	{
		// Normalize the type
		TypeTypeChecker t = new TypeTypeChecker();
		IType type = t.typecheck(a.getType());

		// Normalize the List of sizes
		List<INExpr> sizes = new LinkedList<>();
		// Make sure the tcheck makes sure it an int
		for (IASTExpr x : a.getSizes())
		{
			INExpr expr = tcheck(x);

			if (expr.getType() != NTypeInt.INT)
			{
				throw new TypeException("Given Array Size Values Not Type Integer");
			}

			sizes.add(expr);
		}

		int indexSize = sizes.size();
		// Checking to make sure that the size list matches the dimensions of the array
		type.arrayCheckSize(indexSize);

		// Push a new Normalized Array
		ret(new NArray(type, sizes));
	}

	public void visit(ArrayIndex a)
	{
		// array has type array but array[0] should have the inner most type

		// Represents the expression that will eventually be an array
		INExpr array = tcheck(a.getArray());

		// Should then have to recursively find the inner most type of this expression
		TypeTypeChecker t = new TypeTypeChecker();
		IType arrayType = t.typecheck(array.getType());

		List<INExpr> indices = new LinkedList<>();
		for (IASTExpr index : a.getIndices())
		{
			INExpr nIndex = tcheck(index);
			if (nIndex.getType() != NTypeInt.INT)
			{
				throw new TypeException("BAD INDEX TYPE GIVEN");

			}

			indices.add(nIndex);
		}

		ret(new NArrayIndex(arrayType, array, indices));
	}

	@Override public void visit(Function a)
	{

		boolean returnThere = false;
		// Possibly needs a new scope
		TypeTypeChecker t = new TypeTypeChecker();
		// Need to make sure this is a TypeFunction
		IType functionType = t.typecheck(a.getTypeFunction());

		// Get idents outside of scope of function
		List<String> closureIdents = typeEnvironment.getClosureIdents();

		// Enter new scope here?
		typeEnvironment.enterNewScope();

		// Fudging to test, does not contain foo, need to pass this back
		if (!records.empty())
		{
			typeEnvironment.declare("this", records.peek());
		}

		if (functionType instanceof NTypeFunction)
		{
			NTypeRecord typeRec = (NTypeRecord) ((NTypeFunction) functionType).getArgs();
			for (NIdentifier ident : typeRec.getArgs())
			{
				typeEnvironment.declare(ident.getName(), ident.getType());
			}
		}
		else
		{
			throw new TypeException("NOT A TYPE FUNCTION");
		}

		StatementTypeChecker statementTypeChecker = new StatementTypeChecker(typeEnvironment);
		List<INStatement> nStmts = new LinkedList<>();
		Stack<Boolean> markers = statementTypeChecker.getMarkers();
		Stack<IType> retType = statementTypeChecker.getRetType();

		for (IASTStatement stmt : a.getBody())
		{
			// For making sure there is at least one return stmt, will still also need to make
			// that each is of the return type found in the functionType

			INStatement nStmt = statementTypeChecker.typecheck(stmt);

			if (markers.peek())
			{
				markers.pop();
				returnThere = true;

				if (!(retType.pop().equals(((NTypeFunction) functionType).getResult())))
				{
					throw new TypeException("AT LEAST ONE RETURN TYPE DOES NOT MATCH FUNCTION RETURN TYPE");
				}
			}


			nStmts.add(nStmt);
		}

		typeEnvironment.exitScope();

		if (!returnThere)
		{
			throw new TypeException("NO RETURN STATEMENT GIVEN");
		}

		ret(new NFunction(functionType, nStmts, closureIdents));
	}

	@Override public void visit(FunctionCall a)
	{

		// Want to make sure that the size of the args as well as their types match
		INExpr func = typecheck(a.getFunction());

		if (!(func.getType() instanceof NTypeFunction))
		{
			throw new TypeException("CALL ON NON FUNCTION");
		}

		NTypeFunction funcType = (NTypeFunction) func.getType();

		NTypeRecord recType = (NTypeRecord) funcType.getArgs();
		if (recType.getArgs().size() != a.getArgValues().size())
		{
			throw new TypeException("FUNCTION CALL ARGS SIZE DOES NOT MATCH");
		}

		List<INExpr> argValues = new LinkedList<>();
		Iterator<NIdentifier> it = recType.getArgs().iterator();

		for (IASTExpr expr : a.getArgValues())
		{
			INExpr argValue = typecheck(expr);
			NIdentifier argParam = it.next();

			// Pretty sure this check is redundant because assign will catch but this will catch sooner
			if (!argValue.getType().equals(argParam.getType()))
			{
				throw new TypeException("FUNCTION CALL ARG TYPE INCORRECT");
			}

			argValues.add(argValue);
		}

		IType returnType = funcType.getResult();

		ret(new NFunctionCall(returnType, func, argValues));
	}

	@Override public void visit(Record a)
	{
		records.push(new NTypeRecord(new LinkedList<>()));

		// What to type check about a record?
		List<INStatement> nElements = new LinkedList<>();
		List<NIdentifier> nIdentifiers = new LinkedList<>();

		// What I want is in this scope, or rather what I want is this scope
		typeEnvironment.enterNewScope();
		StatementTypeChecker statementTypeChecker = new StatementTypeChecker(typeEnvironment);

		// Each stmt is guaranteed to be an Assignment, a declare, or declare%assign by the grammar
		for (IASTStatement stmt : a.getElements())
		{
			INStatement nElement = statementTypeChecker.typecheck(stmt);

			// Compute the type of the record
			if (nElement instanceof NDeclaration)
			{
				NIdentifier ident = ((NDeclaration) nElement).getRhs();
				nIdentifiers.add(ident);
				records.peek().getArgs().add(ident);
			}
			else if (nElement instanceof NDeclareAssign)
			{
				NIdentifier ident = ((NDeclareAssign) nElement).getIdentifier();
				nIdentifiers.add(ident);
				records.peek().getArgs().add(ident);
			}

			nElements.add(nElement);
		}

		typeEnvironment.exitScope();

		ret(new NRecord(new NTypeRecord(nIdentifiers), nElements));

		records.pop();
	}

	@Override public void visit(RecordAccess a)
	{
		INExpr record = tcheck(a.getRec());
		String index = a.getIndex();

		IType recType = record.getType();
		IType recAT = null;

		if (recType instanceof NTypeRecord)
		{
			// Need to check the args in the record if their name matches the index
			for (NIdentifier ident : ((NTypeRecord) recType).getArgs())
			{
				if (ident.getName().equals(index))
				{
					recAT = ident.getType();
					break;
				}
			}
		}
		else
		{
			throw new TypeException("ATTEMPTING TO ACCESS NON RECORD");
		}

		if (recAT != null)
		{
			ret(new NRecordAccess(recAT, record, index));
		}
		else
		{
			throw new TypeException("RECORD DOES NOT CONTAIN : " + index);
		}

	}

	@Override public void visit(Identifier a)
	{
		IType type = typeEnvironment.lookup(a.getName());
		if (type == null)
		{
			throw new UndeclaredIdentifer(a.getName());
		}
		else
		{
			ret(new NIdentifier(a.getName(), type));
		}
	}

	private void checkIsNumeric(INExpr e)
	{
		if (e.getType() != NTypeInt.INT && e.getType() != NTypeDouble.DOUBLE)
		{
			System.out.println(e.getType());
			throw new TypeException("Non numeric arguments");
		}
	}

	private INExpr promote(INExpr node)
	{
		if (node.getType() == NTypeInt.INT)
		{
			return new Promote(NTypeDouble.DOUBLE, node);
		}
		else
		{
			return node;
		}
	}

	public void visit(Mult a)
	{
		normalizeExprOperator(a);
	}

	public void visit(Div a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(Mod a)
	{
		normalizeExprOperator(a);
	}

	public void visit(Plus a)
	{
		normalizeExprOperator(a);
	}

	public void visit(Sub a)
	{
		normalizeExprOperator(a);
	}

	public void visit(LessThan a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(GreaterThan a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(LessThanEQ a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(GreaterThanEQ a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(EQ a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(NotEQ a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(And a)
	{
		normalizeExprOperator(a);
	}

	@Override public void visit(Or a)
	{
		normalizeExprOperator(a);
	}

	private void normalizeExprOperator(IASTExprOperator operator)
	{
		INExpr l = tcheck(operator.getLeft());
		INExpr r = tcheck(operator.getRight());

		if(!(operator instanceof Or) && !(operator instanceof And))
		{
			checkIsNumeric(l);
			checkIsNumeric(r);
		}

		OperatorNormalizer operatorNormalizer = new OperatorNormalizer();

		if (l.getType() == NTypeInt.INT && r.getType() == NTypeInt.INT)
		{
			ret(operatorNormalizer.normalize(operator, l, r));
		}
		else
		{
			ret(operatorNormalizer.normalize(operator, promote(l), promote(r)));
		}
	}
}
