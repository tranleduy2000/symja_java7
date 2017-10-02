package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.AppellF1;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D3;
import static org.matheclipse.core.expression.F.C1D4;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN1D4;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.EllipticE;
import static org.matheclipse.core.expression.F.EllipticF;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.GreaterEqual;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.LessEqual;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialDegree;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PolynomialDivide;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplerSqrtQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules6 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(a, x, Power(c, -1)), Times(Plus(Times(b, c), Times(CN1, a, d)), Power(c, -1), Int(Power(Plus(d, Times(c, Power(x, Negate(n)))), -1), x))), And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(n)), Less(n, C0)))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(b, x, Power(d, -1)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(d, -1), Int(Power(Plus(c, Times(d, Power(x, n))), -1), x))), And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(And(RationalQ(n), Less(n, C0)))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(a, x, Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(c, -1)), And(And(And(FreeQ(List(a, b, c, d, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))))), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(a, Times(b, Power(x, n))), Power(Plus(c, Times(d, Power(x, n))), p)), x), x), And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))))), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, c), Times(CN1, a, d)), x, Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Times(c, d, n, Plus(p, C1)), -1)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))), Power(Times(c, d, n, Plus(p, C1)), -1), Int(Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), x))), And(And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(b, x, Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Times(d, Plus(Times(n, Plus(p, C1)), C1)), -1)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))), Power(Times(d, Plus(Times(n, Plus(p, C1)), C1)), -1), Int(Power(Plus(c, Times(d, Power(x, n))), p), x))), And(And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d, Plus(Times(n, Plus(p, C1)), C1))))), Not(PositiveIntegerQ(p))), Not(And(RationalQ(p), Less(p, CN1)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Plus(c_, Times(d_DEFAULT, Power(x_, n_)))), -1), x_Symbol),
                    Condition(Plus(Times(b, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Plus(a, Times(b, Power(x, n))), -1), x)), Times(CN1, d, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Plus(c, Times(d, Power(x, n))), -1), x))), And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Power(x_, n_)))), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(b, Power(d, -1), Int(Power(Plus(a, Times(b, Power(x, n))), CN1D2), x)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(d, -1), Int(Power(Times(Sqrt(Plus(a, Times(b, Power(x, n)))), Plus(c, Times(d, Power(x, n)))), -1), x))), And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Sqr(x_))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Subst(Int(Power(Simp(Plus(a, Times(Plus(Times(b, c), Times(CN1, a, d)), Sqr(x))), x), -1), x), x, Times(x, Power(Plus(c, Times(d, Sqr(x))), CN1D2))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Times(Power(Times(Sqrt(a), Sqrt(c), Rt(Times(CN1, d, Power(c, -1)), C2)), -1), EllipticF(ArcSin(Times(Rt(Times(CN1, d, Power(c, -1)), C2), x)), Times(b, c, Power(Times(a, d), -1)))), And(And(And(FreeQ(List(a, b, c, d), x), PositiveQ(a)), PositiveQ(c)), Not(SimplerSqrtQ(Times(CN1, b, Power(a, -1)), Times(CN1, d, Power(c, -1))))))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(a, Times(b, Sqr(x))), Power(a, -1))), Sqrt(Times(Plus(c, Times(d, Sqr(x))), Power(c, -1))), Power(Times(Sqrt(Plus(a, Times(b, Sqr(x)))), Sqrt(Plus(c, Times(d, Sqr(x))))), -1), Int(Power(Times(Sqrt(Plus(C1, Times(b, Power(a, -1), Sqr(x)))), Sqrt(Plus(C1, Times(d, Power(c, -1), Sqr(x))))), -1), x)), And(FreeQ(List(a, b, c, d), x), Not(And(PositiveQ(a), PositiveQ(c)))))),
            ISetDelayed(Int(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)))), Power(Plus(c_, Times(d_DEFAULT, Sqr(x_))), CN1D2)), x_Symbol),
                    Condition(Times(Sqrt(a), Power(Times(Sqrt(c), Rt(Times(CN1, d, Power(c, -1)), C2)), -1), EllipticE(ArcSin(Times(Rt(Times(CN1, d, Power(c, -1)), C2), x)), Times(b, c, Power(Times(a, d), -1)))), And(And(FreeQ(List(a, b, c, d), x), PositiveQ(a)), PositiveQ(c)))),
            ISetDelayed(Int(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)))), Power(Plus(c_, Times(d_DEFAULT, Sqr(x_))), CN1D2)), x_Symbol),
                    Condition(Times(Sqrt(Plus(a, Times(b, Sqr(x)))), Sqrt(Times(Plus(c, Times(d, Sqr(x))), Power(c, -1))), Power(Times(Sqrt(Plus(c, Times(d, Sqr(x)))), Sqrt(Times(Plus(a, Times(b, Sqr(x))), Power(a, -1)))), -1), Int(Times(Sqrt(Plus(C1, Times(b, Power(a, -1), Sqr(x)))), Power(Plus(C1, Times(d, Power(c, -1), Sqr(x))), CN1D2)), x)), And(FreeQ(List(a, b, c, d), x), Not(And(PositiveQ(a), PositiveQ(c)))))),
            ISetDelayed(Int(Times(Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), x_Symbol),
                    Condition(Plus(Times(x, Sqrt(Plus(a, Times(b, Sqr(x)))), C1D3, Sqrt(Plus(c, Times(d, Sqr(x))))), Times(C1D3, Int(Times(Plus(Times(C2, a, c), Times(Plus(Times(b, c), Times(a, d)), Sqr(x))), Power(Times(Sqrt(Plus(a, Times(b, Sqr(x)))), Sqrt(Plus(c, Times(d, Sqr(x))))), -1)), x))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), Sqrt(Plus(c_, Times(d_DEFAULT, Power(x_, 4))))), -1), x_Symbol),
                    Condition(Plus(Times(Power(Times(C2, a), -1), Int(Power(Times(Plus(C1, Times(CN1, Rt(Times(CN1, b, Power(a, -1)), C2), Sqr(x))), Sqrt(Plus(c, Times(d, Power(x, 4))))), -1), x)), Times(Power(Times(C2, a), -1), Int(Power(Times(Plus(C1, Times(Rt(Times(CN1, b, Power(a, -1)), C2), Sqr(x))), Sqrt(Plus(c, Times(d, Power(x, 4))))), -1), x))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Power(Times(Plus(c_, Times(d_DEFAULT, Power(x_, 4))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), C1D4)), -1), x_Symbol),
                    Condition(Subst(Int(Power(Plus(c, Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(x, 4))), -1), x), x, Times(x, Power(Plus(a, Times(b, Power(x, 4))), CN1D4))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(PolynomialDivide(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), Negate(p)), x), x), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PositiveIntegerQ(n, m, Negate(p))), GreaterEqual(m, Negate(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(a, d), Times(CN1, c, b)), x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C1))), Power(Times(a, b, n, Plus(m, C1)), -1)), Times(CN1, Power(Times(a, b, n, Plus(m, C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(a, d), Times(CN1, c, b, Plus(Times(n, Plus(m, C1)), C1)))), Times(d, Plus(Times(a, d, Plus(Times(n, Plus(p, Negate(C1))), C1)), Times(CN1, b, c, Plus(Times(n, Plus(m, p)), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C2)))), x))), And(And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, p)), Less(m, CN1)), Greater(p, C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), p), Power(Times(a, n, Plus(m, C1)), -1)), Times(Power(Times(a, n, Plus(m, C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(n, Plus(m, C1)), C1)), Times(d, Plus(Times(n, Plus(m, p, C1)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, p)), Less(m, CN1)), And(Less(C0, p), LessEqual(p, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(CN1, b, x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Times(a, n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), -1)), And(And(And(And(And(FreeQ(List(a, b, c, d, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Times(n, Plus(m, p, C2)), C1))), ZeroQ(Plus(Times(b, c), Times(n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, b, x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Times(a, n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), -1)), Times(Plus(Times(b, c), Times(n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)))), Power(Times(a, n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), p)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Times(n, Plus(m, p, C2)), C1))), NonzeroQ(Plus(Times(b, c), Times(n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, b, x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Times(a, n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), -1)), Times(Power(Times(a, n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(Simp(Plus(Times(b, c), Times(n, Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d))), Times(d, b, Plus(Times(n, Plus(m, p, C2)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), p)), x))), And(And(And(And(FreeQ(List(a, b, c, d, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m)), Less(m, CN1)), NonzeroQ(Plus(Times(n, Plus(m, p, C2)), C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p)), x), x), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PositiveIntegerQ(n)), IntegersQ(m, p)), Greater(Plus(m, p), C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(d, x, Power(Plus(a, Times(b, Power(x, n))), Plus(m, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C1))), Power(Times(b, Plus(Times(n, Plus(m, p)), C1)), -1)), Times(Power(Times(b, Plus(Times(n, Plus(m, p)), C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(b, c, Plus(Times(n, Plus(m, p)), C1)), Times(CN1, a, d))), Times(d, Plus(Times(b, c, Plus(Times(n, Plus(m, Times(C2, p), Negate(C1))), C1)), Times(CN1, a, d, Plus(Times(n, Plus(p, Negate(C1))), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C2)))), x))), And(And(And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C1)), NonzeroQ(Plus(Times(n, Plus(m, p)), C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(c_, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(x, Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p), Power(Times(Power(Plus(C1, Times(b, Power(x, n), Power(a, -1))), m), Power(Plus(C1, Times(d, Power(x, n), Power(c, -1))), p)), -1), AppellF1(Power(n, -1), Negate(m), Negate(p), Plus(C1, Power(n, -1)), Times(CN1, b, Power(x, n), Power(a, -1)), Times(CN1, d, Power(x, n), Power(c, -1)))), And(And(FreeQ(List(a, b, c, d, m, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(n, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(u_, n_))), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(v_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p)), x), x, u)), And(And(And(FreeQ(List(a, b, c, d, m, n, p), x), ZeroQ(Plus(u, Negate(v)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p)), x), And(And(And(FreeQ(List(m, p), x), BinomialQ(List(u, v), x)), ZeroQ(Plus(BinomialDegree(u, x), Negate(BinomialDegree(v, x))))), Not(BinomialMatchQ(List(u, v), x)))))
    );
}
