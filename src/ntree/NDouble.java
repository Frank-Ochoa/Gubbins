package ntree;

public class NDouble extends Expr
{
    private double value;

    public NDouble(double value)
    {
        super(NTypeDouble.DOUBLE);
        this.value = value;
    }

    public double getValue()
    {
        return value;
    }

    @Override public void visit(INExprVisitor visitor)
    {
        visitor.visit(this);
    }
}
