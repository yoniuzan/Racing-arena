package game.arenas;

import game.arenas.air.AerialArena;
import game.arenas.land.LandArena;
import game.arenas.sea.NavalArena;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class FactoryArena {
	public Arena getArena(String arenaType) {
		/*
		 * return arena
		 */
		Arena arena = null;
		if(arenaType.compareTo("AerialArena")==0) {
			arena =  new AerialArena();
		}
		if(arenaType.compareTo("LandArena")==0) {
			arena =  new LandArena();
		}
		if(arenaType.compareTo("NavalArena")==0) {
			arena =  new NavalArena();
		}
		System.out.println(arena);
		return arena;
	}
}
