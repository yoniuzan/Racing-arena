package state;

import game.racers.Racer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Disabled implements State {

	@Override
	public void doAction(Racer racer) {
		racer.setState(this);
	}

	@Override
	public String toString() {
		return "FAILED";
	}
}
