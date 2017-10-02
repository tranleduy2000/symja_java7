package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.ArcSinh;
import static org.matheclipse.core.expression.F.ArcTan;
import static org.matheclipse.core.expression.F.ArcTanh;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Cancel;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.Hypergeometric2F1;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.Unequal;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PerfectSquareQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules12 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Int(Cancel(Times(Power(Plus(Times(C1D2, b), Times(c, x)), Times(C2, p)), Power(Power(c, p), -1))), x), And(And(FreeQ(List(a, b, c), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), CN1D2), x_Symbol),
                    Condition(Times(Plus(Times(C1D2, b), Times(c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), CN1D2), Int(Power(Plus(Times(C1D2, b), Times(c, x)), -1), x)), And(FreeQ(List(a, b, c), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Times(Plus(b, Times(C2, c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), Power(Times(C2, c, Plus(Times(C2, p), C1)), -1)), And(And(And(FreeQ(List(a, b, c, p), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(p))), NonzeroQ(Plus(Times(C2, p), C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Plus(Times(c, Power(q, -1), Int(Power(Simp(Plus(Times(C1D2, b), Times(CN1, C1D2, q), Times(c, x)), x), -1), x)), Times(CN1, c, Power(q, -1), Int(Power(Simp(Plus(Times(C1D2, b), Times(C1D2, q), Times(c, x)), x), -1), x)))), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PosQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PerfectSquareQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), x_Symbol),
                    Condition(Times(CN2, ArcTanh(Times(Plus(b, Times(C2, c, x)), Power(Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2), -1))), Power(Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2), -1)), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PosQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(PerfectSquareQ(Plus(Sqr(b), Times(CN1, C4, a, c))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), x_Symbol),
                    Condition(Times(C2, ArcTan(Plus(Times(b, Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1)), Times(C2, c, x, Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1)))), Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1)), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NegQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(Times(b, Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), -1), x_Symbol),
                    Condition(Times(C2, ArcTan(Times(Plus(b, Times(C2, c, x)), Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1))), Power(Rt(Plus(Times(C4, a, c), Negate(Sqr(b))), C2), -1)), And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NegQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), CN1D2), x_Symbol),
                    Condition(Times(ArcSinh(Times(Plus(b, Times(C2, c, x)), Power(Times(Rt(c, C2), Sqrt(Plus(Times(C4, a), Times(CN1, Sqr(b), Power(c, -1))))), -1))), Power(Rt(c, C2), -1)), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveQ(Plus(Times(C4, a), Times(CN1, Sqr(b), Power(c, -1))))), PosQ(c)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), CN1D2), x_Symbol),
                    Condition(Times(CN1, ArcSin(Times(Plus(b, Times(C2, c, x)), Power(Times(Rt(Negate(c), C2), Sqrt(Plus(Times(C4, a), Times(CN1, Sqr(b), Power(c, -1))))), -1))), Power(Rt(Negate(c), C2), -1)), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveQ(Plus(Times(C4, a), Times(CN1, Sqr(b), Power(c, -1))))), NegQ(c)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), CN1D2), x_Symbol),
                    Condition(Times(C2, Subst(Int(Power(Plus(Times(C4, c), Negate(Sqr(x))), -1), x), x, Times(Plus(b, Times(C2, c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), CN1D2)))), And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(PositiveQ(Plus(Times(C4, a), Times(CN1, Sqr(b), Power(c, -1)))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), QQ(-3L, 2L)), x_Symbol),
                    Condition(Times(CN2, Plus(b, Times(C2, c, x)), Power(Times(Plus(Sqr(b), Times(CN1, C4, a, c)), Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x))))), -1)), And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Times(Power(Power(c, p), -1), Int(Times(Power(Simp(Plus(Times(C1D2, b), Times(CN1, C1D2, q), Times(c, x)), x), p), Power(Simp(Plus(Times(C1D2, b), Times(C1D2, q), Times(c, x)), x), p)), x))), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(p)), PerfectSquareQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Int(ExpandIntegrand(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), x), x), And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(p)), Not(PerfectSquareQ(Plus(Sqr(b), Times(CN1, C4, a, c))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Plus(Times(Plus(b, Times(C2, c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), Power(Times(C2, c, Plus(Times(C2, p), C1)), -1)), Times(CN1, p, Plus(Sqr(b), Times(CN1, C4, a, c)), Power(Times(C2, c, Plus(Times(C2, p), C1)), -1), Int(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, Negate(C1))), x))), And(And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Greater(p, C0)), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Plus(Times(Plus(b, Times(C2, c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, C1)), Power(Times(Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1)), Times(CN1, C2, c, Plus(Times(C2, p), C3), Power(Times(Plus(p, C1), Plus(Sqr(b), Times(CN1, C4, a, c))), -1), Int(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(p, C1)), x))), And(And(And(And(FreeQ(List(a, b, c), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), RationalQ(p)), Less(p, CN1)), Unequal(p, QQ(-3L, 2L))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), p_), x_Symbol),
                    Condition(Times(Power(C2, Plus(p, Negate(C1))), Plus(b, Negate(Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2)), Times(C2, c, x)), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), Power(Times(c, Plus(p, C1), Power(Times(Plus(b, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2), Times(C2, c, x)), Power(Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2), -1)), p)), -1), Hypergeometric2F1(Negate(p), Plus(p, C1), Plus(p, C2), Times(Plus(Negate(b), Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2), Times(CN1, C2, c, x)), Power(Times(C2, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2)), -1)))), And(And(FreeQ(List(a, b, c, p), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(p, C1))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, u_), Times(c_DEFAULT, Sqr(v_))), p_), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p), x), x, u)), And(And(And(FreeQ(List(a, b, c, p), x), ZeroQ(Plus(u, Negate(v)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Power(u_, p_), x_Symbol),
                    Condition(Int(Power(ExpandToSum(u, x), p), x), And(And(FreeQ(p, x), QuadraticQ(u, x)), Not(QuadraticMatchQ(u, x)))))
    );
}
