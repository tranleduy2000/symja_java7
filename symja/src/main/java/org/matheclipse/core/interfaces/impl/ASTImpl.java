package org.matheclipse.core.interfaces.impl;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Duy on 10/2/2017.
 */

public abstract class ASTImpl extends ExprImpl implements IAST {
    /**
     * @param collection
     * @return
     * @deprecated use #appendAll()
     */
    @Deprecated
    public boolean addAll(Collection<? extends IExpr> collection) {
        return appendAll(collection);
    }

    /**
     * @param ast
     * @param startPosition
     * @param endPosition
     * @return
     * @deprecated use appendAll()
     */
    @Deprecated
    public boolean addAll(IAST ast, int startPosition, int endPosition) {
        return appendAll(ast, startPosition, endPosition);
    }

    /**
     * @param location
     * @param collection
     * @return
     * @deprecated use appendAll()
     */
    @Deprecated
    public boolean addAll(int location, Collection<? extends IExpr> collection) {
        return appendAll(location, collection);
    }

    /**
     * @param list
     * @param startPosition
     * @param endPosition
     * @return
     * @deprecated use appendAll()
     */
    @Deprecated
    public boolean addAll(List<? extends IExpr> list, int startPosition, int endPosition) {
        return appendAll(list, startPosition, endPosition);
    }

    /**
     * @param ast
     * @return
     * @deprecated use appendArgs();
     */
    @Deprecated
    public boolean addArgs(IAST ast) {
        return appendArgs(ast);
    }

    /**
     * @param expr
     * @return
     * @deprecated use appendClone();
     */
    @Deprecated
    public IAST addClone(IExpr expr) {
        return appendClone(expr);
    }


    /**
     * @param subAST
     * @return
     * @deprecated - use appendOneIdentity()
     */
    @Deprecated
    public IAST addOneIdentity(IAST subAST) {
        return appendOneIdentity(subAST);
    }

    /**
     * Tests whether this {@code Collection} contains all objects contained in the
     * specified {@code Collection}. This implementation iterates over the specified
     * {@code Collection}. If one element returned by the iterator is not contained
     * in this {@code Collection}, then {@code false} is returned; {@code true}
     * otherwise.
     *
     * @param collection the collection of objects.
     * @return {@code true} if all objects in the specified {@code Collection} are
     * elements of this {@code Collection}, {@code false} otherwise.
     * @throws ClassCastException   if one or more elements of {@code collection} isn't of the
     *                              correct type.
     * @throws NullPointerException if {@code collection} contains at least one {@code null} element
     *                              and this {@code Collection} doesn't support {@code null}
     *                              elements.
     * @throws NullPointerException if {@code collection} is {@code null}.
     */
    public boolean containsAll(Collection<?> collection) {
        Iterator<?> it = collection.iterator();
        while (it.hasNext()) {
            if (!contains(it.next())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns a shallow copy of this <code>IAST</code> instance (the elements
     * themselves are not copied). In contrast to the <code>clone()</code> method,
     * this method returns exactly the same type for
     * <code>AST0, AST1, AST2,AST3</code>.
     *
     * @return a copy of this <code>IAST</code> instance.
     */
    @Override
    public IAST copy() {
        return (IAST) clone();
    }

    /**
     * Check if the object at index 0 (i.e. the head of the list) is the same object
     * as <code>head</code> and if the size of the list is greater or equal
     * <code>length</code>.
     *
     * @param head   object to compare with element at location <code>0</code>
     * @param length
     * @return
     */
    @Override
    public boolean isSameHeadSizeGE(IExpr head, int length) {
        int size = size();
        return head().equals(head) && length <= size;
    }

    /**
     * Test if the last argument contains a pattern with a public argument.
     *
     * @return
     */
    public boolean hasDefaultArgument() {
        if (size() > 1) {
            return get(size() - 1).isPatternDefault();
        }
        return false;
    }

    /**
     * Test if one of the arguments gives <code>true</code> for the
     * <code>isNumericArgument()</code> method
     *
     * @return <code>true</code> if one of the arguments gives <code>true</code> for
     * the <code>isNumericArgument()</code> method
     */
    public boolean hasNumericArgument() {
        int size = size();
        for (int i = 1; i < size; i++) {
            if (get(i).isNumericArgument()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Test if the last argument contains a pattern with an optional argument. (i.e.
     * <code>x_:value</code>)
     *
     * @return
     */
    public boolean hasOptionalArgument() {
        if (size() > 1) {
            return get(size() - 1).isPatternDefault();
        }
        return false;
    }

    /**
     * Maps the elements of this IAST with the unary functor
     * <code>Functors.replaceArg(replacement, position)</code>, there
     * <code>replacement</code> is an IAST at which the argument at the given
     * position will be replaced by the currently mapped element and appends the
     * element to <code>appendAST</code>.
     *
     * @param appendAST
     * @param replacement an IAST there the argument at the given position is replaced by
     *                    the currently mapped argument of this IAST.
     * @param position
     * @return <code>appendAST</code>
     * @deprecated use IAST#mapThread() instead
     */
    @Deprecated
    public IAST mapAt(IAST appendAST, final IAST replacement, int position) {
        return mapThread(appendAST, replacement, position);
    }

    public IAST mapAt(final IAST replacement, int position) {
        return mapThread(replacement, position);
    }

    /**
     * Create a shallow copy of this <code>IAST</code> instance (the elements
     * themselves are not copied) and set the <code>expr</code> at the given
     * <code>position</code>. In contrast to the <code>setAtClone()</code> method,
     * this method returns exactly the same type for
     * <code>AST0, AST1, AST2, AST3</code>.
     *
     * @param i
     * @param expr
     * @return a copy with element set to <code>expr</code> at the given
     * <code>position</code>.
     */
    public IAST setAtCopy(int i, IExpr expr) {
        IAST ast = copy();
        ast.set(i, expr);
        return ast;
    }

    public boolean appendPlus(IExpr expr) {
        if (head().equals(F.Plus) && expr.head().equals(F.Plus)) {
            return appendArgs((IAST) expr);
        }
        return append(expr);
    }
}
