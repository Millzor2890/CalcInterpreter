package src;
/**
 * 
 * Function.java
 * 
 * Version:
 * 	$Id: Function.java,v 1.5 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision
 * 	$Log: Function.java,v $
 * 	Revision 1.5  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 	Revision 1.4  2010/04/16 20:21:50  asm2242
 * 	Added the CharacterPlotter which works like a dream.  Everything still needs comments and tidying up but is otherwise finished.
 * 	
 * 	Revision 1.3  2010/04/15 20:03:12  asm2242
 * 	Implemented integral method. Works for all classes.  New classes are still not commented.
 * 	
 * 	Revision 1.2  2010/04/14 05:29:20  asm2242
 * 	added Sum class
 * 	
 * 	Revision 1.1  2010/03/30 01:15:21  asm2242
 * 	Initial revision made it up to the variable class. Not sure what to do .
 * 	
 * 
 * @author Adam Miller(asm2242)
 *
 */
public abstract class Function {
	
	/**
	 * Given a double floating point value computes the value of the function
	 * 
	 * @param eval
	 * double that will represent a variables value
	 * 
	 * @return
	 * returns the value of the function
	 */
	public abstract double evaluate(double eval );
	
	/**
	 * 
	 * Write the function as a readable String
	 * 
	 * @return
	 * returns a human readable String output
	 */
	public abstract String toString();
	/**
	 * 
	 * @return
	 * returns the function of the first derivative with respect to x
	 */
	public abstract Function derivative();
	/**
	 * Determines whether or not a Function is a constant
	 * 
	 * @param possible_constant
	 * Function that is input 
	 * 
	 * @return
	 * returns true or false based on whether or not a function is a constant or not
	 */
	
	public abstract boolean isConstant();
	/**
	 * 
	 * A function that is in each method that will call the non abstract integral method
	 * in the Function class
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
	public abstract double integral(double a,double b,double N);
	/**
	 * 
	 * This method is called by all sub-classes. It takes a function and then
	 * runs it through the trapezoidal method of determining an integral
	 * and then returns that double
	 * 
	 * @param f
	 * Given function-Different in each subclass
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
	public double integral(Function f, double a, double b, double N ){
		double Final_Integral = f.evaluate(a)/2+f.evaluate(b)/2;
		for(int k = 1;k<N;k++){
			Final_Integral+=f.evaluate(a+(b-a)*(k/N));
		}
		return(b-a)*Final_Integral/N;
	}
	


}
