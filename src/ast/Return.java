package ast;

public class Return implements IASTStatement
{
	private IASTExpr expr;

	public Return(IASTExpr expr)
	{
		this.expr = expr;
	}

	public IASTExpr getExpr()
	{
		return expr;
	}

	@Override public void visit(IASTStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
