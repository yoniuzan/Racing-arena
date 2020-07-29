package game.racers.land;

import game.racers.Racer;
import game.racers.Wheeled;
import utilities.EnumContainer;
import utilities.EnumContainer.BicycleType;
import utilities.EnumContainer.Color;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Bicycle extends Racer implements ILandRacer {

	private static final String CLASS_NAME = "Bicycle";
	private static final String DEFUALT_NAME = CLASS_NAME + " #" + lastSerialNumber;

	private static final int DEFAULT_WHEELS = 2;
	private static final double DEFAULT_MAX_SPEED = 270;
	private static final double DEFAULT_ACCELERATION = 10;
	private static final Color DEFAULT_color = Color.Green;

	private EnumContainer.BicycleType type;
	private Wheeled wheeled;

	public Bicycle() {
		this(DEFUALT_NAME, DEFAULT_MAX_SPEED, DEFAULT_ACCELERATION, DEFAULT_color);
	}

	public Bicycle(String name, double maxSpeed, double acceleration, utilities.EnumContainer.Color color) {
		super(name, maxSpeed, acceleration, color);
		this.wheeled = new Wheeled(DEFAULT_WHEELS);
		this.type = BicycleType.MOUNTAIN;
	}

	@Override
	public String className() {
		return CLASS_NAME;
	}

	@Override
	public String describeSpecific() {
		String s = "";
		s += this.wheeled.describeSpecific();
		s += ", Bicycle Type: " + this.type;
		return s;
	}


}
