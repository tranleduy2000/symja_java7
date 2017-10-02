package org.matheclipse.core.reflection.system.function;

import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IComplex;
import org.matheclipse.core.interfaces.IComplexNum;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.INum;
import org.matheclipse.core.interfaces.IPattern;
import org.matheclipse.core.interfaces.IPatternSequence;
import org.matheclipse.core.interfaces.IStringX;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.visit.VisitorExpr;

/**
 * Replace all occurrences of expressions where the given
 * <code>function.apply()</code> method returns a non <code>F.NIL</code>
 * value. The visitors <code>visit()</code> methods return
 * <code>F.NIL</code> if no substitution occurred.
 */
public class ShareReplaceAll extends VisitorExpr {
    final Function<IExpr, IExpr> fFunction;
    final int fOffset;
    public int fCounter;

    public ShareReplaceAll(Function<IExpr, IExpr> function) {
        this(function, 0);
    }

    public ShareReplaceAll(Function<IExpr, IExpr> function, int offset) {
        super();
        this.fFunction = function;
        this.fOffset = offset;
        fCounter = 0;
    }

    @Override
    public IExpr visit(IInteger element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IFraction element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IComplex element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(INum element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IComplexNum element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(ISymbol element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IPattern element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IPatternSequence element) {
        return null;
    }

    /**
     * @return <code>F.NIL</code>, if no evaluation is possible
     */
    @Override
    public IExpr visit(IStringX element) {
        return null;
    }

    @Override
    public IExpr visit(IAST ast) {
        IExpr temp = fFunction.apply(ast);
        if (temp != null) {
            return temp;
        }
        return visitAST(ast);
    }

    @Override
    protected IExpr visitAST(IAST ast) {
        IExpr temp;
        boolean evaled = false;
        int i = fOffset;
        while (i < ast.size()) {
            temp = ast.get(i);
            if (temp.isAST()) {
                temp = temp.accept(this);
                if (temp != null) {
                    // share the object with the same id:
                    ast.set(i, temp);
                    evaled = true;
                    fCounter++;
                }
            }
            i++;
        }
        return evaled ? ast : null;
    }
}