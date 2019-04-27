package ntree;

public class NReturn implements INStatement
{
	private INExpr expr;

	public NReturn(INExpr expr)
	{
		this.expr = expr;
	}

	public INExpr getExpr()
	{
		return expr;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
