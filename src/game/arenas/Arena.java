package game.arenas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Racer;
import state.*;
import utilities.Point;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public abstract class Arena implements Observer {
	private final static int MIN_Y_GAP = 50;
	private ArrayList<Racer> activeRacers;
	private ArrayList<Racer> compleatedRacers;
	private ArrayList<Racer> brokenRacers;
	private ArrayList<Racer> allRacers;
	private boolean hasStart = false;
	private ArrayList<Racer> disabledRacers;
	private double length;
	private final int MAX_RACERS;
	private final double FRICTION;
	private ClockStop stop;
	
	public ArrayList<Racer> getAllRacers() {
		return allRacers;
	}
	public boolean isHasStart() {
		return hasStart;
	}
	
	/**
	 * 
	 * @param length
	 *            the x value for the finish line
	 * @param maxRacers
	 *            Maximum number of racers
	 * @param friction
	 *            Coefficient of friction
	 * 
	 */
	protected Arena(double length, int maxRacers, double friction) {
		this.length = length;
		this.MAX_RACERS = maxRacers;
		this.FRICTION = friction;
		this.activeRacers = new ArrayList<Racer>();
		this.compleatedRacers = new ArrayList<Racer>();
		this.brokenRacers = new ArrayList<Racer>();
		this.disabledRacers = new ArrayList<Racer>();
		this.allRacers = new ArrayList<Racer>();
	}

	public void startRace() {
		if(activeRacers.size() == 0) {
			System.out.println("There aren't data of racer for the game");
			return;
		}
		stop = new ClockStop();
		stop.start();
		hasStart = true;
		ExecutorService game = Executors.newFixedThreadPool(this.activeRacers.size());
		for (Racer racer : this.activeRacers) 
		{
			racer.setFinish(new Point(this.length, 0));
			racer.setCurrentLocation(new Point(0, 0));
			game.execute(racer);
		}
	}

	

	public void addRacer(Racer newRacer) throws RacerLimitException, RacerTypeException {
		if (this.activeRacers.size() == this.MAX_RACERS) 
		{
			throw new RacerLimitException(this.MAX_RACERS, newRacer.getSerialNumber());
		}
		this.activeRacers.add(newRacer);
		this.allRacers.add(newRacer);
		newRacer.setSerialNumber(activeRacers.size());
	}

	public ArrayList<Racer> getActiveRacers() {
		return activeRacers;
	}

	public ArrayList<Racer> getCompleatedRacers() {
		return compleatedRacers;
	}

	public boolean hasActiveRacers() {
		return this.activeRacers.size() > 0;
	}

	public void initRace() {
		int y = 0;
		for (Racer racer : this.activeRacers) {
			Point s = new Point(0, y);
			Point f = new Point(this.length, y);
			racer.initRace(this, s, f);
			y += Arena.MIN_Y_GAP;
		}
	}

	public void showResults() {
		for (Racer r : this.compleatedRacers) {
			String s = "#" + this.compleatedRacers.indexOf(r) + " -> ";
			s += r.describeRacer();
			System.out.println(s);
		}
	}

	public Arena getArena() {
		return this;
	}

	public static int getNewMinYGap() {
		return MIN_Y_GAP;
	}

	public int getMaxRacer() {
		return this.MAX_RACERS;
	}

	public void setCompletedRacers(Racer racer) {
		this.compleatedRacers.add(racer);
		if(activeRacers.size() == 0) stop.stop(true);
	}
	
	@Override
	public synchronized void update(Observable o, Object arg) {
		
		Racer r = (Racer) o;
		synchronized(r)
		{
			State state = r.getState();
			if(state instanceof Completed) {
				this.compleatedRacers.add(r);
				this.activeRacers.remove(r);
				r.setRating(this.compleatedRacers.size());
				r.addTimeBroken(stop.getTime() , "Completed");
				sortRacer(compleatedRacers);
				//System.out.println("R" + r.getSerialNumber() + "\thas Completed");
			}
			else if(state instanceof Broken  && !this.brokenRacers.contains(r) && this.activeRacers.contains(r)) {
				this.brokenRacers.add(r);
				this.activeRacers.remove(r);
				r.addTimeBroken(stop.getTime(), "Broken");
				//System.out.println("R" + r.getSerialNumber() + "\thas brokend");
			}
			else if(state instanceof Active && this.brokenRacers.contains(r) && !this.activeRacers.contains(r)) {
				this.brokenRacers.remove(r);
				this.activeRacers.add(r);
				sortRacer(activeRacers);
				r.setRating(this.activeRacers.indexOf(r)+1);
				//System.out.println("R" + r.getSerialNumber() + "\thas active");
			}
			else if(state instanceof Disabled) {
				this.disabledRacers.add(r);
				this.activeRacers.remove(r);
				r.addTimeBroken(stop.getTime() , "Disabled");
				r.setRating(999);
				//System.out.println("R" + r.getSerialNumber() + "\thas disabled");
			}
			if(infoTable != null) infoTable.update(r, this);
		}
	}

	public void setActiveRacers(Racer rac) {
		activeRacers.remove(rac);
	}

	public double getFriction() {
		return this.FRICTION;
	}
	
	public synchronized boolean isFinished(){
		boolean res = hasStart == true && activeRacers.size() == 0 && brokenRacers.size() == 0;
		return res;
	}

	private Observer infoTable = null;
	public void setTableObserver(Observer obs) {
		infoTable = obs;
	}
	
	public void updateResultTableDate(){
		sortRacer(compleatedRacers);
	}
	private synchronized void sortRacer(ArrayList<Racer> racers){
		if(racers == activeRacers)			Collections.sort(racers, Racer.copmareDistance); 
		else if(racers == compleatedRacers) Collections.sort(racers, Racer.copmareDirug);
		if(racers == compleatedRacers && isFinished()){
			for (Racer racer : compleatedRacers) {
				System.out.println("Dirug " + racer.getRating()+ ") Racer " + racer.getSerialNumber() + " - finished at time: " + racer.getFinishedTime());
			}
		}
		Collections.sort(allRacers, Racer.copmareDirug);
	}
}
