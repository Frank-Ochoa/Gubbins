package ntree;

public class NLessThan extends Expr
{
	private INExpr left;
	private INExpr right;

	public NLessThan(INExpr left, INExpr right)
	{
		super(NTypeBoolean.BOOLEAN);
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
