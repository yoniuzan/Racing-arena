package game.racers;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Wheeled {

	private int numOfWheels;

	public Wheeled(int numOfWheels) {
		this.numOfWheels = numOfWheels;
	}

	public String describeSpecific() {
		return ", Number of Wheels: " + this.getNumOfWheels();
	}

	public int getNumOfWheels() {
		return numOfWheels;
	}
}
