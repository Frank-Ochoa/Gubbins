package symtab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class SymTab<T> implements ISymTab<T>
{
	private LinkedList<Map<String, T>> scopes;

	public SymTab()
	{
		this.scopes = new LinkedList<>();
	}

	@Override public void declare(String name, T type)
	{
		//  declare in the first scope = inner scope
		scopes.get(0).put(name, type);
	}

	public void declareCheckIfThere(String name, T type)
	{
		for (Map<String, T> scope : scopes)
		{
			if (scope.containsKey(name))
			{
				scope.put(name, type);
				break;
			}
		}
	}

	@Override public T lookup(String name)
	{
		for (Map<String, T> scope : scopes)
		{
			T t = scope.get(name);
			if (t != null)
			{
				return t;
			}
		}

		return null;
	}

	@Override public void enterNewScope()
	{
		scopes.addFirst(new HashMap<>());
	}

	@Override public T lookupNearest(String name)
	{
		T t = scopes.get(0).get(name);
		return t;
	}

	@Override public void exitScope()
	{
		scopes.removeFirst();
	}

	@Override public List<String> getClosureIdents()
	{
		// Might want things from all different scopes
		List<String> idents = new LinkedList<>();

		// Maybe all scopes but function scope

		for(Map<String, T> scope : scopes)
		{
			for (Map.Entry<String, T> entry : scope.entrySet())
			{
				idents.add(entry.getKey());
			}
		}

		return idents;
	}

	@Override public String toString()
	{
		return "SymTab{" + "scopes=" + scopes + '}';
	}
}
