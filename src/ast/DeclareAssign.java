package ast;

import java.util.List;

public class DeclareAssign implements IASTStatement
{
	private IASTType type;
	private Identifier ident;
	private IASTExpr expr;
	private List<IASTExpr> indicies;

	public DeclareAssign(IASTType type, Identifier ident, IASTExpr expr, List<IASTExpr> indicies)
	{
		this.type = type;
		this.ident = ident;
		this.expr = expr;
		this.indicies = indicies;
	}

	public IASTType getType()
	{
		return type;
	}

	public Identifier getIden()
	{
		return ident;
	}

	public IASTExpr getExpr()
	{
		return expr;
	}

	public List<IASTExpr> getIndex()
	{
		return indicies;
	}

	@Override public void visit(IASTStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
