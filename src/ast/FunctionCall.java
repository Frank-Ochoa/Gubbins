package ast;

import java.util.List;

public class FunctionCall implements IASTExpr
{
	private IASTExpr function;
	private List<IASTExpr> argValues;

	public FunctionCall(IASTExpr function, List<IASTExpr> argValues)
	{
		this.function = function;
		this.argValues = argValues;
	}

	public IASTExpr getFunction()
	{
		return function;
	}

	public List<IASTExpr> getArgValues()
	{
		return argValues;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}
}
