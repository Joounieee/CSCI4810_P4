import java.awt.*;
import java.awt.image.*;

public class MyCanvas extends Canvas {
	
	public static BufferedImage Image
		= new BufferedImage(500, 500, BufferedImage.TYPE_INT_RGB);
	
	public void paint(Graphics g) {
		g.drawImage(Image, 0, 0, Color.white, null);
	} // paint
	
	public void update(Graphics g) {
		paint(g);
	} // update
	
} // MyCanvas
