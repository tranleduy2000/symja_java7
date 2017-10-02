package org.matheclipse.core.builtin.intfunction;

import org.matheclipse.core.expression.F;

public class IntegerFunctions {
    final static IntegerFunctions CONST = new IntegerFunctions();

    static {
        F.BitLength.setEvaluator(new BitLength());
        F.Ceiling.setEvaluator(new Ceiling());
        F.Floor.setEvaluator(new Floor());
        F.FractionalPart.setEvaluator(new FractionalPart());
        F.IntegerExponent.setEvaluator(new IntegerExponent());
        F.IntegerLength.setEvaluator(new IntegerLength());
        F.IntegerPart.setEvaluator(new IntegerPart());
        F.Mod.setEvaluator(new Mod());
        F.PowerMod.setEvaluator(new PowerMod());
        F.Quotient.setEvaluator(new Quotient());
        F.Round.setEvaluator(new Round());
    }

    private IntegerFunctions() {

    }

    public static IntegerFunctions initialize() {
        return CONST;
    }


}
