package ntree;

public class NOR extends Expr
{
	private INExpr $var;
	private INExpr condt;

	public NOR(INExpr $var, INExpr condt)
	{
		super(NTypeBoolean.BOOLEAN);
		this.$var = $var;
		this.condt = condt;
	}

	public INExpr get$var()
	{
		return $var;
	}

	public INExpr getCondt()
	{
		return condt;
	}

	@Override public void visit(INExprVisitor visitor)
	{
		visitor.visit(this);
	}
}
