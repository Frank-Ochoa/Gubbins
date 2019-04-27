package ast;

import java.util.Map;

public class TypeRecord implements IASTType
{
	private Map<Identifier, IASTType> args;

	public TypeRecord(Map<Identifier, IASTType> args)
	{
		this.args = args;
	}

	public Map<Identifier, IASTType> getArgs()
	{
		return args;
	}

	@Override public void visit(IASTTypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
