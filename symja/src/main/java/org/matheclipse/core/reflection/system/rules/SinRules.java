package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://bitbucket.org/axelclk/symja_android_library">bitbucket.org/axelclk/symja_android_library under the tools directory</a>.</p>
 */
public interface SinRules {
  /**
   * <ul>
   * <li>index 0 - number of equal rules in <code>RULES</code></li>
	 * </ul>
	 */
  final public static int[] SIZES = { 55, 4 };

  final public static IAST RULES = List(
    IInit(Sin, SIZES),
    // Sin(0)=0
    ISet(Sin(C0),
      C0),
    // Sin(Pi/12)=(-1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(1L,12L),Pi)),
      Times(C1D2,C1DSqrt2,Plus(CN1,CSqrt3))),
    // Sin(Pi/10)=1/4*(-1+Sqrt(5))
    ISet(Sin(Times(QQ(1L,10L),Pi)),
      Times(C1D4,Plus(CN1,CSqrt5))),
    // Sin(Pi/8)=Sqrt(2-Sqrt(2))/2
    ISet(Sin(Times(QQ(1L,8L),Pi)),
      Times(C1D2,Sqrt(Plus(C2,Negate(CSqrt2))))),
    // Sin(Pi/6)=1/2
    ISet(Sin(Times(QQ(1L,6L),Pi)),
      C1D2),
    // Sin(Pi/5)=Sqrt(1/2*(5-Sqrt(5)))/2
    ISet(Sin(Times(QQ(1L,5L),Pi)),
      Times(C1D2,Sqrt(C1D2),Sqrt(Plus(C5,Negate(CSqrt5))))),
    // Sin(Pi/4)=Sqrt(2)/2
    ISet(Sin(Times(C1D4,Pi)),
      C1DSqrt2),
    // Sin(3/10*Pi)=1/4*(Sqrt(5)+1)
    ISet(Sin(Times(QQ(3L,10L),Pi)),
      Times(C1D4,Plus(C1,CSqrt5))),
    // Sin(Pi/3)=Sqrt(3)/2
    ISet(Sin(Times(C1D3,Pi)),
      Times(C1D2,CSqrt3)),
    // Sin(3/8*Pi)=Sqrt(2+Sqrt(2))/2
    ISet(Sin(Times(QQ(3L,8L),Pi)),
      Times(C1D2,Sqrt(Plus(C2,CSqrt2)))),
    // Sin(2/5*Pi)=Sqrt(1/2*(5+Sqrt(5)))/2
    ISet(Sin(Times(QQ(2L,5L),Pi)),
      Times(C1D2,Sqrt(C1D2),Sqrt(Plus(C5,CSqrt5)))),
    // Sin(5/12*Pi)=(1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(5L,12L),Pi)),
      Times(C1D2,C1DSqrt2,Plus(C1,CSqrt3))),
    // Sin(Pi/2)=1
    ISet(Sin(Times(C1D2,Pi)),
      C1),
    // Sin(7/12*Pi)=(1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(7L,12L),Pi)),
      Times(C1D2,C1DSqrt2,Plus(C1,CSqrt3))),
    // Sin(3/5*Pi)=Sqrt(1/2*(5+Sqrt(5)))/2
    ISet(Sin(Times(QQ(3L,5L),Pi)),
      Times(C1D2,Sqrt(C1D2),Sqrt(Plus(C5,CSqrt5)))),
    // Sin(5/8*Pi)=Sqrt(2+Sqrt(2))/2
    ISet(Sin(Times(QQ(5L,8L),Pi)),
      Times(C1D2,Sqrt(Plus(C2,CSqrt2)))),
    // Sin(2/3*Pi)=Sqrt(3)/2
    ISet(Sin(Times(QQ(2L,3L),Pi)),
      Times(C1D2,CSqrt3)),
    // Sin(7/10*Pi)=1/4*(Sqrt(5)+1)
    ISet(Sin(Times(QQ(7L,10L),Pi)),
      Times(C1D4,Plus(C1,CSqrt5))),
    // Sin(3/4*Pi)=Sqrt(2)/2
    ISet(Sin(Times(QQ(3L,4L),Pi)),
      C1DSqrt2),
    // Sin(4/5*Pi)=Sqrt(1/2*(5-Sqrt(5)))/2
    ISet(Sin(Times(QQ(4L,5L),Pi)),
      Times(C1D2,Sqrt(C1D2),Sqrt(Plus(C5,Negate(CSqrt5))))),
    // Sin(5/6*Pi)=1/2
    ISet(Sin(Times(QQ(5L,6L),Pi)),
      C1D2),
    // Sin(7/8*Pi)=Sqrt(2-Sqrt(2))/2
    ISet(Sin(Times(QQ(7L,8L),Pi)),
      Times(C1D2,Sqrt(Plus(C2,Negate(CSqrt2))))),
    // Sin(9/10*Pi)=1/4*(-1+Sqrt(5))
    ISet(Sin(Times(QQ(9L,10L),Pi)),
      Times(C1D4,Plus(CN1,CSqrt5))),
    // Sin(11/12*Pi)=(-1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(11L,12L),Pi)),
      Times(C1D2,C1DSqrt2,Plus(CN1,CSqrt3))),
    // Sin(Pi)=0
    ISet(Sin(Pi),
      C0),
    // Sin(13/12*Pi)=-(-1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(13L,12L),Pi)),
      Times(CN1D2,C1DSqrt2,Plus(CN1,CSqrt3))),
    // Sin(11/10*Pi)=-(-1+Sqrt(5))/4
    ISet(Sin(Times(QQ(11L,10L),Pi)),
      Times(CN1D4,Plus(CN1,CSqrt5))),
    // Sin(9/8*Pi)=-Sqrt(2-Sqrt(2))/2
    ISet(Sin(Times(QQ(9L,8L),Pi)),
      Times(CN1D2,Sqrt(Plus(C2,Negate(CSqrt2))))),
    // Sin(7/6*Pi)=-1/2
    ISet(Sin(Times(QQ(7L,6L),Pi)),
      CN1D2),
    // Sin(6/5*Pi)=-Sqrt(1/2*(5-Sqrt(5)))/2
    ISet(Sin(Times(QQ(6L,5L),Pi)),
      Times(CN1D2,Sqrt(C1D2),Sqrt(Plus(C5,Negate(CSqrt5))))),
    // Sin(5/4*Pi)=(-1)*1/2*Sqrt(2)
    ISet(Sin(Times(QQ(5L,4L),Pi)),
      Negate(C1DSqrt2)),
    // Sin(13/10*Pi)=-(Sqrt(5)+1)/4
    ISet(Sin(Times(QQ(13L,10L),Pi)),
      Times(CN1D4,Plus(C1,CSqrt5))),
    // Sin(4/3*Pi)=(-1)*1/2*Sqrt(3)
    ISet(Sin(Times(QQ(4L,3L),Pi)),
      Times(CN1D2,CSqrt3)),
    // Sin(11/8*Pi)=-Sqrt(2+Sqrt(2))/2
    ISet(Sin(Times(QQ(11L,8L),Pi)),
      Times(CN1D2,Sqrt(Plus(C2,CSqrt2)))),
    // Sin(7/5*Pi)=-Sqrt(1/2*(5+Sqrt(5)))/2
    ISet(Sin(Times(QQ(7L,5L),Pi)),
      Times(CN1D2,Sqrt(C1D2),Sqrt(Plus(C5,CSqrt5)))),
    // Sin(17/12*Pi)=-(1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(17L,12L),Pi)),
      Times(CN1D2,C1DSqrt2,Plus(C1,CSqrt3))),
    // Sin(3/2*Pi)=-1
    ISet(Sin(Times(QQ(3L,2L),Pi)),
      CN1),
    // Sin(19/12*Pi)=-(1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(19L,12L),Pi)),
      Times(CN1D2,C1DSqrt2,Plus(C1,CSqrt3))),
    // Sin(8/5*Pi)=-Sqrt(1/2*(5+Sqrt(5)))/2
    ISet(Sin(Times(QQ(8L,5L),Pi)),
      Times(CN1D2,Sqrt(C1D2),Sqrt(Plus(C5,CSqrt5)))),
    // Sin(13/8*Pi)=-Sqrt(2+Sqrt(2))/2
    ISet(Sin(Times(QQ(13L,8L),Pi)),
      Times(CN1D2,Sqrt(Plus(C2,CSqrt2)))),
    // Sin(5/3*Pi)=(-1)*1/2*Sqrt(3)
    ISet(Sin(Times(QQ(5L,3L),Pi)),
      Times(CN1D2,CSqrt3)),
    // Sin(17/10*Pi)=-(Sqrt(5)+1)/4
    ISet(Sin(Times(QQ(17L,10L),Pi)),
      Times(CN1D4,Plus(C1,CSqrt5))),
    // Sin(7/4*Pi)=(-1)*1/2*Sqrt(2)
    ISet(Sin(Times(QQ(7L,4L),Pi)),
      Negate(C1DSqrt2)),
    // Sin(9/5*Pi)=-Sqrt(1/2*(5-Sqrt(5)))/2
    ISet(Sin(Times(QQ(9L,5L),Pi)),
      Times(CN1D2,Sqrt(C1D2),Sqrt(Plus(C5,Negate(CSqrt5))))),
    // Sin(11/6*Pi)=-1/2
    ISet(Sin(Times(QQ(11L,6L),Pi)),
      CN1D2),
    // Sin(15/8*Pi)=-Sqrt(2-Sqrt(2))/2
    ISet(Sin(Times(QQ(15L,8L),Pi)),
      Times(CN1D2,Sqrt(Plus(C2,Negate(CSqrt2))))),
    // Sin(19/10*Pi)=-(-1+Sqrt(5))/4
    ISet(Sin(Times(QQ(19L,10L),Pi)),
      Times(CN1D4,Plus(CN1,CSqrt5))),
    // Sin(23/12*Pi)=-(-1+Sqrt(3))/(2*Sqrt(2))
    ISet(Sin(Times(QQ(23L,12L),Pi)),
      Times(CN1D2,C1DSqrt2,Plus(CN1,CSqrt3))),
    // Sin(2*Pi)=0
    ISet(Sin(Times(C2,Pi)),
      C0),
    // Sin(I)=I*Sinh(1)
    ISet(Sin(CI),
      Times(CI,Sinh(C1))),
    // Sin(ArcSin(x_)):=x
    ISetDelayed(Sin(ArcSin(x_)),
      x),
    // Sin(ArcCos(x_)):=Sqrt(1-x^2)
    ISetDelayed(Sin(ArcCos(x_)),
      Sqrt(Plus(C1,Negate(Sqr(x))))),
    // Sin(ArcTan(x_)):=x/Sqrt(1+x^2)
    ISetDelayed(Sin(ArcTan(x_)),
      Times(x,Power(Plus(C1,Sqr(x)),CN1D2))),
    // Sin(x_NumberQ*Pi):=If(x<1,Sin((1-x)*Pi),If(x<2,-Sin((2-x)*Pi),Sin((x-2*Quotient(IntegerPart(x),2))*Pi)))/;x>=1/2
    ISetDelayed(Sin(Times(Pi,$p(x,NumberQ))),
      Condition(If(Less(x,C1),Sin(Times(Plus(C1,Negate(x)),Pi)),If(Less(x,C2),Negate(Sin(Times(Plus(C2,Negate(x)),Pi))),Sin(Times(Plus(x,Times(CN2,Quotient(IntegerPart(x),C2))),Pi)))),GreaterEqual(x,C1D2))),
    // Sin(I*Infinity)=I*Infinity
    ISet(Sin(DirectedInfinity(CI)),
      DirectedInfinity(CI)),
    // Sin(-I*Infinity)=-I*Infinity
    ISet(Sin(DirectedInfinity(CNI)),
      DirectedInfinity(CNI)),
    // Sin(ComplexInfinity)=Indeterminate
    ISet(Sin(CComplexInfinity),
      Indeterminate),
    // Sin(Infinity)=Interval({-1,1})
    ISet(Sin(oo),
      Interval(List(CN1,C1))),
    // Sin(-Infinity)=Interval({-1,1})
    ISet(Sin(Noo),
      Interval(List(CN1,C1)))
  );
}
