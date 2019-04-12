package ast;

import java.util.List;

public class Declaration implements IASTStatement
{
	private IASTType lhs;
	private Identifier rhs;
	private List<IASTExpr> indices;

	public Declaration(IASTType lhs, Identifier rhs, List<IASTExpr> indices)
	{
		this.lhs = lhs;
		this.rhs = rhs;
		this.indices = indices;
	}

	public IASTType getLhs()
	{
		return lhs;
	}

	public Identifier getRhs()
	{
		return rhs;
	}

	public List<IASTExpr> getIndices()
	{
		return indices;
	}

	@Override public void visit(IASTStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
