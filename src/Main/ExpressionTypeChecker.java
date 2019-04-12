package Main;

import ast.*;
import ntree.*;
import symtab.ISymTab;
import types.TypeException;
import types.UndeclaredIdentifer;

import java.util.LinkedList;
import java.util.List;
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

		// Also need to make sure that the index does not exceed the size of the array
		// No way to do that right now since EX: Have no way of evaluating a Plus node currently

		List<INExpr> indices = new LinkedList<>();
		for(IASTExpr index : a.getIndices())
		{
			INExpr nIndex = tcheck(index);
			if(nIndex.getType() != NTypeInt.INT)
			{
				throw new TypeException("BAD INDEX TYPE GIVEN");

			}

			indices.add(nIndex);
		}


		/*INExpr index = tcheck(a.getIndex());
		if (index.getType() != NTypeInt.INT)
		{
			throw new TypeException("BAD INDEX TYPE GIVEN");
		}*/

		ret(new NArrayIndex(arrayType, array, indices));
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
		/*INExpr l = tcheck(a.getLeft());
		checkIsNumeric(l);

		INExpr r = tcheck(a.getRight());
		checkIsNumeric(r);

		if (l.getType() == NTypeInt.INT && r.getType() == NTypeInt.INT)
		{
			ret(new NMult(l, r));
		}
		else
		{
			ret(new NMult(promote(l), promote(r)));
		}*/

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
