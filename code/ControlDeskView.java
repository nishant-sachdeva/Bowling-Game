/* ControlDeskView.java
 *
 *  Version:
 *			$Id$
 * 
 *  Revisions:
 * 		$Log$
 * 
 */

/**
 * Class for representation of the control desk
 *
 */

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

import java.util.*;

public class ControlDeskView implements ActionListener, ControlDeskObserver {

	private JButton addParty, finished, assign;  // these look like the buttons

	private JFrame win; // short for window, this seems to be the place where everything is gonna be displayed

	private JList partyList; // this is gonna be the list of the member of the party, at least that's what it looks like
	
	/** The maximum  number of members in a party : we have changed this to a 6 in the prev file */
	private int maxMembers;
	
	private ControlDesk controlDesk; // this is the control desk that we are gonna decorate and display now

	/**
	 * Displays a GUI representation of the ControlDesk : I have  a feeling, this is where I am gonna implement the multiplayer thing after all

	 *
	 */

	public ControlDeskView(ControlDesk controlDesk, int maxMembers) {

		this.controlDesk = controlDesk; // so here we assign the controlDesk

		this.maxMembers = maxMembers; // here go the members, I do not know how are they getting this number tho, 
		
		int numLanes = controlDesk.getNumLanes(); // I remember seeing this function, It returns the number of lanes here. Altho, we could have simply given it that number as it is


		win = new JFrame("Control Desk"); // The new frame that is gonna be decorated

		win.getContentPane().setLayout(new BorderLayout()); //

		((JPanel) win.getContentPane()).setOpaque(false);

		JPanel colPanel = new JPanel();

		colPanel.setLayout(new BorderLayout());

		// Controls Panel
		JPanel controlsPanel = new JPanel();
		controlsPanel.setLayout(new GridLayout(3, 1));
		controlsPanel.setBorder(new TitledBorder("Controls"));

		addParty = new JButton("Start a new Tourney");
		JPanel addPartyPanel = new JPanel();
		addPartyPanel.setLayout(new FlowLayout());
		addParty.addActionListener(this);
		addPartyPanel.add(addParty);
		controlsPanel.add(addPartyPanel);

		assign = new JButton("Assign Lanes");
		JPanel assignPanel = new JPanel();
		assignPanel.setLayout(new FlowLayout());
		assign.addActionListener(this);
		assignPanel.add(assign);
//		controlsPanel.add(assignPanel);

		finished = new JButton("Ight, Ima head out");
		JPanel finishedPanel = new JPanel();
		finishedPanel.setLayout(new FlowLayout());
		finished.addActionListener(this);
		finishedPanel.add(finished);
		controlsPanel.add(finishedPanel);

		// Lane Status Panel
		JPanel laneStatusPanel = new JPanel();
		laneStatusPanel.setLayout(new GridLayout(numLanes, 1));
		laneStatusPanel.setBorder(new TitledBorder("Lane Status"));

		HashSet lanes = controlDesk.getLanes();
		Iterator it = lanes.iterator();
		
		int laneCount = 0;  // initialising the lane count. 
		
		while (it.hasNext()) {
			// this looks like the loop that is gonna have to be parallelised so that I get a multiplayer game 
			Lane curLane = (Lane) it.next();

			LaneStatusView laneStat = new LaneStatusView(curLane,(laneCount+1));

			curLane.subscribe(laneStat);

			((Pinsetter)curLane.getPinsetter()).subscribe(laneStat);

			JPanel lanePanel = laneStat.showLane();

			System.out.println("Yo Mami, me is here. Let's study what the code has been doing"); 

			lanePanel.setBorder(new TitledBorder("Lane Number " + ++laneCount ));

			laneStatusPanel.add(lanePanel);
		}

		// Party Queue Panel
		JPanel partyPanel = new JPanel();
		partyPanel.setLayout(new FlowLayout());
		partyPanel.setBorder(new TitledBorder("Party Queue"));

		Vector empty = new Vector();
		empty.add("(Empty)");

		partyList = new JList(empty);
		partyList.setFixedCellWidth(120);
		partyList.setVisibleRowCount(10);
		JScrollPane partyPane = new JScrollPane(partyList);
		partyPane.setVerticalScrollBarPolicy(
			JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		partyPanel.add(partyPane);
		//		partyPanel.add(partyList);

		// Clean up main panel
		colPanel.add(controlsPanel, "East");
		colPanel.add(laneStatusPanel, "Center");
		colPanel.add(partyPanel, "West");

		win.getContentPane().add("Center", colPanel);

		win.pack();

		/* Close program when this window closes */
		win.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		// Center Window on Screen
		Dimension screenSize = (Toolkit.getDefaultToolkit()).getScreenSize();
		win.setLocation(
			((screenSize.width) / 2) - ((win.getSize().width) / 2),
			((screenSize.height) / 2) - ((win.getSize().height) / 2));
		win.show();

	}

	/**
	 * Handler for actionEvents
	 *
	 * @param e	the ActionEvent that triggered the handler
	 *
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(addParty)) {
			AddPartyView addPartyWin = new AddPartyView(this, maxMembers);
		}
		if (e.getSource().equals(assign)) {
			controlDesk.assignLane();
		}
		if (e.getSource().equals(finished)) {
			win.hide();
			System.exit(0);
		}
	}

	/**
	 * Receive a new party from andPartyView.
	 *
	 * @param addPartyView	the AddPartyView that is providing a new party
	 *
	 */

	public void updateAddParty(AddPartyView addPartyView) {
		controlDesk.addPartyQueue(addPartyView.getParty());
	}

	/**
	 * Receive a broadcast from a ControlDesk
	 *
	 * @param ce	the ControlDeskEvent that triggered the handler
	 *
	 */

	public void receiveControlDeskEvent(ControlDeskEvent ce) {
		partyList.setListData(((Vector) ce.getPartyQueue()));
	}
}
