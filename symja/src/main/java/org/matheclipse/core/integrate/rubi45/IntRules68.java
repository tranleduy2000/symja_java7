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
public class IntRules68 { 
  public static IAST RULES = List( 
ISetDelayed(Int(Sqr(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(Sqr(a),x),Times(C2,a,b,Int(Sec(Plus(c,Times(d,x))),x)),Times(Sqr(b),Int(Sqr(Sec(Plus(c,Times(d,x)))),x))),FreeQ(List(a,b,c,d),x))),
ISetDelayed(Int(Sqr(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Plus(Times(Sqr(a),x),Times(C2,a,b,Int(Csc(Plus(c,Times(d,x))),x)),Times(Sqr(b),Int(Sqr(Csc(Plus(c,Times(d,x)))),x))),FreeQ(List(a,b,c,d),x))),
ISetDelayed(Int(Sqrt(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Times(C2,b,Power(d,-1),Subst(Int(Power(Plus(a,Sqr(x)),-1),x),x,Times(b,Tan(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),CN1D2)))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Sqrt(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Times(CN2,b,Power(d,-1),Subst(Int(Power(Plus(a,Sqr(x)),-1),x),x,Times(b,Cot(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),CN1D2)))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(Sqr(b),Tan(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,Negate(C2))),Power(Times(d,Plus(n,Negate(C1))),-1)),Times(a,Power(Plus(n,Negate(C1)),-1),Int(Times(Plus(Times(a,Plus(n,Negate(C1))),Times(b,Plus(Times(C3,n),Negate(C4)),Sec(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Greater(n,C1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(CN1,Sqr(b),Cot(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,Negate(C2))),Power(Times(d,Plus(n,Negate(C1))),-1)),Times(a,Power(Plus(n,Negate(C1)),-1),Int(Times(Plus(Times(a,Plus(n,Negate(C1))),Times(b,Plus(Times(C3,n),Negate(C4)),Csc(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,Negate(C2)))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Greater(n,C1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),CN1D2),x_Symbol),
    Condition(Plus(Times(Power(a,-1),Int(Sqrt(Plus(a,Times(b,Sec(Plus(c,Times(d,x)))))),x)),Times(CN1,b,Power(a,-1),Int(Times(Sec(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),CN1D2)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),CN1D2),x_Symbol),
    Condition(Plus(Times(Power(a,-1),Int(Sqrt(Plus(a,Times(b,Csc(Plus(c,Times(d,x)))))),x)),Times(CN1,b,Power(a,-1),Int(Times(Csc(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),CN1D2)),x))),And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(Tan(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),n),Power(Times(d,Plus(Times(C2,n),C1)),-1)),Times(Power(Times(Sqr(a),Plus(Times(C2,n),C1)),-1),Int(Times(Plus(Times(a,Plus(Times(C2,n),C1)),Times(CN1,b,Plus(n,C1),Sec(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,C1))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),LessEqual(n,CN1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(CN1,Cot(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),n),Power(Times(d,Plus(Times(C2,n),C1)),-1)),Times(Power(Times(Sqr(a),Plus(Times(C2,n),C1)),-1),Int(Times(Plus(Times(a,Plus(Times(C2,n),C1)),Times(CN1,b,Plus(n,C1),Csc(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,C1))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),LessEqual(n,CN1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Times(C2,a,Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),n),Tan(Times(C1D2,Plus(c,Times(d,x)))),Power(Plus(C1,Negate(Sqr(Tan(Times(C1D2,Plus(c,Times(d,x))))))),n),Power(Times(d,Plus(a,Times(n,Plus(a,Negate(b))))),-1),AppellF1(Plus(Times(n,Plus(a,Negate(b)),Power(Times(C2,a),-1)),C1D2),n,C1,Plus(Times(n,Plus(a,Negate(b)),Power(Times(C2,a),-1)),QQ(3L,2L)),Sqr(Tan(Times(C1D2,Plus(c,Times(d,x))))),Negate(Sqr(Tan(Times(C1D2,Plus(c,Times(d,x)))))))),And(And(FreeQ(List(a,b,c,d,n),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),Not(IntegerQ(Times(C2,n)))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Times(CN2,a,Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),n),Cot(Plus(Times(C1D2,Plus(c,Times(d,x))),Times(C1D4,Pi))),Power(Plus(C1,Negate(Sqr(Cot(Plus(Times(C1D2,Plus(c,Times(d,x))),Times(C1D4,Pi)))))),n),Power(Times(d,Plus(a,Times(n,Plus(a,Negate(b))))),-1),AppellF1(Plus(Times(n,Plus(a,Negate(b)),Power(Times(C2,a),-1)),C1D2),n,C1,Plus(Times(n,Plus(a,Negate(b)),Power(Times(C2,a),-1)),QQ(3L,2L)),Sqr(Cot(Plus(Times(C1D2,Plus(c,Times(d,x))),Times(C1D4,Pi)))),Negate(Sqr(Cot(Plus(Times(C1D2,Plus(c,Times(d,x))),Times(C1D4,Pi))))))),And(And(FreeQ(List(a,b,c,d,n),x),ZeroQ(Plus(Sqr(a),Negate(Sqr(b))))),Not(IntegerQ(Times(C2,n)))))),
ISetDelayed(Int(Sqrt(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Times(Sqrt(Cos(Plus(c,Times(d,x)))),Sqrt(Plus(a,Times(b,Sec(Plus(c,Times(d,x)))))),Power(Plus(b,Times(a,Cos(Plus(c,Times(d,x))))),CN1D2),Int(Times(Sqrt(Plus(b,Times(a,Cos(Plus(c,Times(d,x)))))),Power(Cos(Plus(c,Times(d,x))),CN1D2)),x)),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Sqrt(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_)))))),x_Symbol),
    Condition(Times(Sqrt(Sin(Plus(c,Times(d,x)))),Sqrt(Plus(a,Times(b,Csc(Plus(c,Times(d,x)))))),Power(Plus(b,Times(a,Sin(Plus(c,Times(d,x))))),CN1D2),Int(Times(Sqrt(Plus(b,Times(a,Sin(Plus(c,Times(d,x)))))),Power(Sin(Plus(c,Times(d,x))),CN1D2)),x)),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),QQ(3L,2L)),x_Symbol),
    Condition(Plus(Int(Times(Plus(Sqr(a),Times(b,Plus(Times(C2,a),Negate(b)),Sec(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),CN1D2)),x),Times(Sqr(b),Int(Times(Sec(Plus(c,Times(d,x))),Plus(C1,Sec(Plus(c,Times(d,x)))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),CN1D2)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),QQ(3L,2L)),x_Symbol),
    Condition(Plus(Int(Times(Plus(Sqr(a),Times(b,Plus(Times(C2,a),Negate(b)),Csc(Plus(c,Times(d,x))))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),CN1D2)),x),Times(Sqr(b),Int(Times(Csc(Plus(c,Times(d,x))),Plus(C1,Csc(Plus(c,Times(d,x)))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),CN1D2)),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(Sqr(b),Tan(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,Negate(C2))),Power(Times(d,Plus(n,Negate(C1))),-1)),Times(Power(Plus(n,Negate(C1)),-1),Int(Times(Simp(Plus(Times(Power(a,3),Plus(n,Negate(C1))),Times(b,Plus(Times(Sqr(b),Plus(n,Negate(C2))),Times(C3,Sqr(a),Plus(n,Negate(C1)))),Sec(Plus(c,Times(d,x)))),Times(a,Sqr(b),Plus(Times(C3,n),Negate(C4)),Sqr(Sec(Plus(c,Times(d,x)))))),x),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,Negate(C3)))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Greater(n,C2)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(CN1,Sqr(b),Cot(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,Negate(C2))),Power(Times(d,Plus(n,Negate(C1))),-1)),Times(Power(Plus(n,Negate(C1)),-1),Int(Times(Simp(Plus(Times(Power(a,3),Plus(n,Negate(C1))),Times(b,Plus(Times(Sqr(b),Plus(n,Negate(C2))),Times(C3,Sqr(a),Plus(n,Negate(C1)))),Csc(Plus(c,Times(d,x)))),Times(a,Sqr(b),Plus(Times(C3,n),Negate(C4)),Sqr(Csc(Plus(c,Times(d,x)))))),x),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,Negate(C3)))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Greater(n,C2)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),-1),x_Symbol),
    Condition(Plus(Times(x,Power(a,-1)),Times(CN1,Power(a,-1),Int(Power(Plus(C1,Times(a,Power(b,-1),Cos(Plus(c,Times(d,x))))),-1),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),-1),x_Symbol),
    Condition(Plus(Times(x,Power(a,-1)),Times(CN1,Power(a,-1),Int(Power(Plus(C1,Times(a,Power(b,-1),Sin(Plus(c,Times(d,x))))),-1),x))),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),CN1D2),x_Symbol),
    Condition(Times(Sqrt(Plus(b,Times(a,Cos(Plus(c,Times(d,x)))))),Power(Times(Sqrt(Cos(Plus(c,Times(d,x)))),Sqrt(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))))),-1),Int(Times(Sqrt(Cos(Plus(c,Times(d,x)))),Power(Plus(b,Times(a,Cos(Plus(c,Times(d,x))))),CN1D2)),x)),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),CN1D2),x_Symbol),
    Condition(Times(Sqrt(Plus(b,Times(a,Sin(Plus(c,Times(d,x)))))),Power(Times(Sqrt(Sin(Plus(c,Times(d,x)))),Sqrt(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))))),-1),Int(Times(Sqrt(Sin(Plus(c,Times(d,x)))),Power(Plus(b,Times(a,Sin(Plus(c,Times(d,x))))),CN1D2)),x)),And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(CN1,Sqr(b),Tan(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,C1)),Power(Times(a,d,Plus(n,C1),Plus(Sqr(a),Negate(Sqr(b)))),-1)),Times(Power(Times(a,Plus(n,C1),Plus(Sqr(a),Negate(Sqr(b)))),-1),Int(Times(Simp(Plus(Times(Plus(Sqr(a),Negate(Sqr(b))),Plus(n,C1)),Times(CN1,a,b,Plus(n,C1),Sec(Plus(c,Times(d,x)))),Times(Sqr(b),Plus(n,C2),Sqr(Sec(Plus(c,Times(d,x)))))),x),Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),Plus(n,C1))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Less(n,CN1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Plus(Times(Sqr(b),Cot(Plus(c,Times(d,x))),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,C1)),Power(Times(a,d,Plus(n,C1),Plus(Sqr(a),Negate(Sqr(b)))),-1)),Times(Power(Times(a,Plus(n,C1),Plus(Sqr(a),Negate(Sqr(b)))),-1),Int(Times(Simp(Plus(Times(Plus(Sqr(a),Negate(Sqr(b))),Plus(n,C1)),Times(CN1,a,b,Plus(n,C1),Csc(Plus(c,Times(d,x)))),Times(Sqr(b),Plus(n,C2),Sqr(Csc(Plus(c,Times(d,x)))))),x),Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),Plus(n,C1))),x))),And(And(And(And(FreeQ(List(a,b,c,d),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),RationalQ(n)),Less(n,CN1)),IntegerQ(Times(C2,n))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§sec"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Integrate(Power(Plus(a,Times(b,Sec(Plus(c,Times(d,x))))),n),x),And(And(FreeQ(List(a,b,c,d,n),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),Not(IntegerQ(Times(C2,n)))))),
ISetDelayed(Int(Power(Plus(a_,Times(b_DEFAULT,$($s("§csc"),Plus(c_DEFAULT,Times(d_DEFAULT,x_))))),n_),x_Symbol),
    Condition(Integrate(Power(Plus(a,Times(b,Csc(Plus(c,Times(d,x))))),n),x),And(And(FreeQ(List(a,b,c,d,n),x),NonzeroQ(Plus(Sqr(a),Negate(Sqr(b))))),Not(IntegerQ(Times(C2,n))))))
  );
}
