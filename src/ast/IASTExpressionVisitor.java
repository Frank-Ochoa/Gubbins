package ast;

public interface IASTExpressionVisitor
{

    void visit(Bool a);

    void visit(Dble a);

    void visit(Int a);

    void visit(Array a);

    void visit(Identifier a);

    void visit (ArrayIndex a);

    void visit (Function a);

    void visit (Mult a);

    void visit (Plus a);

    void visit (Sub a);

    void visit (Div a);

    void visit (LessThan a);

    void visit (GreaterThan a);

    void visit (Record a);

    void visit (RecordAccess a);

}
