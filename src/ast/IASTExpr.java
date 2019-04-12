package ast;

//  a AST node that is an expression

public interface IASTExpr extends IAST
{
	void visit(IASTExpressionVisitor visitor);
}
