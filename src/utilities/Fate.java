package utilities;

import java.util.Random;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Fate {

	private static Random rand = new Random();

	public static boolean breakDown(double failureProbability) {
		return rand.nextFloat()<=failureProbability;
	}

	public static boolean generateFixable() {
		return rand.nextInt(100) < 98;
	}

	public static Mishap generateMishap() {
		return new Mishap(generateFixable(), generateTurns(), generateReduction());
	}

	private static float generateReduction() {
		return rand.nextFloat();
	}

	private static int generateTurns() {
		return rand.nextInt(5) + 1;
	}

	public static void setSeed(int seed) {
		rand.setSeed(seed);
	}

}
