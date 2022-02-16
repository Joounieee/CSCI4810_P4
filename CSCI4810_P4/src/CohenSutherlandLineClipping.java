
// Code borrowed from  lecho/CohenSutherlandComputator.java  and altered for this project
public class CohenSutherlandLineClipping {
	
	// region codes
	public static final byte INSIDE = 0;
	public static final byte LEFT = 1;
	public static final byte RIGHT = 2;
	public static final byte BOTTOM = 4;
	public static final byte TOP = 8;
	
	// viewport coordinates
	final int x_max = 499;
	final int y_max = 499;
	final int x_min = 0;
	final int y_min = 0;
	
	// computing region code for a point
	public byte computeCode(double x, double y) {
		byte code = INSIDE;
		
		if (x < x_min) {
			code |= LEFT;
		} else if (x > x_max) {
			code |= RIGHT;
		} // if
		
		if (y < y_min) {
			code |= TOP;
		} else if (y > y_max) {
			code |= BOTTOM;
		} // if
		
		return code;
	} // computeCode method
	
	public int[] lineClip(double[] points) {
		double x1 = points[0];
		double y1 = points[1];
		double x2 = points[2];
		double y2 = points[3];
		int[] clippedPointsToInt = new int[4];
		
		byte code1 = computeCode(x1, y1);
		byte code2 = computeCode(x2, y2);
		System.out.println(code1);
		System.out.println(code2);
		boolean isClipped = false;
		
		while (true) {
			if ((code1 | code2) == INSIDE) {
				isClipped = true;
				System.out.println(code1 | code2);
				System.out.println("cringe if ");
				break;
			} else if ((code1 & code2) != INSIDE) {
				
					x1 = 0;
					y1 = 0;
					
				
					x2 = 0;
					y2 = 0;
					
				System.out.println(code1 & code2);
				System.out.println("cringe elif ");
				break;
			} else {
				final double clipX;
				final double clipY;
				final byte outCode = (code1 != INSIDE) ? code1 : code2;
				System.out.println("outCode: " + outCode);
				if ((outCode & TOP) != INSIDE) {
					// Point is above the clip rectangle
					clipX = x1 + (x2 - x1) * (y_min - y1) / (y2 - y1);
					clipY = y_min;
				} else if ((outCode & BOTTOM) != INSIDE) {
					// Point is below the clip rectangle.
					clipX = x1 + (x2 - x1) * (y_max - y1) / (y2 - y1);
					clipY = y_max;
				} else if ((outCode & RIGHT) != INSIDE) {
					// Point is to the right of clip rectangle.
					clipY = y1 + (y2 - y1) * (x_max - x1) / (x2 - x1);
					clipX = x_max;
				} else if ((outCode & LEFT) != INSIDE) {
					// Point is to the left of clip rectangle.
					clipY = y1 + (y2 - y1) * (x_min - x1) / (x2 - x1);
					clipX = x_min;
				} else {
					clipX = Double.NaN;
					clipY = Double.NaN;
				} // if

				if (outCode == code1) {
					x1 = clipX;
					y1 = clipY;
					code1 = computeCode(x1, y1);
				} else {
					x2 = clipX;
					y2 = clipY;
					code2 = computeCode(x2, y2);
				} // if
			} // if
		} // while
		
		clippedPointsToInt[0] = (int)x1;
		clippedPointsToInt[1] = (int)y1;
		clippedPointsToInt[2] = (int)x2;
		clippedPointsToInt[3] = (int)y2;
		
		return clippedPointsToInt;
	} // lineClip method
	
} // CohenSuthlandLineClipping class
