/* 
 * Player.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * Player class creates the profile for the two players.
 *
 * @author Sanket Agarwal

 */
public class Player{

	String pName;
	int sumSize  = 0;
	int tempSize     = 0;

	String[][] ocean    = new String[10][10];




	public Player(){

	}
	public Player(String pName){



		this.pName = pName;

		for(int i = 0; i < 10; i++){
			for(int j = 0; j < 10; j++){
				ocean[i][j] = "0";

			}

		}


	}
}
