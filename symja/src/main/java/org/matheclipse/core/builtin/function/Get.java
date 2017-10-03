package org.matheclipse.core.builtin.function;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.matheclipse.core.convert.AST2Expr;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.exception.WrongNumberOfArguments;
import org.matheclipse.core.eval.interfaces.AbstractFunctionEvaluator;
import org.matheclipse.core.expression.Context;
import org.matheclipse.core.expression.ContextPath;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.parser.client.Parser;
import org.matheclipse.parser.client.ast.ASTNode;

/**
 * Get[{&lt;file name&gt;}}
 * 
 */
public class Get extends AbstractFunctionEvaluator {

	private static int addContextToPath(ContextPath contextPath, final List<ASTNode> node, int i,
			final EvalEngine engine, ISymbol endSymbol) {
		ContextPath path = engine.getContextPath();
		try {
			engine.setContextPath(contextPath);
			AST2Expr ast2Expr = new AST2Expr(engine.isRelaxedSyntax(), engine);
			while (i < node.size()) {
				IExpr temp = ast2Expr.convert(node.get(i++));
				if (temp.isAST()) {
					IExpr head = temp.head();
					IAST ast = (IAST) temp;
					if (head.equals(endSymbol) && ast.isAST0()) {
						continue;
					} else if (head.equals(F.Begin) && ast.size() >= 2) {
						try {
							contextPath.add(engine.getContextPath().getContext(ast.arg1().toString()));
							i = addContextToPath(contextPath, node, i, engine, F.End);
						} finally {
							contextPath.remove(contextPath.size() - 1);
						}
						continue;
					}
				}
				engine.evaluate(temp);
			}
			// TODO add error message
		} finally {
			engine.setContextPath(path);
		}
		return i;
	}

	/**
	 * Load a package from the given reader
	 * 
	 * @param engine
	 * @param is
	 * @return the last evaluated expression result
	 */
	public static IExpr loadPackage(final EvalEngine engine, final Reader is) {
		final BufferedReader r = new BufferedReader(is);
		Context packageContext = null;
		try {
			final List<ASTNode> node = parseReader(r, engine);

			IExpr temp;
			int i = 0;
			AST2Expr ast2Expr = new AST2Expr(engine.isRelaxedSyntax(), engine);
			IExpr result = F.Null;
			while (i < node.size()) {
				temp = ast2Expr.convert(node.get(i++));
				if (temp.isAST()) {
					IAST ast = (IAST) temp;
					IExpr head = temp.head();
					if (head.equals(F.BeginPackage) && ast.size() >= 2) {
						String contextName = Validate.checkContextName(ast, 1);
						packageContext = engine.getContextPath().getContext(contextName);
						ISymbol endSymbol = F.EndPackage;
						for (int j = 2; j < ast.size(); j++) {
							FileReader reader = new FileReader(ast.get(j).toString());
							Get.loadPackage(engine, reader);
							reader.close();
						}
						i = addContextToPath(new ContextPath(packageContext), node, i, engine, endSymbol);
						continue;
					} else if (head.equals(F.Begin) && ast.size() >= 2) {
						String contextName = Validate.checkContextName(ast, 1);
						ISymbol endSymbol = F.End;
						i = addContextToPath(new ContextPath(contextName), node, i, engine, endSymbol);
						continue;
					}
				}
				result = engine.evaluate(temp);
			}
			return result;
		} catch (final Exception e) {
			e.printStackTrace();
		} finally {
			if (packageContext != null) {
				engine.getContextPath().add(packageContext);
			}
			try {
				r.close();
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return F.Null;
	}

	/**
	 * <p>
	 * Parse the <code>reader</code> input.
	 * </p>
	 * <p>
	 * This method ignores the first line of the script if it starts with the
	 * <code>#!</code> characters (i.e. Unix Script Executables)
	 * </p>
	 * <p>
	 * <b>Note</b>: uses the <code>ASTNode</code> parser and not the
	 * <code>ExprParser</code>, because otherwise the symbols couldn't be assigned
	 * to the contexts.
	 * </p>
	 * 
	 * @param reader
	 * @param engine
	 * @return
	 * @throws IOException
	 */
	public static List<ASTNode> parseReader(final BufferedReader reader, final EvalEngine engine) throws IOException {
		String record;
		StringBuilder builder = new StringBuilder(2048);
		if ((record = reader.readLine()) != null) {
			// ignore the first line of the script if it starts with the #!
			// characters (i.e. Unix Script Executables)
			if (!record.startsWith("!#")) {
				builder.append(record);
				builder.append('\n');
			}
		}
		while ((record = reader.readLine()) != null) {
			builder.append(record);
			builder.append('\n');
		}
		final Parser parser = new Parser(engine.isRelaxedSyntax(), true);
		final List<ASTNode> node = parser.parsePackage(builder.toString());
		return node;
	}

	public Get() {
	}

	@Override
	public IExpr evaluate(final IAST ast, EvalEngine engine) {
		Validate.checkSize(ast, 2);

		if (!(ast.arg1() instanceof IStringX)) {
			throw new WrongNumberOfArguments(ast, 1, ast.size() - 1);
		}
		IStringX arg1 = (IStringX) ast.arg1();
		FileReader reader;
		try {
			reader = new FileReader(arg1.toString());
			return loadPackage(engine, reader);
		} catch (FileNotFoundException e) {
			engine.printMessage("Get: file " + arg1.toString() + " not found!");
		}
		return F.Null;
	}

	@Override
	public void setUp(ISymbol newSymbol) {
		newSymbol.setAttributes(ISymbol.HOLDALL);
	}
}
