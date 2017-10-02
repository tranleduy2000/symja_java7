package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.ReturnException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorLevelSpecification;

/**
 * @see Map
 */
public class Scan extends Map {

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 3, 5);

        int lastIndex = ast.size() - 1;
        boolean heads = false;
        final Options options = new Options(ast.topHead(), ast, lastIndex, engine);
        IExpr option = options.getOption("Heads");
        if (option.isPresent()) {
            lastIndex--;
            if (option.isTrue()) {
                heads = true;
            }
        } else {
            Validate.checkRange(ast, 3, 4);
        }

        try {
            IExpr arg1 = ast.arg1();
            IExpr arg2 = ast.arg2();
            if (lastIndex == 3) {
                IAST result = F.ListAlloc(10);
                java.util.function.Function<IExpr, IExpr> sf = x -> {
                    IAST a = F.unaryAST1(arg1, x);
                    result.append(a);
                    return F.NIL;
                };

                VisitorLevelSpecification level = new VisitorLevelSpecification(sf, ast.get(lastIndex), heads,
                        engine);

                arg2.accept(level);
                for (int i = 1; i < result.size(); i++) {
                    engine.evaluate(result.get(i));
                }

            } else {
                if (arg2.isAST()) {
                    engine.evaluate(((IAST) arg2).map(x -> F.unaryAST1(arg1, x), 1));
                } else {
                    engine.evaluate(arg2);
                }
            }
            return F.Null;
        } catch (final ReturnException e) {
            return e.getValue();
            // don't catch Throw[] here !
        }
    }

}
