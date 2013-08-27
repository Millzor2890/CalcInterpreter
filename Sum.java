package src;

/**
 * Sum.java
 * 
 * Version:
 * 		$Id: Sum.java,v 1.6 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision:
 * 		$Log: Sum.java,v $
 * 		Revision 1.6  2010/04/17 00:51:40  asm2242
 * 		Fully commented and ready for submission.
 * 		
 * 		Revision 1.5  2010/04/15 20:03:12  asm2242
 * 		Implemented integral method. Works for all classes.  New classes are still not commented.
 * 		
 * 		Revision 1.4  2010/04/14 05:29:19  asm2242
 * 		added Sum class
 * 		
 * 		Revision 1.3  2010/04/07 15:38:05  asm2242
 * 		Part one of the project is complete, commented, and ready for submission.
 * 		
 * 		Revision 1.2  2010/04/05 15:20:41  asm2242
 * 		Got the derivative in Sum to work properly. Need to touch up the toString and comment it all.
 * 		
 * 		Revision 1.1  2010/04/02 16:35:05  asm2242
 * 		Added sum class, all it can do is evaluate though;
 * 		
 */

import java.util.ArrayList;


public class Sum extends Function{
	private double ConstantSum = 0.0;
	private ArrayList<Function> Abridged_Sum_Array = new ArrayList<Function>();
	
	/**
	 * 
	 * Constructor method that takes in a variable number of arguments and combines 
	 * constants into an ArrayList full of functions called Abridged_Sum_Array
	 * it will also only add 0 if it the rest of Abridged_Sum_Array is empty
	 * 
	 * @param terms
	 * takes a variable number of arguments that must be functions
	 */
	public Sum(Function...terms ){
		for(int i = 0; i<terms.length;i++){
			if(terms[i].isConstant()){
				ConstantSum += terms[i].evaluate(0.0);
				
			}
			else{
				Abridged_Sum_Array.add(terms[i]);
			}
		}
		if(ConstantSum == 0.0 && Abridged_Sum_Array.isEmpty()){
			Abridged_Sum_Array.add(new Constant(ConstantSum));
		}
		else if(ConstantSum != 0.0){
			Abridged_Sum_Array.add(new Constant(ConstantSum));
		}
	}
	
	/**
	 * This method is private because it only constructs a Sum that has already been
	 * constructed which is only used in the integral method  
	 * this constructor is a shortcut
	 * 
	 * @param list
	 * Creates a Sum given an ArrayList
	 */
	private Sum(ArrayList<Function> list){
		Abridged_Sum_Array = list;
	}

	/**
	 * 
	 * This method calculates the derivative of the terms given to the sum function 
	 * by using recursion to make a Sum of Sums
	 * 
	 * @return
	 * Returns a Function that is the derivative of a given equation
	 */
	public Function derivative() {
		Function partSum = new Sum();
		if(Abridged_Sum_Array.size()==1){
			return Abridged_Sum_Array.get(0).derivative();
			
		}
		else if(Abridged_Sum_Array.size()==2){
			return new Sum(Abridged_Sum_Array.get(0).derivative(),Abridged_Sum_Array.get(1).derivative());
			
		}
		else{
			partSum = new Sum(Abridged_Sum_Array.get(0).derivative(),Abridged_Sum_Array.get(1).derivative());
		}
		
		for(int i = 2;i<Abridged_Sum_Array.size();i++){
			partSum = new Sum(partSum,Abridged_Sum_Array.get(i).derivative());
		}
		return partSum;
	}

	/**
	 * 
	 * This method evaluates all the terms at a given value and returns a double
	 * 
	 * @param eval
	 * A double is input to evaluate all variables in the sum
	 * @return
	 * Returns a double that represents the sum of all numbers when the variables
	 *  are evaluated at a given number
	 */
	public double evaluate(double eval) {
		double final_evaluation = 0.0;
		for(int i = 0;i<Abridged_Sum_Array.size();i++){
			final_evaluation += Abridged_Sum_Array.get(i).evaluate(eval); 
		}
		return final_evaluation;
		
	}

	/**
	 * Determines whether or not the Function is a sum of constants
	 * If it is it returns true
	 * else returns false
	 * @return
	 * Returns a boolean: true if it is a sum of constants or false if there are variables
	 */
	public boolean isConstant() {
		boolean isconstant = true;
		for(int i =0;i<Abridged_Sum_Array.size();i++){
			if(Abridged_Sum_Array.get(i).isConstant()== false){
				isconstant = false;
				break;
			}
		}

		return isconstant;
	}
	/** 
	 * Creates a readable String that represents the Sum equation
	 * 
	 * @return
	 * Returns a String that readable shows the function
	 */
	public String toString(){
		String Output = Abridged_Sum_Array.get(0).toString();
		for(int i = 1;i<Abridged_Sum_Array.size();i++){
			Output+= " + "+ Abridged_Sum_Array.get(i).toString();
		}
		
		return "("+Output+")";
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
		Function A_Sum = new Sum(Abridged_Sum_Array);
		return integral(A_Sum,a,b,N);
	}

}
