package org.matheclipse.core.eval;

import org.matheclipse.core.eval.interfaces.INumeric;
import org.matheclipse.core.eval.interfaces.ISignedNumberConstant;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.Num;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IEvaluator;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.interfaces.IBuiltInSymbol;

public class DoubleStackEvaluator {

	public static double evalSymbol(final ISymbol symbol) {
		if (symbol.hasLocalVariableStack()) {
			return ((ISignedNumber) symbol.get()).doubleValue();
		}
		if (symbol.isSignedNumberConstant()) {
			// fast evaluation path
			return ((ISignedNumberConstant) ((IBuiltInSymbol) symbol).getEvaluator()).evalReal();
		}
		// slow evaluation path
		final IExpr result = F.evaln(symbol);
		if (result instanceof Num) {
			return ((Num) result).doubleValue();
		}
		throw new UnsupportedOperationException("EvalDouble#evalSymbol() - no value assigned for symbol: " + symbol);
	}

	public static double evalAST(double[] stack, final int top, final IAST ast) {
		final ISymbol symbol = (ISymbol) ast.get(0);
		if (symbol.isBuiltInSymbol()) {
			final IEvaluator module = ((IBuiltInSymbol) symbol).getEvaluator();
			if (module instanceof INumeric) {
				int newTop = top;
				// fast evaluation path
				if (top + ast.size() >= stack.length) {
					stack = new double[ast.size() + 50];
				}
				for (int i = 1; i < ast.size(); i++) {
					++newTop;
					stack[newTop] = DoubleStackEvaluator.eval(stack, newTop, ast.get(i));
				}
				return ((INumeric) module).evalReal(stack, newTop, ast.size() - 1);
			}
		}
		// slow evaluation path
		final IExpr result = F.evaln(ast);
		if (result instanceof Num) {
			return ((Num) result).doubleValue();
		}
		throw new UnsupportedOperationException("EvalDouble#evalAST(): " + ast);
	}

	public static double eval(final double[] stack, final int top, final IExpr expr) {
		if (expr instanceof IAST) {
			return evalAST(stack, top, (IAST) expr);
		}
		if (expr instanceof ISignedNumber) {
			return ((ISignedNumber) expr).doubleValue();
		}
		if (expr instanceof ISymbol) {
			return evalSymbol(((ISymbol) expr));
		}
		throw new UnsupportedOperationException("EvalDouble#eval(): " + expr);
	}

}
