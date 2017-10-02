package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$p;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.Block;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.Cancel;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.D;
import static org.matheclipse.core.expression.F.ExpIntegralEi;
import static org.matheclipse.core.expression.F.Exponent;
import static org.matheclipse.core.expression.F.FSymbol;
import static org.matheclipse.core.expression.F.F_;
import static org.matheclipse.core.expression.F.False;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Gamma;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.LogGamma;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Null;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Part;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.PolyLog;
import static org.matheclipse.core.expression.F.PolynomialQ;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.SameQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Simplify;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.Together;
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
import static org.matheclipse.core.expression.F.e_;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_DEFAULT;
import static org.matheclipse.core.expression.F.m;
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
import static org.matheclipse.core.expression.F.r;
import static org.matheclipse.core.expression.F.r_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.u_DEFAULT;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.w;
import static org.matheclipse.core.expression.F.w_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.expression.F.z;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialTest;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.DerivativeDivides;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Dist;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FalseQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FunctionOfLog;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.InverseFunctionFreeQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonsumQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.OneQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuotientOfLinearsMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuotientOfLinearsParts;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuotientOfLinearsQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalFunctionQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplifyIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules36 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Log(Times(e, Power(u, n))), p), Power(Times(Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1)), Times(CN1, n, p, Power(Plus(m, C1), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Power(Plus(c, Times(d, x)), Plus(m, C2)), -1), Power(Log(Times(e, Power(u, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(u_), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Log(u), p), Power(Times(Plus(m, C1), Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1)), Times(CN1, p, Power(Plus(m, C1), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Power(Plus(c, Times(d, x)), Plus(m, C2)), -1), Power(Log(u), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(Times(e_DEFAULT, Power(u_, n_))), -1)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Times(n, Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1), ExpIntegralEi(Times(Plus(m, C1), Power(n, -1), Log(Times(e, Power(u, n))))), Power(Power(Times(e, Power(u, n)), Times(Plus(m, C1), Power(n, -1))), -1)), And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(u_), -1)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1), ExpIntegralEi(Times(Plus(m, C1), Log(u))), Power(Power(u, Plus(m, C1)), -1)), And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Log(Times(e, Power(u, n))), Plus(p, C1)), Power(Times(n, Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1)), Times(CN1, Plus(m, C1), Power(Times(n, Plus(p, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Power(Plus(c, Times(d, x)), Plus(m, C2)), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), $p("m2", true)), Power(Log(u_), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Log(u), Plus(p, C1)), Power(Times(Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d)), Power(Plus(c, Times(d, x)), Plus(m, C1))), -1)), Times(CN1, Plus(m, C1), Power(Plus(p, C1), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Power(Plus(c, Times(d, x)), Plus(m, C2)), -1), Power(Log(u), Plus(p, C1))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), ZeroQ(Plus($s("m2"), m, C2))), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, C1))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_DEFAULT), Log(v_)), x_Symbol),
                    Condition(Plus(Times(CN1, PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(Times(e, Power(u, n))), p)), Times(n, p, Int(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Power(Plus(C1, Negate(v)), -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_DEFAULT), Log(v_)), x_Symbol),
                    Condition(Plus(Times(CN1, PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(u), p)), Times(p, Int(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Power(Plus(C1, Negate(v)), -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), Log(v_)), x_Symbol),
                    Condition(Module(List(Set(f, Simplify(Times(Plus(C1, Negate(v)), Power(u, -1))))), Plus(Times(Log(v), Power(Times(n, Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), Times(f, Power(Times(n, Plus(p, C1)), -1), Int(Times(Power(Times(Plus(c, Times(d, x)), Plus(c, Times(CN1, a, f), d, Times(CN1, b, f))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), x)))), And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Power(Plus(C1, Negate(v)), -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), Log(v_)), x_Symbol),
                    Condition(Module(List(Set(f, Simplify(Times(Plus(C1, Negate(v)), Power(u, -1))))), Plus(Times(Log(v), Power(Times(Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(u), Plus(p, C1))), Times(f, Power(Plus(p, C1), -1), Int(Times(Power(Times(Plus(c, Times(d, x)), Plus(c, Times(CN1, a, f), d, Times(CN1, b, f))), -1), Power(Log(u), Plus(p, C1))), x)))), And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Power(Plus(C1, Negate(v)), -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_DEFAULT), Log(v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(Times(e, Power(u, n))), p)), Times(CN1, n, p, Int(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Plus(C1, Negate(v)))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_DEFAULT), Log(v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(u), p)), Times(CN1, p, Int(Times(PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Plus(C1, Negate(v)))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), Log(v_)), x_Symbol),
                    Condition(Module(List(Set(f, Simplify(Times(u, Plus(C1, Negate(v)))))), Plus(Times(Log(v), Power(Times(n, Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), Times(CN1, f, Power(Times(n, Plus(p, C1)), -1), Int(Times(Power(Times(Plus(a, Times(b, x)), Plus(a, Times(CN1, c, f), b, Times(CN1, d, f))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), x)))), And(And(And(FreeQ(List(a, b, c, d, e, n, Simplify(Times(u, Plus(C1, Negate(v)))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), Log(v_)), x_Symbol),
                    Condition(Module(List(Set(f, Simplify(Times(u, Plus(C1, Negate(v)))))), Plus(Times(Log(v), Power(Times(Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(u), Plus(p, C1))), Times(CN1, f, Power(Plus(p, C1), -1), Int(Times(Power(Times(Plus(a, Times(b, x)), Plus(a, Times(CN1, c, f), b, Times(CN1, d, f))), -1), Power(Log(u), Plus(p, C1))), x)))), And(And(And(FreeQ(List(a, b, c, d, Simplify(Times(u, Plus(C1, Negate(v)))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(Plus(q, C1), v), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(Times(e, Power(u, n))), p)), Times(CN1, n, p, Int(Times(PolyLog(Plus(q, C1), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, q, Simplify(Times(u, Power(v, -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(Plus(q, C1), v), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(u), p)), Times(CN1, p, Int(Times(PolyLog(Plus(q, C1), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, q, Simplify(Times(u, Power(v, -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(q, v), Power(Times(n, Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), Times(CN1, Power(Times(n, Plus(p, C1)), -1), Int(Times(PolyLog(Plus(q, Negate(C1)), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, q, Simplify(Times(u, Power(v, -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(q, v), Power(Times(Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(u), Plus(p, C1))), Times(CN1, Power(Plus(p, C1), -1), Int(Times(PolyLog(Plus(q, Negate(C1)), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, C1))), x))), And(And(And(FreeQ(List(a, b, c, d, q, Simplify(Times(u, Power(v, -1))), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(CN1, PolyLog(Plus(q, C1), v), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(Times(e, Power(u, n))), p)), Times(n, p, Int(Times(PolyLog(Plus(q, C1), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, q, Simplify(Times(u, v)), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(CN1, PolyLog(Plus(q, C1), v), Power(Plus(Times(b, c), Times(CN1, a, d)), -1), Power(Log(u), p)), Times(p, Int(Times(PolyLog(Plus(q, C1), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, q, Simplify(Times(u, v)), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Greater(p, C1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(Times(e_DEFAULT, Power(u_, n_))), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(q, v), Power(Times(n, Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), Times(Power(Times(n, Plus(p, C1)), -1), Int(Times(PolyLog(Plus(q, Negate(C1)), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(Times(e, Power(u, n))), Plus(p, C1))), x))), And(And(And(FreeQ(List(a, b, c, d, e, n, q, Simplify(Times(u, v)), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), -1), Power(Log(u_), p_), PolyLog(q_, v_)), x_Symbol),
                    Condition(Plus(Times(PolyLog(q, v), Power(Times(Plus(p, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Power(Log(u), Plus(p, C1))), Times(Power(Plus(p, C1), -1), Int(Times(PolyLog(Plus(q, Negate(C1)), v), Power(Times(Plus(a, Times(b, x)), Plus(c, Times(d, x))), -1), Power(Log(u), Plus(p, C1))), x))), And(And(And(FreeQ(List(a, b, c, d, q, Simplify(Times(u, v)), Simplify(Times(u, Plus(c, Times(d, x)), Power(Plus(a, Times(b, x)), -1)))), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Log(v_), p_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set($s("lst"), QuotientOfLinearsParts(v, x))), Condition(Int(Times(u, Power(Log(Times(Plus(Part($s("lst"), C1), Times(Part($s("lst"), C2), x)), Power(Plus(Part($s("lst"), C3), Times(Part($s("lst"), C4), x)), -1))), p)), x), Not(And(OneQ(p), ZeroQ(Part($s("lst"), C3)))))), And(And(FreeQ(p, x), QuotientOfLinearsQ(v, x)), Not(QuotientOfLinearsMatchQ(v, x))))),
            ISetDelayed(Int(Times(u_, Log(v_)), x_Symbol),
                    Module(List(Set(w, DerivativeDivides(v, Times(u, Plus(C1, Negate(v))), x))), Condition(Times(w, PolyLog(C2, Together(Plus(C1, Negate(v))))), Not(FalseQ(w))))),
            ISetDelayed(Int(Times(u_, Log(v_), Log(w_)), x_Symbol),
                    Condition(Module(List(Set(z, DerivativeDivides(v, Times(u, Plus(C1, Negate(v))), x))), Condition(Plus(Times(z, Log(w), PolyLog(C2, Together(Plus(C1, Negate(v))))), Negate(Int(SimplifyIntegrand(Times(z, D(w, x), PolyLog(C2, Together(Plus(C1, Negate(v)))), Power(w, -1)), x), x))), Not(FalseQ(z)))), InverseFunctionFreeQ(w, x))),
            ISetDelayed(Int(Log(Times(c_DEFAULT, Power($p("§px"), n_DEFAULT))), x_Symbol),
                    Condition(Plus(Times(x, Log(Times(c, Power($s("§px"), n)))), Times(CN1, n, Int(Times(x, D($s("§px"), x), Power($s("§px"), -1)), x))), And(And(FreeQ(List(c, n), x), PolynomialQ($s("§px"), x)), Greater(Exponent($s("§px"), x), C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Log(Times(c_DEFAULT, Power($p("§px"), n_DEFAULT)))), x_Symbol),
                    Condition(Plus(Times(Log(Plus(a, Times(b, x))), Log(Times(c, Power($s("§px"), n))), Power(b, -1)), Times(CN1, n, Power(b, -1), Int(Times(Log(Plus(a, Times(b, x))), D($s("§px"), x), Power($s("§px"), -1)), x))), And(And(FreeQ(List(a, b, c, n), x), PolynomialQ($s("§px"), x)), Greater(Exponent($s("§px"), x), C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Log(Times(c_DEFAULT, Power($p("§px"), n_DEFAULT)))), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Log(Times(c, Power($s("§px"), n))), Power(Times(b, Plus(m, C1)), -1)), Times(CN1, n, Power(Times(b, Plus(m, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), D($s("§px"), x), Power($s("§px"), -1)), x))), And(And(And(FreeQ(List(a, b, c, m, n), x), PolynomialQ($s("§px"), x)), Greater(Exponent($s("§px"), x), C1)), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Log(Times(c_DEFAULT, Power(Times(b_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT)), p_DEFAULT))), x_Symbol),
                    Condition(Plus(Times(Plus(d, Times(e, x)), Log(Times(c, Power(Times(b, Power(Plus(d, Times(e, x)), n)), p))), Power(e, -1)), Times(CN1, n, p, x)), FreeQ(List(b, c, d, e, n, p), x))),
            ISetDelayed(Int(Log(Times(c_DEFAULT, Power(Plus(a_, Times(b_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_))), p_DEFAULT))), x_Symbol),
                    Condition(Plus(Times(Plus(d, Times(e, x)), Log(Times(c, Power(Plus(a, Times(b, Power(Plus(d, Times(e, x)), n))), p))), Power(e, -1)), Times(CN1, b, n, p, Int(Power(Plus(b, Times(a, Power(Plus(d, Times(e, x)), Negate(n)))), -1), x))), And(And(FreeQ(List(a, b, c, d, e, p), x), RationalQ(n)), Less(n, C0)))),
            ISetDelayed(Int(Log(Times(c_DEFAULT, Power(Plus(a_, Times(b_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT))), p_DEFAULT))), x_Symbol),
                    Condition(Plus(Times(Plus(d, Times(e, x)), Log(Times(c, Power(Plus(a, Times(b, Power(Plus(d, Times(e, x)), n))), p))), Power(e, -1)), Times(CN1, n, p, x), Times(a, n, p, Int(Power(Plus(a, Times(b, Power(Plus(d, Times(e, x)), n))), -1), x))), And(FreeQ(List(a, b, c, d, e, n, p), x), Not(And(RationalQ(n), Less(n, C0)))))),
            ISetDelayed(Int(Times(Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT), Log(Plus(C1, Times(e_DEFAULT, Power(Power(F_, Times(c_DEFAULT, Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_DEFAULT))))), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(f, Times(g, x)), m), PolyLog(C2, Times(CN1, e, Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n))), Power(Times(b, c, n, Log(FSymbol)), -1)), Times(g, m, Power(Times(b, c, n, Log(FSymbol)), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(m, Negate(C1))), PolyLog(C2, Times(CN1, e, Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n)))), x))), And(And(FreeQ(List(FSymbol, a, b, c, e, f, g, n), x), RationalQ(m)), Greater(m, C0)))),
            ISetDelayed(Int(Times(Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT), Log(Plus(d_, Times(e_DEFAULT, Power(Power(F_, Times(c_DEFAULT, Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_DEFAULT))))), x_Symbol),
                    Condition(Plus(Times(Power(Plus(f, Times(g, x)), Plus(m, C1)), Log(Plus(d, Times(e, Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n)))), Power(Times(g, Plus(m, C1)), -1)), Times(CN1, Power(Plus(f, Times(g, x)), Plus(m, C1)), Log(Plus(C1, Times(e, Power(d, -1), Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n)))), Power(Times(g, Plus(m, C1)), -1)), Int(Times(Power(Plus(f, Times(g, x)), m), Log(Plus(C1, Times(e, Power(d, -1), Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n))))), x)), And(And(And(FreeQ(List(FSymbol, a, b, c, d, e, f, g, n), x), RationalQ(m)), Greater(m, C0)), NonzeroQ(Plus(d, Negate(C1)))))),
            ISetDelayed(Int(Times(Power(ArcSin(Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_DEFAULT), Log(Times(c_DEFAULT, Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)))), x_Symbol),
                    Condition(Module(List(Set(w, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Power(ArcSin(Plus(d, Times(e, x))), m), x)))), Plus(Dist(Log(Times(c, Power(Plus(a, Times(b, Power(x, n))), p))), w, x), Times(CN1, b, n, p, Int(SimplifyIntegrand(Times(w, Power(x, Plus(n, Negate(C1))), Power(Plus(a, Times(b, Power(x, n))), -1)), x), x)))), And(FreeQ(List(a, b, c, d, e, n, p), x), PositiveIntegerQ(m)))),
            ISetDelayed(Int(Log(u_), x_Symbol),
                    Condition(Plus(Times(x, Log(u)), Negate(Int(SimplifyIntegrand(Times(x, D(u, x), Power(u, -1)), x), x))), InverseFunctionFreeQ(u, x))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Log(u_)), x_Symbol),
                    Condition(Plus(Times(Log(Plus(a, Times(b, x))), Log(u), Power(b, -1)), Times(CN1, Power(b, -1), Int(SimplifyIntegrand(Times(Log(Plus(a, Times(b, x))), D(u, x), Power(u, -1)), x), x))), And(And(FreeQ(List(a, b), x), RationalFunctionQ(Times(D(u, x), Power(u, -1)), x)), Or(NonzeroQ(a), Not(And(BinomialTest(u, x), SameQ(Sqr(Part(BinomialTest(u, x), C3)), C1))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Log(u_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Log(u), Power(Times(b, Plus(m, C1)), -1)), Times(CN1, Power(Times(b, Plus(m, C1)), -1), Int(SimplifyIntegrand(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), D(u, x), Power(u, -1)), x), x))), And(And(FreeQ(List(a, b, m), x), InverseFunctionFreeQ(u, x)), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Log(Times(d_DEFAULT, Power(Plus(e_DEFAULT, Times(f_DEFAULT, x_)), n_DEFAULT)))), x_Symbol),
                    Condition(Int(ExpandIntegrand(Log(Times(d, Power(Plus(e, Times(f, x)), n))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1), x), x), FreeQ(List(a, b, c, d, e, f, n), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Log(Times(d_DEFAULT, Power(Plus(e_, Times(f_DEFAULT, x_)), n_DEFAULT)))), x_Symbol),
                    Condition(Int(ExpandIntegrand(Log(Times(d, Power(Plus(e, Times(f, x)), n))), Power(Plus(a, Times(c, Sqr(x))), -1), x), x), FreeQ(List(a, c, d, e, f, n), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), Log(u_)), x_Symbol),
                    Condition(Module(List(Set(w, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1), x)))), Plus(Times(w, Log(u)), Negate(Int(SimplifyIntegrand(Times(w, D(u, x), Power(u, -1)), x), x)))), And(FreeQ(List(a, b, c), x), InverseFunctionFreeQ(u, x)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1), Log(u_)), x_Symbol),
                    Condition(Module(List(Set(w, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Power(Plus(a, Times(c, Sqr(x))), -1), x)))), Plus(Times(w, Log(u)), Negate(Int(SimplifyIntegrand(Times(w, D(u, x), Power(u, -1)), x), x)))), And(FreeQ(List(a, c), x), InverseFunctionFreeQ(u, x)))),
            ISetDelayed(Int(Times(Power(u_, Times(a_DEFAULT, x_)), Log(u_)), x_Symbol),
                    Condition(Plus(Times(Power(u, Times(a, x)), Power(a, -1)), Negate(Int(SimplifyIntegrand(Times(x, Power(u, Plus(Times(a, x), Negate(C1))), D(u, x)), x), x))), And(FreeQ(a, x), InverseFunctionFreeQ(u, x)))),
            ISetDelayed(Int(Times(v_, Log(u_)), x_Symbol),
                    Condition(Module(List(Set(w, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(v, x)))), Condition(Plus(Dist(Log(u), w, x), Negate(Int(SimplifyIntegrand(Times(w, D(u, x), Power(u, -1)), x), x))), InverseFunctionFreeQ(w, x))), InverseFunctionFreeQ(u, x))),
            ISetDelayed(Int(Times(Log(v_), Log(w_)), x_Symbol),
                    Condition(Plus(Times(x, Log(v), Log(w)), Negate(Int(SimplifyIntegrand(Times(x, Log(w), D(v, x), Power(v, -1)), x), x)), Negate(Int(SimplifyIntegrand(Times(x, Log(v), D(w, x), Power(w, -1)), x), x))), And(InverseFunctionFreeQ(v, x), InverseFunctionFreeQ(w, x)))),
            ISetDelayed(Int(Times(u_, Log(v_), Log(w_)), x_Symbol),
                    Condition(Module(List(Set(z, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(u, x)))), Condition(Plus(Dist(Times(Log(v), Log(w)), z, x), Negate(Int(SimplifyIntegrand(Times(z, Log(w), D(v, x), Power(v, -1)), x), x)), Negate(Int(SimplifyIntegrand(Times(z, Log(v), D(w, x), Power(w, -1)), x), x))), InverseFunctionFreeQ(z, x))), And(InverseFunctionFreeQ(v, x), InverseFunctionFreeQ(w, x)))),
            ISetDelayed(Int(Power(Log(Times(c_DEFAULT, Power(Plus(a_, Times(b_DEFAULT, Power(x_, -1))), n_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Plus(b, Times(a, x)), Power(Log(Times(c, Power(Plus(a, Times(b, Power(x, -1))), n))), p), Power(a, -1)), Times(b, Power(a, -1), n, p, Int(Times(Power(Log(Times(c, Power(Plus(a, Times(b, Power(x, -1))), n))), Plus(p, Negate(C1))), Power(x, -1)), x))), And(FreeQ(List(a, b, c, n), x), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Power(Log(Times(d_DEFAULT, Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), n_DEFAULT))), p_DEFAULT), x_Symbol),
                    Condition(Int(Power(Log(Times(d, Power(Plus(b, Times(C2, c, x)), Times(C2, n)), Power(Times(Power(C4, n), Power(c, n)), -1))), p), x), And(And(FreeQ(List(a, b, c, d, p), x), IntegerQ(n)), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Sqr(Log(Times(d_DEFAULT, Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), n_DEFAULT)))), x_Symbol),
                    Condition(Plus(Times(x, Sqr(Log(Times(d, Power(Plus(a, Times(b, x), Times(c, Sqr(x))), n))))), Times(CN1, C2, b, n, Int(Times(x, Log(Times(d, Power(Plus(a, Times(b, x), Times(c, Sqr(x))), n))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x)), Times(CN1, C4, c, n, Int(Times(Sqr(x), Log(Times(d, Power(Plus(a, Times(b, x), Times(c, Sqr(x))), n))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x))), And(FreeQ(List(a, b, c, d, n), x), Not(And(IntegerQ(n), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))))),
            ISetDelayed(Int(Power(Log(Times(a_DEFAULT, Power(Times(b_DEFAULT, Power(x_, n_DEFAULT)), p_))), q_DEFAULT), x_Symbol),
                    Condition(Subst(Int(Power(Log(Power(x, Times(n, p))), q), x), Power(x, Times(n, p)), Times(a, Power(Times(b, Power(x, n)), p))), FreeQ(List(a, b, n, p, q), x))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Log(Times(a_DEFAULT, Power(Times(b_DEFAULT, Power(x_, n_DEFAULT)), p_))), q_DEFAULT)), x_Symbol),
                    Condition(Subst(Int(Times(Power(x, m), Power(Log(Power(x, Times(n, p))), q)), x), Power(x, Times(n, p)), Times(a, Power(Times(b, Power(x, n)), p))), And(And(FreeQ(List(a, b, m, n, p, q), x), NonzeroQ(Plus(m, C1))), Not(SameQ(Power(x, Times(n, p)), Times(a, Power(Times(b, Power(x, n)), p))))))),
            ISetDelayed(Int(Power(Log(Times(a_DEFAULT, Power(Times(b_DEFAULT, Power(Times(c_DEFAULT, Power(x_, n_DEFAULT)), p_)), q_))), r_DEFAULT), x_Symbol),
                    Condition(Subst(Int(Power(Log(Power(x, Times(n, p, q))), r), x), Power(x, Times(n, p, q)), Times(a, Power(Times(b, Power(Times(c, Power(x, n)), p)), q))), FreeQ(List(a, b, c, n, p, q, r), x))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Log(Times(a_DEFAULT, Power(Times(b_DEFAULT, Power(Times(c_DEFAULT, Power(x_, n_DEFAULT)), p_)), q_))), r_DEFAULT)), x_Symbol),
                    Condition(Subst(Int(Times(Power(x, m), Power(Log(Power(x, Times(n, p, q))), r)), x), Power(x, Times(n, p, q)), Times(a, Power(Times(b, Power(Times(c, Power(x, n)), p)), q))), And(And(FreeQ(List(a, b, c, m, n, p, q, r), x), NonzeroQ(Plus(m, C1))), Not(SameQ(Power(x, Times(n, p, q)), Times(a, Power(Times(b, Power(Times(c, Power(x, n)), p)), q))))))),
            ISetDelayed(Int(Log(Times(a_DEFAULT, Power(Log(Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT))), x_Symbol),
                    Condition(Plus(Times(x, Log(Times(a, Power(Log(Times(b, Power(x, n))), p)))), Times(CN1, n, p, Int(Power(Log(Times(b, Power(x, n))), -1), x))), FreeQ(List(a, b, n, p), x))),
            ISetDelayed(Int(Times(Power(x_, -1), Log(Times(a_DEFAULT, Power(Log(Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)))), x_Symbol),
                    Condition(Times(Log(Times(b, Power(x, n))), Plus(Negate(p), Log(Times(a, Power(Log(Times(b, Power(x, n))), p)))), Power(n, -1)), FreeQ(List(a, b, n, p), x))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Log(Times(a_DEFAULT, Power(Log(Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)))), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Log(Times(a, Power(Log(Times(b, Power(x, n))), p))), Power(Plus(m, C1), -1)), Times(CN1, n, p, Power(Plus(m, C1), -1), Int(Times(Power(x, m), Power(Log(Times(b, Power(x, n))), -1)), x))), And(FreeQ(List(a, b, m, n, p), x), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, Log(Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_, Times(b_DEFAULT, Log(Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, ASymbol), Times(CN1, a, BSymbol)), Power(b, -1), Int(Power(Plus(a, Times(b, Log(Plus(c, Times(d, x))))), CN1D2), x)), Times(BSymbol, Power(b, -1), Int(Sqrt(Plus(a, Times(b, Log(Plus(c, Times(d, x)))))), x))), And(FreeQ(List(a, b, c, d, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(b, ASymbol), Times(CN1, a, BSymbol)))))),
            ISetDelayed(Int(Power(f_, Times(a_DEFAULT, Log(u_))), x_Symbol),
                    Condition(Int(Power(u, Times(a, Log(f))), x), FreeQ(List(a, f), x))),
            ISetDelayed(Int(u_, x_Symbol),
                    Condition(Module(List(Set($s("lst"), FunctionOfLog(Cancel(Times(x, u)), x))), Condition(Times(Power(Part($s("lst"), C3), -1), Subst(Int(Part($s("lst"), C1), x), x, Log(Part($s("lst"), C2)))), Not(FalseQ($s("lst"))))), NonsumQ(u))),
            ISetDelayed(Int(Times(u_DEFAULT, Log(Gamma(v_))), x_Symbol),
                    Plus(Times(Plus(Log(Gamma(v)), Negate(LogGamma(v))), Int(u, x)), Int(Times(u, LogGamma(v)), x))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(a_DEFAULT, w_), Times(b_DEFAULT, w_, Power(Log(v_), n_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(u, Power(w, p), Power(Plus(a, Times(b, Power(Log(v), n))), p)), x), And(FreeQ(List(a, b, n), x), IntegerQ(p))))
    );
}
