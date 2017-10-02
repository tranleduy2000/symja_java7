package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RemoveContent;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules0 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(x_, -1), x_Symbol),
                    Log(x)),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_)), -1), x_Symbol),
                    Condition(Times(Log(RemoveContent(Plus(a, Times(b, x)), x)), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_)), m_), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, x)), Plus(m, C1)), Power(Times(b, Plus(m, C1)), -1)), And(FreeQ(List(a, b, m), x), NonzeroQ(Plus(m, C1))))),
            ISetDelayed(Int(Power(Plus(a_DEFAULT, Times(b_DEFAULT, u_)), m_), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Power(Plus(a, Times(b, x)), m), x), x, u)), And(And(FreeQ(List(a, b, m), x), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Power(u_, m_), x_Symbol),
                    Condition(Int(Power(ExpandToSum(u, x), m), x), And(And(FreeQ(m, x), LinearQ(u, x)), Not(LinearMatchQ(u, x)))))
    );
}
