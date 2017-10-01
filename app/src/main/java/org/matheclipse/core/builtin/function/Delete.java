package org.matheclipse.core.builtin.function;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * Delete(list,n) - delete the n-th argument from the list. Negative n counts from the end.
 * 
 */
public class Delete extends AbstractCoreFunctionEvaluator {

	public Delete() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 3);
		final IExpr arg1 = engine.evaluate(ast.arg1());
		final IExpr arg2 = engine.evaluate(ast.arg2());
		if (arg1.isAST() && arg2.isInteger()) {
			final IAST list1 = (IAST) arg1;

			try {
				int indx = Validate.checkIntType(ast, 2, Integer.MIN_VALUE);
				if (indx < 0) {
					// negative n counts from the end
					indx = list1.size() + indx;
				}
				return list1.removeAtClone(indx);
			} catch (final IndexOutOfBoundsException e) {
				if (Config.DEBUG) {
					e.printStackTrace();
				}
				return F.NIL;
			}

		}
		return F.NIL;
	}

}