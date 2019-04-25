package ntree;

import java.util.List;

public class NTypeArgs implements IType
{
	List<IType> args;

	public NTypeArgs(List<IType> args)
	{
		this.args = args;
	}

	public List<IType> getArgs()
	{
		return args;
	}

	@Override public void arrayCheckSize(int size)
	{

	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
