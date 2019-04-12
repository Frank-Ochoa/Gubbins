package ast;

public class Dble implements IASTExpr
{
    private double value;

    public Dble(double value)
    {
        this.value = value;
    }

    public double getValue()
    {
        return value;
    }

    @Override
    public void visit(IASTExpressionVisitor visitor)
    {
        visitor.visit(this);
    }
}
