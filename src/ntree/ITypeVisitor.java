package ntree;

public interface ITypeVisitor
{
	void visit (NTypeArray a);
	void visit (NTypeBoolean a);
	void visit (NTypeInt a);
	void visit (NTypeDouble a);
	void visit (NTypeFunction a);
	void visit (NTypeRecord a);
}
