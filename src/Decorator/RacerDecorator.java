package Decorator;

import game.racers.IRacer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public abstract class RacerDecorator implements IRacer{
	protected IRacer decoratedRacer;
	
	public RacerDecorator(IRacer decoratedRacer){
		this.decoratedRacer = decoratedRacer;
	}
	
	@Override
	public Object getAttribut(String type) {
		/*
		 *get Attribut 
		 */
		return this.decoratedRacer.getAttribut(type);
	}
	
}
