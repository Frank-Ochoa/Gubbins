package ntree;

import types.TypeException;

public class NTypeDouble implements IType
{
	public static NTypeDouble DOUBLE = new NTypeDouble();

	private NTypeDouble()
	{
	}

	@Override public boolean equals(Object obj)
	{
		return this == obj;
	}

	@Override public int hashCode()
	{
		return super.hashCode();
	}

	@Override public void arrayCheckSize(int size)
	{
		if(size != 0)
		{
			throw new TypeException("SIZES DO NOT MATCH");
		}
	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}

}
