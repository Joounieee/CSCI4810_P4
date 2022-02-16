public class BresenhamAlg {
	
	public int checkSign (int x) {
		return (x > 0) ? 1 : (x < 0) ? -1 : 0;
	} // checkSign method
	
	public void brz(int x0, int y0, int x1, int y1) {
		int x, y, dx, dy, incx, incy, pdx, pdy, es, el, err;
		 
        dx = x1 - x0;
        dy = y1 - y0;
 
        incx = checkSign(dx);
        incy = checkSign(dy);
 
        if (dx < 0) {
        	dx = -dx;
        } // if
        
        if (dy < 0) { 
        	dy = -dy;
        } // if
 
        if (dx > dy) {
        	pdx = incx;     
        	pdy = 0;
        	es = dy;        
        	el = dx;
        } else {
        	pdx = 0;
        	pdy = incy;
        	es = dx;
        	el = dy;
        } // if
 
        x = x0;
        y = y0;
        err = el/2;
        MyCanvas.Image.setRGB((int) x, (int)(Math.round(y)), 0xFFC0CB);
        
        for (int t = 0; t < el; t++) {
        	err -= es;
            if (err < 0) {
            	err += el;
            	x += incx;
            	y += incy;
            } else {
            	x += pdx;
            	y += pdy;
            } // if
 
            MyCanvas.Image.setRGB((int) x, (int)(Math.round(y)), 0xFFC0CB);
        } // for
        
	} // brz method
	
} // BresenhamAlg class
