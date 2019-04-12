package ntree;

public interface INStatement extends INTree
{
	void visit(INStatementVisitor visitor);
}
