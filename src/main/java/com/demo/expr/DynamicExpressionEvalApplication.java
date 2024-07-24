package com.demo.expr;

import java.util.HashMap;
import java.util.Map;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import com.demo.expr.config.SimpleBooleanLexer;
import com.demo.expr.config.SimpleBooleanParser;
import com.demo.expr.eval.BooleanExprEvaluator;

public class DynamicExpressionEvalApplication {

	public static void main(String[] args) {
		Map<String, Object> variables = new HashMap<String, Object>() {{
		      put("A", true);
		      put("a", true);
		      put("B", false);
		      put("b", false);
		      put("C", 42.0);
		      put("c", 42.0);
		      put("D", -999.0);
		      put("d", -1999.0);
		      put("E", 42.001);
		      put("e", 142.001);
		      put("F", 42.001);
		      put("f", 42.001);
		      put("G", -1.0);
		      put("g", -1.0);
		    }};

		    String[] expressions = {
		        "1 > 2",
		        "1 >= 1.0",
		        "TRUE = FALSE",
		        "FALSE = FALSE",
		        "A OR B",
		        "B",
		        "A = B",
		        "c = C",
		        "E > D",
		        "B OR (c = B OR (A = A AND c = C AND E > D))",
		        "(A = a OR B = b OR C = c AND ((D = d AND E = e) OR (F = f AND G = g)))"
		    };

		    for (String expression : expressions) {
		      SimpleBooleanLexer lexer = new SimpleBooleanLexer(CharStreams.fromString(expression));
		      SimpleBooleanParser parser = new SimpleBooleanParser(new CommonTokenStream(lexer));
		      Object result = new BooleanExprEvaluator(variables).visit(parser.parse());
		      System.out.printf("%-70s -> %s\n", expression, result);
		    }
	}

}
