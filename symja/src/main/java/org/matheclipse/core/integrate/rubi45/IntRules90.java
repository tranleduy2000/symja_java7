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
public class IntRules90 { 
  public static IAST RULES = List( 
ISetDelayed(Int(Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Module(List(Set(g,Numerator(Power(n,-1)))),Times(g,Subst(Int(Times(Power(x,Plus(g,Negate(C1))),Power(Sin(Plus(a,Times(b,Power(x,Times(n,g))))),p)),x),x,Power(x,Power(g,-1))))),And(And(FreeQ(List(a,b,p),x),RationalQ(n)),Or(Less(n,C0),FractionQ(n))))),
ISetDelayed(Int(Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Module(List(Set(g,Numerator(Power(n,-1)))),Times(g,Subst(Int(Times(Power(x,Plus(g,Negate(C1))),Power(Cos(Plus(a,Times(b,Power(x,Times(n,g))))),p)),x),x,Power(x,Power(g,-1))))),And(And(FreeQ(List(a,b,p),x),RationalQ(n)),Or(Less(n,C0),FractionQ(n))))),
ISetDelayed(Int(Sin(Times(b_DEFAULT,Sqr(x_))),x_Symbol),
    Condition(Times(Sqrt(Times(C1D2,Pi)),Power(Rt(b,C2),-1),FresnelS(Times(Sqrt(Times(C2,Power(Pi,-1))),Rt(b,C2),x))),FreeQ(b,x))),
ISetDelayed(Int(Cos(Times(b_DEFAULT,Sqr(x_))),x_Symbol),
    Condition(Times(Sqrt(Times(C1D2,Pi)),Power(Rt(b,C2),-1),FresnelC(Times(Sqrt(Times(C2,Power(Pi,-1))),Rt(b,C2),x))),FreeQ(b,x))),
ISetDelayed(Int(Sin(Times(c_DEFAULT,Sqr(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))),x_Symbol),
    Condition(Times(Sqrt(Times(C1D2,Pi)),Power(Times(b,Rt(c,C2)),-1),FresnelS(Times(Sqrt(Times(C2,Power(Pi,-1))),Rt(c,C2),Plus(a,Times(b,x))))),FreeQ(List(a,b,c),x))),
ISetDelayed(Int(Cos(Times(c_DEFAULT,Sqr(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))),x_Symbol),
    Condition(Times(Sqrt(Times(C1D2,Pi)),Power(Times(b,Rt(c,C2)),-1),FresnelC(Times(Sqrt(Times(C2,Power(Pi,-1))),Rt(c,C2),Plus(a,Times(b,x))))),FreeQ(List(a,b,c),x))),
ISetDelayed(Int(Sin(Plus(a_,Times(b_DEFAULT,Sqr(x_)))),x_Symbol),
    Condition(Plus(Times(Sin(a),Int(Cos(Times(b,Sqr(x))),x)),Times(Cos(a),Int(Sin(Times(b,Sqr(x))),x))),FreeQ(List(a,b),x))),
ISetDelayed(Int(Cos(Plus(a_,Times(b_DEFAULT,Sqr(x_)))),x_Symbol),
    Condition(Plus(Times(Cos(a),Int(Cos(Times(b,Sqr(x))),x)),Times(CN1,Sin(a),Int(Sin(Times(b,Sqr(x))),x))),FreeQ(List(a,b),x))),
ISetDelayed(Int(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),x_Symbol),
    Condition(Plus(Times(C1D2,CI,Int(Power(E,Plus(Times(CN1,a,CI),Times(CN1,b,CI,Power(x,n)))),x)),Times(CN1,C1D2,CI,Int(Power(E,Plus(Times(a,CI),Times(b,CI,Power(x,n)))),x))),And(FreeQ(List(a,b,n),x),NonzeroQ(Plus(n,Negate(C2)))))),
ISetDelayed(Int(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),x_Symbol),
    Condition(Plus(Times(C1D2,Int(Power(E,Plus(Times(CN1,a,CI),Times(CN1,b,CI,Power(x,n)))),x)),Times(C1D2,Int(Power(E,Plus(Times(a,CI),Times(b,CI,Power(x,n)))),x))),And(FreeQ(List(a,b,n),x),NonzeroQ(Plus(n,Negate(C2)))))),
ISetDelayed(Int(Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_),x_Symbol),
    Condition(Int(ExpandTrigReduce(Power(Sin(Plus(a,Times(b,Power(x,n)))),p),x),x),And(And(FreeQ(List(a,b,n),x),IntegerQ(p)),Greater(p,C1)))),
ISetDelayed(Int(Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_),x_Symbol),
    Condition(Int(ExpandTrigReduce(Power(Cos(Plus(a,Times(b,Power(x,n)))),p),x),x),And(And(FreeQ(List(a,b,n),x),IntegerQ(p)),Greater(p,C1)))),
ISetDelayed(Int(Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Integrate(Power(Sin(Plus(a,Times(b,Power(x,n)))),p),x),FreeQ(List(a,b,n,p),x))),
ISetDelayed(Int(Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Integrate(Power(Cos(Plus(a,Times(b,Power(x,n)))),p),x),FreeQ(List(a,b,n,p),x))),
ISetDelayed(Int(Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(u_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Times(Power(Coefficient(u,x,C1),-1),Subst(Int(Power(Sin(Plus(a,Times(b,Power(x,n)))),p),x),x,u)),And(And(FreeQ(List(a,b,n,p),x),LinearQ(u,x)),NonzeroQ(Plus(u,Negate(x)))))),
ISetDelayed(Int(Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(u_,n_)))),p_DEFAULT),x_Symbol),
    Condition(Times(Power(Coefficient(u,x,C1),-1),Subst(Int(Power(Cos(Plus(a,Times(b,Power(x,n)))),p),x),x,u)),And(And(FreeQ(List(a,b,n,p),x),LinearQ(u,x)),NonzeroQ(Plus(u,Negate(x)))))),
ISetDelayed(Int(Times(Power(x_,-1),Sin(Times(b_DEFAULT,Power(x_,n_)))),x_Symbol),
    Condition(Times(SinIntegral(Times(b,Power(x,n))),Power(n,-1)),FreeQ(List(b,n),x))),
ISetDelayed(Int(Times(Power(x_,-1),Cos(Times(b_DEFAULT,Power(x_,n_)))),x_Symbol),
    Condition(Times(CosIntegral(Times(b,Power(x,n))),Power(n,-1)),FreeQ(List(b,n),x))),
ISetDelayed(Int(Times(Power(x_,-1),Sin(Plus(a_,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Sin(a),Int(Times(Cos(Times(b,Power(x,n))),Power(x,-1)),x)),Times(Cos(a),Int(Times(Sin(Times(b,Power(x,n))),Power(x,-1)),x))),FreeQ(List(a,b,n),x))),
ISetDelayed(Int(Times(Power(x_,-1),Cos(Plus(a_,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Cos(a),Int(Times(Cos(Times(b,Power(x,n))),Power(x,-1)),x)),Times(CN1,Sin(a),Int(Times(Sin(Times(b,Power(x,n))),Power(x,-1)),x))),FreeQ(List(a,b,n),x))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Times(CN1,Cos(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(Plus(n,Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Times(Sin(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(Plus(n,Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Times(C2,Power(n,-1),Subst(Int(Sin(Plus(a,Times(b,Sqr(x)))),x),x,Power(x,Times(C1D2,n)))),And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(Plus(Times(C1D2,n),Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Times(C2,Power(n,-1),Subst(Int(Cos(Plus(a,Times(b,Sqr(x)))),x),x,Power(x,Times(C1D2,n)))),And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Negate(Plus(Times(C1D2,n),Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,Negate(n),C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),Times(Plus(m,Negate(n),C1),Power(Times(b,n),-1),Int(Times(Power(x,Plus(m,Negate(n))),Cos(Plus(a,Times(b,Power(x,n))))),x))),And(And(FreeQ(List(a,b),x),RationalQ(m,n)),Or(Less(Less(C0,n),Plus(m,C1)),Less(Less(Plus(m,C1),n),C0))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Module(List(Set($s("mn"),Simplify(Plus(m,Negate(n))))),Plus(Times(CN1,Power(x,Plus($s("mn"),C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),Times(Plus($s("mn"),C1),Power(Times(b,n),-1),Int(Times(Power(x,$s("mn")),Cos(Plus(a,Times(b,Power(x,n))))),x)))),And(And(FreeQ(List(a,b,m,n),x),NonzeroQ(Plus(m,Negate(n),C1))),PositiveIntegerQ(Simplify(Times(Plus(m,C1),Power(n,-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),Times(CN1,Plus(m,Negate(n),C1),Power(Times(b,n),-1),Int(Times(Power(x,Plus(m,Negate(n))),Sin(Plus(a,Times(b,Power(x,n))))),x))),And(And(FreeQ(List(a,b),x),RationalQ(m,n)),Or(Less(Less(C0,n),Plus(m,C1)),Less(Less(Plus(m,C1),n),C0))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Module(List(Set($s("mn"),Simplify(Plus(m,Negate(n))))),Plus(Times(Power(x,Plus($s("mn"),C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Times(b,n),-1)),Times(CN1,Plus($s("mn"),C1),Power(Times(b,n),-1),Int(Times(Power(x,$s("mn")),Sin(Plus(a,Times(b,Power(x,n))))),x)))),And(And(FreeQ(List(a,b,m,n),x),NonzeroQ(Plus(m,Negate(n),C1))),PositiveIntegerQ(Simplify(Times(Plus(m,C1),Power(n,-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Plus(m,C1),-1)),Times(CN1,b,n,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,n)),Cos(Plus(a,Times(b,Power(x,n))))),x))),And(And(FreeQ(List(a,b),x),RationalQ(m,n)),Or(And(Greater(n,C0),Less(m,CN1)),And(Less(n,C0),Greater(m,CN1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Plus(m,C1),-1)),Times(CN1,b,n,Power(Plus(m,C1),-1),Int(Times(Power(x,Simplify(Plus(m,n))),Cos(Plus(a,Times(b,Power(x,n))))),x))),And(FreeQ(List(a,b,m,n),x),NegativeIntegerQ(Simplify(Times(Plus(m,C1),Power(n,-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Plus(m,C1),-1)),Times(b,n,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,n)),Sin(Plus(a,Times(b,Power(x,n))))),x))),And(And(FreeQ(List(a,b),x),RationalQ(m,n)),Or(And(Greater(n,C0),Less(m,CN1)),And(Less(n,C0),Greater(m,CN1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Plus(m,C1),-1)),Times(b,n,Power(Plus(m,C1),-1),Int(Times(Power(x,Simplify(Plus(m,n))),Sin(Plus(a,Times(b,Power(x,n))))),x))),And(FreeQ(List(a,b,m,n),x),NegativeIntegerQ(Simplify(Times(Plus(m,C1),Power(n,-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(C1D2,CI,Int(Times(Power(x,m),Power(E,Plus(Times(CN1,a,CI),Times(CN1,b,CI,Power(x,n))))),x)),Times(CN1,C1D2,CI,Int(Times(Power(x,m),Power(E,Plus(Times(a,CI),Times(b,CI,Power(x,n))))),x))),FreeQ(List(a,b,m,n),x))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_))))),x_Symbol),
    Condition(Plus(Times(C1D2,Int(Times(Power(x,m),Power(E,Plus(Times(CN1,a,CI),Times(CN1,b,CI,Power(x,n))))),x)),Times(C1D2,Int(Times(Power(x,m),Power(E,Plus(Times(a,CI),Times(b,CI,Power(x,n))))),x))),FreeQ(List(a,b,m,n),x))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(Sin(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Plus(n,Negate(C1)),Power(x,Plus(n,Negate(C1)))),-1)),Times(b,n,p,Power(Plus(n,Negate(C1)),-1),Int(Times(Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Cos(Plus(a,Times(b,Power(x,n))))),x))),And(And(And(And(FreeQ(List(a,b),x),IntegersQ(n,p)),ZeroQ(Plus(m,n))),Greater(p,C1)),NonzeroQ(Plus(n,Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(Cos(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Plus(n,Negate(C1)),Power(x,Plus(n,Negate(C1)))),-1)),Times(CN1,b,n,p,Power(Plus(n,Negate(C1)),-1),Int(Times(Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Sin(Plus(a,Times(b,Power(x,n))))),x))),And(And(And(And(FreeQ(List(a,b),x),IntegersQ(n,p)),ZeroQ(Plus(m,n))),Greater(p,C1)),NonzeroQ(Plus(n,Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(n,Power(Sin(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1)),Times(CN1,Power(x,n),Cos(Plus(a,Times(b,Power(x,n)))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,p),-1)),Times(Plus(p,Negate(C1)),Power(p,-1),Int(Times(Power(x,m),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Times(CN1,C2,n),C1))),RationalQ(p)),Greater(p,C1)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(n,Power(Cos(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1)),Times(Power(x,n),Sin(Plus(a,Times(b,Power(x,n)))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,p),-1)),Times(Plus(p,Negate(C1)),Power(p,-1),Int(Times(Power(x,m),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Times(CN1,C2,n),C1))),RationalQ(p)),Greater(p,C1)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Plus(m,Negate(n),C1),Power(x,Plus(m,Times(CN1,C2,n),C1)),Power(Sin(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1)),Times(CN1,Power(x,Plus(m,Negate(n),C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,p),-1)),Times(Plus(p,Negate(C1)),Power(p,-1),Int(Times(Power(x,m),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x)),Times(CN1,Plus(m,Negate(n),C1),Plus(m,Times(CN1,C2,n),C1),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1),Int(Times(Power(x,Plus(m,Times(CN1,C2,n))),Power(Sin(Plus(a,Times(b,Power(x,n)))),p)),x))),And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Greater(p,C1)),Less(Less(C0,Times(C2,n)),Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Plus(m,Negate(n),C1),Power(x,Plus(m,Times(CN1,C2,n),C1)),Power(Cos(Plus(a,Times(b,Power(x,n)))),p),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1)),Times(Power(x,Plus(m,Negate(n),C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,p),-1)),Times(Plus(p,Negate(C1)),Power(p,-1),Int(Times(Power(x,m),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x)),Times(CN1,Plus(m,Negate(n),C1),Plus(m,Times(CN1,C2,n),C1),Power(Times(Sqr(b),Sqr(n),Sqr(p)),-1),Int(Times(Power(x,Plus(m,Times(CN1,C2,n))),Power(Cos(Plus(a,Times(b,Power(x,n)))),p)),x))),And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Greater(p,C1)),Less(Less(C0,Times(C2,n)),Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Power(Sin(Plus(a,Times(b,Power(x,n)))),p),Power(Plus(m,C1),-1)),Times(CN1,b,n,p,Power(x,Plus(m,n,C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1)),Times(CN1,Sqr(b),Sqr(n),Sqr(p),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1),Int(Times(Power(x,Plus(m,Times(C2,n))),Power(Sin(Plus(a,Times(b,Power(x,n)))),p)),x)),Times(Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1),Int(Times(Power(x,Plus(m,Times(C2,n))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Greater(p,C1)),Less(Less(C0,Times(C2,n)),Plus(C1,Negate(m)))),NonzeroQ(Plus(m,n,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),Power(Cos(Plus(a,Times(b,Power(x,n)))),p),Power(Plus(m,C1),-1)),Times(b,n,p,Power(x,Plus(m,n,C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1)),Times(CN1,Sqr(b),Sqr(n),Sqr(p),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1),Int(Times(Power(x,Plus(m,Times(C2,n))),Power(Cos(Plus(a,Times(b,Power(x,n)))),p)),x)),Times(Sqr(b),Sqr(n),p,Plus(p,Negate(C1)),Power(Times(Plus(m,C1),Plus(m,n,C1)),-1),Int(Times(Power(x,Plus(m,Times(C2,n))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Greater(p,C1)),Less(Less(C0,Times(C2,n)),Plus(C1,Negate(m)))),NonzeroQ(Plus(m,n,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Times(Power(Plus(m,C1),-1),Subst(Int(Power(Sin(Plus(a,Times(b,Power(x,Simplify(Times(n,Power(Plus(m,C1),-1))))))),p),x),x,Power(x,Plus(m,C1)))),And(And(FreeQ(List(a,b,m,n,p),x),NonzeroQ(Plus(m,C1))),PositiveIntegerQ(Simplify(Times(n,Power(Plus(m,C1),-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Times(Power(Plus(m,C1),-1),Subst(Int(Power(Cos(Plus(a,Times(b,Power(x,Simplify(Times(n,Power(Plus(m,C1),-1))))))),p),x),x,Power(x,Plus(m,C1)))),And(And(FreeQ(List(a,b,m,n,p),x),NonzeroQ(Plus(m,C1))),PositiveIntegerQ(Simplify(Times(n,Power(Plus(m,C1),-1))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Int(ExpandTrigReduce(Power(x,m),Power(Sin(Plus(a,Times(b,Power(x,n)))),p),x),x),And(And(And(And(FreeQ(List(a,b,m,n),x),IntegerQ(p)),Greater(p,C1)),Not(FractionQ(m))),Not(FractionQ(n))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Int(ExpandTrigReduce(Power(x,m),Power(Cos(Plus(a,Times(b,Power(x,n)))),p),x),x),And(And(And(And(FreeQ(List(a,b,m,n),x),IntegerQ(p)),Greater(p,C1)),Not(FractionQ(m))),Not(FractionQ(n))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,n),Cos(Plus(a,Times(b,Power(x,n)))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,n,Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(p,C2),Power(Plus(p,C1),-1),Int(Times(Power(x,m),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x))),And(And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Times(CN1,C2,n),C1))),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,n),Sin(Plus(a,Times(b,Power(x,n)))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,n,Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(p,C2),Power(Plus(p,C1),-1),Int(Times(Power(x,m),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x))),And(And(And(And(FreeQ(List(a,b,m,n),x),ZeroQ(Plus(m,Times(CN1,C2,n),C1))),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Cos(Plus(a,Times(b,Power(x,n)))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,Plus(m,Negate(n),C1),Power(x,Plus(m,Times(CN1,C2,n),C1)),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(p,C2),Power(Plus(p,C1),-1),Int(Times(Power(x,m),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x)),Times(Plus(m,Negate(n),C1),Plus(m,Times(CN1,C2,n),C1),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Times(Power(x,Plus(m,Times(CN1,C2,n))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x))),And(And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),Less(Less(C0,Times(C2,n)),Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,Negate(n),C1)),Sin(Plus(a,Times(b,Power(x,n)))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,Plus(m,Negate(n),C1),Power(x,Plus(m,Times(CN1,C2,n),C1)),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C2)),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1)),Times(Plus(p,C2),Power(Plus(p,C1),-1),Int(Times(Power(x,m),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x)),Times(Plus(m,Negate(n),C1),Plus(m,Times(CN1,C2,n),C1),Power(Times(Sqr(b),Sqr(n),Plus(p,C1),Plus(p,C2)),-1),Int(Times(Power(x,Plus(m,Times(CN1,C2,n))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C2))),x))),And(And(And(And(And(FreeQ(List(a,b),x),IntegersQ(m,n)),RationalQ(p)),Less(p,CN1)),Unequal(p,CN2)),Less(Less(C0,Times(C2,n)),Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(u_,n_)))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(Power(Coefficient(u,x,C1),Plus(m,C1)),-1),Subst(Int(Times(Power(Plus(x,Negate(Coefficient(u,x,C0))),m),Power(Sin(Plus(a,Times(b,Power(x,n)))),p)),x),x,u)),And(And(And(FreeQ(List(a,b,n,p),x),LinearQ(u,x)),IntegerQ(m)),NonzeroQ(Plus(u,Negate(x)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(u_,n_)))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(Power(Coefficient(u,x,C1),Plus(m,C1)),-1),Subst(Int(Times(Power(Plus(x,Negate(Coefficient(u,x,C0))),m),Power(Cos(Plus(a,Times(b,Power(x,n)))),p)),x),x,u)),And(And(And(FreeQ(List(a,b,n,p),x),LinearQ(u,x)),IntegerQ(m)),NonzeroQ(Plus(u,Negate(x)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT)))),p_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT))))),x_Symbol),
    Condition(Times(Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),And(And(FreeQ(List(a,b,m,n,p),x),ZeroQ(Plus(m,Negate(n),C1))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT)))),p_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT))))),x_Symbol),
    Condition(Times(CN1,Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),And(And(FreeQ(List(a,b,m,n,p),x),ZeroQ(Plus(m,Negate(n),C1))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT)))),p_DEFAULT),Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(CN1,Plus(m,Negate(n),C1),Power(Times(b,n,Plus(p,C1)),-1),Int(Times(Power(x,Plus(m,Negate(n))),Power(Sin(Plus(a,Times(b,Power(x,n)))),Plus(p,C1))),x))),And(And(And(FreeQ(List(a,b,p),x),RationalQ(m,n)),Less(Less(C0,n),Plus(m,C1))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cos(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT)))),p_DEFAULT),Sin(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_DEFAULT))))),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,Negate(n),C1)),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C1)),Power(Times(b,n,Plus(p,C1)),-1)),Times(Plus(m,Negate(n),C1),Power(Times(b,n,Plus(p,C1)),-1),Int(Times(Power(x,Plus(m,Negate(n))),Power(Cos(Plus(a,Times(b,Power(x,n)))),Plus(p,C1))),x))),And(And(And(FreeQ(List(a,b,p),x),RationalQ(m,n)),Less(Less(C0,n),Plus(m,C1))),NonzeroQ(Plus(p,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Tan(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(n,-1),Subst(Int(Power(Tan(Plus(a,Times(b,x))),p),x),x,Power(x,n))),And(FreeQ(List(a,b,m,n,p),x),ZeroQ(Plus(m,Negate(Plus(n,Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cot(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_DEFAULT)),x_Symbol),
    Condition(Times(Power(n,-1),Subst(Int(Power(Cot(Plus(a,Times(b,x))),p),x),x,Power(x,n))),And(FreeQ(List(a,b,m,n,p),x),ZeroQ(Plus(m,Negate(Plus(n,Negate(C1)))))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Tan(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,Negate(n),C1)),Power(Tan(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(CN1,Plus(m,Negate(n),C1),Power(Times(b,n,Plus(p,Negate(C1))),-1),Int(Times(Power(x,Plus(m,Negate(n))),Power(Tan(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1)))),x)),Negate(Int(Times(Power(x,m),Power(Tan(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(FreeQ(List(a,b),x),RationalQ(m,n,p)),Greater(p,C1)),Less(Less(C0,n),Plus(m,C1))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),Power(Cot(Plus(a_DEFAULT,Times(b_DEFAULT,Power(x_,n_)))),p_)),x_Symbol),
    Condition(Plus(Times(CN1,Power(x,Plus(m,Negate(n),C1)),Power(Cot(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1))),Power(Times(b,n,Plus(p,Negate(C1))),-1)),Times(Plus(m,Negate(n),C1),Power(Times(b,n,Plus(p,Negate(C1))),-1),Int(Times(Power(x,Plus(m,Negate(n))),Power(Cot(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C1)))),x)),Negate(Int(Times(Power(x,m),Power(Cot(Plus(a,Times(b,Power(x,n)))),Plus(p,Negate(C2)))),x))),And(And(And(FreeQ(List(a,b),x),RationalQ(m,n,p)),Greater(p,C1)),Less(Less(C0,n),Plus(m,C1)))))
  );
}