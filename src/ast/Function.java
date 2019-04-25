package ast;

import java.util.List;

public class Function
{
	// typeFunction holds the list of args type as well as the return type
	private IASTType typeFunction;
	// List of idents that correspond to the list of args. size have to be ==
	private List<IASTExpr> idents;
	// List of statements that represent the actions done in the body
	private List<IASTStatement> body;

	public Function(IASTType typeFunction, List<IASTExpr> idents, List<IASTStatement> body)
	{
		this.typeFunction = typeFunction;
		this.idents = idents;
		this.body = body;
	}

	public IASTType getTypeFunction()
	{
		return typeFunction;
	}

	public List<IASTExpr> getIdents()
	{
		return idents;
	}

	public List<IASTStatement> getBody()
	{
		return body;
	}
}
