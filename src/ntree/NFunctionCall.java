package ntree;

import java.util.List;

public class NFunctionCall extends Expr
{
	private INExpr function;
	private List<INExpr> argValues;

	public NFunctionCall(IType type, INExpr function, List<INExpr> argValues)
	{
		super(type);
		this.function = function;
		this.argValues = argValues;
	}

	public INExpr getFunction()
	{
		return function;
	}

	public List<INExpr> getArgValues()
	{
		return argValues;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
