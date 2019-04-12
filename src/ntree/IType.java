package ntree;

public interface IType
{
	void arrayCheckSize(int size);
	void visit (ITypeVisitor visitor);
}
