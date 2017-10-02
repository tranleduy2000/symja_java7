package org.matheclipse.core.builtin.algebra;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.convert.JASConvert;
import org.matheclipse.core.convert.JASIExpr;
import org.matheclipse.core.convert.JASModInteger;
import org.matheclipse.core.convert.VariablesSet;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.JASConversionException;
import org.matheclipse.core.expression.ASTRange;
import org.matheclipse.core.expression.ExprRingFactory;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.IFraction;
import org.matheclipse.core.interfaces.IInteger;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.core.polynomials.ExprPolynomial;
import org.matheclipse.core.polynomials.ExprPolynomialRing;
import org.matheclipse.core.polynomials.IPartialFractionGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;

import edu.jas.arith.BigRational;
import edu.jas.arith.ModLong;
import edu.jas.arith.ModLongRing;
import edu.jas.poly.Complex;
import edu.jas.poly.ComplexRing;
import edu.jas.poly.GenPolynomial;
import edu.jas.poly.GenPolynomialRing;
import edu.jas.poly.PolyUtil;
import edu.jas.poly.TermOrder;
import edu.jas.poly.TermOrderByName;
import edu.jas.ufd.FactorAbstract;
import edu.jas.ufd.FactorComplex;
import edu.jas.ufd.FactorFactory;
import edu.jas.ufd.GCDFactory;
import edu.jas.ufd.GreatestCommonDivisor;
import edu.jas.ufd.SquarefreeAbstract;
import edu.jas.ufd.SquarefreeFactory;


public class Algebra {
    final static Algebra CONST = new Algebra();

    static {
        F.Apart.setEvaluator(new Apart());
        F.Cancel.setEvaluator(new Cancel());
        F.Collect.setEvaluator(new Collect());
        F.Denominator.setEvaluator(new Denominator());
        F.Expand.setEvaluator(new Expand());
        F.ExpandAll.setEvaluator(new ExpandAll());
        F.Factor.setEvaluator(new Factor());
        F.FactorSquareFree.setEvaluator(new FactorSquareFree());
        F.FactorSquareFreeList.setEvaluator(new FactorSquareFreeList());
        F.FactorTerms.setEvaluator(new FactorTerms());
        F.FullSimplify.setEvaluator(new FullSimplify());
        F.Numerator.setEvaluator(new Numerator());

        F.PolynomialExtendedGCD.setEvaluator(new PolynomialExtendedGCD());
        F.PolynomialGCD.setEvaluator(new PolynomialGCD());
        F.PolynomialLCM.setEvaluator(new PolynomialLCM());
        F.PolynomialQ.setEvaluator(new PolynomialQ());
        F.PolynomialQuotient.setEvaluator(new PolynomialQuotient());
        F.PolynomialQuotientRemainder.setEvaluator(new PolynomialQuotientRemainder());
        F.PolynomialRemainder.setEvaluator(new PolynomialRemainder());

        F.PowerExpand.setEvaluator(new PowerExpand());
        F.Root.setEvaluator(new Root());
        F.Simplify.setEvaluator(new Simplify());
        F.Together.setEvaluator(new Together());
        F.ToRadicals.setEvaluator(new ToRadicals());
        F.Variables.setEvaluator(new Variables());
    }

    private Algebra() {

    }

    /**
     * Calculate the 3 elements result array
     * <p>
     * <pre>
     * [
     *   commonFactor,
     *   numeratorPolynomial.divide(gcd(numeratorPolynomial, denominatorPolynomial)),
     *   denominatorPolynomial.divide(gcd(numeratorPolynomial, denominatorPolynomial))
     * ]
     * </pre>
     * <p>
     * for the given expressions <code>numeratorPolynomial</code> and
     * <code>denominatorPolynomial</code>.
     *
     * @param numeratorPolynomial   a <code>BigRational</code> polynomial which could be converted to
     *                              JAS polynomial
     * @param denominatorPolynomial a <code>BigRational</code> polynomial which could be converted to
     *                              JAS polynomial
     * @return <code>null</code> if the expressions couldn't be converted to JAS
     * polynomials or gcd equals 1
     * @throws JASConversionException
     */
    public static IExpr[] cancelGCD(IExpr numeratorPolynomial, IExpr denominatorPolynomial)
            throws JASConversionException {

        try {
            if (denominatorPolynomial.isInteger() && numeratorPolynomial.isPlus()) {
                IExpr[] result = Cancel.cancelPlusIntegerGCD((IAST) numeratorPolynomial,
                        (IInteger) denominatorPolynomial);
                if (result != null) {
                    return result;
                }
            }

            VariablesSet eVar = new VariablesSet(numeratorPolynomial);
            eVar.addVarList(denominatorPolynomial);
            if (eVar.size() == 0) {
                return null;
            }

            IAST vars = eVar.getVarList();
            ExprPolynomialRing ring = new ExprPolynomialRing(vars);
            ExprPolynomial pol1 = ring.create(numeratorPolynomial);
            ExprPolynomial pol2 = ring.create(denominatorPolynomial);
            ASTRange r = new ASTRange(eVar.getVarList(), 1);
            JASIExpr jas = new JASIExpr(r, true);
            GenPolynomial<IExpr> p1 = jas.expr2IExprJAS(pol1);
            GenPolynomial<IExpr> p2 = jas.expr2IExprJAS(pol2);

            GreatestCommonDivisor<IExpr> engine;
            engine = GCDFactory.getImplementation(ExprRingFactory.CONST);
            GenPolynomial<IExpr> gcd = engine.gcd(p1, p2);
            IExpr[] result = new IExpr[3];
            if (gcd.isONE()) {
                result[0] = jas.exprPoly2Expr(gcd);
                result[1] = jas.exprPoly2Expr(p1);
                result[2] = jas.exprPoly2Expr(p2);
            } else {
                if (JASIExpr.isInexactCoefficient(gcd)) {
                    return null;
                }
                result[0] = F.C1;
                result[1] = F.eval(jas.exprPoly2Expr(p1.divide(gcd)));
                result[2] = F.eval(jas.exprPoly2Expr(p2.divide(gcd)));
            }
            return result;
        } catch (RuntimeException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Expand the given <code>ast</code> expression.
     *
     * @param ast
     * @param patt
     * @param distributePlus TODO
     * @return <code>F.NIL</code> if the expression couldn't be expanded.
     */
    public static IExpr expand(final IAST ast, IExpr patt, boolean expandNegativePowers, boolean distributePlus) {
        Expand.Expander expander = new Expand.Expander(patt, expandNegativePowers, distributePlus);
        return expander.expandAST(ast);
    }

    /**
     * Expand the given <code>ast</code> expression.
     *
     * @param ast
     * @param patt
     * @param expandNegativePowers
     * @param distributePlus
     * @return <code>F.NIL</code> if the expression couldn't be expanded.
     */
    public static IExpr expandAll(final IAST ast, IExpr patt, boolean expandNegativePowers, boolean distributePlus,
                                  EvalEngine engine) {
        if (patt != null && ast.isFree(patt, true)) {
            return F.NIL;
        }
        IAST localAST = ast;
        IAST tempAST = F.NIL;
        if ((localAST.getEvalFlags() & IAST.IS_SORTED) != IAST.IS_SORTED) {
            tempAST = engine.evalFlatOrderlessAttributesRecursive(localAST);
            if (tempAST.isPresent()) {
                localAST = tempAST;
            }
        }
        if (localAST.isAllExpanded()) {
            if (localAST != ast) {
                return localAST;
            }
            return F.NIL;
        }
        IAST result = F.NIL;
        IExpr temp = F.NIL;
        int size = localAST.size();
        if (localAST.head().isAST()) {
            temp = expandAll((IAST) localAST.head(), patt, expandNegativePowers, distributePlus, engine);
            if (temp.isPresent()) {
                result = F.ast(temp, size, false);
            }
        }
        for (int i = 1; i < localAST.size(); i++) {
            if (localAST.get(i).isAST()) {
                temp = expandAll((IAST) localAST.get(i), patt, expandNegativePowers, distributePlus, engine);
                if (temp.isPresent()) {
                    if (!result.isPresent()) {

                        if (temp.isAST()) {
                            size += ((IAST) temp).size();
                        }
                        result = F.ast(localAST.head(), size, false);
                        result.appendArgs(localAST, i);
                    }
                    result.appendPlus(temp);
                    continue;
                }
            }
            if (result.isPresent()) {
                result.append(localAST.get(i));
            }
        }
        if (!result.isPresent()) {
            temp = expand(localAST, patt, expandNegativePowers, distributePlus);
            if (temp.isPresent()) {
                ExpandAll.setAllExpanded(temp, expandNegativePowers, distributePlus);
                return temp;
            } else {
                if (localAST != ast) {
                    ExpandAll.setAllExpanded(localAST, expandNegativePowers, distributePlus);
                    return localAST;
                }
            }
            ExpandAll.setAllExpanded(ast, expandNegativePowers, distributePlus);
            return F.NIL;
        }
        temp = expand(result, patt, expandNegativePowers, distributePlus);
        if (temp.isPresent()) {
            return ExpandAll.setAllExpanded(temp, expandNegativePowers, distributePlus);
        }
        return ExpandAll.setAllExpanded(result, expandNegativePowers, distributePlus);
    }

    /**
     * Factor the <code>expr</code> in the domain of GaussianIntegers.
     *
     * @param expr
     * @param varList
     * @param head             the head of the result AST
     * @param noGCDLCM
     * @param numeric2Rational TODO
     * @return
     * @throws JASConversionException
     */
    public static IAST factorComplex(IExpr expr, List<IExpr> varList, ISymbol head, boolean noGCDLCM,
                                     boolean numeric2Rational) throws JASConversionException {
        JASConvert<BigRational> jas = new JASConvert<BigRational>(varList, BigRational.ZERO);
        GenPolynomial<BigRational> polyRat = jas.expr2JAS(expr, numeric2Rational);
        return factorComplex(polyRat, jas, varList, head, noGCDLCM);
    }

    public static IAST factorComplex(GenPolynomial<BigRational> polyRat, JASConvert<BigRational> jas,
                                     List<IExpr> varList, ISymbol head, boolean noGCDLCM) {
        TermOrder termOrder = TermOrderByName.Lexicographic;
        String[] vars = new String[varList.size()];
        for (int i = 0; i < varList.size(); i++) {
            vars[i] = varList.get(i).toString();
        }
        Object[] objects = JASConvert.rationalFromRationalCoefficientsFactor(
                new GenPolynomialRing<BigRational>(BigRational.ZERO, varList.size(), termOrder, vars), polyRat);
        java.math.BigInteger gcd = (java.math.BigInteger) objects[0];
        java.math.BigInteger lcm = (java.math.BigInteger) objects[1];
        GenPolynomial<BigRational> poly = (GenPolynomial<BigRational>) objects[2];

        ComplexRing<BigRational> cfac = new ComplexRing<BigRational>(BigRational.ZERO);
        GenPolynomialRing<Complex<BigRational>> cpfac = new GenPolynomialRing<Complex<BigRational>>(cfac, 1, termOrder);
        GenPolynomial<Complex<BigRational>> a = PolyUtil.complexFromAny(cpfac, poly);
        FactorComplex<BigRational> factorAbstract = new FactorComplex<BigRational>(cfac);
        SortedMap<GenPolynomial<Complex<BigRational>>, Long> map = factorAbstract.factors(a);

        IAST result = F.ast(head);
        if (!noGCDLCM) {
            if (!gcd.equals(java.math.BigInteger.ONE) || !lcm.equals(java.math.BigInteger.ONE)) {
                result.append(F.fraction(gcd, lcm));
            }
        }
        GenPolynomial<Complex<BigRational>> temp;
        for (SortedMap.Entry<GenPolynomial<Complex<BigRational>>, Long> entry : map.entrySet()) {
            if (entry.getKey().isONE() && entry.getValue().equals(1L)) {
                continue;
            }
            temp = entry.getKey();
            result.append(F.Power(jas.complexPoly2Expr(entry.getKey()), F.integer(entry.getValue())));
        }
        return result;
    }

    public static IAST factorModulus(IExpr expr, List<IExpr> varList, boolean factorSquareFree, IExpr option)
            throws JASConversionException {
        try {
            // found "Modulus" option => use ModIntegerRing
            ModLongRing modIntegerRing = JASModInteger.option2ModLongRing((ISignedNumber) option);
            JASModInteger jas = new JASModInteger(varList, modIntegerRing);
            GenPolynomial<ModLong> poly = jas.expr2JAS(expr);

            return factorModulus(jas, modIntegerRing, poly, factorSquareFree);
        } catch (ArithmeticException ae) {
            // toInt() conversion failed
            if (Config.DEBUG) {
                ae.printStackTrace();
            }
        }
        return F.NIL;
    }

    public static IAST factorModulus(JASModInteger jas, ModLongRing modIntegerRing, GenPolynomial<ModLong> poly,
                                     boolean factorSquareFree) {
        FactorAbstract<ModLong> factorAbstract = FactorFactory.getImplementation(modIntegerRing);
        SortedMap<GenPolynomial<ModLong>, Long> map;
        if (factorSquareFree) {
            map = factorAbstract.squarefreeFactors(poly);
        } else {
            map = factorAbstract.factors(poly);
        }
        IAST result = F.Times();
        for (SortedMap.Entry<GenPolynomial<ModLong>, Long> entry : map.entrySet()) {
            GenPolynomial<ModLong> singleFactor = entry.getKey();
            Long val = entry.getValue();
            result.append(F.Power(jas.modLongPoly2Expr(singleFactor), F.integer(val)));
        }
        return result;
    }

    public static IAST factorRational(GenPolynomial<BigRational> polyRat, JASConvert<BigRational> jas,
                                      List<IExpr> varList, ISymbol head) {
        Object[] objects = jas.factorTerms(polyRat);
        GenPolynomial<edu.jas.arith.BigInteger> poly = (GenPolynomial<edu.jas.arith.BigInteger>) objects[2];
        FactorAbstract<edu.jas.arith.BigInteger> factorAbstract = FactorFactory
                .getImplementation(edu.jas.arith.BigInteger.ONE);
        SortedMap<GenPolynomial<edu.jas.arith.BigInteger>, Long> map;
        map = factorAbstract.factors(poly);
        IAST result = F.ast(head);
        java.math.BigInteger gcd = (java.math.BigInteger) objects[0];
        java.math.BigInteger lcm = (java.math.BigInteger) objects[1];
        if (!gcd.equals(java.math.BigInteger.ONE) || !lcm.equals(java.math.BigInteger.ONE)) {
            result.append(F.fraction(gcd, lcm));
        }
        for (SortedMap.Entry<GenPolynomial<edu.jas.arith.BigInteger>, Long> entry : map.entrySet()) {
            if (entry.getKey().isONE() && entry.getValue().equals(1L)) {
                continue;
            }
            if (entry.getValue() == 1L) {
                result.append(jas.integerPoly2Expr(entry.getKey()));
            } else {
                result.append(F.Power(jas.integerPoly2Expr(entry.getKey()), F.integer(entry.getValue())));
            }
        }
        return result;
    }

    /**
     * Split the expression into numerator and denominator parts, by calling the
     * <code>Numerator[]</code> and <code>Denominator[]</code> functions
     *
     * @param ast
     * @return an array with the numerator, denominator and the evaluated
     * <code>Together[expr]</code>.
     */
    public static IExpr[] getNumeratorDenominator(IAST ast, EvalEngine engine) {
        IExpr[] result = new IExpr[3];
        result[2] = together(ast, engine);
        // split expr into numerator and denominator
        result[1] = F.eval(F.Denominator(result[2]));
        if (!result[1].isOne()) {
            // search roots for the numerator expression
            result[0] = F.eval(F.Numerator(result[2]));
        } else {
            result[0] = result[2];
        }
        return result;
    }

    /**
     * Return the numerator and denominator for the given <code>Times[...]</code> or
     * <code>Power[a, b]</code> AST, by separating positive and negative powers.
     *
     * @param timesPower             a Times[] or Power[] expression (a*b*c....) or a^b
     * @param splitNumeratorOne      split a fractional number into numerator and denominator, only if
     *                               the numerator is 1, if <code>true</code>, ignore
     *                               <code>splitFractionalNumbers</code> parameter.
     * @param splitFractionalNumbers split a fractional number into numerator and denominator
     * @param trig                   try to find a trigonometric numerator/denominator form (Example:
     *                               Csc[x] gives 1 / Sin[x])
     * @param evalParts              evaluate the determined parts
     * @return the numerator and denominator expression and an optional fractional
     * number (maybe <code>null</code>), if splitNumeratorOne is
     * <code>true</code>.
     */
    public static IExpr[] fractionalPartsTimesPower(final IAST timesPower, boolean splitNumeratorOne,
                                                    boolean splitFractionalNumbers, boolean trig, boolean evalParts) {
        if (timesPower.isPower()) {
            IAST powerAST = timesPower;
            IExpr[] parts = Apart.fractionalPartsPower(powerAST, trig);
            if (parts != null) {
                return parts;
            }
            return null;
        }

        IAST timesAST = timesPower;
        IExpr[] result = new IExpr[3];
        result[2] = null;
        IAST numerator = F.TimesAlloc(timesAST.size());
        IAST denominator = F.TimesAlloc(timesAST.size());

        IExpr arg;
        IAST argAST;
        boolean evaled = false;
        boolean splitFractionEvaled = false;
        for (int i = 1; i < timesAST.size(); i++) {
            arg = timesAST.get(i);
            if (arg.isAST()) {
                argAST = (IAST) arg;
                if (trig && argAST.isAST1()) {
                    IExpr numerForm = Numerator.getTrigForm(argAST, trig);
                    if (numerForm.isPresent()) {
                        IExpr denomForm = Denominator.getTrigForm(argAST, trig);
                        if (denomForm.isPresent()) {
                            if (!numerForm.isOne()) {
                                numerator.append(numerForm);// numerator.addMerge(numerForm);
                            }
                            if (!denomForm.isOne()) {
                                denominator.append(denomForm);// denominator.addMerge(denomForm);
                            }
                            evaled = true;
                            continue;
                        }
                    }
                } else if (arg.isPower()) {
                    IExpr[] parts = Apart.fractionalPartsPower((IAST) arg, trig);
                    if (parts != null) {
                        if (!parts[0].isOne()) {
                            numerator.append(parts[0]); // numerator.addMerge(parts[0]);
                        }
                        if (!parts[1].isOne()) {
                            denominator.append(parts[1]);// denominator.addMerge(parts[1]);
                        }
                        evaled = true;
                        continue;
                    }
                }
            } else if (i == 1 && arg.isFraction()) {
                if (splitNumeratorOne) {
                    IFraction fr = (IFraction) arg;
                    if (fr.getNumerator().isOne()) {
                        denominator.append(fr.getDenominator()); // denominator.addMerge(fr.getDenominator());
                        splitFractionEvaled = true;
                        continue;
                    }
                    if (fr.getNumerator().isMinusOne()) {
                        numerator.append(fr.getNumerator()); // numerator.addMerge(fr.getNumerator());
                        denominator.append(fr.getDenominator());// denominator.addMerge(fr.getDenominator());
                        splitFractionEvaled = true;
                        continue;
                    }
                    result[2] = fr;
                    continue;
                } else if (splitFractionalNumbers) {
                    IFraction fr = (IFraction) arg;
                    if (!fr.getNumerator().isOne()) {
                        numerator.append(fr.getNumerator()); // numerator.addMerge(fr.getNumerator());
                    }
                    denominator.append(fr.getDenominator()); // denominator.addMerge(fr.getDenominator());
                    evaled = true;
                    continue;
                }
            }
            numerator.append(arg);
        }
        if (evaled) {
            if (evalParts) {
                result[0] = F.eval(numerator);
                result[1] = F.eval(denominator);
            } else {
                result[0] = numerator.getOneIdentity(F.C1);
                result[1] = denominator.getOneIdentity(F.C1);
            }
            if (result[0].isNegative() && result[1].isPlus() && ((IAST) result[1]).isAST2()) {
                // negate numerator and denominator:
                result[0] = result[0].negate();
                result[1] = result[1].negate();
            }
            return result;
        }
        if (splitFractionEvaled) {
            result[0] = numerator.getOneIdentity(F.C1);// numerator.getProduct();
            if (!result[0].isTimes() && !result[0].isPlus()) {
                result[1] = denominator.getOneIdentity(F.C1); // denominator.getProduct();
                return result;
            }
            if (result[0].isTimes() && ((IAST) result[0]).isAST2() && ((IAST) result[0]).arg1().isMinusOne()) {
                result[1] = denominator.getOneIdentity(F.C1); // denominator.getProduct();
                return result;
            }
        }
        return null;
    }

    /**
     * Split the expression into numerator and denominator parts, by separating
     * positive and negative powers.
     *
     * @param arg
     * @param trig determine the denominator by splitting up functions like
     *             <code>Tan[],Cot[], Csc[],...</code>
     * @return the numerator and denominator expression or <code>null</code> if no
     * denominator was found.
     */
    public static IExpr[] fractionalParts(final IExpr arg, boolean trig) {
        IExpr[] parts = null;
        if (arg.isAST()) {
            IAST ast = (IAST) arg;
            if (arg.isTimes()) {
                parts = fractionalPartsTimesPower(ast, false, true, trig, true);
            } else if (arg.isPower()) {
                parts = Apart.fractionalPartsPower(ast, trig);
                if (parts == null) {
                    return null;
                }
            } else {

                IExpr numerForm = Numerator.getTrigForm(ast, trig);
                if (numerForm.isPresent()) {
                    IExpr denomForm = Denominator.getTrigForm(ast, trig);
                    if (denomForm.isPresent()) {
                        parts = new IExpr[2];
                        parts[0] = numerForm;
                        parts[1] = denomForm;
                        return parts;
                    }
                }
            }
        }
        return parts;
    }

    /**
     * Returns an AST with head <code>Plus</code>, which contains the partial
     * fraction decomposition of the numerator and denominator parts.
     *
     * @param pf       partial fraction generator
     * @param parts
     * @param variable a variable
     * @return <code>F.NIL</code> if the partial fraction decomposition wasn't
     * constructed
     */
    public static IExpr partialFractionDecompositionRational(IPartialFractionGenerator pf, IExpr[] parts,
                                                             IExpr variable) {
        try {
            IAST variableList = F.List(variable);
            IExpr exprNumerator = F.evalExpandAll(parts[0]);
            IExpr exprDenominator = F.evalExpandAll(parts[1]);
            ASTRange r = new ASTRange(variableList, 1);
            List<IExpr> varList = r;

            String[] varListStr = new String[1];
            varListStr[0] = variableList.arg1().toString();
            JASConvert<BigRational> jas = new JASConvert<BigRational>(varList, BigRational.ZERO);
            GenPolynomial<BigRational> numerator = jas.expr2JAS(exprNumerator, false);
            GenPolynomial<BigRational> denominator = jas.expr2JAS(exprDenominator, false);

            // get factors
            FactorAbstract<BigRational> factorAbstract = FactorFactory.getImplementation(BigRational.ZERO);
            SortedMap<GenPolynomial<BigRational>, Long> sfactors = factorAbstract.baseFactors(denominator);

            List<GenPolynomial<BigRational>> D = new ArrayList<GenPolynomial<BigRational>>(sfactors.keySet());

            SquarefreeAbstract<BigRational> sqf = SquarefreeFactory.getImplementation(BigRational.ZERO);
            List<List<GenPolynomial<BigRational>>> Ai = sqf.basePartialFraction(numerator, sfactors);
            // returns [ [Ai0, Ai1,..., Aie_i], i=0,...,k ] with A/prod(D) =
            // A0 + sum( sum ( Aij/di^j ) ) with deg(Aij) < deg(di).

            if (Ai.size() > 0) {
                // IAST result = F.Plus();
                pf.allocPlus(Ai.size() * 2);
                pf.setJAS(jas);
                if (!Ai.get(0).get(0).isZERO()) {
                    pf.addNonFractionalPart(Ai.get(0).get(0));
                }
                for (int i = 1; i < Ai.size(); i++) {
                    List<GenPolynomial<BigRational>> list = Ai.get(i);
                    int j = 0;
                    for (GenPolynomial<BigRational> genPolynomial : list) {
                        if (!genPolynomial.isZERO()) {
                            GenPolynomial<BigRational> Di_1 = D.get(i - 1);
                            pf.addSinglePartialFraction(genPolynomial, Di_1, j);
                        }
                        j++;
                    }

                }
                return pf.getResult();
            }
        } catch (JASConversionException e) {
            if (Config.DEBUG) {
                e.printStackTrace();
            }
        }
        return F.NIL;
    }

    /**
     * Split the expression into numerator and denominator parts, by separating
     * positive and negative powers.
     *
     * @param arg
     * @return the numerator and denominator expression
     */
    public static IExpr[] fractionalPartsRational(final IExpr arg) {
        if (arg.isFraction()) {
            IFraction fr = (IFraction) arg;
            IExpr[] parts = new IExpr[2];
            parts[0] = fr.getNumerator();
            parts[1] = fr.getDenominator();
            return parts;
        }
        return fractionalParts(arg, false);
    }

    public static IExpr together(IAST ast, EvalEngine engine) {
        IExpr temp = expandAll(ast, null, true, false, engine);
        if (!temp.isPresent()) {
            temp = ast;
        }
        if (temp.isAST()) {
            IExpr result = Together.togetherPlusTimesPower((IAST) temp, engine);
            if (result.isPresent()) {
                return F.eval(result);
            }
        }
        return temp;
    }

    public static Algebra initialize() {
        return CONST;
    }


}
