package Main;

import ast.*;
import ntree.*;

import java.util.Stack;

public class OperatorNormalizer implements IASTExprOperatorVisitor
{
	private Stack<INExpr> oNormStack;

	OperatorNormalizer()
	{
		this.oNormStack = new Stack<>();
	}

	INExpr normalize(IASTExprOperator node, INExpr nLeft, INExpr nRight)
	{
		oNormStack.push(nRight);
		oNormStack.push(nLeft);
		node.visit(this);
		return oNormStack.pop();
	}

	private void ret(INExpr e)
	{
		oNormStack.push(e);
	}

	private INExpr left()
	{
		return oNormStack.pop();
	}

	private INExpr right()
	{
		return oNormStack.pop();
	}


	@Override public void visit(LessThan a)
	{
		ret(new NLessThan(left(), right()));
	}

	@Override public void visit(GreaterThan a)
	{
		ret(new NGreaterThan(left(), right()));
	}

	@Override public void visit(Mult a)
	{
		ret(new NMult(left(), right()));
	}

	@Override public void visit(Div a)
	{
		ret(new NDiv(left(), right()));
	}

	@Override public void visit(Plus a)
	{
		ret(new NPlus(left(), right()));
	}

	@Override public void visit(Sub a)
	{
		ret(new NSub(left(), right()));
	}
}
