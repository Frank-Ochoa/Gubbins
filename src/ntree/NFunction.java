package ntree;

import java.util.List;

public class NFunction extends Expr
{
	private List<INStatement> body;
	private List<String> closureIdents;

	public NFunction(IType typeFunction, List<INStatement> body, List<String> closureIdents)
	{
		super(typeFunction);
		this.body = body;
		this.closureIdents = closureIdents;
	}

	public List<INStatement> getBody()
	{
		return body;
	}

	public List<String> getClosureIdents()
	{
		return closureIdents;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
