package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cot;
import static org.matheclipse.core.expression.F.EvenQ;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.LessEqual;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Tan;
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
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.q_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FreeFactors;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules52 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_)), Times(c_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_))), n_), Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(d, Times(e, x))), x))), Times(CN1, f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(c, Times(b, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, q), Times(CN1, C1D2, p)))), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, q)))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, q), C1)), -1)), x), x, Times(Cot(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), And(Less(C0, p), LessEqual(p, q))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_)), Times(b_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_))), n_), Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(d, Times(e, x))), x))), Times(f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(c, Times(b, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, q), Times(CN1, C1D2, p)))), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, q)))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, q), C1)), -1)), x), x, Times(Tan(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), And(Less(C0, p), LessEqual(p, q))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_)), Times(c_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_))), n_), Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(d, Times(e, x))), x))), Times(CN1, f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, p))), Times(b, Power(f, p), Power(x, p)), Times(c, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, p), Times(CN1, C1D2, q))))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), Less(Less(C0, q), p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_)), Times(b_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_))), n_), Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(d, Times(e, x))), x))), Times(f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, p))), Times(b, Power(f, p), Power(x, p)), Times(c, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, p), Times(CN1, C1D2, q))))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), Less(Less(C0, q), p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_)), Times(c_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_))), n_), Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(d, Times(e, x))), x))), Times(CN1, f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(c, Times(b, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, q), Times(CN1, C1D2, p)))), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, q)))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, q), C1)), -1)), x), x, Times(Cot(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), And(Less(C0, p), LessEqual(p, q))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_)), Times(b_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_))), n_), Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(d, Times(e, x))), x))), Times(f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(c, Times(b, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, q), Times(CN1, C1D2, p)))), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, q)))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, q), C1)), -1)), x), x, Times(Tan(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), And(Less(C0, p), LessEqual(p, q))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_)), Times(c_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_))), n_), Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(d, Times(e, x))), x))), Times(CN1, f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, p))), Times(b, Power(f, p), Power(x, p)), Times(c, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, p), Times(CN1, C1D2, q))))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), Less(Less(C0, q), p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(c_DEFAULT, Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), q_)), Times(b_DEFAULT, Power($($s("§sin"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), p_))), n_), Power($($s("§cos"), Plus(d_DEFAULT, Times(e_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(d, Times(e, x))), x))), Times(f, Power(e, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, p))), Times(b, Power(f, p), Power(x, p)), Times(c, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, p), Times(CN1, C1D2, q))))), x), n), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(d, Times(e, x))), Power(f, -1))))), And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), EvenQ(m)), EvenQ(p)), EvenQ(q)), IntegerQ(n)), Less(Less(C0, q), p))))
    );
}
