
public class MatrixFunctions3D {
	
	public int[] translate3D(int[] arr, int tX, int tY, int tZ) {
		int x1 = arr[0] + tX;
		int y1 = arr[1] + tY;
		int z1 = arr[2] + tZ;
		
		int x2 = arr[3] + tX;
		int y2 = arr[4] + tY;
		int z2 = arr[5] + tZ;
		
		int[] arr2 = {x1, y1, z1, x2, y2, z2};

		return arr2;
	} // translate method
	
	public int[] basicSc3D(int[] arr, int sX, int sY, int sZ) {
		int x1 = arr[0] * sX;
		int y1 = arr[1] * sY;
		int z1 = arr[2] * sZ;
		
		int x2 = arr[3] * sX;
		int y2 = arr[4] * sY;
		int z2 = arr[5] * sZ;
		
		int[] arr2 = {x1, y1, z1, x2, y2, z2};
		return arr2;
	} // basicSc method
	
	public int[] basicRot3D(int[] arr, double angle, String axis) {
		double rad = Math.toRadians(angle);
		double x1, y1, z1, x2, y2, z2;
		
		if (axis.equals("x")) {
			x1 = (double)arr[0];
			y1 = (double)arr[1] * Math.cos(rad) - (double)arr[2] * Math.sin(rad);
			z1 = (double)arr[1] * Math.sin(rad) + (double)arr[2] * Math.cos(rad);
			
			x2 = (double)arr[3];
			y2 = (double)arr[4] * Math.cos(rad) - (double)arr[5] * Math.sin(rad);
			z2 = (double)arr[4] * Math.sin(rad) + (double)arr[5] * Math.cos(rad);
		} else if (axis.equals("y")) {
			x1 = (double)arr[0] * Math.cos(rad) + (double)arr[2] * Math.sin(rad);
			y1 = (double)arr[1];
			z1 = -(double)arr[0] * Math.sin(rad) + (double)arr[2] * Math.cos(rad);
			
			x2 = (double)arr[3] * Math.cos(rad) + (double)arr[5] * Math.sin(rad);
			y2 = (double)arr[4];
			z2 = -(double)arr[3] * Math.sin(rad) + (double)arr[5] * Math.cos(rad);
		} else if (axis.equals("z")) {
			x1 = (double)arr[0] * Math.cos(rad) - (double)arr[1] * Math.sin(rad);
			y1 = (double)arr[0] * Math.sin(rad) + (double)arr[1] * Math.cos(rad);
			z1 = (double)arr[2];
			
			x2 = (double)arr[3] * Math.cos(rad) - (double)arr[4] * Math.sin(rad);
			y2 = (double)arr[3] * Math.sin(rad) + (double)arr[4] * Math.cos(rad);
			z2 = (double)arr[5];
		} else {
			x1 = 0;
			y1 = 0;
			z1 = 0;
			x2 = 0;
			y2 = 0;
			z2 = 0;
		} // if
		
		int[] arr2 = {(int) Math.round(x1), (int) Math.round(y1), (int) Math.round(z1), 
						(int) Math.round(x2), (int) Math.round(y2), (int) Math.round(z2)};
		return arr2;
	} // basicRot method
	
}
