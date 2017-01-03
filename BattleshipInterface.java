/* 
 * BattleshipInterface.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * BattleshipInterface interface having all the methods required by the client.
 *
 * @author Sanket Agarwal

 */

public interface BattleshipInterface extends java.rmi.Remote{

	public int track() throws java.rmi.RemoteException;
	public String errMessage() throws java.rmi.RemoteException;
	public void printOcean(int identity) throws java.rmi.RemoteException;

	public void initializePlayer(int identity,String name) throws java.rmi.RemoteException;

	public boolean fleetArrangement(String shipCommon,
			int posRow,int posColumn, String direction,int identity)  throws java.rmi.RemoteException;

	public int  PlayGAme(int identity,int row, int col) throws java.rmi.RemoteException;

	public boolean hitOrMiss() throws java.rmi.RemoteException;
	public boolean oponentsChance() throws java.rmi.RemoteException;
}
