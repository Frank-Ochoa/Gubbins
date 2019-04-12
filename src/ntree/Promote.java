package ntree;

public class Promote extends Expr
{
    private INExpr node;

    public Promote(IType type, INExpr node)
    {
        super(type);
        this.node = node;
    }

    public INExpr getNode()
    {
        return node;
    }

    @Override public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
