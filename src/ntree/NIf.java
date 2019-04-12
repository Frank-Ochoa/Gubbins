package ntree;

import java.util.List;

public class NIf implements INStatement
{
	private INExpr condition;
	private List<INStatement> statements;

	public NIf(INExpr condition, List<INStatement> statements)
	{
		this.condition = condition;
		this.statements = statements;
	}

	public INExpr getCondition()
	{
		return condition;
	}

	public List<INStatement> getStatements()
	{
		return statements;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
