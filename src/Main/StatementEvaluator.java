package Main;

import ntree.*;
import symtab.ISymTab;

import java.util.ArrayList;
import java.util.List;

public class StatementEvaluator implements INStatementVisitor
{
	private ISymTab<Object> evalEnviroment;
	private ExpressionEvaluator exprEval;

	public StatementEvaluator(ISymTab<Object> evalEnviroment)
	{
		this.evalEnviroment = evalEnviroment;
		this.exprEval = new ExpressionEvaluator(evalEnviroment);
	}

	public void eval(INStatement e)
	{
		e.visit(this);
	}

	private void eval(List<INStatement> l)
	{
		for (INStatement e : l)
		{
			eval(e);
		}
	}

	@Override public void visit(NDeclaration a)
	{
		String ident = a.getRhs().getName();
		evalEnviroment.declare(ident, null);
	}

	@Override public void visit(NDeclareAssign a)
	{
		String ident = a.getIdentifier().getName();
		Object value = exprEval.eval(a.getExpr());

		evalEnviroment.declare(ident, value);
	}

	@SuppressWarnings("Duplicates") @Override public void visit(NAssignment a)
	{
		// Consider arrays, indices not empty

		// If indices empty, it's a normal assignment
		if (a.getIndices().isEmpty())
		{
			String ident = a.getLhs().getName();
			Object value = exprEval.eval(a.getRhs());
			evalEnviroment.declare(ident, value);
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
		if ((Boolean) exprEval.eval(a.getCondition()))
		{
			eval(a.getStatements());
		}
	}

	@Override public void visit(NPrint a)
	{
		System.out.println(exprEval.eval(a.getExpr()));
	}

	@Override public void visit(NWhile a)
	{
		evalEnviroment.enterNewScope();

		eval(a.getInitializers());

		while (true)
		{
			if ((Boolean) exprEval.eval(a.getCondt()))
			{
				eval(a.getBody());
			}
			else
			{
				break;
			}
		}
	}
}
