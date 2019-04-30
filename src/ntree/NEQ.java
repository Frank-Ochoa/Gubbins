package ntree;

public class NEQ extends Expr
{
	private INExpr left;
	private INExpr right;

	public NEQ(INExpr left, INExpr right)
	{
		super(NTypeBoolean.BOOLEAN);
		this.left = left;
		this.right = right;
	}

	public INExpr getLeft()
	{
		return left;
	}

	public INExpr getRight()
	{
		return right;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
