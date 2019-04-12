package ntree;

import java.util.LinkedList;
import java.util.List;

public class NWhile implements INStatement
{
    private List<INStatement> initializers;
    private INExpr condt;
    private List<INStatement> body;

    // Represents a normalized For
    public NWhile(List<INStatement> initializers, INExpr condt, List<INStatement> body)
    {
        this.initializers = initializers;
        this.condt = condt;
        this.body = body;
    }

    // Represents a normalized While, empty list of initializers
    public NWhile(INExpr condt, List<INStatement> body)
    {
        this(new LinkedList<>(), condt, body);
    }

    public List<INStatement> getInitializers()
    {
        return initializers;
    }

    public INExpr getCondt()
    {
        return condt;
    }

    public List<INStatement> getBody()
    {
        return body;
    }

    @Override public void visit(INStatementVisitor visitor)
    {
        visitor.visit(this);
    }
}
