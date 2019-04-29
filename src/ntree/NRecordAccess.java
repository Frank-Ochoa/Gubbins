package ntree;

public class NRecordAccess extends Expr
{
	private INExpr rec;
	private String index;

	public NRecordAccess(IType type, INExpr rec, String index)
	{
		super(type);
		this.rec = rec;
		this.index = index;
	}

	public INExpr getRec()
	{
		return rec;
	}

	public String getIndex()
	{
		return index;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
