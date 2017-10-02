package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C1D3;
import static org.matheclipse.core.expression.F.C1D4;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.C7;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.CN3;
import static org.matheclipse.core.expression.F.CSqrt2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.Denominator;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
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
import static org.matheclipse.core.expression.F.Numerator;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Simplify;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Sum;
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
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.k;
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
import static org.matheclipse.core.expression.F.r;
import static org.matheclipse.core.expression.F.s;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FractionQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Gcd;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearPairQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonsumQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PolynomialDivide;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RemoveContent;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplifyIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumSimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules5 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Times(Log(RemoveContent(Plus(a, Times(b, Power(x, n))), x)), Power(Times(b, n), -1)), And(FreeQ(List(a, b, m, n), x), ZeroQ(Plus(m, Negate(n), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, n, Plus(p, C1)), -1)), And(And(FreeQ(List(a, b, m, n, p), x), ZeroQ(Plus(m, Negate(n), C1))), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, c, Plus(m, C1)), -1)), And(And(And(FreeQ(List(a, b, c, m, n, p), x), NonzeroQ(Plus(m, Negate(n), C1))), ZeroQ(Plus(m, Times(n, p), n, C1))), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Times(Sqr(Plus(a_, Times(b_DEFAULT, Power(x_, n_)))), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Times(c, x), m), Sqr(Plus(a, Times(b, Power(x, n))))), x), x), And(FreeQ(List(a, b, c, m, n), x), NonzeroQ(Plus(m, Times(C3, n), C1))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(c, Times(CN1, n, p)), Int(Times(Power(Times(c, x), Plus(m, Times(n, p))), Power(Plus(b, Times(a, Power(x, Negate(n)))), p)), x)), And(And(And(FreeQ(List(a, b, c, m, n), x), NegQ(n)), IntegerQ(p)), Or(IntegerQ(n), PositiveQ(c))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Times(Power(n, -1), Subst(Int(Times(Power(x, Plus(Simplify(Times(Plus(m, C1), Power(n, -1))), Negate(C1))), Power(Plus(a, Times(b, x)), p)), x), x, Power(x, n))), And(FreeQ(List(a, b, m, n, p), x), IntegerQ(Simplify(Times(Plus(m, C1), Power(n, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(CN1, Power(Times(c, Plus(m, C1)), -1), Subst(Int(Times(Power(Plus(a, Times(b, Power(x, Times(CN1, n, Power(Plus(m, C1), -1))), Power(Power(c, n), -1))), p), Power(x, -2)), x), x, Power(Times(c, x), Negate(Plus(m, C1))))), And(And(And(And(And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Less(Less(CN1, p), C0)), NegativeIntegerQ(Times(n, Power(Plus(m, C1), -1)))), NonzeroQ(Plus(m, C2))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(g, Gcd(Plus(m, C1), n))), Condition(Times(Power(Times(c, g), -1), Subst(Int(Times(Power(x, Plus(Times(Plus(m, C1), Power(g, -1)), Negate(C1))), Power(Plus(a, Times(b, Power(x, Times(n, Power(g, -1))), Power(Power(c, n), -1))), p)), x), x, Power(Times(c, x), g))), Unequal(g, C1))), And(And(And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Less(Less(CN1, p), C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Times(c, x), m), Power(Plus(a, Times(b, Power(x, n))), p)), x), x), And(And(And(And(And(FreeQ(List(a, b, c, m), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n, p)), Not(PositiveIntegerQ(Times(Plus(m, C1), Power(n, -1))))), Or(Or(Not(IntegerQ(Times(Plus(m, C1), Power(n, -1)))), LessEqual(Plus(Times(C7, p), Times(C4, Plus(Times(Plus(m, C1), Power(n, -1)), Negate(C1)))), C0)), Greater(Plus(p, Times(Plus(m, C1), Power(n, -1)), C1), C0))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_)), x_Symbol),
                    Condition(Plus(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, c, Plus(m, C1)), -1)), Times(CN1, b, Plus(m, Times(n, p), n, C1), Power(Times(a, Power(c, n), Plus(m, C1)), -1), Int(Times(Power(Times(c, x), Plus(m, n)), Power(Plus(a, Times(b, Power(x, n))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m)), Less(m, CN1)), NegativeIntegerQ(Times(Plus(m, Times(n, p), n, C1), Power(n, -1)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), p), Power(Times(c, Plus(m, C1)), -1)), Times(CN1, b, n, p, Power(Times(Power(c, n), Plus(m, C1)), -1), Int(Times(Power(Times(c, x), Plus(m, n)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Greater(p, C0)), Less(m, CN1)), Not(NegativeIntegerQ(Times(Plus(m, Times(n, p), n, C1), Power(n, -1))))), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), p), Power(Times(c, Plus(m, Times(n, p), C1)), -1)), Times(a, n, p, Power(Plus(m, Times(n, p), C1), -1), Int(Times(Power(Times(c, x), m), Power(Plus(a, Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, m), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Greater(p, C0)), NonzeroQ(Plus(m, Times(n, p), C1))), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(Power(c, Plus(n, Negate(C1))), Power(Times(c, x), Plus(m, Negate(n), C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, n, Plus(p, C1)), -1)), Times(CN1, Power(c, n), Plus(m, Negate(n), C1), Power(Times(b, n, Plus(p, C1)), -1), Int(Times(Power(Times(c, x), Plus(m, Negate(n))), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Less(p, CN1)), Greater(Plus(m, C1), n)), Not(NegativeIntegerQ(Times(Plus(m, Times(n, p), n, C1), Power(n, -1))))), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, c, n, Plus(p, C1)), -1)), Times(Plus(m, Times(n, p), n, C1), Power(Times(a, n, Plus(p, C1)), -1), Int(Times(Power(Times(c, x), m), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, m), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m, p)), Less(p, CN1)), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(x_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), C3))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), C3)))), Plus(Times(CN1, Sqr(r), Power(Times(C3, a, s), -1), Int(Power(Plus(r, Times(s, x)), -1), x)), Times(Sqr(r), Power(Times(C3, a, s), -1), Int(Times(Plus(r, Times(s, x)), Power(Plus(Sqr(r), Times(CN1, r, s, x), Times(Sqr(s), Sqr(x))), -1)), x)))), And(FreeQ(List(a, b), x), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(x_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), C3))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), C3)))), Plus(Times(Sqr(r), Power(Times(C3, a, s), -1), Int(Power(Plus(r, Times(CN1, s, x)), -1), x)), Times(CN1, Sqr(r), Power(Times(C3, a, s), -1), Int(Times(Plus(r, Times(CN1, s, x)), Power(Plus(Sqr(r), Times(r, s, x), Times(Sqr(s), Sqr(x))), -1)), x)))), And(FreeQ(List(a, b), x), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), n))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), n)))), Int(Plus(Times(CN1, Power(Negate(r), Plus(m, C1)), Power(Times(a, n, Power(s, m), Plus(r, Times(s, x))), -1)), Sum(Times(C2, Power(r, Plus(m, C1)), Plus(Times(r, Cos(Times(Plus(Times(C2, k), Negate(C1)), m, Pi, Power(n, -1)))), Times(CN1, s, Cos(Times(Plus(Times(C2, k), Negate(C1)), Plus(m, C1), Pi, Power(n, -1))), x)), Power(Times(a, n, Power(s, m), Plus(Sqr(r), Times(CN1, C2, r, s, Cos(Times(Plus(Times(C2, k), Negate(C1)), Pi, Power(n, -1))), x), Times(Sqr(s), Sqr(x)))), -1)), List(k, C1, Times(C1D2, Plus(n, Negate(C1)))))), x)), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D2, Plus(n, Negate(C1))))), Less(Less(C0, Plus(m, C1)), n)), Equal(Gcd(Plus(m, C1), n), C1)), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), n))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), n)))), Int(Plus(Times(Power(r, Plus(m, C1)), Power(Times(a, n, Power(s, m), Plus(r, Times(CN1, s, x))), -1)), Negate(Sum(Times(C2, Power(Negate(r), Plus(m, C1)), Plus(Times(r, Cos(Times(Plus(Times(C2, k), Negate(C1)), m, Pi, Power(n, -1)))), Times(s, Cos(Times(Plus(Times(C2, k), Negate(C1)), Plus(m, C1), Pi, Power(n, -1))), x)), Power(Times(a, n, Power(s, m), Plus(Sqr(r), Times(C2, r, s, Cos(Times(Plus(Times(C2, k), Negate(C1)), Pi, Power(n, -1))), x), Times(Sqr(s), Sqr(x)))), -1)), List(k, C1, Times(C1D2, Plus(n, Negate(C1))))))), x)), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D2, Plus(n, Negate(C1))))), Less(Less(C0, Plus(m, C1)), n)), Equal(Gcd(Plus(m, C1), n), C1)), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), Times(C1D2, n)))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), Times(C1D2, n))))), Plus(Times(CN2, Power(Negate(r), Plus(Times(C1D2, m), C1)), Power(Times(a, n, Power(s, Times(C1D2, m))), -1), Int(Power(Plus(r, Times(s, Sqr(x))), -1), x)), Times(C4, Power(r, Plus(Times(C1D2, m), C1)), Power(Times(a, n, Power(s, Times(C1D2, m))), -1), Int(Sum(Times(Plus(Times(r, Cos(Times(Plus(Times(C2, k), Negate(C1)), m, Pi, Power(n, -1)))), Times(CN1, s, Cos(Times(Plus(Times(C2, k), Negate(C1)), Plus(m, C2), Pi, Power(n, -1))), Sqr(x))), Power(Plus(Sqr(r), Times(CN1, C2, r, s, Cos(Times(C2, Plus(Times(C2, k), Negate(C1)), Pi, Power(n, -1))), Sqr(x)), Times(Sqr(s), Power(x, 4))), -1)), List(k, C1, Times(C1D4, Plus(n, Negate(C2))))), x)))), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D4, Plus(n, Negate(C2))))), Less(Less(C0, Plus(m, C1)), n)), Equal(Gcd(Plus(m, C1), n), C1)), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), Times(C1D2, n)))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), Times(C1D2, n))))), Plus(Times(C2, Power(r, Plus(Times(C1D2, m), C1)), Power(Times(a, n, Power(s, Times(C1D2, m))), -1), Int(Power(Plus(r, Times(CN1, s, Sqr(x))), -1), x)), Times(C4, Power(r, Plus(Times(C1D2, m), C1)), Power(Times(a, n, Power(s, Times(C1D2, m))), -1), Int(Sum(Times(Plus(Times(r, Cos(Times(C2, k, m, Pi, Power(n, -1)))), Times(CN1, s, Cos(Times(C2, k, Plus(m, C2), Pi, Power(n, -1))), Sqr(x))), Power(Plus(Sqr(r), Times(CN1, C2, r, s, Cos(Times(C4, k, Pi, Power(n, -1))), Sqr(x)), Times(Sqr(s), Power(x, 4))), -1)), List(k, C1, Times(C1D4, Plus(n, Negate(C2))))), x)))), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D4, Plus(n, Negate(C2))))), Less(Less(C0, Plus(m, C1)), n)), Equal(Gcd(Plus(m, C1), n), C1)), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Sqr(x_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), C2))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), C2)))), Plus(Times(Power(Times(C2, s), -1), Int(Times(Plus(r, Times(s, Sqr(x))), Power(Plus(a, Times(b, Power(x, 4))), -1)), x)), Times(CN1, Power(Times(C2, s), -1), Int(Times(Plus(r, Times(CN1, s, Sqr(x))), Power(Plus(a, Times(b, Power(x, 4))), -1)), x)))), And(FreeQ(List(a, b), x), Or(PositiveQ(Times(a, Power(b, -1))), And(And(PosQ(Times(a, Power(b, -1))), NonsumQ(a)), NonsumQ(b)))))),
            ISetDelayed(Int(Times(Sqr(x_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), C2))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), C2)))), Plus(Times(s, Power(Times(C2, b), -1), Int(Power(Plus(r, Times(s, Sqr(x))), -1), x)), Times(CN1, s, Power(Times(C2, b), -1), Int(Power(Plus(r, Times(CN1, s, Sqr(x))), -1), x)))), And(FreeQ(List(a, b), x), Not(PositiveQ(Times(a, Power(b, -1))))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), C4))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), C4)))), Plus(Times(Power(s, 3), Power(Times(C2, CSqrt2, b, r), -1), Int(Times(Power(x, Plus(m, Times(CN1, C1D4, n))), Power(Plus(Sqr(r), Times(CN1, CSqrt2, r, s, Power(x, Times(C1D4, n))), Times(Sqr(s), Power(x, Times(C1D2, n)))), -1)), x)), Times(CN1, Power(s, 3), Power(Times(C2, CSqrt2, b, r), -1), Int(Times(Power(x, Plus(m, Times(CN1, C1D4, n))), Power(Plus(Sqr(r), Times(CSqrt2, r, s, Power(x, Times(C1D4, n))), Times(Sqr(s), Power(x, Times(C1D2, n)))), -1)), x)))), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D4, n))), Less(Less(C0, Plus(m, C1)), n)), Equal(Gcd(Plus(m, C1), n), C1)), PositiveQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), C2))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), C2)))), Plus(Times(r, Power(Times(C2, a), -1), Int(Times(Power(x, m), Power(Plus(r, Times(s, Power(x, Times(C1D2, n)))), -1)), x)), Times(r, Power(Times(C2, a), -1), Int(Times(Power(x, m), Power(Plus(r, Times(CN1, s, Power(x, Times(C1D2, n)))), -1)), x)))), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D4, n))), Less(Less(C0, m), Times(C1D2, n))), Equal(Gcd(Plus(m, C1), n), C1)), Not(PositiveQ(Times(a, Power(b, -1))))))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), C2))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), C2)))), Plus(Times(s, Power(Times(C2, b), -1), Int(Times(Power(x, Plus(m, Times(CN1, C1D2, n))), Power(Plus(r, Times(s, Power(x, Times(C1D2, n)))), -1)), x)), Times(CN1, s, Power(Times(C2, b), -1), Int(Times(Power(x, Plus(m, Times(CN1, C1D2, n))), Power(Plus(r, Times(CN1, s, Power(x, Times(C1D2, n)))), -1)), x)))), And(And(And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, Times(C1D4, n))), And(LessEqual(Times(C1D2, n), m), Less(m, n))), Equal(Gcd(Plus(m, C1), n), C1)), Not(PositiveQ(Times(a, Power(b, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(g, Gcd(Plus(m, C1), n))), Condition(Times(Power(c, Plus(n, Negate(C1))), Power(g, -1), Subst(Int(Times(Power(x, Plus(Times(Plus(m, C1), Power(g, -1)), Negate(C1))), Power(Plus(Times(a, Power(c, n)), Times(b, Power(x, Times(n, Power(g, -1))))), -1)), x), x, Power(Times(c, x), g))), Unequal(g, C1))), And(And(And(FreeQ(List(a, b, c), x), PositiveIntegerQ(n)), RationalQ(m)), Less(Less(C0, Plus(m, C1)), n)))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Int(PolynomialDivide(Power(x, m), Plus(a, Times(b, Power(x, n))), x), x), And(And(FreeQ(List(a, b), x), PositiveIntegerQ(m, n)), Less(Times(C2, n), Plus(m, C1))))),
            ISetDelayed(Int(Times(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), CN1D2)), x_Symbol),
                    Condition(Times(Sqrt(Plus(Power(a, C1D3), Times(Power(b, C1D3), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(b, QQ(2L, 3L))))), Times(Power(a, C1D3), Power(b, C1D3)), Times(CN1, C2, Power(b, QQ(2L, 3L)), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(b, QQ(2L, 3L))))), Times(CN1, Power(a, C1D3), Power(b, C1D3)), Times(C2, Power(b, QQ(2L, 3L)), x))), Power(Plus(a, Times(b, Power(x, 3))), CN1D2), Int(Times(Plus(c, Times(d, x)), Power(Times(Sqrt(Plus(Power(a, C1D3), Times(Power(b, C1D3), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(b, QQ(2L, 3L))))), Times(Power(a, C1D3), Power(b, C1D3)), Times(CN1, C2, Power(b, QQ(2L, 3L)), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(b, QQ(2L, 3L))))), Times(CN1, Power(a, C1D3), Power(b, C1D3)), Times(C2, Power(b, QQ(2L, 3L)), x)))), -1)), x)), And(FreeQ(List(a, b, c, d), x), PosQ(b)))),
            ISetDelayed(Int(Times(Plus(c_DEFAULT, Times(d_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), CN1D2)), x_Symbol),
                    Condition(Times(Sqrt(Plus(Power(a, C1D3), Times(CN1, Power(Negate(b), C1D3), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(Negate(b), QQ(2L, 3L))))), Times(CN1, Power(a, C1D3), Power(Negate(b), C1D3)), Times(CN1, C2, Power(Negate(b), QQ(2L, 3L)), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(Negate(b), QQ(2L, 3L))))), Times(Power(a, C1D3), Power(Negate(b), C1D3)), Times(C2, Power(Negate(b), QQ(2L, 3L)), x))), Power(Plus(a, Times(b, Power(x, 3))), CN1D2), Int(Times(Plus(c, Times(d, x)), Power(Times(Sqrt(Plus(Power(a, C1D3), Times(CN1, Power(Negate(b), C1D3), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(Negate(b), QQ(2L, 3L))))), Times(CN1, Power(a, C1D3), Power(Negate(b), C1D3)), Times(CN1, C2, Power(Negate(b), QQ(2L, 3L)), x))), Sqrt(Plus(Times(Power(a, C1D3), Sqrt(Times(CN3, Power(Negate(b), QQ(2L, 3L))))), Times(Power(a, C1D3), Power(Negate(b), C1D3)), Times(C2, Power(Negate(b), QQ(2L, 3L)), x)))), -1)), x)), And(FreeQ(List(a, b, c, d), x), NegQ(b)))),
            ISetDelayed(Int(Times(Plus(c_DEFAULT, Times(d_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(Rt(Negate(b), C2), c), Times(CN1, Sqrt(a), d)), Power(Rt(Negate(b), C2), -1), Int(Power(Times(Sqrt(Plus(Sqrt(a), Times(Rt(Negate(b), C2), Sqr(x)))), Sqrt(Plus(Sqrt(a), Times(CN1, Rt(Negate(b), C2), Sqr(x))))), -1), x)), Times(d, Power(Rt(Negate(b), C2), -1), Int(Times(Sqrt(Plus(Sqrt(a), Times(Rt(Negate(b), C2), Sqr(x)))), Power(Plus(Sqrt(a), Times(CN1, Rt(Negate(b), C2), Sqr(x))), CN1D2)), x))), And(FreeQ(List(a, b, c, d), x), PositiveQ(a)))),
            ISetDelayed(Int(Times(Plus(c_DEFAULT, Times(d_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 4))), CN1D2)), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(a, Times(b, Power(x, 4))), Power(a, -1))), Power(Plus(a, Times(b, Power(x, 4))), CN1D2), Int(Times(Plus(c, Times(d, Sqr(x))), Power(Plus(C1, Times(b, Power(x, 4), Power(a, -1))), CN1D2)), x)), And(FreeQ(List(a, b, c, d), x), Not(PositiveQ(a))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_)), x_Symbol),
                    Condition(Plus(Times(Power(c, Plus(n, Negate(C1))), Power(Times(c, x), Plus(m, Negate(n), C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(m, Times(n, p), C1)), -1)), Times(CN1, a, Power(c, n), Plus(m, Negate(n), C1), Power(Times(b, Plus(m, Times(n, p), C1)), -1), Int(Times(Power(Times(c, x), Plus(m, Negate(n))), Power(Plus(a, Times(b, Power(x, n))), p)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m)), Greater(m, Plus(n, Negate(C1)))), NonzeroQ(Plus(m, Times(n, p), C1))), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_)), x_Symbol),
                    Condition(Plus(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, c, Plus(m, C1)), -1)), Times(CN1, b, Plus(m, Times(n, p), n, C1), Power(Times(a, Power(c, n), Plus(m, C1)), -1), Int(Times(Power(Times(c, x), Plus(m, n)), Power(Plus(a, Times(b, Power(x, n))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(n)), RationalQ(m)), Less(m, CN1)), Or(IntegerQ(Times(C2, p)), IntegerQ(Plus(p, Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(g, Gcd(Plus(m, C1), n))), Condition(Times(Power(Times(c, g), -1), Subst(Int(Times(Power(x, Plus(Times(Plus(m, C1), Power(g, -1)), Negate(C1))), Power(Plus(a, Times(b, Power(x, Times(n, Power(g, -1))), Power(Power(c, n), -1))), p)), x), x, Power(Times(c, x), g))), Unequal(g, C1))), And(And(And(And(FreeQ(List(a, b, c, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), NegativeIntegerQ(n)), RationalQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n))), p), Power(Times(Power(Times(c, x), Times(n, p)), Power(Plus(b, Times(a, Power(x, Negate(n)))), p)), -1), Int(Times(Power(Times(c, x), Plus(m, Times(n, p))), Power(Plus(b, Times(a, Power(x, Negate(n)))), p)), x)), And(And(And(And(FreeQ(List(a, b, c, m, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), NegativeIntegerQ(n)), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Times(Power(Plus(m, C1), -1), Subst(Int(Power(Plus(a, Times(b, Power(x, Simplify(Times(n, Power(Plus(m, C1), -1)))))), p), x), x, Power(x, Plus(m, C1)))), And(And(And(And(FreeQ(List(a, b, m, n, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), IntegerQ(Simplify(Times(n, Power(Plus(m, C1), -1))))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Module(List(Set(d, Denominator(n))), Times(d, Subst(Int(Times(Power(x, Plus(Times(d, Plus(m, C1)), Negate(C1))), Power(Plus(a, Times(b, Power(x, Times(d, n)))), p)), x), x, Power(x, Power(d, -1))))), And(And(And(And(And(FreeQ(List(a, b, m, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), FractionQ(n)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(m, C1), -1)), Times(CN1, b, n, p, Power(Plus(m, C1), -1), Int(Times(Power(x, Plus(m, n)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), IntegerQ(Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1)))))), RationalQ(p)), Greater(p, C0)), ZeroQ(Plus(m, Times(n, p), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), p), Power(Plus(m, Times(n, p), C1), -1)), Times(a, n, p, Power(Plus(m, Times(n, p), C1), -1), Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(And(And(FreeQ(List(a, b, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), IntegerQ(Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1)))))), RationalQ(p)), Greater(p, C0)), NonzeroQ(Plus(m, Times(n, p), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, n, Plus(p, C1)), -1)), Times(Plus(m, Times(n, p), n, C1), Power(Times(a, n, Plus(p, C1)), -1), Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), IntegerQ(Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1)))))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set($s("mn"), Simplify(Plus(m, Negate(n))))), Plus(Times(Power(x, Plus($s("mn"), C1)), Power(Times(b, Plus($s("mn"), C1)), -1)), Times(CN1, a, Power(b, -1), Int(Times(Power(x, $s("mn")), Power(Plus(a, Times(b, Power(x, n))), -1)), x)))), And(And(And(And(FreeQ(List(a, b, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), SumSimplerQ(m, Negate(n))))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Power(Times(a, Plus(m, C1)), -1)), Times(CN1, b, Power(a, -1), Int(Times(Power(x, Simplify(Plus(m, n))), Power(Plus(a, Times(b, Power(x, n))), -1)), x))), And(And(And(And(FreeQ(List(a, b, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), Not(IntegerQ(n))), Not(IntegerQ(Times(n, Power(Plus(m, C1), -1))))), SumSimplerQ(m, n)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(c, x), m), Power(Power(x, m), -1), Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n))), p)), x)), And(And(And(And(FreeQ(List(a, b, c, m, n, p), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), Not(IntegerQ(n))), Or(Or(Or(IntegerQ(Simplify(Times(n, Power(Plus(m, C1), -1)))), FractionQ(n)), IntegerQ(Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1)))))), ZeroQ(Plus(p, C1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Module(List(Set(q, Denominator(p))), Times(q, Power(a, Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1))))), Power(n, -1), Subst(Int(Times(Power(x, Plus(Times(q, Simplify(Times(Plus(m, C1), Power(n, -1)))), Negate(C1))), Power(Power(Plus(C1, Times(CN1, b, Power(x, q))), Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1))), C1)), -1)), x), x, Times(Power(x, Times(n, Power(q, -1))), Power(Power(Plus(a, Times(b, Power(x, n))), Power(q, -1)), -1))))), And(And(And(And(FreeQ(List(a, b, m, n), x), RationalQ(p)), Less(Less(CN1, p), C0)), Unequal(p, CN1D2)), IntegerQ(Plus(p, Simplify(Times(Plus(m, C1), Power(n, -1)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Times(c, x), m), Power(Plus(a, Times(b, Power(x, n))), p)), x), x), And(And(And(FreeQ(List(a, b, c, m, n), x), Not(IntegerQ(Times(Plus(m, C1), Power(n, -1))))), NonzeroQ(Plus(m, Times(n, p), n, C1))), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(a, p), Power(Times(c, x), Plus(m, C1)), Power(Times(c, Plus(m, C1)), -1), Hypergeometric2F1(Negate(p), Times(Plus(m, C1), Power(n, -1)), Plus(Times(Plus(m, C1), Power(n, -1)), C1), Times(CN1, b, Power(x, n), Power(a, -1)))), And(And(And(And(FreeQ(List(a, b, c, m, n, p), x), Not(IntegerQ(Simplify(Times(Plus(m, C1), Power(n, -1)))))), Not(NegativeIntegerQ(Simplify(Plus(Times(Plus(m, C1), Power(n, -1)), p))))), Not(PositiveIntegerQ(p))), Or(NegativeIntegerQ(p), PositiveQ(a))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(c, x), Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, c, Plus(m, C1)), -1), Hypergeometric2F1(C1, Plus(Times(Plus(m, C1), Power(n, -1)), p, C1), Plus(Times(Plus(m, C1), Power(n, -1)), C1), Times(CN1, b, Power(x, n), Power(a, -1)))), And(And(And(FreeQ(List(a, b, c, m, n, p), x), Not(IntegerQ(Simplify(Times(Plus(m, C1), Power(n, -1)))))), Not(NegativeIntegerQ(Simplify(Plus(Times(Plus(m, C1), Power(n, -1)), p))))), Not(PositiveIntegerQ(p))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(v_, n_))), p_)), x_Symbol),
                    Condition(Times(Power(Power(Coefficient(v, x, C1), Plus(m, C1)), -1), Subst(Int(SimplifyIntegrand(Times(Power(Plus(x, Negate(Coefficient(v, x, C0))), m), Power(Plus(a, Times(b, Power(x, n))), p)), x), x), x, v)), And(And(And(FreeQ(List(a, b, n, p), x), LinearQ(v, x)), IntegerQ(m)), NonzeroQ(Plus(v, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(v_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(u, m), Power(Times(Coefficient(v, x, C1), Power(v, m)), -1), Subst(Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n))), p)), x), x, v)), And(FreeQ(List(a, b, m, n, p), x), LinearPairQ(u, v, x)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(Times(d_DEFAULT, Power(x_, -1)), n_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(CN1, Power(d, Plus(m, C1)), Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n))), p), Power(Power(x, Plus(m, C2)), -1)), x), x, Times(d, Power(x, -1)))), And(FreeQ(List(a, b, d, n, p), x), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(Times(d_DEFAULT, Power(x_, -1)), n_))), p_DEFAULT), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Times(CN1, d, Power(Times(c, x), m), Power(Times(d, Power(x, -1)), m), Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n))), p), Power(Power(x, Plus(m, C2)), -1)), x), x, Times(d, Power(x, -1)))), And(FreeQ(List(a, b, c, d, m, n, p), x), Not(IntegerQ(m))))),
            ISetDelayed(Int(Times(Power(u_, p_DEFAULT), Power(Times(c_DEFAULT, x_), m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(Times(c, x), m), Power(ExpandToSum(u, x), p)), x), And(And(FreeQ(List(c, m, p), x), BinomialQ(u, x)), Not(BinomialMatchQ(u, x))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(Times(c_DEFAULT, Power(x_, n_)), q_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(x, Plus(m, C1)), Power(Power(Times(c, Power(x, n)), Times(Plus(m, C1), Power(n, -1))), -1), Subst(Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, Times(n, q)))), p)), x), x, Power(Times(c, Power(x, n)), Power(n, -1)))), And(And(FreeQ(List(a, b, c, m, n, p, q), x), IntegerQ(m)), IntegerQ(Times(n, q)))))
    );
}
