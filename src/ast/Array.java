package ast;

import java.util.LinkedList;
import java.util.List;

public class Array implements IASTExpr
{
	// List for multidimensional array
	private IASTType type;
	private List<IASTExpr> size;

	// Array value has a type and list of size that represent the dimensions of the array, I need to know
	// that size though
	// IAST Expr within the list has to to represent an int
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
