package util;

import gui.GUI_Frame;
import gui.MandelbrotPanel;
import gui.OutputPanel;

import java.awt.Color;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

import tabs.CustomColorTab;
import tabs.FormulaTab;
import tabs.LocationsTab;
import tabs.PresetColorTab;

public class Data {

	//ArrayLists for the undo/redo functions
	private static ArrayList<Double> X1 = new ArrayList<Double>();
	private static ArrayList<Double> X2 = new ArrayList<Double>();
	private static ArrayList<Double> Y1 = new ArrayList<Double>();
	private static ArrayList<Double> Y2 = new ArrayList<Double>();
	private static ArrayList<Integer> Exp = new ArrayList<Integer>();
	private static int currentActionIndex = -1;
	private static int maxActionIndex = currentActionIndex;
	
	//ArrayLists for colors
	private static ArrayList<String> savedColorNames = new ArrayList<String>();
	private static ArrayList<Double> savedRedStart = new ArrayList<Double>();
	private static ArrayList<Double> savedGreenStart = new ArrayList<Double>();
	private static ArrayList<Double> savedBlueStart = new ArrayList<Double>();
	private static ArrayList<Double> savedRedShift = new ArrayList<Double>();
	private static ArrayList<Double> savedGreenShift = new ArrayList<Double>();
	private static ArrayList<Double> savedBlueShift = new ArrayList<Double>();
	
	
	
	//ArrayLists for saving and loading Locations
	private static ArrayList<String> savedLocationNames = new ArrayList<String>();
	private static ArrayList<Double> savedX1 = new ArrayList<Double>();
	private static ArrayList<Double> savedX2 = new ArrayList<Double>();
	private static ArrayList<Double> savedY1 = new ArrayList<Double>();
	private static ArrayList<Double> savedY2 = new ArrayList<Double>();
	private static ArrayList<Integer> savedExp = new ArrayList<Integer>();
	
	
	
	public static void loadColor(int index, String name) {
		Coloring.setrShift(savedRedStart.get(index));
		Coloring.setgShift(savedGreenStart.get(index));
		Coloring.setbShift(savedBlueStart.get(index));
		Coloring.setrCompression(savedRedShift.get(index));
		Coloring.setgCompression(savedGreenShift.get(index));
		Coloring.setbCompression(savedBlueShift.get(index));
		
		CustomColorTab.update(savedRedStart.get(index), savedRedShift.get(index), savedGreenStart.get(index), savedGreenShift.get(index), savedBlueStart.get(index), savedBlueShift.get(index));
		
		OutputPanel.updateLog("The color \"" + name + "\" has been loaded", new Color(0, 150, 0));
	}
	
	
	public static void deleteColor(int index) {
		String name = savedColorNames.get(index);
		
		savedColorNames.remove(index);
		savedRedStart.remove(index);
		savedGreenStart.remove(index);
		savedBlueStart.remove(index);
		savedRedShift.remove(index);
		savedGreenShift.remove(index);
		savedBlueShift.remove(index);
		
		try {
			saveNameArray("savedColorNames", savedColorNames, savedColorNames.size());
			saveColorData("savedColorData", savedRedStart, savedGreenStart, savedBlueStart, savedRedShift, savedGreenShift, savedBlueShift, savedColorNames.size());
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		
		OutputPanel.updateLog("The Color \"" + name + "\" has been deleted", Color.blue);
	}
	
	
	public static void saveColor(String name, double rStart, double gStart, double bStart, double rShift, double gShift, double bShift) {
		savedColorNames.add(name);
		savedRedStart.add(rStart);
		savedGreenStart.add(gStart);
		savedBlueStart.add(bStart);
		savedRedShift.add(rShift);
		savedGreenShift.add(gShift);
		savedBlueShift.add(bShift);
		
		try {
			saveNameArray("savedColorNames", savedColorNames, savedColorNames.size());
			saveColorData("savedColorData", savedRedStart, savedGreenStart, savedBlueStart, savedRedShift, savedGreenShift, savedBlueShift, savedColorNames.size());
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		
		OutputPanel.updateLog("This color has been saved as \"" + name + "\"", Color.blue);
	}
	
	
	public static void saveLocation(String name, Double mX1, Double mX2, Double mY1, Double mY2, int exponent) {
		 savedLocationNames.add(name);
		 savedX1.add(mX1);
		 savedX2.add(mX2);
		 savedY1.add(mY1);
		 savedY2.add(mY2);
		 savedExp.add(exponent);
		 
		 try {
			saveNameArray("savedLocationNames", savedLocationNames, savedLocationNames.size());
			saveLocationData("savedLocationData", savedX1, savedX2, savedY1, savedY2, savedExp, savedLocationNames.size());
		 } catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		 }
		 
		 OutputPanel.updateLog("This location has been saved as \"" + name + "\"", Color.blue);
	}
	
	
	
	public static void loadLoaction(int index, String name) {
		Calculations.setmX1(savedX1.get(index));
		Calculations.setmX2(savedX2.get(index));
		Calculations.setmY1(savedY1.get(index));
		Calculations.setmY2(savedY2.get(index));
		FormulaTab.setExponent(savedExp.get(index));
		
		OutputPanel.updateLog("The location \"" + name + "\" has been loaded", Color.blue);
	}
	
	
	
	public static void deleteLocation(int index) {
		String name = savedLocationNames.get(index);
		
		savedLocationNames.remove(index);
		savedX1.remove(index);
		savedX2.remove(index);
		savedY1.remove(index);
		savedY2.remove(index);
		savedExp.remove(index);
		
		try {
			saveNameArray("savedLocationNames", savedLocationNames, savedLocationNames.size());
			saveLocationData("savedLocationData", savedX1, savedX2, savedY1, savedY2, savedExp, savedLocationNames.size());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		OutputPanel.updateLog("The location \"" + name + "\" has been deleted", Color.blue);
	}
	
	
	
	public static void saveNameArray(String fileName, ArrayList<String> array, int total) throws IOException {
	    BufferedWriter writer = null;
	    try {

	        writer = new BufferedWriter(new FileWriter(fileName));
	        for ( int i = 0; i < total; i++)
	        {      
	          writer.write(array.get(i));
	          writer.newLine();
	      writer.flush();
	        }
	        
	    } catch(IOException ex) {
	        ex.printStackTrace();
	    } finally{
	        if(writer!=null){
	            writer.close();
	        }  
	    }
	}
	
	
	
	public static void saveLocationData(String fileName, ArrayList<Double> x1, ArrayList<Double> x2, ArrayList<Double> y1, ArrayList<Double> y2, ArrayList<Integer> e, int total) throws IOException {
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(fileName));
			for ( int i = 0; i < total; i++) {    
				writer.write(Double.toString(x1.get(i)) + "\t" + Double.toString(x2.get(i)) + "\t" + Double.toString(y1.get(i)) + "\t" + Double.toString(y2.get(i)) + "\t" + Integer.toString(e.get(i)));
				writer.newLine();
				writer.flush();
		    }
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally{
			if(writer!=null){
				writer.close();
			}  
		}
	}
	
	
	public static void saveColorData(String fileName, ArrayList<Double> rStart, ArrayList<Double> gStart, ArrayList<Double> bStart, ArrayList<Double> rShift, ArrayList<Double> gShift, ArrayList<Double> bShift, int total) throws IOException {
		BufferedWriter writer = null;
		try {

			writer = new BufferedWriter(new FileWriter(fileName));
			for ( int i = 0; i < total; i++) {    
				writer.write(Double.toString(rStart.get(i)) + "\t" + Double.toString(gStart.get(i)) + "\t" + Double.toString(bStart.get(i)) + "\t" + Double.toString(rShift.get(i)) + "\t" + Double.toString(gShift.get(i)) + "\t" + Double.toString(bShift.get(i)));
				writer.newLine();
				writer.flush();
		    }
		} catch(IOException ex) {
			ex.printStackTrace();
		} finally{
			if(writer!=null){
				writer.close();
			}  
		}
	}
	
	
	
	public static void reloadColorNames(String file) {
		
		int ctr = 0;
			
		try {
			Scanner s1 = new Scanner(new File(file));
			while (s1.hasNextLine()) {
				ctr += 1;
				s1.nextLine();
			}
				
			String[] names = new String[ctr];
				
			Scanner s2 = new Scanner(new File(file));
			for(int i = 0; i < ctr; i++) {
				names[i] = s2.next();
			}
				
			for(int i = 0; i < names.length; i++) {
				savedColorNames.add(names[i]);
				PresetColorTab.addToChooser(names[i]);
			}
		}
		catch (FileNotFoundException e) {	
		}	
	}
	
	
	public static void reloadColorData(String file) {	
		int ctr = 0;
			
		try {
			Scanner s1 = new Scanner(new File(file));
			while (s1.hasNextLine()) {
				ctr += 1;
				s1.nextLine();
			}
				
			Double[] rStart = new Double[ctr];
			Double[] gStart = new Double[ctr];
			Double[] bStart = new Double[ctr];
			Double[] rShift = new Double[ctr];
			Double[] gShift = new Double[ctr];
			Double[] bShift = new Double[ctr];
			
					
			Scanner s2 = new Scanner(new File(file));
			for(int i = 0; i < ctr; i++) {
				rStart[i] = Double.parseDouble(s2.next());
				gStart[i] = Double.parseDouble(s2.next());
				bStart[i] = Double.parseDouble(s2.next());
				rShift[i] = Double.parseDouble(s2.next());
				gShift[i] = Double.parseDouble(s2.next());
				bShift[i] = Double.parseDouble(s2.next());
			}
				
			for(int i = 0; i < rStart.length; i++) {
				savedRedStart.add(rStart[i]);
				savedGreenStart.add(gStart[i]);
				savedBlueStart.add(bStart[i]);
				savedRedShift.add(rShift[i]);
				savedGreenShift.add(gShift[i]);
				savedBlueShift.add(bShift[i]);
			}	
		}
		catch (FileNotFoundException e) {
		}	
	}
	
	
	
	
	
	 
	public static void reloadLocationNames(String file) {
			
		int ctr = 0;
			
		try {
			Scanner s1 = new Scanner(new File(file));
			while (s1.hasNextLine()) {
				ctr += 1;
				s1.nextLine();
			}
				
			String[] names = new String[ctr];
				
			Scanner s2 = new Scanner(new File(file));
			for(int i = 0; i < ctr; i++) {
				names[i] = s2.next();
			}
				
			for(int i = 0; i < names.length; i++) {
				savedLocationNames.add(names[i]);
				LocationsTab.addToChooser(names[i]);
			}
		}
		catch (FileNotFoundException e) {	
		}	
	}
	 
	 

	public static void reloadLocationData(String file) {	
		int ctr = 0;
			
		try {
			Scanner s1 = new Scanner(new File(file));
			while (s1.hasNextLine()) {
				ctr += 1;
				s1.nextLine();
			}
				
			Double[] x1 = new Double[ctr];
			Double[] x2 = new Double[ctr];
			Double[] y1 = new Double[ctr];
			Double[] y2 = new Double[ctr];
			Integer[] exp = new Integer[ctr];
					
			Scanner s2 = new Scanner(new File(file));
			for(int i = 0; i < ctr; i++) {
				x1[i] = Double.parseDouble(s2.next());
				x2[i] = Double.parseDouble(s2.next());
				y1[i] = Double.parseDouble(s2.next());
				y2[i] = Double.parseDouble(s2.next());
				exp[i] = Integer.parseInt(s2.next());
			}
				
			for(int i = 0; i < exp.length; i++) {
				savedX1.add(x1[i]);
				savedX2.add(x2[i]);
				savedY1.add(y1[i]);
				savedY2.add(y2[i]);
				savedExp.add(exp[i]);
			}	
		}
		catch (FileNotFoundException e) {
		}	
	}
	 
	
	public static void undoAction() {
		if(currentActionIndex > 0) {
			if(Exp.get(currentActionIndex) > 2 && Exp.get(currentActionIndex - 1) == 2) {
				currentActionIndex--;
				Calculations.setmX1(X1.get(currentActionIndex) + 0.75);//need this otherwise the mandelbrot shifts too far because of calculations and old position
				Calculations.setmX2(X2.get(currentActionIndex) + 0.75);
				Calculations.setmY1(Y1.get(currentActionIndex));
				Calculations.setmY2(Y2.get(currentActionIndex));
				FormulaTab.setExponent(Exp.get(currentActionIndex));
			}
			else {
				currentActionIndex--;
				Calculations.setmX1(X1.get(currentActionIndex));
				Calculations.setmX2(X2.get(currentActionIndex));
				Calculations.setmY1(Y1.get(currentActionIndex));
				Calculations.setmY2(Y2.get(currentActionIndex));
				FormulaTab.setExponent(Exp.get(currentActionIndex));
			}
			
			
			
			MandelbrotPanel.setUpdateColors(false);
			OutputPanel.updateLog("Undoing last action...", Color.blue);
			GUI_Frame.getMandelbrotPanel().repaint();
			
		}
		else
			OutputPanel.updateLog("Can't undo any further", Color.red);
	}
	
	public static void redoAction() {
		if(currentActionIndex < maxActionIndex) {
			if(Exp.get(currentActionIndex) == 2 && Exp.get(currentActionIndex + 1) > 2) {
				currentActionIndex++;
				Calculations.setmX1(X1.get(currentActionIndex));
				Calculations.setmX2(X2.get(currentActionIndex));
				Calculations.setmY1(Y1.get(currentActionIndex));
				Calculations.setmY2(Y2.get(currentActionIndex));
				FormulaTab.setExponent(Exp.get(currentActionIndex));
			}
			else {
				currentActionIndex++;
				Calculations.setmX1(X1.get(currentActionIndex));
				Calculations.setmX2(X2.get(currentActionIndex));
				Calculations.setmY1(Y1.get(currentActionIndex));
				Calculations.setmY2(Y2.get(currentActionIndex));
				FormulaTab.setExponent(Exp.get(currentActionIndex));
			}
			
			MandelbrotPanel.setUpdateColors(false);
			OutputPanel.updateLog("Redoing previous action...", Color.blue);
			GUI_Frame.getMandelbrotPanel().repaint();
		}
		else
			OutputPanel.updateLog("Can't redo any further", Color.red);
	}
	
	
	public static void addXY(double x1, double x2, double y1, double y2, int e) {
		if(currentActionIndex == X1.size() - 1) {
			currentActionIndex++;
			X1.add(x1);
			X2.add(x2);
			Y1.add(y1);
			Y2.add(y2);
			Exp.add(e);
			maxActionIndex = currentActionIndex;
		}
		else {
			currentActionIndex++;
			X1.set(currentActionIndex, x1);
			X2.set(currentActionIndex, x2);
			Y1.set(currentActionIndex, y1);
			Y2.set(currentActionIndex, y2);
			Exp.set(currentActionIndex, e);
			maxActionIndex = currentActionIndex;

		}
		
		
		
		System.out.println(currentActionIndex);
	}
	
	public static void clearActions() {
		X1.clear();
		X2.clear();
		Y1.clear();
		Y2.clear();
		Exp.clear();
		currentActionIndex = -1;
		maxActionIndex = currentActionIndex;
	}
	
	public static ArrayList<String> getSavedLocationNames() {
		return savedLocationNames;
	}
	
	public static ArrayList<String> getSavedColorNames() {
		return savedColorNames;
	}
	
	public static int getCurrentActionIndex() {
		return currentActionIndex;
	}
	 
	 
}
