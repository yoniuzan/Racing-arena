package Decorator;

import game.racers.IRacer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class WheeledRacer extends RacerDecorator {
	public static final String ATTRIBUTENAME = "numOfWheels";
	private int numOfWheels;
	public WheeledRacer(IRacer decoratedRacer, int numOfWheels) {
		/*
		 * constractor
		 */
		super(decoratedRacer);
		this.numOfWheels = 0;
		addAttribute(ATTRIBUTENAME, numOfWheels);
	}
	@Override
	public void addAttribute(String type, Object attr) {
		/*
		 * add Attribute
		 */
		this.numOfWheels += ((Integer)attr).intValue();
		decoratedRacer.addAttribute(type, this.numOfWheels);
	}
}
