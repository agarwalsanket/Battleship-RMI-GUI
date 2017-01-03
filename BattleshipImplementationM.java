/* 
 * BattleshipImplementation.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * BattleshipImplementation class implementing the BattleshipInterface methods.
 * This class has all the logic of the game and is the class to be binded to the registry.
 *
 * @author Sanket Agarwal

 */
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class BattleshipImplementationM extends  UnicastRemoteObject implements BattleshipInterface {



	protected BattleshipImplementationM() throws RemoteException {
		super();
		// TODO Auto-generated constructor stub
	}


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	static int track = 0;
	private int player1identity = 0;
	private int player2identity = 0;
	static Player p1 = null;
	static Player p2 = null;
	String errMessage = "";
	static boolean oponents_chance = false;
	static int p1Counter = 0;
	static int p2Counter = 0;

	public int track() throws RemoteException {
		track  += 1;
		if(track == 1){
			player1identity = track;
			//System.out.println(track);
			return player1identity;
		}
		else if(track == 2){
			player2identity = track;
			//System.out.println(track);
			return player2identity;
		}
		else{
			System.out.println("Only two players can play!");
			return 999;
		}
	}


	public String errMessage() throws RemoteException {

		return errMessage;
	}


	public void printOcean(int identity){
		if(identity ==1){
			System.out.println("This is the ocean for the player "+p1.pName);
			for(int i = 0; i < 10; i++){                  // Printing the ocean
				for(int j = 0; j < 10; j++){
					System.out.print(p1.ocean[i][j]);
				}
				System.out.println();

			}
		}
		else{
			System.out.println("This is the ocean for the player "+p2.pName);
			for(int i = 0; i < 10; i++){                  // Printing the ocean
				for(int j = 0; j < 10; j++){
					System.out.print(p2.ocean[i][j]);
				}
				System.out.println();

			}
		}

	}


	public void initializePlayer(int identity, String name) throws RemoteException {
		//System.out.println("Inside initializePlayer");
		if(identity == player1identity){
			p1 = new Player(name);

			for(int i = 0; i < 10; i++){                  // Printing the ocean
				for(int j = 0; j < 10; j++){
					System.out.print(p1.ocean[i][j]);
				}
				System.out.println();

			}
		}
		else if(identity == player2identity){
			p2 = new Player(name);
			for(int i = 0; i < 10; i++){                  // Printing the ocean
				for(int j = 0; j < 10; j++){
					System.out.print(p2.ocean[i][j]);
				}
				System.out.println();

			}
		}
		else{
			System.out.println("Something went wrong while initializing players!");
		}
	}


	public boolean fleetArrangement(String shipCommon, int posRow, int posColumn, String direction, int identity)
			throws RemoteException {
		boolean isVacant = true;
		int shipSize  = 0;
		Player p= null;
		if(identity == player1identity){
			p = p1;
		}
		else{
			p = p2;
		}

		if(shipCommon.equals("carrier")){
			shipSize = 5;
		}
		else if(shipCommon.equals("battleship")){
			shipSize = 4;
		}
		else if(shipCommon.equals("cruiser")){
			shipSize = 3;
		}
		else if(shipCommon.equals("destroyer")){
			shipSize = 2;
		}
		else{
			System.out.println("Not a battleship"+shipCommon);
			errMessage = "Not a battleship"+shipCommon;
			return false;
		}

		if(direction.equals("v")){
			if(posRow < 0 || posRow > (9-shipSize)+1){
				//System.out.println("Enter a value more than 0 & less than or equal to"+((9-shipSize)+1)+" for row");
				errMessage = "For row, enter a value more than 0 & less than or equal to "+((9-shipSize)+1) ;
				isVacant = false;

			}
			else if(posColumn < 0 || posColumn > 9){
				//System.out.println("Enter a value more than 0 & less than or equal to 9 for column");
				errMessage ="Enter a value more than 0 & less than or equal to 9 for column";
				isVacant = false;
			}
			else
			{
				if(p.ocean[Math.abs(posRow-1)][posColumn] == "S" || p.ocean[(posRow + shipSize)>9?9:(posRow + shipSize)][posColumn] == "S"){
					isVacant = false;
					//System.out.println("There are adjacent ships on its top or bottom");
					errMessage ="There are adjacent ships on its top or bottom";
				} 
				else if(true){
					for(int i = posRow; i < posRow+shipSize; i++){
						if(p.ocean[i][Math.abs(posColumn-1)] == "S" || p.ocean[i][posColumn+1] == "S"){
							isVacant = false;
							//System.out.println("There are adjacent ships on its left or right");
							errMessage ="There are adjacent ships on its left or right";
							continue;

						}
						else if(p.ocean[i][posColumn] == "S"){
							isVacant = false;
							//System.out.println("You are overlapping a ship at position "+ i);
							errMessage ="You are overlapping a ship at position "+ i;
							continue;

						}
						/*else{
						isVacant = true;
					}*/
					}

				}

				if(isVacant == true){

					for(int i = posRow; i < posRow+shipSize; i++){
						p.ocean[i][posColumn] = "S";
					}

				}

			}
		}
		else if(direction.equals("h")){

			if(posRow < 0 || posRow > 9){
				//System.out.println("Enter a value more than 0 & less than or equal to 9 for row");
				errMessage = "Enter a value more than 0 & less than or equal to 9 for row";
				isVacant = false;

			}
			else if(posColumn < 0 || posColumn > (9-shipSize)+1){
				//System.out.println("Enter a value more than 0 & less than or equal to "+((9-shipSize)+1)+"  for column");
				errMessage ="Enter a value more than 0 & less than or equal to "+((9-shipSize)+1)+"  for column";
				isVacant = false;
			}
			else
			{
				if(p.ocean[posRow][Math.abs(posColumn-1)] == "S" || p.ocean[posRow][(posColumn + shipSize)>9?9:(posColumn + shipSize)] == "S"){
					isVacant = false;
					errMessage = "There are adjacent ships on its left or right";
					//System.out.println("There are adjacent ships on its left or right");
				} 
				else if(true){
					for(int i = posColumn; i < posColumn+shipSize; i++){
						if(p.ocean[Math.abs(posRow-1)][i] == "S" ||  (p.ocean[((posRow+1)>9?posRow:posRow+1)][i] == "S")){
							isVacant = false;
							errMessage = "There are adjacent ships on its top or bottom";
							//System.out.println("There are adjacent ships on its top or bottom");
							continue;

						}
						else if(p.ocean[posRow][i] == "S"){
							errMessage ="You are overlapping a ship at position "+ i;
							//System.out.println("You are overlapping a ship at position "+ i);
							isVacant = false;
							continue;

						}
						/*else{
						isVacant = isVacant;
					}
						 */

					}
				}

				if(isVacant == true){


					for(int i = posColumn; i < posColumn+shipSize; i++){
						p.ocean[posRow][i] = "S";
					}

				}
			}

		}
		return isVacant;

	}


	public int PlayGAme(int pIdentity,int posRow, int posCol) throws RemoteException {

		if(pIdentity == 1){
			oponents_chance = true;
			if(p1Counter >= 999){
				System.out.println("Sorry "+p1.pName+",You lost! \n"+p2.pName+" already won!");
				return 999;
			}
			if(p2.ocean[posRow][posCol].equals("S")){
				p2.ocean[posRow][posCol] = "D";  // D for hit done
				//p1.track[rowTemp][rowTemp] = "H";  //H for keeping track of hits
				System.out.println("Congrats "+p1.pName+"! This was a hit at point:"+ "("+posRow+","+posCol+")"); 
				errMessage = "Congrats "+p1.pName+"! This was a hit at point:"+ "("+posRow+","+posCol+")";
				//hitOrMiss = true;
				p1Counter++;
				if(p1Counter > 13){
					System.out.println("Congrats "+p1.pName+"! You Won!");
					p2Counter = 999;
				}

				//System.out.println(p1Counter);
			}
			else if(p2.ocean[posRow][posCol].equals("D")){
				System.out.println("You have already hit this point"); 
				errMessage = "You have already hit this point";
			}
			/*else if(p2.ocean[rowTemp][colTemp].equals("X") || p2.ocean[rowTemp][colTemp].equals("0"))            {

			System.out.println("You missed it"+p2.ocean[rowTemp][rowTemp]); 
			p2.ocean[rowTemp][rowTemp] = "X";
		}*/
			else 
			{
				System.out.println("You missed it"); 
				errMessage = "You missed it";
				//p1.track[rowTemp][rowTemp]  = "X"; //X for miss both for tracking board and ocean
				p2.ocean[posRow][posCol] = "X";
			}
			return p1Counter;
		}
		else{
			oponents_chance = false;
			if(p2Counter >= 999){
				System.out.println("Sorry "+p2.pName+",You lost! \n"+p1.pName+" already won!");
				return 999;
			}
			if(p1.ocean[posRow][posCol].equals("S")){
				p1.ocean[posRow][posCol] = "D";
				//p2.track[rowTemp][colTemp] = "H"; 
				System.out.println("Congrats "+p2.pName+"! This was a hit at point:"+ "("+posRow+","+posCol+")"); 
				errMessage= "Congrats "+p2.pName+"! This was a hit at point:"+ "("+posRow+","+posCol+")";
				p2Counter++;
				if(p2Counter > 13){
					System.out.println("Congrats "+p2.pName+"! You Won!");
					p1Counter = 999;
				}
				//System.out.println(p2Counter);
			}
			else if(p1.ocean[posRow][posCol].equals("D")){
				System.out.println("You have already hit this point"); 
				errMessage = "You have already hit this point";
			}
			/*else if(p1.ocean[rowTemp][colTemp].equals("0") || p1.ocean[rowTemp][colTemp].equals("X")){
System.out.println("You missed it"+p1.ocean[rowTemp][rowTemp]); 
			p1.ocean[rowTemp][rowTemp] = "X";
		}*/
			else{
				System.out.println("You missed it"); 
				errMessage = "You missed it";
				//p2.track[rowTemp][rowTemp]  = "X"; //X for miss both for tracking board and ocean
				p1.ocean[posRow][posCol] = "X";
			}
			return p2Counter;
		}


	}


	public boolean hitOrMiss() throws RemoteException {

		return false;
	}


	public boolean oponentsChance(){
		return oponents_chance;
	}

}
