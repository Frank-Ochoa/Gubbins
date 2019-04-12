package ntree;

public class NPrint implements INStatement
{
	private INExpr expr;

	public NPrint(INExpr expr)
	{
		this.expr = expr;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}

	public INExpr getExpr()
	{
		return expr;
	}
}
