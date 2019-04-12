package ntree;

import java.util.LinkedList;
import java.util.List;

public class NDeclareAssign implements INStatement
{
	private IType type;
	private NIdentifier identifier;
	private INExpr expr;
	private List<INExpr> indicies;

	public NDeclareAssign(IType type, NIdentifier identifier, INExpr expr, List<INExpr> indicies)
	{
		this.type = type;
		this.identifier = identifier;
		this.expr = expr;
		this.indicies = indicies;
	}

	public NDeclareAssign(IType type, NIdentifier identifier, INExpr expr)
	{
		this(type, identifier, expr, new LinkedList<>());
	}

	public IType getType()
	{
		return type;
	}

	public NIdentifier getIdentifier()
	{
		return identifier;
	}

	public INExpr getExpr()
	{
		return expr;
	}

	public List<INExpr> getIndex()
	{
		return indicies;
	}

	@Override public void visit(INStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
