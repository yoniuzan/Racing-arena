package Decorator;

import game.racers.IRacer;
import utilities.EnumContainer.Color;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class ColoredRacer extends RacerDecorator {
	public static final String ATTRIBUTENAME = "color";
	private Color color;
	
	public ColoredRacer(IRacer decoratedRacer, Color color) {
		/*
		 * constractor
		 */
		super(decoratedRacer);
		this.color = null;
		addAttribute(ATTRIBUTENAME, color);
	}
	
	@Override
	public void addAttribute(String type, Object attr) {
		/*
		 * add Attribute
		 */
		this.color = (Color)attr;
		decoratedRacer.addAttribute(type, this.color);
	}
}
