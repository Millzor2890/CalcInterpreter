package src;
/**
 * CharacterPlotter.java
 * 
 * Version:
 * 	$Id: CharacterPlotter.java,v 1.2 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision:
 * 	$Log: CharacterPlotter.java,v $
 * 	Revision 1.2  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 */



import java.util.ArrayList;

public class CharacterPlotter implements Plotter {

	public final int fWidth;
	/**
	 * Sets the with of the characters to 80
	 * so that the max number of characters being output can be at max 80.
	 * @param fRange
	 * range of the function 
	 */
	public CharacterPlotter(int fRange){
		fWidth = fRange;
		
	}
	/**
	 * 
	 * Using a given function (f) and an x range(determined by xmin and xmax) this method
	 * will evaluate all x incremented by (xIncr) to create a textual representation of a 
	 * function
	 * 
	 * @param f
	 * Given function
	 * 
	 * @param xmin
	 * minimum x value
	 * 
	 * @param xmax
	 * maximum X value
	 * 
	 * @param xIncr
	 * distance between two evaluated x values
	 */
	public void plotStep(Function f, double xmin, double xmax, double xIncr) {
		ArrayList<Double> yVal = new ArrayList<Double>();
		double Ypoint;
		double Ymax = f.evaluate(xmin);
		double Ymin = f.evaluate(xmin);
		//generate y min and max and add Y values to an array
		for(double i =xmin;i<=xmax;i+=xIncr){
			Ypoint = f.evaluate(i);
			if(Ypoint< Ymin){
				Ymin = Ypoint;
			}
			else if(Ypoint>Ymax){
				Ymax = Ypoint;
			}
			yVal.add(Ypoint);	
		}
		//step determines the numerical value of a space
		double step = (Ymax-Ymin)/((double)(fWidth-1));
		//generates output  based on steps
		for(int i =0;i<yVal.size();i++){
			double spaceval = yVal.get(i) - Ymin;
			double stepinto = 0.0;
			while(stepinto<spaceval){
				System.out.print(" ");
				stepinto +=step; 
			}
			System.out.println("*");
			
		}
	}

}
