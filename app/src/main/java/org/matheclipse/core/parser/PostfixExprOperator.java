package org.matheclipse.core.parser;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

class PostfixExprOperator extends AbstractExprOperator {

	public PostfixExprOperator(final String oper, final String functionName, final int precedence) {
		super(oper, functionName, precedence);
	}

	public IExpr createFunction(final IExprParserFactory factory, final IExpr argument) {
		return F.$(F.$s(getFunctionName()), argument);
	}
}