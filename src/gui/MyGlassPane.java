package gui;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class MyGlassPane extends JPanel{

	private static int tempmousePressX, tempmousePressY, tempmouseDragX, tempmouseDragY, templength;
	private static int tempCV, tempCH;
	
	private static int mousePressX, mousePressY, mouseDragX, mouseDragY, length;
	private static int CV, CH;
	
	public MyGlassPane() {
	}
	

	protected void paintComponent(Graphics g) {
        super.paintComponent(g);
		drawSelectionBox(g);
    }
	
	
	public static void clear() {
		mousePressX = 0;
		mousePressY = 0;
		CV = 0;
		CH = 0;
	}
	
	public static void update(int mPX, int mPY, int mDX, int mDY, int l) {
		
		tempmousePressX = mPX;
		tempmousePressY = mPY;
		tempmouseDragX = mDX;
		tempmouseDragY = mDY;
		templength = l;
	
		if(tempmouseDragX < tempmousePressX && tempmouseDragY < tempmousePressY) {
			tempCV = tempmousePressY - templength;
			tempCH = tempmousePressX - templength;
		}
		else if(tempmouseDragX < tempmousePressX && tempmouseDragY >= tempmousePressY) {
			tempCV = tempmousePressY + templength;
			tempCH = tempmousePressX - templength;
		}
		else if(tempmouseDragX >= tempmousePressX && tempmouseDragY < tempmousePressY) {
			tempCV = tempmousePressY - templength;
			tempCH = tempmousePressX + templength;
		}
		else {
			tempCV = tempmousePressY + templength;
			tempCH = tempmousePressX + templength;
		}
		
		if(getSmallest(tempmouseDragX, tempCH) > 0
			&& getLargest(tempmouseDragX, tempCH) < GUI_Frame.getMandelbrotPanelWidth()
			&& getSmallest(tempmouseDragY, tempCV) > 0
			&& getLargest(tempmouseDragY, tempCV) < GUI_Frame.getMandelbrotPanelHeight()) {
			
			mousePressX = tempmousePressX;
			mousePressY = tempmousePressY;
			mouseDragX = tempmouseDragX;
			mouseDragY = tempmouseDragY;
			length = templength;
			CV = tempCV;
			CH = tempCH;
		}
		
	}
	
	
	
	public static void drawSelectionBox(Graphics g) {
		g.setColor(Color.RED);

		g.drawLine(mousePressX, mousePressY, CH, mousePressY);
		g.drawLine(mousePressX, mousePressY, mousePressX, CV);
		g.drawLine(CH, mousePressY, CH, CV);
		g.drawLine(mousePressX, CV, CH, CV);
		
	}
	
	public static int getLeftmostX() {
		return getSmallest(mousePressX, CH);
	}
	
	public static int getRightmostX() {
		return getLargest(mousePressX, CH);
	}
	
	public static int getTopmostY() {
		return getSmallest(mousePressY, CV);
	}
	
	public static int getBottommostY() {
		return getLargest(mousePressY, CV);
	}

	
	
	private static int getSmallest(int num1, int num2) {
		int smallest;
		if(num1 <= num2)
			smallest = num1;
		else
			smallest = num2;
		
		return smallest;
	}
	
	private static int getLargest(int num1, int num2) {
		int largest;
		if(num1 >= num2)
			largest = num1;
		else
			largest = num2;
		
		return largest;
	}
	
}
