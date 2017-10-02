package org.matheclipse.core.reflection.system;

import org.matheclipse.core.builtin.structure.Structure;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractEvaluator;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.reflection.system.function.TrigExpandFunction;
import org.matheclipse.core.visit.VisitorPlusTimesPowerReplaceAll;

import static org.matheclipse.core.expression.F.evalExpandAll;

/**
 * <pre>
 * TrigExpand(expr)
 * </pre>
 * 
 * <blockquote>
 * <p>
 * expands out trigonometric expressions in <code>expr</code>.
 * </p>
 * </blockquote>
 * <h3>Examples</h3>
 * 
 * <pre>
 * &gt;&gt; TrigExpand(Sin(x+y))
 * Cos(x)*Sin(y)+Cos(y)*Sin(x)
 * </pre>
 */
public class TrigExpand extends AbstractEvaluator {

	private final static Function<IExpr, IExpr> function = new TrigExpandFunction();
	private final static VisitorPlusTimesPowerReplaceAll visitor = new VisitorPlusTimesPowerReplaceAll(function);



	public TrigExpand() {
	}

	/**
	 * Expands the argument of sine and cosine functions.
	 * 
	 * <a href="http://en.wikipedia.org/wiki/List_of_trigonometric_identities" >List
	 * of trigonometric identities</a>
	 */
	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);

		IExpr temp = Structure.threadLogicEquationOperators(ast.arg1(), ast, 1);
		if (temp.isPresent()) {
			return temp;
		}

		temp = ast.arg1();
		IExpr result;
		do {
			result = evalExpandAll(temp, engine);
			temp = result.accept(visitor);
		} while (temp.isPresent());
		return result;
	}

	@Override
	public void setUp(final ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.LISTABLE);
	}

}
