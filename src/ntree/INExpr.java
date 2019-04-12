package ntree;

//  normalized tree nodes representing expressions

public interface INExpr extends INTree
{
	IType getType();
	void visit(INExprVisitor visitor);
}
