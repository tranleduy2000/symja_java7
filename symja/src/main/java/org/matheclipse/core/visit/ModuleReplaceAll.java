package org.matheclipse.core.visit;

import java.util.IdentityHashMap;

import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.ISymbol;

/**
 * Replace all occurrences of expressions where the given <code>function.apply()</code> method returns a non
 * <code>F.NIL</code> value. The visitors <code>visit()</code> methods return <code>F.NIL</code> if no substitution
 * occurred.
 */
public class ModuleReplaceAll extends VisitorExpr {
	final IdentityHashMap<ISymbol, IExpr> fModuleVariables;
	final int fOffset;
	final EvalEngine fEngine;

	public ModuleReplaceAll(IdentityHashMap<ISymbol, IExpr> moduleVariables, EvalEngine engine) {
		this(moduleVariables, engine, 0);
	}

	public ModuleReplaceAll(IdentityHashMap<ISymbol, IExpr> moduleVariables, EvalEngine engine, int offset) {
		this.fModuleVariables = moduleVariables;
		this.fOffset = offset;
		this.fEngine = engine;
	}

	private IExpr apply(final IExpr arg) {
		IExpr temp = fModuleVariables.get(arg);
		return temp != null ? temp : F.NIL;
	}

	/**
	 * 
	 * @return <code>F.NIL</code>, if no evaluation is possible
	 */
	@Override
	public IExpr visit(ISymbol element) {
		return apply(element);
	}

	@Override
	public IExpr visit(IAST ast) {
		IAST temp;
		if (ast.isASTSizeGE(F.Function, 2)) {
			temp = visitNestedScope(ast, true);
			if (temp.isPresent()) {
				return temp;
			}
			return F.NIL;
		} else if (ast.isASTSizeGE(F.Block, 2) || ast.isASTSizeGE(F.Module, 2) || ast.isASTSizeGE(F.With, 2)) {
			temp = visitNestedScope(ast, false);
			if (temp.isPresent()) {
				return temp;
			}
		}

		return visitASTModule(ast);
	}

	/**
	 * Nested Block() Function()
	 * 
	 * @param ast
	 * @param isFunction
	 *            TODO
	 * @return
	 */
	private IAST visitNestedScope(IAST ast, boolean isFunction) {
		IAST result = F.NIL;
		IAST localVariablesList = F.NIL;
		if (ast.arg1().isSymbol()) {
			localVariablesList = F.List(ast.arg1());
		} else if (ast.arg1().isList()) {
			localVariablesList = (IAST) ast.arg1();
		}
		ModuleReplaceAll visitor = this;
		if (localVariablesList.isPresent()) {
			IdentityHashMap<ISymbol, IExpr> variables = renamedVariables(localVariablesList, isFunction);
			if (variables != null) {
				visitor = new ModuleReplaceAll(variables, fEngine);
			}
		}
		
		IExpr temp;

		int i = fOffset;
		while (i < ast.size()) {
			temp = ast.get(i).accept(visitor);
			if (temp.isPresent()) {
				// something was evaluated - return a new IAST:
				result = ast.copy();
				result.set(i++, temp);
				break;
			}
			i++;
		}
		if (result.isPresent()) {
			while (i < ast.size()) {
				temp = ast.get(i).accept(visitor);
				if (temp.isPresent()) {
					result.set(i, temp);
				}
				i++;
			}
		}

		return result;

	}

	private IdentityHashMap<ISymbol, IExpr> renamedVariables(IAST localVariablesList, boolean isFunction) {
		IdentityHashMap<ISymbol, IExpr> variables = null;
		final int moduleCounter = fEngine.incModuleCounter();
		final String varAppend = "$" + moduleCounter;
		int size = localVariablesList.size();
		for (int i = 1; i < size; i++) {
			IExpr temp = localVariablesList.get(i);
			if (temp.isSymbol()) {
				ISymbol symbol = (ISymbol) temp;
				if (isFunction || fModuleVariables.get(symbol) != null) {

					if (variables == null) {
						variables = (IdentityHashMap<ISymbol, IExpr>) fModuleVariables.clone();
					}
					variables.remove(symbol);
					variables.put(symbol, F.$s(symbol.toString() + varAppend));
				}
			} else {
				if (temp.isAST(F.Set, 3)) {
					// lhs = rhs
					final IAST setFun = (IAST) temp;
					if (setFun.arg1().isSymbol()) {
						ISymbol symbol = (ISymbol) setFun.arg1();
						if (isFunction || fModuleVariables.get(symbol) != null) {
							if (variables == null) {
								variables = (IdentityHashMap<ISymbol, IExpr>) fModuleVariables.clone();
							}
							variables.remove(symbol);
							variables.put(symbol, F.$s(symbol.toString() + varAppend));
						}
					}
				}
			}
		}

		return variables;
	}

	private IExpr visitASTModule(IAST ast) {
		IExpr temp;
		IAST result = F.NIL;
		int i = fOffset;
		while (i < ast.size()) {
			temp = ast.get(i).accept(this);
			if (temp.isPresent()) {
				// something was evaluated - return a new IAST:
				result = ast.copy();
				result.set(i++, temp);
				break;
			}
			i++;
		}
		if (result.isPresent()) {
			while (i < ast.size()) {
				temp = ast.get(i).accept(this);
				if (temp.isPresent()) {
					result.set(i, temp);
				}
				i++;
			}
		}
		return result;
	}
}
