package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.q_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
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
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules10 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Int(Power(Times(Plus(a, b), Power(x, n)), p), x), And(FreeQ(List(a, b, n, p), x), ZeroQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Int(Times(Power(x, Times(p, q)), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), x), And(And(FreeQ(List(a, b, n, q), x), IntegerQ(p)), PosQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1), Power(x, Plus(n, Negate(C1)))), -1)), And(And(And(FreeQ(List(a, b, n, p, q), x), Not(IntegerQ(p))), NonzeroQ(Plus(n, Negate(q)))), ZeroQ(Plus(Times(p, q), Negate(n), q, C1))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(Times(n, p), C1), Power(x, Plus(n, Negate(C1)))), -1)), Times(CN1, a, Plus(Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(Times(n, p), C1)), -1), Int(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Power(x, Plus(n, Negate(q))), -1)), x))), And(And(And(And(And(FreeQ(List(a, b, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), PositiveIntegerQ(Times(Plus(Times(p, q), Negate(n), q, C1), Power(Plus(n, Negate(q)), -1)))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(Times(p, q), C1), Power(x, Plus(q, Negate(C1)))), -1)), Times(CN1, b, Plus(Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(Times(p, q), C1)), -1), Int(Times(Power(x, Plus(n, Negate(q))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, b, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), NonzeroQ(Plus(Times(p, q), C1))), NegativeIntegerQ(Plus(Times(n, p), n, Negate(q), C1))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(x, Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Plus(Times(p, q), C1), -1)), Times(CN1, b, Plus(n, Negate(q)), p, Power(Plus(Times(p, q), C1), -1), Int(Times(Power(x, n), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b), x), Not(IntegerQ(p))), RationalQ(n, p, q)), Less(q, n)), Greater(p, C0)), Less(Plus(Times(p, q), C1), C0)))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(x, Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Plus(Times(n, p), C1), -1)), Times(a, Plus(n, Negate(q)), p, Power(Plus(Times(n, p), C1), -1), Int(Times(Power(x, q), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Greater(p, C0)), NonzeroQ(Plus(Times(n, p), C1))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1), Power(x, Plus(n, Negate(C1)))), -1)), Times(CN1, Plus(Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(n, Negate(q)), Plus(p, C1)), -1), Int(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Power(x, n), -1)), x))), And(And(And(And(And(FreeQ(List(a, b), x), Not(IntegerQ(p))), RationalQ(n, p, q)), Less(q, n)), Less(p, CN1)), Greater(Plus(Times(p, q), C1), Plus(n, Negate(q)))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(CN1, Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(n, Negate(q)), Plus(p, C1), Power(x, Plus(q, Negate(C1)))), -1)), Times(Plus(Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(n, Negate(q)), Plus(p, C1)), -1), Int(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Power(x, q), -1)), x))), And(And(And(And(FreeQ(List(a, b, n, q), x), Not(IntegerQ(p))), PosQ(Plus(n, Negate(q)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, x_), Times(a_DEFAULT, Sqr(x_))), CN1D2), x_Symbol),
                    Condition(Times(CN1, Power(Times(b, Sqrt(Times(CN1, a, Power(b, -2)))), -1), ArcSin(Plus(C1, Times(C2, a, x, Power(b, -1))))), And(FreeQ(List(a, b), x), PositiveQ(Times(CN1, a, Power(b, -2)))))),
            ISetDelayed(Int(Power(Plus(Times(a_DEFAULT, Sqr(x_)), Times(b_DEFAULT, Power(x_, n_DEFAULT))), CN1D2), x_Symbol),
                    Condition(Times(C2, Power(Plus(C2, Negate(n)), -1), Subst(Int(Power(Plus(C1, Times(CN1, a, Sqr(x))), -1), x), x, Times(x, Power(Plus(Times(a, Sqr(x)), Times(b, Power(x, n))), CN1D2)))), And(FreeQ(List(a, b, n), x), NonzeroQ(Plus(n, Negate(C2)))))),
            ISetDelayed(Int(Power(Plus(Times(a_DEFAULT, x_), Times(b_DEFAULT, Power(x_, n_))), CN1D2), x_Symbol),
                    Condition(Times(Sqrt(x), Sqrt(Plus(a, Times(b, Power(x, Plus(n, Negate(C1)))))), Power(Plus(Times(a, x), Times(b, Power(x, n))), CN1D2), Int(Power(Times(Sqrt(x), Sqrt(Plus(a, Times(b, Power(x, Plus(n, Negate(C1))))))), -1), x)), And(FreeQ(List(a, b, n), x), Or(ZeroQ(Plus(n, Negate(C3))), ZeroQ(Plus(n, Negate(C4))))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, Plus(Times(n, p), C1), Power(x, Plus(n, Negate(C1)))), -1)), Times(CN1, a, Plus(Times(p, q), Negate(n), q, C1), Power(Times(b, Plus(Times(n, p), C1)), -1), Int(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Power(x, Plus(n, Negate(q))), -1)), x))), And(And(And(And(FreeQ(List(a, b), x), RationalQ(n, p, q)), Less(q, n)), Less(Less(CN1, p), C0)), Greater(Plus(Times(p, q), C1), Plus(n, Negate(q)))))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, Plus(Times(p, q), C1), Power(x, Plus(q, Negate(C1)))), -1)), Times(CN1, b, Plus(Times(n, p), n, Negate(q), C1), Power(Times(a, Plus(Times(p, q), C1)), -1), Int(Times(Power(x, Plus(n, Negate(q))), Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p)), x))), And(And(And(And(FreeQ(List(a, b), x), RationalQ(n, p, q)), Less(q, n)), Less(Less(CN1, p), C0)), Less(Plus(Times(p, q), C1), C0)))),
            ISetDelayed(Int(Power(Plus(Times(b_DEFAULT, Power(x_, n_DEFAULT)), Times(a_DEFAULT, Power(x_, q_DEFAULT))), p_), x_Symbol),
                    Condition(Times(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), Power(Times(Power(x, Times(p, q)), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), -1), Int(Times(Power(x, Times(p, q)), Power(Plus(a, Times(b, Power(x, Plus(n, Negate(q))))), p)), x)), And(And(And(FreeQ(List(a, b, n, p, q), x), Not(IntegerQ(p))), NonzeroQ(Plus(n, Negate(q)))), PosQ(Plus(n, Negate(q)))))),
            ISetDelayed(Int(Power(Plus(Times(a_DEFAULT, Power(u_, q_DEFAULT)), Times(b_DEFAULT, Power(v_, n_DEFAULT))), p_), x_Symbol),
                    Condition(Times(Power(Coefficient(u, x, C1), -1), Subst(Int(Power(Plus(Times(a, Power(x, q)), Times(b, Power(x, n))), p), x), x, u)), And(And(And(FreeQ(List(a, b, n, p, q), x), ZeroQ(Plus(u, Negate(v)))), LinearQ(u, x)), NonzeroQ(Plus(u, Negate(x)))))),
            ISetDelayed(Int(Power(u_, p_), x_Symbol),
                    Condition(Int(Power(ExpandToSum(u, x), p), x), And(And(FreeQ(p, x), GeneralizedBinomialQ(u, x)), Not(GeneralizedBinomialMatchQ(u, x)))))
    );
}
