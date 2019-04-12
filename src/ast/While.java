package ast;

import java.util.List;

public class While implements IASTStatement
{
    private IASTExpr condt;
    private List<IASTStatement> body;

    public While(IASTExpr condt, List<IASTStatement> body)
    {
        this.condt = condt;
        this.body = body;
    }

    public IASTExpr getCondt()
    {
        return condt;
    }

    public List<IASTStatement> getBody()
    {
        return body;
    }

    @Override public void visit(IASTStatementVisitor visitor)
    {
		visitor.visit(this);
    }

    //    @Override
//    public void checkUses(Map<String, Boolean> usageEnvironment)
//    {
//        this.rhs.checkUses(usageEnvironment);
//    }

}
