package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.ScrollPane;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextPane;
import javax.swing.SpringLayout;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

import java.awt.Font;

public class OutputPanel extends JPanel {

	private final SpringLayout currentLayout;

	private static ScrollPane outputScrollPane;
	private static JLabel systemOutputLabel;
	private static JTextPane outputTextPane;
	private static StyledDocument outputDoc;
	
	
	public OutputPanel() {
		currentLayout = new SpringLayout();
		
		outputTextPane = new JTextPane();
		systemOutputLabel = new JLabel("System Output");
		outputScrollPane = new ScrollPane();
		outputDoc = outputTextPane.getStyledDocument();
		
		this.setBackground(new Color(176, 196, 222));
		
		setupPanel();
		setupLayout();
	}
	
	
	public static void updateLog(String s, Color c) {
		outputScrollPane.setScrollPosition(0, outputTextPane.getHeight());
		
		SimpleAttributeSet keyWord = new SimpleAttributeSet();
		StyleConstants.setForeground(keyWord, c);
		
		String string = s + "\n";
		
		try {
			outputDoc.insertString(outputDoc.getLength(), string, keyWord);
		}
		catch(Exception e) {
			
		}
	}
	
	
	
	
	private void setupPanel() {
		this.setLayout(currentLayout);
		outputScrollPane.add(outputTextPane);
		this.add(outputScrollPane);
		this.add(systemOutputLabel);
		
		systemOutputLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		
		outputTextPane.setToolTipText("Displays messages from the system");
		outputTextPane.setFont(new Font("Tahoma", Font.PLAIN, 20));
		outputTextPane.setEditable(false);
		
	}
	
	
	private void setupLayout() {
		currentLayout.putConstraint(SpringLayout.NORTH, systemOutputLabel, 0, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, systemOutputLabel, 0, SpringLayout.WEST, outputScrollPane);
		currentLayout.putConstraint(SpringLayout.SOUTH, systemOutputLabel, 0, SpringLayout.NORTH, outputScrollPane);
		currentLayout.putConstraint(SpringLayout.EAST, systemOutputLabel, 0, SpringLayout.EAST, outputScrollPane);
		
		currentLayout.putConstraint(SpringLayout.NORTH, outputScrollPane, 30, SpringLayout.NORTH, this);
		currentLayout.putConstraint(SpringLayout.WEST, outputScrollPane, 10, SpringLayout.WEST, this);
		currentLayout.putConstraint(SpringLayout.SOUTH, outputScrollPane, -10, SpringLayout.SOUTH, this);
		currentLayout.putConstraint(SpringLayout.EAST, outputScrollPane, -10, SpringLayout.EAST, this);
	}
	
}
