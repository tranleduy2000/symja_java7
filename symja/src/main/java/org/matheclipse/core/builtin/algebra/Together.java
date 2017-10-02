package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.structure.Structure;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.builtin.algebra.Algebra.cancelGCD;
import static org.matheclipse.core.builtin.algebra.Algebra.expandAll;
import static org.matheclipse.core.builtin.algebra.Algebra.fractionalPartsRational;

/**
 * <h2>Together</h2>
 * <p>
 * <pre>
 * <code>Together(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * writes sums of fractions in <code>expr</code> together.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt;&gt; Together(a/b+x/y)
 * (a*y+b*x)*b^(-1)*y^(-1)
 *
 * &gt;&gt; Together(a / c + b / c)
 * (a+b)/c
 * </code>
 * </pre>
 * <p>
 * <code>Together</code> operates on lists:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Together({x / (y+1) + x / (y+1)^2})
 * {x (2 + y) / (1 + y) ^ 2}
 * </code>
 * </pre>
 * <p>
 * But it does not touch other functions:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Together(f(a / c + b / c))
 * f(a/c+b/c)
 * &gt;&gt; f(x)/x+f(x)/x^2//Together
 * f(x)/x^2+f(x)/x
 * </code>
 * </pre>
 */
public class Together extends AbstractFunctionEvaluator {

    /**
     * Calls <code>Together</code> for each argument of the <code>ast</code>.
     *
     * @param ast
     * @return <code>F.NIL</code> if the <code>ast</code> couldn't be evaluated.
     */
    private static IAST togetherForEach(final IAST ast, EvalEngine engine) {
        IAST result = F.NIL;
        for (int i = 1; i < ast.size(); i++) {
            if (ast.get(i).isAST()) {
                IExpr temp = togetherNull((IAST) ast.get(i), engine);
                if (temp.isPresent()) {
                    if (!result.isPresent()) {
                        result = ast.copy();
                    }
                    result.set(i, temp);
                }
            }
        }
        return result;
    }

    /**
     * Do a <code>ExpandAll(ast)</code> and call <code>togetherAST</code> afterwards
     * with the result..
     *
     * @param ast
     * @return <code>null</code> couldn't be transformed by
     * <code>ExpandAll(()</code> od <code>togetherAST()</code>
     */
    private static IExpr togetherNull(IAST ast, EvalEngine engine) {
        boolean evaled = false;
        IExpr temp = expandAll(ast, null, true, false, engine);
        if (!temp.isPresent()) {
            temp = ast;
        } else {
            evaled = true;
        }
        if (temp.isAST()) {
            IExpr result = togetherPlusTimesPower((IAST) temp, engine);
            if (result.isPresent()) {
                return F.eval(result);
            }
        }
        if (evaled) {
            return temp;
        }
        return F.NIL;
    }

    /**
     * @param plusAST a <code>Plus[...]</code> expresion
     * @return <code>null</code> couldn't be transformed by
     * <code>ExpandAll(()</code> od <code>togetherAST()</code>
     */
    private static IExpr togetherPlus(IAST plusAST) {
        if (plusAST.size() <= 2) {
            return F.NIL;
        }
        IAST numerator = F.ast(F.Plus, plusAST.size(), false);
        IAST denominator = F.ast(F.Times, plusAST.size(), false);
        boolean evaled = false;
        IExpr temp;
        IExpr[] fractionalParts;
        for (int i = 1; i < plusAST.size(); i++) {
            fractionalParts = Algebra.fractionalParts(plusAST.get(i), false);
            if (fractionalParts != null) {
                numerator.append(i, fractionalParts[0]);
                temp = fractionalParts[1];
                if (!temp.isOne()) {
                    evaled = true;
                }
                denominator.append(i, temp);
            } else {
                numerator.append(i, plusAST.get(i));
                denominator.append(i, F.C1);
            }
        }
        if (!evaled) {
            return F.NIL;
        }
        IAST ni;
        for (int i = 1; i < plusAST.size(); i++) {
            ni = F.TimesAlloc(plusAST.size() - 1);
            ni.append(numerator.get(i));
            for (int j = 1; j < plusAST.size(); j++) {
                if (i == j) {
                    continue;
                }
                temp = denominator.get(j);
                if (!temp.isOne()) {
                    ni.append(temp);
                }
            }
            numerator.set(i, ni.getOneIdentity(F.C1));
        }
        int i = 1;
        while (denominator.size() > i) {
            if (denominator.get(i).isOne()) {
                denominator.remove(i);
                continue;
            }
            i++;
        }
        if (denominator.isAST0()) {
            return F.NIL;
        }

        IExpr exprNumerator = F.evalExpand(numerator.getOneIdentity(F.C0));
        IExpr exprDenominator = F.evalExpand(denominator.getOneIdentity(F.C1));
        if (exprNumerator.isZero()) {
            if (exprDenominator.isZero()) {
                // let the standard evaluation handle the division by zero
                // 0^0
                return F.Times(exprNumerator, F.Power(exprDenominator, F.CN1));
            }
            return F.C0;
        }
        if (!exprDenominator.isOne()) {
            try {
                IExpr[] result = cancelGCD(exprNumerator, exprDenominator);
                if (result != null) {
                    IExpr pInv = result[2].inverse();
                    if (result[0].isOne()) {
                        return F.Times(pInv, result[1]);
                    }
                    return F.Times(result[0], result[1], pInv);
                }
                return F.Times(exprNumerator, F.Power(exprDenominator, -1));
            } catch (JASConversionException jce) {
                if (Config.DEBUG) {
                    jce.printStackTrace();
                }
            }
            return F.Times(exprNumerator, F.Power(exprDenominator, F.CN1));
        }
        return exprNumerator;
    }

    public static IExpr togetherPlusTimesPower(final IAST ast, EvalEngine engine) {
        if (ast.isPlus()) {
            IAST result = togetherForEach(ast, engine);
            if (result.isPresent()) {
                return togetherPlus(result).orElse(result);
            }
            return togetherPlus(ast);
        } else if (ast.isTimes() || ast.isPower()) {
            try {
                IAST result = F.NIL;
                if (ast.isTimes()) {
                    result = togetherForEach(ast, engine);
                } else {
                    // Power
                    result = togetherPower(ast, result, engine);
                }
                if (result.isPresent()) {
                    IExpr temp = F.eval(result);
                    if (temp.isTimes() || temp.isPower()) {
                        return Cancel.cancelPowerTimes(temp).orElse(temp);
                    }
                    return temp;
                }
                return Cancel.cancelPowerTimes(ast);
            } catch (JASConversionException jce) {
                if (Config.DEBUG) {
                    jce.printStackTrace();
                }
            }
        }

        return F.NIL;
    }

    private static IAST togetherPower(final IAST ast, IAST result, EvalEngine engine) {
        if (ast.arg1().isAST()) {
            IExpr temp = togetherNull((IAST) ast.arg1(), engine);
            if (temp.isPresent()) {
                if (!result.isPresent()) {
                    result = ast.copy();
                }
                if (ast.arg2().isNegative() && temp.isTimes()) {
                    IExpr[] fractionalParts = fractionalPartsRational(temp);
                    if (fractionalParts != null) {
                        result.set(1, F.Divide(fractionalParts[1], fractionalParts[0]));
                        result.set(2, ast.arg2().negate());
                    } else {
                        result.set(1, temp);
                    }
                } else {
                    result.set(1, temp);
                }
            }
        }
        return result;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        IExpr arg1 = ast.arg1();
        IAST temp = Structure.threadLogicEquationOperators(arg1, ast, 1);
        if (temp.isPresent()) {
            return temp;
        }
        if (arg1.isPlusTimesPower()) {
            return togetherNull((IAST) arg1, engine).orElse(arg1);
        }
        return arg1;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }
}
