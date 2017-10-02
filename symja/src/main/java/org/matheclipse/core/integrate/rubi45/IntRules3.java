package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Integrate;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_DEFAULT;
import static org.matheclipse.core.expression.F.h;
import static org.matheclipse.core.expression.F.h_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
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
import static org.matheclipse.core.expression.F.z;
import static org.matheclipse.core.expression.F.z_;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumSimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules3 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Plus(ASymbol, Times(BSymbol, x))), x), x), And(And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, m), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), Or(IntegersQ(m, n, p), PositiveIntegerQ(n, p))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Plus(e, Times(f, x)), Plus(p, C1)), Power(Times(d, Plus(n, C1), Plus(Times(d, e), Times(CN1, c, f))), -1)), Times(Power(Times(d, Plus(n, C1), Plus(Times(d, e), Times(CN1, c, f))), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, Negate(C1))), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Plus(e, Times(f, x)), p), Simp(Plus(Times(a, d, Plus(Times(BSymbol, e), Times(CN1, ASymbol, f)), Plus(n, C1)), Times(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Plus(Times(b, e, m), Times(a, f, Plus(p, C1)))), Times(b, Plus(Times(d, Plus(Times(BSymbol, e), Times(CN1, ASymbol, f)), Plus(n, C1)), Times(f, Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Plus(m, p, C1))), x)), x)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), RationalQ(m, n)), Greater(m, C0)), Less(n, CN1)), Not(And(Equal(m, C1), SimplerQ(Plus(ASymbol, Times(BSymbol, x)), Plus(a, Times(b, x)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Plus(e, Times(f, x)), Plus(p, C1)), Power(Times(d, f, Plus(m, n, p, C2)), -1)), Times(Power(Times(d, f, Plus(m, n, p, C2)), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, Negate(C1))), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Simp(Plus(Times(a, ASymbol, d, f, Plus(m, n, p, C2)), Times(CN1, BSymbol, Plus(Times(b, c, e, m), Times(a, Plus(Times(d, e, Plus(n, C1)), Times(c, f, Plus(p, C1)))))), Times(Plus(Times(ASymbol, b, d, f, Plus(m, n, p, C2)), Times(BSymbol, Plus(Times(a, d, f, m), Times(CN1, b, Plus(Times(d, e, Plus(m, n, C1)), Times(c, f, Plus(m, p, C1))))))), x)), x)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), RationalQ(m)), Greater(m, C0)), NonzeroQ(Plus(m, n, p, C2))), Not(And(Equal(m, C1), SimplerQ(Plus(ASymbol, Times(BSymbol, x)), Plus(a, Times(b, x)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Plus(e, Times(f, x)), Plus(p, C1)), Power(Times(Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)), Plus(Times(b, e), Times(CN1, a, f))), -1)), Times(Power(Times(Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)), Plus(Times(b, e), Times(CN1, a, f))), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Simp(Plus(Times(Plus(Times(b, BSymbol, c, e), Times(ASymbol, Plus(Times(a, d, f), Times(CN1, b, Plus(Times(d, e), Times(c, f)))))), Plus(m, C1)), Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(Times(d, e, Plus(n, C1)), Times(c, f, Plus(p, C1)))), Times(CN1, d, f, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, n, p, C3), x)), x)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), RationalQ(m)), Less(m, CN1)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_), Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(Plus(e, Times(f, x)), p), Power(Plus(a, Times(b, x)), -1)), x)), Times(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(Plus(e, Times(f, x)), p), Power(Plus(c, Times(d, x)), -1)), x))), And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(b, -1), Int(Times(Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p)), x)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(b, -1), Int(Times(Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Power(Plus(a, Times(b, x)), -1)), x))), And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), CN1D2), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(d, -1), Int(Times(Sqrt(Plus(c, Times(d, x))), Power(Plus(a, Times(b, x)), m), Power(Plus(e, Times(f, x)), p)), x)), Times(CN1, Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(d, -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(e, Times(f, x)), p), Power(Plus(c, Times(d, x)), CN1D2)), x))), And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, m, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(b, -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p)), x)), Times(BSymbol, Power(b, -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p)), x))), And(And(And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol, m, n, p), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(b, e), Times(CN1, a, f)))), NonzeroQ(Plus(Times(d, e), Times(CN1, c, f)))), Or(SumSimplerQ(m, C1), And(Not(SumSimplerQ(n, C1)), Not(SumSimplerQ(p, C1))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_), Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_)), q_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Power(Plus(g, Times(h, x)), q)), x), x), And(FreeQ(List(a, b, c, d, e, f, g, h, m, n), x), IntegersQ(p, q)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), p_DEFAULT), Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_)), q_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Power(Plus(g, Times(h, x)), q)), x), FreeQ(List(a, b, c, d, e, f, g, h, m, n, p, q), x))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, u_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, v_)), n_DEFAULT), Power(Plus(e_DEFAULT, Times(f_DEFAULT, w_)), p_DEFAULT), Power(Plus(g_DEFAULT, Times(h_DEFAULT, z_)), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n), Power(Plus(e, Times(f, x)), p), Power(Plus(g, Times(h, x)), q)), x), x, u)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, h, m, n, p, q), x), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), ZeroQ(Plus(u, Negate(z)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, n_DEFAULT), Power(w_, p_DEFAULT), Power(z_, q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), n), Power(ExpandToSum(w, x), p), Power(ExpandToSum(z, x), q)), x), And(And(FreeQ(List(m, n, p, q), x), LinearQ(List(u, v, w, z), x)), Not(LinearMatchQ(List(u, v, w, z), x)))))
    );
}
