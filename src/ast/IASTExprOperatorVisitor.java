package ast;

public interface IASTExprOperatorVisitor
{
	void visit(LessThan a);

	void visit(GreaterThan a);

	void visit(LessThanEQ a);

	void visit(GreaterThanEQ a);

	void visit(EQ a);

	void visit(NotEQ a);

	void visit(And a);

	void visit(Or a);

	void visit(Mult a);

	void visit(Div a);

	void visit(Mod a);

	void visit(Plus a);

	void visit(Sub a);
}
