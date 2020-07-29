
package GUI;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;


public class mainPanel extends JPanel{

	private topPanel arenaPanel;
	private midPanel racerPanel;
	private botPanel racePanel;
	
	
	
	public mainPanel() {

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));		
		arenaPanel=new topPanel();
		racerPanel=new midPanel(arenaPanel);
		racePanel=new botPanel(arenaPanel);
	
		
		racePanel.setArenaChoosePanel(arenaPanel);
		racePanel.setRacerChoosePanel(racerPanel);
		racerPanel.setArenePanel(arenaPanel);
		
		add(arenaPanel);		
		add(racerPanel);
		add(racePanel);
		
		
	}
	public mainPanel getMainPanel() {
		return this;
	}
	public topPanel getArenaPanel() {
		return arenaPanel;
	}

	public midPanel getRacerPanel() {
		return racerPanel;
		
	}
	public botPanel getRacePanel() {
		return racePanel;
	}
}
