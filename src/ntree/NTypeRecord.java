package ntree;

import types.TypeException;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class NTypeRecord implements IType
{
	// This would be better represented as a Hashmap instead of a map
	private Map<String, IType> args;

	public NTypeRecord(Map<String, IType> args)
	{
		this.args = args;
	}

	public Map<String, IType> getArgs()
	{
		return args;
	}


	@Override public void arrayCheckSize(int size)
	{
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


		if(this.args.size() != rec.getArgs().size())
		{
			return false;
		}

		Iterator<Map.Entry<String, IType>> it1 = args.entrySet().iterator();
		Iterator<Map.Entry<String, IType>> it2 = rec.getArgs().entrySet().iterator();

		while(it1.hasNext() && it2.hasNext())
		{
			Map.Entry<String, IType> map1 = it1.next();
			Map.Entry<String, IType> map2 = it2.next();

			if (!(map1.getKey().equals(map2.getKey())))
			{
				return false;
			}
			else if (!(map1.getValue().equals(map2.getValue())))
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
