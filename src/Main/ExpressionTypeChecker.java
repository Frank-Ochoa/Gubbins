package Main;

import ast.*;
import ntree.*;
import symtab.ISymTab;
import types.TypeException;
import types.UndeclaredIdentifer;

import java.lang.reflect.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;

public class ExpressionTypeChecker implements IASTExpressionVisitor
{
	private Stack<INExpr> estack;
	private ISymTab<IType> typeEnvironment;

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
		boolean returnTypeGood = true;
		// Possibly needs a new scope
		TypeTypeChecker t = new TypeTypeChecker();
		// Need to make sure this is a TypeFunction
		IType functionType = t.typecheck(a.getTypeFunction());

		// Enter new scope here?
		typeEnvironment.enterNewScope();

		if (functionType instanceof NTypeFunction)
		{
			TypeFunction astTF = (TypeFunction) a.getTypeFunction();
			TypeRecord astTR = (TypeRecord) astTF.getArgs();

			for(Map.Entry<Identifier, IASTType> entry : astTR.getArgs().entrySet())
			{
				//System.out.println(entry.getKey().getName() + " : " + entry.getValue());
				a.getBody().add(0, new Declaration(entry.getValue(), entry.getKey(), new LinkedList<>()));
			}
		}
		else
		{
			throw new TypeException("NOT A TYPE FUNCTION");
		}

		StatementTypeChecker statementTypeChecker = new StatementTypeChecker(typeEnvironment);
		List<INStatement> nStmts = new LinkedList<>();

		for (IASTStatement stmt : a.getBody())
		{
			// For making sure there is at least one return stmt, will still also need to make
			// that each is of the return type found in the functionType

			INStatement nStmt = statementTypeChecker.typecheck(stmt);

			if (nStmt instanceof NReturn)
			{
				// This boolean might be redundant
				returnThere = true;

				if (!(((NReturn) nStmt).getExpr().getType().equals(((NTypeFunction) functionType).getResult())))
				{
					returnTypeGood = false;
				}
			}

			nStmts.add(nStmt);
		}

		typeEnvironment.exitScope();

		if (!returnThere)
		{
			throw new TypeException("NO RETURN STATEMENT GIVEN");
		}

		if (!returnTypeGood)
		{
			throw new TypeException("AT LEAST ONE RETURN TYPE DOES NOT MATCH FUNCTION RETURN TYPE");
		}

		ret(new NFunction(functionType, nStmts));

	}

	@Override public void visit(Record a)
	{
		// What to type check about a record?
		List<INStatement> nElements = new LinkedList<>();
		List<NIdentifier> nIdentifiers = new LinkedList<>();

		// Enter new scope here?
		typeEnvironment.enterNewScope();
		StatementTypeChecker statementTypeChecker = new StatementTypeChecker(typeEnvironment);

		// Each stmt is guaranteed to be an Assignment, a declare, or declare%assign by the grammar
		for (IASTStatement stmt : a.getElements())
		{
			INStatement nElement = statementTypeChecker.typecheck(stmt);

			// Compute the type of the record
			if (nElement instanceof NDeclaration)
			{
				nIdentifiers.add(((NDeclaration) nElement).getRhs());
			}
			else if (nElement instanceof NDeclareAssign)
			{
				nIdentifiers.add(((NDeclareAssign) nElement).getIdentifier());
			}

			nElements.add(nElement);
		}

		typeEnvironment.exitScope();

		ret(new NRecord(new NTypeRecord(nIdentifiers), nElements));
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
				if(ident.getName().equals(index))
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


		if(recAT != null)
		{
			ret(new NRecordAccess(recAT, record, index));
		}
		else
		{
			throw new TypeException("RECORD DOES NOT CONTAIN : " + index);
		}

	}

	@Override public void visit(FunctionCall a)
	{

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
			//  type checking error
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

	private void normalizeExprOperator(IASTExprOperator operator)
	{
		INExpr l = tcheck(operator.getLeft());
		checkIsNumeric(l);
		INExpr r = tcheck(operator.getRight());
		checkIsNumeric(r);

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
