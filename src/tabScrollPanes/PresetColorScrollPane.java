package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import gui.TabbedPanel;
import tabs.PresetColorTab;

public class PresetColorScrollPane extends ScrollPane {

	private PresetColorTab presetColorTab;
	
	public PresetColorScrollPane() {
		presetColorTab = new PresetColorTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(presetColorTab);
		presetColorTab.setVisible(true);
		presetColorTab.setPreferredSize(TabbedPanel.getMinimumDimensions());	
	}
}
