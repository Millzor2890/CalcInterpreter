package src;

/**
 * Variable.java
 * 
 * Version:
 * 	$Id: Variable.java,v 1.8 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision:
 * 	$Log: Variable.java,v $
 * 	Revision 1.8  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 	Revision 1.7  2010/04/16 20:21:50  asm2242
 * 	Added the CharacterPlotter which works like a dream.  Everything still needs comments and tidying up but is otherwise finished.
 * 	
 * 	Revision 1.6  2010/04/15 20:03:12  asm2242
 * 	Implemented integral method. Works for all classes.  New classes are still not commented.
 * 	
 * 	Revision 1.5  2010/04/14 05:29:20  asm2242
 * 	added Sum class
 * 	
 * 	Revision 1.4  2010/04/07 15:38:05  asm2242
 * 	Part one of the project is complete, commented, and ready for submission.
 * 	
 * 	Revision 1.3  2010/04/05 15:20:41  asm2242
 * 	Got the derivative in Sum to work properly. Need to touch up the toString and comment it all.
 * 	
 * 	Revision 1.2  2010/04/02 16:35:05  asm2242
 * 	Added sum class, all it can do is evaluate though;
 * 	
 * 
 * @author Adam Miller(asm2242)
 * @param <X>
 *
 */
public class Variable extends Function {
	/**
	 * The only instance of variable is X
	 */
	public final static Variable X = new Variable();
	/**
	 * Constructor class(unused)
	 */
	private Variable(){
		
	}
	/**
	 * 
	 * Finds the derivative of X;
	 * 
	 * @return 
	 * Returns a function that represents the derivative of X which is always 1
	 */
	public Function derivative() {
		return new Constant(1);
	}
	
	
	/**
	 * Function that returns the value of X evaluated at a given number
	 * 
	 * @param
	 * Takes a double argument to evaluate X at
	 * @return
	 * returns a double eval that is the evaluation of X at that number
	 */
	public double evaluate(double eval) {
		return eval;
	}

	/**
	 * 
	 * 
	 * @return
	 * returns false because the variable X is not a constant
	 */
	public boolean isConstant() {
		return false;
	}
	/**
	 * @return
	 * returns a Sting representation of the variable X
	 */
	public String toString(){
		return "x";
	}
	/**
	 * Creates a function that is itself and calls the integral method in the superclass
	 * 
	 * @param a
	 * lower value X is evaluated at
	 * @param b
	 * higher value that X is evaluated at
	 * @param N
	 * number of subintervals
	 * @return
	 * returns a double that is the integral evaluated from a to b
	 */
	public double integral(double a, double b, double N) {
		return integral(Variable.X,a,b,N);
	}

}
