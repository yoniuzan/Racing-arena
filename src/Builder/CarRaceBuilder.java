package Builder;

import game.arenas.Arena;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.arenas.land.LandArena;
import game.racers.Prototype;
import game.racers.Racer;

import java.util.ArrayList;

public class CarRaceBuilder {
	private ArrayList<Racer> cars;
	private Arena land;
	public CarRaceBuilder(int N){
		cars = new ArrayList<Racer>(N);
		for (int i = 0; i < N; i++) {
			Racer r = Prototype.getRacer("Car");
			r.setSerialNumber(i++);
			cars.add(r);
		}
		
		land = new LandArena();
		for (Racer car : cars) {
			try {
				land.addRacer(car);
			} catch (RacerLimitException | RacerTypeException e) {}
		}
		
		land.initRace();
	}
	
	public ArrayList<Racer> getCars(){
		/*
		 * get Cars
		 */
		return cars;
	}
	public Arena getArena(){
		/*
		 * get Arena
		 */
		return land;
	}
	
	public void startRace(){
		land.startRace();
	}
}
