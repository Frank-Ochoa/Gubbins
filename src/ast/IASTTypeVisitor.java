package ast;

public interface IASTTypeVisitor
{
	void visit(TypeInt a);

	void visit(TypeBoolean a);

	void visit(TypeDouble a);

	void visit(TypeArray a);
}
