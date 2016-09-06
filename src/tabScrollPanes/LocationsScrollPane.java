package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import gui.TabbedPanel;
import tabs.LocationsTab;

public class LocationsScrollPane extends ScrollPane {

	private LocationsTab locationsTab;
	
	public LocationsScrollPane() {
		locationsTab = new LocationsTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(locationsTab);
		locationsTab.setVisible(true);
		locationsTab.setPreferredSize(TabbedPanel.getMinimumDimensions());
			
	}
}