package ast;

public class TypeBoolean implements IASTType
{
	public static TypeBoolean BOOLEAN = new TypeBoolean();

	private TypeBoolean()
	{
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
