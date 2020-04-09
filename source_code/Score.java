/**
 *
 * To change this generated comment edit the template variable "typecomment":
 * Window>Preferences>Java>Templates.
 * To enable and disable the creation of type comments go to
 * Window>Preferences>Java>Code Generation.
 */

public class Score {

    private String nick;
    private String date;
    private String score;

    public Score( String nick, String date, String score ) {
		this.nick=nick;
		this.date=date;
		this.score=score;
    }

    public String getNickName() {
        return nick;             //getter function to get Nickname
    }

	public String getDate() {  // //getter function to get Date
		return date;
	}
	
	public String getScore() { //getter function to get Score
		return score;
	}

	public String toString() {
		return nick + "\t" + date + "\t" + score;
	}

}
