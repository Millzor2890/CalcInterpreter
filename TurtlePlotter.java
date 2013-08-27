package src;

/**
 * TurtlePlotter.java
 * 
 * This class graphs things in using Turtle.
 * 
 * Version:
 * 	$Id: TurtlePlotter.java,v 1.5 2010/04/18 03:24:11 asm2242 Exp $
 * 
 * Revision:
 * 	$Log: TurtlePlotter.java,v $
 * 	Revision 1.5  2010/04/18 03:24:11  asm2242
 * 	Fixed minor spelling errors Ready for submission
 * 	
 * 	Revision 1.4  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 */

import java.util.ArrayList;


public class TurtlePlotter implements Plotter {
	/**
	 * This method creates a turtle window and draws a function
	 */

	public void plotStep(Function f, double xmin, double xmax, double xIncr) {
		double Curr_angle = 0.0;
		ArrayList<Double> Xvals = new ArrayList<Double>();
		double Ymin = xmin;
		double Ymax = f.evaluate(xmin);
		//finds y min and max
		for(double i = xmin;i<xmax;i+=xIncr){
			Xvals.add(i);
			if(f.evaluate(i)<Ymin){
				Ymin =f.evaluate(i);
			}
			if(f.evaluate(i)>Ymax){
				Ymax = f.evaluate(i);
			}
		}
		//Creates turtle object and display window
		Turtle Leo = new Turtle(xmin,f.evaluate(xmin),Curr_angle);
		Leo.setWorldCoordinates(xmin, Ymin, xmax, Ymax);
		//This loop draws line segments that make up the graph
		double Turtlecurrent_x = xmin;
		double Turtlecurrent_y = f.evaluate(xmin);
		double Turtletarget_x;
		double Turtletarget_y;
		for(int i =1;i<Xvals.size();i++){
			
			Turtletarget_x = Xvals.get(i);
			Turtletarget_y = f.evaluate(Xvals.get(i));
			if (Turtletarget_y<Turtlecurrent_y){
				double turnval = Math.toDegrees(Math.atan((Turtletarget_y-Turtlecurrent_y)/(Turtletarget_x-Turtlecurrent_x)));
				if(turnval !=Curr_angle){
					Leo.turnRight(Curr_angle - turnval);
					Curr_angle = turnval;
				}
				Leo.goForward(Math.sqrt(((Turtletarget_y-Turtlecurrent_y)*(Turtletarget_y-Turtlecurrent_y))+((Turtletarget_x-Turtlecurrent_x)*(Turtletarget_x-Turtlecurrent_x))));
			}
			else if(Turtletarget_y>=Turtlecurrent_y){
				double turnval = Math.toDegrees(Math.atan((Turtletarget_y-Turtlecurrent_y)/(Turtletarget_x-Turtlecurrent_x)));
				if(turnval!=Curr_angle){
					Leo.turnLeft(turnval-Curr_angle);
					Curr_angle = turnval;
				}
				Leo.goForward(Math.sqrt((Turtletarget_y-Turtlecurrent_y)*(Turtletarget_y-Turtlecurrent_y)+(Turtletarget_x-Turtlecurrent_x)*(Turtletarget_x-Turtlecurrent_x)));
			}
			Turtlecurrent_x = Turtletarget_x;
			Turtlecurrent_y = Turtletarget_y;
		}	
	}
}
