package tabScrollPanes;

//Import Java packages
import java.awt.ScrollPane;
import tabs.FormulaTab;
import gui.TabbedPanel;

public class FormulaScrollPane extends ScrollPane {

	private FormulaTab formulaTab;
	
	public FormulaScrollPane() {
		formulaTab = new FormulaTab();
		
		setupPane();
	}
		
	private void setupPane() {
		this.add(formulaTab);
		formulaTab.setVisible(true);
		formulaTab.setPreferredSize(TabbedPanel.getMinimumDimensions());
			
	}
}