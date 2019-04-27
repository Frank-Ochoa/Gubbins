package ast;

public interface IASTStatementVisitor
{
	void visit (Declaration a);
	void visit (Assignment a);
	void visit (For a);
	void visit (DoWhile a);
	void visit (While a);
	void visit (DeclareAssign a);
	void visit (If a);
	void visit (Print a);
	void visit (Return a);
}
