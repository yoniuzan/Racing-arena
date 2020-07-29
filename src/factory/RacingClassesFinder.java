package factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

import game.arenas.Arena;
import game.racers.Racer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class RacingClassesFinder {
	private static String GAME_PACKAGE = "game";
	private static String GAME_PACKAGE_DIR = "src/game";
	private static RacingClassesFinder instance;
	
	public static RacingClassesFinder getInstance() {
		/*
		 * constractor
		 */
		if (instance == null) {
			instance = new RacingClassesFinder();
		}
		return instance;
	}

	private List<Class<?>> classList;
	private List<Class<?>> racersList;
	private List<Class<?>> arenasList;

	private RacingClassesFinder() {
		try {
			this.classList = this.loadClasses(new File(GAME_PACKAGE_DIR), GAME_PACKAGE);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		this.arenasList = loadArenas();
		this.racersList = loadRacers();
	}

	public List<String> getArenasList() {
		/*
		 * get Arenas List
		 */
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.arenasList) {
			list.add(c.getName());
		}
		return list;
	}

	public List<String> getArenasNamesList() {
		/*
		 * get Arenas Names List
		 */
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.arenasList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	public List<String> getRacersList() {
		/*
		 * get Racers List
		 */
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.racersList) {
			list.add(c.getName());
		}
		return list;
	}

	public List<String> getRacersNamesList() {
		/*
		 * return list
		 */
		/*
		 *get Racers Names List 
		 */
		List<String> list = new ArrayList<>();
		for (Class<?> c : this.racersList) {
			String s = c.getName();
			list.add(s.substring(s.lastIndexOf('.') + 1));
		}
		return list;
	}

	private List<Class<?>> loadArenas() {
		/*
		 * return list
		 */
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Arena.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {
				list.add(cls);
			}
		}
		return list;
	}

	private List<Class<?>> loadClasses(File directory, String packageName)
			throws ClassNotFoundException, FileNotFoundException {
		List<Class<?>> list = new ArrayList<Class<?>>();

		if (!directory.exists()) {
			throw new FileNotFoundException();
		}
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				list.addAll(loadClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".java")) {
				list.add(Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 5)));
			}
		}
		return list;
	}

	private List<Class<?>> loadRacers() {
		/*
		 * return list
		 */
		List<Class<?>> list = new ArrayList<>();
		for (Class<?> cls : classList) {
			if (Racer.class.isAssignableFrom(cls) && (Modifier.isAbstract(cls.getModifiers()) == false)) {
				list.add(cls);
			}
		}
		return list;
	}

}
