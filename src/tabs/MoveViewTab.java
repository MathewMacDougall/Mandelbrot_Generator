package tabs;

import java.awt.Color;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.SpringLayout;
import javax.swing.SwingConstants;
import java.awt.Font;

public class MoveViewTab extends JPanel {

	private final SpringLayout currentLayout;
	
	private static int sliderMax;
	private static int sliderMin;
	
	private final JLabel xSliderLabel;
	private final JLabel ySliderLabel;
	private static JSlider xSlider;
	private static JSlider ySlider;
	
	private static JPanel moveViewTab;
	
	public MoveViewTab() {
		currentLayout = new SpringLayout();
		
		sliderMax = 100;
		sliderMin = -sliderMax;
		
		xSliderLabel = new JLabel("Shift Horizontal View (Up to 1 screen width)");
		ySliderLabel = new JLabel("Shift Vertical View (Up to 1 screen height");
		xSlider = new JSlider();
		ySlider = new JSlider();
		
		moveViewTab = this;
		
		setBackground(new Color(175, 238, 238));
		setupPanel();
		setupLayout();
	}
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		this.add(xSliderLabel);
		this.add(ySliderLabel);
		this.add(xSlider);
		this.add(ySlider);
		
		xSliderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		xSliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		xSlider.setPaintLabels(true);
		xSlider.setPaintTicks(true);
		xSlider.setSnapToTicks(false);
		xSlider.setMaximum(sliderMax);
		xSlider.setMinimum(sliderMin);
		xSlider.setMajorTickSpacing(25);
		xSlider.setOrientation(SwingConstants.HORIZONTAL);
		xSlider.setValue(0);
		
		ySliderLabel.setHorizontalAlignment(SwingConstants.CENTER);
		ySliderLabel.setFont(new Font("Tahoma", Font.BOLD, 14));
		ySlider.setPaintLabels(true);
		ySlider.setPaintTicks(true);
		ySlider.setMajorTickSpacing(25);
		ySlider.setSnapToTicks(false);
		ySlider.setMaximum(sliderMax);
		ySlider.setMinimum(sliderMin);
		ySlider.setOrientation(SwingConstants.VERTICAL);
		ySlider.setValue(0);
		
		ySlider.setToolTipText("Adjusts how much the field of view will be shifted vertically");
		xSlider.setToolTipText("Adjusts how far the field of view will be shifted horizontally");
	}
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, xSliderLabel, 30, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, xSliderLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, xSliderLabel, 60, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, xSliderLabel, 0, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.WEST, ySliderLabel, 0, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.EAST, ySliderLabel, 0, SpringLayout.EAST, this);
		currentLayout.putConstraint(SpringLayout.NORTH, ySliderLabel, 40, SpringLayout.SOUTH, xSlider);
		currentLayout.putConstraint(SpringLayout.SOUTH, ySliderLabel, 70, SpringLayout.SOUTH, xSlider);
		
		currentLayout.putConstraint(SpringLayout.NORTH, xSlider, 15, SpringLayout.SOUTH, xSliderLabel);
		currentLayout.putConstraint(SpringLayout.WEST, xSlider, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, xSlider, 80, SpringLayout.SOUTH, xSliderLabel);
		currentLayout.putConstraint(SpringLayout.EAST, xSlider, -10, SpringLayout.EAST, this);
		
		currentLayout.putConstraint(SpringLayout.NORTH, ySlider, 15, SpringLayout.SOUTH, ySliderLabel);
		currentLayout.putConstraint(SpringLayout.WEST, ySlider, 125, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, ySlider, -30, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, ySlider, -125, SpringLayout.EAST, this);
	}
	
	public static void resetSliders() {
		xSlider.setValue(0);
		ySlider.setValue(0);
	}
	
	public static double getXShift() {
		return (double) xSlider.getValue() / sliderMax;
	}
	
	public static double getYShift() {
		return (double) ySlider.getValue() / sliderMax;
	}
	
	public static JPanel getMoveViewTab() {
		return moveViewTab;
	}
	
	public boolean isSelected() {
		return this.isSelected();
	}
	
	public static int getSliderMax() {
		return sliderMax;
	}
	
	public static int getSliderMin() {
		return sliderMin;
	}
	
	
}