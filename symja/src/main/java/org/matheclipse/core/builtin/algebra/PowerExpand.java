package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.expression.F.Assumptions;


/**
 * <h2>PowerExpand</h2>
 * <p>
 * <pre>
 * <code>PowerExpand(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * expands out powers of the form <code>(x^y)^z</code> and <code>(x*y)^z</code>
 * in <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; PowerExpand((a ^ b) ^ c)
 * a^(b*c)
 *
 * &gt;&gt; PowerExpand((a * b) ^ c)
 * a^c*b^c
 * </code>
 * </pre>
 * <p>
 * <code>PowerExpand</code> is not correct without certain assumptions:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; PowerExpand((x ^ 2) ^ (1/2))
 * x
 * </code>
 * </pre>
 */
public class PowerExpand extends AbstractFunctionEvaluator {

    public static IExpr powerExpand(final IAST ast, boolean assumptions) {
        return ast.accept(new PowerExpandVisitor(assumptions)).orElse(ast);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);
        if (ast.arg1().isAST()) {
            boolean assumptions = false;
            if (ast.isAST2()) {
                final Options options = new Options(ast.topHead(), ast, ast.size() - 1, engine);
                IExpr option = options.getOption(Assumptions);
                if (option.isTrue()) {
                    // found "Assumptions -> True"
                    assumptions = true;
                }
            }

            return powerExpand((IAST) ast.arg1(), assumptions);

        }
        return ast.arg1();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

}