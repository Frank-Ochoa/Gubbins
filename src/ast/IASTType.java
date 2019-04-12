package ast;

public interface IASTType extends IAST
{
	void visit(IASTTypeVisitor visitor);
}
