package gui;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import tabs.CustomColorTab;
import tabs.FormulaTab;
import tabs.LocationsTab;
import tabs.MoveViewTab;
import tabs.PresetColorTab;
import util.Calculations;
import util.Coloring;
import util.Data;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import gui.TabbedPanel;

public class ControlPanel extends JPanel {

	private final SpringLayout currentLayout;
	
	private final JLabel maxIterationsLabel;
	private final JLabel escapeValueLabel;
	private final JLabel zoomLabel;
	private static JTextField zoomField;
	private static JTextField maxIterationsField;
	private static JTextField escapeValueField;
	private final JButton repaintButton;
	private final JButton undoButton;
	private final JButton redoButton;
	private final JButton zoomOutButton;
	private final JButton resetButton;
	
	private int presetColorTabIndex;
	private int customColorTabIndex;
	private int moveViewTabIndex;
	private int formulaTabIndex;
	private int locationsTabIndex;
	private int saveImageTabIndex;
	
	
	public ControlPanel() {
		
		currentLayout = new SpringLayout();
		
		presetColorTabIndex = 0;
		customColorTabIndex = 1;
		moveViewTabIndex = 2;
		formulaTabIndex = 3;
		locationsTabIndex = 4;
		saveImageTabIndex = 5;
		
		maxIterationsLabel = new JLabel("Max Iterations");
		escapeValueLabel = new JLabel("Escape Value");
		zoomLabel = new JLabel("Zoom Factor");
		zoomField = new JTextField();
		maxIterationsField = new JTextField();
		escapeValueField = new JTextField();
		repaintButton = new JButton("REPAINT");
		undoButton = new JButton("Undo");
		redoButton = new JButton("Redo");
		zoomOutButton = new JButton("Zoom Out");
		resetButton = new JButton("Reset");
		
		setBackground(new Color(176, 224, 230));
		
		setupPanel();
		setupLayout();
	}
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(maxIterationsLabel);
		this.add(zoomLabel);
		this.add(zoomField);
		this.add(maxIterationsField);
		this.add(escapeValueField);
		this.add(escapeValueLabel);
		this.add(repaintButton);
		this.add(undoButton);
		this.add(redoButton);
		this.add(resetButton);
		this.add(zoomOutButton);
		
		zoomField.setToolTipText("Displays how far zoomed in the image is");
		repaintButton.setToolTipText("Repaints the mandelbrot");
		undoButton.setToolTipText("Undoes your last movement/zooming action");
		redoButton.setToolTipText("Redoes your previous movement/zooming action");
		resetButton.setToolTipText("Resets the mandelbrot");
		zoomOutButton.setToolTipText("Zooms out by a fixed amount");
		
		maxIterationsField.setText(Integer.toString(Calculations.getDefaultMaxIterations()));
		escapeValueField.setText(Integer.toString(Calculations.getDefaultEscapeValue()));
		
		maxIterationsLabel.setForeground(new Color(0, 0, 128));
		escapeValueLabel.setForeground(new Color(0, 0, 128));
		zoomLabel.setForeground(new Color(0, 0, 128));
		
		maxIterationsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		zoomLabel.setFont(new Font("Tahoma", Font.BOLD, 17));
		escapeValueLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		zoomField.setEditable(false);
		zoomField.setEnabled(true);
		
		zoomOutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Calculations.zoomOut();
				OutputPanel.updateLog("Zooming Out...", Color.black);
				GUI_Frame.getMandelbrotPanel().repaint();
			}
		});
		
		
		undoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Data.undoAction();
			}
		});
		
		redoButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Data.redoAction();
			}
		});
		
		
		resetButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Calculations.resetSelection();
				maxIterationsField.setText(Integer.toString(Calculations.getDefaultMaxIterations()));
				escapeValueField.setText(Integer.toString(Calculations.getDefaultEscapeValue()));
				OutputPanel.updateLog("The Mandelbrot has been reset", Color.blue);
				LocationsTab.resetChooser();
				GUI_Frame.getMandelbrotPanel().repaint();
				
			}
		});
		
		
		
		/* Adds a listener to the repaint button
		 * The action performed depends on the tab selected when the button is pressed
		 * 
		 */
		repaintButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(inputIsValid()) {

					int selectedTab = TabbedPanel.getSelectedTab();
					
					if(selectedTab == presetColorTabIndex) {
						MandelbrotPanel.setUpdateColors(true);
					}
					else if(selectedTab == customColorTabIndex) {
						Coloring.update(CustomColorTab.getRedSpeed(), CustomColorTab.getGreenSpeed(), CustomColorTab.getBlueSpeed(), CustomColorTab.getRedStart(), CustomColorTab.getGreenStart(), CustomColorTab.getBlueStart());
						MandelbrotPanel.setUpdateColors(true);
						PresetColorTab.resetChooser();
					}
					else if(selectedTab == moveViewTabIndex) {
						Calculations.updateLocation(MoveViewTab.getXShift(), MoveViewTab.getYShift());
						MoveViewTab.resetSliders();
						LocationsTab.resetChooser();
						MandelbrotPanel.setUpdateColors(false);
					}
					else if(selectedTab == formulaTabIndex) {
						MandelbrotPanel.setUpdateColors(false);
						LocationsTab.resetChooser();
						Data.addXY(Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), FormulaTab.getExponent());
					}
					else if(selectedTab == locationsTabIndex) {
						MandelbrotPanel.setUpdateColors(false);
					}
					else if(selectedTab == saveImageTabIndex) {		
						MandelbrotPanel.setUpdateColors(false);
					}
					else {
						System.err.println("ERROR: Invalid Tab");
					}
					
					OutputPanel.updateLog("Repainting...", Color.blue);
					GUI_Frame.getMandelbrotPanel().repaint();
				}	
			}
		});
	}

	
	
	private boolean inputIsValid() {
		boolean valid = false;
		
		if(maxIterationsField.getText().isEmpty() || Integer.parseInt(maxIterationsField.getText()) <= 0)
			OutputPanel.updateLog("Please enter a positive integer for the maximum iterations", Color.red);
		else if(escapeValueField.getText().isEmpty() || Integer.parseInt(escapeValueField.getText()) <= 0)
			OutputPanel.updateLog("Please enter a positive integer for the escape value", Color.red);
		else
			valid = true;
		
		return valid;
	}
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.WEST, maxIterationsLabel, 5, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, maxIterationsLabel, -35, SpringLayout.NORTH, escapeValueLabel);
		currentLayout.putConstraint(SpringLayout.EAST, maxIterationsLabel, 0, SpringLayout.EAST, escapeValueLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, maxIterationsLabel, -10, SpringLayout.NORTH, escapeValueLabel);
		
		currentLayout.putConstraint(SpringLayout.EAST, escapeValueLabel, 125, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.WEST, escapeValueLabel, 5, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, escapeValueLabel, -40, SpringLayout.NORTH, repaintButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, escapeValueLabel, -15, SpringLayout.NORTH, undoButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, zoomLabel, 10, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, zoomLabel, 0, SpringLayout.WEST, maxIterationsLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, zoomLabel, -15, SpringLayout.NORTH, maxIterationsLabel);
		currentLayout.putConstraint(SpringLayout.EAST, zoomLabel, 0, SpringLayout.EAST, maxIterationsLabel);
		
		currentLayout.putConstraint(SpringLayout.WEST, zoomField, 0, SpringLayout.WEST, maxIterationsField);
		currentLayout.putConstraint(SpringLayout.EAST, zoomField, 0, SpringLayout.EAST, maxIterationsField);
		currentLayout.putConstraint(SpringLayout.NORTH, zoomField, 0, SpringLayout.NORTH, zoomLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, zoomField, 0, SpringLayout.SOUTH, zoomLabel);
		
		currentLayout.putConstraint(SpringLayout.WEST, maxIterationsField, 10, SpringLayout.EAST, maxIterationsLabel);
		currentLayout.putConstraint(SpringLayout.NORTH, maxIterationsField, 0, SpringLayout.NORTH, maxIterationsLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, maxIterationsField, 0, SpringLayout.SOUTH, maxIterationsLabel);
		currentLayout.putConstraint(SpringLayout.EAST, maxIterationsField, -5, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, escapeValueField, 0, SpringLayout.NORTH, escapeValueLabel);
		currentLayout.putConstraint(SpringLayout.WEST, escapeValueField, 0, SpringLayout.WEST, maxIterationsField);
		currentLayout.putConstraint(SpringLayout.SOUTH, escapeValueField, 0, SpringLayout.SOUTH, escapeValueLabel);
		currentLayout.putConstraint(SpringLayout.EAST, escapeValueField, 0, SpringLayout.EAST, maxIterationsField);
		
		currentLayout.putConstraint(SpringLayout.WEST, repaintButton, 5, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, repaintButton, -10, SpringLayout.NORTH, resetButton);
		currentLayout.putConstraint(SpringLayout.EAST, repaintButton, -20, SpringLayout.WEST, undoButton);
		
		currentLayout.putConstraint(SpringLayout.WEST, redoButton, -95, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.EAST, redoButton, -5, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, redoButton, -10, SpringLayout.NORTH, zoomOutButton);
		
		currentLayout.putConstraint(SpringLayout.WEST, undoButton, 0, SpringLayout.WEST, zoomOutButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, undoButton, -10, SpringLayout.NORTH, zoomOutButton);
		currentLayout.putConstraint(SpringLayout.EAST, undoButton, -10, SpringLayout.WEST, redoButton);
		
		currentLayout.putConstraint(SpringLayout.WEST, zoomOutButton, -200, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, zoomOutButton, -5, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, zoomOutButton, -5, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.WEST, resetButton, 0, SpringLayout.WEST, repaintButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, resetButton, -5, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, resetButton, 0, SpringLayout.EAST, repaintButton);
	}
	
	public static void setZoomField(double domain) {
		zoomField.setText(Integer.toString((int) (3.5 / domain)));
	}
	
	public static int getMaxIterations() {
		return Integer.parseInt(maxIterationsField.getText());
	}
	
	public static int getEscapeValue() {
		return Integer.parseInt(escapeValueField.getText());
	}
	
	
}
