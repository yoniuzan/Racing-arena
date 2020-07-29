package state;

import game.racers.Racer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Broken implements State {

	@Override
	public void doAction(Racer racer) {
		/*
		 *doAction 
		 */
		racer.setState(this);
	}

	@Override
	public String toString() {
		/*
		 *to String 
		 */
		return "BROKEN";
	}
}
