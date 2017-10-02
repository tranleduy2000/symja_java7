package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractArg2;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.expression.ApcomplexNum;
import org.matheclipse.core.expression.ApfloatNum;
import org.matheclipse.core.expression.NumberUtil;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.rules.PowerRules;

import static org.matheclipse.core.expression.F.Abs;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CComplexInfinity;
import static org.matheclipse.core.expression.F.CI;
import static org.matheclipse.core.expression.F.CInfinity;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CNI;
import static org.matheclipse.core.expression.F.CNInfinity;
import static org.matheclipse.core.expression.F.Exp;
import static org.matheclipse.core.expression.F.Indeterminate;
import static org.matheclipse.core.expression.F.Interval;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Max;
import static org.matheclipse.core.expression.F.NIL;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.ast;
import static org.matheclipse.core.expression.F.complexNum;
import static org.matheclipse.core.expression.F.evalExpand;
import static org.matheclipse.core.expression.F.evaln;
import static org.matheclipse.core.expression.F.fraction;

public class Power extends AbstractArg2 implements INumeric, PowerRules {

    /**
     * <p>
     * Calculate <code>interval({lower, upper}) ^ exponent</code>.
     * </p>
     * <p>
     * See: <a href=
     * "https://de.wikipedia.org/wiki/Intervallarithmetik#Elementare_Funktionen">
     * Intervallarithmetik - Elementare Funktionen</a>
     * </p>
     *
     * @param interval
     * @param exponent
     * @return
     */
    private static IExpr powerInterval(final IExpr interval, IInteger exponent) {
        if (exponent.isNegative()) {
            if (exponent.isMinusOne()) {
                // TODO implement division
                return NIL;
            }
            return Power(powerIntervalPositiveExponent(interval, exponent.negate()), CN1);
        }
        return powerIntervalPositiveExponent(interval, exponent);
    }

    private static IExpr powerIntervalPositiveExponent(final IExpr interval, IInteger exponent) {
        if (exponent.isEven()) {
            if (!interval.lower().isNegativeResult()) {
                return Interval(List(interval.lower().power(exponent), interval.upper().power(exponent)));
            } else {
                if (interval.upper().isNegativeResult()) {
                    return Interval(List(interval.upper().power(exponent), interval.lower().power(exponent)));
                }
                return Interval(
                        List(C0, Max(interval.lower().power(exponent), interval.upper().power(exponent))));
            }
        }
        return Interval(List(interval.lower().power(exponent), interval.upper().power(exponent)));
    }

    /**
     * Transform <code>Power(Times(a,b,c,Power(d,-1.0)....), -1.0)</code> to
     * <code>Times(a^(-1.0),b^(-1.0),c^(-1.0),d,....)</code>
     *
     * @param timesAST
     * @param arg2
     * @return <code>F.NIL</code> if the transformation isn't possible.
     */
    private static IExpr powerTimesInverse(IAST timesAST, final IExpr arg2) {
        IAST resultAST = NIL;
        for (int i = 1; i < timesAST.size(); i++) {
            IExpr temp = timesAST.get(i);
            if (temp.isPower() && temp.getAt(2).isMinusOne()) {
                if (!resultAST.isPresent()) {
                    resultAST = timesAST.clone();
                    for (int j = 1; j < i; j++) {
                        resultAST.set(j, Power(timesAST.get(j), arg2));
                    }
                }
                resultAST.set(i, temp.getAt(1));
            } else {
                if (resultAST.isPresent()) {
                    resultAST.set(i, Power(temp, arg2));
                }
            }
        }
        return resultAST;
    }

    /**
     * Split this integer into the nth-root (with prime factors less equal 1021) and
     * the &quot;rest factor&quot;
     *
     * @return <code>{nth-root, rest factor}</code> or <code>null</code> if the root
     * is not available
     */
    private IInteger[] calculateRoot(IInteger a, IInteger root) {
        try {
            int n = root.toInt();
            if (n > 0) {
                if (a.isOne()) {
                    return null;
                }
                if (a.isMinusOne()) {
                    return null;
                }
                IInteger[] result = a.nthRootSplit(n);
                if (result[1].equals(a)) {
                    // no roots found
                    return null;
                }
                return result;
            }
        } catch (ArithmeticException e) {

        }
        return null;
    }

    @Override
    public IExpr e2ApcomplexArg(final ApcomplexNum ac0, final ApcomplexNum ac1) {
        return ac0.pow(ac1);
    }

    @Override
    public IExpr e2ApfloatArg(final ApfloatNum af0, final ApfloatNum af1) {
        return af0.pow(af1);
    }

    @Override
    public IExpr e2ComArg(final IComplex c0, final IComplex c1) {
        return NIL;
    }

    @Override
    public IExpr e2DblArg(final INum d0, final INum d1) {
        if (d1.isMinusOne()) {
            return d0.inverse();
        }
        if (d1.isNumIntValue()) {
            return d0.pow(d1);
        }
        if (d0.isNegative()) {
            return complexNum(d0.doubleValue()).pow(complexNum(d1.doubleValue()));
        }
        return d0.pow(d1);
    }

    @Override
    public IExpr e2DblComArg(final IComplexNum c0, final IComplexNum c1) {
        return c0.pow(c1);
    }

    @Override
    public IExpr e2FraArg(IFraction f0, IFraction f1) {
        if (f0.getNumerator().isZero()) {
            return C0;
        }

        if (f1.getNumerator().isZero()) {
            return C1;
        }

        if (f1.equals(C1D2)) {
            if (f0.isNegative()) {
                return Times(CI, Power(f0.negate(), f1));
            }
        }

        if (f1.equals(CN1D2)) {
            if (f0.isNegative()) {
                return Times(CNI, Power(f0.negate().inverse(), f1.negate()));
            }
        }

        if (!f1.getDenominator().isOne()) {
            IInteger a;
            IInteger b;
            IFraction f0Temp = f0;
            if (f0.sign() < 0) {
                f0Temp = f0Temp.negate();
            }
            if (f1.isNegative()) {
                a = f0Temp.getDenominator();
                b = f0Temp.getNumerator();
            } else {
                a = f0Temp.getNumerator();
                b = f0Temp.getDenominator();
            }

            // example: (-27)^(2/3) or 8^(1/3)
            if (!f1.getNumerator().isOne()) {
                try {
                    int exp = f1.getNumerator().toInt();
                    if (exp < 0) {
                        exp *= (-1);
                    }
                    a = a.pow(exp);
                    b = b.pow(exp);
                } catch (ArithmeticException e) {
                    return NIL;
                }
            }

            final IInteger root = f1.getDenominator();

            IInteger[] new_numer = calculateRoot(a, root);
            IInteger[] new_denom = calculateRoot(b, root);
            final IFraction new_root = fraction(C1, root);

            if (new_numer != null) {
                if (new_denom != null) {
                    IRational p0 = null;
                    if (new_denom[1].isOne()) {
                        p0 = new_numer[1];
                    } else {
                        p0 = fraction(new_numer[1], new_denom[1]);
                    }
                    if (f0.sign() < 0) {
                        return Times(fraction(new_numer[0], new_denom[0]), Power(p0.negate(), new_root));
                    }
                    return Times(fraction(new_numer[0], new_denom[0]), Power(p0, new_root));
                } else {
                    if (a.isOne()) {
                        return NIL;
                    }
                    IRational p0 = null;
                    if (b.isOne()) {
                        p0 = new_numer[1];
                    } else {
                        p0 = fraction(new_numer[1], b);
                    }
                    if (f0.sign() < 0) {
                        return Times(new_numer[0], Power(p0.negate(), new_root));
                    }
                    return Times(new_numer[0], Power(p0, new_root));
                }
            } else {
                if (new_denom != null) {
                    if (b.isOne()) {
                        return NIL;
                    }
                    IRational p0 = null;
                    if (new_denom[1].isOne()) {
                        p0 = a;
                    } else {
                        p0 = fraction(a, new_denom[1]);
                    }
                    if (f0.sign() < 0) {
                        return Times(fraction(C1, new_denom[0]), Power(p0.negate(), new_root));
                    }
                    return Times(fraction(C1, new_denom[0]), Power(p0, new_root));
                }
            }

            return NIL;
        }
        return f0.power(f1);
    }

    @Override
    public IExpr e2IntArg(final IInteger i0, final IInteger i1) {
        if (i0.isZero()) {
            // all other cases see e2ObjArg
            return NIL;
        }

        try {
            long n = i1.toLong();
            return i0.power(n);
        } catch (ArithmeticException ae) {

        }
        return NIL;
    }

    /**
     * @param arg1 a number
     * @param arg2 must be a <code>DirectedInfinity[...]</code> expression
     * @return
     */
    private IExpr e2NumberDirectedInfinity(final INumber arg1, final IAST arg2) {
        int comp = arg1.compareAbsValueToOne();
        switch (comp) {
            case 1:
                // Abs(arg1) > 1
                if (arg2.isInfinity()) {
                    // arg1 ^ Inf
                    if (arg1.isSignedNumber() && arg1.isPositive()) {
                        return CInfinity;
                    }
                    // complex or negative numbers
                    return CComplexInfinity;
                }
                if (arg2.isNegativeInfinity()) {
                    // arg1 ^ (-Inf)
                    return C0;
                }
                break;
            case -1:
                // Abs(arg1) < 1
                if (arg2.isInfinity()) {
                    // arg1 ^ Inf
                    return C0;
                }
                if (arg2.isNegativeInfinity()) {
                    // arg1 ^ (-Inf)
                    if (arg1.isSignedNumber() && arg1.isPositive()) {
                        return CInfinity;
                    }
                    // complex or negative numbers
                    return CComplexInfinity;
                }
                break;
        }
        return NIL;
    }

    @Override
    public IExpr e2ObjArg(final IExpr arg1, final IExpr arg2) {
        if (arg2.isDirectedInfinity()) {
            if (arg2.isComplexInfinity()) {
                return Indeterminate;
            }

            if (arg1.isOne() || arg1.isMinusOne() || arg1.isImaginaryUnit() || arg1.isNegativeImaginaryUnit()) {
                return Indeterminate;
            }
            IAST directedInfinity = (IAST) arg2;
            if (arg1.isZero()) {
                if (directedInfinity.isInfinity()) {
                    // 0 ^ Inf
                    return C0;
                }
                if (directedInfinity.isNegativeInfinity()) {
                    // 0 ^ (-Inf)
                    return CComplexInfinity;
                }
                return Indeterminate;
            }
            if (arg1.isInfinity()) {
                if (directedInfinity.isInfinity()) {
                    // Inf ^ Inf
                    return CComplexInfinity;
                }
                if (directedInfinity.isNegativeInfinity()) {
                    // Inf ^ (-Inf)
                    return C0;
                }
                return Indeterminate;
            }
            if (arg1.isNegativeInfinity()) {
                if (directedInfinity.isInfinity()) {
                    // (-Inf) ^ Inf
                    return CComplexInfinity;
                }
                if (directedInfinity.isNegativeInfinity()) {
                    // (-Inf) ^ (-Inf)
                    return C0;
                }
                return Indeterminate;
            }
            if (arg1.isComplexInfinity()) {
                if (directedInfinity.isInfinity()) {
                    // ComplexInfinity ^ Inf
                    return CComplexInfinity;
                }
                if (directedInfinity.isNegativeInfinity()) {
                    // ComplexInfinity ^ (-Inf)
                    return C0;
                }
                return Indeterminate;
            }
            if (arg1.isDirectedInfinity()) {
                if (directedInfinity.isInfinity()) {
                    return CComplexInfinity;
                }
                if (directedInfinity.isNegativeInfinity()) {
                    return C0;
                }
                return Indeterminate;
            }

            if (arg1.isNumber()) {
                IExpr temp = e2NumberDirectedInfinity((INumber) arg1, directedInfinity);
                if (temp.isPresent()) {
                    return temp;
                }
            } else {
                IExpr a1 = evaln(arg1);
                if (a1.isNumber()) {
                    IExpr temp = e2NumberDirectedInfinity((INumber) a1, directedInfinity);
                    if (temp.isPresent()) {
                        return temp;
                    }
                }
            }
        }
        if (arg1.isDirectedInfinity()) {
            if (arg2.isZero()) {
                return Indeterminate;
            }
            if (arg1.isComplexInfinity()) {
                if (arg2.isSignedNumber()) {
                    if (arg2.isNegative()) {
                        return C0;
                    }
                    return CComplexInfinity;
                }
                return Indeterminate;
            }
            if (arg2.isOne()) {
                return arg1;
            }
            if (arg2.isMinusOne()) {
                return C0;
            }
        }
        if (arg1.isZero()) {
            return powerZero(arg2);
        }
        if (arg1.isInterval1()) {
            if (arg2.isInteger()) {
                IInteger ii = (IInteger) arg2;
                return powerInterval(arg1, ii);
            }
        }

        if (arg2.isZero()) {
            return (arg1.isInfinity() || arg1.isNegativeInfinity()) ? Indeterminate : C1;
        }

        if (arg2.isOne()) {
            return arg1;
        }

        if (arg1.isOne()) {
            return C1;
        }

        if (arg1.isMinusOne() && arg2.isInteger()) {
            return (((IInteger) arg2).isEven()) ? C1 : CN1;
        }

        if (arg2.isSignedNumber()) {
            if (arg1.isComplex() && arg2.isFraction() && arg2.isPositive()) {
                IExpr temp = powerComplexFraction((IComplex) arg1, (IFraction) arg2);
                if (temp.isPresent()) {
                    return temp;
                }
            }
            ISignedNumber is1 = (ISignedNumber) arg2;
            if (arg1.isInfinity()) {
                if (is1.isNegative()) {
                    return C0;
                } else {
                    return CInfinity;
                }
            } else if (arg1.isPower() && is1.isNumIntValue() && is1.isPositive()) {
                IAST a0 = (IAST) arg1;
                if (a0.arg2().isNumIntValue() && a0.arg2().isPositive()) {
                    // (x*n)^m => x ^(n*m)
                    return Power(a0.arg1(), is1.times(a0.arg2()));
                }
            } else if (arg1.isNegativeInfinity() && arg2.isInteger()) {
                IInteger ii = (IInteger) arg2;
                if (ii.isNegative()) {
                    return C0;
                } else {
                    if (ii.isOdd()) {
                        return CNInfinity;
                    } else {
                        return CInfinity;
                    }
                }
            }
            if (arg2.isMinusOne() || arg2.isInteger()) {
                if (arg1.isNumber()) {
                    if (arg2.isMinusOne()) {
                        return ((INumber) arg1).inverse();
                    }
                    try {
                        long n = ((IInteger) arg2).toLong();
                        return ((INumber) arg1).power(n);
                    } catch (ArithmeticException ae) {

                    }
                } else {
                    IExpr o1negExpr = NIL;
                    if (arg2.isInteger() && ((IInteger) arg2).isEven()) {
                        o1negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1, true);
                    } else {
                        o1negExpr = AbstractFunctionEvaluator.getNormalizedNegativeExpression(arg1, false);
                    }
                    if (o1negExpr.isPresent()) {
                        if (arg2.isMinusOne()) {
                            return Times(CN1, Power(o1negExpr, CN1));
                        } else {
                            IInteger ii = (IInteger) arg2;
                            if (ii.isEven()) {
                                return Power(o1negExpr, arg2);
                            }
                        }
                    }
                    if (arg2.isMinusOne() && arg1.isTimes()) {
                        IAST timesAST = (IAST) arg1;
                        IExpr temp = powerTimesInverse(timesAST, arg2);
                        if (temp.isPresent()) {
                            return temp;
                        }
                    }
                }
            }
        }

        if (arg1.isSignedNumber() && ((ISignedNumber) arg1).isNegative() && arg2.equals(C1D2)) {
            // extract I for sqrt
            return Times(CI, Power(Negate(arg1), arg2));
        }

        if (arg1.isE() && (arg2.isPlusTimesPower())) {
            IExpr expandedFunction = evalExpand(arg2);
            if (expandedFunction.isPlus()) {
                return powerEPlus((IAST) expandedFunction);
            }
        }

        if (arg1.isAST()) {
            IAST astArg1 = (IAST) arg1;
            if (astArg1.isTimes()) {
                if (arg2.isInteger() || arg2.isMinusOne()) {
                    // (a * b * c)^n => a^n * b^n * c^n
                    return astArg1.mapThread(Power(null, arg2), 1);
                }
                if (arg2.isNumber()) {
                    final IAST f0 = astArg1;

                    if ((f0.size() > 1) && (f0.arg1().isNumber())) {
                        return Times(Power(f0.arg1(), arg2), Power(ast(f0, Times, true, 2, f0.size()), arg2));
                    }
                }
            } else if (astArg1.isPower()) {
                if (astArg1.arg2().isSignedNumber() && arg2.isSignedNumber()) {
                    IExpr temp = astArg1.arg2().times(arg2);
                    if (temp.isOne()) {
                        if (astArg1.arg1().isNonNegativeResult()) {
                            return astArg1.arg1();
                        }
                        if (astArg1.arg1().isRealResult()) {
                            return Abs(astArg1.arg1());
                        }
                    }
                }
                if (arg2.isInteger()) {
                    // (a ^ b )^n => a ^ (b * n)
                    if (astArg1.arg2().isNumber()) {
                        return Power(astArg1.arg1(), arg2.times(astArg1.arg2()));
                    }
                    return Power(astArg1.arg1(), Times(arg2, astArg1.arg2()));
                }
            }
        }
        return NIL;
    }

    /**
     * Determine <code>0 ^ exponent</code>.
     *
     * @param exponent the exponent of the 0-Power expression
     * @return
     */
    private IExpr powerZero(final IExpr exponent) {
        EvalEngine engine = EvalEngine.get();
        if (exponent.isZero()) {
            // 0^0
            // TODO add a real log message
            // throw new DivisionByZero("0^0");
            engine.printMessage("Infinite expression 0^0");
            return Indeterminate;
        }

        IExpr a = exponent.re();
        if (a.isSignedNumber()) {
            if (((ISignedNumber) a).isNegative()) {
                engine.printMessage("Infinite expression 0^(negative number)");
                return CComplexInfinity;
            }
            if (a.isZero()) {
                engine.printMessage("Infinite expression 0^0.");
                return Indeterminate;
            }
            return C0;
        }
        if (a.isNumericFunction()) {
            IExpr temp = engine.evalN(a);
            if (temp.isSignedNumber()) {
                if (((ISignedNumber) temp).isNegative()) {
                    engine.printMessage("Infinite expression 0^(negative number)");
                    return CComplexInfinity;
                }
                if (temp.isZero()) {
                    engine.printMessage("Infinite expression 0^0.");
                    return Indeterminate;
                }
                return C0;
            }
            if (temp.isComplex() || temp.isComplexNumeric()) {
                engine.printMessage("Indeterminate expression 0 ^ (complex number) encountered.");
                return Indeterminate;
            }
        }

        return NIL;
    }

    /**
     * Simplify <code>E^(y+Log(x))</code> to <code>x*E^(y)</code>
     *
     * @param plus
     * @return
     */
    private IAST powerEPlus(IAST plus) {
        IAST multiplicationFactors = NIL;
        IAST plusClone = NIL;
        for (int i = plus.size() - 1; i > 0; i--) {
            if (plus.get(i).isLog()) {
                if (!multiplicationFactors.isPresent()) {
                    multiplicationFactors = Times();
                    plusClone = plus.clone();
                }
                multiplicationFactors.append(plus.get(i).getAt(1));
                plusClone.remove(i);
            } else if (plus.get(i).isTimes()) {
                IAST times = (IAST) plus.get(i);
                for (int j = times.size() - 1; j > 0; j--) {
                    if (times.get(j).isLog()) {
                        IExpr innerFunc = times.get(j).getAt(1);
                        if (!multiplicationFactors.isPresent()) {
                            multiplicationFactors = Times();
                            plusClone = plus.clone();
                        }
                        multiplicationFactors.append(Power(innerFunc, ast(times, Times, false, j, j + 1)));
                        plusClone.remove(i);
                        break;
                    }
                }
            }
        }
        if (multiplicationFactors.isPresent()) {
            multiplicationFactors.append(Exp(plusClone));
            return multiplicationFactors;
        }
        return NIL;
    }

    /**
     * <code> complexNumber ^ fractionNumber</code>
     *
     * @param complexNumber
     * @param fractionNumber
     * @return
     */
    private IExpr powerComplexFraction(final IComplex complexNumber, final IFraction fractionNumber) {
        if (complexNumber.isImaginaryUnit()) {
            return Power(CN1, C1D2.times(fractionNumber));
        } else if (complexNumber.isNegativeImaginaryUnit()) {
            IInteger numerator = fractionNumber.getNumerator();
            IInteger denominator = fractionNumber.getDenominator();
            IInteger div = numerator.div(denominator);
            if (div.isOdd()) {
                div = div.subtract(C1);
            }
            IRational rat = fractionNumber.subtract(div);
            numerator = rat.getNumerator();
            denominator = rat.getDenominator().multiply(C2);
            return Times(CN1, Power(CNI, div),
                    Power(CN1, fraction(denominator.subtract(numerator), denominator)));
        }
        return NIL;
    }

    @Override
    public IExpr eComFraArg(final IComplex c0, final IFraction i1) {
        if (i1.equals(C1D2) && c0.getRealPart().isZero()) {
            // square root of pure imaginary number
            IRational im = c0.getImaginaryPart();
            boolean negative = false;
            im = im.divideBy(C2);
            if (im.isNegative()) {
                im = im.negate();
                negative = true;
            }
            if (NumberUtil.isPerfectSquare(im)) {
                IExpr temp = Sqrt(im);
                if (negative) {
                    // Sqrt(im.negate()) - I * Sqrt(im);
                    return Plus(temp, Times(CNI, temp));
                }
                // Sqrt(im.negate()) + I * Sqrt(im);
                return Plus(temp, Times(CI, temp));
            }
        }
        return NIL;
    }

    @Override
    public IExpr eComIntArg(final IComplex c0, final IInteger i1) {
        if (c0.isZero()) {
            return C0;
        }

        if (i1.isZero()) {
            return C1;
        }

        return c0.pow(i1.toBigNumerator().intValue());
    }

    @Override
    public double evalReal(final double[] stack, final int top, final int size) {
        if (size != 2) {
            throw new UnsupportedOperationException();
        }
        return Math.pow(stack[top - 1], stack[top]);
    }

    @Override
    public IAST getRuleAST() {
        return RULES;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        int size = ast.size();
        if (size == 1) {
            return C1;
        }
        if (size == 2 && ast.head() == Power) {
            return ast.arg1();
        }
        return super.evaluate(ast, engine);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.LISTABLE | ISymbol.ONEIDENTITY | ISymbol.NUMERICFUNCTION);
        super.setUp(newSymbol);
    }
}


  