package ntree;

//  base class for all expressions
public abstract class Expr implements INExpr
{
    private IType type;

    public Expr(IType type)
    {
        this.type = type;
    }

    public IType getType()
    {
        return type;
    }

}
