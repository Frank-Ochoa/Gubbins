package ast;

public class Int implements IASTExpr
{
    private int value;

    public Int(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    @Override
    public void visit(IASTExpressionVisitor visitor)
    {
        visitor.visit(this);
    }
}
