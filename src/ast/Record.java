package ast;

import java.util.List;

public class Record implements IASTExpr
{
	private List<IASTStatement> elements;

	public Record(List<IASTStatement> elements)
	{
		this.elements = elements;
	}

	public List<IASTStatement> getElements()
	{
		return elements;
	}

	// Compute type of records based on its assignments, maybe in the N version


	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
