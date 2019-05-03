package Main;

import ntree.*;
import symtab.ISymTab;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class StatementEvaluator implements INStatementVisitor
{
	private ISymTab<Object> evalEnviroment;
	private ExpressionEvaluator exprEval;
	private Stack<Boolean> markerS;
	private Stack<Object> returnValS;

	public StatementEvaluator(ISymTab<Object> evalEnviroment)
	{
		this.evalEnviroment = evalEnviroment;
		this.exprEval = new ExpressionEvaluator(evalEnviroment);
		this.markerS = new Stack<>();
		this.returnValS = new Stack<>();
	}

	public void eval(INStatement e)
	{
		e.visit(this);
	}

	public void eval(List<INStatement> l)
	{
		for (INStatement e : l)
		{
			eval(e);

			// If a return was executed, stop evaling further statements
			if (markerS.peek())
			{
				System.out.println("Found a return");
				break;
			}
		}
	}

	@Override public void visit(NDeclaration a)
	{
		markerS.push(false);

		String ident = a.getRhs().getName();

		// Just call the array method
		TypeTypeChecker typeTypeChecker = new TypeTypeChecker();
		IType declareType = typeTypeChecker.typecheck(a.getLhs());

		// Have to do something different for functions here, because, I mean, where is their default return type?
		// The default of the type of the return type? weird

		evalEnviroment.declare(ident, exprEval.returnDefaultValue(declareType));
	}

	@Override public void visit(NDeclareAssign a)
	{
		markerS.push(false);

		String ident = a.getIdentifier().getName();
		Object value = exprEval.eval(a.getExpr());

		evalEnviroment.declare(ident, value);
	}

	@SuppressWarnings("Duplicates") @Override public void visit(NAssignment a)
	{
		markerS.push(false);

		// If indices empty, it's a normal assignment
		if (a.getIndices().isEmpty())
		{
			String ident = a.getLhs().getName();
			Object value = exprEval.eval(a.getRhs());

			// keep going back to find a map that has that ident, and declare this value there
			evalEnviroment.declareCheckIfThere(ident, value);
		}
		// Else it's an array index assignment
		else
		{
			ArrayList<Integer> indices = new ArrayList<>();
			for (INExpr index : a.getIndices())
			{
				indices.add((Integer) exprEval.eval(index));
			}

			int numIndices = indices.size();

			Object[] array = (Object[]) exprEval.eval(a.getLhs());

			for (int i = 0; i < numIndices - 1; i++)
			{
				array = (Object[]) array[indices.get(i)];
			}

			array[indices.get(indices.size() - 1)] = exprEval.eval(a.getRhs());

		}

	}

	@Override public void visit(NIf a)
	{
		markerS.push(false);

		if ((Boolean) exprEval.eval(a.getCondition()))
		{
			evalEnviroment.enterNewScope();
			// If return, stop evaling the rest
			eval(a.getStatements());
			evalEnviroment.exitScope();
		}
	}

	@Override public void visit(NPrint a)
	{
		markerS.push(false);

		Object x = exprEval.eval(a.getExpr());
		if (x instanceof Object[])
		{
			System.out.println(Arrays.toString((Object[]) x));
		}
		else
		{
			System.out.println(x);
		}
	}

	@Override public void visit(NWhile a)
	{
		markerS.push(false);

		evalEnviroment.enterNewScope();

		eval(a.getInitializers());

		while ((Boolean) exprEval.eval(a.getCondt()))
		{
			evalEnviroment.enterNewScope();
			eval(a.getBody());
			evalEnviroment.exitScope();
		}

		evalEnviroment.exitScope();
	}

	@Override public void visit(NReturn a)
	{
		// Peek after evaling a statement, if true, stop evaling, and return back up
		Object x = exprEval.eval(a.getExpr());
		markerS.push(true);
		returnValS.push(x);
	}


	public Stack<Boolean> getMarkerS()
	{
		return markerS;
	}

	public Stack<Object> getReturnValS()
	{
		return returnValS;
	}
}
