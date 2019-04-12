package ntree;

public class NBool extends Expr
{
    private boolean value;

    public NBool(boolean value)
    {
        super(NTypeBoolean.BOOLEAN);
        this.value = value;
    }

    public boolean isValue()
    {
        return value;
    }

    @Override public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
