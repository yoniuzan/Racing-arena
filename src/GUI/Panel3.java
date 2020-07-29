package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import factory.RacingClassesFinder;
import game.arenas.Arena;
import game.arenas.exceptions.RacerLimitException;
import game.arenas.exceptions.RacerTypeException;
import game.racers.Prototype;
import game.racers.Racer;
import utilities.EnumContainer;

import Decorator.ColoredRacer;
import Decorator.WheeledRacer;
/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Panel3 extends JPanel {

	
	private static final long serialVersionUID = 1L;
	private Panel2 areneP;
	private JComboBox<String> race;
	private Frame frame;
	private static Arena arena;
	public boolean tempo=false;
	private JComboBox<utilities.EnumContainer.Color> color;
	private Panel5 arensP;
	private JButton more;
	private JTextField numOfWheels, confer , maxSpeed, nameRace;
	
	public void updateToCarRaceArena(Arena a, ArrayList<Racer> cars){
		arena = areneP.getArena();
		race.setSelectedIndex(3);
		int i = 1;
		for (Racer car : cars) 
		{
			numOfWheels.setText("" + car.getAttribut(WheeledRacer.ATTRIBUTENAME));
			confer.setText("" + car.getAcceleration());
			maxSpeed.setText("" + car.getMaxSpeed());
			nameRace.setText("" + car.getName());
			utilities.EnumContainer.Color colorE = (utilities.EnumContainer.Color)car.getAttribut(ColoredRacer.ATTRIBUTENAME);
			switch (colorE) {
				case Black: color.setSelectedIndex(0);	break;
				case Red: color.setSelectedIndex(1);	break;
				case Green: color.setSelectedIndex(2);	break;
				case Blue: color.setSelectedIndex(3);	break;
				case Yellow: color.setSelectedIndex(4);	break;
				default: color.setSelectedIndex(0);	break;
			}
			more.doClick();
			if(arena.getActiveRacers().size() == i){
				arena.getActiveRacers().get(i-1).setName("Car #" + i);
				i++;
			}
		}
	}
	
	
	public Panel3(Panel2 arenaPanel) {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
			
		// type-racer row
		JLabel choserace = new JLabel("Choose Racer: ");
		add(choserace);
		this.race = new JComboBox<>();
		race.setMaximumSize(new Dimension(180, 30));
		for (String string : RacingClassesFinder.getInstance().getRacersNamesList()) race.addItem(string);
		add(race);
		
		// color row
		color = new JComboBox<>(utilities.EnumContainer.Color.values());
		color.setMaximumSize(new Dimension(180, 30));
		JLabel thecolor = new JLabel("Choose Color: ");
		add(thecolor);
		add(color);
		
		// name row
		nameRace = new JTextField();
		nameRace.setMaximumSize(new Dimension(180, 180));
		JLabel racename = new JLabel("Racer name: ");
		add(racename);
		add(nameRace);
		
		// speed row		
		maxSpeed = new JTextField();
		maxSpeed.setMaximumSize(new Dimension(180, 180));
		JLabel max = new JLabel("Max speed: ");
		add(max);
		add(maxSpeed);
		
		// acceleration row
		confer = new JTextField();
		confer.setMaximumSize(new Dimension(180, 180));
		JLabel acc = new JLabel("Acceleration: ");
		add(acc);
		add(confer);
		
		// wheel row
		numOfWheels = new JTextField();
		numOfWheels.setMaximumSize(new Dimension(180, 180));
		JLabel wheelLabel = new JLabel("num of wheels: ");
		add(wheelLabel);
		add(numOfWheels);
		
		// default setting - for debugging
		nameRace.setText("Yoni&Liron "); maxSpeed.setText("50"); confer.setText("20"); numOfWheels.setText("4");
		
		// button row
		more = new JButton("Add racer");
		add(more);
		more.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(arena != null && arena.isHasStart())
					more.setEnabled(false);
				
				arena = arena.getArena();
				String name_field = nameRace.getText();
				String speed_field = maxSpeed.getText();
				String accceleration_field = confer.getText();
				String numOfWheels_field = numOfWheels.getText();
				
				if(name_field.equals("") == true) {
					JOptionPane.showMessageDialog(null, "Please fill the \'Name\' field");
					return;
				}
				double speed = 0;
				if(maxSpeed.getText().length() > 0)
					speed = Float.parseFloat(maxSpeed.getText());
				else {
					JOptionPane.showMessageDialog(null, "Please fill the \'Max Speed\' field");
					return;
				}
				double acceleration = 0;
				if(confer.getText().length() > 0)
					acceleration = Float.parseFloat(confer.getText());
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
				String racerr = race.getSelectedItem().toString();
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
							arensP.objects(racerr, obColor, arena);
							r.addGuiObserver(arensP);
						} catch (RacerLimitException e1) {							
							JOptionPane.showMessageDialog(null, "The arena is full!");
						} 
						catch (RacerTypeException e3) {
							JOptionPane.showMessageDialog(null, "The racer dosen't match to the arena type");
						}
					}
				}
			}
		});
	}

	public Panel3 getRacerPanel() {
		/*
		 *get Racer Panel 
		 */
		return this;
	}

	public void setRacerPanel(Frame Frame) {
		/*
		 * set Racer Panel
		 */
		if (Frame != null) this.setFrame(Frame);
	}
	
	public boolean getFlag() {
		/*
		 * get Flag
		 */
		return this.tempo;
	}

	public void setArenasPANEL(Panel5 arena) {
		/*
		 * set Arenas PANEL
		 */
		arensP = arena;
	}

	public static void setArena(Arena ARENA) {
		/*
		 * set Arena
		 */
		arena = ARENA;
	}

	public Frame getFrame() {
		/*
		 * get Frame
		 */
		return frame;
	}

	public void setFrame(Frame frame) {
		/*
		 * set Frame
		 */
		this.frame = frame;
	}
	
	public Panel2 getArenePanel() {
		/*
		 * get Arene Panel
		 */
		return areneP;
	}

	public void setArenePanel(Panel2 arenePanel) {
		/*
		 *set Arene Panel 
		 */
		this.areneP = arenePanel;
	}
	

}