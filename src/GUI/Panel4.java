package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import Builder.CarRaceBuilder;
import game.arenas.Arena;
import game.arenas.land.LandArena;
import game.racers.Racer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Panel4 extends JPanel implements Observer{
	
	private static final long serialVersionUID = 1L;
	private JButton information = new JButton("Show Info");
	private boolean tampRace=false;		
	private JFrame temp1 =null;
	private JTable strong = new JTable();
	private Arena arena ;
	public Arena getArena() {
		return arena;
	}
	private JScrollPane rolling;
	private CarRaceBuilder racers;
	private Panel2 choice;
	private Panel3 choice1;
	private JTextField carsMiss;
	private int l = 8;
	
	public Panel4(Panel2 arenaPanel) {
		
		
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		JButton racestart = new JButton("Start Race");
		add(racestart);
		this.add(Box.createGlue());
		racestart.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{

				if (!(arenaPanel.getFlagArena())) 
				{
					JOptionPane.showMessageDialog(null, "Arena dosen't exists ,please build arena first.");
				}
				else if (arenaPanel.getArena().getActiveRacers().size()<1) 
				{
					JOptionPane.showMessageDialog(null, "Something went wrong! Please add racer first before starting a race.");
				}
				
				try 
				{
					if(arenaPanel.getArena()!=null)
					{
						Thread t1=new Thread(new Runnable() 
						{
							@Override
							public void run() {
								//arenaPanel.getArena().initRace(); // call init in startRace()
								arenaPanel.getArena().startRace();
							}
						});
						t1.start();
					}
					else 
					{
						JOptionPane.showMessageDialog(null, "Something went wrong! You have started a race.");
					}
				}
				catch(Exception ex) {}
				racestart.setEnabled(false);
			}
		});

		// ------------------------------------------------------------------------------------------------------------------------
		
		
		
		carsMiss = new JTextField();
		carsMiss.setMaximumSize(new Dimension(180, 180));
		JLabel max = new JLabel("Num Of Car: "+LandArena.DEFAULT_MAX_RACERS);
		add(max);
		add(carsMiss);
		
		JButton carrace = new JButton("init Race car");
		add(carrace);
		this.add(Box.createGlue());
		carrace.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try{
					l = Integer.parseInt(carsMiss.getText());
					if(l <= 0) return;
				}catch(Exception exc){
					l = 8;
				}
				racers = new CarRaceBuilder(l);
				arena = racers.getArena();
				arenaPanel.setArena(arena);
				updatePanelForCarRace();
			}
		});
		

		// ------------------------------------------------------------------------------------------------------------------------

		arena = arenaPanel.getArena();
		updateTable(arena);
		for (int i = 0; i < strong.getColumnModel().getColumnCount(); i++) {
			switch (i){
				case 0:
				case 1:
				case 2:
				case 4:	strong.getColumnModel().getColumn(0).setPreferredWidth(90);	break;
				case 3:	strong.getColumnModel().getColumn(3).setPreferredWidth(120);	break;
				default: break;
			}
		}
		strong.setRowHeight(30);
		JScrollPane scroll = new JScrollPane(strong);
		WorkerThread wt = new WorkerThread(arena);
		wt.start();
		
		information = new JButton("Show Info");
		add(information);
		information.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try 
				{
					//System.out.println("info Button do Action Listener");
					if (!arenaPanel.getFlagArena())
						JOptionPane.showMessageDialog(null, "You havent build arena yet,build arena first!");
					else 
					{
						int /*w = table.getWidth(); if(w == 0)*/ w = (int) Frame.PW;
						int /*h = table.getHeight();if(h == 0)*/ h = (int) Frame.PH / 3;
						
						temp1 = new JFrame("Racers information");
						temp1.setSize(w, h);
						temp1.getContentPane().setBackground(Color.white);
						temp1.add(scroll);
						temp1.setVisible(true);	
						temp1.setLocationRelativeTo(null);
						temp1.setLocation(new Point(100, temp1.getLocation().y + h));

						wt.setArena(arena);
						updateTable(arena);
					}
				}
				catch(Exception ex){}
			}
		});	
	}
	
	
	
	public void setRaceFlag(boolean flag) {
		/*
		 *set Race Flag 
		 */
		this.tampRace=flag;
	}
	public boolean getRaceFlag() {
		/*
		 *get Race Flag 
		 */
		return this.tampRace;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof Arena && arena == null) {
			arena = (Arena)arg;
		}
	}
	
	
	private synchronized void updateTable(Arena arena) {
		try {
			if(arena == null &&  this.arena == null) {
				return;
			}
			else if(arena == null) {
				arena = this.arena;
			}

			int numracers = arena.getAllRacers().size();
			
			String[] colls = {"Rating" , "No Serial" , "Racer name", "Current speed", "Max speed", "Current X location", "Finished" };					
			Object[][] s = new Object[numracers][colls.length];
	
			int i = 0;
			for (Racer r : arena.getAllRacers()) 
			{
					if(r != null)
					{
						s[i][0] = r.getRating();
						s[i][1] = r.getSerialNumber();
						s[i][2] = r.getName();
						s[i][3] = r.getCurSpeed();
						s[i][4] = r.getMaxSpeed();
						s[i][5] = r.getCurLocation().getX();
						s[i][6] = r.getState() != null ? r.getState().toString() : "Empty";
						i = i+1;
					}
			}
			DefaultTableModel tm = new DefaultTableModel(s, colls);
			strong.setModel(tm); 
		}catch(Exception ex) {
			
		}
	}
	
	class WorkerThread extends Thread {
		private Arena arena;
	    public WorkerThread(Arena arena) {
	    	this.arena = arena;
	        setDaemon(true); 
	    }
	    
	    public void setArena(Arena arena) {
	    	this.arena = arena;
	    }
	    
	    public void run() 
	    {
	    	boolean flag = false;
	        while (true) 
	        {
	        	if(this.arena != null)	flag = arena.isHasStart();
	        	if(flag)
	        	{
	        		while(arena.isFinished() == false){
	        			updateTable(null);
	        			try { sleep(70); } catch (InterruptedException e) {}
	        		}
	        		break;
	        	}
	        	try { sleep(1000); } catch (InterruptedException e) {}
	        }
	    }
	}
	
	
	public Panel2 getArenaChoosePanel() {
		/*
		 *get Arena Choose Panel 
		 */
		return choice;
	}
	public void setArenaChoosePanel(Panel2 arenaChoosePanel) {
		/*
		 * set Arena Choose Panel
		 */
		this.choice = arenaChoosePanel;
	}
	public Panel3 getRacerChoosePanel() {
		/*
		 * get Racer Choose Panel
		 */
		return choice1;
	}
	public void setRacerChoosePanel(Panel3 racerChoosePanel) {
		/*
		 * set Racer Choose Panel
		 */
		this.choice1 = racerChoosePanel;
	}
	public void updatePanelForCarRace(){
		/*
		 * update
		 */
		this.choice.UpdateCar(arena);
		arena = choice.getArena();
		
		this.choice1.updateToCarRaceArena(arena, racers.getCars());
	}
}