package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$p;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcTan;
import static org.matheclipse.core.expression.F.ArcTanh;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.C8;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN3;
import static org.matheclipse.core.expression.F.CSymbol;
import static org.matheclipse.core.expression.F.C_DEFAULT;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.Exponent;
import static org.matheclipse.core.expression.F.Factor;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.PolynomialQ;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.ReplaceAll;
import static org.matheclipse.core.expression.F.Rule;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.UnsameQ;
import static org.matheclipse.core.expression.F.ZZ;
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
import static org.matheclipse.core.expression.F.f_;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.q;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.v;
import static org.matheclipse.core.expression.F.v_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NegQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PosQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Rt;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SimplifyIntegrand;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.SumQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules31 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(f_, Times(g_DEFAULT, Sqr(x_))), Power(Times(Plus(d_, Times(e_DEFAULT, x_), Times(d_DEFAULT, Sqr(x_))), Sqrt(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(b_DEFAULT, Power(x_, 3)), Times(a_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(a, f, Power(Times(d, Rt(Times(Sqr(a), Plus(Times(C2, a), Negate(c))), C2)), -1), ArcTan(Times(Plus(Times(a, b), Times(Plus(Times(C4, Sqr(a)), Sqr(b), Times(CN1, C2, a, c)), x), Times(a, b, Sqr(x))), Power(Times(C2, Rt(Times(Sqr(a), Plus(Times(C2, a), Negate(c))), C2), Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4))))), -1)))), And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), ZeroQ(Plus(Times(b, d), Times(CN1, a, e)))), ZeroQ(Plus(f, g))), PosQ(Times(Sqr(a), Plus(Times(C2, a), Negate(c))))))),
            ISetDelayed(Int(Times(Plus(f_, Times(g_DEFAULT, Sqr(x_))), Power(Times(Plus(d_, Times(e_DEFAULT, x_), Times(d_DEFAULT, Sqr(x_))), Sqrt(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(b_DEFAULT, Power(x_, 3)), Times(a_DEFAULT, Power(x_, 4))))), -1)), x_Symbol),
                    Condition(Times(CN1, a, f, Power(Times(d, Rt(Times(CN1, Sqr(a), Plus(Times(C2, a), Negate(c))), C2)), -1), ArcTanh(Times(Plus(Times(a, b), Times(Plus(Times(C4, Sqr(a)), Sqr(b), Times(CN1, C2, a, c)), x), Times(a, b, Sqr(x))), Power(Times(C2, Rt(Times(CN1, Sqr(a), Plus(Times(C2, a), Negate(c))), C2), Sqrt(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4))))), -1)))), And(And(And(FreeQ(List(a, b, c, d, e, f, g), x), ZeroQ(Plus(Times(b, d), Times(CN1, a, e)))), ZeroQ(Plus(f, g))), NegQ(Times(Sqr(a), Plus(Times(C2, a), Negate(c))))))),
            ISetDelayed(Int(Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), p_), x_Symbol),
                    Condition(Subst(Int(SimplifyIntegrand(Power(Plus(a, Times(Power(d, 4), Power(Times(ZZ(256L), Power(e, 3)), -1)), Times(CN1, b, d, Power(Times(C8, e), -1)), Times(Plus(c, Times(CN1, C3, Sqr(d), Power(Times(C8, e), -1))), Sqr(x)), Times(e, Power(x, 4))), p), x), x), x, Plus(Times(d, Power(Times(C4, e), -1)), x)), And(And(And(FreeQ(List(a, b, c, d, e, p), x), ZeroQ(Plus(Power(d, 3), Times(CN1, C4, c, d, e), Times(C8, b, Sqr(e))))), UnsameQ(p, C2)), UnsameQ(p, C3)))),
            ISetDelayed(Int(Power(v_, p_), x_Symbol),
                    Condition(Module(List(Set(a, Coefficient(v, x, C0)), Set(b, Coefficient(v, x, C1)), Set(c, Coefficient(v, x, C2)), Set(d, Coefficient(v, x, C3)), Set(e, Coefficient(v, x, C4))), Condition(Subst(Int(SimplifyIntegrand(Power(Plus(a, Times(Power(d, 4), Power(Times(ZZ(256L), Power(e, 3)), -1)), Times(CN1, b, d, Power(Times(C8, e), -1)), Times(Plus(c, Times(CN1, C3, Sqr(d), Power(Times(C8, e), -1))), Sqr(x)), Times(e, Power(x, 4))), p), x), x), x, Plus(Times(d, Power(Times(C4, e), -1)), x)), And(ZeroQ(Plus(Power(d, 3), Times(CN1, C4, c, d, e), Times(C8, b, Sqr(e)))), NonzeroQ(d)))), And(And(And(And(FreeQ(p, x), PolynomialQ(v, x)), Equal(Exponent(v, x), C4)), UnsameQ(p, C2)), UnsameQ(p, C3)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_DEFAULT, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), p_)), x_Symbol),
                    Condition(Subst(Int(SimplifyIntegrand(Times(ReplaceAll(u, Rule(x, Plus(Times(CN1, d, Power(Times(C4, e), -1)), x))), Power(Plus(a, Times(Power(d, 4), Power(Times(ZZ(256L), Power(e, 3)), -1)), Times(CN1, b, d, Power(Times(C8, e), -1)), Times(Plus(c, Times(CN1, C3, Sqr(d), Power(Times(C8, e), -1))), Sqr(x)), Times(e, Power(x, 4))), p)), x), x), x, Plus(Times(d, Power(Times(C4, e), -1)), x)), And(And(And(FreeQ(List(a, b, c, d, e, p), x), PolynomialQ(u, x)), ZeroQ(Plus(Power(d, 3), Times(CN1, C4, c, d, e), Times(C8, b, Sqr(e))))), Not(PositiveIntegerQ(p))))),
            ISetDelayed(Int(Times(u_, Power(v_, p_)), x_Symbol),
                    Condition(Module(List(Set(a, Coefficient(v, x, C0)), Set(b, Coefficient(v, x, C1)), Set(c, Coefficient(v, x, C2)), Set(d, Coefficient(v, x, C3)), Set(e, Coefficient(v, x, C4))), Condition(Subst(Int(SimplifyIntegrand(Times(ReplaceAll(u, Rule(x, Plus(Times(CN1, d, Power(Times(C4, e), -1)), x))), Power(Plus(a, Times(Power(d, 4), Power(Times(ZZ(256L), Power(e, 3)), -1)), Times(CN1, b, d, Power(Times(C8, e), -1)), Times(Plus(c, Times(CN1, C3, Sqr(d), Power(Times(C8, e), -1))), Sqr(x)), Times(e, Power(x, 4))), p)), x), x), x, Plus(Times(d, Power(Times(C4, e), -1)), x)), And(ZeroQ(Plus(Power(d, 3), Times(CN1, C4, c, d, e), Times(C8, b, Sqr(e)))), NonzeroQ(d)))), And(And(And(And(FreeQ(p, x), PolynomialQ(u, x)), PolynomialQ(v, x)), Equal(Exponent(v, x), C4)), Not(PositiveIntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), p_), x_Symbol),
                    Condition(Times(ZZ(-16L), Sqr(a), Subst(Int(Times(Power(Plus(b, Times(CN1, C4, a, x)), -2), Power(Times(a, Plus(Times(CN3, Power(b, 4)), Times(ZZ(16L), a, Sqr(b), c), Times(CN1, ZZ(64L), Sqr(a), b, d), Times(ZZ(256L), Power(a, 3), e), Times(CN1, ZZ(32L), Sqr(a), Plus(Times(C3, Sqr(b)), Times(CN1, C8, a, c)), Sqr(x)), Times(ZZ(256L), Power(a, 4), Power(x, 4))), Power(Plus(b, Times(CN1, C4, a, x)), -4)), p)), x), x, Plus(Times(b, Power(Times(C4, a), -1)), Power(x, -1)))), And(And(FreeQ(List(a, b, c, d, e), x), ZeroQ(Plus(Power(b, 3), Times(CN1, C4, a, b, c), Times(C8, Sqr(a), d)))), IntegerQ(Times(C2, p))))),
            ISetDelayed(Int(Power(v_, p_), x_Symbol),
                    Condition(Module(List(Set(a, Coefficient(v, x, C0)), Set(b, Coefficient(v, x, C1)), Set(c, Coefficient(v, x, C2)), Set(d, Coefficient(v, x, C3)), Set(e, Coefficient(v, x, C4))), Condition(Times(ZZ(-16L), Sqr(a), Subst(Int(Times(Power(Plus(b, Times(CN1, C4, a, x)), -2), Power(Times(a, Plus(Times(CN3, Power(b, 4)), Times(ZZ(16L), a, Sqr(b), c), Times(CN1, ZZ(64L), Sqr(a), b, d), Times(ZZ(256L), Power(a, 3), e), Times(CN1, ZZ(32L), Sqr(a), Plus(Times(C3, Sqr(b)), Times(CN1, C8, a, c)), Sqr(x)), Times(ZZ(256L), Power(a, 4), Power(x, 4))), Power(Plus(b, Times(CN1, C4, a, x)), -4)), p)), x), x, Plus(Times(b, Power(Times(C4, a), -1)), Power(x, -1)))), And(And(NonzeroQ(a), NonzeroQ(b)), ZeroQ(Plus(Power(b, 3), Times(CN1, C4, a, b, c), Times(C8, Sqr(a), d)))))), And(And(And(FreeQ(p, x), PolynomialQ(v, x)), Equal(Exponent(v, x), C4)), IntegerQ(Times(C2, p))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_)), Times($p("D", true), Power(x_, 3))), Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Sqrt(Plus(Times(C8, Sqr(a)), Sqr(b), Times(CN1, C4, a, c))))), Plus(Times(Power(q, -1), Int(Times(Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(CN1, C2, a, CSymbol), Times(b, $s("D")), Times($s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, q), x), Times(C2, a, Sqr(x))), -1)), x)), Times(CN1, Power(q, -1), Int(Times(Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(CN1, ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(CN1, C2, a, CSymbol), Times(b, $s("D")), Times(CN1, $s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, Negate(q)), x), Times(C2, a, Sqr(x))), -1)), x)))), And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, CSymbol, $s("D")), x), ZeroQ(Plus(d, Negate(b)))), ZeroQ(Plus(e, Negate(a)))), SumQ(Factor(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times($p("D", true), Power(x_, 3))), Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Sqrt(Plus(Times(C8, Sqr(a)), Sqr(b), Times(CN1, C4, a, c))))), Plus(Times(Power(q, -1), Int(Times(Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(b, $s("D")), Times($s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, q), x), Times(C2, a, Sqr(x))), -1)), x)), Times(CN1, Power(q, -1), Int(Times(Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(CN1, ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(b, $s("D")), Times(CN1, $s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, Negate(q)), x), Times(C2, a, Sqr(x))), -1)), x)))), And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, $s("D")), x), ZeroQ(Plus(d, Negate(b)))), ZeroQ(Plus(e, Negate(a)))), SumQ(Factor(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times(C_DEFAULT, Sqr(x_)), Times($p("D", true), Power(x_, 3))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Sqrt(Plus(Times(C8, Sqr(a)), Sqr(b), Times(CN1, C4, a, c))))), Plus(Times(Power(q, -1), Int(Times(Power(x, m), Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(CN1, C2, a, CSymbol), Times(b, $s("D")), Times($s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, q), x), Times(C2, a, Sqr(x))), -1)), x)), Times(CN1, Power(q, -1), Int(Times(Power(x, m), Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(CN1, ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(CN1, C2, a, CSymbol), Times(b, $s("D")), Times(CN1, $s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, Negate(q)), x), Times(C2, a, Sqr(x))), -1)), x)))), And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, CSymbol, $s("D"), m), x), ZeroQ(Plus(d, Negate(b)))), ZeroQ(Plus(e, Negate(a)))), SumQ(Factor(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4)))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(B_DEFAULT, x_), Times($p("D", true), Power(x_, 3))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, x_), Times(c_DEFAULT, Sqr(x_)), Times(d_DEFAULT, Power(x_, 3)), Times(e_DEFAULT, Power(x_, 4))), -1)), x_Symbol),
                    Condition(Module(List(Set(q, Sqrt(Plus(Times(C8, Sqr(a)), Sqr(b), Times(CN1, C4, a, c))))), Plus(Times(Power(q, -1), Int(Times(Power(x, m), Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(b, $s("D")), Times($s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, q), x), Times(C2, a, Sqr(x))), -1)), x)), Times(CN1, Power(q, -1), Int(Times(Power(x, m), Plus(Times(b, ASymbol), Times(CN1, C2, a, BSymbol), Times(C2, a, $s("D")), Times(CN1, ASymbol, q), Times(Plus(Times(C2, a, ASymbol), Times(b, $s("D")), Times(CN1, $s("D"), q)), x)), Power(Plus(Times(C2, a), Times(Plus(b, Negate(q)), x), Times(C2, a, Sqr(x))), -1)), x)))), And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol, $s("D"), m), x), ZeroQ(Plus(d, Negate(b)))), ZeroQ(Plus(e, Negate(a)))), SumQ(Factor(Plus(a, Times(b, x), Times(c, Sqr(x)), Times(b, Power(x, 3)), Times(a, Power(x, 4))))))))
    );
}
