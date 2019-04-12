package ast;

public class TypeDouble implements IASTType
{

	public static TypeDouble DOUBLE = new TypeDouble();

	private TypeDouble()
	{
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
