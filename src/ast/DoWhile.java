package ast;

import Main.UniqueNameSource;

import java.util.List;

public class DoWhile implements IASTStatement
{
	private Identifier initialTrue;
	private IASTExpr cond;
	private List<IASTStatement> body;

	public DoWhile(IASTExpr cond, List<IASTStatement> body)
	{
		this.initialTrue = new Identifier(UniqueNameSource.getUniqueName());
		this.cond = cond;
		this.body = body;
	}

	public Identifier getInitialTrue()
	{
		return initialTrue;
	}

	public IASTExpr getCond()
	{
		return cond;
	}

	public List<IASTStatement> getBody()
	{
		return body;
	}

	@Override public void visit(IASTStatementVisitor visitor)
	{
		visitor.visit(this);
	}
}
