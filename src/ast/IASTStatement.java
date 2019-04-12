package ast;

public interface IASTStatement extends IAST
{
	void visit(IASTStatementVisitor visitor);
}
