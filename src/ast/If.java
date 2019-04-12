package ast;

import java.util.List;

public class If implements IASTStatement
{
	private IASTExpr condition;
	private List<IASTStatement> statements;

	public If(IASTExpr condition, List<IASTStatement> statements)
	{
		this.condition = condition;
		this.statements = statements;
	}

	public IASTExpr getCondition()
	{
		return condition;
	}

	public List<IASTStatement> getStatements()
	{
		return statements;
	}

	@Override public void visit(IASTStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
