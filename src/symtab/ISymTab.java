package symtab;

public interface ISymTab<T>
{
    void declare(String name, T type);

    void declareCheckIfThere(String name, T type);
    
    T lookup(String name);
    
    void enterNewScope();
    
    void exitScope();
}
