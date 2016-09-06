package util;

import gui.MandelbrotPanel;
import java.awt.Graphics;
import java.util.ArrayList;
import tabs.FormulaTab;

public class Calculations {
	
	private static double mX1 = -2.5;
	private static double mX2 = 1.0;
	private static double mY1 = -1.75;
	private static double mY2 = 1.75;
	private static double mDomain = mX2 - mX1;
	private static double mRange = mDomain;
	private static double incrementX, incrementY;
	private static boolean shiftedRight = false;
	private static int exponent;
	
	private static ComplexNumber c = new ComplexNumber();
	private static ComplexNumber z = new ComplexNumber();
	
	private static int defaultMaxIterations = 1000;
	private static int defaultEscapeValue = 2;
	
	private static ArrayList<Integer> iterationData = new ArrayList();

	
	public static void calculateMandelbrot(int width, int height, int maxIterations, int escapeValue, int exp, Graphics g) {
		mDomain = mX2 - mX1;
		mRange = mDomain;
		exponent = exp;
		incrementX = mDomain / width;
		incrementY = mRange / height;
		double x0;
		double x, y, xtemp;
		double y0 = mY1;
		int iteration;
		iterationData.clear();
		
//		if(exponent > 2 && shiftedRight == false) {//Shifts mandelbrot so is centered at powers higher than 2
//			mX1 += 0.75;
//			mX2 += 0.75;
//			shiftedRight = true;
//		}
//		else if(exponent <= 2 && shiftedRight == true) {//reverts the above
//			mX1 -= 0.75;
//			mX2 -= 0.75;
//			shiftedRight = false;
//		}
		
		
		for(int py = 0; py < height; py++) {
			x0 = mX1;
			for(int px = 0; px < width; px++) {
				iteration = 0;
				c.set(x0, y0);
				z.clear();
				
				x = 0;
				y = 0;
				
				if(exponent == 2) {
					while(x*x + y*y <= escapeValue * escapeValue && iteration < maxIterations) {//Do negative powers too?
						xtemp = x*x - y*y + x0;
						y = 2 * x * y +  y0;
						x = xtemp;
						iteration++;
					}
				}
				else {
					while(z.modulus() <= escapeValue && iteration < maxIterations) {//Do negative powers too?
						z = (z.exponent(z, exponent)).add(c);
						iteration++;
					}
				}
				
				iterationData.add(py * width + px, iteration);
				
				x0 += incrementX;
			}
			y0 += incrementY;
		}	
	}
	
	
	public static void updateLocation(double xShift, double yShift) {
		mX1 += xShift * mDomain ;
		mX2 += xShift * mDomain;
		mY1 -= yShift * mRange;
		mY2 -= yShift * mRange;
		
		Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), FormulaTab.getExponent());
	}
	
	public static void zoomOut() {
		mX1 -= mDomain / 2;
		mX2 += mDomain / 2;
		mY1 -= mRange / 2;
		mY2 += mRange / 2;
		
		Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), FormulaTab.getExponent());
	}
	
	public static void resetSelection() {
		MandelbrotPanel.setUpdateColors(false);
		
		mX1 = -2.5;
		mX2 = 1.0;
		mY1 = -1.75;
		mY2 = 1.75;
		
		shiftedRight = false;
		
		Data.clearActions();
		Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), FormulaTab.getExponent());
	}
	
	public static void updateSelection(int LX, int TY, int RX, int BY) {
		mX2 = mX1 + (incrementX * RX);
		mY2 = mY1 + (incrementY * BY);
		
		mX1 += incrementX * LX;
		mY1 += incrementY * TY;
		
		Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), FormulaTab.getExponent());
	}
	
	
	
	public static double getDomain() {
		return mDomain;
	}
	
	public static ArrayList<Integer> getIterationData() {
		return iterationData;
	}
	
	public static int getDefaultMaxIterations() {
		return defaultMaxIterations;
	}
	
	public static int getDefaultEscapeValue() {
		return defaultEscapeValue;
	}

	public static int getExponent() {
		return exponent;
	}


	public static double getmX1() {
		return mX1;
	}


	public static void setmX1(double mX1) {
		Calculations.mX1 = mX1;
	}


	public static double getmX2() {
		return mX2;
	}


	public static void setmX2(double mX2) {
		Calculations.mX2 = mX2;
	}


	public static double getmY1() {
		return mY1;
	}


	public static void setmY1(double mY1) {
		Calculations.mY1 = mY1;
	}


	public static double getmY2() {
		return mY2;
	}


	public static void setmY2(double mY2) {
		Calculations.mY2 = mY2;
	}

	
	

}
