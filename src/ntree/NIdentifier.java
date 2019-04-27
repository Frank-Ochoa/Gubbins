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

    @Override public boolean equals(Object obj)
    {
        if (!(obj instanceof NIdentifier))
        {
            return false;
        }

        NIdentifier iden = (NIdentifier) obj;

        return (this.getType().equals(iden.getType()) && this.name.equals(iden.name));
    }

    @Override public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
