package src;

/**
 * Product.java
 * 
 * Version:
 * 	$id$
 * 
 * Revision:
 * 	$Log: Product.java,v $
 * 	Revision 1.3  2010/04/17 00:51:41  asm2242
 * 	Fully commented and ready for submission.
 * 	
 */

import java.util.ArrayList;


public class Product extends Function {
	private double ConstantProduct = 1.0;
	private ArrayList<Function> Abridged_Product_Array = new ArrayList<Function>();
	private ArrayList<Function> ZeroException = new ArrayList<Function>();
	/**
	 * Constructor method that takes a variable number of arguments and combines constants
	 * into and ArrayList called Abridged_Product_Array which will only hold 1 if it is the
	 * only value.
	 * 
	 *  
	 * @param terms
	 * Functions that are input
	 */
	public Product(Function...terms){
		ZeroException.add(new Constant(0));
		for(int i = 0; i<terms.length;i++){
			if(terms[i].isConstant()){
				if(terms[i].evaluate(0.0)==0.0){
					Abridged_Product_Array = ZeroException;
					break;
				}
				else{
				ConstantProduct = ConstantProduct *terms[i].evaluate(0.0);
				}
			}
			else{
				Abridged_Product_Array.add(terms[i]);
			}
		}
		if(ConstantProduct == 1.0 && Abridged_Product_Array.isEmpty()){
			Abridged_Product_Array.add(new Constant(ConstantProduct));
		}
		else if(ConstantProduct != 1.0){
			Abridged_Product_Array.add(new Constant(ConstantProduct));
		}
	}
	/**
	 * This method is private because it only constructs a Product that has already been
	 * constructed which is only used in the integral method  
	 * this constructor is a shortcut
	 * 
	 * @param list
	 * Creates a Product given an ArrayList
	 */
	private Product(ArrayList<Function> list){
		Abridged_Product_Array = list;
	}

	/**
	 * This method calculates the derivative of a product by using recursion and 
	 * the product rule in calculus
	 */
	public Function derivative() {
		if(Abridged_Product_Array.size() == 1){
			return Abridged_Product_Array.get(0).derivative();
		}
		else if(Abridged_Product_Array.size()==2){
			return new Sum(new Product(Abridged_Product_Array.get(0).derivative(),Abridged_Product_Array.get(1)),
					new Product(Abridged_Product_Array.get(0),Abridged_Product_Array.get(1).derivative()));
		}
		else{
			Function The_rest = new Product(Abridged_Product_Array.get(1));
			for(int i = 2;i<Abridged_Product_Array.size();i++){
				The_rest = new Product(The_rest,new Product(Abridged_Product_Array.get(i)));
				
			}
			
			return new Sum(new Product(Abridged_Product_Array.get(0).derivative(),The_rest),
					new Product(Abridged_Product_Array.get(0),The_rest.derivative()));
		}
	}

	/**
	 * 
	 * This method evaluates all the terms at a given value and returns a double
	 * 
	 * @param eval
	 * A double is input to evaluate all variables in the product
	 * @return
	 * Returns a double that represents the product of all numbers when the variables
	 *  are evaluated at a given number
	 */
	
	public double evaluate(double eval) {
		double final_evaluation = 1.0;
		for(int i = 0;i<Abridged_Product_Array.size();i++){
			final_evaluation = final_evaluation * Abridged_Product_Array.get(i).evaluate(eval); 
		}
		return final_evaluation;
	}

	/**
	 * Determines whether or not the Function is a product of constants
	 * If it is it returns true
	 * else returns false
	 * @return
	 * Returns a boolean: true if it is a product of constants or false if there are variables
	 */
	public boolean isConstant() {
		boolean isconstant = true;
		for(int i =0;i<Abridged_Product_Array.size();i++){
			if(Abridged_Product_Array.get(i).isConstant()==false){
				isconstant = false;
				break;
			}
		}
		return isconstant;
	}
	/** 
	 * Creates a readable String that represents the Product equation
	 * 
	 * @return
	 * Returns a String that readable shows the function
	 */
	public String toString(){
		String Output = Abridged_Product_Array.get(0).toString();
		for(int i = 1;i<Abridged_Product_Array.size();i++){
			Output+= " * "+ Abridged_Product_Array.get(i).toString();
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
		Function A_Product = new Product(Abridged_Product_Array);
		return integral(A_Product,a,b,N);
	}

}
