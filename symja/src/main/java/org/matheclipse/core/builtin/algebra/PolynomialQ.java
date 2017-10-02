package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.function.BiPredicate;

import static org.matheclipse.core.expression.F.List;

/**
 * <h2>PolynomialQ</h2>
 * <p>
 * <pre>
 * <code>PolynomialQ(p, x)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * return <code>True</code> if <code>p</code> is a polynomial for the variable
 * <code>x</code>. Return <code>False</code> in all other cases.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>PolynomialQ(p, {x, y, ...})
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * return <code>True</code> if <code>p</code> is a polynomial for the variables
 * <code>x, y, ...</code> defined in the list. Return <code>False</code> in all
 * other cases.
 * </p>
 * </blockquote>
 */
public class PolynomialQ extends AbstractFunctionEvaluator implements BiPredicate<IExpr, IExpr> {

    /**
     * Returns <code>True</code> if the given expression is a polynoomial object;
     * <code>False</code> otherwise
     */
    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 3);

        IAST variables;
        if (ast.arg2().isList()) {
            variables = (IAST) ast.arg2();
        } else {
            variables = List(ast.arg2());
        }
        return F.bool(ast.arg1().isPolynomial(variables));

    }

    /**
     *
     * @param polnomialExpr
     * @param variables
     * @param numericFunction
     * @return
     * @deprecated use
     *             <code>ExprPolynomialRing ring = new ExprPolynomialRing(variables); ExprPolynomial poly = ring.create(polnomialExpr);</code>
     *             if possible.
     */
    // private static GenPolynomial<IExpr> polynomial(final IExpr polnomialExpr,
    // final IAST variables,
    // boolean numericFunction) {
    // IExpr expr = F.evalExpandAll(polnomialExpr, engine);
    // int termOrder = ExprTermOrder.INVLEX;
    // ExprPolynomialRing ring = new ExprPolynomialRing(ExprRingFactory.CONST,
    // variables, variables.size() - 1,
    // new ExprTermOrder(termOrder), true);
    // try {
    // ExprPolynomial poly = ring.create(expr);
    // ASTRange r = new ASTRange(variables, 1);
    // JASIExpr jas = new JASIExpr(r, numericFunction);
    // return jas.expr2IExprJAS(poly);
    // } catch (RuntimeException ex) {
    //
    // }
    // return null;
    // }

    /**
     * @param polnomialExpr
     * @param symbol
     * @param numericFunction
     * @return
     * @deprecated use
     * <code>ExprPolynomialRing ring = new ExprPolynomialRing(symbol); ExprPolynomial poly = ring.create(polnomialExpr);</code>
     * if possible
     */
    // private static GenPolynomial<IExpr> polynomial(final IExpr polnomialExpr,
    // final ISymbol symbol,
    // boolean numericFunction) {
    // return polynomial(polnomialExpr, List(symbol), numericFunction);
    // }
    @Deprecated
    @Override
    public void setUp(final ISymbol newSymbol) {
    }

    @Override
    public boolean test(final IExpr firstArg, final IExpr secondArg) {
        IAST list;
        if (secondArg.isList()) {
            list = (IAST) secondArg;
        } else {
            list = List(secondArg);
        }
        return firstArg.isPolynomial(list);
    }
}
