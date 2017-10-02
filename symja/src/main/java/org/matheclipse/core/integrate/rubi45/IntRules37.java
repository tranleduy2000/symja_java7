package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C1D4;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.EllipticE;
import static org.matheclipse.core.expression.F.EllipticF;
import static org.matheclipse.core.expression.F.Expand;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.Hypergeometric2F1;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.OddQ;
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.DeactivateTrig;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FunctionOfTrigOfLinearQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules37 {
    public static IAST RULES = List(
            ISetDelayed(Int(u_, x_Symbol),
                    Condition(Int(DeactivateTrig(u, x), x), FunctionOfTrigOfLinearQ(u, x))),
            ISetDelayed(Int($($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), x_Symbol),
                    Condition(Times(CN1, Cos(Plus(a, Times(b, x))), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int($($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), x_Symbol),
                    Condition(Times(Sin(Plus(a, Times(b, x))), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power($($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), n_), x_Symbol),
                    Condition(Times(CN1, Power(b, -1), Subst(Int(Expand(Power(Plus(C1, Negate(Sqr(x))), Times(C1D2, Plus(n, Negate(C1)))), x), x), x, Cos(Plus(a, Times(b, x))))), And(And(And(FreeQ(List(a, b), x), RationalQ(n)), Greater(n, C1)), OddQ(n)))),
            ISetDelayed(Int(Power($($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), n_), x_Symbol),
                    Condition(Times(Power(b, -1), Subst(Int(Expand(Power(Plus(C1, Negate(Sqr(x))), Times(C1D2, Plus(n, Negate(C1)))), x), x), x, Sin(Plus(a, Times(b, x))))), And(And(And(FreeQ(List(a, b), x), RationalQ(n)), Greater(n, C1)), OddQ(n)))),
            ISetDelayed(Int(Sqr($($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), x_Symbol),
                    Condition(Plus(Times(C1D2, x), Times(CN1, Cos(Plus(a, Times(b, x))), Sin(Plus(a, Times(b, x))), Power(Times(C2, b), -1))), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Sqr($($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), x_Symbol),
                    Condition(Plus(Times(C1D2, x), Times(Cos(Plus(a, Times(b, x))), Sin(Plus(a, Times(b, x))), Power(Times(C2, b), -1))), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, c, Cos(Plus(a, Times(b, x))), Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, Negate(C1))), Power(Times(b, n), -1)), Times(Sqr(c), Plus(n, Negate(C1)), Power(n, -1), Int(Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, Negate(C2))), x))), And(And(And(FreeQ(List(a, b, c), x), RationalQ(n)), Greater(n, C1)), Not(OddQ(n))))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Plus(Times(c, Sin(Plus(a, Times(b, x))), Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, Negate(C1))), Power(Times(b, n), -1)), Times(Sqr(c), Plus(n, Negate(C1)), Power(n, -1), Int(Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, Negate(C2))), x))), And(And(And(FreeQ(List(a, b, c), x), RationalQ(n)), Greater(n, C1)), Not(OddQ(n))))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Plus(Times(Cos(Plus(a, Times(b, x))), Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, C1)), Power(Times(b, c, Plus(n, C1)), -1)), Times(Plus(n, C2), Power(Times(Sqr(c), Plus(n, C1)), -1), Int(Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, C2)), x))), And(And(FreeQ(List(a, b, c), x), RationalQ(n)), Less(n, CN1)))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, Sin(Plus(a, Times(b, x))), Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, C1)), Power(Times(b, c, Plus(n, C1)), -1)), Times(Plus(n, C2), Power(Times(Sqr(c), Plus(n, C1)), -1), Int(Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, C2)), x))), And(And(FreeQ(List(a, b, c), x), RationalQ(n)), Less(n, CN1)))),
            ISetDelayed(Int(Sqrt($($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), x_Symbol),
                    Condition(Times(CN2, EllipticE(Plus(Times(C1D4, Pi), Times(CN1, C1D2, Plus(a, Times(b, x)))), C2), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Sqrt($($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), x_Symbol),
                    Condition(Times(C2, EllipticE(Times(C1D2, Plus(a, Times(b, x))), C2), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power($($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), CN1D2), x_Symbol),
                    Condition(Times(CN2, EllipticF(Plus(Times(C1D4, Pi), Times(CN1, C1D2, Plus(a, Times(b, x)))), C2), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power($($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_))), CN1D2), x_Symbol),
                    Condition(Times(C2, EllipticF(Times(C1D2, Plus(a, Times(b, x))), C2), Power(b, -1)), FreeQ(List(a, b), x))),
            ISetDelayed(Int(Power(Times(c_, $($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(Power(Times(c, Sin(Plus(a, Times(b, x)))), n), Power(Power(Sin(Plus(a, Times(b, x))), n), -1), Int(Power(Sin(Plus(a, Times(b, x))), n), x)), And(FreeQ(List(a, b, c), x), ZeroQ(Plus(Sqr(n), Negate(C1D4)))))),
            ISetDelayed(Int(Power(Times(c_, $($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(Power(Times(c, Cos(Plus(a, Times(b, x)))), n), Power(Power(Cos(Plus(a, Times(b, x))), n), -1), Int(Power(Cos(Plus(a, Times(b, x))), n), x)), And(FreeQ(List(a, b, c), x), ZeroQ(Plus(Sqr(n), Negate(C1D4)))))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(CN1, c, Cos(Plus(a, Times(b, x))), Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, Negate(C1))), Power(Times(b, Power(Sqr(Sin(Plus(a, Times(b, x)))), Times(C1D2, Plus(n, Negate(C1))))), -1), Hypergeometric2F1(C1D2, Times(C1D2, Plus(C1, Negate(n))), QQ(3L, 2L), Sqr(Cos(Plus(a, Times(b, x)))))), And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(C2, n)))), RationalQ(n)), Greater(n, C0)))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(c, Sin(Plus(a, Times(b, x))), Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, Negate(C1))), Power(Times(b, Power(Sqr(Cos(Plus(a, Times(b, x)))), Times(C1D2, Plus(n, Negate(C1))))), -1), Hypergeometric2F1(C1D2, Times(C1D2, Plus(C1, Negate(n))), QQ(3L, 2L), Sqr(Sin(Plus(a, Times(b, x)))))), And(And(And(FreeQ(List(a, b, c), x), Not(IntegerQ(Times(C2, n)))), RationalQ(n)), Greater(n, C0)))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§sin"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(CN1, Cos(Plus(a, Times(b, x))), Power(Times(c, Sin(Plus(a, Times(b, x)))), Plus(n, C1)), Power(Times(b, c, Power(Sqr(Sin(Plus(a, Times(b, x)))), Times(C1D2, Plus(n, C1)))), -1), Hypergeometric2F1(C1D2, Times(C1D2, Plus(C1, Negate(n))), QQ(3L, 2L), Sqr(Cos(Plus(a, Times(b, x)))))), And(And(FreeQ(List(a, b, c, n), x), Not(IntegerQ(Times(C2, n)))), Not(And(RationalQ(n), Greater(n, C0)))))),
            ISetDelayed(Int(Power(Times(c_DEFAULT, $($s("§cos"), Plus(a_DEFAULT, Times(b_DEFAULT, x_)))), n_), x_Symbol),
                    Condition(Times(Sin(Plus(a, Times(b, x))), Power(Times(c, Cos(Plus(a, Times(b, x)))), Plus(n, C1)), Power(Times(b, c, Power(Sqr(Cos(Plus(a, Times(b, x)))), Times(C1D2, Plus(n, C1)))), -1), Hypergeometric2F1(C1D2, Times(C1D2, Plus(C1, Negate(n))), QQ(3L, 2L), Sqr(Sin(Plus(a, Times(b, x)))))), And(And(FreeQ(List(a, b, c, n), x), Not(IntegerQ(Times(C2, n)))), Not(And(RationalQ(n), Greater(n, C0))))))
    );
}
