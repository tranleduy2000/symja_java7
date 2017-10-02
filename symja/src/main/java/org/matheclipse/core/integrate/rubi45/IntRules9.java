package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
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
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.q_DEFAULT;
import static org.matheclipse.core.expression.F.r;
import static org.matheclipse.core.expression.F.r_DEFAULT;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialDegree;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearPairQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules9 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(n, -1), Subst(Int(Times(Plus(ASymbol, Times(BSymbol, x)), Power(Plus(a, Times(b, x)), p), Power(Plus(c, Times(d, x)), q)), x), x, Power(x, n))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(m, Negate(n), C1))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Times(x_, Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Plus(c_, Times(d_DEFAULT, Power(x_, n_)))), -1)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Log(x), Power(Times(a, c), -1)), Times(CN1, b, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(Times(a, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(Power(x, Plus(n, Negate(C1))), Power(Plus(a, Times(b, Power(x, n))), -1)), x)), Times(CN1, d, Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(Times(c, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(Power(x, Plus(n, Negate(C1))), Power(Plus(c, Times(d, Power(x, n))), -1)), x))), And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), Plus(c_, Times(d_DEFAULT, Power(x_, n_)))), -1)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n))), -1)), x)), Times(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Int(Times(Power(x, m), Power(Plus(c, Times(d, Power(x, n))), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Less(Less(CN1, m), n)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(Times(x_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))))), -1)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Power(a, -1), Int(Power(Times(x, Sqrt(Plus(c, Times(d, Power(x, n))))), -1), x)), Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(a, -1), Int(Times(Power(x, Plus(n, Negate(C1))), Power(Times(Plus(a, Times(b, Power(x, n))), Sqrt(Plus(c, Times(d, Power(x, n))))), -1)), x))), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))))), -1)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(b, -1), Int(Times(Power(x, m), Power(Plus(c, Times(d, Power(x, n))), CN1D2)), x)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(b, -1), Int(Times(Power(x, m), Power(Times(Plus(a, Times(b, Power(x, n))), Sqrt(Plus(c, Times(d, Power(x, n))))), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), RationalQ(m, n)), Less(Less(C0, Plus(m, C1)), n)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x), x), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Or(PositiveIntegerQ(p, q), And(And(IntegersQ(m, p, q), Greater(p, C0)), Equal(q, CN1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q), Power(Times(a, b, n, Plus(p, C1)), -1)), Times(Power(Times(a, b, n, Plus(p, C1)), -1), Int(Times(Power(x, m), Simp(Plus(Times(c, Plus(Times(ASymbol, b, n, Plus(p, C1)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)))), Times(d, Plus(Times(ASymbol, b, n, Plus(p, C1)), Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, Times(n, q), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, Negate(C1)))), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p, q)), Less(p, CN1)), Greater(q, C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(x, Plus(m, Negate(n), C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, C1)), Power(Times(b, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1)), Times(CN1, Power(Times(b, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1), Int(Times(Power(x, Plus(m, Negate(n))), Simp(Plus(Times(c, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, Negate(n), C1)), Times(Plus(Times(d, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, Times(n, q), C1)), Times(CN1, b, n, Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)), Plus(p, C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n, p)), Less(Less(C0, n), Plus(m, C1))), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q), Power(Times(a, Plus(m, C1)), -1)), Times(CN1, Power(Times(a, Plus(m, C1)), -1), Int(Times(Power(x, Plus(m, n)), Simp(Plus(Times(c, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)), Times(ASymbol, n, Plus(Times(b, c, Plus(p, C1)), Times(a, d, q))), Times(d, Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)), Times(ASymbol, b, n, Plus(p, q, C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), Plus(q, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, p), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n, q)), Less(m, CN1)), Greater(n, C0)), Greater(q, C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q), Power(Times(b, Plus(m, Times(n, Plus(p, q, C1)), C1)), -1)), Times(Power(Times(b, Plus(m, Times(n, Plus(p, q, C1)), C1)), -1), Int(Times(Power(x, m), Simp(Plus(Times(c, Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)), Times(ASymbol, b, n, Plus(p, q, C1)))), Times(Plus(Times(d, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)), Times(BSymbol, n, q, Plus(Times(b, c), Times(CN1, a, d))), Times(ASymbol, b, d, n, Plus(p, q, C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), Plus(q, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(q)), Greater(q, C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, C1)), Power(Times(a, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1)), Times(Power(Times(a, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), -1), Int(Times(Power(x, m), Simp(Plus(Times(c, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, C1)), Times(ASymbol, n, Plus(Times(b, c), Times(CN1, a, d)), Plus(p, C1)), Times(d, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)), Plus(m, Times(n, Plus(p, q, C2)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(x, Plus(m, Negate(n), C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, C1)), Power(Times(b, d, Plus(m, Times(n, Plus(p, q, C1)), C1)), -1)), Times(CN1, Power(Times(b, d, Plus(m, Times(n, Plus(p, q, C1)), C1)), -1), Int(Times(Power(x, Plus(m, Negate(n))), Simp(Plus(Times(a, BSymbol, c, Plus(m, Negate(n), C1)), Times(Plus(Times(a, BSymbol, d, Plus(m, Times(n, q), C1)), Times(CN1, b, Plus(Times(CN1, BSymbol, c, Plus(m, Times(n, p), C1)), Times(ASymbol, d, Plus(m, Times(n, Plus(p, q, C1)), C1))))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, p, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Less(Less(C0, n), Plus(m, C1))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Plus(c, Times(d, Power(x, n))), Plus(q, C1)), Power(Times(a, c, Plus(m, C1)), -1)), Times(Power(Times(a, c, Plus(m, C1)), -1), Int(Times(Power(x, Plus(m, n)), Simp(Plus(Times(a, BSymbol, c, Plus(m, C1)), Times(CN1, ASymbol, Plus(Times(b, c), Times(a, d)), Plus(m, n, C1)), Times(CN1, ASymbol, n, Plus(Times(b, c, p), Times(a, d, q))), Times(CN1, ASymbol, b, d, Plus(m, Times(n, Plus(p, q, C2)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, p, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Less(m, CN1)), Greater(n, C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(x_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x), x), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p, q), x), NonzeroQ(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol)))), NonzeroQ(Plus(Times(BSymbol, c), Times(CN1, ASymbol, d)))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(v_, n_))), Power(u_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(w_, n_))), p_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, Power(z_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(u, m), Power(Times(Coefficient(v, x, C1), Power(v, m)), -1), Subst(Int(Times(Power(x, m), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(c, Times(d, Power(x, n))), q)), x), x, v)), And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p, q), x), LinearPairQ(u, v, x)), ZeroQ(Plus(v, Negate(w)))), ZeroQ(Plus(v, Negate(z)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_DEFAULT))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_), Power(Plus(c_, Times(d_DEFAULT, Power(x_, r_DEFAULT))), q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(d, Times(c, Power(x, n))), q), Power(Power(x, Times(n, q)), -1)), x), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n, p), x), ZeroQ(Plus(n, r))), IntegerQ(q)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_DEFAULT))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_), Power(Plus(c_, Times(d_DEFAULT, Power(x_, r_DEFAULT))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(x, Times(n, q)), Power(Plus(c, Times(d, Power(Power(x, n), -1))), q), Power(Power(Plus(d, Times(c, Power(x, n))), q), -1), Int(Times(Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(d, Times(c, Power(x, n))), q), Power(Power(x, Times(n, q)), -1)), x)), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n, p, q), x), ZeroQ(Plus(n, r))), Not(IntegerQ(q))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_DEFAULT))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_), Power(Plus(c_, Times(d_DEFAULT, Power(x_, r_DEFAULT))), q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, Plus(m, Times(CN1, n, q))), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(d, Times(c, Power(x, n))), q)), x), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p), x), ZeroQ(Plus(n, r))), IntegerQ(q)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_DEFAULT))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_), Power(Plus(c_, Times(d_DEFAULT, Power(x_, r_DEFAULT))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(x, Times(n, q)), Power(Plus(c, Times(d, Power(Power(x, n), -1))), q), Power(Power(Plus(d, Times(c, Power(x, n))), q), -1), Int(Times(Power(x, Plus(m, Times(CN1, n, q))), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(d, Times(c, Power(x, n))), q)), x)), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n, p, q), x), ZeroQ(Plus(n, r))), Not(IntegerQ(q))))),
            ISetDelayed(Int(Times(z_, Power(u_, p_DEFAULT), Power(v_, q_DEFAULT), Power(x_, m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, m), ExpandToSum(z, x), Power(ExpandToSum(u, x), p), Power(ExpandToSum(v, x), q)), x), And(And(And(And(FreeQ(List(m, p, q), x), BinomialQ(List(z, u, v), x)), ZeroQ(Plus(BinomialDegree(u, x), Negate(BinomialDegree(v, x))))), ZeroQ(Plus(BinomialDegree(u, x), Negate(BinomialDegree(z, x))))), Not(BinomialMatchQ(List(z, u, v), x)))))
    );
}
