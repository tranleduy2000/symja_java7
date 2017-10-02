package org.matheclipse.core.reflection.system.function;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.functors.Functors;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.function.Function;

import static org.matheclipse.core.reflection.system.Replace.replaceRule;

public final class ReplaceFunction implements Function<IExpr, IExpr> {
    private final IAST ast;
    private final EvalEngine engine;
    private IExpr rules;

    public ReplaceFunction(final IAST ast, final IExpr rules, final EvalEngine engine) {
        this.ast = ast;
        this.rules = rules;
        this.engine = engine;
    }

    /**
     * Replace the <code>input</code> expression with the given rules.
     *
     * @param input the expression which should be replaced by the given rules
     * @return the expression created by the replacements or <code>null</code> if no
     * replacement occurs
     */
    @Override
    public IExpr apply(IExpr input) {
        if (rules.isList()) {
            for (IExpr element : (IAST) rules) {
                if (element.isRuleAST()) {
                    IAST rule = (IAST) element;
                    Function<IExpr, IExpr> function = Functors.rules(rule, engine);
                    IExpr temp = function.apply(input);
                    if (temp.isPresent()) {
                        return temp;
                    }
                } else {
                    WrongArgumentType wat = new WrongArgumentType(ast, ast, -1,
                            "Rule expression (x->y) expected: ");
                    throw wat;
                }

            }
            return input;
        }
        if (rules.isRuleAST()) {
            return replaceRule(input, (IAST) rules, engine);
        } else {
            WrongArgumentType wat = new WrongArgumentType(ast, ast, -1, "Rule expression (x->y) expected: ");
            engine.printMessage(wat.getMessage());
        }
        return F.NIL;
    }

    public void setRule(IExpr rules) {
        this.rules = rules;
    }

}
