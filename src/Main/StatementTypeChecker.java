package Main;

import ast.*;
import ntree.*;
import symtab.ISymTab;
import types.TypeException;

import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

public class StatementTypeChecker implements IASTStatementVisitor
{
	private Stack<INStatement> sstack;
	private ISymTab<IType> typeEnvironment;
	// Could just make new ones each time used
	private TypeTypeChecker typeTypeCheck;
	private ExpressionTypeChecker exprTypeCheck;
	private Stack<Boolean> markers;
	private Stack<IType> retType;

	public StatementTypeChecker(ISymTab<IType> typeEnvironment)
	{
		this.sstack = new Stack<>();
		this.typeEnvironment = typeEnvironment;
		this.typeTypeCheck = new TypeTypeChecker();
		this.exprTypeCheck = new ExpressionTypeChecker(typeEnvironment);
		this.markers = new Stack<>();
		this.retType = new Stack<>();
	}

	private void ret(INStatement e)
	{
		sstack.push(e);
	}

	public INStatement typecheck(IASTStatement node)
	{
		node.visit(this);
		return sstack.pop();
	}

	private INStatement tcheck(IASTStatement node)
	{
		node.visit(this);
		return sstack.pop();
	}

	private List<INStatement> tcheck(List<IASTStatement> stmts)
	{
		LinkedList<INStatement> body = new LinkedList<>();

		for(IASTStatement stmt : stmts)
		{
			body.add(tcheck(stmt));
		}

		return body;
	}

	@Override public void visit(Declaration a)
	{
		markers.push(false);
		// Make sure grammar does that
		IType type = typeTypeCheck.typecheck(a.getLhs());

		// If being declared in a record or function though, only want to look at nearest scope
		// I don't think I can even get that info right now

		if (typeEnvironment.lookupNearest(a.getRhs().getName()) != null)
		{
			throw new TypeException(a.getRhs().getName() + " ALREADY DEFINED IN SCOPE");
		}

		if (!a.getIndices().isEmpty())
		{
			throw new TypeException("You big dummy, you can't declare an array like that");
		}

		typeEnvironment.declare(a.getRhs().getName(), type);

		NIdentifier identifier = (NIdentifier) exprTypeCheck.typecheck(a.getRhs());

		// Maybe don't want this
		ret(new NDeclaration(type, identifier));
	}

	@SuppressWarnings("Duplicates") @Override public void visit(DeclareAssign a)
	{
		markers.push(false);
		// Get declared type od Iden
		IType declareType = typeTypeCheck.typecheck(a.getType());

		// Make sure Iden is not already within scope, look up closet scope, comment this out for
		// standard lexical analysis
		if (typeEnvironment.lookupNearest(a.getIden().getName()) != null)
		{
			throw new TypeException(a.getIden().getName() + " ALREADY DEFINED IN SCOPE");
		}

		// Declare it within the scope
		typeEnvironment.declare(a.getIden().getName(), declareType);

		// Normalize the iden
		NIdentifier identifier = (NIdentifier) exprTypeCheck.typecheck(a.getIden());

		// Normalize the rhs expr
		INExpr expr = exprTypeCheck.typecheck(a.getExpr());

		// Get its type
		IType exprType = expr.getType();

		// Check index to make sure its an int at some point
		List<INExpr> indices = new LinkedList<>();
		for (IASTExpr x : a.getIndex())
		{
			INExpr index = exprTypeCheck.typecheck(x);
			if (index.getType() != NTypeInt.INT)
			{
				throw new TypeException("GIVEN INDEX TYPE NON INTEGER");
			}
			indices.add(index);
		}

		IType innerType = null;

		if (identifier.getType() instanceof NTypeArray && !(exprType instanceof NTypeArray))
		{
			// To prevent arr int x = 4;
			if (indices.isEmpty())
			{
				throw new TypeException("You big dummy, you can't declare an array like that");
			}

			identifier.getType().arrayCheckSize(indices.size());

			TypeTypeChecker typeTypeChecker = new TypeTypeChecker();
			innerType = typeTypeChecker.typecheck(identifier.getType());
		}

		// Potentially want to promote an int being assigned to a double.
		// but also want to make sure the type of the expression matches the
		// declareType in every other case

		if (declareType == NTypeDouble.DOUBLE && exprType == NTypeInt.INT)
		{
			expr = promote(expr);
		}
		else if (innerType != null)
		{
			if ((!innerType.equals(exprType)))
			{
				throw new TypeException("Incompatible Given Type to Identifier");
			}
		}
		else
		{
			if ((!(identifier.getType().equals(exprType))))
			{
				throw new TypeException("Incompatible Given Type to Identifier");
			}
		}

		if (indices.isEmpty())
		{
			ret(new NDeclareAssign(declareType, identifier, expr));
		}
		else
		{
			ret(new NDeclareAssign(declareType, identifier, expr, indices));
		}
	}

	@SuppressWarnings("Duplicates") @Override public void visit(Assignment a)
	{
		markers.push(false);
		// Change the LHS to be just an expression
		NIdentifier lhs = (NIdentifier) exprTypeCheck.typecheck(a.getLhs());
		INExpr rhs = exprTypeCheck.typecheck(a.getRhs());

		List<INExpr> indices = new LinkedList<>();
		for (IASTExpr x : a.getIndex())
		{
			INExpr index = exprTypeCheck.typecheck(x);
			if (index.getType() != NTypeInt.INT)
			{
				throw new TypeException("GIVEN INDEX TYPE NON INTEGER");
			}
			indices.add(index);
		}

		IType innerType = null;

		if (lhs.getType() instanceof NTypeArray && !(rhs.getType() instanceof NTypeArray))
		{
			if (indices.isEmpty())
			{
				throw new TypeException("You big dummy, you can't assign to an array like that");
			}

			lhs.getType().arrayCheckSize(indices.size());

			TypeTypeChecker typeTypeChecker = new TypeTypeChecker();
			innerType = typeTypeChecker.typecheck(lhs.getType());
		}

		if (lhs.getType() == NTypeDouble.DOUBLE && rhs.getType() == NTypeInt.INT)
		{
			rhs = promote(rhs);
		}
		else if (innerType != null)
		{
			if ((!innerType.equals(rhs.getType())))
			{
				if (innerType == NTypeDouble.DOUBLE && rhs.getType() == NTypeInt.INT)
				{
					rhs = promote(rhs);
				}
				else
				{
					throw new TypeException("Incompatible Given Type to Identifier");
				}
			}
		}
		else
		{
			if ((!(lhs.getType().equals(rhs.getType()))))
			{
				System.out.println(lhs.getName() + " :: " + lhs.getType());
				throw new TypeException("Incompatible Given Type to Identifier");
			}
		}

		if (indices.isEmpty())
		{
			ret(new NAssignment(lhs, rhs));
		}
		else
		{
			ret(new NAssignment(lhs, rhs, indices));
		}
	}

	@SuppressWarnings("Duplicates") @Override public void visit(For a)
	{
		markers.push(false);
		// Question about this and if you want to loop range amount of times
		INExpr range = exprTypeCheck.typecheck(a.getRange());
		if (range.getType() != NTypeInt.INT)
		{
			throw new TypeException("Non integer range");
		}
		typeEnvironment.enterNewScope();
		typeEnvironment.declare(a.getIdentifier().getName(), NTypeInt.INT);
		List<INStatement> body = tcheck(a.getBody());

		typeEnvironment.exitScope();
		NIdentifier var = new NIdentifier(a.getIdentifier().getName(), NTypeInt.INT);
		body.add(new NAssignment(var, new NPlus(var, new NInt(1))));

		List<INStatement> initializers = new LinkedList<>();
		initializers.add(new NDeclareAssign(NTypeInt.INT, var, new NInt(0)));

		//  this should be a less than node
		INExpr condt = new NLessThan(var, range);
		if (condt.getType() != NTypeBoolean.BOOLEAN)
		{
			throw new TypeException("Non boolean arguments");
		}

		ret(new NWhile(initializers, condt, body));
	}

	public void visit(DoWhile a)
	{
		markers.push(false);
		// Enter new scope
		typeEnvironment.enterNewScope();
		// Declare the $var initializer within the scope
		typeEnvironment.declare(a.getInitialTrue().getName(), NTypeBoolean.BOOLEAN);
		//System.out.println(a.getInitialTrue().getName());

		// Normalize the body of the doWhile
		List<INStatement> body = tcheck(a.getBody());

		// Normalize the initializer
		NIdentifier initialTrue = (NIdentifier) exprTypeCheck.typecheck(a.getInitialTrue());
		// Add to the scope that var now = false since the loop has ran once
		body.add(new NAssignment(initialTrue, new NBool(false)));
		// Exit the scope
		typeEnvironment.exitScope();
		// Create the list of initializers to pass through, which in this case will be just of size 1
		List<INStatement> initializers = new LinkedList<>();
		// Add to the list that initialTrue is well, initially true
		initializers.add(new NDeclareAssign(NTypeBoolean.BOOLEAN, initialTrue, new NBool(true)));

		INExpr condt = exprTypeCheck.typecheck(a.getCond());

		NOR whileCondt = new NOR(initialTrue, condt);

		if (condt.getType() != NTypeBoolean.BOOLEAN)
		{
			throw new TypeException("Non boolean arguments");
		}

		ret(new NWhile(initializers, whileCondt, body));
	}

	@Override public void visit(While a)
	{
		markers.push(false);
		INExpr e = exprTypeCheck.typecheck(a.getCondt());
		if (e.getType() != NTypeBoolean.BOOLEAN)
		{
			throw new TypeException("Non boolean arguments");
		}
		List<INStatement> body = tcheck(a.getBody());

		ret(new NWhile(e, body));
	}

	public void visit(If a)
	{
		markers.push(false);
		INExpr e = exprTypeCheck.typecheck(a.getCondition());
		if (e.getType() != NTypeBoolean.BOOLEAN)
		{
			throw new TypeException("Non boolean arguments");
		}
		List<INStatement> body = tcheck(a.getStatements());

		ret(new NIf(e, body));
	}

	public void visit(Print a)
	{
		markers.push(false);
		INExpr expr = exprTypeCheck.typecheck(a.getExpr());
		ret(new NPrint(expr));
	}

	@Override public void visit(Return a)
	{
		markers.push(true);
		INExpr expr = exprTypeCheck.typecheck(a.getExpr());
		retType.push(expr.getType());
		ret(new NReturn(expr));
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

	public Stack<Boolean> getMarkers()
	{
		return markers;
	}

	public Stack<IType> getRetType()
	{
		return retType;
	}
}
