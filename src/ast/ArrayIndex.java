package ast;

import java.util.List;

public class ArrayIndex implements IASTExpr
{
	private IASTExpr array;
	private List<IASTExpr> indices;

	public ArrayIndex(IASTExpr array, List<IASTExpr> indices)
	{
		this.array = array;
		this.indices = indices;
	}

	public IASTExpr getArray()
	{
		return array;
	}

	public List<IASTExpr> getIndices()
	{
		return indices;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
