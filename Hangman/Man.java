import java.util.*;
import objectdraw.*;

public class Man {
	protected boolean DEBUG = false;

	public int partsShowing=0;

	// arrays as many are used for thickness
	private ArrayList<FramedOval> head;
	private ArrayList<Line> arm1, arm2, body, leg1, leg2;

	/* All of the boundary arrays are public static, they can be changed outside the class */

	// global arrays
	public static double[] BOUNDS = {0,0,0.5,1}; // x,y,w,h (all %)
	public static double[] PADDING = {0.1,0.5,0.1,0}; // top, right, bottom, left
	public static int thickness=4;

	// head
	public static double[] HEAD_BOUNDS = {0.3,0,0.4,0.3}; // x,y,w,h (all % of overall)
	protected double HEAD_HEIGHT, HEAD_WIDTH, HEAD_X, HEAD_Y;

	// body
	public static double[] BODY_BOUNDS = {0.5,0.3,0,0.4}; // x,y,w,h (all % of overall)
	protected double BODY_HEIGHT, BODY_WIDTH, BODY_X, BODY_Y;

	// arms
	public static double[] ARM_BOUNDS = {0.3,0.3,0.4,0.3}; // x,y,w,h (all % of overall)
	protected double ARM_HEIGHT, ARM_WIDTH, ARM_X, ARM_Y;

	// dont skip leg day
	public static double[] LEG_BOUNDS = {0.2,0.7,0.6,0.3}; // x,y,w,h (all % of overall)
	protected double LEG_HEIGHT, LEG_WIDTH, LEG_X, LEG_Y;

	protected double OVERALL_WIDTH, OVERALL_HEIGHT, OVERALL_X, OVERALL_Y, PADDED_WIDTH, PADDED_HEIGHT, PADDED_X, PADDED_Y;

	/* TODO: add image feature */
	
	/** Constructor: Man(DrawingCanvas)
	 * Creates a man on the provided canvas using default BOUNDS and PADDING
	 * @param DrawingCanvas canvas to draw man on
	*/
	public Man(DrawingCanvas canvas) {
		// set constants
		OVERALL_WIDTH = BOUNDS[2] * canvas.getWidth();
		OVERALL_HEIGHT = BOUNDS[3] * canvas.getHeight();
		OVERALL_X = BOUNDS[0] * canvas.getWidth();
		OVERALL_Y = BOUNDS[1] * canvas.getHeight();

		PADDED_WIDTH = OVERALL_WIDTH * (1 - PADDING[1] - PADDING[3]);
		PADDED_HEIGHT = OVERALL_HEIGHT * (1 - PADDING[0] - PADDING[2]);
		PADDED_X = OVERALL_X + OVERALL_WIDTH * PADDING[3];
		PADDED_Y = OVERALL_Y + OVERALL_HEIGHT * PADDING[0];

		if (DEBUG) {
			// show bounds
			new FramedRect(OVERALL_X, OVERALL_Y, OVERALL_WIDTH, OVERALL_HEIGHT, canvas);
			new FramedRect(PADDED_X, PADDED_Y, PADDED_WIDTH, PADDED_HEIGHT, canvas);
		}

		// draw head
		HEAD_WIDTH = PADDED_WIDTH*HEAD_BOUNDS[2];
		HEAD_HEIGHT = PADDED_HEIGHT*HEAD_BOUNDS[3];
		HEAD_X = PADDED_X+(PADDED_WIDTH*HEAD_BOUNDS[0]);
		HEAD_Y = PADDED_Y+(PADDED_HEIGHT*HEAD_BOUNDS[1]);
		
		if (DEBUG) {
			// show bounds
			new FramedRect(HEAD_X, HEAD_Y, HEAD_WIDTH, HEAD_HEIGHT, canvas);
		}

		// initialize head arr
		head = new ArrayList<FramedOval>();

		// draw head
		head.add(new FramedOval(HEAD_X, HEAD_Y, HEAD_WIDTH, HEAD_HEIGHT, canvas));
		// make thick
		for (int i=1; i<=(thickness/2); i+=2) {
			head.add(new FramedOval(HEAD_X-(i), HEAD_Y-(i), HEAD_WIDTH+(2*i), HEAD_HEIGHT+(2*i), canvas));
			head.add(new FramedOval(HEAD_X+(i), HEAD_Y+(i), HEAD_WIDTH-(2*i), HEAD_HEIGHT-(2*i), canvas));
		}

		// draw body
		BODY_WIDTH = 0; // kept for compatibility
		BODY_HEIGHT = PADDED_HEIGHT*BODY_BOUNDS[3];
		BODY_X = PADDED_X+(PADDED_WIDTH*BODY_BOUNDS[0]);
		BODY_Y = PADDED_Y+(PADDED_HEIGHT*BODY_BOUNDS[1]);
		
		if (DEBUG) {
			// show bounds
			new FramedRect(BODY_X, BODY_Y, BODY_WIDTH, BODY_HEIGHT, canvas);
		}

		// init arrs
		body = new ArrayList<Line>();

		body.add(new Line(BODY_X, BODY_Y, BODY_X, BODY_Y+BODY_HEIGHT, canvas));

		// thicc
		for (int i=1; i<=(thickness/2)*2; i+=2) {
			body.add(new Line(BODY_X+(i/2+1), BODY_Y, BODY_X+(i/2+1), BODY_Y+BODY_HEIGHT, canvas));
			body.add(new Line(BODY_X-(i/2+1), BODY_Y, BODY_X-(i/2+1), BODY_Y+BODY_HEIGHT, canvas));
		}

		// draw arms
		ARM_WIDTH = PADDED_WIDTH*ARM_BOUNDS[2];
		ARM_HEIGHT = PADDED_HEIGHT*ARM_BOUNDS[3];
		ARM_X = PADDED_X+(PADDED_WIDTH*ARM_BOUNDS[0]);
		ARM_Y = PADDED_Y+(PADDED_HEIGHT*ARM_BOUNDS[1]);
		
		if (DEBUG) {
			// show bounds
			new FramedRect(ARM_X, ARM_Y, ARM_WIDTH, ARM_HEIGHT, canvas);
		}

		// initialize arm arrs
		arm1 = new ArrayList<Line>();
		arm2 = new ArrayList<Line>();

		// draw arms
		arm1.add(new Line(ARM_X, ARM_Y+ARM_HEIGHT, ARM_X+(ARM_WIDTH/2), ARM_Y, canvas));
		arm2.add(new Line(ARM_X+(ARM_WIDTH/2), ARM_Y, ARM_X+ARM_WIDTH, ARM_Y+ARM_HEIGHT, canvas));
		// make thick
		for (int i=1; i<=(thickness/2)*2; i+=2) {
			arm1.add(new Line(ARM_X+(i/2+1), ARM_Y+ARM_HEIGHT+(i/2+1), ARM_X+(ARM_WIDTH/2)+(i/2+1), ARM_Y+(i/2+1), canvas));
			arm2.add(new Line(ARM_X+(ARM_WIDTH/2)-(i/2+1), ARM_Y+(i/2+1), ARM_X+ARM_WIDTH-(i/2+1), ARM_Y+ARM_HEIGHT+(i/2+1), canvas));
			arm1.add(new Line(ARM_X-(i/2+1), ARM_Y+ARM_HEIGHT-(i/2+1), ARM_X+(ARM_WIDTH/2)-(i/2+1), ARM_Y-(i/2+1), canvas));
			arm2.add(new Line(ARM_X+(ARM_WIDTH/2)+(i/2+1), ARM_Y-(i/2+1), ARM_X+ARM_WIDTH+(i/2+1), ARM_Y+ARM_HEIGHT-(i/2+1), canvas));
		}

		// draw legs
		LEG_WIDTH = PADDED_WIDTH*LEG_BOUNDS[2];
		LEG_HEIGHT = PADDED_HEIGHT*LEG_BOUNDS[3];
		LEG_X = PADDED_X+(PADDED_WIDTH*LEG_BOUNDS[0]);
		LEG_Y = PADDED_Y+(PADDED_HEIGHT*LEG_BOUNDS[1]);
		
		if (DEBUG) {
			// show bounds
			new FramedRect(LEG_X, LEG_Y, LEG_WIDTH, LEG_HEIGHT, canvas);
		}

		// initialize leg arrs
		leg1 = new ArrayList<Line>();
		leg2 = new ArrayList<Line>();

		// draw legs
		leg1.add(new Line(LEG_X, LEG_Y+LEG_HEIGHT, LEG_X+(LEG_WIDTH/2), LEG_Y, canvas));
		leg2.add(new Line(LEG_X+(LEG_WIDTH/2), LEG_Y, LEG_X+LEG_WIDTH, LEG_Y+LEG_HEIGHT, canvas));
		// make thick
		for (int i=1; i<=(thickness/2)*2; i+=2) {
			leg1.add(new Line(LEG_X+(i/2+1), LEG_Y+LEG_HEIGHT+(i/2+1), LEG_X+(LEG_WIDTH/2)+(i/2+1), LEG_Y+(i/2+1), canvas));
			leg2.add(new Line(LEG_X+(LEG_WIDTH/2)-(i/2+1), LEG_Y+(i/2+1), LEG_X+LEG_WIDTH-(i/2+1), LEG_Y+LEG_HEIGHT+(i/2+1), canvas));
			leg1.add(new Line(LEG_X-(i/2+1), LEG_Y+LEG_HEIGHT-(i/2+1), LEG_X+(LEG_WIDTH/2)-(i/2+1), LEG_Y-(i/2+1), canvas));
			leg2.add(new Line(LEG_X+(LEG_WIDTH/2)+(i/2+1), LEG_Y-(i/2+1), LEG_X+LEG_WIDTH+(i/2+1), LEG_Y+LEG_HEIGHT-(i/2+1), canvas));
		}
		hideAll();
	}

	public void hideAll() {
		for (FramedOval ins : head) {
			ins.hide();
		}
		for (Line arm : arm1) {
			arm.hide();
		}
		for (Line arm : arm2) {
			arm.hide();
		}
		for (Line bod : body) {
			bod.hide();
		}
		for (Line leg : leg1) {
			leg.hide();
		}
		for (Line leg : leg2) {
			leg.hide();
		}
	}

	public boolean showNextPart() {
		switch(partsShowing) {
			case 0:
				for (FramedOval ins : head) {
					ins.show();
				}
				break;
			case 1:
				for (Line bod : body) {
					bod.show();
				}
				break;
			case 2:
				for (Line arm : arm1) {
					arm.show();
				}
				break;
			case 3:
				for (Line arm : arm2) {
					arm.show();
				}
				break;
			case 4:
				for (Line leg : leg1) {
					leg.show();
				}
				break;
			case 5:
				for (Line leg : leg2) {
					leg.show();
				}
				return true;
		}
		partsShowing++;
		return false;
	}
}