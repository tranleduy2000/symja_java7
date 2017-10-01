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
public class IntRules127 { 
  public static IAST RULES = List( 
ISetDelayed(Int(Times(v_,Plus(a_DEFAULT,Times(b_DEFAULT,ArcSech(u_)))),x_Symbol),
    Condition(Module(List(Set(w,Block(List(Set($s("§showsteps"),False),Set($s("§stepcounter"),Null)),Int(v,x)))),Condition(Plus(Dist(Plus(a,Times(b,ArcSech(u))),w,x),Times(b,Sqrt(Plus(C1,Negate(Sqr(u)))),Power(Times(u,Sqrt(Plus(CN1,Power(u,-1))),Sqrt(Plus(C1,Power(u,-1)))),-1),Int(SimplifyIntegrand(Times(w,D(u,x),Power(Times(u,Sqrt(Plus(C1,Negate(Sqr(u))))),-1)),x),x))),InverseFunctionFreeQ(w,x))),And(And(FreeQ(List(a,b),x),InverseFunctionFreeQ(u,x)),Not(MatchQ(v,Condition(Power(Plus(c_DEFAULT,Times(d_DEFAULT,x)),m_DEFAULT),FreeQ(List(c,d,m),x))))))),
ISetDelayed(Int(Times(v_,Plus(a_DEFAULT,Times(b_DEFAULT,ArcCsch(u_)))),x_Symbol),
    Condition(Module(List(Set(w,Block(List(Set($s("§showsteps"),False),Set($s("§stepcounter"),Null)),Int(v,x)))),Condition(Plus(Dist(Plus(a,Times(b,ArcCsch(u))),w,x),Times(CN1,b,u,Power(Negate(Sqr(u)),CN1D2),Int(SimplifyIntegrand(Times(w,D(u,x),Power(Times(u,Sqrt(Plus(CN1,Negate(Sqr(u))))),-1)),x),x))),InverseFunctionFreeQ(w,x))),And(And(FreeQ(List(a,b),x),InverseFunctionFreeQ(u,x)),Not(MatchQ(v,Condition(Power(Plus(c_DEFAULT,Times(d_DEFAULT,x)),m_DEFAULT),FreeQ(List(c,d,m),x)))))))
  );
}
