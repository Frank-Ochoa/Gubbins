package Main;

import ntree.*;
import symtab.ISymTab;

import java.util.*;

public class ExpressionEvaluator implements INExprVisitor
{
	private Stack<Object> operand;
	private ISymTab<Object> valueEnviro;

	public ExpressionEvaluator(ISymTab<Object> valueEnviro)
	{
		this.operand = new Stack<>();
		this.valueEnviro = valueEnviro;
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
		Object identValue = valueEnviro.lookup(a.getName());
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

	@Override public void visit(NMod a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getType() == NTypeInt.INT)
		{
			ret(((Integer) left % ((Integer) right)));
		}
		else
		{
			ret(((Double) left % ((Double) right)));
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

	@Override public void visit(NLessThanEQ a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push(((Integer) left <= ((Integer) right)));
		}
		else
		{
			operand.push(((Double) left <= ((Double) right)));
		}
	}

	@Override public void visit(NGreaterThanEQ a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push(((Integer) left >= ((Integer) right)));
		}
		else
		{
			operand.push(((Double) left >= ((Double) right)));
		}
	}

	@Override public void visit(NEQ a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push((((Integer) left).equals((Integer) right)));
		}
		else
		{
			operand.push((((Double) left).equals((Double) right)));
		}
	}

	@Override public void visit(NNotEQ a)
	{
		Object left = eval(a.getLeft());

		Object right = eval(a.getRight());

		if (a.getLeft().getType() == NTypeInt.INT)
		{
			operand.push((!((Integer) left).equals((Integer) right)));
		}
		else
		{
			operand.push((!((Double) left).equals((Double) right)));
		}
	}

	@Override public void visit(NAnd a)
	{
		Object left = eval(a.getLeft());
		if((Boolean) left)
		{
			ret(eval(a.getRight()));
		}
		else
		{
			ret(left);
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
		valueEnviro.enterNewScope();
		// Possibly hashmap of values? Where a record index expression then does a look up on that hashmap
		HashMap<String, Object> recordMap = new HashMap<>();
		StatementEvaluator sEval = new StatementEvaluator(valueEnviro);

		for(INStatement stmt : a.getArgs())
		{
			sEval.eval(stmt);
			if (stmt instanceof NDeclaration)
			{
				String name = ((NDeclaration) stmt).getRhs().getName();
				recordMap.put(name, valueEnviro.lookup(name));
			}
			else if (stmt instanceof NDeclareAssign)
			{
				String name = ((NDeclareAssign) stmt).getIdentifier().getName();
				recordMap.put(name, valueEnviro.lookup(name));
			}
			else if(stmt instanceof NAssignment)
			{
				String name = ((NAssignment) stmt).getLhs().getName();
				recordMap.put(name, valueEnviro.lookup(name));
			}
		}

		valueEnviro.exitScope();

		ret(recordMap);
	}

	@Override public void visit(NRecordAccess a)
	{
		// Here I need eval the left to get the hashmap
		HashMap<String, Object> map = (HashMap <String,Object>) eval(a.getRec());
		ret(map.get(a.getIndex()));
	}

	@Override public void visit(NFunction a)
	{

		// Need to Associate the function and a type enviro

		Map<String, Object> closureEnviro = new HashMap<>();

		for (String name : a.getClosureIdents())
		{
			closureEnviro.put(name, valueEnviro.lookup(name));
		}

		// No associate the function and the above
		Map<NFunction, Map<String, Object>> function = new HashMap<>();

		function.put(a, closureEnviro);

		ret(function);

	}

	@Override public void visit(NFunctionCall a)
	{

		Map<NFunction, Map<String, Object>> function = (Map<NFunction, Map<String, Object>>) eval(a.getFunction());

		NFunction func = null;
		Map<String, Object> closureEnviro = null;

		for (Map.Entry<NFunction, Map<String, Object>> entry : function.entrySet())
		{
			func = entry.getKey();
			closureEnviro = entry.getValue();
		}

		assert func != null;
		assert closureEnviro != null;

		NTypeFunction funcType = (NTypeFunction) func.getType();
		NTypeRecord recType = (NTypeRecord) funcType.getArgs();

		Iterator<NIdentifier> it1 = recType.getArgs().iterator();
		Iterator<INExpr> it2 = a.getArgValues().iterator();

		// Scope for the closure
		valueEnviro.enterNewScope();

		for(Map.Entry<String, Object> entry : closureEnviro.entrySet())
		{
			valueEnviro.declare(entry.getKey(), entry.getValue());
		}

		valueEnviro.enterNewScope();


		while(it1.hasNext() && it2.hasNext())
		{
			String name = it1.next().getName();
			Object value = eval(it2.next());
			valueEnviro.declare(name, value);
		}

		StatementEvaluator sEval = new StatementEvaluator(valueEnviro);
		Stack<Boolean> marker = sEval.getMarkerS();
		Stack<Object> returnVals = sEval.getReturnValS();

		for(INStatement stmt : func.getBody())
		{
			sEval.eval(stmt);

			// So will eval an if, and then a return, and then stop that evaling that if, and
			// come back out to this loop, where then I need to ret whats on the eval stack
			if(marker.peek())
			{
				marker.pop();
				ret(returnVals.pop());
				break;
			}

		}

		valueEnviro.exitScope();

		valueEnviro.exitScope();

	}

}
