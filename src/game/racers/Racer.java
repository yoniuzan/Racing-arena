
package game.racers;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Hashtable;
import java.util.Observable;
import java.util.Observer;

import Decorator.ColoredRacer;
import Decorator.WheeledRacer;
import GUI.Panel5;
import game.arenas.Arena;
import state.*;
import utilities.EnumContainer;
import utilities.EnumContainer.Color;
import utilities.Fate;
import utilities.Mishap;
import utilities.Point;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public abstract class Racer extends Observable implements Runnable, Cloneable, IRacer {
	

	protected static int lastSerialNumber = 1;
	
	private int serialNumber; 
	private String name;
	private Point currentLocation;
	private Point finish;
	private Arena arena;
	private double maxSpeed;
	private double acceleration;
	private double currentSpeed;
	@SuppressWarnings("unused")
	private double failureProbability; 
	private final double FAILURE_DEFAULT = 0.35;
	private EnumContainer.Color color; 
	private Hashtable<String, Object> attributes;
    private State state;
	private static final State activeState = new Active();
	private static final State brokenState = new Broken();
	private static final State disabledState = new Disabled();
	private static final State completedState = new Completed();
	
	private boolean clicked = false;
	public boolean isClicked() {
		/*
		 * return true or false
		 */
		return clicked;
	}
	public void setClick(boolean clicked) {
		/*
		 * set click
		 */
		this.clicked = clicked;
	}
	public void setName(String name) {
		/*
		 * set name
		 */
		this.name = name;
	}
	public double getAcceleration() {
		/*
		 * get Acceleration
		 */
		return acceleration;
	}
	
	public static Comparator<Racer> copmareDistance = new Comparator<Racer>() {
		/*
		 * Comparator
		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
		 */
			public int compare(Racer r1, Racer r2) {

			   int rollno1 = (int)r1.getCurLocation().getX();
			   int rollno2 = (int)r2.getCurLocation().getX();

			   /*For ascending order*/
			   //return rollno1-rollno2;

			   /*For descending order*/
			   return rollno2-rollno1;
   }};
   public static Comparator<Racer> copmareDirug = new Comparator<Racer>() {
	   /*
	    * Comparator
	    * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	    */
		public int compare(Racer r1, Racer r2) {

		   int rollno1 = r1.getRating();
		   int rollno2 = r2.getRating();

		   /*For ascending order*/
		   return rollno1-rollno2;

		   /*For descending order*/
		   //return rollno2-rollno1;
   }};

	public State getState() {
		/*
		 * get state
		 */
		return state;
	}
	public void setSerialNumber(int serialNumber) {
		/*
		 * set SerialNumber
		 */
		this.serialNumber = serialNumber;
	}
	public void setState(State state) {
		/*
		 * set State
		 */
		this.state = state;
		arena.update(this, null);
	}
	
	private int rating;
	public int getRating() {
		/*
		 *get Rating 
		 */
		return rating;
	}
	public void setRating(int dirug) {
		this.rating = dirug;
	}
	
	public ArrayList<String> tb = new ArrayList<String>();

	public String getFinishedTime() {
		if(tb.size() > 0)
			return tb.get(tb.size()-1);
		return "";
	}
	
	public ArrayList<String> getTimeBrokenArray() {
		return tb;
	}
	public void addTimeBroken(String tb, String why) {
		this.tb.add(tb);
		System.out.println("#" + this.getSerialNumber() + ": has " + why + " at: " + tb);
	}

	private Mishap mishap;

	/**
	 * @param name
	 * @param maxSpeed
	 * @param acceleration
	 * @param color
	 */
	public Racer(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		/*
		 * racer
		 */
		this.serialNumber = Racer.lastSerialNumber++;
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.failureProbability = FAILURE_DEFAULT;
		attributes = new Hashtable<String, Object>();
		attributes.put(WheeledRacer.ATTRIBUTENAME, 0);
		attributes.put(ColoredRacer.ATTRIBUTENAME, Color.Black);
	}

	public abstract String className();

	public String describeRacer() {
		String s = "";
		s += "name: " + this.name + ", ";
		s += "SerialNumber: " + this.serialNumber + ", ";
		s += "maxSpeed: " + this.maxSpeed + ", ";
		s += "acceleration: " + this.acceleration + ", ";
		s += "color: " + this.color + ", ";
		s = s.substring(0, s.length() - 2);
		// returns a string representation of the racer, including: general attributes
		// (color name, number) and specific ones (numberOfWheels, etc.)
		s += this.describeSpecific();
		return s;
	}


	public abstract String describeSpecific();

	public int getSerialNumber() {
		/*
		 * get SerialNumber
		 */
		return serialNumber;
	}

	private boolean hasMishap() {
		/*
		 * chack hasMishap
		 */
		if (this.mishap != null && this.mishap.getTurnsToFix() == 0)
			this.mishap = null;
		return this.mishap != null;
	
	}

	public void initRace(Arena arena, Point start, Point finish) {
		/*
		 * init Race
		 */
		regObserver(arena);
		this.currentLocation = new Point(start);
		this.finish = new Point(finish);
	}

	public void introduce() {
		/*
		 * systerm
		 */
		// Prints a line, obtained from describeRacer(), with its type
		System.out.println("[" + this.className() + "] " + this.describeRacer());
	}

	public Point move(double friction) {
		/*
		 * print move
		 */
		double reductionFactor = 1;
		if (!(this.hasMishap()) && Fate.breakDown(this.FAILURE_DEFAULT)) {
			this.mishap = Fate.generateMishap();
			//System.out.println(this.name + " Has a new mishap! (" + this.mishap + ")");
		}
		if (this.hasMishap()) {
			if (this.mishap.isFixable() == false) {
				this.setChanged();
				disabledState.doAction(this);
				this.notifyObservers(EnumContainer.RacerEvent.DISABLED);
				Thread.currentThread().interrupt();
			} else {
				this.setChanged();
				brokenState.doAction(this);
				this.notifyObservers(EnumContainer.RacerEvent.BROKENDOWN);
			}
			reductionFactor = mishap.getReductionFactor();
			this.mishap.nextTurn();
			return this.currentLocation;
		}
		else {
			this.setChanged();
			activeState.doAction(this);
			this.notifyObservers(EnumContainer.RacerEvent.REPAIRED);
		}

		if (this.currentSpeed < this.maxSpeed) {
			this.currentSpeed += this.acceleration * friction;
		}
		if (this.currentSpeed > this.maxSpeed) {
			this.currentSpeed = this.maxSpeed;
		}
		if(this.currentLocation.getX()>=this.finish.getX()) {
			this.currentLocation.setX(this.finish.getX());
			
		}
		double newX = ((currentLocation.getX() + (this.currentSpeed)) * reductionFactor);
		GUI.Panel5.setX(getSerialNumber(), newX);
		currentLocation.setX(newX);
		Gui.update(this, null);
		return this.currentLocation;
		
	}

	public Racer getRacer() {
		/*
		 *get Racer 
		 */
		return this;
	}

	public double getMaxSpeed() {
		/*
		 * get Max Speed
		 */
		return this.maxSpeed;
	}

	public double getCurSpeed() {
		/*
		 * get CurSpeed
		 */
		return this.currentSpeed;
	}

	public Point getCurLocation() {
		/*
		 * get CurLocation
		 */
		return this.currentLocation;
	}

	public double getFailure() {
		/*
		 *get Failure 
		 */
		return this.failureProbability;
	}

	public void setFinish(Point f) {
		/*
		 * set Finish
		 */
		finish = new Point(f);
	}

	public Point getFinish() {
		/*
		 *get Finish 
		 */

		return this.finish;
	}

	public void setCurrentLocation(Point p) {
		/*
		 * set Current Location
		 */

		this.currentLocation = new Point(p);
	}

	public Point getCurrentLocation() {
		/*
		 *get Current Location 
		 */

		return this.currentLocation;
	}

	@Override
	
	public void run() {
		while (this.currentLocation.getX() <= finish.getX() )
		{
			move(arena.getFriction());
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {
				Thread.currentThread().interrupt(); // restore interrupted status
			}
			if(this.hasMishap() && this.mishap.isFixable() == false) break;
		}
		completedState.doAction(this);
		this.notifyObservers(utilities.EnumContainer.RacerEvent.FINISHED);
	}	
	public void regObserver(Observer observeR) {
		arena=(Arena) observeR;
	}
	
	public String getName() {
		/*
		 * get Name
		 */
		return name;
	}
	
	public Object clone() {
		Object clone=null;
		try {
			clone=super.clone();
		}catch (CloneNotSupportedException e) {
			e.printStackTrace();
		} return clone;
	}
	public void setFullData(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		this.name = name;
		this.maxSpeed = maxSpeed;
		this.acceleration = acceleration;
		this.color = color;
		this.failureProbability = FAILURE_DEFAULT;
	}
	
	
	private Observer Gui;
	public void addGuiObserver(Observer arenasPANEL) {
		Gui = arenasPANEL;
	}
	public Arena getArena() {
		/*
		 * return arena
		 */
		return arena;
	}
	
	@Override
	public void addAttribute(String type, Object attr){
		/*
		 * add Attribute
		 */
		attributes.put(type, attr);
	}
	@Override
	public Object getAttribut(String type){
		/*
		 * get Attribut
		 */
		return  attributes.get(type);
	}
}







