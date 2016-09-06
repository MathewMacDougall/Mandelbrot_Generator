package util;

import gui.OutputPanel;
import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import tabs.CustomColorTab;

public class Coloring {

	private static double amplitudeR = 255.0 / 2.0;
	private static double verticalShiftR = 255.0 / 2.0;
	private static double rCompression = 0.25;
	private static double rShift = 0.0;
	
	private static double amplitudeG = 255.0 / 2.0;
	private static double verticalShiftG = 255.0 / 2.0;
	private static double gCompression = 1.0;
	private static double gShift = 0.0;
	
	private static double amplitudeB = 255.0 / 2.0;
	private static double verticalShiftB = 255.0 / 2.0;
	private static double bCompression = 0.5;
	private static double bShift = 0.0;
	
	
	public static void generateRandomColor() {
		rCompression = randInt(CustomColorTab.getShiftMin(), CustomColorTab.getShiftMax()) / 1000.0;
		gCompression = randInt(CustomColorTab.getShiftMin(), CustomColorTab.getShiftMax()) / 1000.0;
		bCompression = randInt(CustomColorTab.getShiftMin(), CustomColorTab.getShiftMax()) / 1000.0;
		rShift = randInt(CustomColorTab.getStartMin(), CustomColorTab.getStartMax());
		gShift = randInt(CustomColorTab.getStartMin(), CustomColorTab.getStartMax());
		bShift = randInt(CustomColorTab.getStartMin(), CustomColorTab.getStartMax());
		
		CustomColorTab.update(rShift, rCompression, gShift, gCompression, bShift, bCompression);
		
		OutputPanel.updateLog("The Mandelbrot has been repainted with a random color", Color.blue);
	}
	
	
	public static void update(int rCompress, int gCompress, int bCompress, int rS, int gS, int bS) {
		rCompression = rCompress / 1000.0;
		gCompression = gCompress / 1000.0;
		bCompression = bCompress / 1000.0;
		rShift = rS;
		gShift = gS;
		bShift = bS;
	}
	
	public static void loadPreset1() {
		rCompression = 1.6;
		gCompression = 1.5;
		bCompression = 1.26;
		rShift = 100.0;
		gShift = 72.5;
		bShift = 4.89;
		
		CustomColorTab.update(rShift, rCompression, gShift, gCompression, bShift, bCompression);
		CustomColorTab.trigger();
		OutputPanel.updateLog("Color preset 1 has been loaded", new Color(0, 150, 0));
	}
	
	public static void loadPreset2() {
		rCompression = 1.5;
		gCompression = 1.5;
		bCompression = 1.5;
		rShift = 75.0;
		gShift = 75.0;
		bShift = 75.0;
		
		CustomColorTab.update(rShift, rCompression, gShift, gCompression, bShift, bCompression);
		
		OutputPanel.updateLog("Color preset 2 has been loaded", new Color(0, 150, 0));
	}
	
	public static void loadPreset3() {
		rCompression = 2.7;
		gCompression = 2.0;
		bCompression = 3.0;
		rShift = 0.0;
		gShift = 13.65;
		bShift = 100.0;
		
		CustomColorTab.update(rShift, rCompression, gShift, gCompression, bShift, bCompression);
		
		OutputPanel.updateLog("Color preset 3 has been loaded", new Color(0, 150, 0));
	}
	
	
	public static void updateColors(int width, int height, int maxIterations, Graphics g) {
		int py, px;
		int iteration;
		
		for(py = 0; py < height; py++) {
			for(px = 0; px < width; px++) {
				iteration = Calculations.getIterationData().get(py * width + px);
				
				if(iteration == maxIterations)
					g.setColor(Color.BLACK);
				else
					g.setColor(getColor(iteration));
				
				g.drawLine(px, py, px, py);
			}
		}
	}
	
	
	public static int randInt(int min, int max) {
		
	    Random rand = new Random();

	    int randomNum = rand.nextInt((max - min) + 1) + min;

	    return randomNum;
	}
	
	
	private static Color getColor(int iteration) {
		Color color = new Color(getR(iteration), getG(iteration), getB(iteration));
		return color;
	}
	
	
	private static int getR(int iteration) {
		int r = (int)(amplitudeR * Math.sin( (1 / rCompression) * Math.sqrt(iteration + rShift)) + verticalShiftR);
		return r;
	}
	
	private static int getG(int iteration) {
		int g = (int)(amplitudeG * Math.sin( (1 / gCompression) * Math.sqrt(iteration + gShift)) + verticalShiftG);
		return g;
	}
	
	private static int getB(int iteration) {
		int b = (int)(amplitudeB * Math.sin( (1 / bCompression) * Math.sqrt(iteration + bShift)) + verticalShiftB);
		return b;
	}


	public static double getrCompression() {
		return rCompression;
	}


	public static void setrCompression(double rCompression) {
		Coloring.rCompression = rCompression;
	}


	public static double getrShift() {
		return rShift;
	}


	public static void setrShift(double rShift) {
		Coloring.rShift = rShift;
	}


	public static double getgCompression() {
		return gCompression;
	}


	public static void setgCompression(double gCompression) {
		Coloring.gCompression = gCompression;
	}


	public static double getgShift() {
		return gShift;
	}


	public static void setgShift(double gShift) {
		Coloring.gShift = gShift;
	}


	public static double getbCompression() {
		return bCompression;
	}


	public static void setbCompression(double bCompression) {
		Coloring.bCompression = bCompression;
	}


	public static double getbShift() {
		return bShift;
	}


	public static void setbShift(double bShift) {
		Coloring.bShift = bShift;
	}
	
	
	
	
	
}
