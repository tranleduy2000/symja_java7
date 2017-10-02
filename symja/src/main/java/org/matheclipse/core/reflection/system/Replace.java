package org.matheclipse.core.reflection.system;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.functors.Functors;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.function.ReplaceFunction;
import org.matheclipse.core.visit.VisitorLevelSpecification;

import java.util.function.Function;

/**
 * <pre>
 * Rationalize(expression)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * convert numerical real or imaginary parts in (sub-)expressions into rational
 * numbers.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Rationalize(6.75)
 * 27/4
 *
 * &gt;&gt; Rationalize(0.25+I*0.33333)
 * 1/4+I*33333/100000
 * </pre>
 */
public class Replace extends AbstractEvaluator {

    public Replace() {
    }

    private static IExpr replaceExpr(final IAST ast, IExpr arg1, IExpr rules, final EvalEngine engine) {
        if (rules.isListOfLists()) {
            IAST rulesList = (IAST) rules;
            IAST result = F.ListAlloc(rulesList.size());

            for (IExpr list : rulesList) {
                IAST subList = (IAST) list;
                IExpr temp = F.NIL;
                for (IExpr element : subList) {
                    if (element.isRuleAST()) {
                        IAST rule = (IAST) element;
                        Function<IExpr, IExpr> function = Functors.rules(rule, engine);
                        temp = function.apply(arg1);
                        if (temp.isPresent()) {
                            break;
                        }
                    } else {
                        WrongArgumentType wat = new WrongArgumentType(ast, ast, -1,
                                "Rule expression (x->y) expected: ");
                        throw wat;
                    }
                }
                result.append(temp.orElse(arg1));
            }
            return result;
        } else if (rules.isList()) {
            for (IExpr element : (IAST) rules) {
                if (element.isRuleAST()) {
                    IAST rule = (IAST) element;
                    Function<IExpr, IExpr> function = Functors.rules(rule, engine);
                    IExpr temp = function.apply(arg1);
                    if (temp.isPresent()) {
                        return temp;
                    }
                } else {
                    WrongArgumentType wat = new WrongArgumentType(ast, ast, -1, "Rule expression (x->y) expected: ");
                    throw wat;
                }

            }
            return arg1;
        }
        if (rules.isRuleAST()) {
            return replaceRule(arg1, (IAST) rules, engine);
        } else {
            WrongArgumentType wat = new WrongArgumentType(ast, ast, -1, "Rule expression (x->y) expected: ");
            engine.printMessage(wat.getMessage());
        }
        return F.NIL;
    }

    private static IExpr replaceExprWithLevelSpecification(final IAST ast, IExpr arg1, IExpr rules,
                                                           IExpr exprLevelSpecification, EvalEngine engine) {
        // use replaceFunction#setRule() method to set the current rules which
        // are initialized with null
        ReplaceFunction replaceFunction = new ReplaceFunction(ast, null, engine);
        VisitorLevelSpecification level = new VisitorLevelSpecification(replaceFunction, exprLevelSpecification, false,
                engine);

        if (rules.isListOfLists()) {
            IAST rulesList = (IAST) rules;
            IAST result = F.ListAlloc(rulesList.size());
            for (IExpr list : rulesList) {
                IExpr temp = F.NIL;
                IAST subList = (IAST) list;
                for (IExpr element : subList) {
                    if (element.isRuleAST()) {
                        IAST rule = (IAST) element;
                        replaceFunction.setRule(rule);
                        temp = arg1.accept(level);
                        if (temp.isPresent()) {
                            break;
                        }
                    } else {
                        WrongArgumentType wat = new WrongArgumentType(ast, ast, -1,
                                "Rule expression (x->y) expected: ");
                        throw wat;
                    }
                }
                result.append(temp.orElse(arg1));
            }
            return result;
        }

        replaceFunction.setRule(rules);
        return arg1.accept(level).orElse(arg1);
    }

    /**
     * Try to apply one single rule.
     *
     * @param arg1
     * @param rule
     * @return
     */
    public static IExpr replaceRule(IExpr arg1, IAST rule, EvalEngine engine) {
        Function<IExpr, IExpr> function = Functors.rules(rule, engine);
        IExpr temp = function.apply(arg1);
        if (temp.isPresent()) {
            return temp;
        }
        return arg1;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        if (ast.isAST1()) {
            return F.operatorFormAST1(ast);
        }
        Validate.checkRange(ast, 3, 4);
        try {
            IExpr arg1 = ast.arg1();
            IExpr rules = engine.evaluate(ast.arg2());
            if (ast.isAST3()) {
                // arg3 should contain a "level specification":
                return replaceExprWithLevelSpecification(ast, arg1, rules, ast.arg3(), engine);
            }
            return replaceExpr(ast, arg1, rules, engine);
        } catch (WrongArgumentType wat) {
            engine.printMessage(wat.getMessage());
        }
        return F.NIL;
    }

    @Override
    public void setUp(final ISymbol newSymbol) {
        newSymbol.setAttributes(ISymbol.HOLDREST);
    }
}
