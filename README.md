# Battleship-RMI-GUI
The Battleship Game on networks using RMI, having GUI .
These project consists of 4 classes and 1 interface files which are as follows:
1>BattleshipInterface.java--> Interface-->BattleshipInterface interface having all the methods required by the client.
2>BattleshipImplementationM.java--> BattleshipImplementation class implements the BattleshipInterface methods.
This class has all the logic of the game and is the class to be binded to the registry.
3>BattleshipServer.java-->BattleshipServer class is the server class.
This class binds the implemented class to the registry.
4>BattleshipClientV.java--> BattleshipClientV class handles the client side processing and uses JavaFx for the GUI.
5>Player.java--> Player class creates the profile for the two players.

