package symtab;

import java.util.List;

public interface ISymTab<T>
{
    void declare(String name, T type);

    void declareCheckIfThere(String name, T type);
    
    T lookup(String name);
    
    void enterNewScope();

    T lookupNearest(String name);
    
    void exitScope();

    List<String> getClosureIdents();
}
