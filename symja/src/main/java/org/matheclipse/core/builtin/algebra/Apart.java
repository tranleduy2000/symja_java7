package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.structure.Structure;
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
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.polynomials.PartialFractionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import edu.jas.arith.BigInteger;
import edu.jas.poly.GenPolynomial;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;

import static org.matheclipse.core.builtin.algebra.Algebra.fractionalParts;
import static org.matheclipse.core.builtin.algebra.Algebra.partialFractionDecompositionRational;


/**
 * <h2>Apart</h2>
 * <p>
 * <pre>
 * <code>Apart(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * rewrites <code>expr</code> as a sum of individual fractions.
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * <code>Apart(expr, var)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * treats <code>var</code> as main variable.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; Apart((x-1)/(x^2+x))
 * 2/(x+1)-1/x
 *
 * &gt;&gt; Apart(1 / (x^2 + 5x + 6))
 * 1/(2+x)+1/(-3-x)
 * </code>
 * </pre>
 * <p>
 * When several variables are involved, the results can be different depending
 * on the main variable:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Apart(1 / (x^2 - y^2), x)
 * -1 / (2 y (x + y)) + 1 / (2 y (x - y))
 * &gt;&gt; Apart(1 / (x^2 - y^2), y)
 * 1 / (2 x (x + y)) + 1 / (2 x (x - y))
 * </code>
 * </pre>
 * <p>
 * 'Apart' is 'Listable':
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Apart({1 / (x^2 + 5x + 6)})
 * {1/(2+x)+1/(-3-x)}
 * </code>
 * </pre>
 * <p>
 * But it does not touch other expressions:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Sin(1 / (x ^ 2 - y ^ 2)) // Apart
 * Sin(1/(x^2-y^2))
 * </code>
 * </pre>
 */
public class Apart extends AbstractFunctionEvaluator {

    /**
     * Return the denominator for the given <code>Power[...]</code> AST, by
     * separating positive and negative powers.
     *
     * @param powerAST a power expression (a^b)
     * @param trig     TODO
     * @return the numerator and denominator expression
     */
    public static IExpr[] fractionalPartsPower(final IAST powerAST, boolean trig) {
        IExpr[] parts = new IExpr[2];
        parts[0] = F.C1;

        IExpr arg1 = powerAST.arg1();
        IExpr exponent = powerAST.arg2();
        if (exponent.isSignedNumber()) {
            ISignedNumber sn = (ISignedNumber) exponent;
            if (sn.isMinusOne()) {
                parts[1] = arg1;
                return parts;
            } else if (sn.isNegative()) {
                parts[1] = F.Power(arg1, sn.negate());
                return parts;
            } else {
                if (sn.isInteger() && arg1.isAST()) {
                    // positive integer
                    IAST function = (IAST) arg1;
                    // if (function.isTimes()) {
                    // IExpr[] partsArg1 = fractionalPartsTimesPower(function, true, true, trig,
                    // true);
                    // if (partsArg1 != null) {
                    // parts[0] = F.Power(partsArg1[0], sn);
                    // parts[1] = F.Power(partsArg1[1], sn);
                    // return parts;
                    // }
                    // }
                    IExpr numerForm = Numerator.getTrigForm(function, trig);
                    if (numerForm.isPresent()) {
                        IExpr denomForm = Denominator.getTrigForm(function, trig);
                        if (denomForm.isPresent()) {
                            parts[0] = F.Power(numerForm, sn);
                            parts[1] = F.Power(denomForm, sn);
                            return parts;
                        }
                    }
                }
            }
        }
        IExpr negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(exponent);
        if (negExpr.isPresent()) {
            parts[1] = F.Power(arg1, negExpr);
            return parts;
        }
        return null;
    }

    /**
     * Returns an AST with head <code>Plus</code>, which contains the partial
     * fraction decomposition of the numerator and denominator parts.
     *
     * @param parts
     * @param variableList
     * @return <code>null</code> if the partial fraction decomposition wasn't
     * constructed
     * @deprecated untested at the moment
     */
    @Deprecated
    private static IAST partialFractionDecompositionInteger(IExpr[] parts, IAST variableList) {
        try {
            IExpr exprNumerator = F.evalExpandAll(parts[0]);
            IExpr exprDenominator = F.evalExpandAll(parts[1]);
            ASTRange r = new ASTRange(variableList, 1);
            List<IExpr> varList = r;

            String[] varListStr = new String[1];
            varListStr[0] = variableList.arg1().toString();
            JASConvert<BigInteger> jas = new JASConvert<BigInteger>(varList, BigInteger.ZERO);
            GenPolynomial<BigInteger> numerator = jas.expr2JAS(exprNumerator, false);
            GenPolynomial<BigInteger> denominator = jas.expr2JAS(exprDenominator, false);

            // get factors
            FactorAbstract<BigInteger> factorAbstract = FactorFactory.getImplementation(BigInteger.ZERO);
            SortedMap<GenPolynomial<BigInteger>, Long> sfactors = factorAbstract.baseFactors(denominator);

            List<GenPolynomial<BigInteger>> D = new ArrayList<GenPolynomial<BigInteger>>(sfactors.keySet());

            SquarefreeAbstract<BigInteger> sqf = SquarefreeFactory.getImplementation(BigInteger.ZERO);
            List<List<GenPolynomial<BigInteger>>> Ai = sqf.basePartialFraction(numerator, sfactors);
            // returns [ [Ai0, Ai1,..., Aie_i], i=0,...,k ] with A/prod(D) =
            // A0 + sum( sum ( Aij/di^j ) ) with deg(Aij) < deg(di).

            if (Ai.size() > 0) {
                IAST result = F.Plus();
                IExpr temp;
                if (!Ai.get(0).get(0).isZERO()) {
                    temp = F.eval(jas.integerPoly2Expr(Ai.get(0).get(0)));
                    if (temp.isAST()) {
                        ((IAST) temp).addEvalFlags(IAST.IS_DECOMPOSED_PARTIAL_FRACTION);
                    }
                    result.append(temp);
                }
                for (int i = 1; i < Ai.size(); i++) {
                    List<GenPolynomial<BigInteger>> list = Ai.get(i);
                    long j = 0L;
                    for (GenPolynomial<BigInteger> genPolynomial : list) {
                        if (!genPolynomial.isZERO()) {
                            temp = F.eval(F.Times(jas.integerPoly2Expr(genPolynomial),
                                    F.Power(jas.integerPoly2Expr(D.get(i - 1)), F.integer(j * (-1L)))));
                            if (!temp.isZero()) {
                                if (temp.isAST()) {
                                    ((IAST) temp).addEvalFlags(IAST.IS_DECOMPOSED_PARTIAL_FRACTION);
                                }
                                result.append(temp);
                            }
                        }
                        j++;
                    }

                }
                return result;
            }
        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        final IExpr arg1 = ast.arg1();
        IAST temp = Structure.threadLogicEquationOperators(arg1, ast, 1);
        if (temp.isPresent()) {
            return temp;
        }

        IAST variableList = null;
        if (ast.isAST2()) {
            variableList = Validate.checkSymbolOrSymbolList(ast, 2);
        } else {
            VariablesSet eVar = new VariablesSet(arg1);
            if (eVar.isSize(0)) {
                return arg1;
            }
            if (!eVar.isSize(1)) {
                // partial fraction only possible for univariate polynomials
                return arg1;
            }
            variableList = eVar.getVarList();
        }

        if (arg1.isTimes() || arg1.isPower()) {
            IExpr[] parts = fractionalParts(arg1, false);
            if (parts != null) {
                return partialFractionDecompositionRational(new PartialFractionGenerator(), parts,
                        variableList.arg1());
            }
        }
        return arg1;

        // return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}
