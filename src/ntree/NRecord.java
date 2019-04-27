package ntree;

import java.util.List;

public class NRecord extends Expr
{
	private List<INStatement> elements;

	public NRecord(IType type, List<INStatement> elements)
	{
		super(type);
		this.elements = elements;
	}

	public List<INStatement> getArgs()
	{
		return elements;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
