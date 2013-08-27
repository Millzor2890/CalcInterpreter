package src;

/**
 * Sin.java
 * 
 * Version:
 * 	$Id: Sine.java,v 1.1 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision:
 * 	$Log: Sine.java,v $
 * 	Revision 1.1  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 	Revision 1.5  2010/04/16 20:21:50  asm2242
 * 	Added the CharacterPlotter which works like a dream.  Everything still needs comments and tidying up but is otherwise finished.
 * 	
 * 	Revision 1.4  2010/04/15 20:03:12  asm2242
 * 	Implemented integral method. Works for all classes.  New classes are still not commented.
 * 	
 * 	Revision 1.3  2010/04/14 05:53:16  asm2242
 * 	Added the Cosine class
 * 	
 * 	Revision 1.2  2010/04/14 05:30:19  asm2242
 * 	*** empty log message ***
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
 *
 *
 */
public class Sine extends Function {
	
	private Function SIN_TEMP;
	/**
	 * Constructor method that takes a function arg
	 * @param f
	 * Function given
	 */
	public Sine(Function f){
		SIN_TEMP = f;	
	}
	
	/**
	 * Determines the derivative of Cosine
	 */
	public Function derivative() {
		Function inside_terms = SIN_TEMP;
		return new Product(new Cosine(SIN_TEMP),inside_terms.derivative());

	}
	

	/**
	 * 
	 * Evaluates Sine at a given eval
	 * 
	 * @param eval
	 * given double to evaluate Sine at
	 * @return
	 * returns a double of Sine evaluated at eval 
	 */
	public double evaluate(double eval) {
		return Math.sin(SIN_TEMP.evaluate(eval));
	}


	/**
	 * Determines whether or not a function is constant
	 */
	public boolean isConstant() {
		boolean isconstant = true;
		if(SIN_TEMP.isConstant()==false){
			isconstant = false;		
		}	
		return isconstant;
	}
	
	/**
	 * Generates a String representation of the Sine Function
	 * 
	 * @returns
	 * returns a String representation of a Sine Function
	 * 
	 */
	public String toString() {
		String Output = SIN_TEMP.toString();

		return "sin("+Output+")";
	}

	/**
	 * Creates a function that is itself and calls the integral method in the superclass
	 * 
	 * @param a
	 * lower value X is evaluated at
	 * 
	 * @param b
	 * higher value that X is evaluated at
	 * 
	 * @param N
	 * number of subintervals
	 * 
	 * @return
	 * returns a double that is the integral evaluated from a to b
	 */
	public double integral(double a, double b, double N) {
		Function A_Sin = new Sine(SIN_TEMP);
		return integral(A_Sin,a,b,N);
	}

}
