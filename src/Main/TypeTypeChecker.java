package Main;

import ast.*;
import ntree.*;
import types.TypeException;

import java.util.*;

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
		// All I need to know here is that the lhs is a record
		// and that the rhs is a kind of type

		// Make sure that the left side is a type record
		IType record = tcheck(a.getArgs());
		if(!(record instanceof NTypeRecord))
		{
			throw new TypeException("BAD FUNCTION");
		}

		// Rhs can be type anything? I think, maybe not function, but probably
		IType returnType = tcheck(a.getResult());

		ret(new NTypeFunction(record, returnType));
	}

	@Override public void visit(TypeRecord a)
	{
		List<NIdentifier> args = new LinkedList<>();
		for(Map.Entry<Identifier, IASTType> entry : a.getArgs().entrySet())
		{
			String name = entry.getKey().getName();
			IType type = typecheck(entry.getValue());

			args.add(new NIdentifier(name, type));
		}

		ret(new NTypeRecord(args));
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
		ret(a);
	}

	@Override public void visit(NTypeRecord a)
	{
		ret(a);
	}
}
