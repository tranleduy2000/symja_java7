package org.matheclipse.core.reflection.system.rules;

import static org.matheclipse.core.expression.F.*;
import org.matheclipse.core.interfaces.IAST;

/**
 * <p>Generated by <code>org.matheclipse.core.preprocessor.RulePreprocessor</code>.</p>
 * <p>See GIT repository at: <a href="https://bitbucket.org/axelclk/symja_android_library">bitbucket.org/axelclk/symja_android_library under the tools directory</a>.</p>
 */
public interface SinhRules {
  /**
   * <ul>
   * <li>index 0 - number of equal rules in <code>RULES</code></li>
	 * </ul>
	 */
  final public static int[] SIZES = { 19, 0 };

  final public static IAST RULES = List(
    IInit(Sinh, SIZES),
    // Sinh(0)=0
    ISet(Sinh(C0),
      C0),
    // Sinh(1/6*Pi*I)=I/2
    ISet(Sinh(Times(CC(0L,1L,1L,6L),Pi)),
      CC(0L,1L,1L,2L)),
    // Sinh(1/4*Pi*I)=1/2*Sqrt(2)*I
    ISet(Sinh(Times(CC(0L,1L,1L,4L),Pi)),
      Times(CC(0L,1L,1L,2L),CSqrt2)),
    // Sinh(1/3*Pi*I)=1/2*Sqrt(3)*I
    ISet(Sinh(Times(CC(0L,1L,1L,3L),Pi)),
      Times(CC(0L,1L,1L,2L),CSqrt3)),
    // Sinh(1/2*Pi*I)=I
    ISet(Sinh(Times(CC(0L,1L,1L,2L),Pi)),
      CI),
    // Sinh(2/3*Pi*I)=1/2*Sqrt(3)*I
    ISet(Sinh(Times(CC(0L,1L,2L,3L),Pi)),
      Times(CC(0L,1L,1L,2L),CSqrt3)),
    // Sinh(3/4*Pi*I)=1/2*Sqrt(2)*I
    ISet(Sinh(Times(CC(0L,1L,3L,4L),Pi)),
      Times(CC(0L,1L,1L,2L),CSqrt2)),
    // Sinh(5/6*Pi*I)=I/2
    ISet(Sinh(Times(CC(0L,1L,5L,6L),Pi)),
      CC(0L,1L,1L,2L)),
    // Sinh(Pi*I)=0
    ISet(Sinh(Times(CI,Pi)),
      C0),
    // Sinh(7/6*Pi*I)=-I/2
    ISet(Sinh(Times(CC(0L,1L,7L,6L),Pi)),
      CC(0L,1L,-1L,2L)),
    // Sinh(5/4*Pi*I)=-1/2*Sqrt(2)*I
    ISet(Sinh(Times(CC(0L,1L,5L,4L),Pi)),
      Times(CC(0L,1L,-1L,2L),CSqrt2)),
    // Sinh(4/3*Pi*I)=-1/2*Sqrt(3)*I
    ISet(Sinh(Times(CC(0L,1L,4L,3L),Pi)),
      Times(CC(0L,1L,-1L,2L),CSqrt3)),
    // Sinh(3/2*Pi*I)=-I
    ISet(Sinh(Times(CC(0L,1L,3L,2L),Pi)),
      CNI),
    // Sinh(5/3*Pi*I)=-1/2*Sqrt(3)*I
    ISet(Sinh(Times(CC(0L,1L,5L,3L),Pi)),
      Times(CC(0L,1L,-1L,2L),CSqrt3)),
    // Sinh(7/4*Pi*I)=-1/2*Sqrt(2)*I
    ISet(Sinh(Times(CC(0L,1L,7L,4L),Pi)),
      Times(CC(0L,1L,-1L,2L),CSqrt2)),
    // Sinh(11/6*Pi*I)=-I/2
    ISet(Sinh(Times(CC(0L,1L,11L,6L),Pi)),
      CC(0L,1L,-1L,2L)),
    // Sinh(2*Pi*I)=0
    ISet(Sinh(Times(CC(0L,1L,2L,1L),Pi)),
      C0),
    // Sinh(Infinity)=Infinity
    ISet(Sinh(oo),
      oo),
    // Sinh(ComplexInfinity)=Indeterminate
    ISet(Sinh(CComplexInfinity),
      Indeterminate)
  );
}
