package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.util.Observer;

import javax.swing.JLabel;
import javax.swing.JTextArea;

import game.racers.Prototype;

import javax.swing.BorderFactory;
import javax.swing.JFrame;




public class mainFrame extends JFrame{

	
	
	public static final int PANELS_SIZE = 300;
	public static final int PANELS_WIDTH = 1000;
	public static final int PANELS_HIGHT = 700;
	private mainPanel mainPanel;
	private leftPanel arenasPanel;
	
	
	
	public mainFrame() {
		super("Race");
		Prototype.load();
		
		this.setSize(PANELS_WIDTH + PANELS_SIZE, PANELS_HIGHT);
		setLayout(new BorderLayout());	
		mainPanel=new mainPanel();
			
		mainPanel.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

		arenasPanel=new leftPanel();
		
		mainPanel.getArenaPanel().setArenasPANEL(arenasPanel);
		
		mainPanel.getArenaPanel().setArenaPANELSize(this);
		
		mainPanel.getRacerPanel().setArenasPANEL(arenasPanel);
		
		Observer ob = mainPanel.getRacePanel();
		arenasPanel.setTableObserver(ob);
		
		add(mainPanel, BorderLayout.EAST);
		add(arenasPanel,BorderLayout.CENTER);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
}
	
