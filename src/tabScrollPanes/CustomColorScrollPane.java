package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import gui.TabbedPanel;
import tabs.CustomColorTab;

public class CustomColorScrollPane extends ScrollPane {

	private CustomColorTab customColorTab;
	
	public CustomColorScrollPane() {
		customColorTab = new CustomColorTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(customColorTab);
		customColorTab.setVisible(true);
		customColorTab.setPreferredSize(TabbedPanel.getMinimumDimensions());
			
	}
}