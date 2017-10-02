package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.WrongNumberOfArguments;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.eval.util.Lambda;
import org.matheclipse.core.eval.util.OpenFixedSizeMap;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

import java.util.HashMap;

public class Function extends AbstractCoreFunctionEvaluator {

    private static java.util.Map<IExpr, IExpr> getRulesMap(final IAST symbolSlots, final IAST ast) {
        int size = symbolSlots.size() - 1;
        final java.util.Map<IExpr, IExpr> rulesMap;
        if (size <= 5) {
            rulesMap = new OpenFixedSizeMap<IExpr, IExpr>(size * 3 - 1);
        } else {
            rulesMap = new HashMap<IExpr, IExpr>();
        }
        for (int i = 1; i <= size; i++) {
            rulesMap.put(symbolSlots.get(i), ast.get(i));
        }
        return rulesMap;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.head().equals(F.Function)) {
            IExpr temp = engine.evalSetAttributes(ast, true);
            if (temp.isPresent() && !temp.equals(ast)) {
                return temp;
            }
            return F.NIL;
        }
        if (ast.head().isAST()) {

            final IAST function = (IAST) ast.head();
            if (function.size() > 1) {
                IExpr arg1 = function.arg1();
                if (function.isAST1()) {
                    return Lambda.replaceSlotsOrElse(arg1, ast, arg1);
                } else if (function.isAST2()) {
                    IExpr arg2 = function.arg2();
                    IAST symbolSlots;
                    if (arg1.isList()) {
                        symbolSlots = (IAST) arg1;
                    } else {
                        symbolSlots = F.List(arg1);
                    }
                    if (symbolSlots.size() > ast.size()) {
                        throw new WrongNumberOfArguments(ast, symbolSlots.size() - 1, ast.size() - 1);
                    }
                    return arg2.replaceAll(x -> {
                        IExpr temp = getRulesMap(symbolSlots, ast).get(x);
                        return temp != null ? temp : F.NIL;
                    }).orElse(arg2);
                }
            }
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        // don't set HOLDALL - the arguments are evaluated before applying the 'function
        // head'
    }
}
