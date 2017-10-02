package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CI;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.CSqrt2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Exponent;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.MatchQ;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.PolynomialQ;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Sum;
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
import static org.matheclipse.core.expression.F.d_;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_;
import static org.matheclipse.core.expression.F.g_DEFAULT;
import static org.matheclipse.core.expression.F.h;
import static org.matheclipse.core.expression.F.h_DEFAULT;
import static org.matheclipse.core.expression.F.i;
import static org.matheclipse.core.expression.F.i_DEFAULT;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.j_;
import static org.matheclipse.core.expression.F.k;
import static org.matheclipse.core.expression.F.k_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_DEFAULT;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NiceSqrtQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules32 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(u_, Power(Plus(Times(e_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), Times(f_DEFAULT, Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1)), x_Symbol),
                    Condition(Plus(Times(c, Power(Times(e, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(u, Sqrt(Plus(a, Times(b, x))), Power(x, -1)), x)), Times(CN1, a, Power(Times(f, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(u, Sqrt(Plus(c, Times(d, x))), Power(x, -1)), x))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(Times(a, Sqr(e)), Times(CN1, c, Sqr(f))))))),
            ISetDelayed(Int(Times(u_, Power(Plus(Times(e_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), Times(f_DEFAULT, Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1)), x_Symbol),
                    Condition(Plus(Times(CN1, d, Power(Times(e, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(u, Sqrt(Plus(a, Times(b, x)))), x)), Times(b, Power(Times(f, Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(u, Sqrt(Plus(c, Times(d, x)))), x))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(Times(b, Sqr(e)), Times(CN1, d, Sqr(f))))))),
            ISetDelayed(Int(Times(u_, Power(Plus(Times(e_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), Times(f_DEFAULT, Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1)), x_Symbol),
                    Condition(Plus(Times(e, Int(Times(u, Sqrt(Plus(a, Times(b, x))), Power(Plus(Times(a, Sqr(e)), Times(CN1, c, Sqr(f)), Times(Plus(Times(b, Sqr(e)), Times(CN1, d, Sqr(f))), x)), -1)), x)), Times(CN1, f, Int(Times(u, Sqrt(Plus(c, Times(d, x))), Power(Plus(Times(a, Sqr(e)), Times(CN1, c, Sqr(f)), Times(Plus(Times(b, Sqr(e)), Times(CN1, d, Sqr(f))), x)), -1)), x))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Times(a, Sqr(e)), Times(CN1, c, Sqr(f))))), NonzeroQ(Plus(Times(b, Sqr(e)), Times(CN1, d, Sqr(f))))))),
            ISetDelayed(Int(Times(u_DEFAULT, Power(Plus(Times(d_DEFAULT, Power(x_, n_DEFAULT)), Times(c_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, p_DEFAULT)))))), -1)), x_Symbol),
                    Condition(Plus(Times(CN1, b, Power(Times(a, d), -1), Int(Times(u, Power(x, n)), x)), Times(Power(Times(a, c), -1), Int(Times(u, Sqrt(Plus(a, Times(b, Power(x, Times(C2, n)))))), x))), And(And(FreeQ(List(a, b, c, d, n), x), ZeroQ(Plus(p, Times(CN1, C2, n)))), ZeroQ(Plus(Times(b, Sqr(c)), Negate(Sqr(d))))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(d_DEFAULT, Power(x_, n_DEFAULT)), Times(c_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, p_DEFAULT)))))), -1)), x_Symbol),
                    Condition(Plus(Times(CN1, d, Int(Times(Power(x, Plus(m, n)), Power(Plus(Times(a, Sqr(c)), Times(Plus(Times(b, Sqr(c)), Negate(Sqr(d))), Power(x, Times(C2, n)))), -1)), x)), Times(c, Int(Times(Power(x, m), Sqrt(Plus(a, Times(b, Power(x, Times(C2, n))))), Power(Plus(Times(a, Sqr(c)), Times(Plus(Times(b, Sqr(c)), Negate(Sqr(d))), Power(x, Times(C2, n)))), -1)), x))), And(And(FreeQ(List(a, b, c, d, m, n), x), ZeroQ(Plus(p, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(b, Sqr(c)), Negate(Sqr(d))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(ASymbol, Subst(Int(Power(Plus(d, Times(CN1, Plus(Times(b, d), Times(CN1, C2, a, e)), Sqr(x))), -1), x), x, Times(x, Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), CN1D2)))), And(And(FreeQ(List(a, b, c, d, e, ASymbol, BSymbol), x), ZeroQ(Plus(Times(BSymbol, d), Times(ASymbol, e)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Sqrt(Plus(a_, Times(c_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(ASymbol, Subst(Int(Power(Plus(d, Times(C2, a, e, Sqr(x))), -1), x), x, Times(x, Power(Plus(a, Times(c, Power(x, 4))), CN1D2)))), And(And(FreeQ(List(a, c, d, e, ASymbol, BSymbol), x), ZeroQ(Plus(Times(BSymbol, d), Times(ASymbol, e)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, 4))), Power(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_)), Times(f_DEFAULT, Power(x_, 4))), Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(ASymbol, Subst(Int(Power(Plus(d, Times(CN1, Plus(Times(b, d), Times(CN1, a, e)), Sqr(x))), -1), x), x, Times(x, Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), CN1D2)))), And(And(FreeQ(List(a, b, c, d, e, f, ASymbol, BSymbol), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))), ZeroQ(Plus(Times(a, BSymbol), Times(ASymbol, c)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, 4))), Power(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_)), Times(f_DEFAULT, Power(x_, 4))), Sqrt(Plus(a_, Times(c_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(ASymbol, Subst(Int(Power(Plus(d, Times(a, e, Sqr(x))), -1), x), x, Times(x, Power(Plus(a, Times(c, Power(x, 4))), CN1D2)))), And(And(FreeQ(List(a, c, d, e, f, ASymbol, BSymbol), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))), ZeroQ(Plus(Times(a, BSymbol), Times(ASymbol, c)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, 4))), Power(Times(Plus(d_, Times(f_DEFAULT, Power(x_, 4))), Sqrt(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(ASymbol, Subst(Int(Power(Plus(d, Times(CN1, b, d, Sqr(x))), -1), x), x, Times(x, Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), CN1D2)))), And(And(FreeQ(List(a, b, c, d, f, ASymbol, BSymbol), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))), ZeroQ(Plus(Times(a, BSymbol), Times(ASymbol, c)))))),
            ISetDelayed(Int(Power(Plus(g_DEFAULT, Times(h_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))))), n_))), p_DEFAULT), x_Symbol),
                    Condition(Times(C2, Subst(Int(Times(Power(Plus(g, Times(h, Power(x, n))), p), Plus(Times(Sqr(d), e), Times(CN1, Plus(Times(b, d), Times(CN1, a, e)), Sqr(f)), Times(CN1, Plus(Times(C2, d, e), Times(CN1, b, Sqr(f))), x), Times(e, Sqr(x))), Power(Plus(Times(CN2, d, e), Times(b, Sqr(f)), Times(C2, e, x)), -2)), x), x, Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)))))))), And(And(FreeQ(List(a, b, c, d, e, f, g, h, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(g_DEFAULT, Times(h_DEFAULT, Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_, Times(c_DEFAULT, Sqr(x_)))))), n_))), p_DEFAULT), x_Symbol),
                    Condition(Times(Power(Times(C2, e), -1), Subst(Int(Times(Power(Plus(g, Times(h, Power(x, n))), p), Plus(Sqr(d), Times(a, Sqr(f)), Times(CN1, C2, d, x), Sqr(x)), Power(Plus(d, Negate(x)), -2)), x), x, Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))))), And(And(FreeQ(List(a, c, d, e, f, g, h, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(g_DEFAULT, Times(h_DEFAULT, Power(Plus(u_, Times(f_DEFAULT, Sqrt(v_))), n_))), p_DEFAULT), x_Symbol),
                    Condition(Int(Power(Plus(g, Times(h, Power(Plus(ExpandToSum(u, x), Times(f, Sqrt(ExpandToSum(v, x)))), n))), p), x), And(And(And(And(And(FreeQ(List(f, g, h, n), x), LinearQ(u, x)), QuadraticQ(v, x)), Not(And(LinearMatchQ(u, x), QuadraticMatchQ(v, x)))), ZeroQ(Plus(Sqr(Coefficient(u, x, C1)), Times(CN1, Coefficient(v, x, C2), Sqr(f))))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_)), m_DEFAULT), Power(Plus(Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(Power(C2, Plus(m, C1)), Power(e, Plus(m, C1))), -1), Subst(Int(Times(Power(x, Plus(n, Negate(m), Negate(C2))), Plus(Times(a, Sqr(f)), Sqr(x)), Power(Plus(Times(CN1, a, Sqr(f), h), Times(C2, e, g, x), Times(h, Sqr(x))), m)), x), x, Plus(Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))))), And(And(FreeQ(List(a, c, e, f, g, h, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(x_, p_DEFAULT), Power(Plus(g_, Times(i_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_, Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(Power(C2, Plus(Times(C2, m), p, C1)), Power(e, Plus(p, C1)), Power(f, Times(C2, m))), -1), Power(Times(i, Power(c, -1)), m), Subst(Int(Times(Power(x, Plus(n, Times(CN1, C2, m), Negate(p), Negate(C2))), Power(Plus(Times(CN1, a, Sqr(f)), Sqr(x)), p), Power(Plus(Times(a, Sqr(f)), Sqr(x)), Plus(Times(C2, m), C1))), x), x, Plus(Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))))), And(And(And(And(FreeQ(List(a, c, e, f, g, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), IntegersQ(p, Times(C2, m))), Or(IntegerQ(m), PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_), Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(C2, Power(Power(f, Times(C2, m)), -1), Power(Times(i, Power(c, -1)), m), Subst(Int(Times(Power(x, n), Power(Plus(Times(Sqr(d), e), Times(CN1, Plus(Times(b, d), Times(CN1, a, e)), Sqr(f)), Times(CN1, Plus(Times(C2, d, e), Times(CN1, b, Sqr(f))), x), Times(e, Sqr(x))), Plus(Times(C2, m), C1)), Power(Power(Plus(Times(CN2, d, e), Times(b, Sqr(f)), Times(C2, e, x)), Times(C2, Plus(m, C1))), -1)), x), x, Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)))))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, h, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), ZeroQ(Plus(Times(c, h), Times(CN1, b, i)))), IntegerQ(Times(C2, m))), Or(IntegerQ(m), PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_, Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_, Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(Power(C2, Plus(Times(C2, m), C1)), e, Power(f, Times(C2, m))), -1), Power(Times(i, Power(c, -1)), m), Subst(Int(Times(Power(x, n), Power(Plus(Sqr(d), Times(a, Sqr(f)), Times(CN1, C2, d, x), Sqr(x)), Plus(Times(C2, m), C1)), Power(Power(Plus(Negate(d), x), Times(C2, Plus(m, C1))), -1)), x), x, Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))))), And(And(And(And(FreeQ(List(a, c, d, e, f, g, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), IntegerQ(Times(C2, m))), Or(IntegerQ(m), PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_), Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(i, Power(c, -1)), Plus(m, Negate(C1D2))), Sqrt(Plus(g, Times(h, x), Times(i, Sqr(x)))), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), CN1D2), Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)))))), n)), x)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, h, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), ZeroQ(Plus(Times(c, h), Times(CN1, b, i)))), PositiveIntegerQ(Plus(m, C1D2))), Not(PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_, Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_, Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(i, Power(c, -1)), Plus(m, Negate(C1D2))), Sqrt(Plus(g, Times(i, Sqr(x)))), Power(Plus(a, Times(c, Sqr(x))), CN1D2), Int(Times(Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))), n)), x)), And(And(And(And(FreeQ(List(a, c, d, e, f, g, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), PositiveIntegerQ(Plus(m, C1D2))), Not(PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_DEFAULT, Times(h_DEFAULT, x_), Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(i, Power(c, -1)), Plus(m, C1D2)), Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)))), Power(Plus(g, Times(h, x), Times(i, Sqr(x))), CN1D2), Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)))))), n)), x)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, g, h, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), ZeroQ(Plus(Times(c, h), Times(CN1, b, i)))), NegativeIntegerQ(Plus(m, Negate(C1D2)))), Not(PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqrt(Plus(a_, Times(c_DEFAULT, Sqr(x_)))))), n_DEFAULT), Power(Plus(g_, Times(i_DEFAULT, Sqr(x_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(i, Power(c, -1)), Plus(m, C1D2)), Sqrt(Plus(a, Times(c, Sqr(x)))), Power(Plus(g, Times(i, Sqr(x))), CN1D2), Int(Times(Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqrt(Plus(a, Times(c, Sqr(x)))))), n)), x)), And(And(And(And(FreeQ(List(a, c, d, e, f, g, i, n), x), ZeroQ(Plus(Sqr(e), Times(CN1, c, Sqr(f))))), ZeroQ(Plus(Times(c, g), Times(CN1, a, i)))), NegativeIntegerQ(Plus(m, Negate(C1D2)))), Not(PositiveQ(Times(i, Power(c, -1))))))),
            ISetDelayed(Int(Times(Power(w_, m_DEFAULT), Power(Plus(u_, Times(f_DEFAULT, Sqrt(v_))), n_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(w, x), m), Power(Plus(ExpandToSum(u, x), Times(f, Sqrt(ExpandToSum(v, x)))), n)), x), And(And(And(And(FreeQ(List(f, n), x), LinearQ(u, x)), QuadraticQ(List(v, w), x)), Not(And(LinearMatchQ(u, x), QuadraticMatchQ(List(v, w), x)))), ZeroQ(Plus(Sqr(Coefficient(u, x, C1)), Times(CN1, Coefficient(v, x, C2), Sqr(f))))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Power(x_, n_DEFAULT))), Sqrt(Plus(Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT))))), -1), x_Symbol),
                    Condition(Times(Power(a, -1), Subst(Int(Power(Plus(C1, Times(CN1, c, Sqr(x))), -1), x), x, Times(x, Power(Plus(Times(c, Sqr(x)), Times(d, Power(Plus(a, Times(b, Power(x, n))), Times(C2, Power(n, -1))))), CN1D2)))), And(FreeQ(List(a, b, c, d, n), x), ZeroQ(Plus(p, Times(CN1, C2, Power(n, -1))))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))))), x_Symbol),
                    Condition(Plus(Times(C2, Sqr(b), d, Power(x, 3), Power(Times(C3, Power(Plus(a, Times(b, Sqrt(Plus(c, Times(d, Sqr(x)))))), QQ(3L, 2L))), -1)), Times(C2, a, x, Power(Plus(a, Times(b, Sqrt(Plus(c, Times(d, Sqr(x)))))), CN1D2))), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Times(CN1, Sqr(b), c)))))),
            ISetDelayed(Int(Times(Sqrt(Plus(Times(a_DEFAULT, Sqr(x_)), Times(b_DEFAULT, x_, Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))))), Power(Times(x_, Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), -1)), x_Symbol),
                    Condition(Times(CSqrt2, b, Power(a, -1), Subst(Int(Power(Plus(C1, Times(Sqr(x), Power(a, -1))), CN1D2), x), x, Plus(Times(a, x), Times(b, Sqrt(Plus(c, Times(d, Sqr(x)))))))), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Times(CN1, Sqr(b), d)))), ZeroQ(Plus(Times(Sqr(b), c), a))))),
            ISetDelayed(Int(Times(Sqrt(Times(e_DEFAULT, x_, Plus(Times(a_DEFAULT, x_), Times(b_DEFAULT, Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_)))))))), Power(Times(x_, Sqrt(Plus(c_, Times(d_DEFAULT, Sqr(x_))))), -1)), x_Symbol),
                    Condition(Int(Times(Sqrt(Plus(Times(a, e, Sqr(x)), Times(b, e, x, Sqrt(Plus(c, Times(d, Sqr(x))))))), Power(Times(x, Sqrt(Plus(c, Times(d, Sqr(x))))), -1)), x), And(And(FreeQ(List(a, b, c, d, e), x), ZeroQ(Plus(Sqr(a), Times(CN1, Sqr(b), d)))), ZeroQ(Plus(Times(Sqr(b), c, e), a))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), CN1D2), Sqrt(Plus(Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Sqrt(Plus(a_, Times(b_DEFAULT, Power(x_, 4)))))))), x_Symbol),
                    Condition(Times(d, Subst(Int(Power(Plus(C1, Times(CN1, C2, c, Sqr(x))), -1), x), x, Times(x, Power(Plus(Times(c, Sqr(x)), Times(d, Sqrt(Plus(a, Times(b, Power(x, 4)))))), CN1D2)))), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(c), Times(CN1, b, Sqr(d))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(e_DEFAULT, Power(x_, 4))), CN1D2), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), m_DEFAULT), Sqrt(Plus(Times(b_DEFAULT, Sqr(x_)), Sqrt(Plus(a_, Times(e_DEFAULT, Power(x_, 4))))))), x_Symbol),
                    Condition(Plus(Times(C1D2, Plus(C1, Negate(CI)), Int(Times(Power(Plus(c, Times(d, x)), m), Power(Plus(Sqrt(a), Times(CN1, CI, b, Sqr(x))), CN1D2)), x)), Times(C1D2, Plus(C1, CI), Int(Times(Power(Plus(c, Times(d, x)), m), Power(Plus(Sqrt(a), Times(CI, b, Sqr(x))), CN1D2)), x))), And(And(FreeQ(List(a, b, c, d, m), x), ZeroQ(Plus(e, Negate(Sqr(b))))), PositiveQ(a)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, j_)), Times(c_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Times(Sqr(ASymbol), Plus(n, Negate(C1)), Subst(Int(Power(Plus(a, Times(Sqr(ASymbol), b, Sqr(Plus(n, Negate(C1))), Sqr(x))), -1), x), x, Times(x, Power(Plus(Times(ASymbol, Plus(n, Negate(C1))), Times(CN1, BSymbol, Power(x, n))), -1)))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(n, Negate(C2)))), ZeroQ(Plus(Times(a, Sqr(BSymbol)), Times(CN1, Sqr(ASymbol), d, Sqr(Plus(n, Negate(C1))))))), ZeroQ(Plus(Times(BSymbol, c), Times(C2, ASymbol, d, Plus(n, Negate(C1)))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_DEFAULT))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(d_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, k_DEFAULT)), Times(c_DEFAULT, Power(x_, n_DEFAULT))), -1)), x_Symbol),
                    Condition(Times(Sqr(ASymbol), Plus(m, Negate(n), C1), Power(Plus(m, C1), -1), Subst(Int(Power(Plus(a, Times(Sqr(ASymbol), b, Sqr(Plus(m, Negate(n), C1)), Sqr(x))), -1), x), x, Times(Power(x, Plus(m, C1)), Power(Plus(Times(ASymbol, Plus(m, Negate(n), C1)), Times(BSymbol, Plus(m, C1), Power(x, n))), -1)))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(k, Times(CN1, C2, Plus(m, C1))))), ZeroQ(Plus(Times(a, Sqr(BSymbol), Sqr(Plus(m, C1))), Times(CN1, Sqr(ASymbol), d, Sqr(Plus(m, Negate(n), C1)))))), ZeroQ(Plus(Times(BSymbol, c, Plus(m, C1)), Times(CN1, C2, ASymbol, d, Plus(m, Negate(n), C1))))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Sum(Times(Power(x, i), Plus(Coefficient(u, x, i), Times(Coefficient(u, x, Plus(n, i)), Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), List(i, C0, Plus(n, Negate(C1)))), x), And(And(And(And(And(And(And(FreeQ(List(a, b, c, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(n)), PolynomialQ(u, x)), Less(Less(C1, Exponent(u, x)), Times(C2, n))), Not(BinomialQ(u, x))), Or(NonzeroQ(Plus(p, C1)), Not(NiceSqrtQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))), Not(MatchQ(u, Condition(Power(x, m_), FreeQ(m, x))))))),
            ISetDelayed(Int(Times(u_, Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Sum(Times(Power(x, Plus(m, i)), Plus(Coefficient(u, x, i), Times(Coefficient(u, x, Plus(n, i)), Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), List(i, C0, Plus(n, Negate(C1)))), x), And(And(And(And(And(And(FreeQ(List(a, b, c, m, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(n)), PolynomialQ(u, x)), Less(Less(C1, Exponent(u, x)), Times(C2, n))), Not(BinomialQ(u, x))), Or(NonzeroQ(Plus(p, C1)), Not(NiceSqrtQ(Plus(Sqr(b), Times(CN1, C4, a, c))))))))
    );
}
