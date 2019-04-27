package ast;

import java.util.Map;

public class Record implements IASTExpr
{
	private IASTType type;
	private Map<Identifier, IASTType> args;

	public Record(IASTType type, Map<Identifier, IASTType> args)
	{
		this.type = type;
		this.args = args;
	}

	public IASTType getType()
	{
		return type;
	}

	public Map<Identifier, IASTType> getArgs()
	{
		return args;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
