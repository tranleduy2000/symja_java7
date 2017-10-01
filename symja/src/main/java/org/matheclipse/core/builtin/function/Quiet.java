package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * The call <code>Quiet( expr )</code> evaluates <code>expr</code> in &quot;quiet&quot; mode (i.e. no warning messages are shown
 * during evaluation).
 * 
 */
public class Quiet extends AbstractCoreFunctionEvaluator {

	public Quiet() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);
		boolean quietMode = engine.isQuietMode();
		try {
			engine.setQuietMode(true);
			return engine.evaluate(ast.arg1());
		} finally {
			engine.setQuietMode(quietMode);
		}
	}

	@Override
	public void setUp(ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.HOLDALL);
	}
}
