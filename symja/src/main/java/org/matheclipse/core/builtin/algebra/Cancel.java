package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.structure.Structure;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;

import static org.matheclipse.core.builtin.algebra.Algebra.cancelGCD;
import static org.matheclipse.core.builtin.algebra.Algebra.fractionalParts;

/**
 * <h2>Cancel</h2>
 * <p>
 * <pre>
 * <code>Cancel(expr)
 * </code>
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * cancels out common factors in numerators and denominators.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * <code>&gt;&gt; Cancel(x / x ^ 2)
 * 1/x
 * </code>
 * </pre>
 * <p>
 * 'Cancel' threads over sums:
 * </p>
 * <p>
 * <pre>
 * <code>&gt;&gt; Cancel(x / x ^ 2 + y / y ^ 2)
 * 1/x+1/y
 *
 * &gt;&gt; Cancel(f(x) / x + x * f(x) / x ^ 2)
 * (2*f(x))/x
 * </code>
 * </pre>
 */
public class Cancel extends AbstractFunctionEvaluator {

    /**
     * Return the result divided by the gcd value.
     *
     * @param numeratorPlus  a <code>Plus[...]</code> expression as the numerator
     * @param denominatorInt an integer value for the denominator
     * @param gcd            the integer gcd value
     * @return
     */
    private static IExpr[] calculatePlusIntegerGCD(IAST numeratorPlus, IInteger denominatorInt, IInteger gcd) {
        for (int i = 1; i < numeratorPlus.size(); i++) {
            if (numeratorPlus.get(i).isInteger()) {
                numeratorPlus.set(i, ((IInteger) numeratorPlus.get(i)).div(gcd));
            } else if (numeratorPlus.get(i).isTimes() && numeratorPlus.get(i).getAt(1).isInteger()) {
                IAST times = ((IAST) numeratorPlus.get(i)).clone();
                times.set(1, ((IInteger) times.arg1()).div(gcd));
                numeratorPlus.set(i, times);
            } else {
                throw new WrongArgumentType(numeratorPlus, numeratorPlus.get(i), i, "unexpected argument");
            }
        }
        IExpr[] result = new IExpr[3];
        result[0] = F.C1;
        result[1] = numeratorPlus;
        result[2] = denominatorInt.div(gcd);
        return result;
    }

    /**
     * Calculate the GCD[] of the integer factors in each element of the
     * <code>numeratorPlus</code> expression with the <code>denominatorInt</code>.
     * After that return the result divided by the gcd value, if possible.
     *
     * @param numeratorPlus  a <code>Plus[...]</code> expression as the numerator
     * @param denominatorInt an integer value for the denominator
     * @return <code>null</code> if no gcd value was found
     */
    public static IExpr[] cancelPlusIntegerGCD(IAST numeratorPlus, IInteger denominatorInt) {
        IAST plus = numeratorPlus.clone();
        IAST gcd = F.ast(F.GCD, plus.size() + 1, false);
        gcd.append(denominatorInt);
        boolean evaled = true;
        for (int i = 1; i < plus.size(); i++) {
            IExpr temp = plus.get(i);
            if (temp.isInteger()) {
                gcd.append(temp);
            } else {
                if (temp.isTimes() && temp.getAt(1).isInteger()) {
                    gcd.append(temp.getAt(1));
                } else {
                    evaled = false;
                    break;
                }
            }
        }
        if (evaled) {
            // GCD() has attribute Orderless, so the arguments will
            // be sorted by evaluation!
            IExpr temp = F.eval(gcd);
            if (temp.isInteger() && !temp.isOne()) {
                IInteger igcd = (IInteger) temp;
                return calculatePlusIntegerGCD(plus, denominatorInt, igcd);
            }
        }
        return null;
    }

    /**
     * @param powerTimesAST an <code>Times[...] or Power[...]</code> AST, where common factors
     *                      should be canceled out.
     * @return <code>F.NIL</code> is no evaluation was possible
     * @throws JASConversionException
     */
    public static IExpr cancelPowerTimes(IExpr powerTimesAST) throws JASConversionException {
        IExpr[] parts = fractionalParts(powerTimesAST, false);
        if (parts != null && parts[0].isPlus() && parts[1].isPlus()) {
            IAST numParts = ((IAST) parts[0]).partitionPlus(new PolynomialPredicate(), F.C0, F.C1, F.List);
            IAST denParts = ((IAST) parts[1]).partitionPlus(new PolynomialPredicate(), F.C0, F.C1, F.List);
            if (denParts.isPresent() && !denParts.get(1).isOne()) {
                IExpr[] result = cancelGCD(numParts.get(1), denParts.get(1));
                if (result != null) {
                    return F.Times(result[0], result[1], numParts.get(2),
                            F.Power(F.Times(result[2], denParts.get(2)), F.CN1));
                }
            }

        }
        return F.NIL;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);

        IExpr arg1 = ast.arg1();
        if (ast.isAST1() && arg1.isAtom()) {
            return arg1;
        }
        IAST temp = Structure.threadPlusLogicEquationOperators(arg1, ast, 1);
        if (temp.isPresent()) {
            return temp;
        }
        try {
            if (arg1.isTimes() || arg1.isPower()) {
                IExpr result = cancelPowerTimes(arg1);
                if (result.isPresent()) {
                    return result;
                }
            }
            IExpr expandedArg1 = F.evalExpandAll(arg1, engine);

            if (expandedArg1.isPlus()) {
                return ((IAST) expandedArg1).mapThread(F.Cancel(null), 1);
            } else if (expandedArg1.isTimes() || expandedArg1.isPower()) {
                IExpr result = cancelPowerTimes(expandedArg1);
                if (result.isPresent()) {
                    return result;
                }
            }
        } catch (JASConversionException jce) {
            if (Config.DEBUG) {
                jce.printStackTrace();
            }
        }
        return arg1;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE);
    }

}