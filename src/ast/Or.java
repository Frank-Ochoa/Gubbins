package ast;

public class Or implements IASTExprOperator
{
	private IASTExpr left;
	private IASTExpr right;

	public Or(IASTExpr left, IASTExpr right)
	{
		this.left = left;
		this.right = right;
	}

	@Override public IASTExpr getLeft()
	{
		return left;
	}

	@Override public IASTExpr getRight()
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
