package org.matheclipse.core.form.tex;

import org.matheclipse.core.form.tex.AbstractConverter;
import org.matheclipse.core.form.tex.TeXFormFactory;
import org.matheclipse.core.interfaces.IAST;

public class PreOperator extends AbstractConverter {
	protected int fPrecedence;
	protected String fOperator;

	public PreOperator(final int precedence, final String oper) {
		fPrecedence = precedence;
		fOperator = oper;
	}

	public PreOperator(final TeXFormFactory factory, final int precedence, final String oper) {
		super(factory);
		fPrecedence = precedence;
		fOperator = oper;
	}

	public void precedenceOpen(final StringBuilder buf, final int precedence) {
		if (precedence > fPrecedence) {
			buf.append("\\left( ");
		}
	}

	public void precedenceClose(final StringBuilder buf, final int precedence) {
		if (precedence > fPrecedence) {
			buf.append("\\right) ");
		}
	}

	/** {@inheritDoc} */
	@Override
	public boolean convert(final StringBuilder buf, final IAST f, final int precedence) {
		if (f.size() != 2) {
			return false;
		}
		precedenceOpen(buf, precedence);
		buf.append(fOperator);
		fFactory.convert(buf, f.arg1(), fPrecedence);
		precedenceClose(buf, precedence);
		return true;
	}

}