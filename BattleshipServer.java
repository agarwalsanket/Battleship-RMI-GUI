/* 
 * BattleshipServer.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * BattleshipServer class is the server class.
 * This class binds the implemented class to the registry.
 *
 * @author Sanket Agarwal

 */
import java.rmi.Naming;

public class BattleshipServer {
	public static void main(String[] args){
		try{
			BattleshipInterface obj = new BattleshipImplementationM();
			Naming.rebind("//localhost:12345/BattleshipImplementationobj", obj);
			System.out.println("BattleshipImplementationM bound in registry");
		}
		catch(Exception e){
			System.out.println("Exception while binding object :"+ e.getMessage());
			e.printStackTrace();
		}
	}
}
