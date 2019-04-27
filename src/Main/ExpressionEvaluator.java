package Main;

import ntree.*;
import symtab.ISymTab;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class ExpressionEvaluator implements INExprVisitor
{
	private Stack<Object> operand;
	private ISymTab<Object> typeEnviroment;

	public ExpressionEvaluator(ISymTab<Object> typeEnviroment)
	{
		this.operand = new Stack<>();
		this.typeEnviroment = typeEnviroment;
	}

	public Object eval(INExpr e)
	{
		e.visit(this);
		return operand.pop();
	}

	private List<Integer> evalList(List<INExpr> l)
	{
		ArrayList<Integer> indices = new ArrayList<>();
		for (INExpr index : l)
		{
			indices.add((Integer) eval(index));
		}

		return indices;
	}

	private void ret(Object e)
	{
		operand.push(e);
	}

	@Override public void visit(NPlus a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getType() == NTypeInt.INT)
		{
			ret(((Integer) left + ((Integer) right)));
		}
		else
		{
			ret(((Double) left + ((Double) right)));
		}
	}

	@Override public void visit(NInt a)
	{
		// this auto promotes
		ret(a.getValue());
	}

	@Override public void visit(NIdentifier a)
	{
		Object identValue = typeEnviroment.lookup(a.getName());
		ret(identValue);
	}

	public Object returnDefaultValue(IType type)
	{
		if (type == NTypeInt.INT)
		{
			return 0;
		}
		else if(type == NTypeDouble.DOUBLE)
		{
			return 0.0;
		}
		else
		{
			return false;
		}
	}

	private Object generateArray(List<Integer> sizes, IType arrayType)
	{
		if (sizes.isEmpty())
		{
			return returnDefaultValue(arrayType);
		}

		int n = sizes.get(0);

		Object[] array = new Object[n];
		for (int i = 0; i < n; i++)
		{
			array[i] = generateArray(sizes.subList(1, sizes.size()), arrayType);
		}

		return array;
	}

	@Override public void visit(NArray a)
	{
		// Get the list of sizes and the type of the array
		// create that array, push it on the stack

		// Evaluate those expressions to get their values, or in other words the literal sizes
		List<Integer> sizes = evalList(a.getSize());

		TypeTypeChecker typeTypeChecker = new TypeTypeChecker();
		IType arrayType = typeTypeChecker.typecheck(a.getType());

		// sizes.size() = the number of dimensions you need
		// then loop through the list, filling in those values

		ret(generateArray(sizes, arrayType));

	}

	@Override public void visit(NArrayIndex a)
	{
		NIdentifier ident = (NIdentifier) a.getArray();

		List<Integer> indices = evalList(a.getIndices());

		int numIndices = indices.size();

		Object[] array = (Object[]) eval(ident);

		for (int i = 0; i < numIndices - 1; i++)
		{
			array = (Object[]) array[indices.get(i)];
		}

		ret(array[indices.get(indices.size() - 1)]);

	}

	@Override public void visit(NBool a)
	{
		ret(a.isValue());
	}

	@Override public void visit(NDouble a)
	{
		ret(a.getValue());
	}

	@Override public void visit(NMult a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getType() == NTypeInt.INT)
		{
			ret(((Integer) left * ((Integer) right)));
		}
		else
		{
			ret(((Double) left * ((Double) right)));
		}
	}

	@Override public void visit(NDiv a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getType() == NTypeInt.INT)
		{
			ret(((Integer) left / ((Integer) right)));
		}
		else
		{
			ret(((Double) left / ((Double) right)));
		}
	}

	@Override public void visit(NSub a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getType() == NTypeInt.INT)
		{
			ret(((Integer) left - ((Integer) right)));
		}
		else
		{
			ret(((Double) left - ((Double) right)));
		}
	}

	@Override public void visit(NLessThan a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push(((Integer) left < ((Integer) right)));
		}
		else
		{
			operand.push(((Double) left < ((Double) right)));
		}
	}

	@Override public void visit(NGreaterThan a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push(((Integer) left > ((Integer) right)));
		}
		else
		{
			operand.push(((Double) left > ((Double) right)));
		}
	}

	@Override public void visit(Promote a)
	{
		Integer promoted = ((Integer) eval(a.getNode()));
		ret(promoted.doubleValue());
	}

	@Override public void visit(NOR a)
	{
		Object left = eval(a.get$var());
		if ((Boolean) left)
		{
			ret(left);
		}
		else
		{
			ret(eval(a.getCondt()));
		}

	}

	@Override public void visit(NRecord a)
	{
		// A record is hashmap from ident to value??
		// How to evaluate a record?? A record is a list of declarations currently
	}

	@Override public void visit(NFunction a)
	{
		 ret(null);
	}
}
