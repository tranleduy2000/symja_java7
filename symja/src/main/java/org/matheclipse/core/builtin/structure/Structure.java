package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

public class Structure {

    final static Structure CONST = new Structure();

    static {
        F.Apply.setEvaluator(new Apply());
        F.Depth.setEvaluator(new Depth());
        F.Flatten.setEvaluator(new Flatten());
        F.Function.setEvaluator(new Function());
        F.Map.setEvaluator(new Map());
        F.MapAll.setEvaluator(new MapAll());
        F.MapAt.setEvaluator(new MapAt());
        F.MapThread.setEvaluator(new MapThread());
        F.OrderedQ.setEvaluator(new OrderedQ());
        F.Operate.setEvaluator(new Operate());
        F.Quit.setEvaluator(new Quit());
        F.Scan.setEvaluator(new Scan());
        F.Sort.setEvaluator(new Sort());
        F.Symbol.setEvaluator(new Symbol());
        F.SymbolName.setEvaluator(new SymbolName());
        F.Thread.setEvaluator(new Thread());
        F.Through.setEvaluator(new Through());
    }

    private Structure() {

    }

    /**
     * Maps the elements of the <code>expr</code> with the cloned
     * <code>replacement</code>. <code>replacement</code> is an IAST where the
     * argument at the given position will be replaced by the currently mapped
     * element.
     *
     * @param expr
     * @param replacement an IAST there the argument at the given position is replaced by
     *                    the currently mapped argument of this IAST.
     * @param position
     * @return
     */
    public static IAST threadLogicEquationOperators(IExpr expr, IAST replacement, int position) {
        if (expr.isAST()) {
            IAST ast = (IAST) expr;
            if (ast.size() > 1) {
                ISymbol[] logicEquationHeads = {F.And, F.Or, F.Xor, F.Nand, F.Nor, F.Not, F.Implies, F.Equivalent,
                        F.Equal, F.Unequal, F.Less, F.Greater, F.LessEqual, F.GreaterEqual};
                for (int i = 0; i < logicEquationHeads.length; i++) {
                    if (ast.isAST(logicEquationHeads[i])) {
                        IAST cloned = replacement.clone();
                        cloned.set(position, null);
                        return ast.mapThread(cloned, position);
                    }
                }

            }
        }
        return F.NIL;
    }

    /**
     * Maps the elements of the <code>expr</code> with the cloned
     * <code>replacement</code>. <code>replacement</code> is an IAST where the
     * argument at the given position will be replaced by the currently mapped
     * element.
     *
     * @param expr
     * @param replacement an IAST there the argument at the given position is replaced by
     *                    the currently mapped argument of this IAST.
     * @param position
     * @return
     */
    public static IAST threadPlusLogicEquationOperators(IExpr expr, IAST replacement, int position) {
        if (expr.isAST()) {
            IAST ast = (IAST) expr;
            if (ast.size() > 1) {
                ISymbol[] plusLogicEquationHeads = {F.Plus, F.And, F.Or, F.Xor, F.Nand, F.Nor, F.Not, F.Implies,
                        F.Equivalent, F.Equal, F.Unequal, F.Less, F.Greater, F.LessEqual, F.GreaterEqual};
                for (int i = 0; i < plusLogicEquationHeads.length; i++) {
                    if (ast.isAST(plusLogicEquationHeads[i])) {
                        IAST cloned = replacement.clone();
                        cloned.set(position, null);
                        return ast.mapThread(cloned, position);
                    }
                }

            }
        }
        return F.NIL;
    }

    public static Structure initialize() {
        return CONST;
    }


}
