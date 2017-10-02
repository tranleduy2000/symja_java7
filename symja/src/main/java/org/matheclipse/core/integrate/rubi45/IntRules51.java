package org.matheclipse.core.integrate.rubi45;


import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$s;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.Cot;
import static org.matheclipse.core.expression.F.EvenQ;
import static org.matheclipse.core.expression.F.Expand;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.Greater;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.Less;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Module;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.Not;
import static org.matheclipse.core.expression.F.OddQ;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.Set;
import static org.matheclipse.core.expression.F.Sin;
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
import static org.matheclipse.core.expression.F.m_DEFAULT;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.n_;
import static org.matheclipse.core.expression.F.p;
import static org.matheclipse.core.expression.F.p_;
import static org.matheclipse.core.expression.F.p_DEFAULT;
import static org.matheclipse.core.expression.F.u;
import static org.matheclipse.core.expression.F.u_;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;
import static org.matheclipse.core.expression.F.x_Symbol;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ActivateTrig;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandToSum;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ExpandTrig;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.FreeFactors;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Int;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.IntegersQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.NonzeroQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.PositiveIntegerQ;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.Subst;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.ZeroQ;

/**
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 */
public class IntRules51 {
    public static IAST RULES = List(
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Times(Power(a, p), Int(Power(Cos(Plus(c, Times(d, x))), Times(C2, p)), x)), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(a, b))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Times(Power(a, p), Int(Power(Sin(Plus(c, Times(d, x))), Times(C2, p)), x)), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(a, b))), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Int(Power(Times(a, Sqr(Cos(Plus(c, Times(d, x))))), p), x), And(And(FreeQ(List(a, b, c, d, p), x), ZeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Int(Power(Times(a, Sqr(Sin(Plus(c, Times(d, x))))), p), x), And(And(FreeQ(List(a, b, c, d, p), x), ZeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Module(List(Set(e, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(e, Power(d, -1), Subst(Int(Times(Power(Plus(a, Times(Plus(a, b), Sqr(e), Sqr(x))), p), Power(Power(Plus(C1, Times(Sqr(e), Sqr(x))), Plus(p, C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(e, -1))))), And(FreeQ(List(a, b, c, d), x), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Module(List(Set(e, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(e, Power(d, -1), Subst(Int(Times(Power(Plus(a, b, Times(a, Sqr(e), Sqr(x))), p), Power(Power(Plus(C1, Times(Sqr(e), Sqr(x))), Plus(p, C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(e, -1))))), And(FreeQ(List(a, b, c, d), x), IntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Times(Power(Power(C2, p), -1), Int(Power(Plus(Times(C2, a), b, Times(CN1, b, Cos(Plus(Times(C2, c), Times(C2, d, x))))), p), x)), And(And(FreeQ(List(a, b, c, d, p), x), NonzeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_), x_Symbol),
                    Condition(Times(Power(Power(C2, p), -1), Int(Power(Plus(Times(C2, a), b, Times(b, Cos(Plus(Times(C2, c), Times(C2, d, x))))), p), x)), And(And(FreeQ(List(a, b, c, d, p), x), NonzeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), x_Symbol),
                    Condition(Int(Expand(Power(Plus(a, Times(b, Power(Sin(Plus(c, Times(d, x))), n))), p), x), x), And(And(FreeQ(List(a, b, c, d), x), IntegerQ(n)), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), x_Symbol),
                    Condition(Int(Expand(Power(Plus(a, Times(b, Power(Cos(Plus(c, Times(d, x))), n))), p), x), x), And(And(FreeQ(List(a, b, c, d), x), IntegerQ(n)), PositiveIntegerQ(p)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(c, Times(d, x))), x))), Times(CN1, f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(b, Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(n)), IntegerQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(b, Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(n)), IntegerQ(p)), Less(p, CN1)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_)), x_Symbol),
                    Condition(Times(Power(a, p), Int(Times(ActivateTrig(u), Power(Cos(Plus(c, Times(d, x))), Times(C2, p))), x)), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(a, b))), IntegerQ(p)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_)), x_Symbol),
                    Condition(Times(Power(a, p), Int(Times(ActivateTrig(u), Power(Sin(Plus(c, Times(d, x))), Times(C2, p))), x)), And(And(FreeQ(List(a, b, c, d), x), ZeroQ(Plus(a, b))), IntegerQ(p)))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_)), x_Symbol),
                    Condition(Times(Power(Times(a, Sqr(Cos(Plus(c, Times(d, x))))), p), Power(Power(Cos(Plus(c, Times(d, x))), Times(C2, p)), -1), Int(Times(ActivateTrig(u), Power(Cos(Plus(c, Times(d, x))), Times(C2, p))), x)), And(And(FreeQ(List(a, b, c, d, p), x), ZeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(u_, Power(Plus(a_, Times(b_DEFAULT, Sqr($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))))), p_)), x_Symbol),
                    Condition(Times(Power(Times(a, Sqr(Sin(Plus(c, Times(d, x))))), p), Power(Power(Sin(Plus(c, Times(d, x))), Times(C2, p)), -1), Int(Times(ActivateTrig(u), Power(Sin(Plus(c, Times(d, x))), Times(C2, p))), x)), And(And(FreeQ(List(a, b, c, d, p), x), ZeroQ(Plus(a, b))), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(c, Times(d, x))), x))), Times(CN1, f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(b, Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(n)), EvenQ(m)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(b, Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(n)), EvenQ(m)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cos(Plus(c, Times(d, x))), x))), Times(CN1, f, Power(d, -1), Subst(Int(Times(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, Negate(C1)))), Power(ExpandToSum(Plus(a, Times(b, Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p)), x), x, Times(Cos(Plus(c, Times(d, x))), Power(f, -1))))), And(And(FreeQ(List(a, b, c, d, p), x), EvenQ(n)), OddQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Sin(Plus(c, Times(d, x))), x))), Times(f, Power(d, -1), Subst(Int(Times(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, Negate(C1)))), Power(ExpandToSum(Plus(a, Times(b, Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p)), x), x, Times(Sin(Plus(c, Times(d, x))), Power(f, -1))))), And(And(FreeQ(List(a, b, c, d, p), x), EvenQ(n)), OddQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandTrig(Times(Power($($s("§sin"), Plus(c, Times(d, x))), m), Power(Plus(a, Times(b, Power($($s("§sin"), Plus(c, Times(d, x))), n))), p)), x), x), And(FreeQ(List(a, b, c, d), x), IntegersQ(m, n, p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Int(ExpandTrig(Times(Power($($s("§cos"), Plus(c, Times(d, x))), m), Power(Plus(a, Times(b, Power($($s("§cos"), Plus(c, Times(d, x))), n))), p)), x), x), And(FreeQ(List(a, b, c, d), x), IntegersQ(m, n, p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), EvenQ(n)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(c, Times(d, x))), x))), Times(CN1, f, Power(d, -1), Subst(Int(Times(Power(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(C1D2, m), Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), EvenQ(n)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Int(Expand(Times(Power(Plus(C1, Negate(Sqr(Sin(Plus(c, Times(d, x)))))), Times(C1D2, m)), Power(Plus(a, Times(b, Power(Sin(Plus(c, Times(d, x))), n))), p)), x), x), And(And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), OddQ(n)), IntegerQ(p)), Greater(m, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Int(Expand(Times(Power(Plus(C1, Negate(Sqr(Cos(Plus(c, Times(d, x)))))), Times(C1D2, m)), Power(Plus(a, Times(b, Power(Cos(Plus(c, Times(d, x))), n))), p)), x), x), And(And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), OddQ(n)), IntegerQ(p)), Greater(m, C0)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Int(ExpandTrig(Times(Power(Plus(C1, Negate(Sqr($($s("§sin"), Plus(c, Times(d, x)))))), Times(C1D2, m)), Power(Plus(a, Times(b, Power($($s("§sin"), Plus(c, Times(d, x))), n))), p)), x), x), And(And(And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), OddQ(n)), IntegerQ(p)), Less(m, C0)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Int(ExpandTrig(Times(Power(Plus(C1, Negate(Sqr($($s("§cos"), Plus(c, Times(d, x)))))), Times(C1D2, m)), Power(Plus(a, Times(b, Power($($s("§cos"), Plus(c, Times(d, x))), n))), p)), x), x), And(And(And(And(And(FreeQ(List(a, b, c, d), x), EvenQ(m)), OddQ(n)), IntegerQ(p)), Less(m, C0)), Less(p, CN1)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(Times(e_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_))), p_DEFAULT), Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Sin(Plus(c, Times(d, x))), x))), Times(f, Power(d, -1), Subst(Int(Times(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, Negate(C1)))), Power(Plus(a, Times(b, Power(Times(e, f, x), n))), p)), x), x, Times(Sin(Plus(c, Times(d, x))), Power(f, -1))))), And(FreeQ(List(a, b, c, d, e, n, p), x), OddQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(Times(e_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_))), p_DEFAULT), Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cos(Plus(c, Times(d, x))), x))), Times(CN1, f, Power(d, -1), Subst(Int(Times(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, Negate(C1)))), Power(Plus(a, Times(b, Power(Times(e, f, x), n))), p)), x), x, Times(Cos(Plus(c, Times(d, x))), Power(f, -1))))), And(FreeQ(List(a, b, c, d, e, n, p), x), OddQ(m)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(Times(e_DEFAULT, $($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_))), p_DEFAULT), Power($($s("§tan"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Sin(Plus(c, Times(d, x))), x))), Times(Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(Times(e, f, x), n))), p), Power(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, C1))), -1)), x), x, Times(Sin(Plus(c, Times(d, x))), Power(f, -1))))), And(And(FreeQ(List(a, b, c, d, e, n), x), OddQ(m)), IntegerQ(Times(C2, p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power(Times(e_DEFAULT, $($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_)))), n_))), p_DEFAULT), Power($($s("§cot"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_DEFAULT)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cos(Plus(c, Times(d, x))), x))), Times(CN1, Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(Plus(a, Times(b, Power(Times(e, f, x), n))), p), Power(Power(Plus(C1, Times(CN1, Sqr(f), Sqr(x))), Times(C1D2, Plus(m, C1))), -1)), x), x, Times(Cos(Plus(c, Times(d, x))), Power(f, -1))))), And(And(FreeQ(List(a, b, c, d, e, n), x), OddQ(m)), IntegerQ(Times(C2, p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_DEFAULT), Power($($s("§tan"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(n, C1D2, p), C1)), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d, m), x), Not(OddQ(m))), EvenQ(n)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_DEFAULT), Power($($s("§cot"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(c, Times(d, x))), x))), Times(CN1, Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), p), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Plus(Times(n, C1D2, p), C1)), -1)), x), x, Times(Cot(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d, m), x), Not(OddQ(m))), EvenQ(n)), IntegerQ(p)))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§sin"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_DEFAULT), Power($($s("§tan"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Tan(Plus(c, Times(d, x))), x))), Times(Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(Times(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)), -1)), p), Power(Plus(C1, Times(Sqr(f), Sqr(x))), -1)), x), x, Times(Tan(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d, m, p), x), Not(OddQ(m))), EvenQ(n)), Not(IntegerQ(p))))),
            ISetDelayed(Int(Times(Power(Plus(a_, Times(b_DEFAULT, Power($($s("§cos"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), n_))), p_DEFAULT), Power($($s("§cot"), Plus(c_DEFAULT, Times(d_DEFAULT, x_))), m_)), x_Symbol),
                    Condition(Module(List(Set(f, FreeFactors(Cot(Plus(c, Times(d, x))), x))), Times(CN1, Power(f, Plus(m, C1)), Power(d, -1), Subst(Int(Times(Power(x, m), Power(Times(ExpandToSum(Plus(Times(b, Power(f, n), Power(x, n)), Times(a, Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)))), x), Power(Power(Plus(C1, Times(Sqr(f), Sqr(x))), Times(C1D2, n)), -1)), p), Power(Plus(C1, Times(Sqr(f), Sqr(x))), -1)), x), x, Times(Cot(Plus(c, Times(d, x))), Power(f, -1))))), And(And(And(FreeQ(List(a, b, c, d, m, p), x), Not(OddQ(m))), EvenQ(n)), Not(IntegerQ(p)))))
    );
}
