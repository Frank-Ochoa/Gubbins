package symtab;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class SymTab<T> implements ISymTab<T>
{
    private LinkedList<Map<String, T>> scopes;

    public SymTab()
    {
        this.scopes = new LinkedList<>();
    }

    @Override
    public void declare(String name, T type)
    {
        //  declare in the first scope = inner scope
        scopes.get(0).put(name, type);
    }

    @Override
    public T lookup(String name)
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

    @Override
    public void enterNewScope()
    {
        scopes.addFirst(new HashMap<>());
    }

    @Override
    public void exitScope()
    {
        scopes.removeFirst();
    }

    @Override
    public String toString()
    {
        return "SymTab{" + "scopes=" + scopes + '}';
    }
}
