package ast;

public class TypeArray implements IASTType
{
	private IASTType type;

	public TypeArray(IASTType type)
	{
		this.type = type;
	}

	public IASTType getType()
	{
		return type;
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}

}
