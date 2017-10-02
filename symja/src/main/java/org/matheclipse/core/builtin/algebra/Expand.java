package org.matheclipse.core.builtin.algebra;

import com.google.common.math.LongMath;

import org.matheclipse.core.builtin.Combinatoric;
import org.matheclipse.core.builtin.NumberTheory;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.PlusOp;
import org.matheclipse.core.eval.PowerOp;
import org.matheclipse.core.eval.TimesOp;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INumber;

import javax.annotation.Nonnull;

import static org.matheclipse.core.builtin.algebra.Algebra.expand;
import static org.matheclipse.core.builtin.algebra.Algebra.fractionalPartsTimesPower;

/**
 * <pre>
 * Expand(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * expands out positive rational powers and products of sums in
 * <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Expand((x + y) ^ 3)
 * x^3+3*x^2*y+3*x*y^2+y^3
 *
 * &gt;&gt; Expand((a + b) (a + c + d))
 * a^2+a*b+a*c+b*c+a*d+b*d
 *
 * &gt;&gt; Expand((a + b) (a + c + d) (e + f) + e a a)
 * 2*a^2*e+a*b*e+a*c*e+b*c*e+a*d*e+b*d*e+a^2*f+a*b*f+a*c*f+b*c*f+a*d*f+b*d*f
 *
 * &gt;&gt; Expand((a + b) ^ 2 * (c + d))
 * a^2*c+2*a*b*c+b^2*c+a^2*d+2*a*b*d+b^2*d
 *
 * &gt;&gt; Expand((x + y) ^ 2 + x y)
 * x^2+3*x*y+y^2
 *
 * &gt;&gt; Expand(((a + b) (c + d)) ^ 2 + b (1 + a))
 * a^2*c^2+2*a*b*c^2+b^2*c^2+2*a^2*c*d+4*a*b*c*d+2*b^2*c*d+a^2*d^2+2*a*b*d^2+b^2*d^2+b(1+a)
 * </pre>
 * <p>
 * <code>Expand</code> expands out rational powers by expanding the
 * <code>Floor()</code> part of the rational powers number:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Expand((x + 3)^(5/2)+(x + 1)^(3/2)) Sqrt(1+x)+x*Sqrt(1+x)+9*Sqrt(3+x)+6*x*Sqrt(3+x)+x^2*Sqrt(3+x)
 * </pre>
 * <p>
 * <code>Expand</code> expands items in lists and rules:<br />
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Expand({4 (x + y), 2 (x + y) -&gt; 4 (x + y)})
 * {4*x+4*y,2*(x+y)-&gt;4*(x+y)}
 * </pre>
 * <p>
 * <code>Expand</code> does not change any other expression.<br />
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Expand(Sin(x*(1 + y)))
 * Sin(x*(1+y))
 *
 * &gt;&gt; a*(b*(c+d)+e) // Expand
 * a*b*c+a*b*d+a*e
 *
 * &gt;&gt; (y^2)^(1/2)/(2x+2y)//Expand
 * Sqrt(y^2)/(2*x+2*y)
 *
 * &gt;&gt; 2(3+2x)^2/(5+x^2+3x)^3 // Expand
 * 18/(5+3*x+x^2)^3+(24*x)/(5+3*x+x^2)^3+(8*x^2)/(5+3*x+x^2)^3
 * </pre>
 */
public class Expand extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        if (ast.arg1().isAST()) {
            IAST arg1 = (IAST) ast.arg1();
            if (arg1.isList()) {
                return arg1.mapThread(F.ListAlloc(arg1.size()), ast, 1);
            }
            IExpr patt = null;
            if (ast.size() > 2) {
                patt = ast.arg2();
            }
            return expand(arg1, patt, false, true).orElse(arg1);
        }

        return ast.arg1();
    }

    public static class Expander {

        boolean expandNegativePowers;

        boolean distributePlus;
        /**
         * Pattern which may be <code>null</code>
         */
        IExpr pattern;

        public Expander(IExpr pattern, boolean expandNegativePowers, boolean distributePlus) {
            this.pattern = pattern;
            this.expandNegativePowers = expandNegativePowers;
            this.distributePlus = distributePlus;
        }

        /**
         * Check if the given expression doesn't contain the pattern.
         *
         * @param expression
         * @return
         */
        public boolean isPatternFree(IExpr expression) {
            return (pattern != null && expression.isFree(pattern, false));
        }

        public IExpr expandAST(final IAST ast) {
            if (isPatternFree(ast)) {
                return F.NIL;
            }
            if (ast.isExpanded() && expandNegativePowers && !distributePlus) {
                return F.NIL;
            }
            if (ast.isPower()) {
                return expandPowerNull(ast);
            } else if (ast.isTimes()) {
                // (a+b)*(c+d)...

                IExpr[] temp = fractionalPartsTimesPower(ast, false, false, false, true);
                IExpr tempExpr;
                if (temp == null) {
                    return expandTimes(ast);
                }
                if (temp[0].isOne()) {
                    if (temp[1].isTimes()) {
                        tempExpr = expandTimes((IAST) temp[1]);
                        if (tempExpr.isPresent()) {
                            return PowerOp.power(tempExpr, F.CN1);
                        }
                        addExpanded(ast);
                        return F.NIL;
                    }
                    if (temp[1].isPower() || temp[1].isPlus()) {
                        IExpr denom = expandAST((IAST) temp[1]);
                        if (denom.isPresent()) {
                            return PowerOp.power(denom, F.CN1);
                        }
                    }
                    addExpanded(ast);
                    return F.NIL;
                }

                if (temp[1].isOne()) {
                    return expandTimes(ast);
                }

                boolean evaled = false;
                if (temp[0].isTimes()) {
                    tempExpr = expandTimes((IAST) temp[0]);
                    if (tempExpr.isPresent()) {
                        temp[0] = tempExpr;
                        evaled = true;
                    }
                }
                if (expandNegativePowers) {
                    if (temp[1].isTimes()) {
                        tempExpr = expandTimes((IAST) temp[1]);
                        if (tempExpr.isPresent()) {
                            temp[1] = tempExpr;
                            evaled = true;
                        }
                    } else {
                        if (temp[1].isPower() || temp[1].isPlus()) {
                            IExpr denom = expandAST((IAST) temp[1]);
                            if (denom.isPresent()) {
                                temp[1] = denom;
                                evaled = true;
                            }
                        }
                    }

                }
                IExpr powerAST = PowerOp.power(temp[1], F.CN1);
                if (distributePlus && temp[0].isPlus()) {
                    return addExpanded(PlusOp.plus(((IAST) temp[0]).mapThread(F.Times(null, powerAST), 1)));
                }
                if (evaled) {
                    return addExpanded(TimesOp.times(temp[0], powerAST));
                }
                addExpanded(ast);
                return F.NIL;
            } else if (ast.isPlus()) {
                return expandPlus(ast);
            }
            addExpanded(ast);
            return F.NIL;
        }

        private IExpr addExpanded(@Nonnull IExpr expr) {
            if (expandNegativePowers && !distributePlus && expr.isAST()) {
                ((IAST) expr).addEvalFlags(IAST.IS_EXPANDED);
            }
            return expr;
        }

        /**
         * @param ast
         * @return <code>F.NIL</code> if no evaluation is possible
         */
        public IExpr expandPlus(final IAST ast) {
            IAST result = F.NIL;
            for (int i = 1; i < ast.size(); i++) {
                final IExpr arg = ast.get(i);
                if (arg.isAST()) {
                    IExpr temp = expand((IAST) arg, pattern, expandNegativePowers, false);
                    if (temp.isPresent()) {
                        if (!result.isPresent()) {
                            result = ast.copyUntil(ast.size(), i);
                        }
                        result.append(temp);
                        continue;
                    }
                }
                if (result.isPresent()) {
                    result.append(arg);
                }
            }
            if (result.isPresent()) {
                return PlusOp.plus(result);
            }
            addExpanded(ast);
            return F.NIL;
        }

        /**
         * Expand <code>(a+b)^i</code> with <code>i</code> an integer number in the
         * range Integer.MIN_VALUE to Integer.MAX_VALUE.
         *
         * @param powerAST
         * @return
         */
        public IExpr expandPowerNull(final IAST powerAST) {
            IExpr base = powerAST.arg1();
            if ((base.isPlus())) {
                IExpr exponent = powerAST.arg2();
                if (exponent.isFraction()) {
                    IFraction fraction = (IFraction) exponent;
                    if (fraction.isPositive()) {
                        INumber floorPart = fraction.floorFraction().normalize();
                        if (!floorPart.isZero()) {
                            IFraction fractionalPart = fraction.fractionalPart();
                            return expandAST(F.Times(F.Power(base, fractionalPart), F.Power(base, floorPart)));
                        }
                    }
                }

                try {
                    int exp = Validate.checkPowerExponent(powerAST);
                    IAST plusAST = (IAST) base;
                    if (exp < 0) {
                        if (expandNegativePowers) {
                            exp *= (-1);
                            return PowerOp.power(expandPower(plusAST, exp), F.CN1);
                        }
                        addExpanded(powerAST);
                        return F.NIL;
                    }
                    return expandPower(plusAST, exp);

                } catch (WrongArgumentType e) {
                    addExpanded(powerAST);
                    return F.NIL;
                }
            }
            addExpanded(powerAST);
            return F.NIL;
        }

        /**
         * Expand a polynomial power with the multinomial theorem. See
         * <a href= "http://en.wikipedia.org/wiki/Multinomial_theorem">Wikipedia -
         * Multinomial theorem</a>
         *
         * @param plusAST
         * @param n       <code>n &ge; 0</code>
         * @return
         */
        private IExpr expandPower(final IAST plusAST, final int n) {
            if (n == 1) {
                IExpr temp = expandPlus(plusAST);
                if (temp.isPresent()) {
                    return temp;
                }
                addExpanded(plusAST);
                return plusAST;
            }
            if (n == 0) {
                return F.C1;
            }

            int k = plusAST.size() - 1;
            long numberOfTerms = LongMath.binomial(n + k - 1, n);
            if (numberOfTerms > Integer.MAX_VALUE) {
                throw new ArithmeticException("");
            }
            final IAST expandedResult = F.ast(F.Plus, (int) numberOfTerms, false);
            Expand.NumberPartititon part = new Expand.NumberPartititon(plusAST, n, expandedResult);
            part.partition();
            return PlusOp.plus(expandedResult);
        }

        private IExpr expandTimes(final IAST timesAST) {
            IExpr result = timesAST.arg1();

            IExpr temp;
            boolean evaled = false;
            if (result.isPower()) {
                temp = expandPowerNull((IAST) result);
                if (temp.isPresent()) {
                    result = temp;
                    evaled = true;
                }

            }

            for (int i = 2; i < timesAST.size(); i++) {
                temp = timesAST.get(i);
                if (temp.isPower()) {
                    temp = expandPowerNull((IAST) temp);
                    if (!temp.isPresent()) {
                        temp = timesAST.get(i);
                    } else {
                        evaled = true;
                    }
                }
                result = expandTimesBinary(result, temp);
            }
            if (evaled == false && timesAST.equals(result)) {
                addExpanded(timesAST);
                return F.NIL;
            }
            return result;
        }

        private IExpr expandTimesBinary(final IExpr expr0, final IExpr expr1) {
            if (expr0.isPlus()) {
                if (!expr1.isPlus()) {
                    return expandExprTimesPlus(expr1, (IAST) expr0);
                }
                // assure Plus(...)
                final IAST ast1 = expr1.isPlus() ? (IAST) expr1 : F.Plus(expr1);
                return expandPlusTimesPlus((IAST) expr0, ast1);
            }
            if (expr1.isPlus()) {
                return expandExprTimesPlus(expr0, (IAST) expr1);
            }
            return TimesOp.times(expr0, expr1);
        }

        /**
         * <code>(a+b)*(c+d) -> a*c+a*d+b*c+b*d</code>
         *
         * @param plusAST0
         * @param plusAST1
         * @return
         */
        private IExpr expandPlusTimesPlus(final IAST plusAST0, final IAST plusAST1) {
            long numberOfTerms = (long) (plusAST0.size() - 1) * (long) (plusAST1.size() - 1);
            if (numberOfTerms > Integer.MAX_VALUE) {
                throw new ArithmeticException("");
            }
            final IAST result = F.ast(F.Plus, (int) numberOfTerms, false);
            for (int i = 1; i < plusAST0.size(); i++) {
                for (int j = 1; j < plusAST1.size(); j++) {
                    // evaluate to flatten out Times() exprs
                    evalAndExpandAST(plusAST0.get(i), plusAST1.get(j), result);
                }
            }
            return PlusOp.plus(result);
        }

        /**
         * <code>expr*(a+b+c) -> expr*a+expr*b+expr*c</code>
         *
         * @param expr1
         * @param plusAST
         * @return
         */
        private IExpr expandExprTimesPlus(final IExpr expr1, final IAST plusAST) {
            final IAST result = F.ast(F.Plus, plusAST.size() - 1, false);
            for (int i = 1; i < plusAST.size(); i++) {
                // evaluate to flatten out Times() exprs
                evalAndExpandAST(expr1, plusAST.get(i), result);
            }
            return PlusOp.plus(result);
        }

        /**
         * Evaluate <code>expr1 * expr2</code> and expand the resulting expression, if
         * it's an <code>IAST</code>. After that add the resulting expression to the
         * <code>PlusOp</code>
         *
         * @param result
         * @param expr
         */
        public void evalAndExpandAST(IExpr expr1, IExpr expr2, final IAST result) {
            IExpr arg = TimesOp.times(expr1, expr2);
            if (arg.isAST()) {
                result.appendPlus(expandAST((IAST) arg).orElse(arg));
                return;
            }
            result.append(arg);
        }

    }

    public static class NumberPartititon {
        IAST expandedResult;
        int m;
        int n;
        int[] parts;
        IAST precalculatedPowerASTs;

        public NumberPartititon(IAST plusAST, int n, IAST expandedResult) {

            this.expandedResult = expandedResult;
            this.n = n;
            this.m = plusAST.size() - 1;
            this.parts = new int[m];
            // precalculate all Power[] ASTs:
            this.precalculatedPowerASTs = F.ListAlloc(plusAST.size());
            for (IExpr expr : plusAST) {
                precalculatedPowerASTs.append(expr);
            }
        }

        private void addFactor(int[] j) {
            final Combinatoric.Permutations.KPermutationsIterable perm = new Combinatoric.Permutations.KPermutationsIterable(j, m, m);
            IInteger multinomial = NumberTheory.multinomial(j, n);
            final IAST times = F.Times();
            IExpr temp;
            for (int[] indices : perm) {
                final IAST timesAST = times.clone();
                if (!multinomial.isOne()) {
                    timesAST.append(multinomial);
                }
                for (int k = 0; k < m; k++) {
                    if (indices[k] != 0) {
                        temp = precalculatedPowerASTs.get(k + 1);
                        if (indices[k] == 1) {
                            timesAST.append(temp);
                        } else {
                            if (temp.isTimes()) {
                                IAST ast = (IAST) temp;
                                for (int i = 1; i < ast.size(); i++) {
                                    timesAST.append(PowerOp.power(ast.get(i), F.integer(indices[k])));
                                }
                            } else {
                                timesAST.append(PowerOp.power(temp, F.integer(indices[k])));
                            }
                        }

                    }
                }
                expandedResult.append(TimesOp.times(timesAST));
            }
        }

        public void partition() {
            partition(n, n, 0);
        }

        private void partition(int n, int max, int currentIndex) {
            if (n == 0) {
                addFactor(parts);
                return;
            }
            if (currentIndex >= m) {
                return;
            }
            int old;
            old = parts[currentIndex];
            int min = Math.min(max, n);

            for (int i = min; i >= 1; i--) {
                parts[currentIndex] = i;
                partition(n - i, i, currentIndex + 1);
            }
            parts[currentIndex] = old;
        }
    }
}