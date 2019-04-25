package ntree;

public class NTypeFunction implements IType
{
	private IType args;
	private IType result;

	public NTypeFunction(IType args, IType result)
	{
		this.args = args;
		this.result = result;
	}

	public IType getArgs()
	{
		return args;
	}

	public IType getResult()
	{
		return result;
	}

	@Override public void arrayCheckSize(int size)
	{
		// Because could have an array of functions??? lol
	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
