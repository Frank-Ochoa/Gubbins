package ast;

import java.util.List;

public class TypeArgs implements IASTType
{
	private List<IASTType> args;

	public TypeArgs(List<IASTType> args)
	{
		this.args = args;
	}

	public List<IASTType> getArgs()
	{
		return args;
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
