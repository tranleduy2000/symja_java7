package org.matheclipse.core.form.tex.reflection;

import org.matheclipse.core.form.tex.AbstractConverter;
import org.matheclipse.core.interfaces.IAST;

public class Floor extends AbstractConverter {

  /** constructor will be called by reflection */
  public Floor() {
  }

  /** {@inheritDoc} */
  @Override
public boolean convert(final StringBuilder buf, final IAST f, final int precedence) {
    if (f.size() != 2) {
      return false;
    }
    buf.append(" \\left \\lfloor ");
    fFactory.convert(buf, f.arg1(), 0);
    buf.append(" \\right \\rfloor ");
    return true;
  }
}