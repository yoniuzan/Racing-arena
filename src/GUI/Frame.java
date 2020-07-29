package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.util.Observer;

import javax.swing.BorderFactory;
import javax.swing.JFrame;

import game.racers.Prototype;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */



public class Frame extends JFrame{

	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final int PW = 1000;
	public static final int PH = 700;
	public static final int PS = 1000;
	private Panel1 panel1;
	private Panel5 arenasPanel;
	
	public Frame() {
		super("Race");
		Prototype.load();
		this.setSize(1000,700);
		setLayout(new BorderLayout());	
		panel1=new Panel1();
		panel1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		arenasPanel=new Panel5();
		panel1.getArenaPanel().setArenasPANEL(arenasPanel);
		panel1.getArenaPanel().setArenaPANELSize(this);
		panel1.getRacerPanel().setArenasPANEL(arenasPanel);
		Observer ob = panel1.getRacePanel();
		arenasPanel.setTableObserver(ob);
		
		add(panel1, BorderLayout.EAST);
		add(arenasPanel,BorderLayout.CENTER);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);

	}
	
}