package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.JASModInteger;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;

import edu.jas.arith.BigInteger;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisorAbstract;

/**
 * <h2>PolynomialGCD</h2>
 * <p>
 * <pre>
 * <code>PolynomialGCD(p, q)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the GCD ('greatest common divisor') of the polynomials <code>p</code>
 * and <code>q</code>.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>PolynomialGCD(p, q, Modulus -&gt; prime)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * returns the GCD ('greatest common divisor') of the polynomials <code>p</code>
 * and <code>q</code> modulus the <code>prime</code> integer.
 * </p>
 * </blockquote>
 */
public class PolynomialGCD extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3);

        VariablesSet eVar = new VariablesSet();
        eVar.addVarList(ast, 1);

        IExpr expr = F.evalExpandAll(ast.arg1(), engine);
        if (ast.size() > 3 && ast.last().isRuleAST()) {
            return gcdWithOption(ast, expr, eVar, engine);
        }
        try {
            ASTRange r = new ASTRange(eVar.getVarList(), 1);
            JASConvert<BigInteger> jas = new JASConvert<BigInteger>(r, BigInteger.ZERO);
            GenPolynomial<BigInteger> poly = jas.expr2JAS(expr, false);
            GenPolynomial<BigInteger> temp;
            GreatestCommonDivisorAbstract<BigInteger> factory = GCDFactory.getImplementation(BigInteger.ZERO);
            for (int i = 2; i < ast.size(); i++) {
                expr = F.evalExpandAll(ast.get(i), engine);
                temp = jas.expr2JAS(expr, false);
                poly = factory.gcd(poly, temp);
            }
            return jas.integerPoly2Expr(poly.monic());
        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return F.NIL;
    }

    private IExpr gcdWithOption(final IAST ast, IExpr expr, VariablesSet eVar, final EvalEngine engine) {
        final Options options = new Options(ast.topHead(), ast, ast.size() - 1, engine);
        IExpr option = options.getOption("Modulus");
        if (option.isSignedNumber()) {
            return modulusGCD(ast, expr, eVar, option);
        }
        return F.NIL;
    }

    private IExpr modulusGCD(final IAST ast, IExpr expr, VariablesSet eVar, IExpr option) {
        try {
            // found "Modulus" option => use ModIntegerRing
            // ASTRange r = new ASTRange(eVar.getVarList(), 1);
            // ModIntegerRing modIntegerRing =
            // JASConvert.option2ModIntegerRing((ISignedNumber) option);
            // JASConvert<ModInteger> jas = new
            // JASConvert<ModInteger>(r.toList(), modIntegerRing);
            ModLongRing modIntegerRing = JASModInteger.option2ModLongRing((ISignedNumber) option);
            JASModInteger jas = new JASModInteger(eVar.getArrayList(), modIntegerRing);
            GenPolynomial<ModLong> poly = jas.expr2JAS(expr);
            GenPolynomial<ModLong> temp;
            GreatestCommonDivisorAbstract<ModLong> factory = GCDFactory.getImplementation(modIntegerRing);

            for (int i = 2; i < ast.size() - 1; i++) {
                eVar = new VariablesSet(ast.get(i));
                if (!eVar.isSize(1)) {
                    // gcd only possible for univariate polynomials
                    return F.NIL;
                }
                expr = F.evalExpandAll(ast.get(i));
                temp = jas.expr2JAS(expr);
                poly = factory.gcd(poly, temp);
            }
            return Algebra.factorModulus(jas, modIntegerRing, poly, false);
        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDALL);
    }
}
