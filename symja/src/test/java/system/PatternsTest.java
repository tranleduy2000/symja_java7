package system;

import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.patternmatching.PatternMatcher;

import static org.matheclipse.core.expression.F.Times;
import static org.matheclipse.core.expression.F.a;
import static org.matheclipse.core.expression.F.a_;
import static org.matheclipse.core.expression.F.ast;
import static org.matheclipse.core.expression.F.f;
import static org.matheclipse.core.expression.F.x;
import static org.matheclipse.core.expression.F.x_;

/**
 * Tests for the Java port of the
 * <a href="http://www.apmaths.uwo.ca/~arich/">Rubi - rule-based integrator</a>.
 */
public class PatternsTest extends AbstractTestCase {
    public PatternsTest(String name) {
        super(name);
    }

    @Override
    public void check(String evalString, String expectedResult) {
        check(evalString, expectedResult, -1);
    }

    public void testPriority001() {

        IAST ast1 = ast(f);
        ast1.append(a_);
        IAST ast2 = ast(f);
        ast2.append(Times(a_, x_));
        PatternMatcher pm1 = new PatternMatcher(ast1);
        PatternMatcher pm2 = new PatternMatcher(ast2);
        int cpr = pm1.equivalentTo(pm2);
        assertEquals(cpr, 1);

    }

    public void testPriority002() {

        IAST ast1 = ast(f);
        ast1.append(Times(a, x));
        IAST ast2 = ast(f);
        ast2.append(Times(a_, x_));
        PatternMatcher pm1 = new PatternMatcher(ast1);
        PatternMatcher pm2 = new PatternMatcher(ast2);
        int cpr = pm1.equivalentTo(pm2);
        assertEquals(cpr, -1);

    }

}
