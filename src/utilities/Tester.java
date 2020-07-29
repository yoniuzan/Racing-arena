package utilities;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
import game.racers.air.Airplane;
import game.racers.air.Helicopter;
import game.racers.land.Bicycle;
import game.racers.land.Car;
import game.racers.land.Horse;
import game.racers.sea.RowBoat;
import game.racers.sea.SpeedBoat;

public class Tester {

	public static void main(String[] args) {
		new Car().introduce();
		new Horse().introduce();
		new Bicycle().introduce();
		new Helicopter().introduce();
		new Airplane().introduce();
		new SpeedBoat().introduce();
		new RowBoat().introduce();

	}

}
