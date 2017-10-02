package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.ASymbol;
import static org.matheclipse.core.expression.F.A_;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.BSymbol;
import static org.matheclipse.core.expression.F.B_DEFAULT;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.C3;
import static org.matheclipse.core.expression.F.C4;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Coefficient;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Equal;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.GreaterEqual;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.LessEqual;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.Or;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.Unequal;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.a_DEFAULT;
import static org.matheclipse.core.expression.F.b;
import static org.matheclipse.core.expression.F.b_DEFAULT;
import static org.matheclipse.core.expression.F.c;
import static org.matheclipse.core.expression.F.c_DEFAULT;
import static org.matheclipse.core.expression.F.d;
import static org.matheclipse.core.expression.F.d_DEFAULT;
import static org.matheclipse.core.expression.F.e;
import static org.matheclipse.core.expression.F.e_DEFAULT;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.j_;
import static org.matheclipse.core.expression.F.j_DEFAULT;
import static org.matheclipse.core.expression.F.m;
import static org.matheclipse.core.expression.F.m_;
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
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
import static org.matheclipse.core.expression.F.z;
import static org.matheclipse.core.expression.F.z_;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.BinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.LinearPairQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.RationalQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Simp;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialMatchQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.TrinomialQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules24 {
    public static IAST RULES = List(
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(CN1, Power(x, Plus(m, C1)), Plus(ASymbol, Times(BSymbol, Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(C2, a, n, Plus(p, C1)), -1)), Times(Power(Times(C2, a, n, Plus(p, C1)), -1), Int(Times(Power(x, m), Plus(Times(ASymbol, Plus(m, Times(C2, n, Plus(p, C1)), C1)), Times(BSymbol, Plus(m, Times(n, Plus(Times(C2, p), C3)), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1))), x))), And(And(And(And(And(FreeQ(List(a, c, ASymbol, BSymbol), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(n)), RationalQ(m, p)), Less(p, CN1)), Less(m, Plus(n, Negate(C1)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(x, Plus(m, Negate(n), C1)), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(c, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1)), -1)), Times(CN1, Power(Times(c, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1)), -1), Int(Times(Power(x, Plus(m, Negate(n))), Simp(Plus(Times(a, BSymbol, Plus(m, Negate(n), C1)), Times(Plus(Times(b, BSymbol, Plus(m, Times(n, p), C1)), Times(CN1, c, ASymbol, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1))), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(n)), RationalQ(m, p)), GreaterEqual(m, n)), And(LessEqual(CN1, p), Less(p, C0))), Unequal(Plus(m, Times(n, Plus(Times(C2, p), C1)), C1), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(BSymbol, Power(x, Plus(m, Negate(n), C1)), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(c, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1)), -1)), Times(CN1, Power(Times(c, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1)), -1), Int(Times(Power(x, Plus(m, Negate(n))), Plus(Times(a, BSymbol, Plus(m, Negate(n), C1)), Times(CN1, c, ASymbol, Plus(m, Times(n, Plus(Times(C2, p), C1)), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, c, ASymbol, BSymbol), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(n)), RationalQ(m, p)), GreaterEqual(m, n)), And(LessEqual(CN1, p), Less(p, C0))), Unequal(Plus(m, Times(n, Plus(Times(C2, p), C1)), C1), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Power(x, Plus(m, C1)), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(a, Plus(m, C1)), -1)), Times(Power(Times(a, Plus(m, C1)), -1), Int(Times(Power(x, Plus(m, n)), Simp(Plus(Times(a, BSymbol, Plus(m, C1)), Times(CN1, ASymbol, b, Plus(m, Times(n, Plus(p, C1)), C1)), Times(CN1, c, ASymbol, Plus(m, Times(C2, n, Plus(p, C1)), C1), Power(x, n))), x), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, ASymbol, BSymbol), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), PositiveIntegerQ(n)), RationalQ(m, p)), LessEqual(m, Negate(n))), Or(And(LessEqual(CN1, p), Less(p, C0)), Equal(Plus(m, Times(n, Plus(Times(C2, p), C1)), C1), C0))), Unequal(Plus(m, C1), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Power(x, Plus(m, C1)), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Times(a, Plus(m, C1)), -1)), Times(Power(Times(a, Plus(m, C1)), -1), Int(Times(Power(x, Plus(m, n)), Plus(Times(a, BSymbol, Plus(m, C1)), Times(CN1, c, ASymbol, Plus(m, Times(C2, n, Plus(p, C1)), C1), Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x))), And(And(And(And(And(And(FreeQ(List(a, c, ASymbol, BSymbol), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), PositiveIntegerQ(n)), RationalQ(m, p)), LessEqual(m, Negate(n))), And(LessEqual(CN1, p), Less(p, C0))), Unequal(Plus(m, C1), C0)))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x)), Times(BSymbol, Int(Times(Power(x, Plus(m, n)), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x))), And(FreeQ(List(a, b, c, ASymbol, BSymbol, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Times(Plus(A_, Times(B_DEFAULT, Power(x_, n_))), Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT)), x_Symbol),
                    Condition(Plus(Times(ASymbol, Int(Times(Power(x, m), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x)), Times(BSymbol, Int(Times(Power(x, Plus(m, n)), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x))), And(FreeQ(List(a, c, ASymbol, BSymbol, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(Power(Times(d, e), -1), Int(Times(Power(x, m), Plus(Times(a, e), Times(c, d, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1)))), x)), Times(CN1, Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), Power(Times(d, e), -1), Int(Times(Power(x, Plus(m, n)), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, Power(x, n))), -1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), RationalQ(m, n, p)), Less(m, C0)), Greater(n, C0)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(x_, m_), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_DEFAULT), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(Power(Times(d, e), -1), Int(Times(Power(x, m), Plus(Times(a, e), Times(c, d, Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1)))), x)), Times(CN1, Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), Power(Times(d, e), -1), Int(Times(Power(x, Plus(m, n)), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, Negate(C1))), Power(Plus(d, Times(e, Power(x, n))), -1)), x))), And(And(And(And(And(And(FreeQ(List(a, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), RationalQ(m, n, p)), Less(m, C0)), Greater(n, C0)), Greater(p, C0)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_DEFAULT, Times(c_DEFAULT, Power(x_, j_DEFAULT)), Times(b_DEFAULT, Power(x_, n_))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), -1), Int(Times(Power(x, Plus(m, Negate(n))), Plus(Times(a, e), Times(c, d, Power(x, n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x)), Times(CN1, d, e, Power(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))), -1), Int(Times(Power(x, Plus(m, Negate(n))), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Plus(d, Times(e, Power(x, n))), -1)), x))), And(And(And(And(And(And(And(FreeQ(List(a, b, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Sqr(b), Times(CN1, C4, a, c)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(CN1, b, d, e), Times(a, Sqr(e))))), RationalQ(m, n, p)), Greater(m, C0)), Greater(n, C0)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(x_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(x_, j_DEFAULT))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(x_, n_))), -1)), x_Symbol),
                    Condition(Plus(Times(Power(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), -1), Int(Times(Power(x, Plus(m, Negate(n))), Plus(Times(a, e), Times(c, d, Power(x, n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x)), Times(CN1, d, e, Power(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))), -1), Int(Times(Power(x, Plus(m, Negate(n))), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), Plus(p, C1)), Power(Plus(d, Times(e, Power(x, n))), -1)), x))), And(And(And(And(And(And(FreeQ(List(a, c, d, e), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), NonzeroQ(Plus(Times(c, Sqr(d)), Times(a, Sqr(e))))), RationalQ(m, n, p)), Greater(m, C0)), Greater(n, C0)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(Plus(a_, Times(b_DEFAULT, Power(w_, n_)), Times(c_DEFAULT, Power(z_, j_DEFAULT))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(v_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(u, m), Power(Times(Coefficient(v, x, C1), Power(v, m)), -1), Subst(Int(Times(Power(x, m), Power(Plus(d, Times(e, Power(x, n))), q), Power(Plus(a, Times(b, Power(x, n)), Times(c, Power(x, Times(C2, n)))), p)), x), x, v)), And(And(And(And(FreeQ(List(a, b, c, d, e, m, n, p, q), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), LinearPairQ(u, v, x)), ZeroQ(Plus(v, Negate(w)))), ZeroQ(Plus(v, Negate(z)))))),
            ISetDelayed(Int(Times(Power(u_, m_DEFAULT), Power(Plus(a_, Times(c_DEFAULT, Power(z_, j_DEFAULT))), p_), Power(Plus(d_DEFAULT, Times(e_DEFAULT, Power(v_, n_))), q_DEFAULT)), x_Symbol),
                    Condition(Times(Power(u, m), Power(Times(Coefficient(v, x, C1), Power(v, m)), -1), Subst(Int(Times(Power(x, m), Power(Plus(d, Times(e, Power(x, n))), q), Power(Plus(a, Times(c, Power(x, Times(C2, n)))), p)), x), x, v)), And(And(And(FreeQ(List(a, c, d, e, m, n, p), x), ZeroQ(Plus(j, Times(CN1, C2, n)))), LinearPairQ(u, v, x)), ZeroQ(Plus(v, Negate(z)))))),
            ISetDelayed(Int(Times(Power(u_, p_DEFAULT), Power(x_, m_DEFAULT), Power(z_, q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, m), Power(ExpandToSum(z, x), q), Power(ExpandToSum(u, x), p)), x), And(And(And(FreeQ(List(m, p, q), x), BinomialQ(z, x)), TrinomialQ(u, x)), Not(And(BinomialMatchQ(z, x), TrinomialMatchQ(u, x)))))),
            ISetDelayed(Int(Times(Power(u_, p_DEFAULT), Power(x_, m_DEFAULT), Power(z_, q_DEFAULT)), x_Symbol),
                    Condition(Int(Times(Power(x, m), Power(ExpandToSum(z, x), q), Power(ExpandToSum(u, x), p)), x), And(And(And(FreeQ(List(m, p, q), x), BinomialQ(z, x)), BinomialQ(u, x)), Not(And(BinomialMatchQ(z, x), BinomialMatchQ(u, x))))))
    );
}
