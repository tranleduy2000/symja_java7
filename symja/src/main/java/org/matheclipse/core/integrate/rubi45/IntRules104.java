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
public class IntRules104 { 
  public static IAST RULES = List( 
ISetDelayed(Int(ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcTanh(Plus(c,Times(d,Tanh(Plus(a,Times(b,x))))))),Times(b,Int(Times(x,Power(Plus(c,Negate(d),Times(c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcCoth(Plus(c,Times(d,Tanh(Plus(a,Times(b,x))))))),Times(b,Int(Times(x,Power(Plus(c,Negate(d),Times(c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcTanh(Plus(c,Times(d,Tanh(Plus(a,Times(b,x))))))),Times(b,Plus(C1,c,Negate(d)),Int(Times(x,Power(Plus(C1,c,Negate(d),Times(Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Int(Times(x,Power(Plus(C1,Negate(c),d,Times(Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcCoth(Plus(c,Times(d,Tanh(Plus(a,Times(b,x))))))),Times(b,Plus(C1,c,Negate(d)),Int(Times(x,Power(Plus(C1,c,Negate(d),Times(Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Int(Times(x,Power(Plus(C1,Negate(c),d,Times(Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcTanh(Plus(c,Times(d,Tanh(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(c,Negate(d),Times(c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcCoth(Plus(c,Times(d,Tanh(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(c,Negate(d),Times(c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcTanh(Plus(c,Times(d,Tanh(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Plus(C1,c,Negate(d)),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,c,Negate(d),Times(Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,Negate(c),d,Times(Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Tanh(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcCoth(Plus(c,Times(d,Tanh(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Plus(C1,c,Negate(d)),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,c,Negate(d),Times(Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,Negate(c),d,Times(Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcTanh(Plus(c,Times(d,Coth(Plus(a,Times(b,x))))))),Times(b,Int(Times(x,Power(Plus(c,Negate(d),Times(CN1,c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcCoth(Plus(c,Times(d,Coth(Plus(a,Times(b,x))))))),Times(b,Int(Times(x,Power(Plus(c,Negate(d),Times(CN1,c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcTanh(Plus(c,Times(d,Coth(Plus(a,Times(b,x))))))),Times(b,Plus(C1,c,Negate(d)),Int(Times(x,Power(Plus(C1,c,Negate(d),Times(CN1,Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Int(Times(x,Power(Plus(C1,Negate(c),d,Times(CN1,Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(x,ArcCoth(Plus(c,Times(d,Coth(Plus(a,Times(b,x))))))),Times(b,Plus(C1,c,Negate(d)),Int(Times(x,Power(Plus(C1,c,Negate(d),Times(CN1,Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Int(Times(x,Power(Plus(C1,Negate(c),d,Times(CN1,Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcTanh(Plus(c,Times(d,Coth(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(c,Negate(d),Times(CN1,c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcCoth(Plus(c,Times(d,Coth(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(c,Negate(d),Times(CN1,c,Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcTanh(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcTanh(Plus(c,Times(d,Coth(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Plus(C1,c,Negate(d)),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,c,Negate(d),Times(CN1,Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,Negate(c),d,Times(CN1,Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(Power(x_,m_DEFAULT),ArcCoth(Plus(c_DEFAULT,Times(d_DEFAULT,Coth(Plus(a_DEFAULT,Times(b_DEFAULT,x_))))))),x_Symbol),
    Condition(Plus(Times(Power(x,Plus(m,C1)),ArcCoth(Plus(c,Times(d,Coth(Plus(a,Times(b,x)))))),Power(Plus(m,C1),-1)),Times(b,Plus(C1,c,Negate(d)),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,c,Negate(d),Times(CN1,Plus(C1,c,d),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x)),Times(CN1,b,Plus(C1,Negate(c),d),Power(Plus(m,C1),-1),Int(Times(Power(x,Plus(m,C1)),Power(Plus(C1,Negate(c),d,Times(CN1,Plus(C1,Negate(c),Negate(d)),Power(E,Plus(Times(C2,a),Times(C2,b,x))))),-1)),x))),And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(Plus(c,Negate(d))),Negate(C1)))),RationalQ(m)),Greater(m,C0)))),
ISetDelayed(Int(Times(u_DEFAULT,Power(Plus(Times(a_DEFAULT,Cosh(v_)),Times(b_DEFAULT,Sinh(v_))),n_DEFAULT)),x_Symbol),
    Condition(Int(Times(u,Power(Times(a,Power(E,Times(a,Power(b,-1),v))),n)),x),And(FreeQ(List(a,b,n),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b)))))))
  );
}
