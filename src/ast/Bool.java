package ast;

public class Bool implements IASTExpr
{
    private boolean value;

    public Bool(boolean value)
    {
        this.value = value;
    }

    public boolean getValue()
    {
        return value;
    }

    @Override
    public void visit(IASTExpressionVisitor visitor)
    {
        visitor.visit(this);
    }
}
