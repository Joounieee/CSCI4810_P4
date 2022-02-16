import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Scanner;

public class GraphicsWindow {
	
	static int[] xCoord;
	static int[] yCoord;
	static int[] zCoord;
	static int[] xCoordSC;	// array of clipped lines (x coordinates)
	static int[] yCoordSC;	// array of clipped lines (y coordinates)
	static int[] zCoordSC;
	static int numLines;
	static double viewD = 2.5;	// in cm
	static double screenS = 50; // in cm
	static BresenhamAlg lineDrawer = new BresenhamAlg();
	static MatrixFunctions3D matF_3D = new MatrixFunctions3D();
	static PerspectiveProjection displayer = new PerspectiveProjection();
	static CohenSutherlandLineClipping lineClipper = new CohenSutherlandLineClipping();
	
	public static void main(String[] args) {
		Frame window = new Frame("3D Graphics Program");
		window.setBackground(Color.PINK);
		
		// Clear screen
		Button clearPixB = new Button("Clear Pixels");
		clearPixB.setBounds(740, 40, 200, 30);
		clearPixB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < 500; i++) {
					for (int j = 0; j < 500; j++) {
						MyCanvas.Image.setRGB(i, j, 0x000000);
					} // for
				} // for
			}
		});
		window.add(clearPixB);
		
		// Display
		Button displayPixB = new Button("Display Pixels");
		displayPixB.setBounds(740, 80, 200, 30);
		// displayPixB button action handler
		displayPixB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {
				System.out.println(Arrays.toString(xCoord));
				System.out.println(Arrays.toString(yCoord));
				System.out.println(Arrays.toString(zCoord));
				for (int i = 0; i < numLines; i = i + 2) {
					double[] tempEEE = {(double) xCoord[i], (double) yCoord[i], (double) zCoord[i],
									 (double) xCoord[i + 1], (double) yCoord[i + 1], (double) zCoord[i + 1]};
					int[] temp2 = displayer.pp(screenS, viewD, tempEEE[0], tempEEE[1], tempEEE[2]);
					int[] temp3 = displayer.pp(screenS, viewD, tempEEE[3], tempEEE[4], tempEEE[5]);
					double[] temp4 = {(double)temp2[0], (double)temp2[1], (double)temp3[0], (double)temp3[1]};
					int[] temp5 = lineClipper.lineClip(temp4);
					lineDrawer.brz(temp5[0], temp5[1], temp5[2], temp5[3]);
					System.out.println("Line: " + ((i / 2) + 1));
					System.out.println(Arrays.toString(tempEEE));
					System.out.println(Arrays.toString(temp2));
					System.out.println(Arrays.toString(temp3));
					System.out.println(Arrays.toString(temp4));
					System.out.println(Arrays.toString(temp5));
				} // for
			}
		});
		window.add(displayPixB);
		
		// Output to file
		Button outputLinesB = new Button("Output Lines");
		TextField outputNameTF = new TextField("File name here (make sure to add .txt)");
		outputLinesB.setBounds(740, 120, 200, 30);
		outputNameTF.setBounds(980, 120, 220, 30);
		outputLinesB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				try {
					String newFileName = outputNameTF.getText();
					// File myFile = new File(newFileName);
					FileOutputStream outputStream = new FileOutputStream(newFileName);
					String numLinesAsString = Integer.toString((numLines / 2));
					String coordsAsString = " ";
					
					for (int i = 0; i < xCoord.length; i++) {
						coordsAsString = coordsAsString + " " + "\n"
										 + Integer.toString(xCoord[i]) + " " 
										 + Integer.toString(yCoord[i]) + " " 
										 + Integer.toString(zCoord[i]) + " \n";
					} // for
					
					String fileString = numLinesAsString + coordsAsString;
					byte[] fileStringToBytes = fileString.getBytes();
					outputStream.write(fileStringToBytes);
					
					outputStream.close();
				} catch (IOException ioe) {
					ioe.printStackTrace();
				} // try
			}
		});
		window.add(outputLinesB);
		window.add(outputNameTF);
		
		// Translate
		Button translateB = new Button("Translate 3D");
		TextField translateTx = new TextField("x");
		TextField translateTy = new TextField("y");
		TextField translateTz = new TextField("z");
		translateB.setBounds(740, 160, 200, 30);
		translateTx.setBounds(980, 160, 30, 30);
		translateTy.setBounds(1020, 160, 30, 30);
		translateTz.setBounds(1060, 160, 30, 30);
		translateB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], zCoord[i], 
									xCoord[i + 1], yCoord[i + 1], zCoord[i + 1]};
					int[] temp2 = matF_3D.translate3D(temp, 
														Integer.parseInt(translateTx.getText()), 
														Integer.parseInt(translateTy.getText()),
														Integer.parseInt(translateTz.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					zCoord[i] = temp2[2];
					xCoord[i + 1] = temp2[3];
					yCoord[i + 1] = temp2[4];
					zCoord[i + 1] = temp2[5];
					System.out.println("Line: " + ((i / 2) + 1));
					System.out.println(Arrays.toString(temp2));
				} // for
			}
		});
		window.add(translateB);
		window.add(translateTx);
		window.add(translateTy);
		window.add(translateTz);
		
		// Basic Scale
		Button basicScB = new Button("Basic Scale 3D");
		TextField basicScSx = new TextField("x");
		TextField basicScSy = new TextField("y");
		TextField basicScSz = new TextField("z");
		basicScB.setBounds(740, 200, 200, 30);
		basicScSx.setBounds(980, 200, 30, 30);
		basicScSy.setBounds(1020, 200, 30, 30);
		basicScSz.setBounds(1060, 200, 30, 30);
		basicScB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], zCoord[i], 
									xCoord[i + 1], yCoord[i + 1], zCoord[i + 1]};
					int[] temp2 = matF_3D.basicSc3D(temp, 
													Integer.parseInt(basicScSx.getText()), 
													Integer.parseInt(basicScSy.getText()),
													Integer.parseInt(basicScSz.getText()));
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					zCoord[i] = temp2[2];
					xCoord[i + 1] = temp2[3];
					yCoord[i + 1] = temp2[4];
					zCoord[i + 1] = temp2[5];
					System.out.println("Line: " + ((i / 2) + 1));
					System.out.println(Arrays.toString(temp2));
				} // for
				System.out.println(Arrays.toString(xCoord));
				System.out.println(Arrays.toString(yCoord));
				System.out.println(Arrays.toString(zCoord));
			}
		});
		window.add(basicScB);
		window.add(basicScSx);
		window.add(basicScSy);
		window.add(basicScSz);
		
		// Basic Rotate
		Button basicRotateB = new Button("Basic Rotate 3D");
		TextField basicRotateAngle = new TextField("deg");
		TextField basicRotateAxis = new TextField("axis");
		basicRotateB.setBounds(740, 240, 200, 30);
		basicRotateAngle.setBounds(980, 240, 30, 30);
		basicRotateAxis.setBounds(1020, 240, 30, 30);
		basicRotateB.addActionListener(new ActionListener() {  
			public void actionPerformed(ActionEvent e) {  
				for (int i = 0; i < numLines; i = i + 2) {
					int[] temp = {xCoord[i], yCoord[i], zCoord[i], 
									xCoord[i + 1], yCoord[i + 1], zCoord[i + 1]};
					int[] temp2 = matF_3D.basicRot3D(temp, 
													 Double.parseDouble(basicRotateAngle.getText()),
													 basicRotateAxis.getText());
					xCoord[i] = temp2[0];
					yCoord[i] = temp2[1];
					zCoord[i] = temp2[2];
					xCoord[i + 1] = temp2[3];
					yCoord[i + 1] = temp2[4];
					zCoord[i + 1] = temp2[5];
				} // for
			}
		});
		window.add(basicRotateB);
		window.add(basicRotateAngle);
		window.add(basicRotateAxis);
		
		// Input file
		Button readFileB = new Button("Read File");
		TextField inputFileName = new TextField("File name of datalines here (case sensitive, including file extension)");
		readFileB.setBounds(740, 440, 100, 30);
		inputFileName.setBounds(740, 400, 360, 30);
		
		// readFileB button action handler
		readFileB.addActionListener(new ActionListener() {  
		    public void actionPerformed(ActionEvent e) {  
		    	String fileName = inputFileName.getText();
				try {
					File datalines = new File(fileName);
					Scanner scanner = new Scanner(datalines);
					numLines = 2 * (scanner.nextInt());
					xCoord = new int[numLines];
					yCoord = new int[numLines];
					zCoord = new int[numLines];
					xCoordSC = new int[numLines];
					yCoordSC = new int[numLines];
					zCoordSC = new int[numLines];
					
					for (int i = 0; i < numLines; i++) {
						xCoord[i] = scanner.nextInt();
						yCoord[i] = scanner.nextInt();
						zCoord[i] = scanner.nextInt();
					} // for
					
					scanner.close();
				} catch (FileNotFoundException fnfe) {
					fnfe.printStackTrace();
				} // try
				System.out.println(Arrays.toString(xCoord));
				System.out.println(Arrays.toString(yCoord));
				System.out.println(Arrays.toString(zCoord));
	        }  
	    });
		window.add(readFileB);
		window.add(inputFileName);
		
		// Handling closing window
		window.addWindowListener(new WindowAdapter() {
	        public void windowClosing(WindowEvent we) {
	            window.dispose();
	            System.exit(0);
	         }
	     });
		
		Canvas pic = new MyCanvas();
		window.add("Center", pic);
		
		window.setSize(1280, 500);
		window.setVisible(true);
		
		// Repaint picture
		pic.repaint();
		
	} // main method

} // GraphicsWindow class
