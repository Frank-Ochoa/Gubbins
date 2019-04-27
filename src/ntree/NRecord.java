package ntree;

import java.util.List;

public class NRecord extends Expr
{
	private List<NAssignment> elements;

	public NRecord(IType type, List<NAssignment> elements)
	{
		super(type);
		this.elements = elements;
	}

	public List<NAssignment> getArgs()
	{
		return elements;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
