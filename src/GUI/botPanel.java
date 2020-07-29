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

public class botPanel extends JPanel implements Observer{
	private JButton info = new JButton("Show Info");
	private boolean raceFlag=false;		
	private JFrame f =null;
	private JTable table = new JTable();
	private Arena arena ;
	public Arena getArena() {
		return arena;
	}
	private JScrollPane scroll;
	private CarRaceBuilder race;
	private topPanel arenaChoosePanel;
	private midPanel racerChoosePanel;
	private JTextField numOfCars;
	private int N = 8;
	
	public botPanel(topPanel arenaPanel) {
		
		
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
						System.out.println("######################################################################");
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
		
		
		
		numOfCars = new JTextField();
		numOfCars.setMaximumSize(new Dimension(180, 180));
		JLabel max = new JLabel("Num Of Car (till = "+LandArena.DEFAULT_MAX_RACERS+")");
		add(max);
		add(numOfCars);
		
		JButton carrace = new JButton("Set Car Race");
		add(carrace);
		this.add(Box.createGlue());
		carrace.addActionListener(new ActionListener() 
		{
			@Override
			public void actionPerformed(ActionEvent e) 
			{
				try{
					N = Integer.parseInt(numOfCars.getText());
					if(N <= 0) return;
				}catch(Exception exc){
					N = 8;
				}
				race = new CarRaceBuilder(N);
				arena = race.getArena();
				arenaPanel.setArena(arena);
				updatePanelForCarRace();
			}
		});
		

		// ------------------------------------------------------------------------------------------------------------------------

		arena = arenaPanel.getArena();
		updateTable(arena);
		for (int i = 0; i < table.getColumnModel().getColumnCount(); i++) {
			switch (i){
				case 0:
				case 1:
				case 2:
				case 4:	table.getColumnModel().getColumn(0).setPreferredWidth(90);	break;
				case 3:	table.getColumnModel().getColumn(3).setPreferredWidth(120);	break;
				default: break;
			}
		}
		table.setRowHeight(30);
		JScrollPane scroll = new JScrollPane(table);
		WorkerThread wt = new WorkerThread(arena);
		wt.start();
		
		info = new JButton("Show Info");
		add(info);
		info.addActionListener(new ActionListener() 
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
						int /*w = table.getWidth(); if(w == 0)*/ w = (int) mainFrame.PANELS_WIDTH;
						int /*h = table.getHeight();if(h == 0)*/ h = (int) mainFrame.PANELS_HIGHT / 3;
						
						f = new JFrame("Racers information");
						f.setSize(w, h);
						f.getContentPane().setBackground(Color.white);
						f.add(scroll);
						f.setVisible(true);	
						f.setLocationRelativeTo(null);
						f.setLocation(new Point(100, f.getLocation().y + h));

						wt.setArena(arena);
						updateTable(arena);
					}
				}
				catch(Exception ex){}
			}
		});	
	}
	
	
	
	public void setRaceFlag(boolean flag) {
		this.raceFlag=flag;
	}
	public boolean getRaceFlag() {
		return this.raceFlag;
	}
	@Override
	public void update(Observable o, Object arg) {
		if(arena == null && arg instanceof Arena) arena = (Arena)arg;
	}
	
	
	private synchronized void updateTable(Arena arena) {
		try {
			if(arena == null &&  this.arena == null) return;
			else if(arena == null) arena = this.arena;

			int numracers = arena.getAllRacers().size();
			
			String[] columns = {"Dirug" , "Serial No" , "Racer name", "Current speed", "Max speed", "Current x location", "Finished" };					
			Object[][] s = new Object[numracers][columns.length];
	
			int i = 0;
			for (Racer r : arena.getAllRacers()) 
			{
					if(r != null)
					{
						s[i][0] = r.getDirug();
						s[i][1] = r.getSerialNumber();
						s[i][2] = r.getName();
						s[i][3] = r.getCurSpeed();
						s[i][4] = r.getMaxSpeed();
						s[i][5] = r.getCurLocation().getX();
						s[i][6] = r.getState() != null ? r.getState().toString() : "None";
						i++;
					}
			}
			DefaultTableModel tm = new DefaultTableModel(s, columns);
			table.setModel(tm); 
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
	        			try { sleep(100); } catch (InterruptedException e) {}
	        		}
	        		break;
	        	}
	        	try { sleep(1000); } catch (InterruptedException e) {}
	        }
	    }
	}
	
	
	public topPanel getArenaChoosePanel() {
		return arenaChoosePanel;
	}
	public void setArenaChoosePanel(topPanel arenaChoosePanel) {
		this.arenaChoosePanel = arenaChoosePanel;
	}
	public midPanel getRacerChoosePanel() {
		return racerChoosePanel;
	}
	public void setRacerChoosePanel(midPanel racerChoosePanel) {
		this.racerChoosePanel = racerChoosePanel;
	}
	public void updatePanelForCarRace(){
		this.arenaChoosePanel.updateToCarRaceArena(arena);
		arena = arenaChoosePanel.getArena();
		
		this.racerChoosePanel.updateToCarRaceArena(arena, race.getCars());
	}
}
