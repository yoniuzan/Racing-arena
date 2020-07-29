package game.racers;

import java.util.Hashtable;

import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.*;
import game.racers.sea.*;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Prototype  {
	private static Hashtable <String,Racer> RacerCovering=new Hashtable <String,Racer>();
	
	public static Racer getRacer(String racerType) {
		/*
		 * return racer
		 */
		Racer p = RacerCovering.get(racerType);
		return (Racer) p.clone();
	}
	
	public static void load() {
		Racer r = null;
		
		r = new Airplane();
		r.setSerialNumber(-99);
		RacerCovering.put("Airplane", r);
		
		r = new Helicopter();
		r.setSerialNumber(-99);
		RacerCovering.put("Helicopter", r);
		
		r = new Bicycle();
		r.setSerialNumber(-99);
		RacerCovering.put("Bicycle", r);
		
		r = new Car();
		r.setSerialNumber(-99);
		RacerCovering.put("Car", r);
		
		r = new Horse();
		r.setSerialNumber(-99);
		RacerCovering.put("Horse", r);
		
		r = new RowBoat();
		r.setSerialNumber(-99);
		RacerCovering.put("RowBoat", r);
		
		r = new SpeedBoat();
		r.setSerialNumber(-99);
		RacerCovering.put("SpeedBoat", r);

	}
	
}
