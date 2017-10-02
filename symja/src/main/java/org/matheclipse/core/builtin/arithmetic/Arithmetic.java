package org.matheclipse.core.builtin.arithmetic;

import org.matheclipse.core.eval.interfaces.IFunctionEvaluator;
import org.matheclipse.core.expression.F;

public final class Arithmetic {
    public final static Plus CONST_PLUS = new Plus();
    public final static Times CONST_TIMES = new Times();
    public final static Power CONST_POWER = new Power();
    public final static IFunctionEvaluator CONST_COMPLEX = new Complex();
    public final static IFunctionEvaluator CONST_RATIONAL = new Rational();
    final static Arithmetic CONST = new Arithmetic();

    static {
        F.Plus.setDefaultValue(F.C0);
        F.Plus.setEvaluator(CONST_PLUS);
        F.Times.setDefaultValue(F.C1);
        F.Times.setEvaluator(CONST_TIMES);
        F.Power.setDefaultValue(2, F.C1);
        F.Power.setEvaluator(CONST_POWER);
        F.Sqrt.setEvaluator(new Sqrt());
        F.Minus.setEvaluator(new Minus());

        F.Abs.setEvaluator(new Abs());
        F.AddTo.setEvaluator(new AddTo());
        F.Arg.setEvaluator(new Arg());
        F.Complex.setEvaluator(CONST_COMPLEX);
        F.Conjugate.setEvaluator(new Conjugate());
        F.Decrement.setEvaluator(new Decrement());
        F.DirectedInfinity.setEvaluator(new DirectedInfinity());
        F.DivideBy.setEvaluator(new DivideBy());
        F.Gamma.setEvaluator(new Gamma());
        F.HarmonicNumber.setEvaluator(new HarmonicNumber());
        F.Im.setEvaluator(new Im());
        F.Increment.setEvaluator(new Increment());
        F.Piecewise.setEvaluator(new Piecewise());
        F.Pochhammer.setEvaluator(new Pochhammer());
        F.Precision.setEvaluator(new Precision());
        F.PreDecrement.setEvaluator(new PreDecrement());
        F.PreIncrement.setEvaluator(new PreIncrement());
        F.Rational.setEvaluator(CONST_RATIONAL);
        F.Re.setEvaluator(new Re());
        F.SubtractFrom.setEvaluator(new SubtractFrom());
        F.TimesBy.setEvaluator(new TimesBy());

    }

    private Arithmetic() {

    }

    public static Arithmetic initialize() {
        return CONST;
    }


}