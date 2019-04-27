package ntree;

import java.util.List;

public class NRecord extends Expr
{
	private List<NAssignment> args;

	public NRecord(IType type, List<NAssignment> args)
	{
		super(type);
		this.args = args;
	}

	public List<NAssignment> getArgs()
	{
		return args;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
