package game.arenas.exceptions;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
@SuppressWarnings("serial")
public class RacerLimitException extends Exception {

	/**
	 * @param ""
	 *            + max_racers
	 */
	public RacerLimitException(int max_racers, int racerNumber) {
		/*
		 * constractor
		 */
		super("Arena is full! (" + max_racers + " active racers exist). racer #" + racerNumber + " was not added");
	}

}
