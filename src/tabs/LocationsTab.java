package tabs;

import gui.GUI_Frame;
import gui.OutputPanel;

import java.awt.Choice;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;

import util.Calculations;
import util.Data;

import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class LocationsTab extends JPanel {

	private SpringLayout currentLayout;
	
	private JButton saveLocationButton;
	private JTextField saveLocationNameField;
	private JLabel savedLocationsLabel;
	private JButton loadLocationButton;
	private JButton deleteLocationButton;
	private static Choice locationChooser;
	
	
	public LocationsTab() {
		currentLayout = new SpringLayout();
		
		saveLocationButton = new JButton("Save this location as:");
		saveLocationNameField = new JTextField();
		savedLocationsLabel = new JLabel("SAVED LOCATIONS");
		loadLocationButton = new JButton("Load");
		deleteLocationButton = new JButton("Delete");
		locationChooser = new Choice();
		
		setBackground(new Color(175, 238, 238));
		
		setupPanel();
		setupLayout();
		
		Data.reloadLocationNames("savedLocationNames");
		Data.reloadLocationData("savedLocationData");
	}
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(saveLocationButton);
		this.add(saveLocationNameField);
		this.add(savedLocationsLabel);
		this.add(loadLocationButton);
		this.add(deleteLocationButton);
		this.add(locationChooser);
		
		saveLocationButton.setToolTipText("Saves the current location");
		loadLocationButton.setToolTipText("Loads the currently selected location");
		deleteLocationButton.setToolTipText("Deletes the currently selected location");
		
		locationChooser.add("None Selected");
		savedLocationsLabel.setFont(new Font("Tahoma", Font.BOLD, 24));
		savedLocationsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		saveLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(saveLocationNameField.getText().isEmpty()) {
					OutputPanel.updateLog("Please enter a name for this location", Color.red);
				}
				else if(inputIsValid(saveLocationNameField.getText())) {				
					Data.saveLocation(saveLocationNameField.getText(), Calculations.getmX1(), Calculations.getmX2(), Calculations.getmY1(), Calculations.getmY2(), Calculations.getExponent());
					locationChooser.add(saveLocationNameField.getText());
					locationChooser.select(saveLocationNameField.getText());
					saveLocationNameField.setText("");
				}	
			}
		});
		
		
		loadLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(locationChooser.getSelectedIndex() > 0) {
					Data.loadLoaction(locationChooser.getSelectedIndex() - 1, locationChooser.getSelectedItem());//-1 adjust for the "None_selected"
					GUI_Frame.getMandelbrotPanel().repaint();
				}
				else
					OutputPanel.updateLog("This is not a location and can't be loaded", Color.red);
			}
		});
		
		
		deleteLocationButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(locationChooser.getSelectedIndex() > 0) {
					System.out.println("delete");
					Data.deleteLocation(locationChooser.getSelectedIndex() - 1);
					locationChooser.remove(locationChooser.getSelectedIndex());
				}
				else
					OutputPanel.updateLog("This is not a location and can't be deleted", Color.red);
			}
		});
	}
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, saveLocationButton, 20, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, saveLocationButton, 30, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveLocationButton, 50, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, saveLocationButton, -30, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, saveLocationNameField, 10, SpringLayout.SOUTH, saveLocationButton);
		currentLayout.putConstraint(SpringLayout.WEST, saveLocationNameField, 0, SpringLayout.WEST, saveLocationButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveLocationNameField, 40, SpringLayout.SOUTH, saveLocationButton);
		currentLayout.putConstraint(SpringLayout.EAST, saveLocationNameField, 0, SpringLayout.EAST, saveLocationButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, savedLocationsLabel, 40, SpringLayout.SOUTH, saveLocationNameField);
		currentLayout.putConstraint(SpringLayout.WEST, savedLocationsLabel, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, savedLocationsLabel, 80, SpringLayout.SOUTH, saveLocationNameField);
		currentLayout.putConstraint(SpringLayout.EAST, savedLocationsLabel, -10, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, loadLocationButton, 15, SpringLayout.SOUTH, savedLocationsLabel);
		currentLayout.putConstraint(SpringLayout.WEST, loadLocationButton, 10, SpringLayout.WEST, savedLocationsLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, loadLocationButton, 45, SpringLayout.SOUTH, savedLocationsLabel);
		currentLayout.putConstraint(SpringLayout.EAST, loadLocationButton, 110, SpringLayout.WEST, savedLocationsLabel);
		
		currentLayout.putConstraint(SpringLayout.NORTH, deleteLocationButton, 0, SpringLayout.NORTH, loadLocationButton);
		currentLayout.putConstraint(SpringLayout.WEST, deleteLocationButton, -110, SpringLayout.EAST, savedLocationsLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, deleteLocationButton, 0, SpringLayout.SOUTH, loadLocationButton);
		currentLayout.putConstraint(SpringLayout.EAST, deleteLocationButton, -10, SpringLayout.EAST, savedLocationsLabel);
		
		currentLayout.putConstraint(SpringLayout.NORTH, locationChooser, 10, SpringLayout.SOUTH, loadLocationButton);
		currentLayout.putConstraint(SpringLayout.WEST, locationChooser, 0, SpringLayout.WEST, loadLocationButton);
		currentLayout.putConstraint(SpringLayout.EAST, locationChooser, 0, SpringLayout.EAST, deleteLocationButton);
	}
	
	private boolean inputIsValid(String s) {
		boolean valid = true;
		int i = 0;
		
		while(i < locationChooser.getItemCount()) {
			if(s.equals(locationChooser.getItem(i))) {
				valid = false;
				OutputPanel.updateLog("This name has already been used. Please choose another", Color.red);
				break;
			}
			i++;
		}
		
		return valid;
	}
	
	public static void addToChooser(String name) {
		locationChooser.add(name);
	}
	
	public static void resetChooser() {
		locationChooser.select("None Selected");
	}
	
	public static Choice getChooser() {
		return locationChooser;
	}
	
}