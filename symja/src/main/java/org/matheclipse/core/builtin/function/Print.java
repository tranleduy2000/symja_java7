package org.matheclipse.core.builtin.function;

import java.io.IOException;
import java.io.PrintStream;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IStringX;

public class Print extends AbstractCoreFunctionEvaluator {

	public Print() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		PrintStream stream = engine.getOutPrintStream();
		if (stream == null) {
			stream = System.out;
		}
		try {
			final StringBuilder buf = new StringBuilder();
			IExpr temp;
			for (int i = 1; i < ast.size(); i++) {
				temp = engine.evaluate(ast.get(i));
				if (temp instanceof IStringX) {
					buf.append(temp.toString());
				} else {
					OutputFormFactory.get().convert(buf, temp);
				}
			}
			stream.println(buf.toString());
		} catch (IOException e) {
			stream.println(e.getMessage());
			if (Config.DEBUG) {
				e.printStackTrace();
			}
		}

		return F.Null;
	}

}
