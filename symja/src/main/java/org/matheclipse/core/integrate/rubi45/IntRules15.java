package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.AppellF1;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.w;
import static org.matheclipse.core.expression.F.w_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.QuadraticQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules15 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(x_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, x_)), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Plus(d, Times(e, x)), Plus(m, C1)), Power(Plus(a, Times(c, Sqr(x))), p), Power(Times(e, Plus(m, C1), Power(Plus(C1, Times(CN1, c, Plus(d, Times(e, x)), Power(Plus(Times(c, d), Times(CN1, e, Rt(Times(CN1, a, c), C2))), -1))), p), Power(Plus(C1, Times(CN1, c, Plus(d, Times(e, x)), Power(Plus(Times(c, d), Times(e, Rt(Times(CN1, a, c), C2))), -1))), p)), -1), AppellF1(Plus(m, C1), Negate(p), Negate(p), Plus(m, C2), Times(c, Plus(d, Times(e, x)), Power(Plus(Times(c, d), Times(CN1, e, Rt(Times(CN1, a, c), C2))), -1)), Times(c, Plus(d, Times(e, x)), Power(Plus(Times(c, d), Times(e, Rt(Times(CN1, a, c), C2))), -1)))), And(And(And(And(FreeQ(List(a, c, d, e, m, p), x), Not(IntegerQ(p))), NonzeroQ(Plus(Times(c, d), Times(CN1, e, Rt(Times(CN1, a, c), C2))))), NonzeroQ(Plus(Times(c, d), Times(e, Rt(Times(CN1, a, c), C2))))), Not(NegativeIntegerQ(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, v_), Times(c_DEFAULT, Sqr(w_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, u_)), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(a, Times(b, x), Times(c, Sqr(x))), p)), x), x, u)), And(And(And(And(FreeQ(List(a, b, c, d, e, m, p), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Sqr(w_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, u_)), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(Plus(d, Times(e, x)), m), Power(Plus(a, Times(c, Sqr(x))), p)), x), x, u)), And(And(And(FreeQ(List(a, c, d, e, m, p), x), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(v_, p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(ExpandToSum(u, x), m), Power(ExpandToSum(v, x), p)), x), And(And(And(FreeQ(List(m, p), x), LinearQ(u, x)), QuadraticQ(v, x)), Not(And(LinearMatchQ(u, x), QuadraticMatchQ(v, x))))))
    );
}
