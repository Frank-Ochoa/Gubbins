package Main;

import ast.*;
import ntree.*;

import java.util.Stack;

public class TypeTypeChecker implements IASTTypeVisitor, ITypeVisitor
{
	private Stack<IType> tstack;

	TypeTypeChecker()
	{
		this.tstack = new Stack<>();
	}

	IType typecheck(IASTType node)
	{
		node.visit(this);
		return tstack.pop();
	}

	IType typecheck(IType node)
	{
		node.visit(this);
		return tstack.pop();
	}

	private IType tcheck(IASTType node)
	{
		node.visit(this);
		return tstack.pop();
	}

	private IType tcheck(IType node)
	{
		node.visit(this);
		return tstack.pop();
	}

	private void ret(IType e)
	{
		tstack.push(e);
	}

	@Override public void visit(TypeInt a)
	{
		ret(NTypeInt.INT);
	}

	@Override public void visit(TypeBoolean a)
	{
		ret(NTypeBoolean.BOOLEAN);
	}

	@Override public void visit(TypeDouble a)
	{
		ret(NTypeDouble.DOUBLE);
	}

	@Override public void visit(TypeArray a)
	{
		ret(new NTypeArray(tcheck(a.getType())));
	}

	public void visit(NTypeArray a)
	{
		ret(tcheck(a.getType()));
	}

	@Override public void visit(NTypeBoolean a)
	{
		ret(NTypeBoolean.BOOLEAN);
	}

	@Override public void visit(NTypeInt a)
	{
		ret(NTypeInt.INT);
	}

	@Override public void visit(NTypeDouble a)
	{
		ret(NTypeDouble.DOUBLE);
	}
}
