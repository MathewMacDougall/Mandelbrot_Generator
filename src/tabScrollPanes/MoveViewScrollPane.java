package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import gui.TabbedPanel;
import tabs.MoveViewTab;

public class MoveViewScrollPane extends ScrollPane {

	private MoveViewTab moveViewTab;
	
	public MoveViewScrollPane() {
		moveViewTab = new MoveViewTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(moveViewTab);
		moveViewTab.setVisible(true);
		moveViewTab.setPreferredSize(TabbedPanel.getMinimumDimensions());
			
	}
}