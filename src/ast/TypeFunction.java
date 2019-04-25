package ast;

public class TypeFunction implements IASTType
{
	// Args will be a TypeArgs, which is a list of types
	private IASTType args;
	private	IASTType result;

	public TypeFunction(IASTType args, IASTType result)
	{
		this.args = args;
		this.result = result;
	}

	public IASTType getArgs()
	{
		return args;
	}

	public IASTType getResult()
	{
		return result;
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
