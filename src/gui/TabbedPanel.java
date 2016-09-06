package gui;

//Import java packages
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JTabbedPane;


//Import my classes
import tabScrollPanes.CustomColorScrollPane;
import tabScrollPanes.FormulaScrollPane;
import tabScrollPanes.LocationsScrollPane;
import tabScrollPanes.MoveViewScrollPane;
import tabScrollPanes.PresetColorScrollPane;
import tabScrollPanes.SaveImageScrollPane;


public class TabbedPanel extends JTabbedPane {

	private final int minimumHeight;
	private static Dimension minimumDimensions;
	
	private final PresetColorScrollPane presetColorScrollPane;
	private final CustomColorScrollPane customColorScrollPane;
	private final MoveViewScrollPane moveViewScrollPane;
	private final FormulaScrollPane formulaScrollPane;
	private final LocationsScrollPane locationsScrollPane;
	private final SaveImageScrollPane saveImageScrollPane;
	
	private final String presetColorsTabTitle;
	private final String customColorsTabTitle;
	private final String moveViewTabTitle;
	private final String formulaTabTitle;
	private final String locationsTabTitle;
	private final String saveImageTabTitle;
	
	private static JTabbedPane tabbedPanel;
	
	public TabbedPanel() {
		minimumHeight = 480;
		minimumDimensions = new Dimension(0, minimumHeight);
		
		presetColorsTabTitle = "Preset Colors";
		customColorsTabTitle = "Custom Colors";
		moveViewTabTitle = "Move View";
		formulaTabTitle = "Formula";
		locationsTabTitle = "Locations";
		saveImageTabTitle = "Save Image";
		
		presetColorScrollPane = new PresetColorScrollPane();
		customColorScrollPane = new CustomColorScrollPane();
		moveViewScrollPane = new MoveViewScrollPane();
		formulaScrollPane = new FormulaScrollPane();
		locationsScrollPane = new LocationsScrollPane();
		saveImageScrollPane = new SaveImageScrollPane();
		
		tabbedPanel = this;
		
		setupPanel();
	}
	
	
	private void setupPanel() {
		this.addTab(presetColorsTabTitle, presetColorScrollPane);
		this.addTab(customColorsTabTitle, customColorScrollPane);
		this.addTab(moveViewTabTitle, moveViewScrollPane);
		this.addTab(formulaTabTitle, formulaScrollPane);
		this.addTab(locationsTabTitle, locationsScrollPane);
		this.addTab(saveImageTabTitle, saveImageScrollPane);
	}
	
	public static int getSelectedTab() {
		return tabbedPanel.getSelectedIndex();
	}
	
	
	public final static Dimension getMinimumDimensions() {
		return minimumDimensions;
	}
}
