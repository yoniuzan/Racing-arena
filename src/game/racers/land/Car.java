package game.racers.land;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer.Color;
import utilities.EnumContainer.Engine;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Car extends Racer implements ILandRacer {

	private static final String CLASS_NAME = "Car";

	private static final int DEFAULT_WHEELS = 4;
	private static final double DEFAULT_MAX_SPEED = 400;
	private static final double DEFAULT_ACCELERATION = 20;
	private static final Color DEFAULT_color = Color.Red;

	private Engine engine;
	private Wheeled wheeled;

	public Car() {
		this(CLASS_NAME + " #" + lastSerialNumber, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
	}

	public Car(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		this.engine = Engine.MOUNTAIN;
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
	}

	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public String describeSpecific() {
		String s = "";
		s += this.wheeled.describeSpecific();
		s += ", Engine Type: " + this.engine;

		return s;
	}


}
