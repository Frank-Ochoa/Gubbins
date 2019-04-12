package ast;

public interface IASTExprOperatorVisitor
{
	void visit (LessThan a);
	void visit (GreaterThan a);
	void visit (Mult a);
	void visit (Div a);
	void visit (Plus a);
	void visit (Sub a);
}
