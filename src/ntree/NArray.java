package ntree;

import java.util.List;

public class NArray extends Expr
{
	private List<INExpr> size;

	public NArray(IType type, List<INExpr> size)
	{
		super(type);
		this.size = size;
	}

	public List<INExpr> getSize()
	{
		return size;
	}


	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
