package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

/*
 * 
 * yonatan uzan 307865345
 * liron moyal 208909614
 * 
 * 
 */
public class Panel2 extends JPanel {


	private static final long serialVersionUID = 1L;
	private Frame frame;
	private Arena arena;
	private boolean flag1 = false;
	private JComboBox<String> topBox;
	private Panel5 arenasPANEL;
	private boolean stopRacers = false;
	private JButton buildArena ;
	private JTextField arenaField;
	private JTextField panel2field;
	
	public void UpdateCar(Arena c){
		arena = c;
		this.topBox.setSelectedIndex(1);
		arenaField.setText("1000");
		panel2field.setText("" + LandArena.DEFAULT_MAX_RACERS);
		buildArena.doClick();
	}
	
	public Panel2() {

		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));
		JLabel choarena = new JLabel("Choose Arena: ");
		add(choarena);
		JLabel lenarena = new JLabel("Arena Length: ");
		JLabel maximumracers = new JLabel("Max Racers Number: ");
		arenaField = new JTextField();
		arenaField.setText("1000");
		arenaField.setMaximumSize(new Dimension(180, 180));
		panel2field = new JTextField();
		panel2field.setText("8");
		panel2field.setMaximumSize(new Dimension(180, 180));
		buildArena = new JButton("Build Arena");
		this.topBox = new JComboBox<>();
		topBox.setMaximumSize(new Dimension(180, 30));
		for (String string : RacingClassesFinder.getInstance().getArenasNamesList()) {
			topBox.addItem(string);
		}

		add(topBox);
		add(lenarena);
		add(arenaField);
		add(maximumracers);
		add(panel2field);
		add(buildArena);

		buildArena.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					String arenalen = arenaField.getText();
					String numracers = panel2field.getText();
					if (arenalen.equals("") == true || numracers.equals("") == true) {
						JOptionPane.showMessageDialog(null, "Error");
					}
					int maximumnumberofracers = Integer.parseInt(panel2field.getText());
					int lengthofrace = Integer.parseInt(arenaField.getText());
					if (lengthofrace >= 100 && lengthofrace <= 3000 && maximumnumberofracers >= 1
							&& maximumnumberofracers <= 20 && arenalen.equals("") == false	&& numracers.equals("") == false)
					{
						if (maximumnumberofracers > 14) frame.setSize(lengthofrace + frame.PS, 1000);
						else  frame.setSize(lengthofrace + frame.PS, 700); 
						
						List<String> nameofArenas = RacingClassesFinder.getInstance().getArenasList();
						String choosen = topBox.getSelectedItem().toString();

						for (String ARENA : nameofArenas) 
						{
							if (ARENA.contains(choosen)) {
								arena = new FactoryArena().getArena(choosen); //builder.buildArena(ARENA, lengthofrace, maximumnumberofracers);
								Panel3.setArena(arena);
								arenasPANEL.bg(choosen);
								flag1 = true;
							}
						}
					} else {
						JOptionPane.showMessageDialog(null, "Invalid input values");
					}
				} catch (NumberFormatException er) {
				}
			}
		});
	}



	public void setFlagArena(boolean s) {
		/*
		 * set Flag Arena
		 */
		this.flag1 = s;
	}

	public void setArenasPANEL(Panel5 arena) {
		/*
		 * set Arenas PANEL
		 */
		arenasPANEL = arena;
	}

	public Arena getArena() {
		/*
		 * get Arena
		 */
		return arena;
	}

	public void setArena(Arena ARENA) {
		/*
		 * set Arena
		 */
		arena = ARENA;
	}

	public void setstopAddRacers(boolean flag) {
		/*
		 * set stop Add Racers
		 */
		stopRacers = flag;
	}

	public boolean getstopAddRacers() {
		/*
		 * get stop Add Racers
		 */
		return stopRacers;
	}

	public boolean getFlag() {
		/*
		 * get Flag
		 */
		return this.flag1;
	}
	public void setArenaPANELSize(Frame frame) {
		/*
		 * set Arena PANEL Size
		 */
		if (frame != null)
			this.frame = frame;
	}

	public boolean getFlagArena() {
		/*
		 *get Flag Arena 
		 */
		return this.flag1;
	}
}
