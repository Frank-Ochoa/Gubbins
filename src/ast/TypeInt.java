package ast;

public class TypeInt implements IASTType
{
	public static TypeInt INT = new TypeInt();

	private TypeInt()
	{
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}

}
