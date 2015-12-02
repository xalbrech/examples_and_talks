package expressions.simple;

import com.oracle.truffle.api.frame.VirtualFrame;

public abstract class EvaluatedExpression /*extends Node */{
	
	/*@Children */protected EvaluatedExpression[] arguments;
	
	public EvaluatedExpression(EvaluatedExpression... arguments) {
		this.arguments = arguments;
	}
	
	public abstract double evaluate(VirtualFrame frame);
	
}
