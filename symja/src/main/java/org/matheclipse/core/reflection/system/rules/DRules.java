package org.matheclipse.core.reflection.system.rules;

import org.matheclipse.core.interfaces.IAST;

import static org.matheclipse.core.expression.F.$;
import static org.matheclipse.core.expression.F.$p;
import static org.matheclipse.core.expression.F.Abs;
import static org.matheclipse.core.expression.F.And;
import static org.matheclipse.core.expression.F.ArcCos;
import static org.matheclipse.core.expression.F.ArcCosh;
import static org.matheclipse.core.expression.F.ArcCot;
import static org.matheclipse.core.expression.F.ArcCoth;
import static org.matheclipse.core.expression.F.ArcCsc;
import static org.matheclipse.core.expression.F.ArcCsch;
import static org.matheclipse.core.expression.F.ArcSec;
import static org.matheclipse.core.expression.F.ArcSech;
import static org.matheclipse.core.expression.F.ArcSin;
import static org.matheclipse.core.expression.F.ArcSinh;
import static org.matheclipse.core.expression.F.ArcTan;
import static org.matheclipse.core.expression.F.ArcTanh;
import static org.matheclipse.core.expression.F.BesselJ;
import static org.matheclipse.core.expression.F.Binomial;
import static org.matheclipse.core.expression.F.C0;
import static org.matheclipse.core.expression.F.C1;
import static org.matheclipse.core.expression.F.C1D2;
import static org.matheclipse.core.expression.F.C2;
import static org.matheclipse.core.expression.F.CN1;
import static org.matheclipse.core.expression.F.CN1D2;
import static org.matheclipse.core.expression.F.CN2;
import static org.matheclipse.core.expression.F.Ceiling;
import static org.matheclipse.core.expression.F.Condition;
import static org.matheclipse.core.expression.F.Cos;
import static org.matheclipse.core.expression.F.Cosh;
import static org.matheclipse.core.expression.F.Cot;
import static org.matheclipse.core.expression.F.Coth;
import static org.matheclipse.core.expression.F.Csc;
import static org.matheclipse.core.expression.F.Csch;
import static org.matheclipse.core.expression.F.D;
import static org.matheclipse.core.expression.F.Derivative;
import static org.matheclipse.core.expression.F.DiracDelta;
import static org.matheclipse.core.expression.F.E;
import static org.matheclipse.core.expression.F.Erf;
import static org.matheclipse.core.expression.F.Erfc;
import static org.matheclipse.core.expression.F.Erfi;
import static org.matheclipse.core.expression.F.Factorial;
import static org.matheclipse.core.expression.F.Floor;
import static org.matheclipse.core.expression.F.FractionalPart;
import static org.matheclipse.core.expression.F.FreeQ;
import static org.matheclipse.core.expression.F.FresnelC;
import static org.matheclipse.core.expression.F.FresnelS;
import static org.matheclipse.core.expression.F.Gamma;
import static org.matheclipse.core.expression.F.GreaterEqual;
import static org.matheclipse.core.expression.F.HeavisideTheta;
import static org.matheclipse.core.expression.F.IInit;
import static org.matheclipse.core.expression.F.ISetDelayed;
import static org.matheclipse.core.expression.F.IntegerPart;
import static org.matheclipse.core.expression.F.IntegerQ;
import static org.matheclipse.core.expression.F.InverseErf;
import static org.matheclipse.core.expression.F.KroneckerDelta;
import static org.matheclipse.core.expression.F.List;
import static org.matheclipse.core.expression.F.Log;
import static org.matheclipse.core.expression.F.LogisticSigmoid;
import static org.matheclipse.core.expression.F.Negate;
import static org.matheclipse.core.expression.F.NotListQ;
import static org.matheclipse.core.expression.F.Pi;
import static org.matheclipse.core.expression.F.Plus;
import static org.matheclipse.core.expression.F.Pochhammer;
import static org.matheclipse.core.expression.F.PolyGamma;
import static org.matheclipse.core.expression.F.Power;
import static org.matheclipse.core.expression.F.ProductLog;
import static org.matheclipse.core.expression.F.QQ;
import static org.matheclipse.core.expression.F.Round;
import static org.matheclipse.core.expression.F.Sec;
import static org.matheclipse.core.expression.F.Sech;
import static org.matheclipse.core.expression.F.Sin;
import static org.matheclipse.core.expression.F.Sinh;
import static org.matheclipse.core.expression.F.Sqr;
import static org.matheclipse.core.expression.F.Sqrt;
import static org.matheclipse.core.expression.F.Sum;
import static org.matheclipse.core.expression.F.Tan;
import static org.matheclipse.core.expression.F.Tanh;
import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.f_;
import static org.matheclipse.core.expression.F.g;
import static org.matheclipse.core.expression.F.g_;
import static org.matheclipse.core.expression.F.j;
import static org.matheclipse.core.expression.F.k;
import static org.matheclipse.core.expression.F.n;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://bitbucket.org/axelclk/symja_android_library">bitbucket.org/axelclk/symja_android_library under the tools directory</a>.</p>
 */
public interface DRules {
    /**
     * <ul>
     * <li>index 0 - number of equal rules in <code>RULES</code></li>
     * </ul>
     */
    final public static int[] SIZES = {0, 69};

    final public static IAST RULES = List(
            IInit(D, SIZES),
            // D(ArcCos(f_),x_NotListQ):=(D(f,x)*(-1))/Sqrt(1-f^2)
            ISetDelayed(D(ArcCos(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Power(Plus(C1, Negate(Sqr(f))), CN1D2))),
            // D(ArcCosh(f_),x_NotListQ):=D(f,x)/Sqrt(-1+f^2)
            ISetDelayed(D(ArcCosh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(CN1, Sqr(f)), CN1D2))),
            // D(ArcCot(f_),x_NotListQ):=(D(f,x)*(-1))/(1+f^2)
            ISetDelayed(D(ArcCot(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Power(Plus(C1, Sqr(f)), -1))),
            // D(ArcCoth(f_),x_NotListQ):=D(f,x)/(1-f^2)
            ISetDelayed(D(ArcCoth(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(C1, Negate(Sqr(f))), -1))),
            // D(ArcCsc(f_),x_NotListQ):=(-D(f,x)*1)/(f^2*Sqrt(1-1/x^2))
            ISetDelayed(D(ArcCsc(f_), $p(x, NotListQ)),
                    Times(CN1, D(f, x), C1, Power(f, -2), Power(Plus(C1, Negate(Power(x, -2))), CN1D2))),
            // D(ArcCsch(f_),x_NotListQ):=(D(f,x)*(-1))/(Abs(f)*Sqrt(1+f^2))
            ISetDelayed(D(ArcCsch(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Power(Abs(f), -1), Power(Plus(C1, Sqr(f)), CN1D2))),
            // D(ArcSin(f_),x_NotListQ):=D(f,x)/Sqrt(1-f^2)
            ISetDelayed(D(ArcSin(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(C1, Negate(Sqr(f))), CN1D2))),
            // D(ArcSinh(f_),x_NotListQ):=D(f,x)/Sqrt(1+f^2)
            ISetDelayed(D(ArcSinh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(C1, Sqr(f)), CN1D2))),
            // D(ArcTan(f_),x_NotListQ):=D(f,x)/(1+f^2)
            ISetDelayed(D(ArcTan(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(C1, Sqr(f)), -1))),
            // D(ArcTanh(f_),x_NotListQ):=D(f,x)/(1-f^2)
            ISetDelayed(D(ArcTanh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(Plus(C1, Negate(Sqr(f))), -1))),
            // D(ArcSec(f_),x_NotListQ):=D(f,x)/(x^2*Sqrt(1-1/f^2))
            ISetDelayed(D(ArcSec(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(x, -2), Power(Plus(C1, Negate(Power(f, -2))), CN1D2))),
            // D(ArcSech(f_),x_NotListQ):=(D(f,x)*(-1))/(f*Sqrt(1-f^2))
            ISetDelayed(D(ArcSech(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Power(f, -1), Power(Plus(C1, Negate(Sqr(f))), CN1D2))),
            // D(Ceiling(f_),x_NotListQ):=0
            ISetDelayed(D(Ceiling(f_), $p(x, NotListQ)),
                    C0),
            // D(Erf(f_),x_NotListQ):=D(f,x)*2*1/(E^f^2*Sqrt(Pi))
            ISetDelayed(D(Erf(f_), $p(x, NotListQ)),
                    Times(D(f, x), C2, Power(E, Negate(Sqr(f))), Power(Pi, CN1D2))),
            // D(Erfc(f_),x_NotListQ):=D(f,x)*-2*1/(E^f^2*Sqrt(Pi))
            ISetDelayed(D(Erfc(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN2, Power(E, Negate(Sqr(f))), Power(Pi, CN1D2))),
            // D(Erfi(f_),x_NotListQ):=D(f,x)*2*E^f^2/Sqrt(Pi)
            ISetDelayed(D(Erfi(f_), $p(x, NotListQ)),
                    Times(D(f, x), C2, Power(E, Sqr(f)), Power(Pi, CN1D2))),
            // D(Floor(f_),x_NotListQ):=0
            ISetDelayed(D(Floor(f_), $p(x, NotListQ)),
                    C0),
            // D(FractionalPart(f_),x_NotListQ):=D(f,x)*1
            ISetDelayed(D(FractionalPart(f_), $p(x, NotListQ)),
                    Times(D(f, x), C1)),
            // D(FresnelC(f_),x_NotListQ):=D(f,x)*Cos(1/2*f^2*Pi)
            ISetDelayed(D(FresnelC(f_), $p(x, NotListQ)),
                    Times(D(f, x), Cos(Times(C1D2, Sqr(f), Pi)))),
            // D(FresnelS(f_),x_NotListQ):=D(f,x)*Sin(1/2*f^2*Pi)
            ISetDelayed(D(FresnelS(f_), $p(x, NotListQ)),
                    Times(D(f, x), Sin(Times(C1D2, Sqr(f), Pi)))),
            // D(Gamma(f_),x_NotListQ):=D(f,x)*Gamma(f)*PolyGamma(f)
            ISetDelayed(D(Gamma(f_), $p(x, NotListQ)),
                    Times(D(f, x), Gamma(f), PolyGamma(f))),
            // D(HeavisideTheta(f_),x_NotListQ):=D(f,x)*DiracDelta(f)
            ISetDelayed(D(HeavisideTheta(f_), $p(x, NotListQ)),
                    Times(D(f, x), DiracDelta(f))),
            // D(IntegerPart(f_),x_NotListQ):=0
            ISetDelayed(D(IntegerPart(f_), $p(x, NotListQ)),
                    C0),
            // D(InverseErf(f_),x_NotListQ):=D(f,x)*1/2*Sqrt(Pi)*E^InverseErf(f)^2
            ISetDelayed(D(InverseErf(f_), $p(x, NotListQ)),
                    Times(D(f, x), C1D2, Sqrt(Pi), Power(E, Sqr(InverseErf(f))))),
            // D(Log(f_),x_NotListQ):=D(f,x)/f
            ISetDelayed(D(Log(f_), $p(x, NotListQ)),
                    Times(D(f, x), Power(f, -1))),
            // D(LogisticSigmoid(f_),x_NotListQ):=D(f,x)*LogisticSigmoid(f)*(1-LogisticSigmoid(f))
            ISetDelayed(D(LogisticSigmoid(f_), $p(x, NotListQ)),
                    Times(D(f, x), LogisticSigmoid(f), Plus(C1, Negate(LogisticSigmoid(f))))),
            // D(PolyGamma(f_),x_NotListQ):=D(f,x)*PolyGamma(1,f)
            ISetDelayed(D(PolyGamma(f_), $p(x, NotListQ)),
                    Times(D(f, x), PolyGamma(C1, f))),
            // D(Cot(f_),x_NotListQ):=D(f,x)*(-1)*Csc(f)^2
            ISetDelayed(D(Cot(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Sqr(Csc(f)))),
            // D(Coth(f_),x_NotListQ):=(D(f,x)*(-1))/Sinh(f)^2
            ISetDelayed(D(Coth(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Power(Sinh(f), -2))),
            // D(Cos(f_),x_NotListQ):=D(f,x)*(-1)*Sin(f)
            ISetDelayed(D(Cos(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Sin(f))),
            // D(Cosh(f_),x_NotListQ):=D(f,x)*Sinh(f)
            ISetDelayed(D(Cosh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Sinh(f))),
            // D(Csc(f_),x_NotListQ):=D(f,x)*(-1)*Cot(f)*Csc(f)
            ISetDelayed(D(Csc(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Cot(f), Csc(f))),
            // D(Csch(f_),x_NotListQ):=D(f,x)*(-1)*Coth(f)*Csch(f)
            ISetDelayed(D(Csch(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Coth(f), Csch(f))),
            // D(Round(f_),x_NotListQ):=0
            ISetDelayed(D(Round(f_), $p(x, NotListQ)),
                    C0),
            // D(Sin(f_),x_NotListQ):=D(f,x)*Cos(f)
            ISetDelayed(D(Sin(f_), $p(x, NotListQ)),
                    Times(D(f, x), Cos(f))),
            // D(Sinh(f_),x_NotListQ):=D(f,x)*Cosh(f)
            ISetDelayed(D(Sinh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Cosh(f))),
            // D(Tan(f_),x_NotListQ):=D(f,x)*Sec(f)^2
            ISetDelayed(D(Tan(f_), $p(x, NotListQ)),
                    Times(D(f, x), Sqr(Sec(f)))),
            // D(Tanh(f_),x_NotListQ):=D(f,x)*Sech(f)^2
            ISetDelayed(D(Tanh(f_), $p(x, NotListQ)),
                    Times(D(f, x), Sqr(Sech(f)))),
            // D(Sec(f_),x_NotListQ):=D(f,x)*Sec(f)*Tan(f)
            ISetDelayed(D(Sec(f_), $p(x, NotListQ)),
                    Times(D(f, x), Sec(f), Tan(f))),
            // D(Sech(f_),x_NotListQ):=D(f,x)*(-1)*Tanh(f)*Sech(f)
            ISetDelayed(D(Sech(f_), $p(x, NotListQ)),
                    Times(D(f, x), CN1, Tanh(f), Sech(f))),
            // D(ArcCos(x_),{x_,2}):=-x/(1-x^2)^(3/2)
            ISetDelayed(D(ArcCos(x_), List(x_, C2)),
                    Times(CN1, x, Power(Plus(C1, Negate(Sqr(x))), QQ(-3L, 2L)))),
            // D(ArcCot(x_),{x_,2}):=(2*x)/(1+x^2)^2
            ISetDelayed(D(ArcCot(x_), List(x_, C2)),
                    Times(C2, x, Power(Plus(C1, Sqr(x)), -2))),
            // D(ArcSin(x_),{x_,2}):=x/(1-x^2)^(3/2)
            ISetDelayed(D(ArcSin(x_), List(x_, C2)),
                    Times(x, Power(Plus(C1, Negate(Sqr(x))), QQ(-3L, 2L)))),
            // D(ArcTan(x_),{x_,2}):=((-1)*2*x)/(1+x^2)^2
            ISetDelayed(D(ArcTan(x_), List(x_, C2)),
                    Times(CN1, C2, x, Power(Plus(C1, Sqr(x)), -2))),
            // D(ArcCosh(x_),{x_,2}):=-x/((-1+x)^(3/2)*(1+x)^(3/2))
            ISetDelayed(D(ArcCosh(x_), List(x_, C2)),
                    Times(CN1, x, Power(Times(Power(Plus(CN1, x), QQ(3L, 2L)), Power(Plus(C1, x), QQ(3L, 2L))), -1))),
            // D(ArcCoth(x_),{x_,2}):=(2*x)/(1-x^2)^2
            ISetDelayed(D(ArcCoth(x_), List(x_, C2)),
                    Times(C2, x, Power(Plus(C1, Negate(Sqr(x))), -2))),
            // D(ArcSinh(x_),{x_,2}):=-x/(1+x^2)^(3/2)
            ISetDelayed(D(ArcSinh(x_), List(x_, C2)),
                    Times(CN1, x, Power(Plus(C1, Sqr(x)), QQ(-3L, 2L)))),
            // D(ArcTanh(x_),{x_,2}):=(2*x)/(1-x^2)^2
            ISetDelayed(D(ArcTanh(x_), List(x_, C2)),
                    Times(C2, x, Power(Plus(C1, Negate(Sqr(x))), -2))),
            // D(ArcCsc(x_),{x_,2}):=(-1+2*x^2)/(Sqrt(1-1/x^2)*x^3*(-1+x^2))
            ISetDelayed(D(ArcCsc(x_), List(x_, C2)),
                    Times(Plus(CN1, Times(C2, Sqr(x))), Power(Times(Sqrt(Plus(C1, Negate(Power(x, -2)))), Power(x, 3), Plus(CN1, Sqr(x))), -1))),
            // D(ArcSec(x_),{x_,2}):=(1-2*x^2)/(Sqrt(1-1/x^2)*x^3*(-1+x^2))
            ISetDelayed(D(ArcSec(x_), List(x_, C2)),
                    Times(Plus(C1, Times(CN2, Sqr(x))), Power(Times(Sqrt(Plus(C1, Negate(Power(x, -2)))), Power(x, 3), Plus(CN1, Sqr(x))), -1))),
            // D(Cos(x_),{x_,2}):=-Cos(x)
            ISetDelayed(D(Cos(x_), List(x_, C2)),
                    Negate(Cos(x))),
            // D(Cot(x_),{x_,2}):=2*Cot(x)*Csc(x)^2
            ISetDelayed(D(Cot(x_), List(x_, C2)),
                    Times(C2, Cot(x), Sqr(Csc(x)))),
            // D(Sin(x_),{x_,2}):=-Sin(x)
            ISetDelayed(D(Sin(x_), List(x_, C2)),
                    Negate(Sin(x))),
            // D(Tan(x_),{x_,2}):=2*Sec(x)^2*Tan(x)
            ISetDelayed(D(Tan(x_), List(x_, C2)),
                    Times(C2, Sqr(Sec(x)), Tan(x))),
            // D(Csc(x_),{x_,2}):=Csc(x)^3+Csc(x)*Cot(x)^2
            ISetDelayed(D(Csc(x_), List(x_, C2)),
                    Plus(Power(Csc(x), 3), Times(Csc(x), Sqr(Cot(x))))),
            // D(Sec(x_),{x_,2}):=Sec(x)^3+Sec(x)*Tan(x)^2
            ISetDelayed(D(Sec(x_), List(x_, C2)),
                    Plus(Power(Sec(x), 3), Times(Sec(x), Sqr(Tan(x))))),
            // D(x_^a_,{x_,n_IntegerQ}):=Pochhammer(a-n+1,n)*x^(a-n)/;n>=0&&FreeQ(a,x)
            ISetDelayed(D(Power(x_, a_), List(x_, $p(n, IntegerQ))),
                    Condition(Times(Pochhammer(Plus(a, Negate(n), C1), n), Power(x, Plus(a, Negate(n)))), And(GreaterEqual(n, C0), FreeQ(a, x)))),
            // D(a_^x_,{x_,n_IntegerQ}):=a^x*Log(x)^n/;n>=0&&FreeQ(a,x)
            ISetDelayed(D(Power(a_, x_), List(x_, $p(n, IntegerQ))),
                    Condition(Times(Power(a, x), Power(Log(x), n)), And(GreaterEqual(n, C0), FreeQ(a, x)))),
            // D(ArcCos(x_),{x_,n_IntegerQ}):=KroneckerDelta(n)*ArcCos(x)-1/((-1)^(1-n)*(1-x^2)^(-1/2+n))*Sum((Pochhammer(1-n,k)*Pochhammer(1/2,k)*2^(1+2*k-n)*x^(1+2*k-n)*(-1+x^2)^(-1-k+n))/(2*k-n+1)!,{k,0,-1+n})/;n>=0
            ISetDelayed(D(ArcCos(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(KroneckerDelta(n), ArcCos(x)), Times(CN1, Power(CN1, Plus(CN1, n)), Power(Power(Plus(C1, Negate(Sqr(x))), Plus(CN1D2, n)), -1), Sum(Times(Power(Factorial(Plus(Times(C2, k), Negate(n), C1)), -1), Pochhammer(Plus(C1, Negate(n)), k), Pochhammer(C1D2, k), Power(C2, Plus(C1, Times(C2, k), Negate(n))), Power(x, Plus(C1, Times(C2, k), Negate(n))), Power(Plus(CN1, Sqr(x)), Plus(CN1, Negate(k), n))), List(k, C0, Plus(CN1, n))))), GreaterEqual(n, C0))),
            // D(ArcCot(x_),{x_,n_IntegerQ}):=KroneckerDelta(n)*ArcCot(x)-Sum(((-1)^k*1/((-1-k+n)!/(2*x)^(1+2*k-n))*k!*Pochhammer(2*k-n+2,-2+2*(-k+n)))/(1+x^2)^(1+k),{k,0,-1+n})/;n>=0
            ISetDelayed(D(ArcCot(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(KroneckerDelta(n), ArcCot(x)), Negate(Sum(Times(Power(CN1, k), Power(Times(Factorial(Plus(CN1, Negate(k), n)), Power(Times(C2, x), Plus(CN1, Times(CN2, k), n))), -1), Factorial(k), Pochhammer(Plus(Times(C2, k), Negate(n), C2), Plus(CN2, Times(C2, Plus(Negate(k), n)))), Power(Plus(C1, Sqr(x)), Plus(CN1, Negate(k)))), List(k, C0, Plus(CN1, n))))), GreaterEqual(n, C0))),
            // D(ArcSin(x_),{x_,n_IntegerQ}):=KroneckerDelta(n)*ArcSin(x)+1/((-1)^(1-n)*(1-x^2)^(-1/2+n))*Sum((2^(1+2*k-n)*x^(1+2*k-n)*Pochhammer(1/2,k)*Pochhammer(1-n,k))/((-1+x^2)^(1+k-n)*(2*k-n+1)!),{k,0,-1+n})/;n>=0
            ISetDelayed(D(ArcSin(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(KroneckerDelta(n), ArcSin(x)), Times(Power(CN1, Plus(CN1, n)), Power(Power(Plus(C1, Negate(Sqr(x))), Plus(CN1D2, n)), -1), Sum(Times(Power(C2, Plus(C1, Times(C2, k), Negate(n))), Power(x, Plus(C1, Times(C2, k), Negate(n))), Power(Plus(CN1, Sqr(x)), Plus(CN1, Negate(k), n)), Power(Factorial(Plus(Times(C2, k), Negate(n), C1)), -1), Pochhammer(C1D2, k), Pochhammer(Plus(C1, Negate(n)), k)), List(k, C0, Plus(CN1, n))))), GreaterEqual(n, C0))),
            // D(ArcTan(x_),{x_,n_IntegerQ}):=KroneckerDelta(n)*ArcTan(x)+Sum(((-1)^k*1/((-1-k+n)!/(2*x)^(1+2*k-n))*k!*Pochhammer(2*k-n+2,-2+2*(-k+n)))/(1+x^2)^(1+k),{k,0,-1+n})/;n>=0
            ISetDelayed(D(ArcTan(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(KroneckerDelta(n), ArcTan(x)), Sum(Times(Power(CN1, k), Power(Times(Factorial(Plus(CN1, Negate(k), n)), Power(Times(C2, x), Plus(CN1, Times(CN2, k), n))), -1), Factorial(k), Pochhammer(Plus(Times(C2, k), Negate(n), C2), Plus(CN2, Times(C2, Plus(Negate(k), n)))), Power(Plus(C1, Sqr(x)), Plus(CN1, Negate(k)))), List(k, C0, Plus(CN1, n)))), GreaterEqual(n, C0))),
            // D(Cos(x_),{x_,n_IntegerQ}):=Cos(x+1/2*n*Pi)/;n>=0
            ISetDelayed(D(Cos(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Cos(Plus(x, Times(C1D2, n, Pi))), GreaterEqual(n, C0))),
            // D(Cot(x_),{x_,n_IntegerQ}):=-Csc(x)^2*KroneckerDelta(-1+n)+Cot(x)*KroneckerDelta(n)-n*Sum((((-1)^j*Binomial(-1+n,k))/(k+1)*2^(-2*k+n)*Binomial(2*k,j)*Sin(1/2*n*Pi+2*(-j+k)*x))/(Sin(x)^(2+2*k)*(-j+k)^(1-n)),{k,0,-1+n},{j,0,-1+k})/;n>=0
            ISetDelayed(D(Cot(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(CN1, Sqr(Csc(x)), KroneckerDelta(Plus(CN1, n))), Times(Cot(x), KroneckerDelta(n)), Times(CN1, n, Sum(Times(Power(CN1, j), Power(Plus(k, C1), -1), Binomial(Plus(CN1, n), k), Power(Sin(x), Plus(CN2, Times(CN2, k))), Power(C2, Plus(Times(CN2, k), n)), Binomial(Times(C2, k), j), Power(Plus(Negate(j), k), Plus(CN1, n)), Sin(Plus(Times(C1D2, n, Pi), Times(C2, Plus(Negate(j), k), x)))), List(k, C0, Plus(CN1, n)), List(j, C0, Plus(CN1, k))))), GreaterEqual(n, C0))),
            // D(Sin(x_),{x_,n_IntegerQ}):=Sin(x+1/2*n*Pi)/;n>=0
            ISetDelayed(D(Sin(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Sin(Plus(x, Times(C1D2, n, Pi))), GreaterEqual(n, C0))),
            // D(Tan(x_),{x_,n_IntegerQ}):=Tan(x)*KroneckerDelta(n)+Sec(x)^2*KroneckerDelta(-1+n)+n*Sum((((-1)^k*Binomial(-1+n,k))/(k+1)*2^(-2*k+n)*Binomial(2*k,j)*Sin(1/2*n*Pi+2*(-j+k)*x))/(Cos(x)^(2+2*k)*(-j+k)^(1-n)),{k,0,-1+n},{j,0,-1+k})/;n>=0
            ISetDelayed(D(Tan(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Plus(Times(Tan(x), KroneckerDelta(n)), Times(Sqr(Sec(x)), KroneckerDelta(Plus(CN1, n))), Times(n, Sum(Times(Power(CN1, k), Power(Plus(k, C1), -1), Binomial(Plus(CN1, n), k), Power(Cos(x), Plus(CN2, Times(CN2, k))), Power(C2, Plus(Times(CN2, k), n)), Binomial(Times(C2, k), j), Power(Plus(Negate(j), k), Plus(CN1, n)), Sin(Plus(Times(C1D2, n, Pi), Times(C2, Plus(Negate(j), k), x)))), List(k, C0, Plus(CN1, n)), List(j, C0, Plus(CN1, k))))), GreaterEqual(n, C0))),
            // D(Log(x_),{x_,n_IntegerQ}):=(-1+n)!/((-1)^(1-n)*x^n)/;n>=0
            ISetDelayed(D(Log(x_), List(x_, $p(n, IntegerQ))),
                    Condition(Times(Power(CN1, Plus(CN1, n)), Power(Power(x, n), -1), Factorial(Plus(CN1, n))), GreaterEqual(n, C0))),
            // D(BesselJ(f_,g_),x_NotListQ):=1/2*(BesselJ(-1+f,g)-BesselJ(1+f,g))*D(g,x)+D(f,x)*Derivative(1,0)[BesselJ][f,g]
            ISetDelayed(D(BesselJ(f_, g_), $p(x, NotListQ)),
                    Plus(Times(C1D2, Plus(BesselJ(Plus(CN1, f), g), Negate(BesselJ(Plus(C1, f), g))), D(g, x)), Times(D(f, x), $($(Derivative(C1, C0), BesselJ), f, g)))),
            // D(ProductLog(f_,g_),x_NotListQ):=ProductLog(f,g)*D(g,x)/(g*(1+ProductLog(f,g)))+D(f,x)*Derivative(1,0)[ProductLog][f,g]
            ISetDelayed(D(ProductLog(f_, g_), $p(x, NotListQ)),
                    Plus(Times(ProductLog(f, g), D(g, x), Power(Times(g, Plus(C1, ProductLog(f, g))), -1)), Times(D(f, x), $($(Derivative(C1, C0), ProductLog), f, g))))
    );
}
