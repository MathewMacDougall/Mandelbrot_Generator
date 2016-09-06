package tabs;

import gui.GUI_Frame;
import gui.MandelbrotPanel;
import gui.OutputPanel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import javax.swing.ButtonGroup;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import util.Coloring;
import util.Data;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class CustomColorTab extends JPanel {

	private SpringLayout currentLayout;
	
	private static JRadioButton redButton, greenButton, blueButton;
	private static JSlider start, shift;
	private JLabel startLabel, speedLabel, colorLabel;
	private JButton saveColorButton;
	private JTextField colorNameField;
	private JToggleButton liveColoringButton;
	private final ButtonGroup buttonGroup;
	private static int startMax, startMin, shiftMax, shiftMin;
	
	private static int redStart, redShift, greenStart, greenShift, blueStart, blueShift;
	
	public CustomColorTab() {
		currentLayout = new SpringLayout();
		buttonGroup = new ButtonGroup();
		
		redButton = new JRadioButton("Red");
		greenButton = new JRadioButton("Green");
		blueButton = new JRadioButton("Blue");
		saveColorButton = new JButton("Save this color as:");
		liveColoringButton = new JToggleButton("Update Colors Live");
		colorNameField = new JTextField();
		start = new JSlider();
		shift = new JSlider();
		startLabel = new JLabel("Drag to control the starting color");
		speedLabel = new JLabel("Drag to control how fast colors change");
		colorLabel = new JLabel("Select which pigment to edit");
		
		startMax = 100;
		startMin = 0;
		shiftMax = 3000;
		shiftMin = 500;
		
		redStart = 0;
		redShift = 0;
		greenStart = 0;
		greenShift = 1000;
		blueStart = 1000;
		blueShift = 1000;
	
		setBackground(new Color(175, 238, 238));
		
		setupPanel();
		setupLayout();
	}
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(redButton);
		this.add(greenButton);
		this.add(blueButton);
		this.add(start);
		this.add(shift);
		this.add(speedLabel);
		this.add(startLabel);
		this.add(colorLabel);
		this.add(saveColorButton);
		this.add(colorNameField);
		this.add(liveColoringButton);
		
		redButton.setToolTipText("Shows if the red pigment is being edited");
		greenButton.setToolTipText("Shows if the green pigment is being edited");
		blueButton.setToolTipText("Shows if the blue pigment is being edited");
		liveColoringButton.setToolTipText("Indicates whether or not the colors will be updates as sliders are dragged");
		start.setToolTipText("Adjusts the starting value of the currently selected pigment");
		shift.setToolTipText("Adjusts how fast the currently selected pigment oscillates");
		
		redButton.setSelected(true);
		start.setForeground(Color.red);
		shift.setForeground(Color.red);
		
		liveColoringButton.setSelected(true);
		
		buttonGroup.add(redButton);
		buttonGroup.add(greenButton);
		buttonGroup.add(blueButton);
		
		startLabel.setHorizontalAlignment(SwingConstants.CENTER);
		startLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		speedLabel.setHorizontalAlignment(SwingConstants.CENTER);
		speedLabel.setFont(new Font("Tahoma", Font.PLAIN, 17));
		
		colorLabel.setHorizontalAlignment(SwingConstants.CENTER);
		colorLabel.setFont(new Font("Tahoma", Font.BOLD, 18));
		
		start.setMaximum(startMax);
		start.setMinimum(startMin);
		start.setValue(0);
		start.setPaintTicks(true);
		start.setPaintLabels(true);
		start.setMajorTickSpacing(20);
		
		shift.setMaximum(shiftMax);
		shift.setMinimum(shiftMin);
		shift.setValue(0);
		shift.setPaintTicks(true);
		shift.setPaintLabels(true);
		shift.setMajorTickSpacing(500);
		
		
		saveColorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(colorNameField.getText().isEmpty())
					OutputPanel.updateLog("Please enter a name for this color", Color.red);
				else if(nameUsed(colorNameField.getText()))
					OutputPanel.updateLog("This name has already been used. Please choose another", Color.red);
				else {
					Data.saveColor(colorNameField.getText(), Coloring.getrShift(), Coloring.getgShift(), Coloring.getbShift(), Coloring.getrCompression(), Coloring.getgCompression(), Coloring.getbCompression());;
					PresetColorTab.getColorChooser().add(colorNameField.getText());
					PresetColorTab.getColorChooser().select(colorNameField.getText());
					colorNameField.setText("");
				}	
			}
		});
		
		start.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				PresetColorTab.resetChooser();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		
		start.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(redButton.isSelected()) {
					redStart = start.getValue();
				}
				else if(greenButton.isSelected()) {
					greenStart = start.getValue();
				}
				else {
					blueStart = start.getValue();
				}
				
				if(liveColoringButton.isSelected()) {
					Coloring.update(redShift, greenShift, blueShift, redStart, greenStart, blueStart);
					//PresetColorTab.resetChooser();
					MandelbrotPanel.setUpdateColors(true);
					GUI_Frame.getMandelbrotPanel().repaint();
				}
			}
		});
		
		shift.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseDragged(MouseEvent e) {
				PresetColorTab.resetChooser();
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		shift.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if(redButton.isSelected()) {
					redShift = shift.getValue();
					
				}
				else if(greenButton.isSelected()) {
					greenShift = shift.getValue();
				}
				else {
					blueShift = shift.getValue();
				}
				
				if(liveColoringButton.isSelected()) {
					Coloring.update(redShift, greenShift, blueShift, redStart, greenStart, blueStart);
					MandelbrotPanel.setUpdateColors(true);
					GUI_Frame.getMandelbrotPanel().repaint();
				}
					
			}
		});
		
		
		redButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setForeground(Color.red);
				shift.setForeground(Color.red);
				start.setValue(redStart);
				shift.setValue(redShift);
				System.out.println(redStart + " " + redShift + " " + greenStart + " " + greenShift + " " + blueStart + " " + blueShift);
			}
		});
		
		greenButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setForeground(new Color(10, 150, 10));
				shift.setForeground(new Color(10, 150, 10));
				start.setValue(greenStart);
				shift.setValue(greenShift);
				System.out.println(redStart + " " + redShift + " " + greenStart + " " + greenShift + " " + blueStart + " " + blueShift);
			}
		});
		
		blueButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				start.setForeground(Color.blue);
				shift.setForeground(Color.blue);
				start.setValue(blueStart);
				shift.setValue(blueShift);
				
			}
		});
		
		
	}
	
	
	public static void trigger() {
		for(ActionListener a: redButton.getActionListeners()) {
		    a.actionPerformed(new ActionEvent(redButton, ActionEvent.ACTION_PERFORMED, null));
		}
	}
	
	
	public static boolean nameUsed(String s) {
		int i = 0;
		
		while(i < Data.getSavedColorNames().size()) {
			if(s.equals(Data.getSavedColorNames().get(i)))
				return true;
			i++;
		}
		
		return false;
	}
	
	
	
	public static void update(double rStart, double rShift, double gStart, double gShift, double bStart, double bShift) {
		redStart = (int) rStart;
		redShift = (int) (rShift * 1000.0);
		greenStart = (int) gStart;
		greenShift = (int) (gShift * 1000.0);
		blueStart = (int) bStart;
		blueShift = (int) (bShift * 1000.0);
		
		if(redButton.isSelected()) {
			start.setValue(redStart);
			shift.setValue(redShift);
		}
		else if(greenButton.isSelected()) {
			start.setValue(greenStart);
			shift.setValue(greenShift);
		}
		else {
			start.setValue(blueStart);
			shift.setValue(blueShift);
		}
	}
	
	
	
	
	public static int getRedStart() {
		return redStart;
	}

	public static void setRedStart(int redStart) {
		CustomColorTab.redStart = redStart;
	}

	public static int getRedSpeed() {
		return redShift;
	}

	public static void setRedSpeed(int redSpeed) {
		CustomColorTab.redShift = redSpeed;
	}

	public static int getGreenStart() {
		return greenStart;
	}

	public static void setGreenStart(int greenStart) {
		CustomColorTab.greenStart = greenStart;
	}

	public static int getGreenSpeed() {
		return greenShift;
	}

	public static void setGreenSpeed(int greenSpeed) {
		CustomColorTab.greenShift = greenSpeed;
	}

	public static int getBlueStart() {
		return blueStart;
	}

	public static void setBlueStart(int blueStart) {
		CustomColorTab.blueStart = blueStart;
	}

	public static int getBlueSpeed() {
		return blueShift;
	}

	public static void setBlueSpeed(int blueSpeed) {
		CustomColorTab.blueShift = blueSpeed;
	}
	
	public static int getStartMax() {
		return startMax;
	}
	
	public static int getStartMin() {
		return startMin;
	}
	
	public static int getShiftMax() {
		return shiftMax;
	}
	
	public static int getShiftMin() {
		return shiftMin;
	}
	
	
	

	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, redButton, 10, SpringLayout.SOUTH, colorLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, redButton, 50, SpringLayout.SOUTH, colorLabel);
		currentLayout.putConstraint(SpringLayout.WEST, redButton, 20, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, redButton, 80, SpringLayout.WEST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, greenButton, 0, SpringLayout.NORTH, redButton);
		currentLayout.putConstraint(SpringLayout.WEST, greenButton, 35, SpringLayout.EAST, redButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, greenButton, 0, SpringLayout.SOUTH, redButton);
		currentLayout.putConstraint(SpringLayout.EAST, greenButton, -35, SpringLayout.WEST, blueButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, blueButton, 0, SpringLayout.NORTH, redButton);
		currentLayout.putConstraint(SpringLayout.WEST, blueButton, -80, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, blueButton, 0, SpringLayout.SOUTH, redButton);
		currentLayout.putConstraint(SpringLayout.EAST, blueButton, -20, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, start, 5, SpringLayout.SOUTH, startLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, start, 65, SpringLayout.SOUTH, startLabel);
		currentLayout.putConstraint(SpringLayout.WEST, start, 5, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, start, -5, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, shift, 5, SpringLayout.SOUTH, speedLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, shift, 65, SpringLayout.SOUTH, speedLabel);
		currentLayout.putConstraint(SpringLayout.WEST, shift, 0, SpringLayout.WEST, start);
		currentLayout.putConstraint(SpringLayout.EAST, shift, 0, SpringLayout.EAST, start);
		
		currentLayout.putConstraint(SpringLayout.WEST, startLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, startLabel, 0, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, startLabel, 15, SpringLayout.SOUTH, liveColoringButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, startLabel, 45, SpringLayout.SOUTH, liveColoringButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, speedLabel, 15, SpringLayout.SOUTH, start);
		currentLayout.putConstraint(SpringLayout.SOUTH, speedLabel, 45, SpringLayout.SOUTH, start);
		currentLayout.putConstraint(SpringLayout.WEST, speedLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, speedLabel, 0, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, colorLabel, 10, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, colorLabel, 40, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, colorLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, colorLabel, 0, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.WEST, saveColorButton, 80, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, saveColorButton, -80, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, saveColorButton, 30, SpringLayout.SOUTH, shift);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveColorButton, 60, SpringLayout.SOUTH, shift);
		
		currentLayout.putConstraint(SpringLayout.NORTH, colorNameField, 10, SpringLayout.SOUTH, saveColorButton);
		currentLayout.putConstraint(SpringLayout.WEST, colorNameField, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, colorNameField, 40, SpringLayout.SOUTH, saveColorButton);
		currentLayout.putConstraint(SpringLayout.EAST, colorNameField, -10, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, liveColoringButton, 15, SpringLayout.SOUTH, redButton);
		currentLayout.putConstraint(SpringLayout.WEST, liveColoringButton, 50, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, liveColoringButton, 45, SpringLayout.SOUTH, redButton);
		currentLayout.putConstraint(SpringLayout.EAST, liveColoringButton, -50, SpringLayout.EAST, this);
	}
}
