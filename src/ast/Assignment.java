package ast;

import java.util.List;

public class Assignment implements IASTStatement
{
    private Identifier lhs;
    private IASTExpr rhs;
    private List<IASTExpr> indicies;

    public Assignment(Identifier lhs, IASTExpr rhs, List<IASTExpr> indicies)
    {
        this.lhs = lhs;
        this.rhs = rhs;
        this.indicies = indicies;
    }

    public List<IASTExpr> getIndex()
    {
        return indicies;
    }

    public Identifier getLhs()
    {
        return lhs;
    }

    public IASTExpr getRhs()
    {
        return rhs;
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
