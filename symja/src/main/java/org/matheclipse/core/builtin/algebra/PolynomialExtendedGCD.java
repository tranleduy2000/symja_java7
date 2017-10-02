package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.JASIExpr;
import org.matheclipse.core.convert.JASModInteger;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.ExprRingFactory;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.List;

import edu.jas.arith.BigRational;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.GenPolynomial;

/**
 * <h2>PolynomialExtendedGCD</h2>
 * <p>
 * <pre>
 * <code>PolynomialExtendedGCD(p, q, x)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the extended GCD ('greatest common divisor') of the univariate
 * polynomials <code>p</code> and <code>q</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>PolynomialExtendedGCD(p, q, x, Modulus -&gt; prime)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the extended GCD ('greatest common divisor') of the univariate
 * polynomials <code>p</code> and <code>q</code> modulus the <code>prime</code>
 * integer.
 * </p>
 * </blockquote>
 * <p>
 * See:
 * </p>
 * <ul>
 * <li><a href=
 * "https://en.wikipedia.org/wiki/Extended_Euclidean_algorithm#Polynomial_extended_Euclidean_algorithm">Wikipedia:
 * Polynomial extended Euclidean algorithm</a></li>
 * </ul>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; PolynomialExtendedGCD(x^8 + x^4 + x^3 + x + 1, x^6 + x^4 + x + 1, x, Modulus-&gt;2)
 * {1,{1+x^2+x^3+x^4+x^5,x+x^3+x^6+x^7}}
 * </code>
 * </pre>
 */
public class PolynomialExtendedGCD extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 4, 5);

        ISymbol x = Validate.checkSymbolType(ast, 3);
        IExpr expr1 = F.evalExpandAll(ast.arg1(), engine);
        IExpr expr2 = F.evalExpandAll(ast.arg2(), engine);
        VariablesSet eVar = new VariablesSet();
        eVar.add(x);

        ASTRange r = new ASTRange(eVar.getVarList(), 1);
        if (ast.size() == 5) {
            List<IExpr> varList = r;
            final Options options = new Options(ast.topHead(), ast, 4, engine);
            IExpr option = options.getOption("Modulus");
            if (option.isSignedNumber()) {
                try {
                    // found "Modulus" option => use ModIntegerRing
                    ModLongRing modIntegerRing = JASModInteger.option2ModLongRing((ISignedNumber) option);
                    JASModInteger jas = new JASModInteger(varList, modIntegerRing);
                    GenPolynomial<ModLong> poly1 = jas.expr2JAS(expr1);
                    GenPolynomial<ModLong> poly2 = jas.expr2JAS(expr2);
                    GenPolynomial<ModLong>[] result = poly1.egcd(poly2);
                    IAST list = F.List();
                    list.append(jas.modLongPoly2Expr(result[0]));
                    IAST subList = F.List();
                    subList.append(jas.modLongPoly2Expr(result[1]));
                    subList.append(jas.modLongPoly2Expr(result[2]));
                    list.append(subList);
                    return list;
                } catch (JASConversionException e) {
                    if (Config.DEBUG) {
                        e.printStackTrace();
                    }
                }
                return F.NIL;
            }
        }

        try {
            JASConvert<BigRational> jas = new JASConvert<BigRational>(r, BigRational.ZERO);
            GenPolynomial<BigRational> poly1 = jas.expr2JAS(expr1, false);
            GenPolynomial<BigRational> poly2 = jas.expr2JAS(expr2, false);
            GenPolynomial<BigRational>[] result = poly1.egcd(poly2);
            IAST list = F.List();
            list.append(jas.rationalPoly2Expr(result[0]));
            IAST subList = F.List();
            subList.append(jas.rationalPoly2Expr(result[1]));
            subList.append(jas.rationalPoly2Expr(result[2]));
            list.append(subList);
            return list;
        } catch (JASConversionException e0) {
            try {
                JASIExpr jas = new JASIExpr(r, ExprRingFactory.CONST);
                GenPolynomial<IExpr> poly1 = jas.expr2IExprJAS(expr1);
                GenPolynomial<IExpr> poly2 = jas.expr2IExprJAS(expr2);
                GenPolynomial<IExpr>[] result = poly1.egcd(poly2);
                IAST list = F.List();
                list.append(jas.exprPoly2Expr(result[0], x));
                IAST subList = F.List();
                subList.append(engine.evaluate(F.Together(jas.exprPoly2Expr(result[1], x))));
                subList.append(engine.evaluate(F.Together(jas.exprPoly2Expr(result[2], x))));
                list.append(subList);
                return list;
            } catch (JASConversionException e) {
//                if (Config.DEBUG) {
                e.printStackTrace();
//                }
            }
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }
}