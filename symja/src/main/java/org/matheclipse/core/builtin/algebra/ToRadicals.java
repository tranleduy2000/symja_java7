package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.structure.Structure;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.ExprRingFactory;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.polynomials.ExprMonomial;
import org.matheclipse.core.polynomials.ExprPolynomial;
import org.matheclipse.core.polynomials.ExprPolynomialRing;
import org.matheclipse.core.visit.VisitorExpr;

import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.Floor;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Times;

public class ToRadicals extends AbstractFunctionEvaluator {

    /**
     * Root of a polynomial: <code>a + b*Slot1</code>.
     *
     * @param a       coefficient a of the polynomial
     * @param b       coefficient b of the polynomial
     * @param nthRoot <code>1 <= nthRoot <= 3</code> otherwise return F.NIL;
     * @return
     */
    private static IExpr root1(IExpr a, IExpr b, int nthRoot) {
        if (nthRoot != 1) {
            return F.NIL;
        }
        return Times(F.CN1, a, Power(b, -1));
    }

    /**
     * Root of a polynomial: <code>a + b*Slot1 + c*Slot1^2</code>.
     *
     * @param a       coefficient a of the polynomial
     * @param b       coefficient b of the polynomial
     * @param c       coefficient c of the polynomial
     * @param nthRoot <code>1 <= nthRoot <= 3</code> otherwise return F.NIL;
     * @return
     */
    private static IExpr root2(IExpr a, IExpr b, IExpr c, int nthRoot) {
        if (nthRoot < 1 || nthRoot > 3) {
            return F.NIL;
        }
        IExpr k = F.ZZ(nthRoot);
        return Plus(Times(C1D2, Power(F.CN1, k), F.Sqrt(Times(Plus(F.Sqr(b), Times(F.CN4, a, c)), Power(c, -2)))),
                Times(F.CN1D2, b, Power(c, -1)));
    }

    /**
     * Root of a polynomial: <code>a + b*Slot1 + c*Slot1^2 + d*Slot1^3</code>.
     *
     * @param a       coefficient a of the polynomial
     * @param b       coefficient b of the polynomial
     * @param c       coefficient c of the polynomial
     * @param d       coefficient d of the polynomial
     * @param nthRoot <code>1 <= nthRoot <= 3</code> otherwise return F.NIL;
     * @return
     */
    private static IExpr root3(IExpr a, IExpr b, IExpr c, IExpr d, int nthRoot) {
        if (nthRoot < 1 || nthRoot > 3) {
            return F.NIL;
        }
        IExpr k = F.ZZ(nthRoot);

        // r = 3*b*d - c^2
        IExpr r = Plus(Negate(F.Sqr(c)), Times(F.C3, b, d));
        // q = 9*b*c*d - 2*c^3 - 27*a*d^2
        IExpr q = Plus(Times(F.CN2, Power(c, 3)), Times(F.C9, b, c, d), Times(F.ZZ(-27L), a, F.Sqr(d)));
        // p = (q + Sqrt(q^2 + 4 r^3))^(1/3)
        IExpr p = Power(Plus(q, F.Sqrt(Plus(F.Sqr(q), Times(F.C4, Power(r, 3))))), F.C1D3);
        // -(c/(3*d)) + (E^((2*I*Pi*(k - 1))/3)*p)/(3*2^(1/3)*d) -
        // (2^(1/3)*r)/(E^((2*I*Pi*(k - 1))/3)*(3*p*d))
        return Plus(Times(F.CN1D3, c, Power(d, -1)),
                Times(F.CN1D3, Power(F.E, Times(F.CC(0L, 1L, -2L, 3L), Plus(F.CN1, k), F.Pi)), Power(p, -1), r,
                        Power(C2, F.C1D3), Power(d, -1)),
                Times(F.C1D3, Power(C2, F.CN1D3), Power(F.E, Times(F.CC(0L, 1L, 2L, 3L), Plus(F.CN1, k), F.Pi)),
                        Power(d, -1), p));
    }

    /**
     * Root of a polynomial
     * <code>a + b*Slot1 + c*Slot1^2 + d*Slot1^3 + e*Slot1^4</code>
     *
     * @param a
     * @param b
     * @param c
     * @param d
     * @param e
     * @param nthRoot <code>1 <= nthRoot <= 4</code> otherwise return F.NIL;
     * @return
     */
    private static IExpr root4(IExpr a, IExpr b, IExpr c, IExpr d, IExpr e, int nthRoot) {
        if (nthRoot < 1 || nthRoot > 4) {
            return F.NIL;
        }
        IExpr k = F.ZZ(nthRoot);

        // t = Sqrt(-4*(c^2 - 3*b*d + 12*a*e)^3 + (2*c^3 - 9*c*(b*d + 8*a*e) + 27*(a*d^2
        // + b^2*e))^2)
        IExpr t = F.Sqrt(Plus(Times(F.CN4, Power(Plus(F.Sqr(c), Times(F.CN3, b, d), Times(F.ZZ(12L), a, e)), 3)),
                F.Sqr(Plus(Times(F.CN9, c, Plus(Times(b, d), Times(F.C8, a, e))),
                        Times(F.ZZ(27L), Plus(Times(a, F.Sqr(d)), Times(F.Sqr(b), e))), Times(C2, Power(c, 3))))));
        // s = (t + 2*c^3 - 9*c*(b*d + 8*a*e) + 27*(a*d^2 + b^2*e))^(1/3)
        IExpr s = Power(Plus(Times(C2, Power(c, 3)), t, Times(F.CN9, c, Plus(Times(b, d), Times(F.C8, a, e))),
                Times(F.ZZ(27L), Plus(Times(a, F.Sqr(d)), Times(F.Sqr(b), e)))), F.C1D3);

        // eps1 = (1/2)*Sqrt((2^(1/3)*(c^2 - 3*b*d + 12*a*e))/ (3*s*e) + (3*d^2 +
        // 2*2^(2/3)*s*e - 8*c*e)/ (12 e^2))
        IExpr eps1 = Times(C1D2,
                F.Sqrt(Plus(
                        Times(F.QQ(1L, 12L),
                                Plus(Times(F.C3, F.Sqr(d)), Times(F.CN8, c, e),
                                        Times(C2, e, s, Power(C2, F.QQ(2L, 3L)))),
                                Power(e, -2)),
                        Times(F.C1D3, Plus(F.Sqr(c), Times(F.CN3, b, d), Times(F.ZZ(12L), a, e)), Power(C2, F.C1D3),
                                Power(e, -1), Power(s, -1)))));

        // u = -((2^(1/3)*s^2 + 2*c^2 - 6*b*d + 24*a*e)/ (2^(2/3)*s*e)) + 8*eps1^2
        IExpr u = Plus(Times(F.C8, F.Sqr(eps1)),
                Times(F.CN1,
                        Plus(Times(C2, F.Sqr(c)), Times(F.CN6, b, d), Times(F.ZZ(24L), a, e),
                                Times(Power(C2, F.C1D3), F.Sqr(s))),
                        Power(C2, F.QQ(-2L, 3L)), Power(e, -1), Power(s, -1)));

        // v = (d^3 - 4*c*d*e + 8*b*e^2)/ (8*e^3*eps1)
        IExpr v = Times(F.QQ(1L, 8L), Plus(Power(d, 3), Times(F.CN4, c, d, e), Times(F.C8, b, F.Sqr(e))),
                Power(e, -3), Power(eps1, -1));

        // eps2 = (1/2)*Sqrt(u + v)
        IExpr eps2 = Times(C1D2, F.Sqrt(Plus(u, v)));

        // eps3 = (1/2)*Sqrt(u - v)

        IExpr eps3 = Times(C1D2, F.Sqrt(Plus(u, Negate(v))));

        // -(d/(4*e)) + (2*Floor((k - 1)/2) - 1)*eps1 + (-1)^k*(1 - UnitStep(k -
        // 3))*eps2 - (-1)^k*(UnitStep(2 - k)
        // - 1)*eps3
        return Plus(Times(eps1, Plus(F.CN1, Times(C2, Floor(Times(C1D2, Plus(F.CN1, k)))))),
                Times(eps2, Plus(F.C1, Negate(F.UnitStep(Plus(F.CN3, k)))), Power(F.CN1, k)),
                Times(eps3, Plus(F.CN1, F.UnitStep(Plus(C2, Negate(k)))), Power(F.CN1, Plus(F.C1, k))),
                Times(F.CN1D4, d, Power(e, -1)));

    }

    public static IExpr rootToRadicals(final IAST ast, int maxDegree) {
        if (ast.size() == 3 && ast.arg2().isInteger()) {
            IExpr expr = ast.arg1();
            if (expr.isFunction()) {
                expr = ((IAST) expr).arg1();
                try {
                    int k = ((IInteger) ast.arg2()).toInt();
                    final IAST variables = F.List(F.Slot1);
                    ExprPolynomialRing ring = new ExprPolynomialRing(ExprRingFactory.CONST, variables);
                    ExprPolynomial polynomial = ring.create(expr, false, true);

                    long varDegree = polynomial.degree(0);
                    if (polynomial.isConstant()) {
                        return F.CEmptyList;
                    }
                    IExpr a;
                    IExpr b;
                    IExpr c;
                    IExpr d;
                    IExpr e;
                    if (varDegree >= 1 && varDegree <= maxDegree) {
                        a = C0;
                        b = C0;
                        c = C0;
                        d = C0;
                        e = C0;
                        for (ExprMonomial monomial : polynomial) {
                            IExpr coeff = monomial.coefficient();
                            long lExp = monomial.exponent().getVal(0);
                            if (lExp == 4) {
                                e = coeff;
                            } else if (lExp == 3) {
                                d = coeff;
                            } else if (lExp == 2) {
                                c = coeff;
                            } else if (lExp == 1) {
                                b = coeff;
                            } else if (lExp == 0) {
                                a = coeff;
                            } else {
                                throw new ArithmeticException("Root::Unexpected exponent value: " + lExp);
                            }
                        }
                        if (varDegree == 1) {
                            return root1(a, b, k);
                        } else if (varDegree == 2) {
                            return root2(a, b, c, k);
                        } else if (varDegree == 3) {
                            return root3(a, b, c, d, k);
                        } else if (varDegree == 4) {
                            return root4(a, b, c, d, e, k);
                        }
                    }
                } catch (JASConversionException e2) {
                    if (Config.SHOW_STACKTRACE) {
                        e2.printStackTrace();
                    }
                }
            }
        }
        return F.NIL;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.size() >= 2) {
            IExpr arg1 = ast.arg1();
            IExpr temp = Structure.threadLogicEquationOperators(arg1, ast, 1);
            if (temp.isPresent()) {
                return temp;
            }
            if (arg1.isAST()) {
                ToRadicalsVisitor visitor = new ToRadicalsVisitor(ast);
                temp = ((IAST) arg1).accept(visitor);
                if (temp.isPresent()) {
                    return temp;
                }
                temp = rootToRadicals((IAST) arg1, 4);
                if (temp.isPresent()) {
                    return temp;
                }
            }
            return arg1;
        }
        return F.NIL;
    }

    public class ToRadicalsVisitor extends VisitorExpr {
        IAST replacement;

        private ToRadicalsVisitor(IAST replacement) {
            this.replacement = replacement;
        }

        // D[a_+b_+c_,x_] -> D[a,x]+D[b,x]+D[c,x]
        // return listArg1.mapThread(F.D(F.Null, x), 1);
        @Override
        public IExpr visit(IAST ast) {
            if (!ast.isAST(F.Root)) {
                IAST cloned = replacement.setAtClone(1, null);
                return ast.mapThread(cloned, 1);
            }
            return F.NIL;
        }

    }

}
