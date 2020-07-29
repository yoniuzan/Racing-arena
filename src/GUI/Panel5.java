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
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Panel5 extends JPanel implements ActionListener, Observer {

	
	private static final long serialVersionUID = 1L;
	private Image image;
	private Panel5 arenasP;
	private Observer infoTable;
	private Arena arena;
	private ArrayList<Button> imageArrayButton = new ArrayList<Button>();
	private ArrayList<Image> imageArray = new ArrayList<Image>();
	private static ArrayList<Double> xVal = new ArrayList<Double>();
	private Timer time;
	private final int wait=20;
	public Panel5() {
		time=new Timer(wait,this);
		time.start();
	}
	@Override
	protected synchronized void paintComponent(Graphics ig) {
		super.paintComponent(ig);
		int h = getHeight();
		int w = getWidth();
		ig.drawImage(image, 0, 0, w, h, this);	
		for (Image img : imageArray) {
			ig.drawImage(img, xVal.get(imageArray.indexOf(img)).intValue(), imageArray.indexOf(img)*70, 70, 70, this);			
		}
	}


	public void bg(String name) {
		try {
			//System.out.println("name icon: "+ name+".jpg");
			image = ImageIO.read(new File("src/icons/"+name+".jpg"));
		} catch (IOException e) {
			//e.printStackTrace();
		}
		repaint();
	}
	
	public void setArena(Arena arena) {
		/*
		 * set Arena
		 */
		this.arena = arena;
		setTableObserver();
	}
	
	public boolean setTableObserver() {
		/*
		 * set Table Observer
		 */
		if(infoTable != null && this.arena != null) {
			this.arena.setTableObserver(infoTable);
			return true;
		}
		return false;
	}
	
	public boolean setTableObserver(Observer obs) {
		/*
		 * set Table Observer
		 */
		this.infoTable = obs;
		return setTableObserver();
	}
	
	
	public static synchronized void setX(int serialNumber,double newX) {
		/*
		 * set x
		 */
		xVal.set(serialNumber-1, newX);
	}
	public Panel5 getArenaPanel() {
		/*
		 * get Arena Panel
		 */
		return this.arenasP;
	}

	public static ArrayList<Double> getRacersXval() {
		return xVal;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		//repaint(); // shouldn't update here! 
	}
	
	@Override
	public void update(Observable obs, Object arg) {
		/*
		 * update
		 */
		repaint();
		Racer r = (Racer)obs;
		r.getArena().update(r, null);
	}
	
	public void objects(String name, String color, Arena arena) {
		/*
		 * objects
		 */
		try {
			imageArray.add(ImageIO.read(new File("src/icons/" + name + color + ".png")));
			imageArrayButton.add(new Button());
			xVal.add(0.0);
		} catch (IOException e) {
			//e.printStackTrace();
		}
		repaint();
		setArena(arena);
	}


	
}
