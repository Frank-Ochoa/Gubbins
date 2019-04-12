package Main;


import ast.*;

import java.util.HashMap;
import java.util.Map;

public class CheckUses implements IASTExpressionVisitor
{
    private Map<String, Boolean> usageEnvironment;

    public CheckUses()
    {
        this.usageEnvironment = new HashMap<>();
    }

    public Map<String, Boolean> getUsageEnvironment()
    {
        return usageEnvironment;
    }

    public void checkUses(IASTExpr node)
    {
        node.visit(this);
    }

    @Override
    public void visit(Bool a)
    {
    }

    @Override
    public void visit(Dble a)
    {
    }

    @Override
    public void visit(Identifier a)
    {
        usageEnvironment.put(a.getName(), true);
    }

	@Override public void visit(ArrayIndex a)
	{

	}

	@Override
    public void visit(Int a)
    {
    }

	@Override public void visit(Array a)
	{

	}

	@Override
    public void visit(Mult a)
    {
        checkUses(a.getLeft());
        checkUses(a.getRight());
    }

    @Override
    public void visit(Plus a)
    {
        checkUses(a.getLeft());
        checkUses(a.getRight());
    }

	@Override public void visit(Sub a)
	{
		checkUses(a.getLeft());
		checkUses(a.getRight());
	}

	@Override public void visit(Div a)
	{
		checkUses(a.getLeft());
		checkUses(a.getRight());
	}

	@Override public void visit(LessThan a)
	{
		checkUses(a.getLeft());
		checkUses(a.getRight());
	}

	@Override public void visit(GreaterThan a)
	{

	}
}
