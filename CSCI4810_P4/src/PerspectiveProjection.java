
public class PerspectiveProjection {
	
	public int[] pp(double halfScreen, double viewingDistance, double xE, double yE, double zE) {
		double s = halfScreen;
		double d = viewingDistance;
		int xS, yS;
		
		xS = (int)(Math.round(		((d / s) * (xE / zE) * 250) + 250		));
		yS = (int)(Math.round(		((d / s) * (yE / zE) * 250) + 250		));
		
		int[] arr = {xS, yS};
		return arr;
	} // pp method
	
}
