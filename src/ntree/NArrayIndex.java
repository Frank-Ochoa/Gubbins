package ntree;

import java.util.List;

public class NArrayIndex extends Expr
{
	private INExpr array;
	private List<INExpr> indices;

	public NArrayIndex(IType type, INExpr array, List<INExpr> indices)
	{
		super(type);
		this.array = array;
		this.indices = indices;
	}

	public INExpr getArray()
	{
		return array;
	}

	public List<INExpr> getIndices()
	{
		return indices;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
