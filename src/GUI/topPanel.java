package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import factory.RaceBuilder;
import factory.RacingClassesFinder;
import game.arenas.Arena;
import game.arenas.FactoryArena;
import game.arenas.land.LandArena;
import game.racers.land.Car;

public class topPanel extends JPanel {

	private mainFrame frame;
	private RaceBuilder builder = RaceBuilder.getInstance();
	private Arena arena;
	private boolean flagArena = false;
	private JComboBox<String> topBox;
	private leftPanel arenasPANEL;
	private static final String[]  namesArena = new String[] {"Aerial","Land","Naval"};
	private boolean stopAddRacers = false;
	private JButton buildArena ;
	private JTextField afield, mfield;
	
	public void updateToCarRaceArena(Arena a){
		arena = a;
		this.topBox.setSelectedIndex(1);
		afield.setText("1000");
		mfield.setText("" + LandArena.DEFAULT_MAX_RACERS);
		buildArena.doClick();
	}
	
	public topPanel() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JLabel choarena = new JLabel("Choose Arena: ");
		add(choarena);
		JLabel lenarena = new JLabel("Arena Length: ");
		JLabel maximumracers = new JLabel("Max Racers Number: ");
		afield = new JTextField();
		afield.setText("1000");
		afield.setMaximumSize(new Dimension(180, 180));
		mfield = new JTextField();
		mfield.setText("8");
		mfield.setMaximumSize(new Dimension(180, 180));
		buildArena = new JButton("Build Arena");
		this.topBox = new JComboBox<>();
		topBox.setMaximumSize(new Dimension(180, 30));
		for (String string : RacingClassesFinder.getInstance().getArenasNamesList()) {
			topBox.addItem(string);
		}

		add(topBox);
		add(lenarena);
		add(afield);
		add(maximumracers);
		add(mfield);
		add(buildArena);

		buildArena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String arenalen = afield.getText();
					String numracers = mfield.getText();
					if (arenalen.equals("") == true || numracers.equals("") == true) {
						JOptionPane.showMessageDialog(null, "Something went wrong");
					}
					int maximumnumberofracers = Integer.parseInt(mfield.getText());
					int lengthofrace = Integer.parseInt(afield.getText());
					if (lengthofrace >= 100 && lengthofrace <= 3000 && maximumnumberofracers >= 1
							&& maximumnumberofracers <= 20 && arenalen.equals("") == false	&& numracers.equals("") == false)
					{
						if (maximumnumberofracers > 14) frame.setSize(lengthofrace + mainFrame.PANELS_SIZE, 1000);
						else  frame.setSize(lengthofrace + mainFrame.PANELS_SIZE, 700); 
						
						List<String> nameofArenas = RacingClassesFinder.getInstance().getArenasList();
						String choosen = topBox.getSelectedItem().toString();

						for (String ARENA : nameofArenas) 
						{
							if (ARENA.contains(choosen)) {
								arena = new FactoryArena().getArena(choosen); //builder.buildArena(ARENA, lengthofrace, maximumnumberofracers);
								midPanel.setArena(arena);
								arenasPANEL.bg(choosen);
								flagArena = true;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Invalid input values!");
					}
				} catch (NumberFormatException er) {
				}
			}
		});
	}



	public void setFlagArena(boolean s) {
		this.flagArena = s;
	}

	public void setArenasPANEL(leftPanel arena) {
		arenasPANEL = arena;
	}

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena ARENA) {
		arena = ARENA;
	}

	public void setstopAddRacers(boolean flag) {
		stopAddRacers = flag;
	}

	public boolean getstopAddRacers() {
		return stopAddRacers;
	}

	public boolean getFlag() {
		return this.flagArena;
	}
	public void setArenaPANELSize(mainFrame Frame) {
		if (Frame != null)
			this.frame = Frame;
	}

	public boolean getFlagArena() {
		return this.flagArena;
	}
}
