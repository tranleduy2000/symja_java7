package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import edu.jas.arith.BigInteger;
import edu.jas.arith.BigRational;
import edu.jas.poly.GenPolynomial;

/**
 * <h2>FactorTerms</h2>
 * <p>
 * <pre>
 * <code>FactorTerms(poly)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * pulls out any overall numerical factor in <code>poly</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; FactorTerms(3+3/4*x^3+12/17*x^2, x)
 * 3/68*(17*x^3+16*x^2+68)
 * </code>
 * </pre>
 */
public class FactorTerms extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        IAST variableList = F.NIL;
        if (ast.isAST2()) {
            if (ast.arg2().isSymbol()) {
                ISymbol variable = (ISymbol) ast.arg2();
                variableList = F.List(variable);
            } else if (ast.arg2().isList()) {
                variableList = (IAST) ast.arg2();
            } else {
                return F.NIL;
            }
        } else {
            if (ast.isAST1()) {
                VariablesSet eVar;
                eVar = new VariablesSet(ast.arg1());
                if (!eVar.isSize(1)) {
                    // only possible for univariate polynomials
                    return F.NIL;
                }
                variableList = eVar.getVarList();
            }
        }
        if (!variableList.isPresent() || variableList.size() != 2) {
            // FactorTerms only possible for univariate polynomials
            return F.NIL;
        }
        ASTRange r = new ASTRange(variableList, 1);
        IExpr expr = F.evalExpandAll(ast.arg1(), engine);
        try {

            JASConvert<BigRational> jas = new JASConvert<BigRational>(r, BigRational.ZERO);
            GenPolynomial<BigRational> poly = jas.expr2JAS(expr, false);
            Object[] objects = jas.factorTerms(poly);
            java.math.BigInteger gcd = (java.math.BigInteger) objects[0];
            java.math.BigInteger lcm = (java.math.BigInteger) objects[1];
            if (lcm.equals(java.math.BigInteger.ZERO)) {
                // no changes
                return expr;
            }
            GenPolynomial<BigInteger> iPoly = (GenPolynomial<edu.jas.arith.BigInteger>) objects[2];
            IAST result = F.Times();
            result.append(F.fraction(gcd, lcm));
            result.append(jas.integerPoly2Expr(iPoly));
            return result;
        } catch (JASConversionException e) {
            // if (Config.DEBUG) {
            // e.printStackTrace();
            // }
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.FLAT);
    }
}