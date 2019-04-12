package types;

public class UndeclaredIdentifer extends TypeException
{
    public UndeclaredIdentifer(String var)
    {
        super("Undeclared Identifier: " + var);
    }
    
}
