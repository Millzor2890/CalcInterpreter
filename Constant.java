package src;

/**
 * Constant.java
 * 
 * Version:
 * 	$Id: Constant.java,v 1.8 2010/04/18 03:24:11 asm2242 Exp $
 * 
 * Revision
 * 	$Log: Constant.java,v $
 * 	Revision 1.8  2010/04/18 03:24:11  asm2242
 * 	Fixed minor spelling errors Ready for submission
 * 	
 * 	Revision 1.7  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 	Revision 1.6  2010/04/15 20:03:12  asm2242
 * 	Implemented integral method. Works for all classes.  New classes are still not commented.
 * 	
 * 	Revision 1.5  2010/04/14 05:29:19  asm2242
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
 *
 */
public class Constant extends Function {
	private double constant;
	/**
	 * Constructor method for Constant
	 * @param c
	 * takes a number to set the constant to
	 */
	public Constant(double c){
		this.constant = c;
		
	}
	/**
	 * @return
	 * creates a String representation of the Constant
	 */
	public String toString(){
		return Double.toString(constant);
	}
	/**
	 * Finds the derivative of a constant
	 * 
	 * @return
	 * returns a Function that represents the derivative of a constant
	 */
	public Function derivative() {
		return new Constant(0);
	}

	/**
	 * 
	 * 
	 * @param
	 * takes a double eval to evaluate a constant at
	 * @return
	 * returns the constant because constants cannot be evaluated at a number
	 */
	public double evaluate(double eval) {
		return constant;
	}

	/**
	 * Checks to see if the constant is a constant. It usually is.
	 * 
	 * @return
	 * returns true because constants are constants
	 */
	public boolean isConstant() {
		return true;
	}
	/**
	 * creates a function that is itself and then passes it to the function in the 
	 * Superclass
	 * 
	 * @param a
	 * lower value X is evaluated at
	 * @param b
	 * higher value that X is evaluated at
	 * @param N
	 * Number of subintervals
	 * @return
	 * a double representing the integral evaluated from a to b
	 * 
	 */
	public double integral(double a, double b, double N){
			Function A_Constant = new Constant(constant);
			return integral(A_Constant,a,b,N);
	}


}
