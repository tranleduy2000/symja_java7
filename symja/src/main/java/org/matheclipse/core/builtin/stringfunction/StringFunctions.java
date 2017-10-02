package org.matheclipse.core.builtin.stringfunction;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IExpr;

import java.io.IOException;

public final class StringFunctions {

    final static StringFunctions CONST = new StringFunctions();

    static {
        F.LetterQ.setEvaluator(new LetterQ());
        F.LowerCaseQ.setEvaluator(new LowerCaseQ());
        F.StringDrop.setEvaluator(new StringDrop());
        F.StringJoin.setEvaluator(new StringJoin());
        F.StringLength.setEvaluator(new StringLength());
        F.StringTake.setEvaluator(new StringTake());
        F.SyntaxLength.setEvaluator(new SyntaxLength());
        F.ToCharacterCode.setEvaluator(new ToCharacterCode());
        F.ToString.setEvaluator(new ToString());
        F.ToUnicode.setEvaluator(new ToUnicode());
    }

    private StringFunctions() {

    }

    public static String outputForm(final IExpr expression) {
        try {
            StringBuilder buf = new StringBuilder();
            OutputFormFactory off = OutputFormFactory.get();
            off.setIgnoreNewLine(true);
            off.convert(buf, expression);
            return buf.toString();
        } catch (IOException e) {
            if (Config.SHOW_STACKTRACE) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static StringFunctions initialize() {
        return CONST;
    }


}
