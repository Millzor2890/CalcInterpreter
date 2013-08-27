package src;

/*
 * Plotter.java
 * Version:
 * $Id: Plotter.java,v 1.2 2010/04/17 00:51:40 asm2242 Exp $
 * 
 * Revision:
 * 	$Log: Plotter.java,v $
 * 	Revision 1.2  2010/04/17 00:51:40  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 
 */


/**
 * A generic plotting interface. All implementors have a notion
 * of "plot points" that determine the accuracy of the plot.
 * More specifically, there is a limited number of points available
 * for variations in the function's value. This means that the
 * range of the function must be determined so that it can be
 * scaled to fit.
 */
public interface Plotter {

    /**
     * Plot the provided function.
     * 
     * @param f the Function instance to be evaluated for the plot
     * @param xmin the starting value for x
     * @param xmax the ending value for x
     * @param xIncr the increment for the x values; a single
     *              increment corresponds to one plot point
     *              in the plot.
     */
    public abstract void plotStep( Function f,
            double xmin, double xmax, double xIncr );

}