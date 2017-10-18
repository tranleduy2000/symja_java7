package org.matheclipse.core.system;

import junit.framework.TestCase;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TimeConstrainedEvaluator;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;

import java.io.StringWriter;

/**
 * Tests system.reflection classes
 */
public abstract class AbstractTestCase extends TestCase {

    public AbstractTestCase(String name) {
        super(name);
        Config.SERVER_MODE = false;
    }

    public void check(String input, String expected) {
        try {
            if (input.length() == 0 && expected.length() == 0) {
                return;
            }
            IExpr evaluated = new ExprEvaluator().eval(input);
            String result = evaluated.toString();
            if (input.startsWith("FullForm") ||
                    input.contains("ToString") ||
                    input.startsWith("StringJoin") ||
                    input.startsWith("JavaForm") ||
                    input.startsWith("FromCharacterCode")) {
                assertEquals(expected, evaluated.fullFormString());
            }
            if (expected.length() > 0  &&result.length() > expected.length()) {
                String temp = result.substring(0, expected.length() - 1);
                String temp2 = expected.substring(0, expected.length() - 1);
                if (temp.equals(temp2)) {
                    assertEquals(temp, temp2);
                } else {
                    assertEquals(expected, result);
                }
            } else {
                if (expected.isEmpty()) {
                    assertEquals("Null", result);
                } else {
                    assertEquals(expected, result);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("", "1");
        }
    }

    public void checkNumeric(String evalString, String expectedResult) {
        check(evalString, expectedResult);
    }

    public void check(IAST ast, String strResult) {
        check(EvalEngine.get(), true, ast, strResult);
    }

    public void check(EvalEngine engine, boolean configMode, IAST ast, String strResult) {
        boolean mode = Config.SERVER_MODE;
        try {
            StringWriter buf = new StringWriter();

            Config.SERVER_MODE = configMode;
            if (Config.SERVER_MODE) {
                IAST inExpr = ast;
                TimeConstrainedEvaluator utility = new TimeConstrainedEvaluator(engine, false, Config.FOREVER);
                utility.constrainedEval(buf, inExpr);
            } else {
                if (ast != null) {
                    OutputFormFactory off = OutputFormFactory.get();
                    off.setIgnoreNewLine(true);
                    off.convert(buf, ast);
                }
            }

            assertEquals(strResult, buf.toString());
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("", "1");
        } finally {
            Config.SERVER_MODE = mode;
        }
    }

    public void checkSVGGraphics(String evalString, String expectedResult) {
    }


    /**
     * The JUnit setup method
     */
    @Override
    protected void setUp() {
        try {

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
