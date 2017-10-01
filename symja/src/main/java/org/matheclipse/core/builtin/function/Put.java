package org.matheclipse.core.builtin.function;

import java.io.FileWriter;
import java.io.IOException;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Put[{&lt;file name&gt;}}
 * 
 */
public class Put extends AbstractFunctionEvaluator {

	public Put() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkRange(ast, 3);

		int len = ast.size() - 1;
		IStringX fileName = Validate.checkStringType(ast, len);
		FileWriter writer;
		try {
			writer = new FileWriter(fileName.toString());
			final StringBuilder buf = new StringBuilder();
			for (int i = 1; i < len; i++) {
				IExpr temp = engine.evaluate(ast.get(i));
				OutputFormFactory.get().convert(buf, temp);
				buf.append('\n');
				if (i < len - 1) {
					buf.append('\n');
				}
			}
			writer.write(buf.toString());
			writer.close();
		} catch (IOException e) {
			engine.printMessage("Put: file " + fileName.toString() + " I/O exception !");
		}
		return F.Null;
	}

	@Override
	public void setUp(ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.HOLDALL);
	}
}
