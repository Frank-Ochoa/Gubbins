package ntree;

import java.util.List;

public class NFunction
{
	private IType typeFunction;
	private List<INExpr> idents;
	private List<INStatement> body;

	public NFunction(IType typeFunction, List<INExpr> idents, List<INStatement> body)
	{
		this.typeFunction = typeFunction;
		this.idents = idents;
		this.body = body;
	}

	public IType getTypeFunction()
	{
		return typeFunction;
	}

	public List<INExpr> getIdents()
	{
		return idents;
	}

	public List<INStatement> getBody()
	{
		return body;
	}
}
