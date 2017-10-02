package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.C5;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Equal;
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
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Simplify;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.j_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.q_DEFAULT;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.GeneralizedBinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.GeneralizedBinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumSimplerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules11 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, m), Power(Times(Plus(a, b), Power(x, n)), p)), x), And(FreeQ(List(a, b, m, n, p), x), ZeroQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, Plus(m, Times(p, q))), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), x), And(And(FreeQ(List(a, b, m, n, q), x), IntegerQ(p)), PosQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Times(Power(x, Plus(m, Negate(n), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1)), -1)), And(And(And(FreeQ(List(a, b, m, n, p, q), x), Not(IntegerQ(p))), NonzeroQ(Plus(n, Negate(q)))), ZeroQ(Plus(m, Times(p, q), Negate(n), q, C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, Negate(n), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(m, Times(n, p), C1)), -1)), Times(CN1, a, Plus(m, Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(m, Times(n, p), C1)), -1), Int(Times(Power(x, Plus(m, Negate(n), q)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(And(FreeQ(List(a, b, m, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), PositiveIntegerQ(Times(Plus(m, Times(p, q), Negate(n), q, C1), Power(Plus(n, Negate(q)), -1)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, Negate(q), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(m, Times(p, q), C1)), -1)), Times(CN1, b, Plus(m, Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(m, Times(p, q), C1)), -1), Int(Times(Power(x, Plus(m, n, Negate(q))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, b, m, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), NonzeroQ(Plus(m, Times(p, q), C1))), NegativeIntegerQ(Plus(m, Times(n, p), n, Negate(q), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Plus(m, Times(p, q), C1), -1)), Times(CN1, b, Plus(n, Negate(q)), p, Power(Plus(m, Times(p, q), C1), -1), Int(Times(Power(x, Plus(m, n)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b), x), Not(IntegerQ(p))), RationalQ(m, n, p, q)), Less(q, n)), Greater(p, C0)), Less(Plus(m, Times(p, q), C1), C0)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Plus(m, Times(n, p), C1), -1)), Times(a, Plus(n, Negate(q)), p, Power(Plus(m, Times(n, p), C1), -1), Int(Times(Power(x, Plus(m, q)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b, m, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), NonzeroQ(Plus(m, Times(n, p), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, Negate(n), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1)), -1)), Times(CN1, Plus(m, Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1)), -1), Int(Times(Power(x, Plus(m, Negate(n))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(And(FreeQ(List(a, b), x), Not(IntegerQ(p))), RationalQ(m, n, p, q)), Less(q, n)), Less(p, CN1)), Greater(Plus(m, Times(p, q), C1), Plus(n, Negate(q)))), Not(NegativeIntegerQ(Times(Plus(m, Times(n, p), n, Negate(q), C1), Power(Plus(n, Negate(q)), -1))))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(x, Plus(m, Negate(q), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(n, Negate(q)), Plus(p, C1)), -1)), Times(Plus(m, Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(n, Negate(q)), Plus(p, C1)), -1), Int(Times(Power(x, Plus(m, Negate(q))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(FreeQ(List(a, b, m, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, j_DEFAULT)), Times(a_DEFAULT, Power(x_, n_))), CN1D2)), x_Symbol),
                    Condition(Times(Power(n, -1), Subst(Int(Power(Plus(Times(a, x), Times(b, Sqr(x))), CN1D2), x), x, Power(x, n))), And(And(FreeQ(List(a, b, n), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), ZeroQ(Plus(m, Negate(n), C1))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), CN1D2)), x_Symbol),
                    Condition(Times(CN2, Power(Plus(n, Negate(q)), -1), Subst(Int(Power(Plus(C1, Times(CN1, a, Sqr(x))), -1), x), x, Times(Power(x, Times(C1D2, q)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), CN1D2)))), And(And(FreeQ(List(a, b, n, q), x), ZeroQ(Plus(m, Times(CN1, C1D2, q), C1))), NonzeroQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), CN1D2)), x_Symbol),
                    Condition(Times(Power(x, Times(C1D2, q)), Sqrt(Plus(a, Times(b, Power(x, Plus(n, Negate(q)))))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), CN1D2), Int(Times(Power(x, Plus(m, Times(CN1, C1D2, q))), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), CN1D2)), x)), And(And(And(FreeQ(List(a, b, m, n, q), x), PosQ(Plus(n, Negate(q)))), RationalQ(m, n, q)), Or(Or(Or(And(And(Equal(m, C1), Equal(q, C1)), Equal(n, C3)), And(And(Equal(m, C2), Equal(q, C1)), Equal(n, C4))), And(And(Or(Equal(m, C1D2), Equal(m, QQ(3L, 2L))), Equal(q, C2)), Equal(n, C4))), And(And(Or(Or(Or(Equal(m, C1), Equal(m, C2)), Equal(m, C1D2)), Equal(m, QQ(5L, 2L))), Equal(q, C2)), Equal(n, C5)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, Negate(n), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(m, Times(n, p), C1)), -1)), Times(CN1, a, Plus(m, Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(m, Times(n, p), C1)), -1), Int(Times(Power(x, Plus(m, Negate(n), q)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(FreeQ(List(a, b), x), RationalQ(m, n, p, q)), Less(q, n)), Less(Less(CN1, p), C0)), Greater(Plus(m, Times(p, q), C1), Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Plus(Times(Power(x, Plus(m, Negate(q), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(m, Times(p, q), C1)), -1)), Times(CN1, b, Plus(m, Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(m, Times(p, q), C1)), -1), Int(Times(Power(x, Plus(m, n, Negate(q))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(FreeQ(List(a, b), x), RationalQ(m, n, p, q)), Less(q, n)), Less(Less(CN1, p), C0)), Less(Plus(m, Times(p, q), C1), C0)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Module(List(Set($s("mn"), Simplify(Plus(m, Negate(n), q)))), Plus(Times(Power(x, Plus($s("mn"), Negate(q), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(m, Times(n, p), C1)), -1)), Times(CN1, a, Plus($s("mn"), Times(p, q), C1), Power(Times(b, Plus(m, Times(n, p), C1)), -1), Int(Times(Power(x, $s("mn")), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x)))), And(And(And(And(FreeQ(List(a, b, m, n, p, q), x), Not(RationalQ(p))), NonzeroQ(Plus(n, Negate(q)))), NonzeroQ(Plus(m, Times(n, p), C1))), SumSimplerQ(m, Negate(Plus(n, Negate(q))))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Module(List(Set($s("mn"), Simplify(Plus(m, n, Negate(q))))), Plus(Times(Power(x, Plus(m, Negate(q), C1)), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(m, Times(p, q), C1)), -1)), Times(CN1, b, Plus($s("mn"), Times(n, p), C1), Power(Times(a, Plus(m, Times(p, q), C1)), -1), Int(Times(Power(x, $s("mn")), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x)))), And(And(And(And(FreeQ(List(a, b, m, n, p, q), x), Not(RationalQ(p))), NonzeroQ(Plus(n, Negate(q)))), NonzeroQ(Plus(m, Times(p, q), C1))), SumSimplerQ(m, Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_)), x_Symbol),
                    Condition(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Times(Power(x, Times(p, q)), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), -1), Int(Times(Power(x, Plus(m, Times(p, q))), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), x)), And(And(And(FreeQ(List(a, b, m, n, p, q), x), Not(IntegerQ(p))), NonzeroQ(Plus(n, Negate(q)))), PosQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(Plus(Times(a_DEFAULT, Power(v_, q_DEFAULT)), Times(b_DEFAULT, Power(w_, n_DEFAULT))), p_)), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Times(Power(x, m), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x), x, u)), And(And(And(And(FreeQ(List(a, b, m, n, p, q), x), ZeroQ(Plus(u, Negate(v)))), ZeroQ(Plus(u, Negate(w)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Times(Power(u_, p_DEFAULT), Power(x_, m_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, m), Power(ExpandToSum(u, x), p)), x), And(And(FreeQ(List(m, p), x), GeneralizedBinomialQ(u, x)), Not(GeneralizedBinomialMatchQ(u, x)))))
    );
}
