package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.BinaryFunctorImpl;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;

public class ExpTrigsFunctions {
    final static ExpTrigsFunctions CONST = new ExpTrigsFunctions();

    static {

        F.AngleVector.setEvaluator(new AngleVector());
        F.ArcCos.setEvaluator(new ArcCos());
        F.ArcCosh.setEvaluator(new ArcCosh());
        F.ArcCot.setEvaluator(new ArcCot());
        F.ArcCoth.setEvaluator(new ArcCoth());
        F.ArcCsc.setEvaluator(new ArcCsc());
        F.ArcCsch.setEvaluator(new ArcCsch());
        F.ArcSec.setEvaluator(new ArcSec());
        F.ArcSech.setEvaluator(new ArcSech());
        F.ArcSin.setEvaluator(new ArcSin());
        F.ArcSinh.setEvaluator(new ArcSinh());
        F.ArcTan.setEvaluator(new ArcTan());
        F.ArcTanh.setEvaluator(new ArcTanh());
        F.Cos.setEvaluator(new Cos());
        F.Cosh.setEvaluator(new Cosh());
        F.Cot.setEvaluator(new Cot());
        F.Coth.setEvaluator(new Coth());
        F.Csc.setEvaluator(new Csc());
        F.Csch.setEvaluator(new Csch());
        F.Exp.setEvaluator(new Exp());
        F.Haversine.setEvaluator(new Haversine());
        F.InverseHaversine.setEvaluator(new InverseHaversine());
        F.Log.setEvaluator(new Log());
        F.LogisticSigmoid.setEvaluator(new LogisticSigmoid());
        F.Log10.setEvaluator(new Log10());
        F.Log2.setEvaluator(new Log2());
        F.Sec.setEvaluator(new Sec());
        F.Sech.setEvaluator(new Sech());
        F.Sin.setEvaluator(new Sin());
        F.Sinc.setEvaluator(new Sinc());
        F.Sinh.setEvaluator(new Sinh());
        F.Tan.setEvaluator(new Tan());
        F.Tanh.setEvaluator(new Tanh());

    }

    private ExpTrigsFunctions() {

    }

    public static BinaryFunctorImpl<IExpr> integerLogFunction() {
        return new BinaryFunctorImpl<IExpr>() {
            /**
             * Evaluate if Log(arg2)/Log(arg1) has an integer number result
             */
            @Override
            public IExpr apply(IExpr arg1, IExpr arg2) {
                if (arg1.isInteger() && arg2.isInteger()) {
                    return Log.baseBLog((IInteger) arg2, (IInteger) arg1);
                }
                return F.NIL;
            }
        };
    }

    public static ExpTrigsFunctions initialize() {
        return CONST;
    }


}
