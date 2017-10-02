package org.matheclipse.core.builtin.structure;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.visit.VisitorLevelSpecification;
import org.matheclipse.parser.client.math.MathException;

/**
 * @see Scan
 */
public class Map extends AbstractFunctionEvaluator {
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
            VisitorLevelSpecification level;
            if (lastIndex == 3) {
                level = new VisitorLevelSpecification(x -> F.unaryAST1(arg1, x), ast.get(lastIndex), heads, engine);
            } else {
                level = new VisitorLevelSpecification(x -> F.unaryAST1(arg1, x), 1, heads);
            }
            return arg2.accept(level).orElse(arg2);
        } catch (final MathException e) {
            engine.printMessage(e.getMessage());
        }
        return F.NIL;
    }

}

 