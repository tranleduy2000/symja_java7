package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.AppellF1;
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
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.j_;
import static org.matheclipse.core.expression.F.j_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules20 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(Power(Power(c, p), -1), Int(Power(Plus(Times(C1D2, b), Times(c, Power(x, n))), Times(C2, p)), x)), And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(x, Plus(Times(C2, a), Times(b, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(C2, a), -1)), And(And(And(And(FreeQ(List(a, b, c, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), ZeroQ(Plus(Times(n, Plus(Times(C2, p), C1)), C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Plus(Times(CN1, x, Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(a, Plus(Times(C2, p), C1)), -1)), Times(x, Plus(Times(C2, a), Times(b, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(C2, a, Plus(n, C1)), -1))), And(And(And(And(And(FreeQ(List(a, b, c, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), ZeroQ(Plus(Times(C2, n, Plus(p, C1)), C1))), NonzeroQ(Plus(Times(C2, p), C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(Sqrt(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n))))), Power(Plus(b, Times(C2, c, Power(x, n))), -1), Int(Times(Plus(b, Times(C2, c, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1D2)))), x)), And(And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Or(ZeroQ(Plus(Times(C2, n, p), C1)), ZeroQ(Plus(Times(n, Plus(Times(C2, p), Negate(C1))), C1)))), RationalQ(p)), Greater(p, C0)), IntegerQ(Plus(p, C1D2))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p)), -1), Int(Power(Plus(b, Times(C2, c, Power(x, n))), Times(C2, p)), x)), And(And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Or(ZeroQ(Plus(Times(C2, n, p), C1)), ZeroQ(Plus(Times(n, Plus(Times(C2, p), Negate(C1))), C1)))), RationalQ(p)), Greater(p, C0)), Not(IntegerQ(Times(C2, p)))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_)))), x_Symbol),
                    Condition(Plus(Times(x, Sqrt(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n))))), Power(Plus(n, C1), -1)), Times(b, n, x, Sqrt(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n))))), Power(Times(Plus(n, C1), Plus(b, Times(C2, c, Power(x, n)))), -1))), And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(n, C1))), NonzeroQ(Plus(Times(C2, n), C1))), NonzeroQ(Plus(Times(C3, n), C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Module(List(Set(g, Times(Sign(n), Power(Denominator(n), -1)))), Times(Power(g, -1), Subst(Int(Times(Power(x, Plus(Power(g, -1), Negate(C1))), Power(Plus(a, Times(b, Power(x, Times(n, Power(g, -1)))), Times(c, Power(x, Times(C2, n, Power(g, -1))))), p)), x), x, Power(x, g)))), And(And(And(And(And(FreeQ(List(a, b, c, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), RationalQ(n)), Not(PositiveIntegerQ(n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Plus(Times(x, Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Plus(Times(C2, n, p), C1), -1)), Times(n, p, x, Plus(Times(C2, a), Times(b, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1))), Power(Times(Plus(Times(C2, n, p), C1), Plus(Times(n, Plus(Times(C2, p), Negate(C1))), C1)), -1)), Times(C2, a, Sqr(n), p, Plus(Times(C2, p), Negate(C1)), Power(Times(Plus(Times(C2, n, p), C1), Plus(Times(n, Plus(Times(C2, p), Negate(C1))), C1)), -1), Int(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1))), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), NonzeroQ(Plus(Times(C2, n, p), C1))), NonzeroQ(Plus(Times(n, Plus(Times(C2, p), Negate(C1))), C1))), RationalQ(p)), Greater(p, C1)))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(n, Plus(Times(C2, p), C1)), C1), x, Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(C2, a, Sqr(n), Plus(p, C1), Plus(Times(C2, p), C1)), -1)), Times(CN1, x, Plus(Times(C2, a), Times(b, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(C2, a, n, Plus(Times(C2, p), C1)), -1)), Times(Plus(Times(n, Plus(Times(C2, p), C1)), C1), Plus(Times(C2, n, Plus(p, C1)), C1), Power(Times(C2, a, Sqr(n), Plus(p, C1), Plus(Times(C2, p), C1)), -1), Int(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), NonzeroQ(Plus(Times(n, Plus(Times(C2, p), C1)), C1))), NonzeroQ(Plus(Times(C2, n, Plus(p, C1)), C1))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Power(Plus(Times(C1D2, b), Times(c, Power(x, n))), Times(C2, p)), -1), Int(Power(Plus(Times(C1D2, b), Times(c, Power(x, n))), Times(C2, p)), x)), And(And(And(FreeQ(List(a, b, c, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), x), x), And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Plus(Times(c, Power(q, -1), Int(Power(Plus(Times(C1D2, b), Times(CN1, C1D2, q), Times(c, Sqr(x))), -1), x)), Times(CN1, c, Power(q, -1), Int(Power(Plus(Times(C1D2, b), Times(C1D2, q), Times(c, Sqr(x))), -1), x)))), And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(a, Power(c, -1)), C2))), Plus(Times(c, q, Power(Times(C2, a), -1), Int(Times(Plus(q, Sqr(x)), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)), Times(c, q, Power(Times(C2, a), -1), Int(Times(Plus(q, Negate(Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)))), And(And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(PositiveQ(Plus(Sqr(b), Times(CN1, C4, a, c))))), Or(NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c))), RationalQ(Times(a, Power(c, -1))))), PosQ(Times(a, Power(c, -1)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Times(CN1, a, Power(c, -1)), C2))), Plus(Times(CN1, c, q, Power(Times(C2, a), -1), Int(Times(Plus(q, Sqr(x)), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)), Times(CN1, c, q, Power(Times(C2, a), -1), Int(Times(Plus(q, Negate(Sqr(x))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), -1)), x)))), And(And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(PositiveQ(Plus(Sqr(b), Times(CN1, C4, a, c))))), Or(NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c))), RationalQ(Times(a, Power(c, -1))))), NegQ(Times(a, Power(c, -1)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Times(C2, c, Rt(Times(a, c), C2)), Times(CN1, b, c)), C2))), Plus(Times(Rt(Times(a, c), C2), Power(Times(C2, a, q), -1), Int(Times(Plus(q, Times(CN1, c, Power(x, Times(C1D2, n)))), Power(Plus(Rt(Times(a, c), C2), Times(CN1, q, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)), Times(Rt(Times(a, c), C2), Power(Times(C2, a, q), -1), Int(Times(Plus(q, Times(c, Power(x, Times(C1D2, n)))), Power(Plus(Rt(Times(a, c), C2), Times(q, Power(x, Times(C1D2, n))), Times(c, Power(x, n))), -1)), x)))), And(And(And(And(And(And(And(FreeQ(List(a, b, c), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(n)), Greater(n, C2)), NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(Times(C1D2, n))), PosQ(Times(a, c))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Plus(Times(c, Power(q, -1), Int(Power(Plus(Times(C1D2, b), Times(CN1, C1D2, q), Times(c, Power(x, n))), -1), x)), Times(CN1, c, Power(q, -1), Int(Power(Plus(Times(C1D2, b), Times(C1D2, q), Times(c, Power(x, n))), -1), x)))), And(And(And(And(And(FreeQ(List(a, b, c), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(n)), Greater(n, C2)), Not(And(And(NegativeQ(Plus(Sqr(b), Times(CN1, C4, a, c))), IntegerQ(Times(C1D2, n))), PosQ(Times(a, c))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), -1), x_Symbol),
                    Condition(Plus(Times(x, Power(a, -1)), Times(CN1, Power(a, -1), Int(Times(Plus(c, Times(b, Power(x, Negate(n)))), Power(Plus(c, Times(b, Power(x, Negate(n))), Times(a, Power(x, Times(CN2, n)))), -1)), x))), And(And(And(FreeQ(List(a, b, c), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NegativeIntegerQ(n)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), CN1D2), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Power(a, CN1D2), Int(Power(Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))))), -1), x))), And(And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveQ(a)), NegativeQ(c)), Or(PositiveQ(b), NegativeQ(b))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr(x_)), Times(c_DEFAULT, Power(x_, 4))), CN1D2), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1)))), Power(Plus(a, Times(b, Sqr(x)), Times(c, Power(x, 4))), CN1D2), Int(Power(Times(Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, Negate(q)), -1)))), Sqrt(Plus(C1, Times(C2, c, Sqr(x), Power(Plus(b, q), -1))))), -1), x))), And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Module(List(Set(g, Times(Sign(n), Power(Denominator(n), -1)))), Times(Power(g, -1), Subst(Int(Times(Power(x, Plus(Power(g, -1), Negate(C1))), Power(Plus(a, Times(b, Power(x, Times(n, Power(g, -1)))), Times(c, Power(x, Times(C2, n, Power(g, -1))))), p)), x), x, Power(x, g)))), And(And(And(And(And(FreeQ(List(a, b, c, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(p, C1))), RationalQ(n)), Not(PositiveIntegerQ(n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Plus(Times(x, Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Plus(Times(C2, n, p), C1), -1)), Times(n, p, Power(Plus(Times(C2, n, p), C1), -1), Int(Times(Plus(Times(C2, a), Times(b, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Greater(p, C0)), Not(IntegerQ(p))), NonzeroQ(Plus(Times(C2, n, p), C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Plus(Times(CN1, x, Plus(Sqr(b), Times(CN1, C2, a, c), Times(b, c, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, j))), Plus(p, C1)), Power(Times(a, n, Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1)), Times(Power(Times(a, n, Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1), Int(Times(Plus(Sqr(b), Times(CN1, C2, a, c), Times(n, Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), Times(b, c, Plus(Times(n, Plus(Times(C2, p), C3)), C1), Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1))), x))), And(And(And(And(FreeQ(List(a, b, c, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), x_Symbol),
                    Condition(Times(x, Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(Times(Power(Plus(C1, Times(C2, c, Power(x, n), Power(Plus(b, Sqrt(Plus(Sqr(b), Times(CN1, C4, a, c)))), -1))), p), Power(Plus(C1, Times(C2, c, Power(x, n), Power(Plus(b, Negate(Sqrt(Plus(Sqr(b), Times(CN1, C4, a, c))))), -1))), p)), -1), AppellF1(Power(n, -1), Negate(p), Negate(p), Plus(C1, Power(n, -1)), Times(CN2, c, Power(x, n), Power(Plus(b, Sqrt(Plus(Sqr(b), Times(CN1, C4, a, c)))), -1)), Times(CN2, c, Power(x, n), Power(Plus(b, Negate(Sqrt(Plus(Sqr(b), Times(CN1, C4, a, c))))), -1)))), And(FreeQ(List(a, b, c, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Power(u_, n_)), Times(c_DEFAULT, Power(v_, j_DEFAULT))), p_), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), x), x, u)), And(And(And(And(FreeQ(List(a, b, c, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(u, Negate(v)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(Times(d_DEFAULT, Power(x_, -1)), n_))), p_DEFAULT), x_Symbol),
                    Condition(Times(CN1, d, Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(Power(d, Times(C2, n)), -1), Power(x, Times(C2, n)))), p), Power(x, -2)), x), x, Times(d, Power(x, -1)))), And(And(FreeQ(List(a, b, c, d, n, p), x), ZeroQ(Plus(j, Times(C2, n)))), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(Times(d_DEFAULT, Power(x_, -1)), j_DEFAULT)), Times(b_DEFAULT, Power(Times(d_DEFAULT, Power(x_, -1)), n_))), p_DEFAULT), x_Symbol),
                    Condition(Times(CN1, d, Subst(Int(Times(Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p), Power(x, -2)), x), x, Times(d, Power(x, -1)))), And(FreeQ(List(a, b, c, d, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Power(u_, p_), x_Symbol),
                    Condition(Int(Power(ExpandToSum(u, x), p), x), And(And(FreeQ(p, x), TrinomialQ(u, x)), Not(TrinomialMatchQ(u, x)))))
    );
}
