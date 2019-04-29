package ntree;

import Main.UniqueNameSource;

public class NReturn implements INStatement
{
	private INExpr expr;
	private String uniqueName;

	public NReturn(INExpr expr)
	{
		this.expr = expr;
		this.uniqueName = UniqueNameSource.getUniqueName();
	}

	public INExpr getExpr()
	{
		return expr;
	}

	public String getUniqueName()
	{
		return uniqueName;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
