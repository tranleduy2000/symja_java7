package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.*;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Dist;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FunctionOfExponential;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FunctionOfExponentialFunction;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FunctionOfExponentialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NormalizeIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules34 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(F_, Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power(Plus(d, Times(e, x)), m), Power(FSymbol, Plus(a, Times(b, x), Times(c, Sqr(x))))), x), FreeQ(List(FSymbol, a, b, c, d, e, m), x))),
            ISetDelayed(Int(Times(Power(F_, v_), Power(u_, m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(FSymbol, ExpandToSum(v, x))), x), And(And(And(FreeQ(List(FSymbol, m), x), LinearQ(u, x)), QuadraticQ(v, x)), Not(And(LinearMatchQ(u, x), QuadraticMatchQ(v, x)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), n)), x), x), And(FreeQ(List(FSymbol, a, b, c, d, e, m), x), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(f, Times(g, x)), Plus(m, C1)), Power(Times(a, g, Plus(m, C1)), -1)), Times(CN1, b, Power(a, -1), Int(Times(Power(Plus(f, Times(g, x)), m), Power(FSymbol, Times(e, Plus(c, Times(d, x)))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), -1)), x))), And(And(FreeQ(List(FSymbol, a, b, c, d, e, f, g), x), RationalQ(m)), Greater(m, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(u, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), n), x)))), Plus(Dist(Power(Plus(f, Times(g, x)), m), u, x), Times(CN1, g, m, Int(Times(Power(Plus(f, Times(g, x)), Plus(m, Negate(C1))), u), x)))), And(And(And(FreeQ(List(FSymbol, a, b, c, d, e, f, g), x), RationalQ(m, n)), Greater(m, C0)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), -1), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(f, Times(g, x)), m), Log(Plus(C1, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))), Power(a, -1)))), Power(Times(b, d, e, Log(FSymbol)), -1)), Times(CN1, g, m, Power(Times(b, d, e, Log(FSymbol)), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(m, Negate(C1))), Log(Plus(C1, Times(b, Power(a, -1), Power(FSymbol, Times(e, Plus(c, Times(d, x)))))))), x))), And(And(FreeQ(List(FSymbol, a, b, c, d, e, f, g), x), RationalQ(m)), GreaterEqual(m, C1)))),
            ISetDelayed(Int(Times(Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_DEFAULT), Power(Plus(f_DEFAULT, Times(g_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(f, Times(g, x)), m), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(p, C1)), Power(Times(b, d, e, Plus(p, C1), Log(FSymbol)), -1)), Times(CN1, g, m, Power(Times(b, d, e, Plus(p, C1), Log(FSymbol)), -1), Int(Times(Power(Plus(f, Times(g, x)), Plus(m, Negate(C1))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(p, C1))), x))), And(FreeQ(List(FSymbol, a, b, c, d, e, f, g, m, p), x), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Times(Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(F_, v_))), n_)), x_Symbol),
                    Condition(Module(List(Set(u, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Times(Power(FSymbol, Times(e, Plus(c, Times(d, x)))), Power(Plus(a, Times(b, Power(FSymbol, v))), n)), x)))), Plus(Dist(Power(x, m), u, x), Times(CN1, m, Int(Times(Power(x, Plus(m, Negate(C1))), u), x)))), And(And(And(And(FreeQ(List(FSymbol, a, b, c, d, e), x), ZeroQ(Plus(Times(C2, e, Plus(c, Times(d, x))), Negate(v)))), RationalQ(m)), Greater(m, C0)), NegativeIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(m, FullSimplify(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1))))), Condition(Times(Denominator(m), Power(GSymbol, Plus(Times(f, h), Times(CN1, c, g, h, Power(d, -1)))), Power(Times(d, e, Log(FSymbol)), -1), Subst(Int(Times(Power(x, Plus(Numerator(m), Negate(C1))), Power(Plus(a, Times(b, Power(x, Denominator(m)))), n)), x), x, Power(FSymbol, Times(e, Plus(c, Times(d, x)), Power(Denominator(m), -1))))), And(RationalQ(m), GreaterEqual(Abs(m), C1)))), FreeQ(List(FSymbol, GSymbol, a, b, c, d, e, f, g, h, n), x))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(m, FullSimplify(Times(d, e, Log(FSymbol), Power(Times(g, h, Log(GSymbol)), -1))))), Condition(Times(Denominator(m), Power(Times(g, h, Log(GSymbol)), -1), Subst(Int(Times(Power(x, Plus(Denominator(m), Negate(C1))), Power(Plus(a, Times(b, Power(FSymbol, Plus(Times(c, e), Times(CN1, d, e, f, Power(g, -1)))), Power(x, Numerator(m)))), n)), x), x, Power(GSymbol, Times(h, Plus(f, Times(g, x)), Power(Denominator(m), -1))))), And(RationalQ(m), Greater(Abs(m), C1)))), FreeQ(List(FSymbol, GSymbol, a, b, c, d, e, f, g, h, n), x))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Int(Expand(Times(Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), n)), x), x), And(And(FreeQ(List(FSymbol, GSymbol, a, b, c, d, e, f, g, h), x), Not(RationalQ(FullSimplify(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)))))), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_)), x_Symbol),
                    Condition(Times(Power(a, n), Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power(Times(g, h, Log(GSymbol)), -1), Hypergeometric2F1(Negate(n), Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)), Plus(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)), C1), Simplify(Times(CN1, b, Power(a, -1), Power(FSymbol, Times(e, Plus(c, Times(d, x)))))))), And(And(FreeQ(List(FSymbol, GSymbol, a, b, c, d, e, f, g, h), x), Not(RationalQ(FullSimplify(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)))))), NegativeIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_)), x_Symbol),
                    Condition(Times(Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(n, C1)), Power(Times(a, g, h, Log(GSymbol)), -1), Hypergeometric2F1(C1, Plus(n, Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)), C1), Plus(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)), C1), Times(CN1, b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))), Power(a, -1)))), And(And(FreeQ(List(FSymbol, GSymbol, a, b, c, d, e, f, g, h, n), x), Not(RationalQ(FullSimplify(Times(g, h, Log(GSymbol), Power(Times(d, e, Log(FSymbol)), -1)))))), Not(IntegerQ(n))))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, u_)), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, v_)))), n_)), x_Symbol),
                    Condition(Int(Times(Power(GSymbol, Times(h, ExpandToSum(u, x))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, ExpandToSum(v, x))))), n)), x), And(And(FreeQ(List(FSymbol, GSymbol, a, b, e, h, n), x), LinearQ(List(u, v), x)), Not(LinearMatchQ(List(u, v), x))))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power($p("H"), Times(t_DEFAULT, Plus(r_DEFAULT, Times(s_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(m, FullSimplify(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1))))), Condition(Times(Denominator(m), Power(GSymbol, Plus(Times(f, h), Times(CN1, c, g, h, Power(d, -1)))), Power($s("H"), Plus(Times(r, t), Times(CN1, c, s, t, Power(d, -1)))), Power(Times(d, e, Log(FSymbol)), -1), Subst(Int(Times(Power(x, Plus(Numerator(m), Negate(C1))), Power(Plus(a, Times(b, Power(x, Denominator(m)))), n)), x), x, Power(FSymbol, Times(e, Plus(c, Times(d, x)), Power(Denominator(m), -1))))), RationalQ(m))), FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, c, d, e, f, g, h, r, s, t, n), x))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power($p("H"), Times(t_DEFAULT, Plus(r_DEFAULT, Times(s_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(GSymbol, Times(Plus(f, Times(CN1, c, g, Power(d, -1))), h)), Int(Times(Power($s("H"), Times(t, Plus(r, Times(s, x)))), Power(Plus(b, Times(a, Power(FSymbol, Times(CN1, e, Plus(c, Times(d, x)))))), n)), x)), And(And(FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, c, d, e, f, g, h, r, s, t), x), ZeroQ(Plus(Times(d, e, n, Log(FSymbol)), Times(g, h, Log(GSymbol))))), IntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power($p("H"), Times(t_DEFAULT, Plus(r_DEFAULT, Times(s_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Int(Expand(Times(Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power($s("H"), Times(t, Plus(r, Times(s, x)))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), n)), x), x), And(And(FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, c, d, e, f, g, h, r, s, t), x), Not(RationalQ(FullSimplify(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)))))), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power($p("H"), Times(t_DEFAULT, Plus(r_DEFAULT, Times(s_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_)), x_Symbol),
                    Condition(Times(Power(a, n), Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power($s("H"), Times(t, Plus(r, Times(s, x)))), Power(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), -1), Hypergeometric2F1(Negate(n), Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)), Plus(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)), C1), Simplify(Times(CN1, b, Power(a, -1), Power(FSymbol, Times(e, Plus(c, Times(d, x)))))))), And(And(FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, c, d, e, f, g, h, r, s, t), x), Not(RationalQ(FullSimplify(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)))))), NegativeIntegerQ(n)))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, Plus(f_DEFAULT, Times(g_DEFAULT, x_)))), Power($p("H"), Times(t_DEFAULT, Plus(r_DEFAULT, Times(s_DEFAULT, x_)))), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), n_)), x_Symbol),
                    Condition(Times(Power(GSymbol, Times(h, Plus(f, Times(g, x)))), Power($s("H"), Times(t, Plus(r, Times(s, x)))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), n), Power(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(Plus(a, Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Power(a, -1)), n)), -1), Hypergeometric2F1(Negate(n), Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)), Plus(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)), C1), Simplify(Times(CN1, b, Power(a, -1), Power(FSymbol, Times(e, Plus(c, Times(d, x)))))))), And(And(FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, c, d, e, f, g, h, r, s, t, n), x), Not(RationalQ(FullSimplify(Times(Plus(Times(g, h, Log(GSymbol)), Times(s, t, Log($s("H")))), Power(Times(d, e, Log(FSymbol)), -1)))))), Not(IntegerQ(n))))),
            ISetDelayed(Int(Times(Power(G_, Times(h_DEFAULT, u_)), Power($p("H"), Times(t_DEFAULT, w_)), Power(Plus(a_, Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, v_)))), n_)), x_Symbol),
                    Condition(Int(Times(Power(GSymbol, Times(h, ExpandToSum(u, x))), Power($s("H"), Times(t, ExpandToSum(w, x))), Power(Plus(a, Times(b, Power(FSymbol, Times(e, ExpandToSum(v, x))))), n)), x), And(And(FreeQ(List(FSymbol, GSymbol, $s("H"), a, b, e, h, t, n), x), LinearQ(List(u, v, w), x)), Not(LinearMatchQ(List(u, v, w), x))))),
            ISetDelayed(Int(Times(Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), Power(Plus(Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(a_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, n)), Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(p, C1)), Power(Times(b, d, e, Plus(p, C1), Log(FSymbol)), -1)), Times(CN1, a, n, Power(Times(b, d, e, Log(FSymbol)), -1), Int(Times(Power(x, Plus(n, Negate(C1))), Power(Plus(Times(a, Power(x, n)), Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), p)), x))), And(FreeQ(List(FSymbol, a, b, c, d, e, n, p), x), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Times(Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(F_, Times(e_DEFAULT, Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(a_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(x, m), Power(Plus(Times(a, Power(x, n)), Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(p, C1)), Power(Times(b, d, e, Plus(p, C1), Log(FSymbol)), -1)), Times(CN1, a, n, Power(Times(b, d, e, Log(FSymbol)), -1), Int(Times(Power(x, Plus(m, n, Negate(C1))), Power(Plus(Times(a, Power(x, n)), Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), p)), x)), Times(CN1, m, Power(Times(b, d, e, Plus(p, C1), Log(FSymbol)), -1), Int(Times(Power(x, Plus(m, Negate(C1))), Power(Plus(Times(a, Power(x, n)), Times(b, Power(FSymbol, Times(e, Plus(c, Times(d, x)))))), Plus(p, C1))), x))), And(FreeQ(List(FSymbol, a, b, c, d, e, m, n, p), x), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(F_, u_)), Times(c_DEFAULT, Power(F_, v_))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Condition(Plus(Times(C2, c, Power(q, -1), Int(Times(Power(x, m), Power(Plus(b, Negate(q), Times(C2, c, Power(FSymbol, u))), -1)), x)), Times(CN1, C2, c, Power(q, -1), Int(Times(Power(x, m), Power(Plus(b, q, Times(C2, c, Power(FSymbol, u))), -1)), x))), NonzeroQ(q))), And(And(And(FreeQ(List(FSymbol, a, b, c), x), ZeroQ(Plus(v, Times(CN1, C2, u)))), LinearQ(u, x)), PositiveIntegerQ(m)))),
            ISetDelayed(Int(Times(Power(F_, u_), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(F_, u_)), Times(c_DEFAULT, Power(F_, v_))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Condition(Plus(Times(Plus(C1, Times(CN1, b, Power(q, -1))), Int(Times(Power(x, m), Power(Plus(b, Negate(q), Times(C2, c, Power(FSymbol, u))), -1)), x)), Times(Plus(C1, Times(b, Power(q, -1))), Int(Times(Power(x, m), Power(Plus(b, q, Times(C2, c, Power(FSymbol, u))), -1)), x))), NonzeroQ(q))), And(And(And(FreeQ(List(FSymbol, a, b, c), x), ZeroQ(Plus(v, Times(CN1, C2, u)))), LinearQ(u, x)), PositiveIntegerQ(m)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(F_, v_)), Times(a_DEFAULT, Power(F_, Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1)), x_Symbol),
                    Condition(Module(List(Set(u, Block(List(Set($s("§showsteps"), False), Set($s("§stepcounter"), Null)), Int(Power(Plus(Times(a, Power(FSymbol, Plus(c, Times(d, x)))), Times(b, Power(FSymbol, v))), -1), x)))), Plus(Times(Power(x, m), u), Times(CN1, m, Int(Times(Power(x, Plus(m, Negate(C1))), u), x)))), And(And(And(FreeQ(List(FSymbol, a, b, c, d), x), ZeroQ(Plus(c, Times(d, x), v))), RationalQ(m)), Greater(m, C0)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(F_, v_)), Times(c_DEFAULT, Power(F_, w_))), -1)), x_Symbol),
                    Condition(Int(Times(u, Power(FSymbol, v), Power(Plus(c, Times(a, Power(FSymbol, v)), Times(b, Power(FSymbol, Times(C2, v)))), -1)), x), And(And(And(And(FreeQ(List(FSymbol, a, b, c), x), LinearQ(v, x)), LinearQ(w, x)), ZeroQ(Plus(v, w))), If(RationalQ(Coefficient(v, x, C1)), Greater(Coefficient(v, x, C1), C0), Less(LeafCount(v), LeafCount(w)))))),
            ISetDelayed(Int(Times(Power(F_, Times(g_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(FSymbol, Times(g, Power(Plus(d, Times(e, x)), n))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1), x), x), FreeQ(List(FSymbol, a, b, c, d, e, g, n), x))),
            ISetDelayed(Int(Times(Power(F_, Times(g_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT))), Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), -1)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(FSymbol, Times(g, Power(Plus(d, Times(e, x)), n))), Power(Plus(a, Times(c, Sqr(x))), -1), x), x), FreeQ(List(FSymbol, a, c, d, e, g, n), x))),
            ISetDelayed(Int(Times(Power(F_, Times(g_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT))), Power(u_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_, Sqr(x_))), -1)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(FSymbol, Times(g, Power(Plus(d, Times(e, x)), n))), Times(Power(u, m), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), -1)), x), x), And(And(FreeQ(List(FSymbol, a, b, c, d, e, g, n), x), PolynomialQ(u, x)), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(F_, Times(g_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), n_DEFAULT))), Power(u_, m_DEFAULT), Power(Plus(a_, Times(c_, Sqr(x_))), -1)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(FSymbol, Times(g, Power(Plus(d, Times(e, x)), n))), Times(Power(u, m), Power(Plus(a, Times(c, Sqr(x))), -1)), x), x), And(And(FreeQ(List(FSymbol, a, c, d, e, g, n), x), PolynomialQ(u, x)), IntegerQ(m)))),
            ISetDelayed(Int(Power(F_, Times(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, 4))), Power(x_, -2))), x_Symbol),
                    Condition(Plus(Times(Sqrt(Pi), Power(E, Times(C2, Sqrt(Times(CN1, a, Log(FSymbol))), Sqrt(Times(CN1, b, Log(FSymbol))))), Erf(Times(Plus(Sqrt(Times(CN1, a, Log(FSymbol))), Times(Sqrt(Times(CN1, b, Log(FSymbol))), Sqr(x))), Power(x, -1))), Power(Times(C4, Sqrt(Times(CN1, b, Log(FSymbol)))), -1)), Times(CN1, Sqrt(Pi), Power(E, Times(CN2, Sqrt(Times(CN1, a, Log(FSymbol))), Sqrt(Times(CN1, b, Log(FSymbol))))), Erf(Times(Plus(Sqrt(Times(CN1, a, Log(FSymbol))), Times(CN1, Sqrt(Times(CN1, b, Log(FSymbol))), Sqr(x))), Power(x, -1))), Power(Times(C4, Sqrt(Times(CN1, b, Log(FSymbol)))), -1))), FreeQ(List(FSymbol, a, b), x))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Power(E, x_), Power(x_, m_DEFAULT)), n_)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(Power(E, x), Power(x, m)), Plus(n, C1)), Power(Plus(n, C1), -1)), Int(Power(Plus(Power(E, x), Power(x, m)), Plus(n, C1)), x), Times(m, Int(Times(Power(x, Plus(m, Negate(C1))), Power(Plus(Power(E, x), Power(x, m)), n)), x))), And(And(And(RationalQ(m, n), Greater(m, C0)), Less(n, C0)), Unequal(n, CN1)))),
            ISetDelayed(Int(Log(Plus(d_, Times(e_DEFAULT, Power(Power(F_, Times(c_DEFAULT, Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_DEFAULT)))), x_Symbol),
                    Condition(Plus(Times(x, Log(Plus(d, Times(e, Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n))))), Times(CN1, x, Log(Plus(C1, Times(e, Power(d, -1), Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n))))), Int(Log(Plus(C1, Times(e, Power(d, -1), Power(Power(FSymbol, Times(c, Plus(a, Times(b, x)))), n)))), x)), And(FreeQ(List(FSymbol, a, b, c, d, e, n), x), NonzeroQ(Plus(d, Negate(C1)))))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Times(a_DEFAULT, Power(F_, v_)), n_)), x_Symbol),
                    Condition(Times(Power(Times(a, Power(FSymbol, v)), n), Power(Power(FSymbol, Times(n, v)), -1), Int(Times(u, Power(FSymbol, Times(n, v))), x)), And(FreeQ(List(FSymbol, a, n), x), Not(IntegerQ(n))))),
            ISetDelayed(Int(u_, x_Symbol),
                    Condition(Module(List(Set(v, FunctionOfExponential(u, x))), Times(v, Power(D(v, x), -1), Subst(Int(Times(FunctionOfExponentialFunction(u, x), Power(x, -1)), x), x, v))), FunctionOfExponentialQ(u, x))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(a_DEFAULT, Power(F_, v_)), Times(b_DEFAULT, Power(F_, w_))), n_)), x_Symbol),
                    Condition(Int(Times(u, Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(FSymbol, ExpandToSum(Plus(w, Negate(v)), x)))), n)), x), And(And(FreeQ(List(FSymbol, a, b, n), x), NegativeIntegerQ(n)), LinearQ(List(v, w), x)))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(a_DEFAULT, Power(F_, v_)), Times(b_DEFAULT, Power(G_, w_))), n_)), x_Symbol),
                    Condition(Int(Times(u, Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(E, ExpandToSum(Plus(Times(Log(GSymbol), w), Times(CN1, Log(FSymbol), v)), x)))), n)), x), And(And(FreeQ(List(FSymbol, GSymbol, a, b, n), x), NegativeIntegerQ(n)), LinearQ(List(v, w), x)))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(a_DEFAULT, Power(F_, v_)), Times(b_DEFAULT, Power(F_, w_))), n_)), x_Symbol),
                    Condition(Times(Power(Plus(Times(a, Power(FSymbol, v)), Times(b, Power(FSymbol, w))), n), Power(Times(Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(FSymbol, ExpandToSum(Plus(w, Negate(v)), x)))), n)), -1), Int(Times(u, Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(FSymbol, ExpandToSum(Plus(w, Negate(v)), x)))), n)), x)), And(And(FreeQ(List(FSymbol, a, b, n), x), Not(IntegerQ(n))), LinearQ(List(v, w), x)))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(a_DEFAULT, Power(F_, v_)), Times(b_DEFAULT, Power(G_, w_))), n_)), x_Symbol),
                    Condition(Times(Power(Plus(Times(a, Power(FSymbol, v)), Times(b, Power(GSymbol, w))), n), Power(Times(Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(E, ExpandToSum(Plus(Times(Log(GSymbol), w), Times(CN1, Log(FSymbol), v)), x)))), n)), -1), Int(Times(u, Power(FSymbol, Times(n, v)), Power(Plus(a, Times(b, Power(E, ExpandToSum(Plus(Times(Log(GSymbol), w), Times(CN1, Log(FSymbol), v)), x)))), n)), x)), And(And(FreeQ(List(FSymbol, GSymbol, a, b, n), x), Not(IntegerQ(n))), LinearQ(List(v, w), x)))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(F_, v_), Power(G_, w_)), x_Symbol),
                    Condition(Int(Times(u, NormalizeIntegrand(Power(E, Plus(Times(v, Log(FSymbol)), Times(w, Log(GSymbol)))), x)), x), And(FreeQ(List(FSymbol, GSymbol), x), Or(BinomialQ(Plus(v, w), x), And(PolynomialQ(Plus(v, w), x), LessEqual(Exponent(Plus(v, w), x), C2)))))),
            ISetDelayed(Int(Times(y_DEFAULT, Plus(v_, w_), Power(F_, u_)), x_Symbol),
                    Condition(Module(List(Set(z, Times(v, y, Power(Times(Log(FSymbol), D(u, x)), -1)))), Condition(Times(Power(FSymbol, u), z), ZeroQ(Plus(D(z, x), Times(CN1, w, y))))), FreeQ(FSymbol, x))),
            ISetDelayed(Int(Times(w_, Power(F_, u_), Power(v_, n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(z, Plus(Times(Log(FSymbol), v, D(u, x)), Times(Plus(n, C1), D(v, x))))), Condition(Times(Coefficient(w, x, Exponent(w, x)), Power(Coefficient(z, x, Exponent(z, x)), -1), Power(FSymbol, u), Power(v, Plus(n, C1))), And(Equal(Exponent(w, x), Exponent(z, x)), ZeroQ(Plus(Times(w, Coefficient(z, x, Exponent(z, x))), Times(CN1, z, Coefficient(w, x, Exponent(w, x)))))))), And(And(And(FreeQ(List(FSymbol, n), x), PolynomialQ(u, x)), PolynomialQ(v, x)), PolynomialQ(w, x))))
    );
}
