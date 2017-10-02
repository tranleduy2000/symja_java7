package org.matheclipse.core.builtin.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

/**
 * <pre>
 * Head(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the head of the expression or atom <code>expr</code>.
 * </p>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt; Head(a * b)
 * Times
 * &gt; Head(6)
 * Integer
 * &gt; Head(x)
 * Symbol
 * </pre>
 * <p>
 * </blockquote>
 */
public class Head extends AbstractCoreFunctionEvaluator {

    public Head() {
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            return engine.evaluate(ast.arg1()).head();
        }
        return F.SymbolHead;
    }

}
