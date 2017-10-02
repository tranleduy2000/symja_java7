package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcCosh;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.ArcTan;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D3;
import static org.matheclipse.core.expression.F.C1DSqrt3;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.C5;
import static org.matheclipse.core.expression.F.C7;
import static org.matheclipse.core.expression.F.C9;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CSqrt3;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Denominator;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.GreaterEqual;
import static org.matheclipse.core.expression.F.Hypergeometric2F1;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.LessEqual;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Simplify;
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
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FractionQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RemoveContent;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumSimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TogetherSimplify;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules1 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_, Times(b_DEFAULT, x_))), Sqrt(Plus(c_, Times(d_DEFAULT, x_)))), -1), x_Symbol),
                    Condition(Times(ArcCosh(Times(b, x, Power(a, -1))), Power(b, -1)), And(And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(b, Negate(d)))), ZeroQ(Plus(a, c))), PositiveQ(a)))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_))), Sqrt(Plus(c_, Times(d_DEFAULT, x_)))), -1), x_Symbol),
                    Condition(Times(C2, Power(b, -1), Subst(Int(Power(Plus(c, Negate(a), Sqr(x)), CN1D2), x), x, Sqrt(Plus(a, Times(b, x))))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(b, Negate(d)))))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_, Times(b_DEFAULT, x_))), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), -1), x_Symbol),
                    Condition(Times(ArcSin(Times(Plus(a, Negate(c), Times(C2, b, x)), Power(Plus(a, c), -1))), Power(b, -1)), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(b, d))), PositiveQ(Plus(a, c))))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_))), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), -1), x_Symbol),
                    Condition(Times(C2, Power(b, CN1D2), Subst(Int(Power(Plus(Times(b, c), Times(CN1, a, d), Times(d, Sqr(x))), CN1D2), x), x, Sqrt(Plus(a, Times(b, x))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PositiveQ(b)), Not(And(PositiveQ(d), NegativeQ(Plus(Times(b, c), Times(CN1, a, d)))))))),
            ISetDelayed(Int(Power(Times(Sqrt(Plus(a_DEFAULT, Times(b_DEFAULT, x_))), Sqrt(Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), -1), x_Symbol),
                    Condition(Times(C2, Subst(Int(Power(Plus(b, Times(CN1, d, Sqr(x))), -1), x), x, Times(Sqrt(Plus(a, Times(b, x))), Power(Plus(c, Times(d, x)), CN1D2)))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(Power(Plus(Times(a, c), Times(b, d, Sqr(x))), m), x), And(And(And(FreeQ(List(a, b, c, d, m, n), x), ZeroQ(Plus(m, Negate(n)))), ZeroQ(Plus(Times(b, c), Times(a, d)))), Or(IntegerQ(m), And(PositiveQ(a), PositiveQ(c)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(c, Times(d, x)), n), Power(Plus(a, Times(b, x)), -1)), x), x), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), IntegerQ(n)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(c, Times(d, x)), n), Power(Times(b, n), -1)), Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1), Int(Times(Power(Plus(c, Times(d, x)), Plus(n, Negate(C1))), Power(Plus(a, Times(b, x)), -1)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(IntegerQ(n))), RationalQ(n)), Greater(n, C0)))),
            ISetDelayed(Int(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Power(Plus(c_, Times(d_DEFAULT, x_)), C1D3)), -1), x_Symbol),
                    Condition(Plus(Times(CN1, Log(Plus(a, Times(b, x))), Power(Times(C2, b, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), -1)), Times(CN1, C3, Power(Times(C2, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), -1), Subst(Int(Times(x, Plus(Times(C2, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), x), Power(Plus(Times(b, c), Times(CN1, a, d), Times(CN1, b, Power(x, 3))), -1)), x), x, Power(Plus(c, Times(d, x)), C1D3)))), And(FreeQ(List(a, b, c, d), x), PosQ(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)))))),
            ISetDelayed(Int(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Power(Plus(c_, Times(d_DEFAULT, x_)), C1D3)), -1), x_Symbol),
                    Condition(Plus(Times(Log(Plus(a, Times(b, x))), Power(Times(C2, b, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), -1)), Times(CN1, C3, Power(Times(C2, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), -1), Subst(Int(Times(x, Plus(Times(C2, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), C1D3)), Negate(x)), Power(Plus(Times(b, c), Times(CN1, a, d), Times(CN1, b, Power(x, 3))), -1)), x), x, Power(Plus(c, Times(d, x)), C1D3)))), And(FreeQ(List(a, b, c, d), x), NegQ(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)))))),
            ISetDelayed(Int(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Power(Plus(c_, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CN1, Log(RemoveContent(Plus(a, Times(b, x)), x)), Power(Times(C2, b, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), -1)), Times(CN1, C3, Power(Times(C2, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), -1), Subst(Int(Times(Plus(Times(C2, Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), Sqr(x)), Power(Plus(Times(b, c), Times(CN1, a, d), Times(CN1, b, Power(x, 3))), -1)), x), x, Power(Plus(c, Times(d, x)), C1D3)))), And(FreeQ(List(a, b, c, d), x), PosQ(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)))))),
            ISetDelayed(Int(Power(Times(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), Power(Plus(c_, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CN1, Log(RemoveContent(Plus(a, Times(b, x)), x)), Power(Times(C2, b, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), -1)), Times(CN1, C3, Power(Times(C2, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), -1), Subst(Int(Times(Plus(Times(C2, Power(Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)), QQ(2L, 3L))), Sqr(x)), Power(Plus(Times(b, c), Times(CN1, a, d), Times(CN1, b, Power(x, 3))), -1)), x), x, Power(Plus(c, Times(d, x)), C1D3)))), And(FreeQ(List(a, b, c, d), x), NegQ(Times(Plus(Times(b, c), Times(CN1, a, d)), Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Module(List(Set(p, Denominator(n))), Times(p, Subst(Int(Times(Power(x, Plus(Times(p, Plus(n, C1)), Negate(C1))), Power(Plus(Times(a, d), Times(CN1, b, c), Times(b, Power(x, p))), -1)), x), x, Power(Plus(c, Times(d, x)), Power(p, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(n)), Less(Less(CN1, n), C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(n, C1), Plus(Times(b, c), Times(CN1, a, d))), -1)), Times(b, Plus(n, C1), Power(Times(Plus(n, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Int(Times(Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Plus(a, Times(b, x)), -1)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(IntegerQ(n))), RationalQ(n)), Less(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), -1), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Times(CN1, Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(n, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Hypergeometric2F1(C1, Plus(n, C1), Plus(n, C2), Times(Plus(c, Times(d, x)), Power(Plus(c, Times(CN1, a, d, Power(b, -1))), -1)))), And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(RationalQ(n))), Or(ZeroQ(a), ZeroQ(c))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_)), -1), Power(Plus(c_, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Times(Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(d, n, Plus(a, Times(b, x))), -1), Hypergeometric2F1(C1, C1, Plus(C1, Negate(n)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(Times(d, Plus(a, Times(b, x))), -1)))), And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(RationalQ(n))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Plus(m, C1)), -1)), And(And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), ZeroQ(Plus(m, n, C2))), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_)), m_), Power(Plus(c_, Times(d_DEFAULT, x_)), m_)), x_Symbol),
                    Condition(Int(Power(Plus(Times(a, c), Times(CN1, b, Plus(a, Negate(c)), x), Times(CN1, Sqr(b), Sqr(x))), m), x), And(And(And(FreeQ(List(a, b, c, d), x), FractionQ(m)), ZeroQ(Plus(b, d))), PositiveQ(Plus(a, c))))),
            ISetDelayed(Int(Times(Power(x_, n_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x)), m), Power(x, n)), x), x), And(And(FreeQ(List(a, b, n), x), PositiveIntegerQ(m)), Or(Or(Not(IntegerQ(n)), LessEqual(Plus(Times(C7, m), Times(C4, n)), C0)), Greater(Plus(m, n, C2), C0))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n)), x), x), And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PositiveIntegerQ(m)), Or(Or(Not(IntegerQ(n)), Less(Plus(Times(C9, m), Times(C5, Plus(n, C1))), C0)), Greater(Plus(m, n, C2), C0))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n)), x), x), And(And(And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NegativeIntegerQ(m)), IntegerQ(n)), Not(And(PositiveIntegerQ(n), Less(Plus(m, n, C2), C0)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n), Power(Times(b, Plus(m, C1)), -1)), Times(CN1, d, n, Power(Times(b, Plus(m, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, n, C2))), RationalQ(m, n)), Less(m, CN1)), Greater(n, C0)), Not(And(IntegerQ(n), Not(IntegerQ(m))))), Not(And(And(IntegerQ(Plus(m, n)), LessEqual(Plus(m, n, C2), C0)), Or(FractionQ(m), GreaterEqual(Plus(Times(C2, n), m, C1), C0))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n), Power(Times(b, Plus(m, n, C1)), -1)), Times(n, Plus(Times(b, c), Times(CN1, a, d)), Power(Times(b, Plus(m, n, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), Plus(n, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Unequal(Plus(m, n, C2), C0)), Greater(n, C0)), Unequal(Plus(m, n, C1), C0)), Not(And(PositiveIntegerQ(m), Or(Not(IntegerQ(n)), Less(Less(C0, m), n))))), Not(And(IntegerQ(Plus(m, n)), Less(Plus(m, n, C2), C0)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n), Power(Times(b, Plus(m, n, C1)), -1)), Times(n, Plus(Times(b, c), Times(CN1, a, d)), Power(Times(b, Plus(m, n, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), Simplify(Plus(n, Negate(C1))))), x))), And(And(And(And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NonzeroQ(Plus(m, n, C1))), Not(RationalQ(n))), IntegerQ(Simplify(Plus(m, n)))), SumSimplerQ(n, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Plus(m, C1)), -1)), Times(CN1, d, Plus(m, n, C2), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Plus(m, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), n)), x))), And(And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Unequal(Plus(m, n, C2), C0)), Less(m, CN1)), Not(And(Less(n, CN1), Or(ZeroQ(a), And(And(NonzeroQ(c), Less(m, n)), IntegerQ(n)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Plus(m, C1)), -1)), Times(CN1, d, Plus(m, n, C2), Power(Times(Plus(Times(b, c), Times(CN1, a, d)), Plus(m, C1)), -1), Int(Times(Power(Plus(a, Times(b, x)), Simplify(Plus(m, C1))), Power(Plus(c, Times(d, x)), n)), x))), And(And(And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(RationalQ(m))), IntegerQ(Simplify(Plus(m, n)))), SumSimplerQ(m, C1)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), m_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), m), Power(Power(Plus(Times(a, c), Times(b, d, Sqr(x))), m), -1), Int(Power(Plus(Times(a, c), Times(b, d, Sqr(x))), m), x)), And(And(FreeQ(List(a, b, c, d, m), x), ZeroQ(Plus(Times(b, c), Times(a, d)))), Not(IntegerQ(m))))),
            ISetDelayed(Int(Power(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), C1D3), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CN1, CSqrt3, Power(Times(Rt(b, C3), Sqr(Rt(d, C3))), -1), ArcTan(Times(C1DSqrt3, Plus(C1, Times(C2, Rt(d, C3), Power(Plus(a, Times(b, x)), C1D3), Power(Times(Rt(b, C3), Power(Plus(c, Times(d, x)), C1D3)), -1)))))), Times(CN1, C3, Power(Times(C2, Rt(b, C3), Sqr(Rt(d, C3))), -1), Log(Plus(Times(Rt(d, C3), Power(Plus(a, Times(b, x)), C1D3)), Times(CN1, Rt(b, C3), Power(Plus(c, Times(d, x)), C1D3)))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PosQ(b)), PosQ(d)))),
            ISetDelayed(Int(Power(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), C1D3), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CN1, CSqrt3, Power(Times(Rt(b, C3), Sqr(Rt(Negate(d), C3))), -1), ArcTan(Times(C1DSqrt3, Plus(C1, Times(CN1, C2, Rt(Negate(d), C3), Power(Plus(a, Times(b, x)), C1D3), Power(Times(Rt(b, C3), Power(Plus(c, Times(d, x)), C1D3)), -1)))))), Times(CN1, C3, Power(Times(C2, Rt(b, C3), Sqr(Rt(Negate(d), C3))), -1), Log(Plus(Times(Rt(Negate(d), C3), Power(Plus(a, Times(b, x)), C1D3)), Times(Rt(b, C3), Power(Plus(c, Times(d, x)), C1D3)))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), PosQ(b)), NegQ(d)))),
            ISetDelayed(Int(Power(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), C1D3), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CSqrt3, Power(Times(Rt(Negate(b), C3), Sqr(Rt(d, C3))), -1), ArcTan(Times(C1DSqrt3, Plus(C1, Times(CN1, C2, Rt(d, C3), Power(Plus(a, Times(b, x)), C1D3), Power(Times(Rt(Negate(b), C3), Power(Plus(c, Times(d, x)), C1D3)), -1)))))), Times(C3, Power(Times(C2, Rt(Negate(b), C3), Sqr(Rt(d, C3))), -1), Log(Plus(Times(Rt(d, C3), Power(Plus(a, Times(b, x)), C1D3)), Times(Rt(Negate(b), C3), Power(Plus(c, Times(d, x)), C1D3)))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NegQ(b)), PosQ(d)))),
            ISetDelayed(Int(Power(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), C1D3), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), QQ(2L, 3L))), -1), x_Symbol),
                    Condition(Plus(Times(CSqrt3, Power(Times(Rt(Negate(b), C3), Sqr(Rt(Negate(d), C3))), -1), ArcTan(Times(C1DSqrt3, Plus(C1, Times(C2, Rt(Negate(d), C3), Power(Plus(a, Times(b, x)), C1D3), Power(Times(Rt(Negate(b), C3), Power(Plus(c, Times(d, x)), C1D3)), -1)))))), Times(C3, Power(Times(C2, Rt(Negate(b), C3), Sqr(Rt(Negate(d), C3))), -1), Log(Plus(Times(Rt(Negate(d), C3), Power(Plus(a, Times(b, x)), C1D3)), Times(CN1, Rt(Negate(b), C3), Power(Plus(c, Times(d, x)), C1D3)))))), And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NegQ(b)), NegQ(d)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Module(List(Set(p, Denominator(m))), Times(p, Subst(Int(Times(Power(x, Plus(Times(p, Plus(m, C1)), Negate(C1))), Power(Plus(b, Times(CN1, d, Power(x, p))), -1)), x), x, Times(Power(Plus(a, Times(b, x)), Power(p, -1)), Power(Power(Plus(c, Times(d, x)), Power(p, -1)), -1))))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Less(Less(CN1, m), C0)), Equal(Plus(m, n, C1), C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_)), x_Symbol),
                    Condition(Module(List(Set(p, Denominator(m))), Times(p, Power(b, -1), Subst(Int(Times(Power(x, Plus(Times(p, Plus(m, C1)), Negate(C1))), Power(Plus(c, Times(CN1, a, d, Power(b, -1)), Times(d, Power(x, p), Power(b, -1))), n)), x), x, Power(Plus(a, Times(b, x)), Power(p, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), RationalQ(m, n)), Less(Less(CN1, m), C0)), Less(Less(CN1, n), C0)), LessEqual(Denominator(n), Denominator(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(d, Plus(m, n, C1)), -1), Hypergeometric2F1(C1, Negate(m), Plus(Negate(m), Negate(n)), Times(CN1, Plus(Times(b, c), Times(CN1, a, d)), Power(Times(d, Plus(a, Times(b, x))), -1)))), And(And(And(FreeQ(List(a, b, c, d, m), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), NegativeIntegerQ(Plus(n, C1))), Not(PositiveIntegerQ(Simplify(Plus(m, n, C2))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(CN1, b, c, Power(d, -1))), m), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(d, Plus(n, C1)), -1), Hypergeometric2F1(Negate(m), Plus(n, C1), Plus(n, C2), TogetherSimplify(Times(Plus(c, Times(d, x)), Power(Plus(c, Times(CN1, a, d, Power(b, -1))), -1))))), And(And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(NegativeIntegerQ(n))), Or(IntegerQ(m), PositiveQ(Plus(a, Times(CN1, b, c, Power(d, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(CN1, Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Plus(c, Times(d, x)), Plus(n, C1)), Power(Times(Plus(n, C1), Plus(Times(b, c), Times(CN1, a, d))), -1), Hypergeometric2F1(C1, Plus(m, n, C2), Plus(n, C2), TogetherSimplify(Times(b, Plus(c, Times(d, x)), Power(Plus(Times(b, c), Times(CN1, a, d)), -1))))), And(And(FreeQ(List(a, b, c, d, m, n), x), NonzeroQ(Plus(Times(b, c), Times(CN1, a, d)))), Not(NegativeIntegerQ(n))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, u_)), m_DEFAULT), Power(Plus(c_DEFAULT, Times(d_DEFAULT, v_)), n_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(b, x)), m), Power(Plus(c, Times(d, x)), n)), x), x, u)), And(And(And(FreeQ(List(a, b, c, d, m, n), x), ZeroQ(Plus(u, Negate(v)))), LinearQ(u, x)), NonzeroQ(Coefficient(u, x, C0))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, n_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), n)), x), And(And(FreeQ(List(m, n), x), LinearQ(List(u, v), x)), Not(LinearMatchQ(List(u, v), x)))))
    );
}
