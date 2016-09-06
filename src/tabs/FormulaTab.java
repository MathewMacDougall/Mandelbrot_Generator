package tabs;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SpringLayout;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SpinnerListModel;

public class FormulaTab extends JPanel {

	private final SpringLayout currentLayout;
	
	private final JLabel zExponentSliderLabel;
	private final JLabel formulaOverviewLabel;
	
	private final int zMin;
	private final int zMax;
	private final int zSpacing;
	private final int zDefault;
	
	private static JSpinner zExponentSpinner;
	
	
	public FormulaTab() {
		currentLayout = new SpringLayout();
		
		zExponentSliderLabel = new JLabel("Choose the exponent of Z (2 by default)");
		formulaOverviewLabel = new JLabel("Formula:     Z = Z ^ 2 + C");
		zExponentSpinner = new JSpinner();
		
		zMin = 2;
		zMax = 25;
		zSpacing = 1;
		zDefault = 2;
		
		setBackground(new Color(175, 238, 238));
		
		setupPanel();
		setupLayout();
	}
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(zExponentSliderLabel);
		this.add(formulaOverviewLabel);
		this.add(zExponentSpinner);
	
		zExponentSpinner.setToolTipText("The exponent of Z in the formula");
		
		zExponentSliderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		zExponentSliderLabel.setHorizontalAlignment(SwingConstants.CENTER);

		formulaOverviewLabel.setToolTipText("Describes the formula used to calculate the Mandelbrot");
		formulaOverviewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		formulaOverviewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		zExponentSpinner.setModel(new SpinnerNumberModel(zDefault, zMin, zMax, zSpacing));
		zExponentSpinner.setFont(new Font("Tahoma", Font.BOLD, 99));
	}
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, formulaOverviewLabel, 20, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, formulaOverviewLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, formulaOverviewLabel, 50, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, formulaOverviewLabel, 0, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.WEST, zExponentSliderLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, zExponentSliderLabel, 0, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, zExponentSliderLabel, 30, SpringLayout.SOUTH, formulaOverviewLabel);
		currentLayout.putConstraint(SpringLayout.SOUTH, zExponentSliderLabel, 60, SpringLayout.SOUTH, formulaOverviewLabel);
		
		currentLayout.putConstraint(SpringLayout.NORTH, zExponentSpinner, 20, SpringLayout.SOUTH, zExponentSliderLabel);
		currentLayout.putConstraint(SpringLayout.WEST, zExponentSpinner, 75, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, zExponentSpinner, 130, SpringLayout.SOUTH, zExponentSliderLabel);
		currentLayout.putConstraint(SpringLayout.EAST, zExponentSpinner, -75, SpringLayout.EAST, this);
	}
	
	
	public static int getExponent() {
		return (int) zExponentSpinner.getValue();
	}
	
	public static void setExponent(int e) {
		FormulaTab.zExponentSpinner.setValue(e);
	}
}