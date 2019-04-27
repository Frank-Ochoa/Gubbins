package ntree;

import java.util.List;

public class NFunction extends Expr
{
	private List<INStatement> body;

	public NFunction(IType typeFunction, List<INStatement> body)
	{
		super(typeFunction);
		this.body = body;
	}

	public List<INStatement> getBody()
	{
		return body;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
