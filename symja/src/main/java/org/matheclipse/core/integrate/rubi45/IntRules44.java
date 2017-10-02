package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_DEFAULT;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CSymbol;
import static org.matheclipse.core.expression.F.C_DEFAULT;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules44 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(b, -2), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(b, BSymbol), Times(CN1, a, CSymbol), Times(b, CSymbol, Sin(Plus(c, Times(d, x))))), x)), x)), And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol, m), x), ZeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Times(Power(b, -2), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(b, BSymbol), Times(CN1, a, CSymbol), Times(b, CSymbol, Cos(Plus(c, Times(d, x))))), x)), x)), And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol, m), x), ZeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Times(CSymbol, Power(b, -2), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Negate(a), Times(b, Sin(Plus(c, Times(d, x))))), x)), x)), And(FreeQ(List(a, b, c, d, ASymbol, CSymbol, m), x), ZeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Times(CSymbol, Power(b, -2), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Negate(a), Times(b, Cos(Plus(c, Times(d, x))))), x)), x)), And(FreeQ(List(a, b, c, d, ASymbol, CSymbol, m), x), ZeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, b), Times(CN1, a, BSymbol), Times(b, CSymbol)), Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), m), Power(Times(a, d, Plus(Times(C2, m), C1)), -1)), Times(Power(Times(Sqr(a), Plus(Times(C2, m), C1)), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, ASymbol, Plus(m, C1)), Times(m, Plus(Times(b, BSymbol), Times(CN1, a, CSymbol))), Times(b, CSymbol, Plus(Times(C2, m), C1), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol), Times(b, CSymbol)), Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), m), Power(Times(a, d, Plus(Times(C2, m), C1)), -1)), Times(Power(Times(Sqr(a), Plus(Times(C2, m), C1)), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, ASymbol, Plus(m, C1)), Times(m, Plus(Times(b, BSymbol), Times(CN1, a, CSymbol))), Times(b, CSymbol, Plus(Times(C2, m), C1), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(b, Plus(ASymbol, CSymbol), Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), m), Power(Times(a, d, Plus(Times(C2, m), C1)), -1)), Times(Power(Times(Sqr(a), Plus(Times(C2, m), C1)), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, ASymbol, Plus(m, C1)), Times(CN1, a, CSymbol, m), Times(b, CSymbol, Plus(Times(C2, m), C1), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, b, Plus(ASymbol, CSymbol), Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), m), Power(Times(a, d, Plus(Times(C2, m), C1)), -1)), Times(Power(Times(Sqr(a), Plus(Times(C2, m), C1)), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, ASymbol, Plus(m, C1)), Times(CN1, a, CSymbol, m), Times(b, CSymbol, Plus(Times(C2, m), C1), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), ZeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)), Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(b, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(b, Plus(Times(a, ASymbol), Times(CN1, b, BSymbol), Times(a, CSymbol)), Plus(m, C1)), Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol), Times(b, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol), Times(b, CSymbol)), Plus(m, C1))), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)), Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(b, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(b, Plus(Times(a, ASymbol), Times(CN1, b, BSymbol), Times(a, CSymbol)), Plus(m, C1)), Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol), Times(b, Plus(Times(ASymbol, b), Times(CN1, a, BSymbol), Times(b, CSymbol)), Plus(m, C1))), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)), Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(b, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, b, Plus(ASymbol, CSymbol), Plus(m, C1)), Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol), Times(Sqr(b), Plus(ASymbol, CSymbol), Plus(m, C1))), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_)), x_Symbol),
                    Condition(Plus(Times(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)), Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1)), Times(Power(Times(b, Plus(m, C1), Plus(Sqr(a), Negate(Sqr(b)))), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Simp(Plus(Times(a, b, Plus(ASymbol, CSymbol), Plus(m, C1)), Times(CN1, Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol), Times(Sqr(b), Plus(ASymbol, CSymbol), Plus(m, C1))), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), RationalQ(m)), Less(m, CN1)), NonzeroQ(Plus(Sqr(a), Negate(Sqr(b))))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, CSymbol, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C2)), -1)), Times(Power(Times(b, Plus(m, C2)), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), m), Simp(Plus(Times(ASymbol, b, Plus(m, C2)), Times(b, CSymbol, Plus(m, C1)), Times(Plus(Times(b, BSymbol, Plus(m, C2)), Times(CN1, a, CSymbol)), Sin(Plus(c, Times(d, x))))), x)), x))), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol, m), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), Not(And(RationalQ(m), Less(m, CN1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Times(B_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CSymbol, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C2)), -1)), Times(Power(Times(b, Plus(m, C2)), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), m), Simp(Plus(Times(ASymbol, b, Plus(m, C2)), Times(b, CSymbol, Plus(m, C1)), Times(Plus(Times(b, BSymbol, Plus(m, C2)), Times(CN1, a, CSymbol)), Cos(Plus(c, Times(d, x))))), x)), x))), And(And(FreeQ(List(a, b, c, d, ASymbol, BSymbol, CSymbol, m), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(CN1, a, b, BSymbol), Times(Sqr(a), CSymbol)))), Not(And(RationalQ(m), Less(m, CN1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, CSymbol, Cos(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C2)), -1)), Times(Power(Times(b, Plus(m, C2)), -1), Int(Times(Power(Plus(a, Times(b, Sin(Plus(c, Times(d, x))))), m), Simp(Plus(Times(ASymbol, b, Plus(m, C2)), Times(b, CSymbol, Plus(m, C1)), Times(CN1, a, CSymbol, Sin(Plus(c, Times(d, x))))), x)), x))), And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol, m), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), Not(And(RationalQ(m), Less(m, CN1)))))),
            ISetDelayed(Int(Times(Plus(A_DEFAULT, Times(C_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), Power(Plus(a_DEFAULT, Times(b_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))))), m_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CSymbol, Sin(Plus(c, Times(d, x))), Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), Plus(m, C1)), Power(Times(b, d, Plus(m, C2)), -1)), Times(Power(Times(b, Plus(m, C2)), -1), Int(Times(Power(Plus(a, Times(b, Cos(Plus(c, Times(d, x))))), m), Simp(Plus(Times(ASymbol, b, Plus(m, C2)), Times(b, CSymbol, Plus(m, C1)), Times(CN1, a, CSymbol, Cos(Plus(c, Times(d, x))))), x)), x))), And(And(FreeQ(List(a, b, c, d, ASymbol, CSymbol, m), x), NonzeroQ(Plus(Times(ASymbol, Sqr(b)), Times(Sqr(a), CSymbol)))), Not(And(RationalQ(m), Less(m, CN1))))))
    );
}
