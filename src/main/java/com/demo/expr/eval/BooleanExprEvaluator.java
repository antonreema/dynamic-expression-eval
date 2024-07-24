package com.demo.expr.eval;

import java.util.Map;

import com.demo.expr.config.SimpleBooleanBaseVisitor;
import com.demo.expr.config.SimpleBooleanParser;

public class BooleanExprEvaluator extends SimpleBooleanBaseVisitor<Object> {

	private final Map<String, Object> variables;

	public BooleanExprEvaluator(Map<String, Object> variables) {
		this.variables = variables;
	}

	@Override
	public Object visitParse(SimpleBooleanParser.ParseContext ctx) {
		return super.visit(ctx.expression());
	}

	@Override
	public Object visitDecimalExpression(SimpleBooleanParser.DecimalExpressionContext ctx) {
		return Double.valueOf(ctx.DECIMAL().getText());
	}

	@Override
	public Object visitIdentifierExpression(SimpleBooleanParser.IdentifierExpressionContext ctx) {
		if(variables.get(ctx.IDENTIFIER().getText()) != null) {
			return variables.get(ctx.IDENTIFIER().getText());
		}else {
			return null;
		}
		
	}

	@Override
	public Object visitNotExpression(SimpleBooleanParser.NotExpressionContext ctx) {
		return !((Boolean) this.visit(ctx.expression()));
	}

	@Override
	public Object visitParenExpression(SimpleBooleanParser.ParenExpressionContext ctx) {
		return super.visit(ctx.expression());
	}

	@Override
	public Object visitComparatorExpression(SimpleBooleanParser.ComparatorExpressionContext ctx) {
		if (ctx.op.EQ() != null) {
			return this.visit(ctx.left).equals(this.visit(ctx.right));
		}else if(ctx.op.IN() != null) {
			String checkVal[] = ctx.right.getText().split(",");
			boolean valuePresent = false;
			String inputVal = ctx.left.getText();
			
			if(visit(ctx.left) != null) {
				inputVal = this.visit(ctx.left).toString();
			}
			for(String value : checkVal) {
				if(value.equals(inputVal))
					valuePresent = true;
			}
			return valuePresent;
		}else if (ctx.op.NE() != null) {
			if(visit(ctx.left) != null) {
				return !this.visit(ctx.left).equals(this.visit(ctx.right));
			}else {
				return !ctx.left.getText().equals(ctx.right.getText());
			}
			
		}else if(ctx.op.NOTIN() != null) {
			String checkVal[] = ctx.right.getText().split(",");
			boolean valueNotPresent = true;
			String inputVal = ctx.left.getText();
			
			if(visit(ctx.left) != null) {
				inputVal = this.visit(ctx.left).toString();
			}
			for(String value : checkVal) {
				if(value.equals(inputVal))
					valueNotPresent = false;
			}
			return valueNotPresent;
		}else if(ctx.op.STARTSWITH() != null) {
			boolean startsWith = false;
			if((visit(ctx.left)) != null) {
				if(this.visit(ctx.left).toString().startsWith(ctx.right.getText())) {
					startsWith = true;
				}
			}else if(ctx.left.getText().startsWith(ctx.right.getText())) {
				startsWith = true;
			}
			return startsWith;
		}else if(ctx.op.ENDSWITH() != null) {
			boolean endsWith = false;
			if((visit(ctx.left)) != null) {
				if(this.visit(ctx.left).toString().startsWith(ctx.right.getText())) {
					endsWith = true;
				}
			}else if(ctx.left.getText().startsWith(ctx.right.getText())) {
				endsWith = true;
			}
			return endsWith;
		}else if(ctx.op.LE() != null) {
			return asDouble(ctx.left) <= asDouble(ctx.right);
		}else if(ctx.op.GE() != null) {
			return asDouble(ctx.left) >= asDouble(ctx.right);
		}else if(ctx.op.LT() != null) {
			return asDouble(ctx.left) < asDouble(ctx.right);
		}else if(ctx.op.GT() != null) {
			return asDouble(ctx.left) > asDouble(ctx.right);
		}
		throw new RuntimeException("not implemented: comparator operator " + ctx.op.getText());
	}
	
	@Override
	public Object visitBinaryExpression(SimpleBooleanParser.BinaryExpressionContext ctx) {
		if(ctx.op.AND() != null) {
			return asBoolean(ctx.left) && asBoolean(ctx.right);
		}else if(ctx.op.OR() != null) {
			return asBoolean(ctx.left) || asBoolean(ctx.right);
		}
		throw new RuntimeException("not implemented: binary operator " + ctx.op.getText());
	}
	
	public Object visitBoolExpression(SimpleBooleanParser.BoolExpressionContext ctx) {
		return Boolean.valueOf(ctx.getText());
	}
	
	private boolean asBoolean(SimpleBooleanParser.ExpressionContext ctx) {
		return (boolean)visit(ctx);
	}
	
	private double asDouble(SimpleBooleanParser.ExpressionContext ctx) {
		return (double)visit(ctx);
	}
}
