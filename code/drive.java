import java.util.Vector;
import java.io.*;

public class drive {

	public static void main(String[] args) {

		int numLanes = 3;  // this seems to specify the number of possible lanes 
		int maxPatronsPerParty=5;  // this seesms to be the place where we specifly the M-plicity ( Max number of players that can take part in the multiplayer )

		Alley a = new Alley( numLanes );
		ControlDesk controlDesk = a.getControlDesk();

		ControlDeskView cdv = new ControlDeskView( controlDesk, maxPatronsPerParty);
		controlDesk.subscribe( cdv );

	}
}