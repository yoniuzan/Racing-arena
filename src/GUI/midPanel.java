package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;

import factory.RaceBuilder;
import factory.RacingClassesFinder;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.arenas.land.LandArena;
import game.racers.Prototype;
import game.racers.Racer;
import utilities.EnumContainer;

import javax.swing.JToggleButton;

import Decorator.ColoredRacer;
import Decorator.WheeledRacer;

public class midPanel extends JPanel {

	private topPanel arenePanel;
	private JComboBox<String> racerCB;
	private mainFrame frame;
	private RaceBuilder builder = RaceBuilder.getInstance();
	private static Arena arena;
	public boolean racerFlag=false;
	private JComboBox<utilities.EnumContainer.Color> color;
	private leftPanel arenasPANEL;
	private JButton add;
	private JTextField numOfWheelsf, accf , maxspeedf, racernamef;
	
	public void updateToCarRaceArena(Arena a, ArrayList<Racer> cars){
		arena = arenePanel.getArena();
		racerCB.setSelectedIndex(3);
		int i = 1;
		for (Racer car : cars) 
		{
			numOfWheelsf.setText("" + car.getAttribut(WheeledRacer.ATTRIBUTENAME));
			accf.setText("" + car.getAcceleration());
			maxspeedf.setText("" + car.getMaxSpeed());
			racernamef.setText("" + car.getName());
			utilities.EnumContainer.Color colorE = (utilities.EnumContainer.Color)car.getAttribut(ColoredRacer.ATTRIBUTENAME);
			switch (colorE) {
				case Black: color.setSelectedIndex(0);	break;
				case Red: color.setSelectedIndex(1);	break;
				case Green: color.setSelectedIndex(2);	break;
				case Blue: color.setSelectedIndex(3);	break;
				case Yellow: color.setSelectedIndex(4);	break;
				default: color.setSelectedIndex(0);	break;
			}
			add.doClick();
			if(arena.getActiveRacers().size() == i){
				arena.getActiveRacers().get(i-1).setName("Car #" + i);
				i++;
			}
		}
	}
	
	
	public midPanel(topPanel arenaPanel) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			
		// type-racer row
		JLabel choserace = new JLabel("Choose Racer: ");
		add(choserace);
		this.racerCB = new JComboBox<>();
		racerCB.setMaximumSize(new Dimension(180, 30));
		for (String string : RacingClassesFinder.getInstance().getRacersNamesList()) racerCB.addItem(string);
		add(racerCB);
		
		// color row
		color = new JComboBox<>(utilities.EnumContainer.Color.values());
		color.setMaximumSize(new Dimension(180, 30));
		JLabel thecolor = new JLabel("Choose Color: ");
		add(thecolor);
		add(color);
		
		// name row
		racernamef = new JTextField();
		racernamef.setMaximumSize(new Dimension(180, 180));
		JLabel racename = new JLabel("Racer name: ");
		add(racename);
		add(racernamef);
		
		// speed row		
		maxspeedf = new JTextField();
		maxspeedf.setMaximumSize(new Dimension(180, 180));
		JLabel max = new JLabel("Max speed: ");
		add(max);
		add(maxspeedf);
		
		// acceleration row
		accf = new JTextField();
		accf.setMaximumSize(new Dimension(180, 180));
		JLabel acc = new JLabel("Acceleration: ");
		add(acc);
		add(accf);
		
		// wheel row
		numOfWheelsf = new JTextField();
		numOfWheelsf.setMaximumSize(new Dimension(180, 180));
		JLabel wheelLabel = new JLabel("num of wheels: ");
		add(wheelLabel);
		add(numOfWheelsf);
		
		// default setting - for debugging
		racernamef.setText("Moshes"); maxspeedf.setText("40"); accf.setText("10"); numOfWheelsf.setText("0");
		
		// button row
		add = new JButton("Add racer");
		add(add);
		add.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arena != null && arena.isHasStart())
					add.setEnabled(false);
				
				arena = arena.getArena();
				String name_field = racernamef.getText();
				String speed_field = maxspeedf.getText();
				String accceleration_field = accf.getText();
				String numOfWheels_field = numOfWheelsf.getText();
				
				if(name_field.equals("") == true) {
					JOptionPane.showMessageDialog(null, "Please fill the \'Name\' field");
					return;
				}
				double speed = 0;
				if(maxspeedf.getText().length() > 0)
					speed = Float.parseFloat(maxspeedf.getText());
				else {
					JOptionPane.showMessageDialog(null, "Please fill the \'Max Speed\' field");
					return;
				}
				double acceleration = 0;
				if(accf.getText().length() > 0)
					acceleration = Float.parseFloat(accf.getText());
				else {
					JOptionPane.showMessageDialog(null, "Please fill the \'Acceleration\' field");
					return;
				}
				try {
					if (name_field.equals("") == true || speed_field.equals("") == true || accceleration_field.equals("") == true) {
						JOptionPane.showMessageDialog(null, "Something went wrong");
					}
					if (acceleration < 0 || speed < 0) {
						JOptionPane.showMessageDialog(null, "You have entered negtaive number to a field.");
					}
				} catch (NumberFormatException er) {
				}			
				List<String> listrace = RacingClassesFinder.getInstance().getRacersList();
				String racerr = racerCB.getSelectedItem().toString();
				String obColor = (String) color.getSelectedItem().toString();
				for (String racer : listrace) {
					if (racer.contains(racerr)) {
						try {
							Racer r = Prototype.getRacer(racerr);
							r.setFullData(name_field, speed, acceleration,(EnumContainer.Color) color.getSelectedItem());
							// ColoredRacer
							if(color.getSelectedItem() != "Black"){
								EnumContainer.Color c = (EnumContainer.Color) color.getSelectedItem();
								ColoredRacer cr = new ColoredRacer(r, c); // insert color attribute to racer r
							}
							// WheeledRacer
							if(!numOfWheels_field.equals("")){
								try{
									int wheels = Integer.parseInt(numOfWheels_field);
									if(wheels > 0){
										WheeledRacer wr = new WheeledRacer(r, wheels); // insert wheel attribute to the racer r
									}
								}catch(Exception eint){}
							}
							arena.addRacer(r); // builder.buildRacer(racer, nameee, s, a,(EnumContainer.Color) color.getSelectedItem()));
							arena.initRace();
							arenasPANEL.objects(racerr, obColor, arena);
							r.addGuiObserver(arenasPANEL);
						} catch (RacerLimitException e1) {							
							JOptionPane.showMessageDialog(null, "The arena if FULL!");
						} 
						catch (RacerTypeException e3) {
							JOptionPane.showMessageDialog(null, "The racer dosen't match to the arena type");
						}
					}
				}
			}
		});
	}

	public midPanel getRacerPanel() {
		return this;
	}

	public void setRacerPanel(mainFrame Frame) {
		if (Frame != null) this.setFrame(Frame);
	}
	
	public boolean getFlag() {
		return this.racerFlag;
	}

	public void setArenasPANEL(leftPanel arena) {
		arenasPANEL = arena;
	}

	public static void setArena(Arena ARENA) {
		arena = ARENA;
	}

	public mainFrame getFrame() {
		return frame;
	}

	public void setFrame(mainFrame frame) {
		this.frame = frame;
	}
	
	public topPanel getArenePanel() {
		return arenePanel;
	}

	public void setArenePanel(topPanel arenePanel) {
		this.arenePanel = arenePanel;
	}
	

}