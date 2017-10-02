package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Denominator;
import static org.matheclipse.core.expression.F.EllipticPi;
import static org.matheclipse.core.expression.F.EvenQ;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
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
import static org.matheclipse.core.expression.F.Sign;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.Unequal;
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
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.j_;
import static org.matheclipse.core.expression.F.j_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.r;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.w;
import static org.matheclipse.core.expression.F.w_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules22 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, Times(n, p)), Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(b, Times(c, Power(x, n))), p)), x), And(And(FreeQ(List(b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), Power(Plus(Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, e), Times(CN1, d, c)), Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(b, c, n, Plus(p, C1), Power(x, Times(C2, n, Plus(p, C1)))), -1)), Times(e, Power(c, -1), Int(Times(Power(x, Negate(n)), Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1))), x))), And(And(And(FreeQ(List(b, c, d, e, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), Not(IntegerQ(p))), ZeroQ(Plus(Times(n, Plus(Times(C2, p), C1)), C1))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), Power(Plus(Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Times(e, Power(x, Plus(Negate(n), C1)), Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(c, Plus(Times(n, Plus(Times(C2, p), C1)), C1)), -1)), And(And(And(And(FreeQ(List(b, c, d, e, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), Not(IntegerQ(p))), NonzeroQ(Plus(Times(n, Plus(Times(C2, p), C1)), C1))), ZeroQ(Plus(Times(b, e, Plus(Times(n, p), C1)), Times(CN1, c, d, Plus(Times(n, Plus(Times(C2, p), C1)), C1))))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), Power(Plus(Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(e, Power(x, Plus(Negate(n), C1)), Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(c, Plus(Times(n, Plus(Times(C2, p), C1)), C1)), -1)), Times(CN1, Plus(Times(b, e, Plus(Times(n, p), C1)), Times(CN1, c, d, Plus(Times(n, Plus(Times(C2, p), C1)), C1))), Power(Times(c, Plus(Times(n, Plus(Times(C2, p), C1)), C1)), -1), Int(Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), x))), And(And(And(And(FreeQ(List(b, c, d, e, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), Not(IntegerQ(p))), NonzeroQ(Plus(Times(n, Plus(Times(C2, p), C1)), C1))), NonzeroQ(Plus(Times(b, e, Plus(Times(n, p), C1)), Times(CN1, c, d, Plus(Times(n, Plus(Times(C2, p), C1)), C1))))))),
            ISetDelayed(Int(Times(Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT), Power(Plus(Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Times(Power(Plus(Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(Power(x, Times(n, p)), Power(Plus(b, Times(c, Power(x, n))), p)), -1), Int(Times(Power(x, Times(n, p)), Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(b, Times(c, Power(x, n))), p)), x)), And(And(FreeQ(List(b, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Power(c, p), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(Times(C1D2, b), Times(c, Power(x, n))), Times(C2, p))), x)), And(And(And(FreeQ(List(a, b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Sqrt(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n))))), Power(Times(Power(Times(C4, c), Plus(p, Negate(C1D2))), Plus(b, Times(C2, c, Power(x, n)))), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p))), x)), And(And(And(FreeQ(List(a, b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(Plus(p, C1D2))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Plus(b, Times(C2, c, Power(x, n))), Power(Times(Power(Times(C4, c), Plus(p, C1D2)), Sqrt(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))))), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p))), x)), And(And(And(FreeQ(List(a, b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NegativeIntegerQ(Plus(p, Negate(C1D2)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p)), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p))), x)), And(And(And(FreeQ(List(a, b, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(Times(C2, p)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, p)), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), Power(x, n))), p)), x), And(And(And(And(FreeQ(List(a, b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, p)), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), Power(x, n))), p)), x), And(And(And(FreeQ(List(a, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(Power(Plus(d, Times(e, Power(x, n))), p), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(x, n), Power(e, -1))), p)), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, p)), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), Power(x, n))), p)), x)), And(And(And(And(FreeQ(List(a, b, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), p_), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p), Power(Times(Power(Plus(d, Times(e, Power(x, n))), p), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(x, n), Power(e, -1))), p)), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, p)), Power(Plus(Times(a, Power(d, -1)), Times(c, Power(e, -1), Power(x, n))), p)), x)), And(And(And(FreeQ(List(a, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), x, Power(Plus(d, Times(e, Power(x, n))), Plus(m, C1)), Power(Times(d, Sqr(e), n, Plus(m, C1)), -1)), Times(Power(Times(n, Plus(m, C1), d, Sqr(e)), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, C1)), Simp(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e), Plus(Times(n, Plus(m, C1)), C1)), Times(c, d, e, n, Plus(m, C1), Power(x, n))), x)), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, e, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), RationalQ(m)), Unequal(m, CN1)), Not(PositiveIntegerQ(m))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), x, Power(Plus(d, Times(e, Power(x, n))), Plus(m, C1)), Power(Times(d, Sqr(e), n, Plus(m, C1)), -1)), Times(Power(Times(n, Plus(m, C1), d, Sqr(e)), -1), Int(Times(Power(Plus(d, Times(e, Power(x, n))), Plus(m, C1)), Simp(Plus(Times(c, Sqr(d)), Times(a, Sqr(e), Plus(Times(n, Plus(m, C1)), C1)), Times(c, d, e, n, Plus(m, C1), Power(x, n))), x)), x))), And(And(And(And(And(FreeQ(List(a, c, d, e, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), RationalQ(m)), Unequal(m, CN1)), Not(PositiveIntegerQ(m))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Rt(Times(CN1, e, Power(c, -1), Plus(Times(C2, c, d), Times(b, e))), C2))), Plus(Times(Sqr(e), Power(Times(C2, c, r), -1), Int(Times(Plus(r, Times(C2, e, x)), Power(Plus(d, Times(CN1, r, x), Times(CN1, e, Sqr(x))), -1)), x)), Times(Sqr(e), Power(Times(C2, c, r), -1), Int(Times(Plus(r, Times(CN1, C2, e, x)), Power(Plus(d, Times(r, x), Times(CN1, e, Sqr(x))), -1)), x)))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), NegQ(Times(e, Power(c, -1), Plus(Times(C2, c, d), Times(b, e))))), Not(PosQ(Times(e, Power(c, -1), Plus(Times(C2, c, d), Times(CN1, b, e)))))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Rt(Times(CN2, d, e), C2))), Plus(Times(Sqr(e), Power(Times(C2, c, r), -1), Int(Times(Plus(r, Times(C2, e, x)), Power(Plus(d, Times(CN1, r, x), Times(CN1, e, Sqr(x))), -1)), x)), Times(Sqr(e), Power(Times(C2, c, r), -1), Int(Times(Plus(r, Times(CN1, C2, e, x)), Power(Plus(d, Times(r, x), Times(CN1, e, Sqr(x))), -1)), x)))), And(And(FreeQ(List(a, c, d, e), x), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), NegQ(Times(d, e))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Rt(Times(e, Power(c, -1), Plus(Times(C2, c, d), Times(CN1, b, e))), C2))), Plus(Times(Sqr(e), Power(Times(C2, c), -1), Int(Power(Plus(d, Times(CN1, r, x), Times(e, Sqr(x))), -1), x)), Times(Sqr(e), Power(Times(C2, c), -1), Int(Power(Plus(d, Times(r, x), Times(e, Sqr(x))), -1), x)))), And(And(And(And(FreeQ(List(a, b, c, d, e), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), Not(NegativeQ(Times(e, Power(c, -1), Plus(Times(C2, c, d), Times(CN1, b, e)))))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Plus(Times(Sqr(e), Power(Times(C2, c), -1), Int(Power(Plus(d, Times(CN1, Rt(Times(C2, d, e), C2), x), Times(e, Sqr(x))), -1), x)), Times(Sqr(e), Power(Times(C2, c), -1), Int(Power(Plus(d, Times(Rt(Times(C2, d, e), C2), x), Times(e, Sqr(x))), -1), x))), And(And(FreeQ(List(a, c, d, e), x), ZeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), Not(NegativeQ(Times(d, e)))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(a, c), C2))), Plus(Times(Plus(Times(q, d), Times(a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(c, Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)), Times(Plus(Times(q, d), Times(CN1, a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(CN1, c, Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)))), And(And(And(And(FreeQ(List(a, b, c, d, e), x), NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), PosQ(Times(a, c, Power(b, -2)))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(a, c), C2))), Plus(Times(Plus(Times(q, d), Times(a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(c, Sqr(x))), Power(Plus(a, Times(c, Power(x, 4))), -1)), x)), Times(Plus(Times(q, d), Times(CN1, a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(CN1, c, Sqr(x))), Power(Plus(a, Times(c, Power(x, 4))), -1)), x)))), And(And(And(FreeQ(List(a, c, d, e), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), PosQ(Times(a, c))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, c), C2))), Plus(Times(CN1, Plus(Times(q, d), Times(CN1, a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(c, Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)), Times(CN1, Plus(Times(q, d), Times(a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(CN1, c, Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)))), And(And(And(And(FreeQ(List(a, b, c, d, e), x), NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), NegQ(Times(a, c, Power(b, -2)))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, c), C2))), Plus(Times(CN1, Plus(Times(q, d), Times(CN1, a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(c, Sqr(x))), Power(Plus(a, Times(c, Power(x, 4))), -1)), x)), Times(CN1, Plus(Times(q, d), Times(a, e)), Power(Times(C2, a, c), -1), Int(Times(Plus(q, Times(CN1, c, Sqr(x))), Power(Plus(a, Times(c, Power(x, 4))), -1)), x)))), And(And(And(FreeQ(List(a, c, d, e), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, a, Sqr(e))))), NegQ(Times(a, c))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(a, c), C2))), Condition(Module(List(Set(r, Rt(Plus(Times(C2, c, q), Times(CN1, b, c)), C2))), Plus(Times(c, Power(Times(C2, q, r), -1), Int(Times(Plus(Times(d, r), Times(CN1, Plus(Times(c, d), Times(CN1, e, q)), Power(x, Times(C1D2, n)))), Power(Plus(q, Times(CN1, r, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)), Times(c, Power(Times(C2, q, r), -1), Int(Times(Plus(Times(d, r), Times(Plus(Times(c, d), Times(CN1, e, q)), Power(x, Times(C1D2, n)))), Power(Plus(q, Times(r, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)))), Not(NegativeQ(Plus(Times(C2, c, q), Times(CN1, b, c)))))), And(And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), EvenQ(n)), Greater(n, C2)), PosQ(Times(a, c))))),
            ISetDelayed(Int(Times(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(a, c), C2))), Condition(Module(List(Set(r, Rt(Times(C2, c, q), C2))), Plus(Times(c, Power(Times(C2, q, r), -1), Int(Times(Plus(Times(d, r), Times(CN1, Plus(Times(c, d), Times(CN1, e, q)), Power(x, Times(C1D2, n)))), Power(Plus(q, Times(CN1, r, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)), Times(c, Power(Times(C2, q, r), -1), Int(Times(Plus(Times(d, r), Times(Plus(Times(c, d), Times(CN1, e, q)), Power(x, Times(C1D2, n)))), Power(Plus(q, Times(r, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)))), Not(NegativeQ(Times(C2, c, q))))), And(And(And(And(And(FreeQ(List(a, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), EvenQ(n)), Greater(n, C2)), PositiveQ(Times(a, c))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), -1), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), -1)), x), x), And(And(And(And(FreeQ(List(a, b, c, d, e, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), -1), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), -1)), x), x), And(And(And(FreeQ(List(a, c, d, e, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), -1), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), -1), x), x), And(And(And(And(FreeQ(List(a, b, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), Not(IntegerQ(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), -1), Power(Plus(d_, Times(e_DEFAULT, Power(x_, n_))), m_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), -1), x), x), And(And(And(FreeQ(List(a, c, d, e, m, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), Not(IntegerQ(m))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Power(a, CN1D2), Int(Times(Plus(ASymbol, Times(BSymbol, Sqr(x))), Power(Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))))), -1)), x))), And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveQ(a)), NegativeQ(c)), Or(PositiveQ(b), NegativeQ(b))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, c), C2))), Times(Power(a, CN1D2), Int(Times(Plus(ASymbol, Times(BSymbol, Sqr(x))), Power(Times(Sqrt(Plus(C1, Times(CN1, c, Sqr(x), Power(q, -1)))), Sqrt(Plus(C1, Times(c, Sqr(x), Power(q, -1))))), -1)), x))), And(And(FreeQ(List(a, c, ASymbol, BSymbol), x), PositiveQ(a)), NegativeQ(c)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1)))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), CN1D2), Int(Times(Plus(ASymbol, Times(BSymbol, Sqr(x))), Power(Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))))), -1)), x))), And(FreeQ(List(a, b, c, ASymbol, BSymbol), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, c), C2))), Times(Sqrt(Plus(C1, Times(CN1, c, Sqr(x), Power(q, -1)))), Sqrt(Plus(C1, Times(c, Sqr(x), Power(q, -1)))), Power(Plus(a, Times(c, Power(x, 4))), CN1D2), Int(Times(Plus(ASymbol, Times(BSymbol, Sqr(x))), Power(Times(Sqrt(Plus(C1, Times(CN1, c, Sqr(x), Power(q, -1)))), Sqrt(Plus(C1, Times(c, Sqr(x), Power(q, -1))))), -1)), x))), FreeQ(List(a, c, ASymbol, BSymbol), x))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x), x), And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x), x), And(And(FreeQ(List(a, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Module(List(Set(g, Times(Sign(n), Power(Denominator(n), -1)))), Times(Power(g, -1), Subst(Int(Times(Power(x, Plus(Power(g, -1), Negate(C1))), Plus(ASymbol, Times(BSymbol, Power(x, Times(n, Power(g, -1))))), Power(Plus(a, Times(b, Power(x, Times(n, Power(g, -1)))), Times(c, Power(x, Times(C2, n, Power(g, -1))))), p)), x), x, Power(x, g)))), And(And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(p, C1))), RationalQ(n)), Not(PositiveIntegerQ(n))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_)), x_Symbol),
                    Condition(Module(List(Set(g, Times(Sign(n), Power(Denominator(n), -1)))), Times(Power(g, -1), Subst(Int(Times(Power(x, Plus(Power(g, -1), Negate(C1))), Plus(ASymbol, Times(BSymbol, Power(x, Times(n, Power(g, -1))))), Power(Plus(a, Times(c, Power(x, Times(C2, n, Power(g, -1))))), p)), x), x, Power(x, g)))), And(And(And(And(FreeQ(List(a, c, ASymbol, BSymbol, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(p, C1))), RationalQ(n)), Not(PositiveIntegerQ(n))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(x, Plus(Times(b, BSymbol, n, p), Times(c, ASymbol, Plus(Times(C2, n, p), n, C1)), Times(c, BSymbol, Plus(Times(C2, n, p), C1), Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(c, Plus(Times(C2, n, p), C1), Plus(Times(C2, n, p), n, C1)), -1)), Times(n, p, Power(Times(c, Plus(Times(C2, n, p), C1), Plus(Times(C2, n, p), n, C1)), -1), Int(Times(Simp(Plus(Times(C2, a, c, ASymbol, Plus(Times(C2, n, p), n, C1)), Times(CN1, a, b, BSymbol), Times(Plus(Times(C2, a, c, BSymbol, Plus(Times(C2, n, p), C1)), Times(b, ASymbol, c, Plus(Times(C2, n, p), n, C1)), Times(CN1, Sqr(b), BSymbol, Plus(Times(n, p), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Greater(p, C0)), Not(IntegerQ(p))), NonzeroQ(Plus(Times(C2, n, p), C1))), NonzeroQ(Plus(Times(C2, n, p), n, C1))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(x, Plus(Times(ASymbol, Plus(Times(C2, n, p), n, C1)), Times(BSymbol, Plus(Times(C2, n, p), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p), Power(Times(Plus(Times(C2, n, p), C1), Plus(Times(C2, n, p), n, C1)), -1)), Times(C2, a, n, p, Power(Times(Plus(Times(C2, n, p), C1), Plus(Times(C2, n, p), n, C1)), -1), Int(Times(Plus(Times(ASymbol, Plus(Times(C2, n, p), n, C1)), Times(BSymbol, Plus(Times(C2, n, p), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(FreeQ(List(a, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), RationalQ(p)), Greater(p, C0)), Not(IntegerQ(p))), NonzeroQ(Plus(Times(C2, n, p), C1))), NonzeroQ(Plus(Times(C2, n, p), n, C1))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, x, Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(CN1, C2, a, c, ASymbol), Times(Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol)), c, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(a, n, Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1)), Times(Power(Times(a, n, Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1), Int(Times(Simp(Plus(Times(Plus(Times(n, p), n, C1), ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(CN1, C2, a, c, ASymbol, Plus(Times(C2, n, p), Times(C2, n), C1)), Times(Plus(Times(C2, n, p), Times(C3, n), C1), Plus(Times(ASymbol, b), Times(CN1, C2, a, BSymbol)), c, Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1))), x))), And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, x, Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(C2, a, n, Plus(p, C1)), -1)), Times(Power(Times(C2, a, n, Plus(p, C1)), -1), Int(Times(Plus(Times(ASymbol, Plus(Times(C2, n, p), Times(C2, n), C1)), Times(BSymbol, Plus(Times(C2, n, p), Times(C3, n), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1))), x))), And(And(And(FreeQ(List(a, c, ASymbol, BSymbol, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), x)), Times(BSymbol, Int(Times(Power(x, n), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x))), And(FreeQ(List(a, b, c, ASymbol, BSymbol, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p), x)), Times(BSymbol, Int(Times(Power(x, n), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x))), And(FreeQ(List(a, c, ASymbol, BSymbol, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Sqr(x_))), Sqrt(Plus(c_, Times(d_DEFAULT, Power(x_, 4))))), -1), x_Symbol),
                    Condition(Simp(Times(Power(Times(a, Sqrt(c), Rt(Negate(Rt(Times(CN1, d, Power(c, -1)), C2)), C2)), -1), EllipticPi(Times(b, Power(Times(a, Rt(Times(CN1, d, Power(c, -1)), C2)), -1)), ArcSin(Times(Rt(Negate(Rt(Times(CN1, d, Power(c, -1)), C2)), C2), x)), CN1)), x), And(FreeQ(List(a, b, c, d), x), PositiveQ(c)))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, Sqr(x_))), Sqrt(Plus(c_, Times(d_DEFAULT, Power(x_, 4))))), -1), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(c, Times(d, Power(x, 4))), Power(c, -1))), Power(Plus(c, Times(d, Power(x, 4))), CN1D2), Int(Power(Times(Plus(a, Times(b, Sqr(x))), Sqrt(Plus(C1, Times(d, Power(x, 4), Power(c, -1))))), -1), x)), And(FreeQ(List(a, b, c, d), x), Not(PositiveQ(c))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_))), -1), Sqrt(Plus(c_, Times(d_DEFAULT, Power(x_, 4))))), x_Symbol),
                    Condition(Plus(Times(CN1, d, Power(b, -2), Int(Times(Plus(a, Times(CN1, b, Sqr(x))), Power(Plus(c, Times(d, Power(x, 4))), CN1D2)), x)), Times(Plus(Times(Sqr(b), c), Times(Sqr(a), d)), Power(b, -2), Int(Power(Times(Plus(a, Times(b, Sqr(x))), Sqrt(Plus(c, Times(d, Power(x, 4))))), -1), x))), FreeQ(List(a, b, c, d), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), p_), Power(Plus(d_, Times(e_DEFAULT, Sqr(x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Power(a, Plus(p, Negate(C1D2))), Sqrt(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4)))), Power(Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))))), -1), Int(Times(Power(Plus(d, Times(e, Sqr(x))), m), Power(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1))), p), Power(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))), p)), x))), And(And(And(FreeQ(List(a, b, c, d, e, m), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), IntegerQ(Plus(p, Negate(C1D2)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(x_, 4))), p_), Power(Plus(d_, Times(e_DEFAULT, Sqr(x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, c), C2))), Times(Power(a, Plus(p, Negate(C1D2))), Sqrt(Plus(a, Times(c, Power(x, 4)))), Power(Times(Sqrt(Plus(C1, Times(CN1, c, Sqr(x), Power(q, -1)))), Sqrt(Plus(C1, Times(c, Sqr(x), Power(q, -1))))), -1), Int(Times(Power(Plus(d, Times(e, Sqr(x))), m), Power(Plus(C1, Times(CN1, c, Sqr(x), Power(q, -1))), p), Power(Plus(C1, Times(c, Sqr(x), Power(q, -1))), p)), x))), And(And(FreeQ(List(a, c, d, e, m), x), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), IntegerQ(Plus(p, Negate(C1D2)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(v_, n_)), Times(c_DEFAULT, Power(w_, j_DEFAULT))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, Power(u_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x), x, u)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power(w_, j_DEFAULT))), p_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, Power(u_, n_))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, Power(x, n))), m), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x), x, u)), And(And(And(And(FreeQ(List(a, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p)), x), And(And(And(FreeQ(List(m, p), x), BinomialQ(u, x)), TrinomialQ(v, x)), Not(And(BinomialMatchQ(u, x), TrinomialMatchQ(v, x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p)), x), And(And(And(FreeQ(List(m, p), x), BinomialQ(u, x)), BinomialQ(v, x)), Not(And(BinomialMatchQ(u, x), BinomialMatchQ(v, x))))))
    );
}
