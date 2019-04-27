package ntree;

import types.TypeException;

import java.util.Iterator;
import java.util.List;

public class NTypeRecord implements IType
{
	private List<NIdentifier> args;

	public NTypeRecord(List<NIdentifier> args)
	{
		this.args = args;
	}

	public List<NIdentifier> getArgs()
	{
		return args;
	}

	@Override public void arrayCheckSize(int size)
	{
		// This is going to check the size of the actual list, against the list of other records
		// So (int a, int b), need to make sure in the body there is only two assignments
		// Also need to make sure that (int a, int b) a = rec {a = 3, b = 4;}
		// IE in a function (int a, int b) -> int f := (int a, int b) -> int
		// need to make sure that the lhs record == the rhs record in both types and size
		// but that is during a declare and assign, as well as an assignment

		// The rules are different for a record and a function

		// This method itself though just needs to return like what an int does
		if(size != 0)
		{
			throw new TypeException("SIZES DO NOT MATCH");
		}


	}

	@Override public boolean equals(Object obj)
	{
		if (!(obj instanceof NTypeRecord))
		{
			return false;
		}

		NTypeRecord rec = (NTypeRecord) obj;

		// For two record types to be ==, they need to have the same size
		// and the same types, but maybe or maybe not the same idents, that kind of
		// doesn't matter so much, I have it set up that way rn though but can change
		// the code is in NIdent

		if(this.args.size() != rec.getArgs().size())
		{
			return false;
		}

		Iterator<NIdentifier> it1 = args.iterator();
		Iterator<NIdentifier> it2 = rec.getArgs().iterator();

		while(it1.hasNext() && it2.hasNext())
		{
			if(!it1.next().equals(it2.next()))
			{
				return false;
			}
		}

		return true;
	}

	@Override public void visit(ITypeVisitor visitor)
	{
		visitor.visit(this);
	}
}
