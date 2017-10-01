package org.matheclipse.core.form.mathml.reflection;

import org.matheclipse.core.builtin.Algebra;
import org.matheclipse.core.form.mathml.MMLOperator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.parser.client.operator.ASTNodeFactory;

public class Times extends MMLOperator {
	public final static int NO_SPECIAL_CALL = 0;

	public final static int PLUS_CALL = 1;

	public static Times CONST = new Times();

	public Times() {
		super(ASTNodeFactory.MMA_STYLE_FACTORY.get("Times").getPrecedence(), "mrow", "&#0183;");// centerdot instead of
																									// invisibletimes:
																									// "&#8290;");
	}

	/**
	 * Converts a given function into the corresponding MathML output
	 * 
	 * @param buf
	 *            StringBuilder for MathML output
	 * @param f
	 *            The math function which should be converted to MathML
	 */
	@Override
	public boolean convert(final StringBuilder buf, final IAST f, final int precedence) {
		return convertTimesFraction(buf, f, precedence, NO_SPECIAL_CALL);
	}

	/**
	 * Try to split a given <code>Times[...]</code> function into nominator and
	 * denominator and add the corresponding MathML output
	 * 
	 * @param buf
	 *            StringBuilder for MathML output
	 * @param f
	 *            The function which should be converted to MathML
	 * @precedence
	 * @caller
	 */
	public boolean convertTimesFraction(final StringBuilder buf, final IAST f, final int precedence, final int caller) {
		IExpr[] parts = Algebra.fractionalPartsTimesPower(f, false, true, false, false);
		if (parts == null) {
			convertTimesOperator(buf, f, precedence, caller);
			return true;
		}
		final IExpr numerator = parts[0];
		final IExpr denominator = parts[1];
		if (!denominator.isOne()) {
			// found a fraction expression
			if (caller == PLUS_CALL) {
				fFactory.tag(buf, "mo", "+");
			}
			fFactory.tagStart(buf, "mfrac");
			// insert numerator in buffer:
			if (numerator.isTimes()) {
				convertTimesOperator(buf, (IAST) numerator, precedence, NO_SPECIAL_CALL);
			} else {
				fFactory.convert(buf, numerator, fPrecedence);
			}
			if (denominator.isTimes()) {
				convertTimesOperator(buf, (IAST) denominator, precedence, NO_SPECIAL_CALL);
			} else {
				fFactory.convert(buf, denominator, 0);
			}
			fFactory.tagEnd(buf, "mfrac");
		} else {
			// if (numerator.size() <= 2) {
			if (numerator.isTimes()) {
				convertTimesOperator(buf, (IAST) numerator, precedence, caller);
			} else {
				convertTimesOperator(buf, f, precedence, caller);
			}
		}

		return true;
	}

	/**
	 * Converts a given <code>Times[...]</code> function into the corresponding
	 * MathML output.
	 * 
	 * @param buf
	 * @param timesAST
	 * @param precedence
	 * @param caller
	 * @return
	 */
	private boolean convertTimesOperator(final StringBuilder buf, final IAST timesAST, final int precedence,
			final int caller) {
		int size = timesAST.size();
		if (size > 1) {
			IExpr arg1 = timesAST.arg1();
			if (arg1.isMinusOne()) {
				if (size == 2) {
					fFactory.tagStart(buf, fFirstTag);
					precedenceOpen(buf, precedence);
					fFactory.convert(buf, arg1, fPrecedence);
				} else {
					if (caller == PLUS_CALL) {
						fFactory.tag(buf, "mo", "-");
						if (size == 3) {
							fFactory.convert(buf, timesAST.arg2(), fPrecedence);
							return true;
						}
						fFactory.tagStart(buf, fFirstTag);
					} else {
						fFactory.tagStart(buf, fFirstTag);
						precedenceOpen(buf, precedence);
						fFactory.tag(buf, "mo", "-");
					}
				}
			} else if (arg1.isOne()) {
				if (size == 2) {
					fFactory.tagStart(buf, fFirstTag);
					precedenceOpen(buf, precedence);
					fFactory.convert(buf, arg1, fPrecedence);
				} else {
					if (caller == PLUS_CALL) {
						if (size == 3) {
							fFactory.convert(buf, timesAST.arg2(), fPrecedence);
							return true;
						}
						fFactory.tagStart(buf, fFirstTag);
					} else {
						fFactory.tagStart(buf, fFirstTag);
						precedenceOpen(buf, precedence);
					}
				}
			} else {
				if (caller == PLUS_CALL) {
					if ((arg1 instanceof ISignedNumber) && (((ISignedNumber) arg1).isNegative())) {
						fFactory.tag(buf, "mo", "-");
						fFactory.tagStart(buf, fFirstTag);
						arg1 = ((ISignedNumber) arg1).opposite();
					} else {
						fFactory.tag(buf, "mo", "+");
						fFactory.tagStart(buf, fFirstTag);
					}
				} else {
					fFactory.tagStart(buf, fFirstTag);
					precedenceOpen(buf, precedence);
				}
				fFactory.convert(buf, arg1, fPrecedence);
				if (fOperator.length() > 0) {
					fFactory.tag(buf, "mo", fOperator);
				}
			}
		}

		for (int i = 2; i < size; i++) {
			fFactory.convert(buf, timesAST.get(i), fPrecedence);
			if ((i < timesAST.size() - 1) && (fOperator.length() > 0)) {
				fFactory.tag(buf, "mo", fOperator);
			}
		}
		precedenceClose(buf, precedence);
		fFactory.tagEnd(buf, fFirstTag);
		return true;
	}
}