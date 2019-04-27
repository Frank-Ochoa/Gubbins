package ast;

import java.util.List;

public class Function implements IASTExpr
{
	// typeFunction holds the list of args type as well as the return type
	private IASTType typeFunction;
	// List of statements that represent the actions done in the body
	private List<IASTStatement> body;

	public Function(IASTType typeFunction, List<IASTStatement> body)
	{
		this.typeFunction = typeFunction;
		this.body = body;
	}

	public IASTType getTypeFunction()
	{
		return typeFunction;
	}

	public List<IASTStatement> getBody()
	{
		return body;
	}

	@Override public void visit(IASTExpressionVisitor visitor)
	{
		visitor.visit(this);
	}

}
