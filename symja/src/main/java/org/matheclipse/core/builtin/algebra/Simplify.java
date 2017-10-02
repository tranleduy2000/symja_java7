package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.builtin.function.Refine;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongArgumentType;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.IAssumptions;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.AbstractVisitorBoolean;
import org.matheclipse.core.visit.VisitorExpr;

import java.util.function.Function;

/**
 * <pre>
 * Simplify(expr)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * simplifies <code>expr</code>
 * </p>
 * </blockquote>
 * <p>
 * <pre>
 * Simplify(expr, option1, option2, ...)
 * </pre>
 * <p>
 * <blockquote>
 * <p>
 * simplify <code>expr</code> with some additional options set
 * </p>
 * </blockquote>
 * <ul>
 * <li>Assumptions - use assumptions to simplify the expression</li>
 * <li>ComplexFunction - use this function to determine the &ldquo;weight&rdquo;
 * of an expression.</li>
 * </ul>
 * <h3>Examples</h3>
 * <p>
 * <pre>
 * &gt;&gt; Simplify(1/2*(2*x+2))
 * x+1
 *
 * &gt;&gt; Simplify(2*Sin(x)^2 + 2*Cos(x)^2)
 * 2
 *
 * &gt;&gt; Simplify(x)
 * x
 *
 * &gt;&gt; Simplify(f(x))
 * f(x)
 *
 * &gt;&gt; Simplify(a*x^2+b*x^2)
 * (a+b)*x^2
 * </pre>
 * <p>
 * Simplify with an assumption:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Simplify(Sqrt(x^2), Assumptions -&gt; x&gt;0)
 * x
 * </pre>
 * <p>
 * For <code>Assumptions</code> you can define the assumption directly as second
 * argument:
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; Simplify(Sqrt(x^2), x&gt;0)
 * x
 * </pre>
 * <p>
 * <pre>
 * ```
 * &gt;&gt; Simplify(Abs(x), x&lt;0)
 * Abs(x)
 * </pre>
 * <p>
 * With this &ldquo;complexity function&rdquo; the <code>Abs</code> expression
 * gets a &ldquo;heavier weight&rdquo;.
 * </p>
 * <p>
 * <pre>
 * &gt;&gt; complexity(x_) := 2*Count(x, _Abs, {0, 10}) + LeafCount(x)
 *
 * &gt;&gt; Simplify(Abs(x), x&lt;0, ComplexityFunction-&gt;complexity)
 * -x
 * </pre>
 */
public class Simplify extends AbstractFunctionEvaluator {

    /**
     * Creata the complexity function which determines the &quot;more
     * simplified&quot; expression.
     *
     * @param complexityFunctionHead
     * @param engine
     * @return
     */
    private static Function<IExpr, Long> createComplexityFunction(IExpr complexityFunctionHead, EvalEngine engine) {
        Function<IExpr, Long> complexityFunction = x -> x.leafCountSimplify();
        if (complexityFunctionHead.isPresent()) {
            final IExpr head = complexityFunctionHead;
            complexityFunction = x -> {
                IExpr temp = engine.evaluate(F.unaryAST1(head, x));
                if (temp.isInteger() && !temp.isNegative()) {
                    return ((IInteger) temp).toLong();
                }
                return Long.MAX_VALUE;
            };
        }
        return complexityFunction;
    }

    @Override
    public IExpr evaluate(final IAST ast, EvalEngine engine) {
        Validate.checkRange(ast, 2);

        IExpr arg1 = ast.arg1();
        IExpr assumptionExpr = F.NIL;
        IExpr complexityFunctionHead = F.NIL;

        if (ast.size() > 2) {
            IExpr arg2 = ast.arg2();

            if (!arg2.isRule()) {
                assumptionExpr = arg2;
            }
            final Options options = new Options(ast.topHead(), ast, 2, engine);
            IExpr option = options.getOption("Assumptions");
            if (option.isPresent()) {
                assumptionExpr = option;
            }
            complexityFunctionHead = options.getOption("ComplexityFunction");
        }
        if (arg1.isAtom()) {
            return arg1;
        }

        try {
            Function<IExpr, Long> complexityFunction = createComplexityFunction(complexityFunctionHead, engine);
            long minCounter = complexityFunction.apply(arg1);
            IExpr result = arg1;
            long count = 0L;
            if (assumptionExpr.isPresent()) {
                IAssumptions assumptions = Refine.determineAssumptions(ast.topHead(), assumptionExpr, engine);
                if (assumptions != null) {
                    arg1 = Refine.refineAssumptions(arg1, assumptions, engine);
                    count = complexityFunction.apply(arg1);
                    if (count < minCounter) {
                        minCounter = count;
                        result = arg1;
                    }
                }
            }

            IExpr temp = arg1.replaceAll(F.List(F.Rule(F.GoldenRatio, F.Times(F.C1D2, F.Plus(F.C1, F.Sqrt(F.C5)))),
                    F.Rule(F.Degree, F.Divide(F.Pi, F.ZZ(180)))));
            if (temp.isPresent()) {
                arg1 = temp;
            }

            temp = arg1.accept(new SimplifyVisitor(complexityFunction));
            while (temp.isPresent()) {
                count = complexityFunction.apply(temp);
                if (count < minCounter) {
                    minCounter = count;
                    result = temp;
                    temp = result.accept(new SimplifyVisitor(complexityFunction));
                } else {
                    return result;
                }
            }
            return result;

        } catch (ArithmeticException e) {
            //
        }
        return F.NIL;
    }

    public class IsBasicExpressionVisitor extends AbstractVisitorBoolean {
        public IsBasicExpressionVisitor() {
            super();
        }

        @Override
        public boolean visit(IAST ast) {
            if (ast.isTimes() || ast.isPlus()) {
                // check the arguments
                return ast.forAll(x -> x.accept(this), 1);
            }
            if (ast.isPower() && (ast.arg2().isInteger())) {
                // check the arguments
                return ast.arg1().accept(this);
            }
            return false;
        }

        @Override
        public boolean visit(IComplex element) {
            return true;
        }

        @Override
        public boolean visit(IComplexNum element) {
            return true;
        }

        @Override
        public boolean visit(IFraction element) {
            return true;
        }

        @Override
        public boolean visit(IInteger element) {
            return true;
        }

        @Override
        public boolean visit(INum element) {
            return true;
        }

        @Override
        public boolean visit(ISymbol symbol) {
            return true;
        }
    }

    public class SimplifyVisitor extends VisitorExpr {
        final IsBasicExpressionVisitor isBasicAST = new IsBasicExpressionVisitor();
        /**
         * This function is used to determine the “weight” of an expression. For example
         * by counting the leafs of an expression with the
         * <code>IExpr#leafCountSimplify()</code> method.
         */
        final Function<IExpr, Long> fComplexityFunction;

        public SimplifyVisitor(Function<IExpr, Long> complexityFunction) {
            super();
            fComplexityFunction = complexityFunction;
        }

        private IExpr tryExpandAllTransformation(IAST plusAST, IExpr test) {
            IExpr result = F.NIL;
            long minCounter = fComplexityFunction.apply(plusAST);
            IExpr temp;
            long count;

            try {
                temp = F.evalExpandAll(test);
                count = fComplexityFunction.apply(temp);
                if (count < minCounter) {
                    result = temp;
                }
            } catch (WrongArgumentType wat) {
                //
            }

            return result;
        }

        private IExpr tryTransformations(IExpr expr) {
            IExpr result = F.NIL;
            if (expr.isAST()) {
                // try ExpandAll, Together, Apart, Factor to reduce the
                // expression
                long minCounter = fComplexityFunction.apply(expr);
                IExpr temp;
                long count;

                try {
                    temp = F.evalExpandAll(expr);
                    count = fComplexityFunction.apply(temp);
                    if (count < minCounter) {
                        minCounter = count;
                        result = temp;
                    }
                } catch (WrongArgumentType wat) {
                    //
                }

                try {
                    temp = F.eval(F.Together(expr));
                    count = fComplexityFunction.apply(temp);
                    if (count < minCounter) {
                        minCounter = count;
                        result = temp;
                    }
                } catch (WrongArgumentType wat) {
                    //
                }

                try {
                    temp = F.eval(F.Factor(expr));
                    count = fComplexityFunction.apply(temp);
                    if (count < minCounter) {
                        minCounter = count;
                        result = temp;
                    }
                } catch (WrongArgumentType wat) {
                    //
                }

                try {
                    temp = F.eval(F.Apart(expr));
                    count = fComplexityFunction.apply(temp);
                    if (count < minCounter) {
                        minCounter = count;
                        result = temp;
                    }
                } catch (WrongArgumentType wat) {
                    //
                }
            }
            return result;

        }

        @Override
        public IExpr visit(IAST ast) {
            IExpr result = F.NIL;
            IExpr temp;

            temp = visitAST(ast);
            if (temp.isPresent()) {
                long minCounter = fComplexityFunction.apply(ast);
                long count = fComplexityFunction.apply(temp);
                if (count < minCounter) {
                    minCounter = count;
                    if (temp.isAST()) {
                        ast = (IAST) temp;
                        result = temp;
                    } else {
                        return temp;
                    }
                }
            }

            if (ast.isPlus()) {
                IAST basicPlus = F.Plus();
                IAST restPlus = F.Plus();

                for (int i = 1; i < ast.size(); i++) {
                    temp = ast.get(i);
                    if (temp.accept(isBasicAST)) {
                        basicPlus.append(temp);
                    } else {
                        restPlus.append(temp);
                    }
                }
                if (basicPlus.size() > 1) {
                    temp = tryTransformations(basicPlus.getOneIdentity(F.C0));
                    if (temp.isPresent()) {
                        if (restPlus.isAST0()) {
                            return temp;
                        }
                        return F.Plus(temp, restPlus);
                    }
                }

                temp = tryTransformations(ast);
                if (temp.isPresent()) {
                    return temp;
                }
                return result;

            } else if (ast.isTimes()) {
                IAST basicTimes = F.Times();
                IAST restTimes = F.Times();
                INumber number = null;
                if (ast.arg1().isNumber()) {
                    number = (INumber) ast.arg1();
                }
                IExpr reduced;
                for (int i = 1; i < ast.size(); i++) {
                    temp = ast.get(i);
                    if (temp.accept(isBasicAST)) {
                        if (i != 1 && number != null) {
                            if (temp.isPlus()) {
                                // <number> * Plus[.....]
                                reduced = tryExpandAll(ast, temp, number, i);
                                if (reduced.isPresent()) {
                                    return reduced;
                                }
                            } else if (temp.isPower() && ((IAST) temp).arg1().isPlus()
                                    && ((IAST) temp).arg2().isMinusOne()) {
                                // <number> * Power[Plus[...], -1 ]
                                reduced = tryExpandAll(ast, ((IAST) temp).arg1(), number.inverse(), i);
                                if (reduced.isPresent()) {
                                    return F.Power(reduced, F.CN1);
                                }
                            }
                        }
                        basicTimes.append(temp);
                    } else {
                        restTimes.append(temp);
                    }
                }

                if (basicTimes.size() > 1) {
                    temp = tryTransformations(basicTimes.getOneIdentity(F.C0));
                    if (temp.isPresent()) {
                        if (restTimes.isAST0()) {
                            return temp;
                        }
                        return F.Times(temp, restTimes);
                    }
                }

                temp = tryTransformations(ast);
                if (temp.isPresent()) {
                    return temp;
                }
                return result;
            }

            temp = F.evalExpandAll(ast);
            long minCounter = fComplexityFunction.apply(ast);

            long count = fComplexityFunction.apply(temp);
            if (count < minCounter) {
                return temp;
            }
            return result;
        }

        private IExpr tryExpandAll(IAST ast, IExpr temp, IExpr arg1, int i) {
            IExpr expandedAst = tryExpandAllTransformation((IAST) temp, F.Times(arg1, temp));
            if (expandedAst.isPresent()) {
                IAST result = F.Times();
                // ast.range(2, ast.size()).toList(result.args());
                result.appendAll(ast, 2, ast.size());
                result.set(i - 1, expandedAst);
                return result;
            }
            return F.NIL;
        }
    }

}