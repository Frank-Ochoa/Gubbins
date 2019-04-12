package ast;

import ntree.INExpr;
import ntree.NMult;

public class Mult implements IASTExprOperator
{
    private IASTExpr left;
    private IASTExpr right;

    public Mult(IASTExpr left, IASTExpr right)
    {
        this.left = left;
        this.right = right;
    }

    public IASTExpr getLeft()
    {
        return left;
    }

    public IASTExpr getRight()
    {
        return right;
    }

    @Override public void visit(IASTExprOperatorVisitor visitor)
    {
        visitor.visit(this);
    }

    //    @Override
//    public void checkUses(Map<String, Boolean> usageEnvironment)
//    {
//        this.left.checkUses(usageEnvironment);
//        this.right.checkUses(usageEnvironment);
//    }

    @Override
    public void visit(IASTExpressionVisitor visitor)
    {
        visitor.visit(this);
    }
}
