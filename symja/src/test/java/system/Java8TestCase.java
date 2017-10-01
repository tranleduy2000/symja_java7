package system;

import static org.matheclipse.core.expression.F.*;

import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;

/**
 * Tests for the Java port of the
 * <a href="http://www.apmaths.uwo.ca/~arich/">Rubi - rule-based integrator</a>.
 * 
 */
public class Java8TestCase extends AbstractTestCase {
	public Java8TestCase(String name) {
		super(name);
	}

	public void testForeach() {
		IAST ast = List(C10, a, b, c, d, e);
		IAST result = F.List();
		ast.forEach(x -> result.append(x));
		assertEquals("{10,a,b,c,d,e}", result.toString());
	}

	public void testStream001() {
		IAST ast = List(C10, a, b, c, d, e);
		IAST result = F.List();
		// Consumer<IExpr> action = (IExpr x) -> System.out.println(x);
		ast.stream().forEach(x -> result.append(x));
		ast.stream(0, 7).forEach(x -> result.append(x));
		assertEquals("{10,a,b,c,d,e,List,10,a,b,c,d,e}", result.toString());
	}

}
