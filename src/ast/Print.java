package ast;

public class Print implements IASTStatement
{
	private IASTExpr expr;

	public Print(IASTExpr expr)
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
