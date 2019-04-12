package ast;

public class Identifier implements IASTExpr
{
    private String name;

    public Identifier(String name)
    {
        this.name = name;
    }

    public String getName()
    {
        return name;
    }


//    @Override
//    public void checkUses(Map<String, Boolean> usageEnvironment)
//    {
//        usageEnvironment.put(this.name, true);
//    }
    
        @Override
    public void visit(IASTExpressionVisitor visitor)
    {
        visitor.visit(this);
    }
}
