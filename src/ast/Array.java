package ast;

import java.util.LinkedList;
import java.util.List;

public class Array implements IASTExpr
{
	// List for multidimensional array
	private IASTType type;
	private List<IASTExpr> size;

	public Array(IASTType type, List<IASTExpr> size)
	{
		this.type = type;
		this.size = size;
	}

	public IASTType getType()
	{
		return type;
	}

	public List<IASTExpr> getSizes()
	{
		return size;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
