package ast;

public class LessThan implements IASTExprOperator
{
	private IASTExpr left;
	private IASTExpr right;

	public LessThan(IASTExpr left, IASTExpr right)
	{
		this.left = left;
		this.right = right;
	}

	public IASTExpr getLeft()
	{
		return left;
	}

	public IASTExpr getRight()
	{
		return right;
	}

	@Override public void visit(IASTExprOperatorVisitor visitor)
	{
		visitor.visit(this);
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
