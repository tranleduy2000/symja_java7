package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.builtin.exptrigsfunction.ExpTrigsFunctions;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractArgMultiple;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.ApcomplexNum;
import org.matheclipse.core.expression.ApfloatNum;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.Num;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.patternmatching.HashedOrderlessMatcher;

import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Positive;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Subtract;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.y_;

public class Times extends AbstractArgMultiple implements INumeric {
    /**
     * Constructor for the singleton
     */
    public final static Times CONST = new Times();

    private static HashedOrderlessMatcher ORDERLESS_MATCHER = new HashedOrderlessMatcher();

    public Times() {
    }

    private static IExpr eInfinity(IAST inf, IExpr o1) {
        if (inf.isComplexInfinity()) {
            if (o1.isZero()) {
                return F.Indeterminate;
            }
            return F.CComplexInfinity;
        }
        if (inf.isInfinity()) {
            if (o1.isInfinity()) {
                return F.CInfinity;
            }
            if (o1.isNegativeInfinity()) {
                return F.CNInfinity;
            }
            if (o1.isComplexInfinity()) {
                return F.CComplexInfinity;
            }
        }
        if (inf.isNegativeInfinity()) {
            if (o1.isInfinity()) {
                return F.CNInfinity;
            }
            if (o1.isNegativeInfinity()) {
                return F.CInfinity;
            }
            if (o1.isComplexInfinity()) {
                return F.CComplexInfinity;
            }
        }
        if (inf.isAST1()) {
            if (o1.isNumber() || o1.isSymbol()) {
                if (inf.isAST1()) {
                    return DirectedInfinity.timesInf(inf, o1);
                }

            }
            if (o1.isDirectedInfinity() && o1.isAST1()) {
                return F.eval(F.DirectedInfinity(F.Times(inf.arg1(), ((IAST) o1).arg1())));
            }
        }
        return F.NIL;
    }

    // private void addTrigRules(ISymbol head1, ISymbol head2, ISymbol
    // resultHead) {
    // IAST sinX_ = F.unaryAST1(head1, x_);
    // IAST cotX_ = F.unaryAST1(head2, x_);
    // IAST sinX = F.unaryAST1(head1, x);
    // IAST cotX = F.unaryAST1(head2, x);
    // IAST resultX = F.unaryAST1(resultHead, x);
    // ORDERLESS_MATCHER.defineHashRule(sinX_, cotX_, resultX);
    // ORDERLESS_MATCHER.defineHashRule(sinX_, F.Power(cotX_, $p(n,
    // IntegerQ)),
    // F.Times(F.Power(cotX, F.Subtract(n, F.C1)), resultX), F.Positive(n));
    // ORDERLESS_MATCHER.defineHashRule(F.Power(sinX_, $p(m, IntegerQ)),
    // cotX_,
    // F.Times(F.Power(sinX, F.Subtract(m, F.C1)), resultX), F.Positive(m));
    // ORDERLESS_MATCHER.defineHashRule(F.Power(sinX_, $p(m, IntegerQ)),
    // F.Power(cotX_, $p(n, IntegerQ)),
    // F.If(F.Greater(m, n), F.Times(F.Power(sinX, F.Subtract(m, n)),
    // F.Power(resultX, n)),
    // F.Times(F.Power(cotX, F.Subtract(n, m)), F.Power(resultX, m))),
    // F.And(F.Positive(m), F.Positive(n)));
    // }

    /**
     * Distribute a leading integer factor over the integer powers if available.
     * <code>12*2^x*3^y   ==>   2^(2+x)*3^(1+y)</code>.
     *
     * @param ast          the already evaluated expression
     * @param originalExpr the original expression which is used, if
     *                     <code>!ast.isPresent()</code>
     * @return the evaluated object or <code>ast</code>, if the distribution of an
     * integer factor isn't possible
     */
    private IExpr distributeLeadingFactor(IExpr ast, IAST originalExpr) {
        IExpr expr = ast;
        if (!expr.isPresent()) {
            expr = originalExpr;
        }
        if (expr.isTimes() && expr.getAt(1).isInteger()) {
            IAST times = (IAST) expr;
            IInteger leadingFactor = (IInteger) times.arg1();

            if (!leadingFactor.isMinusOne()) {
                IAST result = F.NIL;
                for (int i = 2; i < times.size(); i++) {
                    IExpr temp = times.get(i);
                    if (temp.isPower() && temp.getAt(1).isInteger() && !temp.getAt(2).isNumber()) {
                        IAST power = (IAST) temp;
                        IInteger powArg1 = (IInteger) power.arg1();
                        if (powArg1.isPositive()) {
                            IInteger mod = F.C0;
                            int count = 0;
                            while (!leadingFactor.isZero()) {
                                mod = leadingFactor.mod(powArg1);
                                if (mod.isZero()) {
                                    count++;
                                    leadingFactor = leadingFactor.div(powArg1);
                                } else {
                                    break;
                                }
                            }
                            if (count > 0) {
                                if (!result.isPresent()) {
                                    result = times.clone();
                                }
                                power = power.clone();
                                power.set(2, F.Plus(F.integer(count), power.arg2()));
                                result.set(i, power);
                            }
                        }
                    }
                }
                if (result.isPresent()) {
                    result.set(1, leadingFactor);
                    return result;
                }
            }

        }
        return ast;
    }

    @Override
    public IExpr e2ComArg(final IComplex c0, final IComplex c1) {
        return c0.multiply(c1);
    }

    @Override
    public IExpr e2DblArg(final INum d0, final INum d1) {
        return d0.multiply(d1);
    }

    @Override
    public IExpr e2DblComArg(final IComplexNum d0, final IComplexNum d1) {
        return d0.multiply(d1);
    }

    @Override
    public IExpr e2FraArg(final IFraction f0, final IFraction f1) {
        return f0.mul(f1);
    }

    @Override
    public IExpr e2IntArg(final IInteger i0, final IInteger i1) {
        return i0.multiply(i1);
    }

    @Override
    public IExpr e2ObjArg(final IExpr o0, final IExpr o1) {
        IExpr temp = F.NIL;

        if (o0.isZero()) {
            if (o1.isDirectedInfinity()) {
                return F.Indeterminate;
            }
            return F.C0;
        }

        if (o1.isZero()) {
            if (o0.isDirectedInfinity()) {
                return F.Indeterminate;
            }
            return F.C0;
        }

        if (o0.isOne()) {
            return o1;
        }

        if (o1.isOne()) {
            return o0;
        }

        if (o0.equals(o1)) {
            if (o0.isNumber()) {
                return o0.times(o0);
            }
            return o0.power(F.C2);
        }

        if (o0.isDirectedInfinity()) {
            temp = eInfinity((IAST) o0, o1);
        } else if (o1.isDirectedInfinity()) {
            temp = eInfinity((IAST) o1, o0);
        }
        if (temp.isPresent()) {
            return temp;
        }

        if (o0.isPower()) {
            // (x^a) * b
            final IAST power0 = (IAST) o0;
            IExpr power0Arg1 = power0.arg1();
            IExpr power0Arg2 = power0.arg2();
            if (power0.equalsAt(1, o1)) {
                // (x^a) * x
                if (power0Arg2.isNumber() && !o1.isRational()) {
                    // avoid reevaluation of a root of a rational number (example: 2*Sqrt(2) )
                    return o1.power(power0Arg2.inc());
                } else if (!power0Arg2.isNumber()) {
                    return o1.power(power0Arg2.inc());
                }
            }

            if (o1.isPower()) {
                final IAST power1 = (IAST) o1;
                IExpr power1Arg1 = power1.arg1();
                IExpr power1Arg2 = power1.arg2();
                temp = timesPowerPower(power0Arg1, power0Arg2, power1Arg1, power1Arg2);
                if (temp.isPresent()) {
                    return temp;
                }
            }
        }

        if (o1.isPower()) {
            final IAST power1 = (IAST) o1;
            IExpr power1Arg1 = power1.arg1();
            IExpr power1Arg2 = power1.arg2();
            temp = timesArgPower(o0, power1Arg1, power1Arg2);
            if (temp.isPresent()) {
                return temp;
            }
        }

        if (o1.isPlus()) {
            // final IAST f1 = (IAST) o1;
            // issue#128
            // if (o0.isMinusOne()) {
            // return f1.mapAt(F.Times(o0, null), 2);
            // }
            // if (o0.isInteger() && o1.isPlus() && o1.isAST2() && (((IAST)
            // o1).arg1().isNumericFunction())) {
            // // Note: this doesn't work for Together() function, if we
            // allow
            // // o0 to be a fractional number
            // return f1.mapAt(F.Times(o0, null), 2);
            // }
        }
        if (o0.isInterval1()) {
            if (o1.isInterval1() || o1.isSignedNumber()) {
                return timesInterval(o0, o1);
            }
        }
        if (o1.isInterval1()) {
            if (o0.isInterval1() || o0.isSignedNumber()) {
                return timesInterval(o0, o1);
            }
        }
        return F.NIL;
    }

    @Override
    public IExpr eComIntArg(final IComplex c0, final IInteger i1) {
        return c0.multiply(F.complex(i1, F.C0));
    }

    private IExpr evalNumericMode(final IAST ast) {
        INum number = F.CD1;
        int start = -1;
        for (int i = 1; i < ast.size(); i++) {
            if (ast.get(i) instanceof INum) {
                if (ast.get(i) instanceof ApfloatNum) {
                    number = number.multiply((INum) ast.get(i));
                } else {
                    if (number instanceof ApfloatNum) {
                        number = number.multiply(((INum) ast.get(i)).apfloatNumValue(number.precision()));
                    } else {
                        number = number.multiply((INum) ast.get(i));
                    }
                }
            } else if (ast.get(i) instanceof IComplexNum) {
                start = i;
                break;
            } else {
                return F.NIL;
            }
        }
        if (start < 0) {
            return number;
        }
        IComplexNum complexNumber;
        if (number instanceof Num) {
            complexNumber = F.complexNum(((Num) number).doubleValue());
        } else {
            complexNumber = F.complexNum(((ApfloatNum) number).apfloatValue());
        }
        for (int i = start; i < ast.size(); i++) {
            if (ast.get(i) instanceof INum) {
                number = (INum) ast.get(i);
                if (number instanceof Num) {
                    complexNumber = complexNumber.multiply(F.complexNum(((Num) number).doubleValue()));
                } else {
                    complexNumber = complexNumber.multiply(F.complexNum(((ApfloatNum) number).apfloatValue()));
                }
            } else if (ast.get(i) instanceof IComplexNum) {
                if (complexNumber instanceof ApcomplexNum) {
                    complexNumber = complexNumber
                            .multiply(((IComplexNum) ast.get(i)).apcomplexNumValue(complexNumber.precision()));
                } else {
                    complexNumber = complexNumber.multiply((IComplexNum) ast.get(i));
                }
            } else {
                return F.NIL;
            }
        }
        return complexNumber;
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        double result = 1;
        for (int i = top - size + 1; i < top + 1; i++) {
            result *= stack[i];
        }
        return result;
    }

    @Override
    public IExpr evaluate(final IAST ast1, EvalEngine engine) {
        IAST astTimes = ast1;
        int size = astTimes.size();
        if (size == 1) {
            return F.C1;
        }
        if (size == 2 && astTimes.head() == F.Times) {
            return astTimes.arg1();
        }
        if (size > 2) {
            IAST temp = evaluateHashsRepeated(astTimes, engine);
            if (temp.isPresent()) {
                return temp.getOneIdentity(F.C1);
            }
        }
        if (size == 3) {
            if ((astTimes.arg1().isNumeric() || astTimes.arg1().isOne() || astTimes.arg1().isMinusOne())
                    && astTimes.arg2().isPlus()) {
                if (astTimes.arg1().isOne()) {
                    return astTimes.arg2();
                }
                // distribute the number over the sum:
                final IAST arg2 = (IAST) astTimes.arg2();
                return arg2.mapThread(F.Times(astTimes.arg1(), null), 2);
            }
            return distributeLeadingFactor(binaryOperator(astTimes.arg1(), astTimes.arg2()), astTimes);
        }

        if (size > 3) {
            final ISymbol sym = astTimes.topHead();
            IAST result = null;
            IExpr tres;
            IExpr temp = astTimes.arg1();
            boolean evaled = false;
            int i = 2;

            while (i < astTimes.size()) {

                tres = binaryOperator(temp, astTimes.get(i));

                if (!tres.isPresent()) {

                    for (int j = i + 1; j < astTimes.size(); j++) {
                        tres = binaryOperator(temp, astTimes.get(j));

                        if (tres.isPresent()) {
                            evaled = true;
                            temp = tres;

                            astTimes = astTimes.removeAtClone(j);

                            break;
                        }
                    }

                    if (!tres.isPresent()) {
                        if (result == null) {
                            result = F.ast(sym, astTimes.size() - i + 1, false);
                        }
                        result.append(temp);
                        if (i == astTimes.size() - 1) {
                            result.append(astTimes.get(i));
                        } else {
                            temp = astTimes.get(i);
                        }
                        i++;
                    }

                } else {
                    evaled = true;
                    temp = tres;

                    if (i == (astTimes.size() - 1)) {
                        if (result == null) {
                            result = F.ast(sym, astTimes.size() - i + 1, false);
                        }
                        result.append(temp);
                    }

                    i++;
                }
            }

            if (evaled) {
                if (sym.hasOneIdentityAttribute() && result.size() > 1) {
                    return result.getOneIdentity(F.C0);
                }

                return distributeLeadingFactor(result, F.NIL);
            }
            return distributeLeadingFactor(F.NIL, astTimes);
        }

        return F.NIL;
    }

    @Override
    public HashedOrderlessMatcher getHashRuleMap() {
        return ORDERLESS_MATCHER;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public IExpr numericEval(final IAST ast, EvalEngine engine) {
        IExpr temp = evalNumericMode(ast);
        if (temp.isPresent()) {
            return temp;
        }
        return evaluate(ast, engine);
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.ONEIDENTITY | ISymbol.ORDERLESS | ISymbol.FLAT | ISymbol.LISTABLE
                | ISymbol.NUMERICFUNCTION);
        // ORDERLESS_MATCHER.setUpHashRule("Log[x_]", "Log[y_]^(-1)",
        // Log.getFunction());
        ORDERLESS_MATCHER.defineHashRule(Log(x_), Power(Log(y_), CN1), ExpTrigsFunctions.integerLogFunction());

        // Sin(x)*Cot(x) -> Cos(x)
        ORDERLESS_MATCHER.defineHashRule(F.Sin(x_), F.Cot(x_), Cos(x));
        ORDERLESS_MATCHER.defineHashRule(F.Sin(x_), F.Sec(x_), F.Tan(x));
        ORDERLESS_MATCHER.defineHashRule(Cos(x_), F.Tan(x_), F.Sin(x));
        ORDERLESS_MATCHER.defineHashRule(F.Csc(x_), F.Tan(x_), F.Sec(x));
        ORDERLESS_MATCHER.defineHashRule(Cos(x_), F.Csc(x_), F.Cot(x));
        ORDERLESS_MATCHER.defineHashRule(F.ProductLog(x_), Power(F.E, F.ProductLog(x_)), x);

        // Cos(x)^m * Sec(x)^n -> Cos(x)^(m-n)
        ORDERLESS_MATCHER.definePatternHashRule(Power(Cos(x_), F.m_Integer), Power(F.Sec(x_), F.n_Integer),
                Power(Cos(x), Subtract(F.m, F.n)), F.And(Positive(F.m), F.Greater(F.m, F.n)));
        // Cos(x)^m * Sec(x) -> Cos(x)^(m-1)
        ORDERLESS_MATCHER.definePatternHashRule(Power(Cos(x_), F.m_Integer), F.Sec(x_),
                Power(Cos(x), Subtract(F.m, F.C1)));

        // Sin(x)^m * Csc(x)^n -> Sin(x)^(m-n)
        ORDERLESS_MATCHER.definePatternHashRule(Power(Sin(x_), F.m_Integer), Power(F.Csc(x_), F.n_Integer),
                Power(Sin(x), Subtract(F.m, F.n)), F.And(Positive(F.m), F.Greater(F.m, F.n)));
        // Sin(x)^m * Csc(x) -> Sin(x)^(m-1)
        ORDERLESS_MATCHER.definePatternHashRule(Power(Sin(x_), F.m_Integer), F.Csc(x_),
                Power(Sin(x), Subtract(F.m, F.C1)));

        super.setUp(newSymbol);
    }

    /**
     * Try simplifying <code>arg0 * ( power1Arg1 ^ power1Arg2 )</code>
     *
     * @param arg0
     * @param power1Arg1
     * @param power1Arg2
     * @return
     */
    private IExpr timesArgPower(final IExpr arg0, IExpr power1Arg1, IExpr power1Arg2) {
        if (power1Arg1.equals(arg0)) {
            if (power1Arg2.isNumber() && !arg0.isRational()) {
                // avoid reevaluation of a root of a rational number (example: 2*Sqrt(2) )
                return arg0.power(power1Arg2.inc());
            } else if (!power1Arg2.isNumber()) {
                return arg0.power(power1Arg2.inc());
            }
            // } else if (arg0.isPlus() && power1Arg1.equals(arg0.negate()))
            // {
            // // Issue#128
            // if (power1Arg2.isInteger()) {
            // return arg0.power(power1Arg2.inc()).negate();
            // } else if (!power1Arg2.isNumber()) {
            // return arg0.power(power1Arg2.inc()).negate();
            // }
        } else if (power1Arg1.isInteger() && power1Arg2.isFraction()) {
            if (power1Arg1.isMinusOne()) {
                if (arg0.isImaginaryUnit()) {
                    // I * power1Arg1 ^ power1Arg2 -> (-1) ^ (power1Arg2 +
                    // (1/2))
                    return Power(CN1, power1Arg2.plus(F.C1D2));
                }
                if (arg0.isNegativeImaginaryUnit()) {
                    // (-I) * power1Arg1 ^ power1Arg2 -> (-1) * (-1) ^
                    // (power1Arg2 + (1/2))
                    return F.Times(CN1, Power(CN1, power1Arg2.plus(F.C1D2)));
                }
            }
            if (arg0.isFraction()) {
                // example: 1/9 * 3^(1/2) -> 1/3 * 3^(-1/2)

                // TODO implementation for complex numbers instead of
                // fractions
                IFraction f0 = (IFraction) arg0;
                IInteger pArg1 = (IInteger) power1Arg1;
                IFraction pArg2 = (IFraction) power1Arg2;
                if (pArg1.isPositive()) {
                    if (pArg2.isPositive()) {
                        IInteger denominatorF0 = f0.getDenominator();
                        IInteger[] res = denominatorF0.divideAndRemainder(pArg1);
                        if (res[1].isZero()) {
                            return F.Times(F.fraction(f0.getNumerator(), res[0]), Power(pArg1, pArg2.negate()));
                        }
                    } else {
                        IInteger numeratorF0 = f0.getNumerator();
                        IInteger[] res = numeratorF0.divideAndRemainder(pArg1);
                        if (res[1].isZero()) {
                            return F.Times(F.fraction(res[0], f0.getDenominator()), Power(pArg1, pArg2.negate()));
                        }
                    }
                }
            }
        }

        return F.NIL;
    }

    private IExpr timesInterval(final IExpr o0, final IExpr o1) {
        return F.Interval(F.List(
                F.Min(o0.lower().times(o1.lower()), o0.lower().times(o1.upper()), o0.upper().times(o1.lower()),
                        o0.upper().times(o1.upper())),
                F.Max(o0.lower().times(o1.lower()), o0.lower().times(o1.upper()), o0.upper().times(o1.lower()),
                        o0.upper().times(o1.upper()))));
    }

    /**
     * Try simpplifying
     * <code>(power0Arg1 ^ power0Arg2) * (power1Arg1 ^ power1Arg2)</code>
     *
     * @param power0Arg1
     * @param power0Arg2
     * @param power1Arg1
     * @param power1Arg2
     * @return
     */
    private IExpr timesPowerPower(IExpr power0Arg1, IExpr power0Arg2, IExpr power1Arg1, IExpr power1Arg2) {
        if (power0Arg2.isNumber()) {
            if (power1Arg2.isNumber()) {
                if (power0Arg1.equals(power1Arg1)) {
                    // x^(a)*x^(b) => x ^(a+b)
                    return power0Arg1.power(power0Arg2.plus(power1Arg2));
                }
                if (power0Arg2.equals(power1Arg2) && power0Arg1.isPositive() && power1Arg1.isPositive()
                        && power0Arg1.isSignedNumber() && power1Arg1.isSignedNumber()) {
                    // a^(c)*b^(c) => (a*b) ^c
                    return power0Arg1.times(power1Arg1).power(power0Arg2);
                }
                // if (power0Arg1.isPlus() && power1Arg1.isPlus() &&
                // power0Arg1.equals(power1Arg1.negate())) {// Issue#128
                // return
                // power0Arg1.power(power0Arg2.plus(power1Arg2)).times(CN1.power(power1Arg2));
                // }
            }
        }
        if (power0Arg1.equals(power1Arg1)) {
            // x^(a)*x^(b) => x ^(a+b)
            return power0Arg1.power(power0Arg2.plus(power1Arg2));
        }
        return F.NIL;
    }
}
