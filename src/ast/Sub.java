package ast;

public class Sub implements IASTExprOperator
{
	private IASTExpr left;
	private IASTExpr right;

	public Sub(IASTExpr left, IASTExpr right)
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
