package ntree;

public class NIdentifier extends Expr
{
    private String name;

    public NIdentifier(String name, IType type)
    {
        super(type);
        this.name = name;
    }

    public String getName()
    {
        return name;
    }

    @Override public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
