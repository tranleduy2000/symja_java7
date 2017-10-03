package org.matheclipse.core.system;

import junit.framework.TestCase;

import org.matheclipse.core.basic.Config;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.ExprEvaluator;
import org.matheclipse.core.eval.TimeConstrainedEvaluator;
import org.matheclipse.core.form.output.OutputFormFactory;
import org.matheclipse.core.interfaces.IAST;

import java.io.StringWriter;

/**
 * Tests system.reflection classes
 */
public abstract class AbstractTestCase extends TestCase {

    public AbstractTestCase(String name) {
        super(name);
        Config.SERVER_MODE = false;
    }

    public void check(String evalString, String expectedResult) {
    }

    public void check(String evalString, String expectedResult, int resultLength) {
        try {
            if (evalString.length() == 0 && expectedResult.length() == 0) {
                return;
            }
            // scriptEngine.put("STEPWISE",Boolean.TRUE);
            String evaledResult = new ExprEvaluator().eval(evalString).toString();

            if (resultLength > 0 && evaledResult.length() > resultLength) {
                evaledResult = evaledResult.substring(0, resultLength) + "<<SHORT>>";
                assertEquals(expectedResult, evaledResult);
            } else {
                assertEquals(expectedResult, evaledResult);
            }
        } catch (Exception e) {
            e.printStackTrace();
            assertEquals("", "1");
        }
    }

    public void checkNumeric(String evalString, String expectedResult) {
        check(evalString, expectedResult, -1);
    }

    public void checkNumeric(String evalString, String expectedResult, int resultLength) {
        check(evalString, expectedResult, resultLength);
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
