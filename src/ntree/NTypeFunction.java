package ntree;

import types.TypeException;

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
		if(size != 0)
		{
			throw new TypeException("SIZES DO NOT MATCH");
		}
	}

	@Override public boolean equals(Object obj)
	{
		if (!(obj instanceof NTypeFunction))
		{
			return false;
		}

		NTypeFunction func = (NTypeFunction) obj;

		return (this.args.equals(func.getArgs()) && this.result.equals(func.getResult()));
	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}

}
