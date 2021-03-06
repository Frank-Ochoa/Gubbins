package ntree;

public interface INExprVisitor
{
	void visit(NPlus a);

	void visit(NInt a);

	void visit(NIdentifier a);

	void visit(NArray a);

	void visit(NArrayIndex a);

	void visit(NBool a);

	void visit(NDouble a);

	void visit(NMult a);

	void visit(NDiv a);

	void visit(NMod a);

	void visit(NSub a);

	void visit(NLessThan a);

	void visit(NGreaterThan a);

	void visit(NLessThanEQ a);

	void visit(NGreaterThanEQ a);

	void visit(NEQ a);

	void visit(NNotEQ a);

	void visit(NAnd a);

	void visit(Promote a);

	void visit(NOR a);

	void visit(NRecord a);

	void visit(NFunction a);

	void visit(NRecordAccess a);

	void visit(NFunctionCall a);
}
