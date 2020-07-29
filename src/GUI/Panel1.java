package GUI;

import javax.swing.BoxLayout;
import javax.swing.JPanel;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */

public class Panel1 extends JPanel{

	
	private static final long serialVersionUID = 1L;
	private Panel2 arenaPanel;
	private Panel3 racerPanel;
	private Panel4 racePanel;
	
	
	
	public Panel1() {

		this.setLayout(new BoxLayout(this,BoxLayout.Y_AXIS));		
		arenaPanel=new Panel2();
		racerPanel=new Panel3(arenaPanel);
		racePanel=new Panel4(arenaPanel);
	
		
		racePanel.setArenaChoosePanel(arenaPanel);
		racePanel.setRacerChoosePanel(racerPanel);
		racerPanel.setArenePanel(arenaPanel);
		
		add(arenaPanel);		
		add(racerPanel);
		add(racePanel);
		
		
	}
	public Panel1 getMainPanel() {
		/*
		 * get Main Panel
		 */
		return this;
	}
	public Panel2 getArenaPanel() {
		/*
		 *get Arena Panel 
		 */
		return arenaPanel;
	}

	public Panel3 getRacerPanel() {
		/*
		 *get Racer Panel 
		 */
		return racerPanel;
		
	}
	public Panel4 getRacePanel() {
		/*
		 * get Race Panel
		 */
		return racePanel;
	}
}