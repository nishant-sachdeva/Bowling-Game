import java.util.Vector;
import java.io.*;

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;
		int maxPatronsPerParty=6; 

		// making the number of players as 6 is the least that we need to do I guess

		ControlDesk controlDesk = new ControlDesk(numLanes);
		// we need the control desk, hence I am getting one. Noone needs different bowling alleys in a game for a single bowling alley hence I have removed the alley line 

		controlDesk.subscribe( new ControlDeskView( controlDesk, maxPatronsPerParty) );
		// Before we launch the game, we need sort of view of how the thing is gonna look like , hence the controlDeskview invocation and finally passing onto the subscribe function
		

	}
}