package ntree;

public class NInt extends Expr
{
    private int value;

    public NInt(int value)
    {
        super(NTypeInt.INT);
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
