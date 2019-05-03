package symtab;

import java.util.List;
import java.util.Map;

public interface ISymTab<T>
{
    void declare(String name, T type);

    void declareCheckIfThere(String name, T type);
    
    T lookup(String name);
    
    void enterNewScope();

    void enterNewScope(Map<String, T> enviro);

    Map<String, T> getCurrentScope();

    T lookupNearest(String name);
    
    void exitScope();

    List<String> getClosureIdents();
}
