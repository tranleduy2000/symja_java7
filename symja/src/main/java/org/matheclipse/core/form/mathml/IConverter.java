package org.matheclipse.core.form.mathml;

import org.matheclipse.core.interfaces.IAST;

/** 
 * General conversion interface
 */
public interface IConverter {
  /**
   * Converts a given function into the corresponding MathML output
   * 
   *@param  buffer    StringBuilder for MathML output
   *@param  function  the math function which should be converted to MathML
   */
  public boolean convert(StringBuilder buffer, IAST function, int precedence);
  
  public void setFactory(final AbstractMathMLFormFactory factory);
}
 