package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C1D3;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CSymbol;
import static org.matheclipse.core.expression.F.C_DEFAULT;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Denominator;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.EvenQ;
import static org.matheclipse.core.expression.F.Exponent;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Numerator;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.PolynomialQ;
import static org.matheclipse.core.expression.F.PolynomialQuotient;
import static org.matheclipse.core.expression.F.PolynomialRemainder;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.ReplaceAll;
import static org.matheclipse.core.expression.F.Rule;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sum;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.i;
import static org.matheclipse.core.expression.F.k;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.n_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.r;
import static org.matheclipse.core.expression.F.s;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegativeIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplifyIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules29 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Int(Times(PolynomialQuotient(u, Plus(a, Times(b, Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x), And(And(And(FreeQ(List(a, b, p), x), PolynomialQ(u, x)), PositiveIntegerQ(n)), ZeroQ(PolynomialRemainder(u, Plus(a, Times(b, Power(x, n))), x))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Coefficient(u, x, Plus(n, Negate(C1))), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, n, Plus(p, C1)), -1)), Int(Times(ExpandToSum(Plus(u, Times(CN1, Coefficient(u, x, Plus(n, Negate(C1))), Power(x, Plus(n, Negate(C1))))), x), Power(Plus(a, Times(b, Power(x, n))), p)), x)), And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(n, p)), NonzeroQ(Coefficient(u, x, Plus(n, Negate(C1))))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(u, Power(Plus(a, Times(b, Power(x, n))), p)), x), x), And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(n, p)), ZeroQ(Coefficient(u, x, Plus(n, Negate(C1))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Times(Power(BSymbol, 3), Power(b, -1), Int(Power(Plus(Sqr(ASymbol), Times(CN1, ASymbol, BSymbol, x), Times(Sqr(BSymbol), Sqr(x))), -1), x)), And(FreeQ(List(a, b, ASymbol, BSymbol), x), ZeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(a, Power(b, -1)), C3))), Set(s, Denominator(Rt(Times(a, Power(b, -1)), C3)))), Plus(Times(CN1, r, Plus(Times(BSymbol, r), Times(CN1, ASymbol, s)), Power(Times(C3, a, s), -1), Int(Power(Plus(r, Times(s, x)), -1), x)), Times(r, Power(Times(C3, a, s), -1), Int(Times(Plus(Times(r, Plus(Times(BSymbol, r), Times(C2, ASymbol, s))), Times(s, Plus(Times(BSymbol, r), Times(CN1, ASymbol, s)), x)), Power(Plus(Sqr(r), Times(CN1, r, s, x), Times(Sqr(s), Sqr(x))), -1)), x)))), And(And(FreeQ(List(a, b, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(r, Numerator(Rt(Times(CN1, a, Power(b, -1)), C3))), Set(s, Denominator(Rt(Times(CN1, a, Power(b, -1)), C3)))), Plus(Times(r, Plus(Times(BSymbol, r), Times(ASymbol, s)), Power(Times(C3, a, s), -1), Int(Power(Plus(r, Times(CN1, s, x)), -1), x)), Times(CN1, r, Power(Times(C3, a, s), -1), Int(Times(Plus(Times(r, Plus(Times(BSymbol, r), Times(CN1, C2, ASymbol, s))), Times(CN1, s, Plus(Times(BSymbol, r), Times(ASymbol, s)), x)), Power(Plus(Sqr(r), Times(r, s, x), Times(Sqr(s), Sqr(x))), -1)), x)))), And(And(FreeQ(List(a, b, ASymbol, BSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, x), -1), x)), Times(Sqr(q), Plus(ASymbol, Times(CN1, CSymbol, Sqr(q))), Power(a, -1), Int(Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1), x))), And(ZeroQ(Plus(ASymbol, Times(CN1, BSymbol, q), Times(CN1, C2, CSymbol, Sqr(q)))), NonzeroQ(Plus(ASymbol, Times(CN1, CSymbol, Sqr(q))))))), And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, x), -1), x)), Times(CN1, CSymbol, Power(q, 4), Power(a, -1), Int(Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1), x))), ZeroQ(Plus(BSymbol, Times(C2, CSymbol, q))))), And(FreeQ(List(a, b, BSymbol, CSymbol), x), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, x), -1), x)), Times(CSymbol, Power(q, 4), Power(a, -1), Int(Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1), x))), ZeroQ(Plus(ASymbol, Times(CN1, C2, CSymbol, Sqr(q)))))), And(FreeQ(List(a, b, ASymbol, CSymbol), x), PosQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(Sqr(q), Plus(ASymbol, Times(CN1, CSymbol, Sqr(q))), Power(a, -1), Int(Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1), x))), And(ZeroQ(Plus(ASymbol, Times(BSymbol, q), Times(CN1, C2, CSymbol, Sqr(q)))), NonzeroQ(Plus(ASymbol, Times(CN1, CSymbol, Sqr(q))))))), And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(CN1, CSymbol, Power(q, 4), Power(a, -1), Int(Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1), x))), ZeroQ(Plus(BSymbol, Times(CN1, C2, CSymbol, q))))), And(FreeQ(List(a, b, BSymbol, CSymbol), x), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(CSymbol, Power(q, 3), Power(a, -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(CSymbol, Power(q, 4), Power(a, -1), Int(Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1), x))), ZeroQ(Plus(ASymbol, Times(CN1, C2, CSymbol, Sqr(q)))))), And(FreeQ(List(a, b, ASymbol, CSymbol), x), NegQ(Times(a, Power(b, -1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Plus(Int(Times(Plus(ASymbol, Times(BSymbol, x)), Power(Plus(a, Times(b, Power(x, 3))), -1)), x), Times(CSymbol, Int(Times(Sqr(x), Power(Plus(a, Times(b, Power(x, 3))), -1)), x))), And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), Or(ZeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3)))), Not(RationalQ(Times(a, Power(b, -1)))))))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Int(Times(x, Power(Plus(a, Times(b, Power(x, 3))), -1)), x)), Times(CSymbol, Int(Times(Sqr(x), Power(Plus(a, Times(b, Power(x, 3))), -1)), x))), And(FreeQ(List(a, b, BSymbol, CSymbol), x), Not(RationalQ(Times(a, Power(b, -1))))))),
            ISetDelayed(Int(Times(Plus(A_, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Power(Plus(a, Times(b, Power(x, 3))), -1), x)), Times(CSymbol, Int(Times(Sqr(x), Power(Plus(a, Times(b, Power(x, 3))), -1)), x))), And(FreeQ(List(a, b, ASymbol, CSymbol), x), Not(RationalQ(a, b, ASymbol, CSymbol))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Times(Sqr(q), Power(a, -1), Int(Times(Plus(ASymbol, Times(CSymbol, q, x)), Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(ASymbol, Times(CN1, BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(Times(B_, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Times(BSymbol, Sqr(q), Power(a, -1), Int(Times(x, Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(Negate(BSymbol), Times(CSymbol, q))))), And(And(FreeQ(List(a, b, BSymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Times(Sqr(q), Power(a, -1), Int(Times(Plus(ASymbol, Times(CSymbol, q, x)), Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(ASymbol, Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, ASymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(q, Plus(ASymbol, Times(CN1, BSymbol, q), Times(CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, x), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(q, Plus(Times(C2, ASymbol), Times(BSymbol, q), Times(CN1, CSymbol, Sqr(q)))), Times(CN1, Plus(ASymbol, Times(CN1, BSymbol, q), Times(CN1, C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(ASymbol, Times(CN1, BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(CN1, q, Plus(Times(BSymbol, q), Times(CN1, CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, x), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(q, Plus(Times(BSymbol, q), Times(CN1, CSymbol, Sqr(q)))), Times(Plus(Times(BSymbol, q), Times(C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(Times(BSymbol, q), Times(CN1, CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, BSymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(a, Power(b, -1)), C1D3))), Condition(Plus(Times(q, Plus(ASymbol, Times(CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, x), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(q, Plus(Times(C2, ASymbol), Times(CN1, CSymbol, Sqr(q)))), Times(CN1, Plus(ASymbol, Times(CN1, C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(CN1, q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(ASymbol, Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, ASymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Greater(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Times(q, Power(a, -1), Int(Times(Plus(Times(ASymbol, q), Times(Plus(ASymbol, Times(BSymbol, q)), x)), Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(ASymbol, Times(BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Times(BSymbol, Sqr(q), Power(a, -1), Int(Times(x, Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(Times(BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, BSymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Times(ASymbol, q, Power(a, -1), Int(Times(Plus(q, x), Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x)), ZeroQ(Plus(ASymbol, Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, ASymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(q, Plus(ASymbol, Times(BSymbol, q), Times(CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(q, Plus(Times(C2, ASymbol), Times(CN1, BSymbol, q), Times(CN1, CSymbol, Sqr(q)))), Times(Plus(ASymbol, Times(BSymbol, q), Times(CN1, C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(ASymbol, Times(BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(And(FreeQ(List(a, b, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(a, Power(BSymbol, 3)), Times(CN1, b, Power(ASymbol, 3))))), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(x_, Plus(B_, Times(C_DEFAULT, x_)), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(q, Plus(Times(BSymbol, q), Times(CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(CN1, q, Plus(Times(BSymbol, q), Times(CSymbol, Sqr(q)))), Times(Plus(Times(BSymbol, q), Times(CN1, C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(Times(BSymbol, q), Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, BSymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(C_DEFAULT, Sqr(x_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, 3))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Power(Times(CN1, a, Power(b, -1)), C1D3))), Condition(Plus(Times(q, Plus(ASymbol, Times(CSymbol, Sqr(q))), Power(Times(C3, a), -1), Int(Power(Plus(q, Negate(x)), -1), x)), Times(q, Power(Times(C3, a), -1), Int(Times(Plus(Times(q, Plus(Times(C2, ASymbol), Times(CN1, CSymbol, Sqr(q)))), Times(Plus(ASymbol, Times(CN1, C2, CSymbol, Sqr(q))), x)), Power(Plus(Sqr(q), Times(q, x), Sqr(x)), -1)), x))), NonzeroQ(Plus(ASymbol, Times(CSymbol, Sqr(q)))))), And(And(FreeQ(List(a, b, ASymbol, CSymbol), x), RationalQ(Times(a, Power(b, -1)))), Less(Times(a, Power(b, -1)), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, m_))), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Power(Plus(a, Times(b, Power(x, n))), p), x)), Times(BSymbol, Int(Times(Power(x, Plus(n, Negate(C1))), Power(Plus(a, Times(b, Power(x, n))), p)), x))), And(FreeQ(List(a, b, ASymbol, BSymbol, m, n, p), x), ZeroQ(Plus(m, Negate(n), C1))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(Coefficient(u, x, Plus(n, Negate(C1))), Int(Times(Power(x, Plus(n, Negate(C1))), Power(Plus(a, Times(b, Power(x, n))), p)), x)), Int(Times(ExpandToSum(Plus(u, Times(CN1, Coefficient(u, x, Plus(n, Negate(C1))), Power(x, Plus(n, Negate(C1))))), x), Power(Plus(a, Times(b, Power(x, n))), p)), x)), And(And(And(FreeQ(List(a, b, p), x), PolynomialQ(u, x)), PositiveIntegerQ(n)), Equal(Exponent(u, x), Plus(n, Negate(C1)))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(x, Sum(Times(Coefficient(u, x, k), Power(x, k), Power(Plus(Times(n, p), k, C1), -1)), List(k, C0, Plus(n, Negate(C2)))), Power(Plus(a, Times(b, Power(x, n))), p)), Times(a, n, p, Int(Times(Sum(Times(Coefficient(u, x, k), Power(x, k), Power(Plus(Times(n, p), k, C1), -1)), List(k, C0, Plus(n, Negate(C2)))), Power(Plus(a, Times(b, Power(x, n))), Plus(p, Negate(C1)))), x))), And(And(And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(n)), Less(Less(C0, Exponent(u, x)), Plus(n, Negate(C1)))), RationalQ(p)), Greater(p, C0)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Module(List(Set(v, Sum(Times(Power(x, i), Plus(Coefficient(u, x, i), Times(Coefficient(u, x, Plus(Times(C1D2, n), i)), Power(x, Times(C1D2, n)))), Power(Plus(a, Times(b, Power(x, n))), -1)), List(i, C0, Plus(Times(C1D2, n), Negate(C1)))))), Condition(Int(v, x), SumQ(v))), And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(Times(C1D2, n))), Less(Exponent(u, x), Plus(n, Negate(C1)))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(u, Power(Plus(a, Times(b, Power(x, n))), -1)), x), x), And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(n)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(CN1, x, u, Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(a, n, Plus(p, C1)), -1)), Times(Power(Times(a, n, Plus(p, C1)), -1), Int(Times(Sum(Times(Plus(Times(n, Plus(p, C1)), k, C1), Coefficient(u, x, k), Power(x, k)), List(k, C0, Plus(n, Negate(C2)))), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), PositiveIntegerQ(n)), Less(Less(C0, Exponent(u, x)), Plus(n, Negate(C1)))), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(u_, Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Plus(Times(u, Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1)), Power(Times(b, n, Plus(p, C1)), -1)), Times(CN1, Power(Times(b, n, Plus(p, C1)), -1), Int(Times(Sum(Times(k, Coefficient(u, x, k), Power(x, Plus(k, Negate(C1)))), List(k, C1, Exponent(u, x))), Power(Plus(a, Times(b, Power(x, n))), Plus(p, C1))), x))), And(And(And(And(And(FreeQ(List(a, b), x), PolynomialQ(u, x)), ZeroQ(Plus(m, Negate(Plus(n, Negate(C1)))))), PositiveIntegerQ(n)), RationalQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(u_, Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Module(List(Set(v, Sum(Times(Power(x, Plus(m, i)), Plus(Coefficient(u, x, i), Times(Coefficient(u, x, Plus(Times(C1D2, n), i)), Power(x, Times(C1D2, n)))), Power(Plus(a, Times(b, Power(x, n))), p)), List(i, C0, Plus(Times(C1D2, n), Negate(C1)))))), Condition(Int(v, x), SumQ(v))), And(And(And(And(FreeQ(List(a, b, m), x), PolynomialQ(u, x)), EvenQ(n)), NegativeIntegerQ(p)), Less(Less(C0, Exponent(u, x)), n)))),
            ISetDelayed(Int(Times(u_, Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(x_, n_))), p_)), x_Symbol),
                    Condition(Int(ExpandIntegrand(Times(Power(x, m), u, Power(Plus(a, Times(b, Power(x, n))), p)), x), x), And(And(And(FreeQ(List(a, b, m), x), PolynomialQ(u, x)), PositiveIntegerQ(n)), IntegersQ(m, p)))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(b_DEFAULT, Power(Plus(c_, Times(d_DEFAULT, x_)), n_))), p_)), x_Symbol),
                    Condition(Module(List(Set(k, Denominator(n))), Times(k, Power(d, -1), Subst(Int(SimplifyIntegrand(Times(Power(x, Plus(k, Negate(C1))), Power(ReplaceAll(u, Rule(x, Plus(Times(Power(x, k), Power(d, -1)), Times(CN1, c, Power(d, -1))))), m), Power(Plus(a, Times(b, Power(x, Times(k, n)))), p)), x), x), x, Power(Plus(c, Times(d, x)), Power(k, -1))))), And(And(And(FreeQ(List(a, b, c, d, p), x), PolynomialQ(u, x)), IntegerQ(m)), RationalQ(n))))
    );
}
