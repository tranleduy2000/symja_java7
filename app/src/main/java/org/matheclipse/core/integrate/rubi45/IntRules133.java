package org.matheclipse.core.integrate.rubi45;


import static org.matheclipse.core.expression.F.*;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctionCtors.*;
import static org.matheclipse.core.integrate.rubi45.UtilityFunctions.*;
import org.matheclipse.core.interfaces.IAST;

/** 
 * IndefiniteIntegrationRules from the <a href="http://www.apmaths.uwo.ca/~arich/">Rubi -
 * rule-based integrator</a>.
 *  
 */
public class IntRules133 { 
  public static IAST RULES = List( 
ISetDelayed(Int(Gamma(n_,Plus(a_DEFAULT,Times(b_DEFAULT,x_))),x_Symbol),
    Condition(Plus(Times(Plus(a,Times(b,x)),Gamma(n,Plus(a,Times(b,x))),Power(b,-1)),Times(CN1,Gamma(Plus(n,C1),Plus(a,Times(b,x))),Power(b,-1))),FreeQ(List(a,b),x))),
ISetDelayed(Int(Times(Power(x_,-1),Gamma(n_,Times(b_,x_))),x_Symbol),
    Condition(Plus(Times(Gamma(n),Log(x)),Times(CN1,Power(Times(b,x),n),Power(n,-2),HypergeometricPFQ(List(n,n),List(Plus(C1,n),Plus(C1,n)),Times(CN1,b,x)))),And(FreeQ(List(b,n),x),Not(And(IntegerQ(n),LessEqual(n,C0)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Gamma(n_,Times(b_,x_))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Gamma(n,Times(b,x)),Power(Plus(m,C1),-1)),Times(CN1,Power(x,m),Gamma(Plus(m,n,C1),Times(b,x)),Power(Times(b,Plus(m,C1),Power(Times(b,x),m)),-1))),And(FreeQ(List(b,m,n),x),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Gamma(n_,Plus(a_,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Block(List(Set($s("§$usegamma"),True)),Plus(Times(Power(x,Plus(m,C1)),Gamma(n,Plus(a,Times(b,x))),Power(Plus(m,C1),-1)),Times(b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(a,Times(b,x)),Plus(n,Negate(C1))),Power(Power(E,Plus(a,Times(b,x))),-1)),x)))),And(And(FreeQ(List(a,b,m,n),x),Or(Or(PositiveIntegerQ(m),PositiveIntegerQ(n)),IntegersQ(m,n))),NonzeroQ(Plus(m,C1))))),
ISetDelayed(Int(LogGamma(Plus(a_DEFAULT,Times(b_DEFAULT,x_))),x_Symbol),
    Condition(Times(PolyGamma(CN2,Plus(a,Times(b,x))),Power(b,-1)),FreeQ(List(a,b),x))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),LogGamma(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Plus(Times(Power(x,m),PolyGamma(CN2,Plus(a,Times(b,x))),Power(b,-1)),Times(CN1,m,Power(b,-1),Int(Times(Power(x,Plus(m,Negate(C1))),PolyGamma(CN2,Plus(a,Times(b,x)))),x))),And(And(FreeQ(List(a,b),x),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(PolyGamma(n_,Plus(a_DEFAULT,Times(b_DEFAULT,x_))),x_Symbol),
    Condition(Times(PolyGamma(Plus(n,Negate(C1)),Plus(a,Times(b,x))),Power(b,-1)),FreeQ(List(a,b,n),x))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),PolyGamma(n_,Plus(a_DEFAULT,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Plus(Times(Power(x,m),PolyGamma(Plus(n,Negate(C1)),Plus(a,Times(b,x))),Power(b,-1)),Times(CN1,m,Power(b,-1),Int(Times(Power(x,Plus(m,Negate(C1))),PolyGamma(Plus(n,Negate(C1)),Plus(a,Times(b,x)))),x))),And(And(FreeQ(List(a,b,n),x),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),PolyGamma(n_,Plus(a_DEFAULT,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),PolyGamma(n,Plus(a,Times(b,x))),Power(Plus(m,C1),-1)),Times(CN1,b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),PolyGamma(Plus(n,C1),Plus(a,Times(b,x)))),x))),And(And(FreeQ(List(a,b,n),x),RationalQ(m)),Less(m,CN1)))),
ISetDelayed(Int(Times(Power(Gamma(Plus(a_DEFAULT,Times(b_DEFAULT,x_))),n_DEFAULT),PolyGamma(C0,Plus(a_DEFAULT,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Times(Power(Gamma(Plus(a,Times(b,x))),n),Power(Times(b,n),-1)),FreeQ(List(a,b,n),x))),
ISetDelayed(Int(Times(Power(Factorial(Plus(a_DEFAULT,Times(b_DEFAULT,x_))),n_DEFAULT),PolyGamma(C0,Plus(c_DEFAULT,Times(b_DEFAULT,x_)))),x_Symbol),
    Condition(Times(Power(Factorial(Plus(a,Times(b,x))),n),Power(Times(b,n),-1)),And(FreeQ(List(a,b,c,n),x),ZeroQ(Plus(a,Negate(c),C1)))))
  );
}
