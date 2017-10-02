package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Integrate;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.OddQ;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Set;
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
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandTrig;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules48 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(v, ExpandTrig(Times(Power($($s("§cos"), Plus(c, Times(d, x))), m), Power($($s("§sin"), Plus(c, Times(d, x))), n), Power(Plus(a, Times(b, $($s("§sin"), Plus(c, Times(d, x))))), p)), x))), Condition(Int(v, x), SumQ(v))), FreeQ(List(a, b, c, d, m, n, p), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(v, ExpandTrig(Times(Power($($s("§cos"), Plus(c, Times(d, x))), m), Power($($s("§sin"), Plus(c, Times(d, x))), n), Power(Plus(a, Times(b, $($s("§cos"), Plus(c, Times(d, x))))), p)), x))), Condition(Int(v, x), SumQ(v))), FreeQ(List(a, b, c, d, m, n, p), x))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), p_DEFAULT), Power(Times(e_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_DEFAULT), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power($($s("§cos"), Plus(c, Times(d, x))), m), Power(Times(e, $($s("§sin"), Plus(c, Times(d, x)))), n), Power(Plus(a, Times(b, $($s("§sin"), Plus(c, Times(d, x))))), p)), x), And(FreeQ(List(a, b, c, d, e, m, n, p), x), Not(OddQ(m))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), p_DEFAULT), Power(Times(e_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_DEFAULT), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Integrate(Times(Power($($s("§cos"), Plus(c, Times(d, x))), m), Power(Times(e, $($s("§sin"), Plus(c, Times(d, x)))), n), Power(Plus(a, Times(b, $($s("§cos"), Plus(c, Times(d, x))))), p)), x), And(FreeQ(List(a, b, c, d, e, m, n, p), x), Not(OddQ(m)))))
    );
}
