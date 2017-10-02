package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <h2>Variables</h2>
 * <p>
 * <pre>
 * <code>Variables[expr]
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * gives a list of the variables that appear in the polynomial
 * <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; Variables(a x^2 + b x + c)
 * {a,b,c,x}
 *
 * &gt;&gt; Variables({a + b x, c y^2 + x/2})
 * {a,b,c,x,y}
 *
 * &gt;&gt; Variables(x + Sin(y))
 * {x,Sin(y)}
 * </code>
 * </pre>
 */
public class Variables extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        // VariablesSet eVar = new VariablesSet(ast.arg1());
        return VariablesSet.getVariables(ast.arg1());
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
    }

}
