package ntree;

public class NDeclaration implements INStatement
{
	private IType lhs;
	private NIdentifier rhs;

	public NDeclaration(IType lhs, NIdentifier rhs)
	{
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public IType getLhs()
	{
		return lhs;
	}

	public NIdentifier getRhs()
	{
		return rhs;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
