package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.EllipticPi;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Integrate;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Set;
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
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.q_;
import static org.matheclipse.core.expression.F.q_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.w;
import static org.matheclipse.core.expression.F.w_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialDegree;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplerSqrtQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules8 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_)))), -1)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Plus(a, Times(b, Power(x, n))), -1), x)), Times(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Plus(c, Times(d, Power(x, n))), -1), x))), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))))), -1)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(b, -1), Int(Power(Plus(c, Times(d, Power(x, n))), CN1D2), x)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(b, -1), Int(Power(Times(Plus(a, Times(b, Power(x, n))), Sqrt(Plus(c, Times(d, Power(x, n))))), -1), x))), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Times(Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_)))), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))))), -1)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(b, -1), Int(Times(Sqrt(Plus(a, Times(b, Power(x, n)))), Power(Plus(c, Times(d, Power(x, n))), CN1D2)), x)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(b, -1), Int(Power(Times(Sqrt(Plus(a, Times(b, Power(x, n)))), Sqrt(Plus(c, Times(d, Power(x, n))))), -1), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(And(Equal(n, C2), SimplerSqrtQ(Times(CN1, b, Power(a, -1)), Times(CN1, d, Power(c, -1)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), x, Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q), Power(Times(a, b, n, Plus(p, C1)), -1)), Times(Power(Times(a, b, n, Plus(p, C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(ASymbol, b, n, Plus(p, C1)), Times(ASymbol, b), Times(CN1, a, BSymbol))), Times(d, Plus(Times(ASymbol, b, n, Plus(p, C1)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(Times(n, q), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, Negate(C1)))), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p, q)), Less(p, CN1)), Greater(q, C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(BSymbol, x, Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q), Power(Times(b, Plus(Times(n, Plus(p, q, C1)), C1)), -1)), Times(Power(Times(b, Plus(Times(n, Plus(p, q, C1)), C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol), Times(ASymbol, b, n, Plus(p, q, C1)))), Times(Plus(Times(d, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol))), Times(BSymbol, n, q, Plus(Times(b, c), Times(CN1, a, d))), Times(ASymbol, b, d, n, Plus(p, q, C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), Plus(q, Negate(C1)))), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n, p), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(q)), Greater(q, C0)), NonzeroQ(Plus(Times(n, Plus(p, q, C1)), C1))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), x, Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, C1)), Power(Times(a, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1)), Times(Power(Times(a, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol))), Times(ASymbol, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), Times(d, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(Times(n, Plus(p, q, C2)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Times(Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x)), Times(BSymbol, Int(Times(Power(x, n), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n, p, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(d, Power(b, -1), Int(Times(Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C1))), Power(Plus(e, Times(f, Power(x, n))), q)), x)), Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1), Int(Times(Power(Plus(c, Times(d, Power(x, n))), Plus(p, Negate(C1))), Power(Plus(e, Times(f, Power(x, n))), q), Power(Plus(a, Times(b, Power(x, n))), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, n, q), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, Sqr(x_))), Plus(c_DEFAULT, Times(d_DEFAULT, Sqr(x_))), Sqrt(Plus(e_DEFAULT, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(b, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Times(Plus(a, Times(b, Sqr(x))), Sqrt(Plus(e, Times(f, Sqr(x))))), -1), x)), Times(CN1, d, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Power(Times(Plus(c, Times(d, Sqr(x))), Sqrt(Plus(e, Times(f, Sqr(x))))), -1), x))), And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))))),
            ISetDelayed(Int(Power(Times(Plus(c_DEFAULT, Times(d_DEFAULT, Sqr(x_))), Sqr(x_), Sqrt(Plus(e_DEFAULT, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(Power(c, -1), Int(Power(Times(Sqr(x), Sqrt(Plus(e, Times(f, Sqr(x))))), -1), x)), Times(CN1, d, Power(c, -1), Int(Power(Times(Plus(c, Times(d, Sqr(x))), Sqrt(Plus(e, Times(f, Sqr(x))))), -1), x))), And(FreeQ(List(c, d, e, f), x), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Sqr(x_))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_)))), Sqrt(Plus(e_, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Times(Power(Times(a, Sqrt(c), Sqrt(e), Rt(Times(CN1, d, Power(c, -1)), C2)), -1), EllipticPi(Times(b, c, Power(Times(a, d), -1)), ArcSin(Times(Rt(Times(CN1, d, Power(c, -1)), C2), x)), Times(c, f, Power(Times(e, d), -1)))), And(And(And(FreeQ(List(a, b, c, d, e, f), x), PositiveQ(c)), PositiveQ(e)), Or(And(PosQ(Times(CN1, e, f)), Or(NegQ(Times(CN1, c, d)), Not(RationalQ(Rt(Times(CN1, c, d), C2))))), And(And(NegQ(Times(CN1, e, f)), NegQ(Times(CN1, c, d))), Not(RationalQ(Rt(Times(c, d), C2)))))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Sqr(x_))), Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_)))), Sqrt(Plus(e_, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(c, Times(d, Sqr(x))), Power(c, -1))), Sqrt(Times(Plus(e, Times(f, Sqr(x))), Power(e, -1))), Power(Times(Sqrt(Plus(c, Times(d, Sqr(x)))), Sqrt(Plus(e, Times(f, Sqr(x))))), -1), Int(Power(Times(Plus(a, Times(b, Sqr(x))), Sqrt(Plus(C1, Times(d, Power(c, -1), Sqr(x)))), Sqrt(Plus(C1, Times(f, Power(e, -1), Sqr(x))))), -1), x)), And(And(FreeQ(List(a, b, c, d, e, f), x), Not(And(PositiveQ(c), PositiveQ(e)))), Or(And(PosQ(Times(CN1, e, f)), Or(NegQ(Times(CN1, c, d)), Not(RationalQ(Rt(Times(CN1, c, d), C2))))), And(And(NegQ(Times(CN1, e, f)), NegQ(Times(CN1, c, d))), Not(RationalQ(Rt(Times(c, d), C2)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, d, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(Plus(c, Times(d, Power(x, n))), p), Power(Plus(e, Times(f, Power(x, n))), q)), x)), Times(b, Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(Plus(c, Times(d, Power(x, n))), Plus(p, C1)), Power(Plus(e, Times(f, Power(x, n))), q), Power(Plus(a, Times(b, Power(x, n))), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, n, q), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, Power(x_, n_))), q_)), x_Symbol),
                    Condition(Module(List(Set(u, ExpandIntegrand(Times(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p), Power(Plus(e, Times(f, Power(x, n))), q)), x))), Condition(Int(u, x), SumQ(u))), And(And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(e_, Times(f_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p), Power(Plus(e, Times(f, Power(x, n))), q)), x), FreeQ(List(a, b, c, d, e, f, m, n, p, q), x))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(u_, n_))), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(v_, n_))), p_DEFAULT), Power(Plus(e_, Times(f_DEFAULT, Power(w_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n))), m), Power(Plus(c, Times(d, Power(x, n))), p), Power(Plus(e, Times(f, Power(x, n))), q)), x), x, u)), And(And(And(And(FreeQ(List(a, b, c, d, e, f, m, n, p, q), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT), Power(w_, q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p), Power(ExpandToSum(w, x), q)), x), And(And(And(And(FreeQ(List(m, p, q), x), BinomialQ(List(u, v, w), x)), ZeroQ(Plus(BinomialDegree(u, x), Negate(BinomialDegree(v, x))))), ZeroQ(Plus(BinomialDegree(u, x), Negate(BinomialDegree(w, x))))), Not(BinomialMatchQ(List(u, v, w), x)))))
    );
}
