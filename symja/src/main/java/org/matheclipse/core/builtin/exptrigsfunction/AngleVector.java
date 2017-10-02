package org.matheclipse.core.builtin.exptrigsfunction;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class AngleVector extends AbstractFunctionEvaluator {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2, 3);

        IExpr arg1 = ast.arg1();
        IExpr phi;
        if (ast.isAST2()) {
            IExpr arg2 = ast.arg2();

            if (arg1.isAST(F.List, 3)) {
                IExpr x = ((IAST) arg1).arg1();
                IExpr y = ((IAST) arg1).arg2();
                if (arg2.isAST(F.List, 3)) {
                    // 'AngleVector[{x_, y_}, {r_, phi_}]': '{x + r * Cos[phi], y + r * Sin[phi]}'
                    IExpr r = ((IAST) arg2).arg1();
                    phi = ((IAST) arg2).arg2();
                    return F.List(F.Plus(x, F.Times(r, F.Cos(phi))), F.Plus(y, F.Times(r, F.Sin(phi))));
                } else {
                    phi = arg2;
                }
                // 'AngleVector[{x_, y_}, phi_]': '{x + Cos[phi], y + Sin[phi]}'
                return F.List(F.Plus(x, F.Cos(phi)), F.Plus(y, F.Sin(phi)));
            }
            return F.NIL;
        }

        if (arg1.isAST(F.List, 3)) {
            // 'AngleVector[{r_, phi_}]': '{r * Cos[phi], r * Sin[phi]}'
            IExpr r = ((IAST) arg1).arg1();
            phi = ((IAST) arg1).arg2();
            return F.List(F.Times(r, F.Cos(phi)), F.Times(r, F.Sin(phi)));
        } else {
            phi = arg1;
        }
        // 'AngleVector[phi_]': '{Cos[phi], Sin[phi]}'
        return F.List(F.Cos(phi), F.Sin(phi));
    }

    @Override
    public void setUp(ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.NUMERICFUNCTION);
    }

}
