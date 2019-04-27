package ntree;

import java.util.List;

public class NFunction
{
	private IType typeFunction;
	private List<INStatement> body;

	public NFunction(IType typeFunction, List<INStatement> body)
	{
		this.typeFunction = typeFunction;
		this.body = body;
	}

	public IType getTypeFunction()
	{
		return typeFunction;
	}

	public List<INStatement> getBody()
	{
		return body;
	}
}
