

package GUI;

import java.awt.Button;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JPanel;
import javax.swing.Timer;

import game.arenas.Arena;
import game.racers.Racer;

import javax.imageio.ImageIO;

public class leftPanel extends JPanel implements ActionListener, Observer {

	private Image bg;
	private leftPanel arenasPanel;
	private Observer InfoTableObserver;
	private Arena arena;
	private ArrayList<Button> imageArrayButton = new ArrayList<Button>();
	private ArrayList<Image> imageArray = new ArrayList<Image>();
	private static ArrayList<Double> racers_X_Val = new ArrayList<Double>();
	private Timer timer;
	private final int DELAY=20;
	public leftPanel() {
		timer=new Timer(DELAY,this);
		timer.start();
	}
	@Override
	protected synchronized void paintComponent(Graphics g) {
		super.paintComponent(g);
		int w = getWidth();
		int h = getHeight();
		g.drawImage(bg, 0, 0, w, h, this);	
		for (Image img : imageArray) {
			g.drawImage(img, racers_X_Val.get(imageArray.indexOf(img)).intValue(), imageArray.indexOf(img)*50, 50, 50, this);			
		}
	}


	public void bg(String name) {
		try {
			//System.out.println("name icon: "+ name+".jpg");
			bg = ImageIO.read(new File("src/icons/"+name+".jpg"));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		repaint();
	}
	
	public void setArena(Arena arena) {
		this.arena = arena;
		setTableObserver();
	}
	
	public boolean setTableObserver() {
		if(InfoTableObserver != null && this.arena != null) {
			this.arena.setTableObserver(InfoTableObserver);
			return true;
		}
		return false;
	}
	
	public boolean setTableObserver(Observer obs) {
		this.InfoTableObserver = obs;
		return setTableObserver();
	}
	
	
	public static synchronized void setX(int serialNumber,double newX) {
		racers_X_Val.set(serialNumber-1, newX);
	}
	public leftPanel getArenaPanel() {
		return this.arenasPanel;
	}

	public static ArrayList<Double> getRacersXval() {
		return racers_X_Val;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//repaint(); // shouldn't update here! 
	}
	
	@Override
	public void update(Observable obs, Object arg) {
		repaint();
		Racer r = (Racer)obs;
		r.getArena().update(r, null);
	}
	
	public void objects(String name, String color, Arena arena) {
		try {
			imageArray.add(ImageIO.read(new File("src/icons/" + name + color + ".png")));
			imageArrayButton.add(new Button());
			racers_X_Val.add(0.0);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		repaint();
		setArena(arena);
	}


	
}

