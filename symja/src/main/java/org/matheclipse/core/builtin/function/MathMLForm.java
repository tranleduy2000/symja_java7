package org.matheclipse.core.builtin.function;

import java.io.StringWriter;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.MathMLUtilities;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * <pre>MathMLForm(expr)
 * </pre>
 * <blockquote><p>returns the MathMLForm form of the evaluated <code>expr</code>.</p>
 * </blockquote>
 */
public class MathMLForm extends AbstractCoreFunctionEvaluator {

	public MathMLForm() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2); 

		MathMLUtilities mathMLUtil = new MathMLUtilities(engine, false, engine.isRelaxedSyntax());
		IExpr arg1 = ast.arg1();
		StringWriter stw = new StringWriter();
		mathMLUtil.toMathML(arg1, stw);
		return F.$str(stw.toString());
	}

	@Override
	public void setUp(ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.HOLDALL);
	}
}
