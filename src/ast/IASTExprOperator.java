package ast;

public interface IASTExprOperator extends IASTExpr
{
	IASTExpr getLeft();
	IASTExpr getRight();
	void visit(IASTExprOperatorVisitor visitor);
}
