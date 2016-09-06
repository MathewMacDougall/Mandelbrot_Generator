package tabs;

import gui.GUI_Frame;
import gui.MandelbrotPanel;
import gui.OutputPanel;

import java.awt.Choice;
import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;

import util.Coloring;
import util.Data;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class PresetColorTab extends JPanel {

	private SpringLayout currentLayout;
	
	private JButton saveColorButton;
	private JTextField saveColorNameField;
	private JButton loadColorButton;
	private JButton deleteColorButton;
	private static Choice colorChooser;
	private JButton generateRandomColorButton;
	private int numPresets;
	
	
	public PresetColorTab() {
		currentLayout = new SpringLayout();
		
		saveColorButton = new JButton("Save this color as:");
		loadColorButton = new JButton("Load");
		deleteColorButton = new JButton("Delete");
		generateRandomColorButton = new JButton("Generate Random Color");
		saveColorNameField = new JTextField();
		colorChooser = new Choice();
		
		numPresets = 4;
		
		setBackground(new Color(175, 238, 238));
		
		setupPanel();
		setupLayout();
		
		Data.reloadColorNames("savedColorNames");
		Data.reloadColorData("savedColorData");
	}
	
	public static void resetChooser() {
		colorChooser.select("None Selected");
	}
	
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(saveColorButton, "name_5195752793784");
		this.add(loadColorButton, "name_5195766343126");
		this.add(deleteColorButton, "name_5195774354986");
		this.add(generateRandomColorButton, "name_5195782767542");
		this.add(saveColorNameField, "name_5195790574538");
		this.add(colorChooser, "name_5195810367096");
		
		saveColorButton.setToolTipText("Saves the current color");
		loadColorButton.setToolTipText("Loads the currently selected color");
		deleteColorButton.setToolTipText("Deletes the currently selected color");
		generateRandomColorButton.setToolTipText("Generates a random color");
		
		colorChooser.add("None Selected");
		colorChooser.add("Preset_1");
		colorChooser.add("Preset_2");
		colorChooser.add("Preset_3");
		
		colorChooser.select("Preset_1");
		
		generateRandomColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Coloring.generateRandomColor();
				MandelbrotPanel.setUpdateColors(true);
				colorChooser.select("None Selected");
				GUI_Frame.getMandelbrotPanel().repaint();
			}
		});
		
		deleteColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(colorChooser.getSelectedIndex() > numPresets - 1) {
					Data.deleteColor(colorChooser.getSelectedIndex() - numPresets);
					colorChooser.remove(colorChooser.getSelectedIndex());
				}
				else
					OutputPanel.updateLog("This is not a color and can't be deleted", Color.red);
				
			}
		});
		
		saveColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(saveColorNameField.getText().isEmpty())
					OutputPanel.updateLog("Please enter a name for this color", Color.red);
				else if(CustomColorTab.nameUsed(saveColorNameField.getText()))
					OutputPanel.updateLog("This name has already been used. Please try another", Color.red);
				else {
					Data.saveColor(saveColorNameField.getText(), Coloring.getrShift(), Coloring.getgShift(), Coloring.getbShift(), Coloring.getrCompression(), Coloring.getgCompression(), Coloring.getbCompression());;
					colorChooser.add(saveColorNameField.getText());
					colorChooser.select(saveColorNameField.getText());
					saveColorNameField.setText("");
					System.out.println("save Color");
				}	
			}
		});
		
		loadColorButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				if(colorChooser.getSelectedIndex() == 1) {
					Coloring.loadPreset1();
					MandelbrotPanel.setUpdateColors(true);
					GUI_Frame.getMandelbrotPanel().repaint();
				}
				else if(colorChooser.getSelectedIndex() == 2) {
					Coloring.loadPreset2();
					MandelbrotPanel.setUpdateColors(true);
					GUI_Frame.getMandelbrotPanel().repaint();
				}
				else if(colorChooser.getSelectedIndex() == 3) {
					Coloring.loadPreset3();
					MandelbrotPanel.setUpdateColors(true);
					GUI_Frame.getMandelbrotPanel().repaint();
				}
				else if(colorChooser.getSelectedIndex() > numPresets - 1){
					MandelbrotPanel.setUpdateColors(true);
					Data.loadColor(colorChooser.getSelectedIndex() - numPresets, colorChooser.getItem(colorChooser.getSelectedIndex()));
					GUI_Frame.getMandelbrotPanel().repaint();
				}
				else
					OutputPanel.updateLog("This is not a color and can't be loaded", Color.red);
			}
		});
		
	}
	
	
	public static void addToChooser(String name) {
		colorChooser.add(name);
	}
	
	public static Choice getColorChooser() {
		return colorChooser;
	}
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, loadColorButton, 20, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, loadColorButton, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, loadColorButton, 50, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, loadColorButton, 125, SpringLayout.WEST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, deleteColorButton, 0, SpringLayout.NORTH, loadColorButton);
		currentLayout.putConstraint(SpringLayout.WEST, deleteColorButton, -125, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, deleteColorButton, 0, SpringLayout.SOUTH, loadColorButton);
		currentLayout.putConstraint(SpringLayout.EAST, deleteColorButton, -10, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, saveColorButton, 35, SpringLayout.SOUTH, generateRandomColorButton);
		currentLayout.putConstraint(SpringLayout.WEST, saveColorButton, 25, SpringLayout.WEST, generateRandomColorButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveColorButton, 65, SpringLayout.SOUTH, generateRandomColorButton);
		currentLayout.putConstraint(SpringLayout.EAST, saveColorButton, -25, SpringLayout.EAST, generateRandomColorButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, saveColorNameField, 10, SpringLayout.SOUTH, saveColorButton);
		currentLayout.putConstraint(SpringLayout.WEST, saveColorNameField, 0, SpringLayout.WEST, saveColorButton);
		currentLayout.putConstraint(SpringLayout.SOUTH, saveColorNameField, 40, SpringLayout.SOUTH, saveColorButton);
		currentLayout.putConstraint(SpringLayout.EAST, saveColorNameField, 0, SpringLayout.EAST, saveColorButton);
		
		currentLayout.putConstraint(SpringLayout.NORTH, colorChooser, 15, SpringLayout.SOUTH, loadColorButton);
		currentLayout.putConstraint(SpringLayout.WEST, colorChooser, 0, SpringLayout.WEST, loadColorButton);
		currentLayout.putConstraint(SpringLayout.EAST, colorChooser, 0, SpringLayout.EAST, deleteColorButton);
		
		currentLayout.putConstraint(SpringLayout.EAST, generateRandomColorButton, 0, SpringLayout.EAST, colorChooser);
		currentLayout.putConstraint(SpringLayout.WEST, generateRandomColorButton, 0, SpringLayout.WEST, colorChooser);
		currentLayout.putConstraint(SpringLayout.NORTH, generateRandomColorButton, 130, SpringLayout.SOUTH, colorChooser);
		currentLayout.putConstraint(SpringLayout.SOUTH, generateRandomColorButton, 160, SpringLayout.SOUTH, colorChooser);
	}
	
}
