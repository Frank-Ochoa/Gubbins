package ast;

public class RecordAccess implements IASTExpr
{
	private IASTExpr rec;
	private String index;

	public RecordAccess(IASTExpr rec, String index)
	{
		this.rec = rec;
		this.index = index;
	}

	public IASTExpr getRec()
	{
		return rec;
	}

	public String getIndex()
	{
		return index;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
