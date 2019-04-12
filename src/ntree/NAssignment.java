package ntree;

import java.util.LinkedList;
import java.util.List;

public class NAssignment implements INStatement
{
    private NIdentifier lhs;
    private INExpr rhs;
    private List<INExpr> indices;

    public NAssignment(NIdentifier lhs, INExpr rhs, List<INExpr> indices)
    {
        this.lhs = lhs;
        this.rhs = rhs;
        this.indices = indices;
    }

    public NAssignment(NIdentifier lhs, INExpr rhs)
    {
        this(lhs, rhs, new LinkedList<>());
    }

    public NIdentifier getLhs()
    {
        return lhs;
    }

    public INExpr getRhs()
    {
        return rhs;
    }

    public List<INExpr> getIndices()
    {
        return indices;
    }

    @Override public void visit(INStatementVisitor visitor)
    {
        visitor.visit(this);
    }
}
