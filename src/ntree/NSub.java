package ntree;

public class NSub extends Expr
{
	private INExpr left;
	private INExpr right;

	public NSub(INExpr left, INExpr right)
	{
		super(left.getType());
		assert left.getType() == right.getType();
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
