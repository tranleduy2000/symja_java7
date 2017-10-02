package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.JASIExpr;
import org.matheclipse.core.convert.JASModInteger;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.ExprRingFactory;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;

import edu.jas.arith.BigRational;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.GenPolynomial;

/**
 * <h2>PolynomialQuotientRemainder</h2>
 * <p>
 * <pre>
 * <code>PolynomialQuotientRemainder(p, q, x)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns a list with the polynomial quotient and remainder of the polynomials
 * <code>p</code> and <code>q</code> for the variable <code>x</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>PolynomialQuotientRemainder(p, q, x, Modulus -&gt; prime)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns list with the polynomial quotient and remainder of the polynomials
 * <code>p</code> and <code>q</code> for the variable <code>x</code> modulus the
 * <code>prime</code> integer.
 * </p>
 * </blockquote>
 */
public class PolynomialQuotientRemainder extends AbstractFunctionEvaluator {

    public static IExpr[] quotientRemainder(final IExpr arg1, IExpr arg2, ISymbol variable) {

        try {
            JASConvert<BigRational> jas = new JASConvert<BigRational>(variable, BigRational.ZERO);
            GenPolynomial<BigRational> poly1 = jas.expr2JAS(arg1, false);
            GenPolynomial<BigRational> poly2 = jas.expr2JAS(arg2, false);
            GenPolynomial<BigRational>[] divRem = poly1.quotientRemainder(poly2);
            IExpr[] result = new IExpr[2];
            result[0] = jas.rationalPoly2Expr(divRem[0]);
            result[1] = jas.rationalPoly2Expr(divRem[1]);
            return result;
        } catch (JASConversionException e1) {
            try {
                JASIExpr jas = new JASIExpr(variable, ExprRingFactory.CONST);
                GenPolynomial<IExpr> poly1 = jas.expr2IExprJAS(arg1);
                GenPolynomial<IExpr> poly2 = jas.expr2IExprJAS(arg2);
                GenPolynomial<IExpr>[] divRem = poly1.quotientRemainder(poly2);
                IExpr[] result = new IExpr[2];
                result[0] = jas.exprPoly2Expr(divRem[0], variable);
                result[1] = jas.exprPoly2Expr(divRem[1], variable);
                return result;
            } catch (JASConversionException e) {
                if (Config.DEBUG) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

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
                return F.List(result[0], result[1]);
            }
            return F.NIL;
        }
        IExpr[] result = quotientRemainder(arg1, arg2, variable);
        if (result == null) {
            return F.NIL;
        }
        return F.List(result[0], result[1]);
    }

    public IExpr[] quotientRemainderModInteger(IExpr arg1, IExpr arg2, ISymbol variable, IExpr option) {
        try {
            // found "Modulus" option => use ModIntegerRing
            ModLongRing modIntegerRing = JASModInteger.option2ModLongRing((ISignedNumber) option);
            JASModInteger jas = new JASModInteger(variable, modIntegerRing);
            GenPolynomial<ModLong> poly1 = jas.expr2JAS(arg1);
            GenPolynomial<ModLong> poly2 = jas.expr2JAS(arg2);
            GenPolynomial<ModLong>[] divRem = poly1.quotientRemainder(poly2);
            IExpr[] result = new IExpr[2];
            result[0] = jas.modLongPoly2Expr(divRem[0]);
            result[1] = jas.modLongPoly2Expr(divRem[1]);
            return result;
        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

}