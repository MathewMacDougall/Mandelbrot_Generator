package gui;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import tabs.FormulaTab;
import tabs.LocationsTab;
import util.Calculations;
import util.Coloring;
import util.Data;

public class MandelbrotPanel extends JPanel {
	
	private BorderLayout currentLayout;

	private MouseAdapter mouseAdapter;	
	
	private static int lastEscapeValue;
	private static int lastMaxIterations;
	private static boolean updateColors;
	private boolean isDragging;
	private BufferedImage image;
	
	public MandelbrotPanel() {
	
		currentLayout = new BorderLayout();
		updateColors = false;	
		
		mouseAdapter = new MouseAdapter() {
			private int mousePressX, mousePressY;
			private int mouseDragX, mouseDragY;
			private int mouseDragWidth, mouseDragHeight;
			private int sideLength;
			private int sideL, sideR, sideT, sideB;
			
			
			public void mousePressed(MouseEvent e) {
				isDragging = true;
				mousePressX = e.getX();
				mousePressY = e.getY();
				GUI_Frame.setGlassPaneVisibility(true);
			}
			
			public void mouseDragged(MouseEvent e) {
				isDragging = true;
				
				mouseDragX = e.getX();
				mouseDragY = e.getY();
				mouseDragWidth = Math.abs(mouseDragX - mousePressX);
				mouseDragHeight = Math.abs(mouseDragY - mousePressY);
				sideLength = getLargest(mouseDragWidth, mouseDragHeight);
				
				MyGlassPane.update(mousePressX, mousePressY, mouseDragX, mouseDragY, sideLength);
				repaint();
				
			}
			
			public void mouseReleased(MouseEvent e) {
				GUI_Frame.setGlassPaneVisibility(false);
				updateColors = false;
				isDragging = false;
				
				Calculations.updateSelection(MyGlassPane.getLeftmostX(), MyGlassPane.getTopmostY(), MyGlassPane.getRightmostX(), MyGlassPane.getBottommostY());
				MyGlassPane.clear();
				LocationsTab.resetChooser();
				repaint();
			}
			
		};
		
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.setBackground(Color.RED);
		
		setupPanel();
		Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), 2);//FormulaTab.getExponent());
	}
	
	
	
    protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		if(inputsAreChanged(ControlPanel.getMaxIterations(), ControlPanel.getEscapeValue())) {
			MandelbrotPanel.setUpdateColors(false);
		}
		
		drawMandelbrot(GUI_Frame.getMandelbrotPanelWidth(), GUI_Frame.getMandelbrotPanelHeight(), ControlPanel.getMaxIterations(), ControlPanel.getEscapeValue(), FormulaTab.getExponent(), g);
		ControlPanel.setZoomField(Calculations.getDomain());
    }
	
    
    private boolean inputsAreChanged(int maxIterations, int escape) {
		if(maxIterations != MandelbrotPanel.getLastMaxIterations() || escape != MandelbrotPanel.getLastEscapeValue())
			return true;
		else
			return false;
	}
    
	
    public static void saveImage(int width, int height, int iterations, String name) {
    	BufferedImage sImg = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
    	Calculations.calculateMandelbrot(width, height, iterations, ControlPanel.getEscapeValue(), FormulaTab.getExponent(), sImg.getGraphics());
    	Coloring.updateColors(width, height, iterations, sImg.getGraphics());
    	try {
			ImageIO.write(sImg, "PNG", new File(name + ".png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    
    
	private void drawMandelbrot(int width, int height, int iterations, int escape, int exponent, Graphics g) {
		if(isDragging)
			g.drawImage(image, 0, 0, null);
		else {
			image = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
			Graphics imageGraphics = image.getGraphics();
		
			if(updateColors) {
				Coloring.updateColors(width, height, iterations, imageGraphics);
			}
			else {//recalculate and re-color mandelbrot
				Calculations.calculateMandelbrot(width, height, iterations, escape, exponent, imageGraphics);
				Coloring.updateColors(width, height, iterations, imageGraphics);
				lastEscapeValue = escape;
				lastMaxIterations = iterations;
			}
		
			g.drawImage(image,  0,  0,  null);
		}
	}
	
	private int getSmallest(int num1, int num2) {
		int smallest;
		if(num1 <= num2)
			smallest = num1;
		else
			smallest = num2;
		
		return smallest;
	}
	
	private int getLargest(int num1, int num2) {
		int largest;
		if(num1 >= num2)
			largest = num1;
		else
			largest = num2;
		
		return largest;
	}
	
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		
	}


	public static int getLastEscapeValue() {
		return lastEscapeValue;
	}
	
	public static int getLastMaxIterations() {
		return lastMaxIterations;
	}
	
	
	public static boolean getUpdateColors() {
		return updateColors;
	}

	public static void setUpdateColors(boolean updateColors) {
		MandelbrotPanel.updateColors = updateColors;
	}
	

	
}
