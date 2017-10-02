package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.JASConvert;
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

import java.util.List;
import java.util.SortedMap;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.poly.GenPolynomial;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;

import static org.matheclipse.core.builtin.algebra.Algebra.factorComplex;
import static org.matheclipse.core.builtin.algebra.Algebra.factorModulus;

/**
 * <h2>Factor</h2>
 * <p>
 * <pre>
 * <code>Factor(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * factors the polynomial expression <code>expr</code>
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; Factor(1+2*x+x^2, x)
 * (1+x)^2
 * </code>
 * </pre>
 * <p>
 * <pre>
 * <code>```
 * &gt;&gt; Factor(x^4-1, GaussianIntegers-&gt;True)
 * (x-1)*(x+1)*(x-I)*(x+I)
 * </code>
 * </pre>
 */
public class Factor extends AbstractFunctionEvaluator {

    public static IExpr factor(IExpr expr, List<IExpr> varList, boolean factorSquareFree)
            throws JASConversionException {
        JASConvert<BigRational> jas = new JASConvert<BigRational>(varList, BigRational.ZERO);
        GenPolynomial<BigRational> polyRat = jas.expr2JAS(expr, false);
        if (polyRat.length() <= 1) {
            return expr;
        }

        Object[] objects = jas.factorTerms(polyRat);
        GenPolynomial<BigInteger> poly = (GenPolynomial<edu.jas.arith.BigInteger>) objects[2];
        FactorAbstract<BigInteger> factorAbstract = FactorFactory
                .getImplementation(edu.jas.arith.BigInteger.ONE);
        SortedMap<GenPolynomial<edu.jas.arith.BigInteger>, Long> map;
        if (factorSquareFree) {
            map = factorAbstract.squarefreeFactors(poly);// factors(poly);
        } else {
            map = factorAbstract.factors(poly);
        }
        IAST result = F.Times();
        java.math.BigInteger gcd = (java.math.BigInteger) objects[0];
        java.math.BigInteger lcm = (java.math.BigInteger) objects[1];
        if (!gcd.equals(java.math.BigInteger.ONE) || !lcm.equals(java.math.BigInteger.ONE)) {
            result.append(F.fraction(gcd, lcm));
        }
        for (SortedMap.Entry<GenPolynomial<edu.jas.arith.BigInteger>, Long> entry : map.entrySet()) {
            if (entry.getKey().isONE() && entry.getValue().equals(1L)) {
                continue;
            }
            if (entry.getValue() == 1L) {
                result.append(jas.integerPoly2Expr(entry.getKey()));
            } else {
                result.append(F.Power(jas.integerPoly2Expr(entry.getKey()), F.integer(entry.getValue())));
            }
        }
        return result.getOneIdentity(F.C0);
    }

    public static IExpr factorList(IExpr expr, List<IExpr> varList, boolean factorSquareFree)
            throws JASConversionException {
        JASConvert<BigRational> jas = new JASConvert<BigRational>(varList, BigRational.ZERO);
        GenPolynomial<BigRational> polyRat = jas.expr2JAS(expr, false);
        Object[] objects = jas.factorTerms(polyRat);
        java.math.BigInteger gcd = (java.math.BigInteger) objects[0];
        java.math.BigInteger lcm = (java.math.BigInteger) objects[1];
        GenPolynomial<edu.jas.arith.BigInteger> poly = (GenPolynomial<edu.jas.arith.BigInteger>) objects[2];
        FactorAbstract<edu.jas.arith.BigInteger> factorAbstract = FactorFactory
                .getImplementation(edu.jas.arith.BigInteger.ONE);
        SortedMap<GenPolynomial<edu.jas.arith.BigInteger>, Long> map;
        if (factorSquareFree) {
            map = factorAbstract.squarefreeFactors(poly);// factors(poly);
        } else {
            map = factorAbstract.factors(poly);
        }
        IAST result = F.ListAlloc(map.size() + 1);
        if (!gcd.equals(java.math.BigInteger.ONE) || !lcm.equals(java.math.BigInteger.ONE)) {
            result.append(F.List(F.fraction(gcd, lcm), F.C1));
        }
        for (SortedMap.Entry<GenPolynomial<edu.jas.arith.BigInteger>, Long> entry : map.entrySet()) {
            if (entry.getKey().isONE() && entry.getValue().equals(1L)) {
                continue;
            }
            result.append(F.List(jas.integerPoly2Expr(entry.getKey()), F.integer(entry.getValue())));
        }
        return result;
    }

    /**
     * Factor the <code>expr</code> with the option given in <code>ast</code>.
     *
     * @param ast
     * @param expr
     * @param varList
     * @param factorSquareFree
     * @return
     * @throws JASConversionException
     */
    public static IExpr factorWithOption(final IAST ast, IExpr expr, List<IExpr> varList, boolean factorSquareFree,
                                         final EvalEngine engine) throws JASConversionException {
        final Options options = new Options(ast.topHead(), ast, 2, engine);
        IExpr option = options.getOption("Modulus");
        if (option.isSignedNumber()) {
            return factorModulus(expr, varList, factorSquareFree, option);
        }
        option = options.getOption("GaussianIntegers");
        if (option.isTrue()) {
            return factorComplex(expr, varList, F.Times, false, false);
        }
        option = options.getOption("Extension");
        if (option.isImaginaryUnit()) {
            return factorComplex(expr, varList, F.Times, false, false);
        }
        return F.NIL; // no evaluation
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        VariablesSet eVar = new VariablesSet(ast.arg1());

        if (ast.arg1().isList()) {
            IAST list = (IAST) ast.arg1();
            return list.mapThread(F.ListAlloc(list.size()), ast, 1);
        }
        IExpr expr = ast.arg1();
        if (ast.isAST1()) {
            expr = engine.evaluate(F.Together(ast.arg1()));
            if (expr.isAST()) {
                IExpr[] parts = Algebra.getNumeratorDenominator((IAST) expr, engine);
                if (!parts[1].isOne()) {
                    return F.Divide(F.Factor(parts[0]), F.Factor(parts[1]));
                }
            }
        }

        ASTRange r = new ASTRange(eVar.getVarList(), 1);
        try {
            List<IExpr> varList = r;

            if (ast.isAST2()) {
                return factorWithOption(ast, expr, varList, false, engine);
            }
            return factor(expr, varList, false);

        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return expr;
    }

}