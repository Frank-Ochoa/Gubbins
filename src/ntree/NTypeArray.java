package ntree;

import types.TypeException;

public class NTypeArray implements IType
{
	private IType type;

	public NTypeArray(IType type)
	{
		this.type = type;
	}

	public IType getType()
	{
		return type;
	}

	@Override public boolean equals(Object obj)
	{
		if (!(obj instanceof NTypeArray))
		{
			return false;
		}

		NTypeArray arr = (NTypeArray) obj;

		return this.getType().equals(arr.getType());
	}

	@Override public int hashCode()
	{
		return super.hashCode();
	}

	@Override public void arrayCheckSize(int size)
	{
		if (size == 0)
		{
			throw new TypeException("ARRAY DIMENSIONS DO NOT MATCH");
		}
		else
		{
			this.getType().arrayCheckSize(size - 1);
		}
	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
