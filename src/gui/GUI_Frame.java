package gui;

//Import Java packages
import javax.swing.JFrame;
import javax.swing.SpringLayout;

import util.Coloring;

import java.awt.Color;
import java.awt.Container;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GUI_Frame extends JFrame{
	
	private final JFrame frame;
	
	private static int frameWidth;
	private static int frameHeight;
	private static String frameTitle;
	
	private static MyGlassPane glassPane;
	
	private final SpringLayout springLayout;
	private final Container contentPane;
	private static MandelbrotPanel mandelbrotPanel;
	private final ControlPanel controlPanel;
	private final TabbedPanel tabbedPanel;
	private final OutputPanel outputPanel;
	
	private final int tabbedPanelWidth;
	private final int outputPanelHeight;
	private final int controlPanelHeight;
	private final int controlPanelWidth;
	private static int mandelbrotPanelWidth;
	private static int mandelbrotPanelHeight;
	
	private int changeHeight;
	private int changeWidth;
	private int change;
	
	private static Rectangle initialBounds;
	
	//The constructor initializes all the variables
	public GUI_Frame() {
		frame = new JFrame();
		
		initialBounds = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
		
		frameTitle = "Mandelbrot";
		tabbedPanelWidth = 350;
		controlPanelWidth = tabbedPanelWidth;
		outputPanelHeight = 150;
		controlPanelHeight = 200;
		
		frameHeight = initialBounds.height - 0;
		mandelbrotPanelHeight = frameHeight - outputPanelHeight - 29;
		mandelbrotPanelWidth = mandelbrotPanelHeight;
		frameWidth = mandelbrotPanelWidth + tabbedPanelWidth + 6;
		
		glassPane = new MyGlassPane();
		springLayout = new SpringLayout();
		contentPane = frame.getContentPane();
		outputPanel = new OutputPanel();
		controlPanel = new ControlPanel();
		mandelbrotPanel = new MandelbrotPanel();
		tabbedPanel = new TabbedPanel();
		
		System.out.println(frameHeight + ", " + frameWidth);
		
		setupFrame();
		setupLayout();
		
		OutputPanel.updateLog("Mandelbrot Loaded Successfully", Color.black);
		Coloring.loadPreset1();
	}

	//frame method adds all the components to the frame and configures them (make new method for config?)
	private void setupFrame() {
		contentPane.setLayout(springLayout);
		
		contentPane.add(mandelbrotPanel);
		contentPane.add(controlPanel);
		contentPane.add(tabbedPanel);
		contentPane.add(outputPanel);
	
		contentPane.setVisible(true);
		mandelbrotPanel.setVisible(true);
		controlPanel.setVisible(true);
		tabbedPanel.setVisible(true);
		outputPanel.setVisible(true);
		
		frame.setGlassPane(glassPane);
		glassPane.setOpaque(false);
		glassPane.setVisible(false);
		
		frame.setTitle(frameTitle);
		frame.setMaximizedBounds(initialBounds);
		
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.requestFocus();
	}
	
	
	private void setupLayout() {
		springLayout.putConstraint(SpringLayout.NORTH, outputPanel, -outputPanelHeight, SpringLayout.SOUTH, contentPane);
		springLayout.putConstraint(SpringLayout.WEST, outputPanel, 0, SpringLayout.WEST, contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, outputPanel, 0, SpringLayout.SOUTH, contentPane);
		springLayout.putConstraint(SpringLayout.EAST, outputPanel, 0, SpringLayout.EAST, contentPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, controlPanel, -controlPanelHeight, SpringLayout.SOUTH, controlPanel);
		springLayout.putConstraint(SpringLayout.SOUTH, controlPanel, 0, SpringLayout.NORTH, outputPanel);
		springLayout.putConstraint(SpringLayout.WEST, controlPanel, -controlPanelWidth, SpringLayout.EAST, contentPane);
		springLayout.putConstraint(SpringLayout.EAST, controlPanel, 0, SpringLayout.EAST, contentPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, tabbedPanel, 0, SpringLayout.NORTH, contentPane);
		springLayout.putConstraint(SpringLayout.WEST, tabbedPanel, -tabbedPanelWidth, SpringLayout.EAST, contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, tabbedPanel, 0, SpringLayout.NORTH, controlPanel);
		springLayout.putConstraint(SpringLayout.EAST, tabbedPanel, 0, SpringLayout.EAST, contentPane);
		
		springLayout.putConstraint(SpringLayout.NORTH, mandelbrotPanel, 0, SpringLayout.NORTH, contentPane);
		springLayout.putConstraint(SpringLayout.WEST, mandelbrotPanel, 0, SpringLayout.WEST, contentPane);
		springLayout.putConstraint(SpringLayout.SOUTH, mandelbrotPanel, 0, SpringLayout.NORTH, outputPanel);
		springLayout.putConstraint(SpringLayout.EAST, mandelbrotPanel, 0, SpringLayout.WEST, tabbedPanel);
	}
	
	
	public void start() {
		frame.setSize(frameWidth, frameHeight);
		
		frame.addComponentListener(new ComponentListener() {
			@Override
			public void componentHidden(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentMoved(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void componentResized(ComponentEvent e) {
				changeHeight = frame.getHeight() - frameHeight;
				changeWidth = frame.getWidth() - frameWidth;
				change = getSmallest(changeHeight, changeWidth);
				frameHeight += change;
				frameWidth += change;
				frame.setSize(frameWidth, frameHeight);
				mandelbrotPanelHeight += change;
				mandelbrotPanelWidth += change;
				frame.setLocationRelativeTo(null);				
			}

			@Override
			public void componentShown(ComponentEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		
	}
	
	
	public static void setGlassPaneVisibility(boolean visible) {
		glassPane.setVisible(visible);
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
	
	public static MandelbrotPanel getMandelbrotPanel() {
		return mandelbrotPanel;
	}

	public static int getMandelbrotPanelWidth() {
		return mandelbrotPanelWidth;
	}

	public static void setMandelbrotPanelWidth(int mandelbrotPanelWidth) {
		GUI_Frame.mandelbrotPanelWidth = mandelbrotPanelWidth;
	}

	public static int getMandelbrotPanelHeight() {
		return mandelbrotPanelHeight;
	}

	public static void setMandelbrotPanelHeight(int mandelbrotPanelHeight) {
		GUI_Frame.mandelbrotPanelHeight = mandelbrotPanelHeight;
	}
	
}
