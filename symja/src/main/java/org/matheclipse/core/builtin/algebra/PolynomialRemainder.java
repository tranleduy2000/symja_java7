package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <h2>PolynomialQuotient</h2>
 * <p>
 * <pre>
 * <code>PolynomialQuotient(p, q, x)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the polynomial remainder of the polynomials <code>p</code> and
 * <code>q</code> for the variable <code>x</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>PolynomialQuotient(p, q, x, Modulus -&gt; prime)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the polynomial remainder of the polynomials <code>p</code> and
 * <code>q</code> for the variable <code>x</code> modulus the <code>prime</code>
 * integer.
 * </p>
 * </blockquote>
 */
public class PolynomialRemainder extends PolynomialQuotientRemainder {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 4, 5);
        ISymbol variable = Validate.checkSymbolType(ast, 3);
        IExpr arg1 = F.evalExpandAll(ast.arg1(), engine);
        IExpr arg2 = F.evalExpandAll(ast.arg2(), engine);

        if (ast.size() == 5) {
            final Options options = new Options(ast.topHead(), ast, 4, engine);
            IExpr option = options.getOption("Modulus");
            if (option.isSignedNumber()) {
                IExpr[] result = quotientRemainderModInteger(arg1, arg2, variable, option);
                if (result == null) {
                    return F.NIL;
                }
                return result[1];
            }
            return F.NIL;
        }
        IExpr[] result = quotientRemainder(arg1, arg2, variable);
        if (result == null) {
            return F.NIL;
        }
        return result[1];
    }

}
