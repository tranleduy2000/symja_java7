package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import static org.matheclipse.core.builtin.algebra.Algebra.expandAll;

/**
 * <h2>ExpandAll</h2>
 * <p>
 * <pre>
 * <code>ExpandAll(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * expands out all positive integer powers and products of sums in
 * <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; ExpandAll((a + b) ^ 2 / (c + d)^2)
 * (a^2+2*a*b+b^2)/(c^2+2*c*d+d^2)
 * </code>
 * </pre>
 * <p>
 * <code>ExpandAll</code> descends into sub expressions
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; ExpandAll((a + Sin(x*(1 + y)))^2)
 * a^2+Sin(x+x*y)^2+2*a*Sin(x+x*y)
 * </code>
 * </pre>
 * <p>
 * <code>ExpandAll</code> also expands heads
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; ExpandAll(((1 + x)(1 + y))[x])
 * (1+x+y+x*y)[x]
 * </code>
 * </pre>
 */
public class ExpandAll extends AbstractFunctionEvaluator {

    public static IExpr setAllExpanded(IExpr expr, boolean expandNegativePowers, boolean distributePlus) {
        if (expr != null && expandNegativePowers && !distributePlus && expr.isAST()) {
            ((IAST) expr).addEvalFlags(IAST.IS_ALL_EXPANDED);
        }
        return expr;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        IExpr arg1 = ast.arg1();
        IExpr patt = null;
        if (ast.size() > 2) {
            patt = ast.arg2();
        }
        if (arg1.isAST()) {
            return expandAll((IAST) arg1, patt, true, false, engine).orElse(arg1);
        }
        return arg1;
    }

}