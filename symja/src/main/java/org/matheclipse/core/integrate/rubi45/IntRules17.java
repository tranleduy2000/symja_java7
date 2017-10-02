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
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Denominator;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.If;
import static org.matheclipse.core.expression.F.IntegerQ;
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
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_DEFAULT;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FractionQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumSimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules17 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_)), Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, e), Times(CN1, BSymbol, d)), Power(Plus(d, Times(e, x)), Plus(m, C1)), Power(Plus(a, Times(c, Sqr(x))), Plus(p, C1)), Power(Times(Plus(m, C1), Plus(Times(c, Sqr(d)), Times(a, Sqr(e)))), -1)), Times(Power(Times(Plus(m, C1), Plus(Times(c, Sqr(d)), Times(a, Sqr(e)))), -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, C1)), Simp(Plus(Times(Plus(Times(ASymbol, c, d), Times(a, BSymbol, e)), Plus(m, C1)), Times(CN1, c, Plus(Times(ASymbol, e), Times(CN1, BSymbol, d)), Plus(m, Times(C2, p), C3), x)), x), Power(Plus(a, Times(c, Sqr(x))), p)), x))), And(And(And(FreeQ(List(a, c, d, e, ASymbol, BSymbol, m, p), x), NonzeroQ(Plus(Times(BSymbol, d), Times(CN1, ASymbol, e)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), If(RationalQ(m), Less(m, CN1), And(NonzeroQ(Plus(m, C1)), SumSimplerQ(m, C1)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), m_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Plus(d, Times(e, x)), Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), x)), p)), x), And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), m_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Plus(d, Times(e, x)), Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), x)), p)), x), And(And(And(FreeQ(List(a, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Sqr(Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), x_Symbol),
                    Condition(Plus(Times(g, Power(Plus(d, Times(e, x)), m), Plus(f, Times(g, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, C1)), Power(Times(c, Plus(m, Times(C2, p), C3)), -1)), Times(CN1, Power(Times(c, Sqr(e), Plus(m, Times(C2, p), C3)), -1), Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), Simp(Plus(Times(b, e, g, Plus(Times(d, g), Times(e, f, Plus(m, p, C1)))), Times(CN1, c, Plus(Times(Sqr(d), Sqr(g)), Times(d, e, f, g, m), Times(Sqr(e), Sqr(f), Plus(m, Times(C2, p), C3)))), Times(e, g, Plus(Times(b, e, g, Plus(m, p, C2)), Times(CN1, c, Plus(Times(d, g, m), Times(e, f, Plus(m, Times(C2, p), C4))))), x)), x)), x))), And(And(And(FreeQ(List(a, b, c, d, e, f, g, m, p), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Sqr(Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), x_Symbol),
                    Condition(Plus(Times(g, Power(Plus(d, Times(e, x)), m), Plus(f, Times(g, x)), Power(Plus(a, Times(c, Sqr(x))), Plus(p, C1)), Power(Times(c, Plus(m, Times(C2, p), C3)), -1)), Times(CN1, Power(Times(c, Sqr(e), Plus(m, Times(C2, p), C3)), -1), Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(a, Times(c, Sqr(x))), p), Simp(Plus(Times(CN1, c, Plus(Times(Sqr(d), Sqr(g)), Times(d, e, f, g, m), Times(Sqr(e), Sqr(f), Plus(m, Times(C2, p), C3)))), Times(CN1, c, e, g, Plus(Times(d, g, m), Times(e, f, Plus(m, Times(C2, p), C4))), x)), x)), x))), And(And(FreeQ(List(a, c, d, e, f, g, m, p), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(p))), Or(PositiveIntegerQ(m), IntegersQ(m, n))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(c, Sqr(x))), p)), x), x), And(And(And(And(FreeQ(List(a, c, d, e, f, g, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(p))), Or(PositiveIntegerQ(m), IntegersQ(m, n))))),
            ISetDelayed(Int(Times(Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_), Power(Plus(Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Times(e_DEFAULT, x_), m_)), x_Symbol),
                    Condition(Times(Power(Times(e, x), m), Power(Plus(Times(b, x), Times(c, Sqr(x))), p), Power(Times(Power(x, Plus(m, p)), Power(Plus(b, Times(c, x)), p)), -1), Int(Times(Power(x, Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(b, Times(c, x)), p)), x)), And(FreeQ(List(b, c, e, f, g, m, n), x), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), m_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Plus(d, Times(e, x)), Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), x)), p)), x), And(And(And(And(And(FreeQ(List(a, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(p))), PositiveQ(a)), PositiveQ(d)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), Power(Times(Power(Plus(d, Times(e, x)), p), Power(Plus(Times(a, Power(d, -1)), Times(c, x, Power(e, -1))), p)), -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), x)), p)), x)), And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(c, Sqr(x))), p), Power(Times(Power(Plus(d, Times(e, x)), p), Power(Plus(Times(a, Power(d, -1)), Times(c, x, Power(e, -1))), p)), -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, p)), Power(Plus(f, Times(g, x)), n), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), x)), p)), x)), And(And(And(FreeQ(List(a, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x), And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegersQ(m, n, p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(c, Sqr(x))), p)), x), x), And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegersQ(m, n, p)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Times(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), Plus(f_DEFAULT, Times(g_DEFAULT, x_))), -1)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, x)), -1)), x)), Times(CN1, Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Plus(Times(c, d, f), Times(CN1, b, e, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(f, Times(g, x)), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), FractionQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Times(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), Plus(f_DEFAULT, Times(g_DEFAULT, x_))), -1)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(a, Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, x)), -1)), x)), Times(CN1, Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Plus(Times(c, d, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(f, Times(g, x)), -1)), x))), And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), FractionQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Module(List(Set(q, Denominator(m))), Times(q, Power(e, -1), Subst(Int(Times(Power(x, Plus(Times(q, Plus(m, C1)), Negate(C1))), Power(Plus(Times(Plus(Times(e, f), Times(CN1, d, g)), Power(e, -1)), Times(g, Power(x, q), Power(e, -1))), n), Power(Plus(Times(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), Power(e, -2)), Times(CN1, Plus(Times(C2, c, d), Times(CN1, b, e)), Power(x, q), Power(e, -2)), Times(c, Power(x, Times(C2, q)), Power(e, -2))), p)), x), x, Power(Plus(d, Times(e, x)), Power(q, -1))))), And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegersQ(n, p)), FractionQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Module(List(Set(q, Denominator(m))), Times(q, Power(e, -1), Subst(Int(Times(Power(x, Plus(Times(q, Plus(m, C1)), Negate(C1))), Power(Plus(Times(Plus(Times(e, f), Times(CN1, d, g)), Power(e, -1)), Times(g, Power(x, q), Power(e, -1))), n), Power(Plus(Times(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), Power(e, -2)), Times(CN1, C2, c, d, Power(x, q), Power(e, -2)), Times(c, Power(x, Times(C2, q)), Power(e, -2))), p)), x), x, Power(Plus(d, Times(e, x)), Power(q, -1))))), And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegersQ(n, p)), FractionQ(m)))),
            ISetDelayed(Int(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Int(Times(Plus(a, Times(b, x)), Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), x), Times(c, Int(Times(Sqr(x), Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), x))), And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), RationalQ(m, n)))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(a, Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), x)), Times(c, Int(Times(Sqr(x), Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), x))), And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), RationalQ(m, n)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(g, Power(c, -2), Int(Times(Simp(Plus(Times(C2, c, e, f), Times(c, d, g), Times(CN1, b, e, g), Times(c, e, g, x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C2)))), x)), Times(Power(c, -2), Int(Times(Simp(Plus(Times(Sqr(c), d, Sqr(f)), Times(CN1, C2, a, c, e, f, g), Times(CN1, a, c, d, Sqr(g)), Times(a, b, e, Sqr(g)), Times(Plus(Times(Sqr(c), e, Sqr(f)), Times(C2, Sqr(c), d, f, g), Times(CN1, C2, b, c, e, f, g), Times(CN1, b, c, d, Sqr(g)), Times(Sqr(b), e, Sqr(g)), Times(CN1, a, c, e, Sqr(g))), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C2))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Greater(n, C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(g, Power(c, -1), Int(Times(Simp(Plus(Times(C2, e, f), Times(d, g), Times(e, g, x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C2)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d, Sqr(f)), Times(CN1, C2, a, e, f, g), Times(CN1, a, d, Sqr(g)), Times(Plus(Times(c, e, Sqr(f)), Times(C2, c, d, f, g), Times(CN1, a, e, Sqr(g))), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C2))), Power(Plus(a, Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Greater(n, C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(e, g, Power(c, -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C1)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d, f), Times(CN1, a, e, g), Times(Plus(Times(c, e, f), Times(c, d, g), Times(CN1, b, e, g)), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Greater(n, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(e, g, Power(c, -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C1)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d, f), Times(CN1, a, e, g), Times(Plus(Times(c, e, f), Times(c, d, g)), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Power(Plus(a, Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Greater(n, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(CN1, g, Plus(Times(e, f), Times(CN1, d, g)), Power(Plus(Times(c, Sqr(f)), Times(CN1, b, f, g), Times(a, Sqr(g))), -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), n)), x)), Times(Power(Plus(Times(c, Sqr(f)), Times(CN1, b, f, g), Times(a, Sqr(g))), -1), Int(Times(Simp(Plus(Times(c, d, f), Times(CN1, b, d, g), Times(a, e, g), Times(c, Plus(Times(e, f), Times(CN1, d, g)), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, C1)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(CN1, g, Plus(Times(e, f), Times(CN1, d, g)), Power(Plus(Times(c, Sqr(f)), Times(a, Sqr(g))), -1), Int(Times(Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), n)), x)), Times(Power(Plus(Times(c, Sqr(f)), Times(a, Sqr(g))), -1), Int(Times(Simp(Plus(Times(c, d, f), Times(a, e, g), Times(c, Plus(Times(e, f), Times(CN1, d, g)), x)), x), Power(Plus(d, Times(e, x)), Plus(m, Negate(C1))), Power(Plus(f, Times(g, x)), Plus(n, C1)), Power(Plus(a, Times(c, Sqr(x))), -1)), x))), And(And(And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))), RationalQ(m, n)), Greater(m, C0)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1), x), x), And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n)), Power(Plus(a, Times(c, Sqr(x))), -1), x), x), And(And(And(FreeQ(List(a, c, d, e, f, g, m, n), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(m))), Not(IntegerQ(n))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, C1)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, x)), -1)), x)), Times(CN1, Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(f, Times(g, x)), n), Plus(Times(c, d, f), Times(CN1, b, e, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(n))), Not(IntegerQ(p))), RationalQ(n, p)), Greater(p, C0)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, C1)), Power(Plus(a, Times(c, Sqr(x))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, x)), -1)), x)), Times(CN1, Power(Times(e, Plus(Times(e, f), Times(CN1, d, g))), -1), Int(Times(Power(Plus(f, Times(g, x)), n), Plus(Times(c, d, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(c, Sqr(x))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(n))), Not(IntegerQ(p))), RationalQ(n, p)), Greater(p, C0)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(e, Plus(Times(e, f), Times(CN1, d, g)), Power(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, C1)), Power(Plus(d, Times(e, x)), -1)), x)), Times(Power(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Plus(Times(c, d, f), Times(CN1, b, e, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(n))), Not(IntegerQ(p))), RationalQ(n, p)), Less(p, CN1)), Greater(n, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(e, Plus(Times(e, f), Times(CN1, d, g)), Power(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Power(Plus(a, Times(c, Sqr(x))), Plus(p, C1)), Power(Plus(d, Times(e, x)), -1)), x)), Times(Power(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(n, Negate(C1))), Plus(Times(c, d, f), Times(a, e, g), Times(CN1, c, Plus(Times(e, f), Times(CN1, d, g)), x)), Power(Plus(a, Times(c, Sqr(x))), p)), x))), And(And(And(And(And(And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(e, f), Times(CN1, d, g)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(n))), Not(IntegerQ(p))), RationalQ(n, p)), Less(p, CN1)), Greater(n, C0)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), Power(Plus(d, Times(e, x)), n), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x), And(And(And(FreeQ(List(a, b, c, d, e, m, p), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), Power(Plus(d, Times(e, x)), n), Power(Plus(a, Times(c, Sqr(x))), p)), x), x), And(And(FreeQ(List(a, c, d, e, m, p), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x), And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Or(Or(PositiveIntegerQ(m), PositiveIntegerQ(p)), IntegersQ(m, n, p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_)), m_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(c, Sqr(x))), p)), x), x), And(And(FreeQ(List(a, c, d, e, f, g), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Or(Or(PositiveIntegerQ(m), PositiveIntegerQ(p)), IntegersQ(m, n, p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, w_), Times(c_DEFAULT, Sqr(z_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, u_)), m_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, v_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x, u)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, m, n, p), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), ZeroQ(Plus(u, Negate(z)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(z_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, u_)), m_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, v_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(f, Times(g, x)), n), Power(Plus(a, Times(c, Sqr(x))), p)), x), x, u)), And(And(And(And(FreeQ(List(a, c, d, e, f, g, m, n, p), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(z)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, n_DEFAULT), Power(w_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), n), Power(ExpandToSum(w, x), p)), x), And(And(And(FreeQ(List(m, n, p), x), LinearQ(List(u, v), x)), QuadraticQ(w, x)), Not(And(LinearMatchQ(List(u, v), x), QuadraticMatchQ(w, x))))))
    );
}
