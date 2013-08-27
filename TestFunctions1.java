package src;
/**
 * TestFunctionClass.java
 * 
 * Version:
 * 	$Id: TestFunctions1.java,v 1.7 2010/04/18 03:24:11 asm2242 Exp $
 * 
 * Revision
 * 	$Log: TestFunctions1.java,v $
 * 	Revision 1.7  2010/04/18 03:24:11  asm2242
 * 	Fixed minor spelling errors Ready for submission
 * 	
 * 	Revision 1.6  2010/04/17 00:51:41  asm2242
 * 	Fully commented and ready for submission.
 * 	
 * 	Revision 1.5  2010/04/16 23:31:44  asm2242
 * 	Added a Turtle implementation of the graph.  Still uncommented
 * 	
 * 	Revision 1.4  2010/04/16 20:21:51  asm2242
 * 	Added the CharacterPlotter which works like a dream.  Everything still needs comments and tidying up but is otherwise finished.
 * 	
 * 	Revision 1.3  2010/04/15 20:03:12  asm2242
 * 	Implemented integral method. Works for all classes.  New classes are still not commented.
 * 	
 * 	Revision 1.2  2010/04/14 05:29:20  asm2242
 * 	added Sum class
 * 	
 * 	Revision 1.1  2010/04/07 15:38:06  asm2242
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

public class TestFunctions1 {

	/**
	 * 
	 * It should be noted that all of the test functions can be tested using the TurtlePlotter
	 * I chose to comment them out because TurtlePlotter will graph them all in the same window
	 * at the same time if i did not
	 * 
	 * So if you would like to see the graphical representation of on of the test functions
	 * please uncomment it and comment the current active TurtlePlotter call.
	 * 
	 * 
	 * @param args(unused)
	 */
	public static void main(String[] args) {
		//Create plotter objects
		Plotter p = new CharacterPlotter(80);
		Plotter t = new TurtlePlotter();
		
				
		Function test1 = new Constant(5);
		
		System.out.println( "Function " + test1 );
		System.out.println( "Value at 0: " + test1.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test1.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test1.derivative() );
		double iResult = test1.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test1, 0, 10, .5);
		System.out.println( "===========================================" );
		p.plotStep( test1, 0, 10, .5 );
		System.out.println();
		
		
		Function test2 = Variable.X;
		
		System.out.println( "Function " + test2 );
		System.out.println( "Value at 0: " + test2.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test2.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test2.derivative() );
		iResult = test1.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test2, 0, 10, .5);
		System.out.println( "===========================================" );
		p.plotStep( test2, 0, 10, .5 );
		
		System.out.println();
		
		
		Function test3 = new Sum(new Constant(3),new Constant(-3) );
		System.out.println( "Function " + test3 );
		System.out.println( "Value at 0: " + test3.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test3.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test3.derivative() );
		iResult = test3.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test3, 0, 10, .5);
		System.out.println( "===========================================" );
		p.plotStep( test3, 0, 10, .5 );
		System.out.println();    
		
		
		Function test4 = new Sum(new Constant(9),new Constant(-3));
		System.out.println( "Function " + test4 );
		System.out.println( "Value at 0: " + test4.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test4.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test4.derivative() );
		iResult = test4.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test4, 0, 10, .5);
		System.out.println( "===========================================" );
		p.plotStep( test4, 0, 10, .5 );
		System.out.println();
		
		Function test5 = new Sum(new Sum(new Constant(2),Variable.X),Variable.X,new Sine(Variable.X));
		System.out.println( "Function " + test5 );
		System.out.println( "Value at 0: " + test5.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test5.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test5.derivative() );
		iResult = test5.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test5, 0, 10, .5);
		System.out.println( "===========================================" );
		p.plotStep( test5, 0, 10, .5 );
		System.out.println();
		
		
		Function test6 = new Sum(new Product(new Constant(2),new Cosine(Variable.X)),new Constant(5));
		System.out.println( "Function " + test6 );
		System.out.println( "Value at 0: " + test6.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test6.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test6.derivative() );
		iResult = test6.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test6, 0, 10, .01);
		System.out.println( "===========================================" );
		p.plotStep( test6, 0, 10, .5 );
		System.out.println();
		
		
		Function test7 = new Product(new Sine(new Constant(2)),new Cosine(new Sum(Variable.X,new Constant(5))));
		System.out.println( "Function " + test7 );
		System.out.println( "Value at 0: " + test7.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test7.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test7.derivative() );
		iResult = test7.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test7, 0, 10, .01);
		System.out.println( "===========================================" );
		p.plotStep( test7, 0, 10, .5 );
		System.out.println();
		
		Function test8 = new Sine(new Product(Variable.X,Variable.X));
		System.out.println( "Function " + test8 );
		System.out.println( "Value at 0: " + test8.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test8.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test8.derivative() );
		iResult = test8.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test8, 0, 10, .01);
		System.out.println( "===========================================" );
		p.plotStep( test8, 0, Math.PI, .05 );
		System.out.println();
		
		Function test9 = new Sum(new Product(Variable.X,Variable.X),new Cosine(new Product(new Constant(2),Variable.X)),new Constant(7));
		System.out.println( "Function " + test9 );
		System.out.println( "Value at 0: " + test9.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test9.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test9.derivative() );
		iResult = test9.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test9, -10, 10, .005);
		System.out.println( "===========================================" );
		p.plotStep( test9, -10, 10, 1 );
		System.out.println();
		
		Function test10 = new Product(Variable.X,Variable.X,Variable.X);
		System.out.println( "Function " + test10 );
		System.out.println( "Value at 0: " + test10.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test10.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test10.derivative() );
		iResult = test10.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		//t.plotStep(test10, -10, 10, .1);
		System.out.println( "===========================================" );
		p.plotStep( test10, -10, 10, 1);
		System.out.println();
		
		
		Function test11 = new Product(
		        new Constant( 0.1 ), Variable.X, new Sine( Variable.X ) );
		    System.out.println( "Function " + test11 );
		System.out.println( "Value at 0: " + test11.evaluate( 0.0 ) );
		System.out.println( "Value at 10: " + test11.evaluate( 10.0 ) );
		System.out.println( "Derivative: " + test11.derivative() );
		iResult = test11.integral( 0.0, 10.0, 1000000 );
		System.out.println( "Integral from 0 to 10: " + iResult );
		t.plotStep(test11, 0, 20*Math.PI, .1);
		System.out.println( "===========================================" );
		p.plotStep( test11, 0, 2*Math.PI, .24 );
    

	}

}
