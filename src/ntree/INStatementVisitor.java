package ntree;

public interface INStatementVisitor
{
	void visit(NAssignment a);
	void visit(NDeclaration a);
	void visit(NDeclareAssign a);
	void visit(NIf a);
	void visit(NPrint a);
	void visit(NWhile a);
}
