package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.AppellF1;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C1D4;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.CSqrt2;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.EllipticE;
import static org.matheclipse.core.expression.F.EllipticF;
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
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
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
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FreeFactors;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules38 {
    public static IAST RULES = List(
            ISetDelayed(Int(Sqr(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Plus(Times(Plus(Times(C2, Sqr(a)), Sqr(b)), C1D2, x), Times(CN1, Sqr(b), Cos(Plus(c, Times(d, x))), Sin(Plus(c, Times(d, x))), Power(Times(C2, d), -1)), Times(C2, a, b, Int(Sin(Plus(c, Times(d, x))), x))), FreeQ(List(a, b, c, d), x))),
            ISetDelayed(Int(Sqr(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Plus(Times(Plus(Times(C2, Sqr(a)), Sqr(b)), C1D2, x), Times(Sqr(b), Sin(Plus(c, Times(d, x))), Cos(Plus(c, Times(d, x))), Power(Times(C2, d), -1)), Times(C2, a, b, Int(Cos(Plus(c, Times(d, x))), x))), FreeQ(List(a, b, c, d), x))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(CN2, b, Cos(Plus(c, Times(d, x))), Power(Times(d, Sqrt(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))))), -1)), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(C2, b, Sin(Plus(c, Times(d, x))), Power(Times(d, Sqrt(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))))), -1)), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, b, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), Power(Times(d, n), -1)), Times(a, Plus(Times(C2, n), Negate(C1)), Power(n, -1), Int(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Greater(n, C1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(b, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), Power(Times(d, n), -1)), Times(a, Plus(Times(C2, n), Negate(C1)), Power(n, -1), Int(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Greater(n, C1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1), x_Symbol),
                    Condition(Times(CN1, Cos(Plus(c, Times(d, x))), Power(Times(d, Plus(b, Times(a, Sin(Plus(c, Times(d, x)))))), -1)), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1), x_Symbol),
                    Condition(Times(Sin(Plus(c, Times(d, x))), Power(Times(d, Plus(b, Times(a, Cos(Plus(c, Times(d, x)))))), -1)), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(CN2, Power(d, -1), Subst(Int(Power(Plus(Times(C2, a), Negate(Sqr(x))), -1), x), x, Times(b, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), CN1D2)))), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(C2, Power(d, -1), Subst(Int(Power(Plus(Times(C2, a), Negate(Sqr(x))), -1), x), x, Times(b, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), CN1D2)))), And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(b, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), n), Power(Times(a, d, Plus(Times(C2, n), C1)), -1)), Times(Plus(n, C1), Power(Times(a, Plus(Times(C2, n), C1)), -1), Int(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, C1)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Less(n, CN1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, b, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), n), Power(Times(a, d, Plus(Times(C2, n), C1)), -1)), Times(Plus(n, C1), Power(Times(a, Plus(Times(C2, n), C1)), -1), Int(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, C1)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Less(n, CN1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Times(a, CSqrt2, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), n), Power(Times(b, d, Plus(Times(C2, n), C1), Sqrt(Times(Plus(a, Times(CN1, b, Sin(Plus(c, Times(d, x))))), Power(a, -1)))), -1), Hypergeometric2F1(C1D2, Plus(n, C1D2), Plus(n, QQ(3L, 2L)), Times(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Power(Times(C2, a), -1)))), And(And(FreeQ(List(a, b, c, d, n), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(IntegerQ(Times(C2, n)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Times(CN1, a, CSqrt2, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), n), Power(Times(b, d, Plus(Times(C2, n), C1), Sqrt(Times(Plus(a, Times(CN1, b, Cos(Plus(c, Times(d, x))))), Power(a, -1)))), -1), Hypergeometric2F1(C1D2, Plus(n, C1D2), Plus(n, QQ(3L, 2L)), Times(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Power(Times(C2, a), -1)))), And(And(FreeQ(List(a, b, c, d, n), x), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(IntegerQ(Times(C2, n)))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(CN2, Sqrt(Plus(a, b)), Power(d, -1), EllipticE(Plus(Times(C1D4, Pi), Times(CN1, C1D2, Plus(c, Times(d, x)))), Times(C2, b, Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, b))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(C2, Sqrt(Plus(a, b)), Power(d, -1), EllipticE(Times(C1D2, Plus(c, Times(d, x))), Times(C2, b, Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, b))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(C2, Sqrt(Plus(a, Negate(b))), Power(d, -1), EllipticE(Plus(Times(C1D4, Pi), Times(C1D2, Plus(c, Times(d, x)))), Times(CN2, b, Power(Plus(a, Negate(b)), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, Negate(b)))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(C2, Sqrt(Plus(a, Negate(b))), Power(d, -1), EllipticE(Plus(Times(C1D2, Pi), Times(C1D2, Plus(c, Times(d, x)))), Times(CN2, b, Power(Plus(a, Negate(b)), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, Negate(b)))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(Sqrt(Plus(a, Times(b, Sin(Plus(c, Times(d, x)))))), Power(Times(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Power(Plus(a, b), -1)), CN1D2), Int(Sqrt(Plus(Times(a, Power(Plus(a, b), -1)), Times(b, Power(Plus(a, b), -1), Sin(Plus(c, Times(d, x)))))), x)), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(PositiveQ(Plus(a, b)))))),
            ISetDelayed(Int(Sqrt(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), x_Symbol),
                    Condition(Times(Sqrt(Plus(a, Times(b, Cos(Plus(c, Times(d, x)))))), Power(Times(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Power(Plus(a, b), -1)), CN1D2), Int(Sqrt(Plus(Times(a, Power(Plus(a, b), -1)), Times(b, Power(Plus(a, b), -1), Cos(Plus(c, Times(d, x)))))), x)), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(PositiveQ(Plus(a, b)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, b, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), Power(Times(d, n), -1)), Times(Power(n, -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, Negate(C2))), Simp(Plus(Times(Sqr(a), n), Times(Sqr(b), Plus(n, Negate(C1))), Times(a, b, Plus(Times(C2, n), Negate(C1)), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Greater(n, C1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(b, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, Negate(C1))), Power(Times(d, n), -1)), Times(Power(n, -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, Negate(C2))), Simp(Plus(Times(Sqr(a), n), Times(Sqr(b), Plus(n, Negate(C1))), Times(a, b, Plus(Times(C2, n), Negate(C1)), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Greater(n, C1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1), x_Symbol),
                    Condition(Module(List(Set(e, FreeFactors(Tan(Times(C1D2, Plus(c, Times(d, x)))), x))), Times(C2, e, Power(d, -1), Subst(Int(Power(Plus(a, Times(C2, b, e, x), Times(a, Sqr(e), Sqr(x))), -1), x), x, Times(Tan(Times(C1D2, Plus(c, Times(d, x)))), Power(e, -1))))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), -1), x_Symbol),
                    Condition(Module(List(Set(e, FreeFactors(Tan(Times(C1D2, Plus(c, Times(d, x)))), x))), Times(C2, e, Power(d, -1), Subst(Int(Power(Plus(a, b, Times(Plus(a, Negate(b)), Sqr(e), Sqr(x))), -1), x), x, Times(Tan(Times(C1D2, Plus(c, Times(d, x)))), Power(e, -1))))), And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(CN2, Power(Times(d, Sqrt(Plus(a, b))), -1), EllipticF(Plus(Times(C1D4, Pi), Times(CN1, C1D2, Plus(c, Times(d, x)))), Times(C2, b, Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, b))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(C2, Power(Times(d, Sqrt(Plus(a, b))), -1), EllipticF(Times(C1D2, Plus(c, Times(d, x))), Times(C2, b, Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, b))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(C2, Power(Times(d, Sqrt(Plus(a, Negate(b)))), -1), EllipticF(Plus(Times(C1D4, Pi), Times(C1D2, Plus(c, Times(d, x)))), Times(CN2, b, Power(Plus(a, Negate(b)), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, Negate(b)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(C2, Power(Times(d, Sqrt(Plus(a, Negate(b)))), -1), EllipticF(Plus(Times(C1D2, Pi), Times(C1D2, Plus(c, Times(d, x)))), Times(CN2, b, Power(Plus(a, Negate(b)), -1)))), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), PositiveQ(Plus(a, Negate(b)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Power(Plus(a, b), -1))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), CN1D2), Int(Power(Plus(Times(a, Power(Plus(a, b), -1)), Times(b, Power(Plus(a, b), -1), Sin(Plus(c, Times(d, x))))), CN1D2), x)), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(PositiveQ(Plus(a, b)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), CN1D2), x_Symbol),
                    Condition(Times(Sqrt(Times(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Power(Plus(a, b), -1))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), CN1D2), Int(Power(Plus(Times(a, Power(Plus(a, b), -1)), Times(b, Power(Plus(a, b), -1), Cos(Plus(c, Times(d, x))))), CN1D2), x)), And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(PositiveQ(Plus(a, b)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(CN1, b, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, C1)), Power(Times(d, Plus(n, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(Plus(n, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, C1)), Simp(Plus(Times(a, Plus(n, C1)), Times(CN1, b, Plus(n, C2), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Less(n, CN1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Plus(Times(b, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, C1)), Power(Times(d, Plus(n, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(Plus(n, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, C1)), Simp(Plus(Times(a, Plus(n, C1)), Times(CN1, b, Plus(n, C2), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), RationalQ(n)), Less(n, CN1)), IntegerQ(Times(C2, n))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(n, C1)), Sqrt(Times(b, Plus(C1, Negate(Sin(Plus(c, Times(d, x))))), Power(Plus(a, b), -1))), Sqrt(Times(CN1, b, Plus(C1, Sin(Plus(c, Times(d, x)))), Power(Plus(a, Negate(b)), -1))), Power(Times(b, d, Plus(n, C1), Cos(Plus(c, Times(d, x)))), -1), AppellF1(Plus(n, C1), C1D2, C1D2, Plus(n, C2), Times(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Power(Plus(a, Negate(b)), -1)), Times(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(IntegerQ(Times(C2, n)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Times(CN1, Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(n, C1)), Sqrt(Times(b, Plus(C1, Negate(Cos(Plus(c, Times(d, x))))), Power(Plus(a, b), -1))), Sqrt(Times(CN1, b, Plus(C1, Cos(Plus(c, Times(d, x)))), Power(Plus(a, Negate(b)), -1))), Power(Times(b, d, Plus(n, C1), Sin(Plus(c, Times(d, x)))), -1), AppellF1(Plus(n, C1), C1D2, C1D2, Plus(n, C2), Times(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Power(Plus(a, Negate(b)), -1)), Times(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Power(Plus(a, b), -1)))), And(And(FreeQ(List(a, b, c, d, n), x), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))), Not(IntegerQ(Times(C2, n)))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), n_), x_Symbol),
                    Condition(Int(Power(Plus(a, Times(b, C1D2, Sin(Plus(Times(C2, c), Times(C2, d, x))))), n), x), FreeQ(List(a, b, c, d, n), x)))
    );
}
