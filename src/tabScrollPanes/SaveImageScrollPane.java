package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import gui.TabbedPanel;
import tabs.SaveImageTab;

public class SaveImageScrollPane extends ScrollPane {

	private SaveImageTab saveImageTab;
	
	public SaveImageScrollPane() {
		saveImageTab = new SaveImageTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(saveImageTab);
		saveImageTab.setVisible(true);
		saveImageTab.setPreferredSize(TabbedPanel.getMinimumDimensions());	
	}
}