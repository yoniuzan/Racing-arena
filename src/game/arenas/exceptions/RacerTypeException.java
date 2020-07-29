package game.arenas.exceptions;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
@SuppressWarnings("serial")
public class RacerTypeException extends Exception {
	/*
	 * constractor
	 */
	public RacerTypeException(String racerType, String arenaType) {
		super(arenaType.compareTo("Land") == 0?
				("Invalid Racer of type \"" + racerType + "\" for " + arenaType + " arena. (Or there isn\'t wheels in this racer)") :
					("Invalid Racer of type \"" + racerType + "\" for " + arenaType + " arena."));
	}

}
