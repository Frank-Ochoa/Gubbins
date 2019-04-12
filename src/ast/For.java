package ast;

import java.util.List;

public class For implements IASTStatement
{
    private Identifier identifier;
    private IASTExpr range;
    private List<IASTStatement> body;

    public For(Identifier identifier, IASTExpr range, List<IASTStatement> body)
    {
        this.identifier = identifier;
        this.range = range;
        this.body = body;
    }

    public Identifier getIdentifier()
    {
        return identifier;
    }

    public IASTExpr getRange()
    {
        return range;
    }

    public List<IASTStatement> getBody()
    {
        return body;
    }

//    @Override
//    public void checkUses(Map<String, Boolean> usageEnvironment)
//    {
//        this.rhs.checkUses(usageEnvironment);
//    }

    @Override public void visit(IASTStatementVisitor visitor)
    {
        visitor.visit(this);
    }
}
