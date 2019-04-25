package Main;

import ast.*;
import ntree.*;

import java.util.LinkedList;
import java.util.List;
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

	@Override public void visit(TypeFunction a)
	{
		NTypeArgs args = (NTypeArgs) tcheck(a.getArgs());
		IType returnType = tcheck(a.getResult());

		ret(new NTypeFunction(args, returnType));
	}

	@Override public void visit(TypeArgs a)
	{
		List<IType> nArgs = new LinkedList<>();
		// Normalize the list of types
		for(IASTType type : a.getArgs())
		{
			nArgs.add(tcheck(type));
		}
		ret(new NTypeArgs(nArgs));
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

	@Override public void visit(NTypeFunction a)
	{

	}

	@Override public void visit(NTypeArgs a)
	{

	}
}
