package org.matheclipse.core.form.output;

import java.io.IOException;
import java.math.BigInteger;
import java.text.NumberFormat;
import org.apfloat.Apcomplex;
import org.apfloat.Apfloat;
import org.matheclipse.core.basic.Config;
import org.matheclipse.core.builtin.Algebra;
import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.expression.ASTRealMatrix;
import org.matheclipse.core.expression.ASTRealVector;
import org.matheclipse.core.expression.ApcomplexNum;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.expression.Num;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.IPatternObject;
import org.matheclipse.core.interfaces.IRational;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.parser.client.operator.ASTNodeFactory;
import org.matheclipse.parser.client.operator.InfixOperator;
import org.matheclipse.parser.client.operator.Operator;
import org.matheclipse.parser.client.operator.PostfixOperator;
import org.matheclipse.parser.client.operator.PrefixOperator;

/**
 * Converts an internal <code>IExpr</code> into a user readable string.
 * 
 */
public class OutputFormFactory {
	/**
	 * The conversion wasn't called with an operator preceding the <code>IExpr</code> object.
	 */
	public final static boolean NO_PLUS_CALL = false;

	/**
	 * The conversion was called with a &quot;+&quot; operator preceding the <code>IExpr</code> object.
	 */
	public final static boolean PLUS_CALL = true;

	private final boolean fRelaxedSyntax;
	private final boolean fPlusReversed;
	private boolean fIgnoreNewLine = false;
	private boolean fEmpty = true;
	private int fColumnCounter;
	private NumberFormat fNumberFormat = null;

	private OutputFormFactory(final boolean relaxedSyntax, final boolean reversed, NumberFormat numberFormat) {
		fRelaxedSyntax = relaxedSyntax;
		fPlusReversed = reversed;
		fNumberFormat = numberFormat;
	}

	public void reset() {
		fColumnCounter = 0;
	}

	/**
	 * Get an <code>OutputFormFactory</code> for converting an internal expression to a user readable string.
	 * 
	 * @param relaxedSyntax
	 *            If <code>true</code> use paranthesis instead of square brackets and ignore case for functions, i.e.
	 *            sin() instead of Sin[]. If <code>true</code> use single square brackets instead of double square
	 *            brackets for extracting parts of an expression, i.e. {a,b,c,d}[1] instead of {a,b,c,d}[[1]].
	 * @return
	 */
	public static OutputFormFactory get(final boolean relaxedSyntax) {
		return get(relaxedSyntax, false);
	}

	/**
	 * Get an <code>OutputFormFactory</code> for converting an internal expression to a user readable string.
	 * 
	 * @param relaxedSyntax
	 *            if <code>true</code> use paranthesis instead of square brackets and ignore case for functions, i.e.
	 *            sin() instead of Sin[]. If <code>true</code> use single square brackets instead of double square
	 *            brackets for extracting parts of an expression, i.e. {a,b,c,d}[1] instead of {a,b,c,d}[[1]].
	 * @param plusReversed
	 *            if <code>true</code> the arguments of the <code>Plus()</code> function will be printed in reversed
	 *            order
	 * @return
	 */
	public static OutputFormFactory get(final boolean relaxedSyntax, final boolean plusReversed) {
		return get(relaxedSyntax, plusReversed, null);
	}

	/**
	 * Get an <code>OutputFormFactory</code> for converting an internal expression to a user readable string.
	 * 
	 * @param relaxedSyntax
	 *            if <code>true</code> use paranthesis instead of square brackets and ignore case for functions, i.e.
	 *            sin() instead of Sin[]. If <code>true</code> use single square brackets instead of double square
	 *            brackets for extracting parts of an expression, i.e. {a,b,c,d}[1] instead of {a,b,c,d}[[1]].
	 * @param plusReversed
	 *            if <code>true</code> the arguments of the <code>Plus()</code> function will be printed in reversed
	 *            order
	 * @param numberFormat
	 *            define the decimal format output for double values
	 * @return
	 */
	public static OutputFormFactory get(final boolean relaxedSyntax, final boolean plusReversed,
			NumberFormat numberFormat) {
		return new OutputFormFactory(relaxedSyntax, plusReversed, numberFormat);
	}

	/**
	 * Get an <code>OutputFormFactory</code> for converting an internal expression to a user readable string, with
	 * <code>relaxedSyntax</code> set to false.
	 * 
	 * @return
	 * @see #get(boolean)
	 */
	public static OutputFormFactory get() {
		return get(false);
	}

	public void convertDouble(final Appendable buf, final INum d, final int precedence, boolean caller)
			throws IOException {

		if (d.isZero()) {
			convertDoubleString(buf, convertDoubleToFormattedString(0.0), precedence, false);
			return;
		}
		final boolean isNegative = d.isNegative();
		if (!isNegative && caller == PLUS_CALL) {
			append(buf, "+");
		}
		if (d instanceof Num) {
			double dValue = d.doubleValue();
			convertDoubleString(buf, convertDoubleToFormattedString(dValue), precedence, isNegative);
		} else {
			convertDoubleString(buf, d.toString(), precedence, isNegative);
		}
	}

	private String convertDoubleToFormattedString(double dValue) {
		return fNumberFormat == null ? Double.toString(dValue) : fNumberFormat.format(dValue);
	}

	private void convertDoubleString(final Appendable buf, final String d, final int precedence,
			final boolean isNegative) throws IOException {
		if (isNegative && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, "(");
		}
		append(buf, d);
		if (isNegative && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, ")");
		}
	}

	public void convertDoubleComplex(final Appendable buf, final IComplexNum dc, final int precedence, boolean caller)
			throws IOException {
		if (dc instanceof ApcomplexNum) {
			convertApcomplex(buf, ((ApcomplexNum) dc).apcomplexValue(), precedence, caller);
			return;
		}
		if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
			append(buf, "(");
		}
		double realPart = dc.getRealPart();
		double imaginaryPart = dc.getImaginaryPart();
		boolean realZero = F.isZero(realPart);
		boolean imaginaryZero = F.isZero(imaginaryPart);
		if (realZero && imaginaryZero) {
			convertDoubleString(buf, convertDoubleToFormattedString(0.0), ASTNodeFactory.PLUS_PRECEDENCE, false);
		} else {
			if (!realZero) {
				append(buf, convertDoubleToFormattedString(realPart));
				if (!imaginaryZero) {
					append(buf, "+I*");
					final boolean isNegative = imaginaryPart < 0;
					convertDoubleString(buf, convertDoubleToFormattedString(imaginaryPart),
							ASTNodeFactory.TIMES_PRECEDENCE, isNegative);
				}
			} else {
				if (caller == PLUS_CALL) {
					append(buf, "+");
				}
				append(buf, "I*");
				final boolean isNegative = imaginaryPart < 0;
				convertDoubleString(buf, convertDoubleToFormattedString(imaginaryPart), ASTNodeFactory.TIMES_PRECEDENCE,
						isNegative);
			}
		}
		if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
			append(buf, ")");
		}
	}

	public void convertApcomplex(final Appendable buf, final Apcomplex dc, final int precedence, boolean caller)
			throws IOException {
		if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
			append(buf, "(");
		}
		Apfloat realPart = dc.real();
		Apfloat imaginaryPart = dc.imag();
		boolean realZero = realPart.equals(Apcomplex.ZERO);
		boolean imaginaryZero = imaginaryPart.equals(Apcomplex.ZERO);
		if (realZero && imaginaryZero) {
			convertDoubleString(buf, "0.0", ASTNodeFactory.PLUS_PRECEDENCE, false);
		} else {
			if (!realZero) {
				append(buf, String.valueOf(realPart));
				if (!imaginaryZero) {
					append(buf, "+I*");
					final boolean isNegative = imaginaryPart.compareTo(Apcomplex.ZERO) < 0;
					convertDoubleString(buf, String.valueOf(imaginaryPart), ASTNodeFactory.TIMES_PRECEDENCE,
							isNegative);
				}
			} else {
				if (caller == PLUS_CALL) {
					append(buf, "+");
				}
				append(buf, "I*");
				final boolean isNegative = imaginaryPart.compareTo(Apcomplex.ZERO) < 0;
				convertDoubleString(buf, String.valueOf(imaginaryPart), ASTNodeFactory.TIMES_PRECEDENCE, isNegative);
			}
		}
		if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
			append(buf, ")");
		}
	}

	public void convertInteger(final Appendable buf, final IInteger i, final int precedence, boolean caller)
			throws IOException {
		final boolean isNegative = i.isNegative();
		if (!isNegative && caller == PLUS_CALL) {
			append(buf, "+");
		}
		if (isNegative && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, "(");
		}
		final String str = i.toBigNumerator().toString();
		if ((str.length() + getColumnCounter() > 80)) {
			if (getColumnCounter() > 40) {
				newLine(buf);
			}
			final int len = str.length();
			for (int j = 0; j < len; j += 79) {
				if (j + 79 < len) {
					append(buf, str.substring(j, j + 79));
					append(buf, '\\');
					newLine(buf);
				} else {
					append(buf, str.substring(j, len));
				}
			}
		} else {
			append(buf, str);
		}
		if (isNegative && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, ")");
		}
	}

	public void convertFraction(final Appendable buf, final IRational f, final int precedence, boolean caller)
			throws IOException {
		convertFraction(buf, f.toBigNumerator(), f.toBigDenominator(), precedence, caller);
	}

	public void convertFraction(final Appendable buf, final BigInteger numerator, BigInteger denominator,
			final int precedence, boolean caller) throws IOException {
		boolean isInteger = denominator.compareTo(BigInteger.ONE) == 0;
		final boolean isNegative = numerator.compareTo(BigInteger.ZERO) < 0;
		final int prec = isNegative ? ASTNodeFactory.PLUS_PRECEDENCE : ASTNodeFactory.TIMES_PRECEDENCE;
		if (!isNegative) {
			if (caller == PLUS_CALL) {
				append(buf, "+");
			}
		}

		if (prec < precedence) {
			append(buf, "(");
		}

		String str = numerator.toString();
		if ((str.length() + getColumnCounter() > 80)) {
			if (getColumnCounter() > 40) {
				newLine(buf);
			}
			final int len = str.length();
			for (int j = 0; j < len; j += 79) {
				if (j + 79 < len) {
					append(buf, str.substring(j, j + 79));
					append(buf, '\\');
					newLine(buf);
				} else {
					append(buf, str.substring(j, len));
				}
			}
		} else {
			append(buf, str);
		}
		if (!isInteger) {
			append(buf, "/");
			str = denominator.toString();
			if ((str.length() + getColumnCounter() > 80)) {
				if (getColumnCounter() > 40) {
					newLine(buf);
				}
				final int len = str.length();
				for (int j = 0; j < len; j += 79) {
					if (j + 79 < len) {
						append(buf, str.substring(j, j + 79));
						append(buf, '\\');
						newLine(buf);
					} else {
						append(buf, str.substring(j, len));
					}
				}
			} else {
				append(buf, str);
			}
		}
		if (prec < precedence) {
			append(buf, ")");
		}
	}

	public void convertComplex(final Appendable buf, final IComplex c, final int precedence, boolean caller)
			throws IOException {
		boolean isReZero = c.getRealPart().isZero();
		final boolean isImOne = c.getImaginaryPart().isOne();
		final boolean isImMinusOne = c.getImaginaryPart().isMinusOne();
		if (!isReZero && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, "(");
		}
		if (!isReZero) {
			convertFraction(buf, c.getRealPart(), ASTNodeFactory.PLUS_PRECEDENCE, caller);
		}
		if (isImOne) {
			if (isReZero) {
				if (caller == PLUS_CALL) {
					append(buf, "+");
				}
				append(buf, "I");
				return;
			} else {
				append(buf, "+I");
			}
		} else if (isImMinusOne) {
			append(buf, "-I");
		} else {
			if (isReZero && (ASTNodeFactory.TIMES_PRECEDENCE < precedence)) {
				append(buf, "(");
			}
			final IRational im = c.getImaginaryPart();
			if (im.isNegative()) {
				append(buf, "-I*");
				convertFraction(buf, c.getImaginaryPart().negate(), ASTNodeFactory.TIMES_PRECEDENCE, NO_PLUS_CALL);
			} else {
				if (isReZero) {
					if (caller == PLUS_CALL) {
						append(buf, "+");
					}
					append(buf, "I*");
				} else {
					append(buf, "+I*");
				}
				convertFraction(buf, c.getImaginaryPart(), ASTNodeFactory.TIMES_PRECEDENCE, NO_PLUS_CALL);
			}
			if (isReZero && (ASTNodeFactory.TIMES_PRECEDENCE < precedence)) {
				append(buf, ")");
			}
		}

		if (!isReZero && (ASTNodeFactory.PLUS_PRECEDENCE < precedence)) {
			append(buf, ")");
		}
	}

	public void convertString(final Appendable buf, final String str) throws IOException {
		append(buf, "\"");
		append(buf, str);
		append(buf, "\"");
	}

	public void convertSymbol(final Appendable buf, final ISymbol symbol) throws IOException {
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			String str = AST2Expr.PREDEFINED_SYMBOLS_MAP.get(symbol.getSymbolName());
			if (str != null) {
				append(buf, str);
				return;
			}
		}
		append(buf, symbol.getSymbolName());
	}

	public void convertPattern(final Appendable buf, final IPatternObject pattern) throws IOException {
		append(buf, pattern.toString());
	}

	public void convertHead(final Appendable buf, final IExpr obj) throws IOException {
		convert(buf, obj);
	}

	private void convertPlusOperator(final Appendable buf, final IAST plusAST, final InfixOperator oper,
			final int precedence) throws IOException {
		int operPrecedence = oper.getPrecedence();
		if (operPrecedence < precedence) {
			append(buf, "(");
		}

		IExpr plusArg;
		int size = plusAST.size();
		if (size > 0) {
			convertPlusArgument(buf, plusAST.arg1(), NO_PLUS_CALL);
			for (int i = 2; i < size; i++) {
				plusArg = plusAST.get(i);
				convertPlusArgument(buf, plusArg, PLUS_CALL);
			}
		}
		if (operPrecedence < precedence) {
			append(buf, ")");
		}
	}

	public void convertPlusArgument(final Appendable buf, IExpr plusArg, boolean caller) throws IOException {
		if (plusArg.isTimes()) {
			final IAST timesAST = (IAST) plusArg;
			// IExpr arg1 = timesAST.arg1();
			final InfixOperator TIMES_OPERATOR = (InfixOperator) ASTNodeFactory.MMA_STYLE_FACTORY.get("Times");
			convertTimesFraction(buf, timesAST, TIMES_OPERATOR, ASTNodeFactory.TIMES_PRECEDENCE, caller);
		} else {
			if (plusArg.isNegativeSigned()) {
				// special case negative number or -Infinity...
				convert(buf, plusArg);
			} else {
				if (caller == PLUS_CALL) {
					append(buf, "+");
				}
				convert(buf, plusArg, ASTNodeFactory.PLUS_PRECEDENCE, false);
			}

		}
	}

	private void convertPlusOperatorReversed(final Appendable buf, final IAST plusAST, final InfixOperator oper,
			final int precedence) throws IOException {
		int operPrecedence = oper.getPrecedence();
		if (operPrecedence < precedence) {
			append(buf, "(");
		}

		String operatorStr = oper.getOperatorString();

		IExpr plusArg;
		int size = plusAST.size() - 1;
		// print Plus[] in reverse order (i.e. numbers at last)
		for (int i = size; i > 0; i--) {
			plusArg = plusAST.get(i);

			if (plusArg.isTimes()) {
				final String multCh = ASTNodeFactory.MMA_STYLE_FACTORY.get("Times").getOperatorString();
				boolean showOperator = true;
				final IAST timesAST = (IAST) plusArg;
				IExpr arg1 = timesAST.arg1();

				if (arg1.isNumber() && (((INumber) arg1).complexSign() < 0)) {
					if (((INumber) arg1).isOne()) {
						showOperator = false;
					} else {
						if (arg1.isMinusOne()) {
							append(buf, "-");
							showOperator = false;
						} else {
							convertNumber(buf, (INumber) arg1, operPrecedence, NO_PLUS_CALL);
						}
					}
				} else {
					if (i < size) {
						append(buf, operatorStr);
					}
					convert(buf, arg1, ASTNodeFactory.TIMES_PRECEDENCE, false);
				}

				IExpr timesArg;
				for (int j = 2; j < timesAST.size(); j++) {
					timesArg = timesAST.get(j);

					if (showOperator) {
						append(buf, multCh);
					} else {
						showOperator = true;
					}

					convert(buf, timesArg, ASTNodeFactory.TIMES_PRECEDENCE, false);

				}
			} else {
				if (plusArg.isNumber() && (((INumber) plusArg).complexSign() < 0)) {
					// special case negative number:
					convert(buf, plusArg);
				} else {
					if (i < size) {
						append(buf, operatorStr);
					}

					convert(buf, plusArg, ASTNodeFactory.PLUS_PRECEDENCE, false);

				}
			}
		}

		if (operPrecedence < precedence) {
			append(buf, ")");
		}
	}

	private void convertTimesFraction(final Appendable buf, final IAST timesAST, final InfixOperator oper,
			final int precedence, boolean caller) throws IOException {
		IExpr[] parts = Algebra.fractionalPartsTimesPower(timesAST, true, false, false, false);
		if (parts == null) {
			convertTimesOperator(buf, timesAST, oper, precedence, caller);
			return;
		}
		final IExpr numerator = parts[0];
		final IExpr denominator = parts[1];
		if (!denominator.isOne()) {
			int currPrecedence = oper.getPrecedence();
			if (currPrecedence < precedence) {
				append(buf, "(");
			}
			final IExpr fraction = parts[2];
			if (fraction != null) {
				convertNumber(buf, (ISignedNumber) fraction, ASTNodeFactory.PLUS_PRECEDENCE, caller);
				append(buf, "*");
				caller = NO_PLUS_CALL;
			}
			if (numerator.isSignedNumber()) {
				convertNumber(buf, (ISignedNumber) numerator, ASTNodeFactory.PLUS_PRECEDENCE, caller);
			} else if (numerator.isComplex() || numerator.isComplexNumeric()) {
				convertNumber(buf, (INumber) numerator, ASTNodeFactory.DIVIDE_PRECEDENCE, caller);
			} else {
				if (numerator.isTimes() && numerator.isAST2() && ((IAST) numerator).arg1().isMinusOne()) {
					append(buf, "-");
					convert(buf, ((IAST) numerator).arg2(), ASTNodeFactory.TIMES_PRECEDENCE, false);
				} else {
					if (caller == PLUS_CALL) {
						append(buf, "+");
					}
					// insert numerator in buffer:
					if (numerator.isTimes()) {
						convertTimesOperator(buf, (IAST) numerator, oper, ASTNodeFactory.DIVIDE_PRECEDENCE,
								NO_PLUS_CALL);
					} else {
						convert(buf, numerator, ASTNodeFactory.DIVIDE_PRECEDENCE, false);
					}
				}
			}
			append(buf, "/");
			// insert denominator in buffer:
			if (denominator.isTimes()) {
				convertTimesOperator(buf, (IAST) denominator, oper, ASTNodeFactory.DIVIDE_PRECEDENCE, NO_PLUS_CALL);
			} else {
				convert(buf, denominator, ASTNodeFactory.DIVIDE_PRECEDENCE, false);
			}
			if (currPrecedence < precedence) {
				append(buf, ")");
			}
			return;
		}
		convertTimesOperator(buf, timesAST, oper, precedence, caller);
	}

	private void convertTimesOperator(final Appendable buf, final IAST timesAST, final InfixOperator oper,
			final int precedence, boolean caller) throws IOException {
		boolean showOperator = true;
		int currPrecedence = oper.getPrecedence();
		if (currPrecedence < precedence) {
			append(buf, "(");
		}

		if (timesAST.size() > 1) {
			IExpr arg1 = timesAST.arg1();
			if (arg1.isSignedNumber() && timesAST.size() > 2 && !timesAST.arg2().isNumber()) {
				if (arg1.isMinusOne()) {
					append(buf, "-");
					showOperator = false;
				} else {
					convertNumber(buf, (ISignedNumber) arg1, ASTNodeFactory.PLUS_PRECEDENCE, caller);
				}
			} else if (arg1.isComplex() && timesAST.size() > 2) {
				convertComplex(buf, (IComplex) arg1, oper.getPrecedence(), caller);
			} else {
				if (caller == PLUS_CALL) {
					append(buf, "+");
				}
				convert(buf, arg1, oper.getPrecedence(), false);
			}
		}
		for (int i = 2; i < timesAST.size(); i++) {
			if (showOperator) {
				append(buf, oper.getOperatorString());
			} else {
				showOperator = true;
			}
			convert(buf, timesAST.get(i), oper.getPrecedence(), false);
		}
		if (currPrecedence < precedence) {
			append(buf, ")");
		}
	}

	public void convertApplyOperator(final Appendable buf, final IAST list, final InfixOperator oper,
			final int precedence) throws IOException {
		IExpr arg2 = list.arg2();
		if (arg2.isNumber()) {
			INumber exp = (INumber) arg2;
			if (exp.complexSign() < 0) {
				if (ASTNodeFactory.APPLY_PRECEDENCE < precedence) {
					append(buf, "(");
				}
				append(buf, "1/");
				if (exp.isMinusOne()) {
					convert(buf, list.arg1(), ASTNodeFactory.DIVIDE_PRECEDENCE, false);
					if (ASTNodeFactory.DIVIDE_PRECEDENCE < precedence) {
						append(buf, ")");
					}
					return;
				}
				// flip presign of the exponent
				IAST pow = list.setAtCopy(2, exp.opposite());
				convertPowerOperator(buf, pow, oper, ASTNodeFactory.DIVIDE_PRECEDENCE);
				if (ASTNodeFactory.APPLY_PRECEDENCE < precedence) {
					append(buf, ")");
				}
				return;
			}
		}
		convertInfixOperator(buf, list, oper, precedence);
	}

	public void convertPowerOperator(final Appendable buf, final IAST list, final InfixOperator oper,
			final int precedence) throws IOException {
		IExpr arg2 = list.arg2();
		if (arg2.isNumber()) {
			INumber exp = (INumber) arg2;
			if (exp.isNumEqualRational(F.C1D2)) {
				append(buf, "Sqrt");
				if (fRelaxedSyntax) {
					append(buf, "(");
				} else {
					append(buf, "[");
				}
				convert(buf, list.arg1(), 0, false);
				if (fRelaxedSyntax) {
					append(buf, ")");
				} else {
					append(buf, "]");
				}
				return;
			}
			if (exp.complexSign() < 0) {
				if (ASTNodeFactory.DIVIDE_PRECEDENCE < precedence) {
					append(buf, "(");
				}
				append(buf, "1/");
				if (exp.isMinusOne()) {
					convert(buf, list.arg1(), ASTNodeFactory.DIVIDE_PRECEDENCE, false);
					if (ASTNodeFactory.DIVIDE_PRECEDENCE < precedence) {
						append(buf, ")");
					}
					return;
				}
				// flip presign of the exponent
				IAST pow = list.setAtCopy(2, exp.opposite());
				convertPowerOperator(buf, pow, oper, ASTNodeFactory.DIVIDE_PRECEDENCE);
				if (ASTNodeFactory.DIVIDE_PRECEDENCE < precedence) {
					append(buf, ")");
				}
				return;
			}
		}
		convertInfixOperator(buf, list, oper, precedence);
	}

	public void convertInfixOperator(final Appendable buf, final IAST list, final InfixOperator oper,
			final int precedence) throws IOException {

		if (list.isAST2()) {
			if (oper.getPrecedence() < precedence) {
				append(buf, "(");
			}
			if (oper.getGrouping() == InfixOperator.RIGHT_ASSOCIATIVE && list.arg1().head().equals(list.head())) {
				append(buf, "(");
			} else {
				if (oper.getOperatorString() == "^") {
					final Operator operator = getOperator(list.arg1().topHead());
					if (operator instanceof PostfixOperator) {
						append(buf, "(");
					}
				}
			}
			convert(buf, list.arg1(), oper.getPrecedence(), false);
			if (oper.getGrouping() == InfixOperator.RIGHT_ASSOCIATIVE && list.arg1().head().equals(list.head())) {
				append(buf, ")");
			} else {
				if (oper.getOperatorString() == "^") {
					final Operator operator = getOperator(list.arg1().topHead());
					if (operator instanceof PostfixOperator) {
						append(buf, ")");
					}
				}
			}

			append(buf, oper.getOperatorString());

			if (oper.getGrouping() == InfixOperator.LEFT_ASSOCIATIVE && list.arg2().head().equals(list.head())) {
				append(buf, "(");
			}
			convert(buf, list.arg2(), oper.getPrecedence(), false);
			if (oper.getGrouping() == InfixOperator.LEFT_ASSOCIATIVE && list.arg2().head().equals(list.head())) {
				append(buf, ")");
			}

			if (oper.getPrecedence() < precedence) {
				append(buf, ")");
			}
			return;
		}

		if (oper.getPrecedence() < precedence) {
			append(buf, "(");
		}
		if (list.size() > 1) {
			convert(buf, list.arg1(), oper.getPrecedence(), false);
		}

		for (int i = 2; i < list.size(); i++) {
			append(buf, oper.getOperatorString());
			convert(buf, list.get(i), oper.getPrecedence(), false);
		}
		if (oper.getPrecedence() < precedence) {
			append(buf, ")");
		}
	}

	public void convertPrefixOperator(final Appendable buf, final IAST list, final PrefixOperator oper,
			final int precedence) throws IOException {
		if (oper.getPrecedence() < precedence) {
			append(buf, "(");
		}
		append(buf, oper.getOperatorString());
		convert(buf, list.arg1(), oper.getPrecedence(), false);
		if (oper.getPrecedence() < precedence) {
			append(buf, ")");
		}
	}

	public void convertPostfixOperator(final Appendable buf, final IAST list, final PostfixOperator oper,
			final int precedence) throws IOException {
		if (oper.getPrecedence() < precedence) {
			append(buf, "(");
		}
		convert(buf, list.arg1(), oper.getPrecedence(), false);
		append(buf, oper.getOperatorString());
		if (oper.getPrecedence() < precedence) {
			append(buf, ")");
		}
	}

	public void convert(final Appendable buf, final IExpr o) throws IOException {
		convert(buf, o, Integer.MIN_VALUE, false);
	}

	private void convertNumber(final Appendable buf, final INumber o, final int precedence, boolean caller)
			throws IOException {
		if (o instanceof INum) {
			convertDouble(buf, (INum) o, precedence, caller);
			return;
		}
		if (o instanceof IComplexNum) {
			convertDoubleComplex(buf, (IComplexNum) o, precedence, caller);
			return;
		}
		if (o instanceof IInteger) {
			convertInteger(buf, (IInteger) o, precedence, caller);
			return;
		}
		if (o instanceof IFraction) {
			convertFraction(buf, (IFraction) o, precedence, caller);
			return;
		}
		if (o instanceof IComplex) {
			convertComplex(buf, (IComplex) o, precedence, caller);
			return;
		}
	}

	private void convert(final Appendable buf, final IExpr o, final int precedence, boolean isASTHead)
			throws IOException {
		if (o instanceof IAST) {
			final IAST list = (IAST) o;
			IExpr header = list.head();
			if (!header.isSymbol()) {
				// print expressions like: f(#1, y)& [x]

				IAST[] derivStruct = list.isDerivativeAST1();
				if (derivStruct != null) {
					IAST a1Head = derivStruct[0];
					IAST headAST = derivStruct[1];
					if (a1Head.isAST1() && a1Head.arg1().isInteger() && headAST.isAST1()
							&& (headAST.arg1().isSymbol() || headAST.arg1().isAST()) && derivStruct[2] != null) {
						try {
							int n = ((IInteger) a1Head.arg1()).toInt();
							if (n == 1 || n == 2) {
								IExpr symbolOrAST = headAST.arg1();
								convert(buf, symbolOrAST);
								if (n == 1) {
									append(buf, "'");
								} else if (n == 2) {
									append(buf, "''");
								}
								convertArgs(buf, symbolOrAST, list);
								return;
							}
						} catch (ArithmeticException ae) {

						}
					}
				}

				convert(buf, header, Integer.MIN_VALUE, true);
				convertFunctionArgs(buf, list);
				return;
			}
			ISymbol head = list.topHead();
			final Operator operator = getOperator(head);
			if (operator != null) {
				if (operator instanceof PostfixOperator) {
					if (list.isAST1()) {
						convertPostfixOperator(buf, list, (PostfixOperator) operator, precedence);
						return;
					}
				} else {
					if (convertOperator(operator, list, buf, isASTHead ? Integer.MAX_VALUE : precedence, head)) {
						return;
					}
				}
			}
			if (list.isList() || list instanceof ASTRealVector || list instanceof ASTRealMatrix) {
				convertList(buf, list);
				return;
			}
			if (head.equals(F.Part) && (list.size() >= 3)) {
				convertPart(buf, list);
				return;
			}
			if (head.equals(F.Slot) && (list.isAST1()) && (list.arg1() instanceof IInteger)) {
				convertSlot(buf, list);
				return;
			}
			if (head.equals(F.SlotSequence) && (list.isAST1()) && (list.arg1() instanceof IInteger)) {
				convertSlotSequence(buf, list);
				return;
			}
			if ((head.equals(F.HoldForm) || head.equals(F.Defer)) && (list.isAST1())) {
				convert(buf, list.arg1());
				return;
			}
			if (head.equals(F.SeriesData) && (list.size() == 7)) {
				if (convertSeriesData(buf, list, precedence)) {
					return;
				}
			}
			if (list.isDirectedInfinity()) { // head.equals(F.DirectedInfinity))
												// {
				if (list.isAST0()) {
					append(buf, "ComplexInfinity");
					return;
				}
				if (list.isAST1()) {
					if (list.arg1().isOne()) {
						append(buf, "Infinity");
						return;
					} else if (list.arg1().isMinusOne()) {
						if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
							append(buf, "(");
						}
						append(buf, "-Infinity");
						if (ASTNodeFactory.PLUS_PRECEDENCE < precedence) {
							append(buf, ")");
						}
						return;
					} else if (list.arg1().isImaginaryUnit()) {
						append(buf, "I*Infinity");
						return;
					} else if (list.arg1().isNegativeImaginaryUnit()) {
						append(buf, "-I*Infinity");
						return;
					}
				}
			}
			convertAST(buf, list);
			return;
		}
		if (o instanceof ISignedNumber) {
			convertNumber(buf, (ISignedNumber) o, precedence, NO_PLUS_CALL);
			return;
		}
		if (o instanceof IComplexNum) {
			convertDoubleComplex(buf, (IComplexNum) o, precedence, NO_PLUS_CALL);
			return;
		}
		if (o instanceof IComplex) {
			convertComplex(buf, (IComplex) o, precedence, NO_PLUS_CALL);
			return;
		}
		if (o instanceof ISymbol) {
			convertSymbol(buf, (ISymbol) o);
			return;
		}
		if (o instanceof IPatternObject) {
			convertPattern(buf, (IPatternObject) o);
			return;
		}
		convertString(buf, o.toString());
	}

	private boolean convertOperator(final Operator operator, final IAST list, final Appendable buf,
			final int precedence, ISymbol head) throws IOException {
		if ((operator instanceof PrefixOperator) && (list.isAST1())) {
			convertPrefixOperator(buf, list, (PrefixOperator) operator, precedence);
			return true;
		}
		if ((operator instanceof InfixOperator) && (list.size() > 2)) {
			InfixOperator infixOperator = (InfixOperator) operator;
			if (head.equals(F.Plus)) {
				if (fPlusReversed) {
					convertPlusOperatorReversed(buf, list, infixOperator, precedence);
				} else {
					convertPlusOperator(buf, list, infixOperator, precedence);
				}
				return true;
			} else if (head.equals(F.Times)) {
				convertTimesFraction(buf, list, infixOperator, precedence, NO_PLUS_CALL);
				return true;
			} else if (list.isPower()) {
				convertPowerOperator(buf, list, infixOperator, precedence);
				return true;
			} else if (list.isAST(F.Apply)) {
				if (list.size() == 3) {
					convertInfixOperator(buf, list, ASTNodeFactory.APPLY_OPERATOR, precedence);
					return true;
				}
				if (list.size() == 4 && list.get(2).equals(F.List(F.C1))) {
					convertInfixOperator(buf, list, ASTNodeFactory.APPLY_LEVEL_OPERATOR, precedence);
					return true;
				}
				return false;
			} else if (list.size() != 3 && infixOperator.getGrouping() != InfixOperator.NONE) {
				return false;
			}
			convertInfixOperator(buf, list, (InfixOperator) operator, precedence);
			return true;
		}
		if ((operator instanceof PostfixOperator) && (list.isAST1())) {
			convertPostfixOperator(buf, list, (PostfixOperator) operator, precedence);
			return true;
		}
		return false;
	}

	public static Operator getOperator(ISymbol head) {
		String headerStr = head.getSymbolName();
		if (Config.PARSER_USE_LOWERCASE_SYMBOLS) {
			String str = AST2Expr.PREDEFINED_SYMBOLS_MAP.get(headerStr);
			if (str != null) {
				headerStr = str;
			}
		}
		final Operator operator = ASTNodeFactory.MMA_STYLE_FACTORY.get(headerStr);
		return operator;
	}

	public void convertSlot(final Appendable buf, final IAST list) throws IOException {
		try {
			final int slot = ((ISignedNumber) list.arg1()).toInt();
			append(buf, "#" + slot);
		} catch (final ArithmeticException e) {
			// add message to evaluation problemReporter
		}
	}

	public void convertSlotSequence(final Appendable buf, final IAST list) throws IOException {
		try {
			final int slotSequenceStartPosition = ((ISignedNumber) list.arg1()).toInt();
			append(buf, "##" + slotSequenceStartPosition);
		} catch (final ArithmeticException e) {
			// add message to evaluation problemReporter
		}
	}

	public void convertList(final Appendable buf, final IAST list) throws IOException {
		if (list instanceof ASTRealVector) {
			((ASTRealVector) list).toString(buf);
			return;
		}
		if (list instanceof ASTRealMatrix) {
			((ASTRealMatrix) list).toString(buf, fEmpty);
			fColumnCounter = 1;
			fEmpty = false;
			return;
		}
		if (list.isEvalFlagOn(IAST.IS_MATRIX)) {
			if (!fEmpty) {
				newLine(buf);
			}
		}
		append(buf, "{");
		final int listSize = list.size();
		if (listSize > 1) {
			convert(buf, list.arg1());
		}
		for (int i = 2; i < listSize; i++) {
			append(buf, ",");
			if (list.isEvalFlagOn(IAST.IS_MATRIX)) {
				newLine(buf);
				append(buf, ' ');

			}
			convert(buf, list.get(i));
		}
		append(buf, "}");
	}

	/**
	 * This method will only be called if <code>list.isAST2()==true</code> and the head equals "Part".
	 * 
	 * @param buf
	 * @param list
	 * @throws IOException
	 */
	public void convertPart(final Appendable buf, final IAST list) throws IOException {
		IExpr arg1 = list.arg1();
		if (!(arg1 instanceof IAST)) {
			append(buf, "(");
		}
		convert(buf, arg1);
		append(buf, "[[");

		for (int i = 2; i < list.size(); i++) {
			convert(buf, list.get(i));
			if (i < list.size() - 1) {
				append(buf, ",");
			}
		}

		append(buf, "]]");
		if (!(arg1 instanceof IAST)) {
			append(buf, ")");
		}
	}

	/**
	 * Convert a <code>SeriesData[...]</code> expression.
	 * 
	 * @param buf
	 * @param seriesData
	 *            <code>SeriesData[x, x0, list, nmin, nmax, den]</code> expression
	 * @param precedence
	 *            the precedence of the parent expression
	 * @return <code>true</code> if the conversion was successful
	 * @throws IOException
	 */
	public boolean convertSeriesData(final Appendable buf, final IAST seriesData, final int precedence)
			throws IOException {
		int operPrecedence = ASTNodeFactory.PLUS_PRECEDENCE;
		StringBuilder tempBuffer = new StringBuilder();
		if (operPrecedence < precedence) {
			append(tempBuffer, "(");
		}

		try {
			IExpr plusArg;
			// SeriesData[x, x0, list, nmin, nmax, den]
			IExpr x = seriesData.arg1();
			IExpr x0 = seriesData.arg2();
			IAST list = (IAST) seriesData.arg3();
			long nmin = ((IInteger) seriesData.arg4()).toLong();
			long nmax = ((IInteger) seriesData.arg5()).toLong();
			long den = ((IInteger) seriesData.get(6)).toLong();
			int size = list.size();
			boolean call = NO_PLUS_CALL;
			if (size > 0) {
				INumber exp = F.fraction(nmin, den).normalize();
				IExpr pow = x.subtract(x0).power(exp);
				call = convertSeriesDataArg(tempBuffer, list.arg1(), pow, call);
				for (int i = 2; i < size; i++) {
					exp = F.fraction(nmin + i - 1L, den).normalize();
					pow = x.subtract(x0).power(exp);
					call = convertSeriesDataArg(tempBuffer, list.get(i), pow, call);
				} 
				plusArg = F.Power(F.O(x.subtract(x0)), F.fraction(nmax, den).normalize());
				if (!plusArg.isZero()) {
					convertPlusArgument(tempBuffer, plusArg, call);
					call = PLUS_CALL;
				}
			} else {
				return false;
			}
		} catch (Exception ex) {
			return false;
		}
		if (operPrecedence < precedence) {
			append(tempBuffer, ")");
		}
		buf.append(tempBuffer);
		return true;

	}

	/**
	 * Convert a factor of a <code>SeriesData</code> object.
	 * 
	 * @param buf
	 * @param coefficient
	 *            the coefficient expression of the factor
	 * @param pow
	 *            the power expression of the factor
	 * @param call
	 * @param operPrecedence
	 * @return the current call status
	 * @throws IOException
	 */
	private boolean convertSeriesDataArg(StringBuilder buf, IExpr coefficient, IExpr pow, boolean call)
			throws IOException {
		IExpr plusArg;
		if (coefficient.isZero()) {
			plusArg = F.C0;
		} else if (coefficient.isOne()) {
			plusArg = pow;
		} else {
			if (pow.isOne()) {
				plusArg = coefficient;
			} else {
				plusArg = F.binary(F.Times, coefficient, pow);
			}
		}
		if (!plusArg.isZero()) {
			convertPlusArgument(buf, plusArg, call);
			call = PLUS_CALL;
		}
		return call;
	}

	public void convertFunctionArgs(final Appendable buf, final IAST list) throws IOException {
		append(buf, "[");
		for (int i = 1; i < list.size(); i++) {
			convert(buf, list.get(i));
			if (i < list.size() - 1) {
				append(buf, ",");
			}
		}
		append(buf, "]");
	}

	/**
	 * Write a function into the given <code>Appendable</code>.
	 * 
	 * @param buf
	 * @param function
	 * @throws IOException
	 */
	public void convertAST(final Appendable buf, final IAST function) throws IOException {
		IExpr head = function.head();
		convert(buf, head);
		convertArgs(buf, head, function);
	}

	public void convertArgs(final Appendable buf, IExpr head, final IAST function) throws IOException {
		if (head.isAST()) {
			append(buf, "[");
		} else if (fRelaxedSyntax) {
			append(buf, "(");
		} else {
			append(buf, "[");
		}
		final int functionSize = function.size();
		if (functionSize > 1) {
			convert(buf, function.arg1());
		}
		for (int i = 2; i < functionSize; i++) {
			append(buf, ",");
			convert(buf, function.get(i));
		}
		if (head.isAST()) {
			append(buf, "]");
		} else if (fRelaxedSyntax) {
			append(buf, ")");
		} else {
			append(buf, "]");
		}
	}

	/**
	 * this resets the columnCounter to offset 0
	 * 
	 */
	private void newLine(Appendable buf) throws IOException {
		if (!fIgnoreNewLine) {
			append(buf, '\n');
		}
		fColumnCounter = 0;
		fEmpty = false;
	}

	private void append(Appendable buf, String str) throws IOException {
		buf.append(str);
		fColumnCounter += str.length();
		fEmpty = false;
	}

	private void append(Appendable buf, char c) throws IOException {
		buf.append(c);
		fColumnCounter += 1;
		fEmpty = false;
	}

	/**
	 * @param ignoreNewLine
	 *            The ignoreNewLine to set.
	 */
	public void setIgnoreNewLine(final boolean ignoreNewLine) {
		fIgnoreNewLine = ignoreNewLine;
	}

	public void setEmpty(final boolean empty) {
		fEmpty = empty;
	}

	/**
	 * @return Returns the columnCounter.
	 */
	public int getColumnCounter() {
		return fColumnCounter;
	}

	/**
	 * @param columnCounter
	 *            The columnCounter to set.
	 */
	public void setColumnCounter(final int columnCounter) {
		fColumnCounter = columnCounter;
	}
}
