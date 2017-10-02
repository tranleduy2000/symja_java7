package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>
 * IntegerExponent(n, b)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives the highest exponent of <code>b</code> that divides <code>n</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; IntegerExponent(16, 2)
 * 4
 * &gt;&gt; IntegerExponent(-510000)
 * 4
 * &gt;&gt; IntegerExponent(10, b)
 * IntegerExponent(10, b)
 * </pre>
 */
public class IntegerExponent extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);
        IInteger base = F.C10;
        if (ast.isAST2()) {
            IExpr arg2 = ast.arg2();
            if (arg2.isInteger() && ((IInteger) arg2).compareInt(1) > 0) {
                base = (IInteger) arg2;
            } else {
                return F.NIL;
            }
        }
        IExpr arg1 = ast.arg1();
        if (arg1.isInteger()) {
            return ((IInteger) arg1).exponent(base);
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}