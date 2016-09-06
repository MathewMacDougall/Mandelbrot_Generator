package tabs;

import gui.MandelbrotPanel;
import gui.OutputPanel;

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.SpringLayout;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import util.Calculations;
import util.Data;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;

public class SaveImageTab extends JPanel {

	private final SpringLayout currentLayout;
	
	private final JLabel imageWidthLabel;
	private final JLabel imageHeightLabel;
	private final JLabel imageIterationsLabel;
	private final JLabel imageFilenameLabel;
	private final JTextField imageWidthField;
	private final JTextField imageHeightField;
	private final JTextField imageIterationsField;
	private final JTextField imageFilenameField;
	private final JToggleButton keepRatioToggleButton;
	private final JButton saveImageButton;
	
	private boolean ratioIsPressed;
	
	
	public SaveImageTab() {
		currentLayout = new SpringLayout();
		
		imageWidthLabel = new JLabel("Image Width (Pixels)");
		imageHeightLabel = new JLabel("Image Height (Pixels)");
		imageIterationsLabel = new JLabel("Image Iterations (Positive Integer)");
		imageFilenameLabel = new JLabel("Enter a name for your image");
		imageWidthField = new JTextField();
		imageHeightField = new JTextField();
		imageIterationsField = new JTextField();
		imageFilenameField = new JTextField();
		keepRatioToggleButton = new JToggleButton("Keep Image Ratio");
		saveImageButton = new JButton("Save Image");
		
		ratioIsPressed = true;
		
		setBackground(new Color(175, 238, 238));
		
		setupPanel();
		setupLayout();
	}
	
	
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(imageWidthLabel);
		this.add(imageHeightLabel);
		this.add(imageIterationsLabel);
		this.add(imageFilenameLabel);
		this.add(imageWidthField);
		this.add(imageHeightField);
		this.add(imageIterationsField);
		this.add(imageFilenameField);
		this.add(keepRatioToggleButton);
		this.add(saveImageButton);
		
		imageWidthLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		imageHeightLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		imageIterationsLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		saveImageButton.setFont(new Font("Tahoma", Font.BOLD, 22));
		imageFilenameLabel.setFont(new Font("Tahoma", Font.BOLD, 16));
		
		imageHeightField.setEditable(false);
		keepRatioToggleButton.setSelected(true);
		
		saveImageButton.setToolTipText("Saves the image with the current properties");
		keepRatioToggleButton.setToolTipText("Pressed: Forces Image Height to be the same as width");
		
		
		saveImageButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(inputIsValid()) {
					int width = Integer.parseInt(imageWidthField.getText());
					int height = Integer.parseInt(imageHeightField.getText());
					int iterations = Integer.parseInt(imageIterationsField.getText());
					String name = imageFilenameField.getText();
					MandelbrotPanel.saveImage(width, height, iterations, name);
					//also saves the image as a location
					Data.saveLocation(imageFilenameField.getText(), Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), Calculations.getExponent());
					LocationsTab.getChooser().add(imageFilenameField.getText());
					LocationsTab.getChooser().select(imageFilenameField.getText());
					
					clearFields();
				}
			}
		});
	
		
		
		imageWidthField.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void changedUpdate(DocumentEvent e) {
				
			}

			@Override
			public void insertUpdate(DocumentEvent e) {
				if(ratioIsPressed == true) {
					imageHeightField.setText(imageWidthField.getText());
				}
			}

			@Override
			public void removeUpdate(DocumentEvent e) {
				if(ratioIsPressed == true) {
					imageHeightField.setText(imageWidthField.getText());
				}
			}
		});
		
		
		keepRatioToggleButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(ratioIsPressed) {
					imageHeightField.setEditable(true);
					ratioIsPressed = false;
				}
				else {
					imageHeightField.setEditable(false);
					imageHeightField.setText( imageWidthField.getText() );
					ratioIsPressed = true;
				}
			}
		});
		
	}
	
	
	
	private void clearFields() {
		imageWidthField.setText("");
		imageHeightField.setText("");
		imageIterationsField.setText("");
		imageFilenameField.setText("");
	}
	
	
	
	private boolean inputIsValid() {
		boolean valid = false;
		
		if(imageWidthField.getText().isEmpty())
			OutputPanel.updateLog("Please enter a width for the image", Color.red);
		else if(Integer.parseInt(imageWidthField.getText()) <= 0)
			OutputPanel.updateLog("Please enter a positive integer greater than 0 for the width", Color.red);
		else if(imageHeightField.getText().isEmpty())
			OutputPanel.updateLog("Please enter a height for the image", Color.red);
		else if(Integer.parseInt(imageHeightField.getText()) <= 0)
			OutputPanel.updateLog("Please enter a positive integer greater than 0 for the height", Color.red);
		else if(imageIterationsField.getText().isEmpty())
			OutputPanel.updateLog("Please enter a value for the iterations of the image", Color.red);
		else if(Integer.parseInt(imageIterationsField.getText()) <= 0)
			OutputPanel.updateLog("Please enter a positive integer greater than 0 for the iterations", Color.red);
		else if(imageFilenameField.getText().isEmpty())
			OutputPanel.updateLog("Please enter a name for the image", Color.red);
		else if(nameUsed(imageFilenameField.getText()))
			OutputPanel.updateLog("This name has already been used. Please choose another", Color.red);
		else
			valid = true;
			
		
		return valid;
	}
	
	
	private static boolean nameUsed(String s) {
		int i = 0;
		
		while(i < Data.getSavedLocationNames().size()) {
			if(s.equals(Data.getSavedLocationNames().get(i)))
				return true;
			i++;
		}
		
		return false;
	}
	
	
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, imageWidthLabel, 20, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, imageWidthLabel, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageWidthLabel, 50, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, imageWidthLabel, 0, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.WEST, imageHeightLabel, 0, SpringLayout.WEST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageHeightLabel, 0, SpringLayout.EAST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.NORTH, imageHeightLabel, 65, SpringLayout.SOUTH, imageWidthField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageHeightLabel, 95, SpringLayout.SOUTH, imageWidthField);
		
		currentLayout.putConstraint(SpringLayout.WEST, imageIterationsLabel, 0, SpringLayout.WEST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageIterationsLabel, 0, SpringLayout.EAST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.NORTH, imageIterationsLabel, 15, SpringLayout.SOUTH, imageHeightField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageIterationsLabel, 45, SpringLayout.SOUTH, imageHeightField);
		
		currentLayout.putConstraint(SpringLayout.WEST, imageFilenameLabel, 0, SpringLayout.WEST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageFilenameLabel, 0, SpringLayout.EAST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.NORTH, imageFilenameLabel, 15, SpringLayout.SOUTH, imageIterationsField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageFilenameLabel, 45, SpringLayout.SOUTH, imageIterationsField);
		
		currentLayout.putConstraint(SpringLayout.NORTH, imageWidthField, 5, SpringLayout.SOUTH, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.WEST, imageWidthField, 0, SpringLayout.WEST, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageWidthField, 35, SpringLayout.SOUTH, imageWidthLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageWidthField, -10, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, imageHeightField, 5, SpringLayout.SOUTH, imageHeightLabel);
		currentLayout.putConstraint(SpringLayout.WEST, imageHeightField, 0, SpringLayout.WEST, imageWidthField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageHeightField, 35, SpringLayout.SOUTH, imageHeightLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageHeightField, 0, SpringLayout.EAST, imageWidthField);
		
		currentLayout.putConstraint(SpringLayout.NORTH, imageIterationsField, 5, SpringLayout.SOUTH, imageIterationsLabel);
		currentLayout.putConstraint(SpringLayout.WEST, imageIterationsField, 0, SpringLayout.WEST, imageWidthField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageIterationsField, 35, SpringLayout.SOUTH, imageIterationsLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageIterationsField, 0, SpringLayout.EAST, imageWidthField);
		
		currentLayout.putConstraint(SpringLayout.NORTH, imageFilenameField, 5, SpringLayout.SOUTH, imageFilenameLabel);
		currentLayout.putConstraint(SpringLayout.WEST, imageFilenameField, 0, SpringLayout.WEST, imageWidthField);
		currentLayout.putConstraint(SpringLayout.SOUTH, imageFilenameField, 35, SpringLayout.SOUTH, imageFilenameLabel);
		currentLayout.putConstraint(SpringLayout.EAST, imageFilenameField, 0, SpringLayout.EAST, imageWidthField);
		
		currentLayout.putConstraint(SpringLayout.NORTH, keepRatioToggleButton, 15, SpringLayout.SOUTH, imageWidthField);
		currentLayout.putConstraint(SpringLayout.WEST, keepRatioToggleButton, -200, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.EAST, keepRatioToggleButton, -20, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, keepRatioToggleButton, -15, SpringLayout.NORTH, imageHeightLabel);
		
		currentLayout.putConstraint(SpringLayout.NORTH, saveImageButton, 30, SpringLayout.SOUTH, imageFilenameField);
		currentLayout.putConstraint(SpringLayout.WEST, saveImageButton, 40, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveImageButton, -20, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, saveImageButton, -40, SpringLayout.EAST, this);
	}
	
}