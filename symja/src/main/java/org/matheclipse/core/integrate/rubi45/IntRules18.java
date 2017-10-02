package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.C8;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Cancel;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Integrate;
import static org.matheclipse.core.expression.F.LeafCount;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.LessEqual;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Set;
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
import static org.matheclipse.core.expression.F.d_;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
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
public class IntRules18 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Times(c, Power(f, -1)), m), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(m, p)), x)), And(And(And(And(FreeQ(List(a, b, c, d, e, f, m, p), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))), ZeroQ(Plus(Times(b, d), Times(CN1, a, e)))), Or(IntegerQ(m), PositiveQ(Times(c, Power(f, -1))))), Or(Not(IntegerQ(p)), LessEqual(LeafCount(Plus(d, Times(e, x), Times(f, Sqr(x)))), LeafCount(Plus(a, Times(b, x), Times(c, Sqr(x))))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), m), -1), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(m, p)), x)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, m, p), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))), ZeroQ(Plus(Times(b, d), Times(CN1, a, e)))), Not(IntegerQ(m))), Not(IntegerQ(p))), Not(PositiveQ(Times(c, Power(f, -1))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Cancel(Times(Power(Plus(Times(C1D2, b), Times(c, x)), Times(C2, m)), Power(Power(c, m), -1))), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), And(And(FreeQ(List(a, b, c, d, e, f, p), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Cancel(Times(Power(Plus(Times(C1D2, b), Times(c, x)), Times(C2, m)), Power(Power(c, m), -1))), Power(Plus(d, Times(f, Sqr(x))), p)), x), And(And(FreeQ(List(a, b, c, d, f, p), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), IntegerQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Power(Plus(b, Times(C2, c, x)), Times(C2, m)), -1), Int(Times(Power(Plus(b, Times(C2, c, x)), Times(C2, m)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x)), And(And(FreeQ(List(a, b, c, d, e, f, m, p), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Power(Plus(b, Times(C2, c, x)), Times(C2, m)), -1), Int(Times(Power(Plus(b, Times(C2, c, x)), Times(C2, m)), Power(Plus(d, Times(f, Sqr(x))), p)), x)), And(And(FreeQ(List(a, b, c, d, f, m, p), x), ZeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), Not(IntegerQ(m))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), -1)), x_Symbol),
                    Condition(Plus(Times(c, x, Power(f, -1)), Times(Power(f, -1), Int(Times(Plus(Times(a, f), Times(CN1, c, d), Times(Plus(Times(b, f), Times(CN1, c, e)), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), -1)), x))), And(FreeQ(List(a, b, c, d, e, f), x), Or(NonzeroQ(Plus(Times(c, d), Times(CN1, a, f))), NonzeroQ(Plus(Times(b, d), Times(CN1, a, e))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), -1)), x_Symbol),
                    Condition(Plus(Times(c, x, Power(f, -1)), Times(Power(f, -1), Int(Times(Plus(Times(a, f), Times(CN1, c, d), Times(CN1, c, e, x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), -1)), x))), FreeQ(List(a, c, d, e, f), x))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), -1)), x_Symbol),
                    Condition(Plus(Times(c, x, Power(f, -1)), Times(Power(f, -1), Int(Times(Plus(Times(a, f), Times(CN1, c, d), Times(b, f, x)), Power(Plus(d, Times(f, Sqr(x))), -1)), x))), FreeQ(List(a, b, c, d, f), x))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Times(Plus(Times(b, f, Plus(Times(C2, p), C3)), Times(CN1, c, e, Plus(p, C2)), Times(C2, c, f, Plus(p, C1), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, Sqr(f), Plus(p, C1), Plus(Times(C2, p), C3)), -1)), And(And(And(FreeQ(List(a, b, c, d, e, f, p), x), NonzeroQ(Plus(p, C1))), NonzeroQ(Plus(Times(C2, p), C3))), ZeroQ(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Times(Plus(Times(CN1, c, e, Plus(p, C2)), Times(C2, c, f, Plus(p, C1), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, Sqr(f), Plus(p, C1), Plus(Times(C2, p), C3)), -1)), And(And(And(FreeQ(List(a, c, d, e, f, p), x), NonzeroQ(Plus(p, C1))), NonzeroQ(Plus(Times(C2, p), C3))), ZeroQ(Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Times(Plus(Times(b, d), Times(C2, a, f, Plus(p, C1), x)), Power(Plus(d, Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, d, f, Plus(p, C1)), -1)), And(And(FreeQ(List(a, b, c, d, f, p), x), NonzeroQ(Plus(p, C1))), ZeroQ(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x), And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), PositiveIntegerQ(p)), NonzeroQ(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(a, Times(c, Sqr(x))), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x), And(And(And(FreeQ(List(a, c, d, e, f), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), PositiveIntegerQ(p)), NonzeroQ(Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Power(Plus(d, Times(f, Sqr(x))), p)), x), x), And(And(FreeQ(List(a, b, c, d, f), x), PositiveIntegerQ(p)), NonzeroQ(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(a, e, f), Times(CN1, C2, b, d, f), Times(c, d, e), Times(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e))), Times(c, Plus(Sqr(e), Times(CN1, C2, d, f)))), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(f, Plus(p, C1), Plus(Sqr(e), Times(CN1, C4, d, f))), -1)), Times(CN1, Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))), Power(Times(f, Plus(p, C1), Plus(Sqr(e), Times(CN1, C4, d, f))), -1), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), x))), And(And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), RationalQ(p)), Less(p, CN1)), NonzeroQ(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(a, e, f), Times(c, d, e), Times(Plus(Times(C2, a, Sqr(f)), Times(c, Plus(Sqr(e), Times(CN1, C2, d, f)))), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(f, Plus(p, C1), Plus(Sqr(e), Times(CN1, C4, d, f))), -1)), Times(CN1, Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))), Power(Times(f, Plus(p, C1), Plus(Sqr(e), Times(CN1, C4, d, f))), -1), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), x))), And(And(And(And(FreeQ(List(a, c, d, e, f), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), RationalQ(p)), Less(p, CN1)), NonzeroQ(Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, d), Times(CN1, Plus(Times(a, f), Times(CN1, c, d)), x)), Power(Plus(d, Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, d, f, Plus(p, C1)), -1)), Times(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)), Power(Times(C2, d, f, Plus(p, C1)), -1), Int(Power(Plus(d, Times(f, Sqr(x))), Plus(p, C1)), x))), And(And(And(FreeQ(List(a, b, c, d, f), x), RationalQ(p)), Less(p, CN1)), NonzeroQ(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, f, Plus(Times(C2, p), C3)), Times(CN1, c, e, Plus(p, C2)), Times(C2, c, f, Plus(p, C1), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, Sqr(f), Plus(p, C1), Plus(Times(C2, p), C3)), -1)), Times(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))), Power(Times(C2, Sqr(f), Plus(Times(C2, p), C3)), -1), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p), x))), And(And(And(And(FreeQ(List(d, e, f, a, b, c, p), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), Not(PositiveIntegerQ(p))), Not(And(RationalQ(p), LessEqual(p, CN1)))), NonzeroQ(Plus(Times(f, Plus(Times(C2, a, f), Times(CN1, b, e)), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(CN1, c, e, Plus(p, C2)), Times(C2, c, f, Plus(p, C1), x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, Sqr(f), Plus(p, C1), Plus(Times(C2, p), C3)), -1)), Times(Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))), Power(Times(C2, Sqr(f), Plus(Times(C2, p), C3)), -1), Int(Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p), x))), And(And(And(And(FreeQ(List(d, e, f, a, c, p), x), NonzeroQ(Plus(Sqr(e), Times(CN1, C4, d, f)))), Not(PositiveIntegerQ(p))), Not(And(RationalQ(p), LessEqual(p, CN1)))), NonzeroQ(Plus(Times(C2, a, Sqr(f), Plus(Times(C2, p), C3)), Times(c, Plus(Times(Sqr(e), Plus(p, C2)), Times(CN1, C2, d, f)))))))),
            ISetDelayed(Int(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(b, f, Plus(Times(C2, p), C3)), Times(C2, c, f, Plus(p, C1), x)), Power(Plus(d, Times(f, Sqr(x))), Plus(p, C1)), Power(Times(C2, Sqr(f), Plus(p, C1), Plus(Times(C2, p), C3)), -1)), Times(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)), Power(Times(f, Plus(Times(C2, p), C3)), -1), Int(Power(Plus(d, Times(f, Sqr(x))), p), x))), And(And(And(FreeQ(List(d, f, a, b, c, p), x), Not(PositiveIntegerQ(p))), Not(And(RationalQ(p), LessEqual(p, CN1)))), NonzeroQ(Plus(Times(C3, a, f), Times(CN1, c, d), Times(C2, a, f, p)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Times(CN2, e, Subst(Int(Power(Plus(Times(e, Plus(Times(b, e), Times(CN1, C4, a, f))), Times(CN1, Plus(Times(b, d), Times(CN1, a, e)), Sqr(x))), -1), x), x, Times(Plus(e, Times(C2, f, x)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), CN1D2)))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, e), Times(CN1, b, f)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Plus(Times(C2, c, Power(q, -1), Int(Power(Times(Plus(b, Negate(q), Times(C2, c, x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1), x)), Times(CN1, C2, c, Power(q, -1), Int(Power(Times(Plus(b, q, Times(C2, c, x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1), x)))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, e), Times(CN1, b, f)))))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(c_DEFAULT, Sqr(x_))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(C1D2, Int(Power(Times(Plus(a, Times(CN1, Rt(Times(CN1, a, c), C2), x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1), x)), Times(C1D2, Int(Power(Times(Plus(a, Times(Rt(Times(CN1, a, c), C2), x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1), x))), FreeQ(List(a, c, d, e, f), x))),
            ISetDelayed(Int(Power(Times(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), Sqrt(Plus(d_, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Module(List(Set(q, Rt(Plus(Sqr(b), Times(CN1, C4, a, c)), C2))), Plus(Times(C2, c, Power(q, -1), Int(Power(Times(Plus(b, Negate(q), Times(C2, c, x)), Sqrt(Plus(d, Times(f, Sqr(x))))), -1), x)), Times(CN1, C2, c, Power(q, -1), Int(Power(Times(Plus(b, q, Times(C2, c, x)), Sqrt(Plus(d, Times(f, Sqr(x))))), -1), x)))), And(FreeQ(List(a, b, c, d, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))))),
            ISetDelayed(Int(Power(Times(Sqr(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(CN1, b, Plus(e, Times(C2, f, x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x)))), Power(Times(Plus(Times(b, d), Times(CN1, a, e)), Plus(Times(b, e), Times(CN1, C4, a, f)), Plus(a, Times(b, x), Times(c, Sqr(x)))), -1)), Times(CN1, e, Plus(Times(C4, c, d), Times(b, e), Times(CN1, C8, a, f)), Power(Times(C2, Plus(Times(b, d), Times(CN1, a, e)), Plus(Times(b, e), Times(CN1, C4, a, f))), -1), Int(Power(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1), x))), And(And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(c, e), Times(CN1, b, f)))), NonzeroQ(Plus(Times(b, d), Times(CN1, a, e)))), NonzeroQ(Plus(Times(b, e), Times(CN1, C4, a, f)))))),
            ISetDelayed(Int(Power(Times(Sqr(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(e, Plus(Times(b, Plus(Times(C3, c, d), Times(CN1, b, e))), Times(c, Plus(Times(C2, c, d), Times(CN1, b, e)), x)), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x)))), Power(Times(a, d, Plus(Times(C4, c, d), Times(CN1, b, e)), Plus(Times(c, e), Times(CN1, b, f)), Plus(a, Times(b, x), Times(c, Sqr(x)))), -1)), Times(e, Power(Times(C2, a, d, Plus(Times(C4, c, d), Times(CN1, b, e)), Plus(Times(c, e), Times(CN1, b, f))), -1), Int(Times(Simp(Plus(Times(Plus(Times(C4, c, d), Times(CN1, b, e)), Plus(Times(c, d), Times(b, e), Times(CN1, C2, a, f))), Times(c, e, Plus(Times(C2, c, d), Times(CN1, b, e), Times(C2, a, f)), x)), x), Power(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1)), x))), And(And(And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(b, d), Times(CN1, a, e)))), NonzeroQ(Plus(Times(c, e), Times(CN1, b, f)))), NonzeroQ(Plus(Times(C4, c, d), Times(CN1, b, e)))))),
            ISetDelayed(Int(Power(Times(Sqr(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(CN2, Sqr(c), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x)))), Power(Times(Plus(Times(c, e), Times(CN1, b, f)), Plus(Sqr(b), Times(CN1, C4, a, c)), Plus(a, Times(b, x), Times(c, Sqr(x)))), -1)), Times(CN1, c, Power(Times(Plus(Times(c, e), Times(CN1, b, f)), Plus(Sqr(b), Times(CN1, C4, a, c))), -1), Int(Times(Plus(Times(C3, c, e), Times(CN1, C2, b, f), Times(C2, c, f, x)), Power(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1)), x))), And(And(FreeQ(List(a, b, c, d, e, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(Sqr(b), f), Times(CN1, c, Plus(Times(b, e), Times(CN1, C2, Plus(Times(c, d), Times(CN1, a, f)))))))))),
            ISetDelayed(Int(Power(Times(Sqr(Plus(a_, Times(c_DEFAULT, Sqr(x_)))), Sqrt(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x)))), Power(Times(C2, a, e, Plus(a, Times(c, Sqr(x)))), -1)), Times(Power(Times(C4, a, e), -1), Int(Times(Plus(Times(C3, e), Times(C2, f, x)), Power(Times(Plus(a, Times(c, Sqr(x))), Sqrt(Plus(d, Times(e, x), Times(f, Sqr(x))))), -1)), x))), And(FreeQ(List(a, c, d, e, f), x), ZeroQ(Plus(Times(c, d), Times(CN1, a, f)))))),
            ISetDelayed(Int(Power(Times(Sqr(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)))), Sqrt(Plus(d_, Times(f_DEFAULT, Sqr(x_))))), -1), x_Symbol),
                    Condition(Plus(Times(C2, Sqr(c), Sqrt(Plus(d, Times(f, Sqr(x)))), Power(Times(b, f, Plus(Sqr(b), Times(CN1, C4, a, c)), Plus(a, Times(b, x), Times(c, Sqr(x)))), -1)), Times(CN1, C2, c, Power(Times(b, Plus(Sqr(b), Times(CN1, C4, a, c))), -1), Int(Times(Plus(b, Times(CN1, c, x)), Power(Times(Plus(a, Times(b, x), Times(c, Sqr(x))), Sqrt(Plus(d, Times(f, Sqr(x))))), -1)), x))), And(And(FreeQ(List(a, b, c, d, f), x), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), ZeroQ(Plus(Times(Sqr(b), f), Times(C2, c, Plus(Times(c, d), Times(CN1, a, f)))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x), And(FreeQ(List(a, b, c, d, e, f, p), x), Or(IntegersQ(m, p), PositiveIntegerQ(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x), And(FreeQ(List(a, c, d, e, f, p), x), Or(Or(IntegersQ(m, p), PositiveIntegerQ(m)), PositiveIntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(f, Power(c, -1), Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(m, C1)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, Negate(C1)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d), Times(CN1, a, f), Times(Plus(Times(c, e), Times(CN1, b, f)), x)), x), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, e, f), x), RationalQ(m, p)), LessEqual(m, CN1)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(f, Power(c, -1), Int(Times(Power(Plus(a, Times(c, Sqr(x))), Plus(m, C1)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, Negate(C1)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d), Times(CN1, a, f), Times(c, e, x)), x), Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, c, d, e, f), x), RationalQ(m, p)), LessEqual(m, CN1)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_, Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(f, Power(c, -1), Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(m, C1)), Power(Plus(d, Times(f, Sqr(x))), Plus(p, Negate(C1)))), x)), Times(Power(c, -1), Int(Times(Simp(Plus(Times(c, d), Times(CN1, a, f), Times(CN1, b, f, x)), x), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(f, Sqr(x))), Plus(p, Negate(C1)))), x))), And(And(And(FreeQ(List(a, b, c, d, f), x), RationalQ(m, p)), LessEqual(m, CN1)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(CN1, e, Plus(Times(b, d), Times(CN1, a, e))))), Times(CN1, Plus(Times(C2, a, c, d), Times(CN1, b, Plus(Times(b, d), Times(CN1, a, e)))), f), Times(Sqr(a), Sqr(f))), -1), Int(Times(Simp(Plus(Times(f, Plus(Times(b, e), Times(CN1, a, f))), Times(CN1, c, Plus(Sqr(e), Times(CN1, d, f))), Times(CN1, f, Plus(Times(c, e), Times(CN1, b, f)), x)), x), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), Plus(m, C1)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x)), Times(Power(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(CN1, e, Plus(Times(b, d), Times(CN1, a, e))))), Times(CN1, Plus(Times(C2, a, c, d), Times(CN1, b, Plus(Times(b, d), Times(CN1, a, e)))), f), Times(Sqr(a), Sqr(f))), -1), Int(Times(Simp(Plus(Times(Sqr(b), f), Times(c, Plus(Times(c, d), Times(CN1, b, e), Times(CN1, a, f))), Times(CN1, c, Plus(Times(c, e), Times(CN1, b, f)), x)), x), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1))), x))), And(And(And(And(FreeQ(List(a, b, c, d, e, f), x), RationalQ(m, p)), LessEqual(m, CN1)), LessEqual(p, CN1)), NonzeroQ(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(CN1, e, Plus(Times(b, d), Times(CN1, a, e))))), Times(CN1, Plus(Times(C2, a, c, d), Times(CN1, b, Plus(Times(b, d), Times(CN1, a, e)))), f), Times(Sqr(a), Sqr(f))))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(a, Sqr(e)))), Times(CN1, C2, a, c, d, f), Times(Sqr(a), Sqr(f))), -1), Int(Times(Simp(Plus(Times(CN1, a, Sqr(f)), Times(CN1, c, Plus(Sqr(e), Times(CN1, d, f))), Times(CN1, c, e, f, x)), x), Power(Plus(a, Times(c, Sqr(x))), Plus(m, C1)), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x)), Times(Power(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(a, Sqr(e)))), Times(CN1, C2, a, c, d, f), Times(Sqr(a), Sqr(f))), -1), Int(Times(Simp(Plus(Times(c, Plus(Times(c, d), Times(CN1, a, f))), Times(CN1, Sqr(c), e, x)), x), Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), Plus(p, C1))), x))), And(And(And(And(FreeQ(List(a, c, d, e, f), x), RationalQ(m, p)), LessEqual(m, CN1)), LessEqual(p, CN1)), NonzeroQ(Plus(Times(c, Plus(Times(c, Sqr(d)), Times(a, Sqr(e)))), Times(CN1, C2, a, c, d, f), Times(Sqr(a), Sqr(f))))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), FreeQ(List(a, b, c, d, e, f, m, p), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_), Times(f_DEFAULT, Sqr(x_))), p_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), FreeQ(List(a, c, d, e, f, m, p), x))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(b_DEFAULT, u_), Times(c_DEFAULT, Sqr(v_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, w_), Times(f_DEFAULT, Sqr(z_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(b, x), Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x, u)), And(And(And(And(And(FreeQ(List(a, b, c, d, e, f, m, p), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), ZeroQ(Plus(u, Negate(z)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(Plus(a_DEFAULT, Times(c_DEFAULT, Sqr(u_))), m_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, v_), Times(f_DEFAULT, Sqr(w_))), p_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(a, Times(c, Sqr(x))), m), Power(Plus(d, Times(e, x), Times(f, Sqr(x))), p)), x), x, u)), And(And(And(And(FreeQ(List(a, c, d, e, f, m, p), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p)), x), And(And(FreeQ(List(m, p), x), QuadraticQ(List(u, v), x)), Not(QuadraticMatchQ(List(u, v), x)))))
    );
}
