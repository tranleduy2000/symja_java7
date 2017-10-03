package org.matheclipse.core.builtin;

import com.duy.lambda.BiPredicate;
import com.duy.lambda.Predicate;

import org.matheclipse.core.eval.EvalAttributes;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.ReturnException;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongNumberOfArguments;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.eval.util.Lambda;
import org.matheclipse.core.eval.util.OpenFixedSizeMap;
import org.matheclipse.core.eval.util.Options;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.generic.Predicates;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.VisitorLevelSpecification;
import org.matheclipse.parser.client.math.MathException;

import java.util.HashMap;

public class Structure {

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

	/**
	 * 
	 * <p>
	 * See the online Symja function reference: <a href=
	 * "https://bitbucket.org/axelclk/symja_android_library/wiki/Symbols/Apply">
	 * Apply</a>
	 * </p>
	 *
	 */
	private final static class Apply extends AbstractCoreFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 3, 5);

			IAST evaledAST = ast.clone();
			for (int i = 1; i < evaledAST.size(); i++) {
				evaledAST.set(i, engine.evaluate(evaledAST.get(i)));
			}
			int lastIndex = evaledAST.size() - 1;
			boolean heads = false;
			final Options options = new Options(evaledAST.topHead(), evaledAST, lastIndex, engine);
			IExpr option = options.getOption("Heads");
			if (option.isPresent()) {
				lastIndex--;
				if (option.isTrue()) {
					heads = true;
				}
			} else {
				Validate.checkRange(evaledAST, 3, 4);
			}

			IExpr arg1 = evaledAST.arg1();
			IExpr arg2 = evaledAST.arg2();
			return evalApply(arg1, arg2, evaledAST, lastIndex, heads, engine);
		}

		public static IExpr evalApply(IExpr arg1, IExpr arg2, IAST evaledAST, int lastIndex, boolean heads,
				EvalEngine engine) {
			VisitorLevelSpecification level = null;
			com.duy.lambda.Function<IExpr, IExpr> af = new com.duy.lambda.Function<IExpr, IExpr>() {
                @Override
                public IExpr apply(IExpr x) {
                    return x.isAST() ? ((IAST) x).setAtCopy(0, arg1) : F.NIL;
                }
            };
			try {
				if (lastIndex == 3) {
					level = new VisitorLevelSpecification(af, evaledAST.get(lastIndex), heads, engine);
				} else {
					level = new VisitorLevelSpecification(af, 0);
				}

				if (!arg2.isAtom()) {
					return arg2.accept(level).orElse(arg2);
				} else if (evaledAST.isAST2()) {
					if (arg1.isFunction()) {
						return F.unaryAST1(arg1, arg2);
					}
					return arg2;
				}
			} catch (final MathException e) {
				engine.printMessage(e.getMessage());
			} catch (final ArithmeticException e) {

			}
			return F.NIL;
		}

		@Override
		public void setUp(final ISymbol newSymbol) {
			newSymbol.setAttributes(ISymbol.HOLDALL);
		}

	}

	/**
	 * Calculates the depth of an expression (i.e. <code>{x,{y}} --> 3</code>
	 */
	private final static class Depth extends AbstractCoreFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 2);

			final IExpr arg1 = engine.evaluate(ast.arg1());
			if (!(arg1.isAST())) {
				return F.C1;
			}
			return F.integer(depth((IAST) ast.arg1(), 1));
		}

		/**
		 * Calculates the depth of an expression. Atomic expressions (no sublists) have
		 * depth <code>1</code> Example: the nested list <code>[x,[y]]</code> has depth
		 * <code>3</code>
		 * 
		 * @param headOffset
		 * 
		 */
		public static int depth(final IAST list, int headOffset) {
			int maxDepth = 1;
			int d;
			for (int i = headOffset; i < list.size(); i++) {
				if (list.get(i).isAST()) {
					d = depth((IAST) list.get(i), headOffset);
					if (d > maxDepth) {
						maxDepth = d;
					}
				}
			}
			return ++maxDepth;
		}

	}

	/**
	 * TODO implement &quot;Flatten&quot; function (especially Flatten(list, 1) )
	 * 
	 */
	private final static class Flatten extends AbstractCoreFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 2);

			IExpr arg1 = engine.evaluate(ast.arg1());
			if (arg1.isAST()) {
				IAST arg1AST = (IAST) arg1;
				if (ast.isAST1()) {
					IAST resultList = EvalAttributes.flatten(arg1AST.topHead(), (IAST) arg1);
					if (resultList.isPresent()) {
						return resultList;
					}
					return arg1AST;
				} else if (ast.isAST2()) {
					IExpr arg2 = engine.evaluate(ast.arg2());

					int level = Validate.checkIntLevelType(arg2);
					if (level > 0) {
						IAST resultList = F.ast(arg1AST.topHead());
						if (EvalAttributes.flatten(arg1AST.topHead(), (IAST) arg1, resultList, 0, level)) {
							return resultList;
						}
					}
					return arg1;
				} else if (ast.isAST3() && ast.arg3().isSymbol()) {
					IExpr arg2 = engine.evaluate(ast.arg2());

					int level = Validate.checkIntLevelType(arg2);
					if (level > 0) {
						IAST resultList = F.ast(arg1AST.topHead());
						if (EvalAttributes.flatten((ISymbol) ast.arg3(), (IAST) arg1, resultList, 0, level)) {
							return resultList;
						}
					}
					return arg1;
				}
			}
			return F.NIL;
		}

		@Override
		public void setUp(ISymbol newSymbol) {
			newSymbol.setAttributes(ISymbol.HOLDALL);
		}
	}

	private final static class Function extends AbstractCoreFunctionEvaluator {

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
						return arg2.replaceAll(new com.duy.lambda.Function<IExpr, IExpr>() {
                            @Override
                            public IExpr apply(IExpr x) {
                                IExpr temp = getRulesMap(symbolSlots, ast).get(x);
                                return temp != null ? temp : F.NIL;
                            }
                        }).orElse(arg2);
					}
				}
			}
			return F.NIL;
		}

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
		public void setUp(final ISymbol newSymbol) {
			// don't set HOLDALL - the arguments are evaluated before applying the 'function
			// head'
		}
	}

	/**
	 * 
	 * @see Scan
	 */
	private static class Map extends AbstractFunctionEvaluator {
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
					level = new VisitorLevelSpecification(new com.duy.lambda.Function<IExpr, IExpr>() {
                        @Override
                        public IExpr apply(IExpr x) {
                            return F.unaryAST1(arg1, x);
                        }
                    }, ast.get(lastIndex), heads, engine);
				} else {
					level = new VisitorLevelSpecification(new com.duy.lambda.Function<IExpr, IExpr>() {
                        @Override
                        public IExpr apply(IExpr x) {
                            return F.unaryAST1(arg1, x);
                        }
                    }, 1, heads);
				}
				return arg2.accept(level).orElse(arg2);
			} catch (final MathException e) {
				engine.printMessage(e.getMessage());
			}
			return F.NIL;
		}

	}

	private static class MapAll extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 3);

			final IExpr arg1 = ast.arg1();
			final VisitorLevelSpecification level = new VisitorLevelSpecification(new com.duy.lambda.Function<IExpr, IExpr>() {
                @Override
                public IExpr apply(IExpr x) {
                    return F.unaryAST1(arg1, x);
                }
            }, 0,
					Integer.MAX_VALUE, false);

			final IExpr result = ast.arg2().accept(level);
			return result.isPresent() ? result : ast.arg2();
		}

	}

	private static class MapAt extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 4);

			final IExpr arg2 = ast.arg2();
			if (arg2.isAST()) {
				try {
					final IExpr arg3 = ast.arg3();
					if (arg3.isInteger()) {
						final IExpr arg1 = ast.arg1();
						IInteger i3 = (IInteger) arg3;
						int n = i3.toInt();
						return ((IAST) arg2).setAtCopy(n, F.unaryAST1(arg1, ((IAST) arg2).get(n)));
					}
				} catch (RuntimeException ae) {
				}
			}
			return F.NIL;
		}

	}

	private final static class MapThread extends AbstractFunctionEvaluator {

		private static class UnaryMapThread implements com.duy.lambda.Function<IExpr, IExpr> {
			final IExpr fConstant;

			public UnaryMapThread(final IExpr constant) {
				fConstant = constant;
			}

			@Override
			public IExpr apply(final IExpr firstArg) {
				if (firstArg.isAST()) {
					return Thread.threadList((IAST) firstArg, F.List, fConstant).orElse(firstArg);
				}
				return firstArg;
			}

		}

		public MapThread() {
		}

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 3, 4);

			VisitorLevelSpecification level = null;
			com.duy.lambda.Function<IExpr, IExpr> umt = new UnaryMapThread(ast.arg1());
			if (ast.isAST3()) {
				level = new VisitorLevelSpecification(umt, ast.arg3(), false, engine);
			} else {
				level = new VisitorLevelSpecification(umt, 0);
			}
			final IExpr result = ast.arg2().accept(level);
			return result.isPresent() ? result : ast.arg2();
		}

	}

	private final static class OrderedQ extends AbstractFunctionEvaluator implements Predicate<IAST> {
		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 2);
			return F.bool(test(((IAST) ast.arg1())));
		}

		@Override
		public boolean test(IAST ast) {
			return ast.args().compareAdjacent(new BiPredicate<IExpr, IExpr>() {
                @Override
                public boolean test(IExpr x, IExpr y) {
                    return x.isLEOrdered(y);
                }
            });
		}

	}

	private final static class Operate extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 3, 4);

			int headDepth = 1;
			if (ast.isAST3()) {
				if (!ast.arg3().isInteger()) {
					return F.NIL;
				}
				IInteger depth = (IInteger) ast.arg3();
				if (depth.isNegative()) {
					engine.printMessage("Non-negative integer expected at position 3 in Operate()");
					return F.NIL;
				}

				headDepth = depth.toIntDefault(Integer.MIN_VALUE);
				if (headDepth == Integer.MIN_VALUE) {
					return F.NIL;
				}

			}

			IExpr p = ast.arg1();
			IExpr arg2 = ast.arg2();
			if (headDepth == 0) {
				// act like Apply()
				return F.unaryAST1(p, arg2);
			}

			if (!arg2.isAST()) {
				return arg2;
			}

			IExpr expr = arg2;
			for (int i = 1; i < headDepth; i++) {
				expr = expr.head();
				if (!expr.isAST()) {
					// headDepth is higher than the depth of heads in arg2
					// return arg2 unmodified.
					return arg2;
				}
			}

			IAST result = ((IAST) arg2).clone();
			IAST last = result;
			IAST head = result;

			for (int i = 1; i < headDepth; i++) {
				head = ((IAST) head.head()).clone();
				last.set(0, head);
				last = head;
			}

			head.set(0, F.unaryAST1(p, head.head()));
			return result;
		}
	}

	private final static class Quit extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 1);
			return F.Null;
		}
	}

	/**
	 * @see Map
	 */
	private final static class Scan extends Map {

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
					com.duy.lambda.Function<IExpr, IExpr> sf = new com.duy.lambda.Function<IExpr, IExpr>() {
                        @Override
                        public IExpr apply(IExpr x) {
                            IAST a = F.unaryAST1(arg1, x);
                            result.append(a);
                            return F.NIL;
                        }
                    };

					VisitorLevelSpecification level = new VisitorLevelSpecification(sf, ast.get(lastIndex), heads,
							engine);

					arg2.accept(level);
					for (int i = 1; i < result.size(); i++) {
						engine.evaluate(result.get(i));
					}

				} else {
					if (arg2.isAST())
                        engine.evaluate(((IAST) arg2).map(new com.duy.lambda.Function<IExpr, IExpr>() {
                            @Override
                            public IExpr apply(IExpr x) {
                                return F.unaryAST1(arg1, x);
                            }
                        }, 1));
                    else {
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

	private static class Sort extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 2, 3);

			if (ast.arg1().isAST()) {
				final IAST shallowCopy = ((IAST) ast.arg1()).copy();
				if (shallowCopy.size() <= 2) {
					return shallowCopy;
				}
				try {
					if (ast.isAST1()) {
						EvalAttributes.sort(shallowCopy);
					} else {
						// use the 2nd argument as a head for the comparator operation:
						EvalAttributes.sort(shallowCopy, new Predicates.IsBinaryFalse(ast.arg2()));
					}
					return shallowCopy;
				} catch (Exception ex) {

				}
			}

			return F.NIL;
		}
	}

	private static class Symbol extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 2);

			if (ast.arg1().isString()) {
				return F.userSymbol(ast.arg1().toString(), engine);
			}
			return F.NIL;
		}
	}

	private static class SymbolName extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkSize(ast, 2);

			if (ast.arg1().isSymbol()) {
				return F.stringx(ast.arg1().toString());
			}
			return F.NIL;
		}
	}

	private final static class Thread extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 2, 3);

			if (!(ast.arg1().isAST())) {
				return F.NIL;
			}
			// LevelSpec level = null;
			// if (functionList.isAST3()) {
			// level = new LevelSpecification(functionList.arg3());
			// } else {
			// level = new LevelSpec(1);
			// }
			IExpr head = F.List;
			if (ast.isAST2()) {
				head = ast.arg2();
			}
			final IAST list = (IAST) ast.arg1();
			if (list.size() > 1) {
				return threadList(list, head, list.head());
			}
			return F.NIL;
		}

		/**
		 * Thread through all lists in the arguments of the IAST [i.e. the list header
		 * has the attribute ISymbol.LISTABLE] example: Sin[{2,x,Pi}] ==>
		 * {Sin[2],Sin[x],Sin[Pi]}
		 * 
		 * @param list
		 * @param head
		 *            the head over which
		 * @param mapHead
		 *            the arguments head (typically <code>ast.head()</code>)
		 * @return
		 */
		public static IAST threadList(final IAST list, IExpr head, IExpr mapHead) {

			int listLength = 0;

			for (int i = 1; i < list.size(); i++) {
				if ((list.get(i).isAST()) && (((IAST) list.get(i)).head().equals(head))) {
					if (listLength == 0) {
						listLength = ((IAST) list.get(i)).size() - 1;
					} else {
						if (listLength != ((IAST) list.get(i)).size() - 1) {
							listLength = 0;
							return F.NIL;
							// for loop
						}
					}
				}
			}

			return EvalAttributes.threadList(list, head, mapHead, listLength);

		}
	}

	private static class Through extends AbstractFunctionEvaluator {

		@Override
		public IExpr evaluate(final IAST ast, EvalEngine engine) {
			Validate.checkRange(ast, 2, 3);

			if (ast.arg1().isAST()) {
				IAST arg1AST = (IAST) ast.arg1();
				IExpr arg1Head = arg1AST.head();
				if (arg1Head.isAST()) {

					IAST clonedList;
					IAST arg1HeadAST = (IAST) arg1Head;
					if (ast.isAST2() && !arg1HeadAST.head().equals(ast.arg2())) {
						return arg1AST;
					}
					IAST result = F.ast(arg1HeadAST.head());
					for (int i = 1; i < arg1HeadAST.size(); i++) {
						clonedList = arg1AST.apply(arg1HeadAST.get(i));
						result.append(clonedList);
					}
					return result;
				}
				return arg1AST;
			}
			return ast.arg1();
		}
	}

	/**
	 * Maps the elements of the <code>expr</code> with the cloned
	 * <code>replacement</code>. <code>replacement</code> is an IAST where the
	 * argument at the given position will be replaced by the currently mapped
	 * element.
	 * 
	 * 
	 * @param expr
	 * @param replacement
	 *            an IAST there the argument at the given position is replaced by
	 *            the currently mapped argument of this IAST.
	 * @param position
	 * @return
	 */
	public static IAST threadLogicEquationOperators(IExpr expr, IAST replacement, int position) {
		if (expr.isAST()) {
			IAST ast = (IAST) expr;
			if (ast.size() > 1) {
				ISymbol[] logicEquationHeads = { F.And, F.Or, F.Xor, F.Nand, F.Nor, F.Not, F.Implies, F.Equivalent,
						F.Equal, F.Unequal, F.Less, F.Greater, F.LessEqual, F.GreaterEqual };
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
	 * 
	 * @param expr
	 * @param replacement
	 *            an IAST there the argument at the given position is replaced by
	 *            the currently mapped argument of this IAST.
	 * @param position
	 * @return
	 */
	public static IAST threadPlusLogicEquationOperators(IExpr expr, IAST replacement, int position) {
		if (expr.isAST()) {
			IAST ast = (IAST) expr;
			if (ast.size() > 1) {
				ISymbol[] plusLogicEquationHeads = { F.Plus, F.And, F.Or, F.Xor, F.Nand, F.Nor, F.Not, F.Implies,
						F.Equivalent, F.Equal, F.Unequal, F.Less, F.Greater, F.LessEqual, F.GreaterEqual };
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

	final static Structure CONST = new Structure();

	public static Structure initialize() {
		return CONST;
	}

	private Structure() {

	}
}
