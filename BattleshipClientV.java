/* 
 * BattleshipClientV.java 
 * 
 * Version: 
 *     $Id$
 * 
 * Revisions: Initial Version
 *    
 */
/**
 * BattleshipClientV class handles the client side processing.
 *
 * @author Sanket Agarwal

 */
import java.rmi.Naming;
import java.rmi.RemoteException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.stage.Stage;


public class BattleshipClientV extends Application {
	private TextField name,row1,col1,or1,row2,col2,or2,row3,col3,or3,row4,col4,or4,play_r,play_c;
	private Button enter,enter1, carrier,battleship,cruiser,destroyer,detach;
	int playerIdentity = 0;
	BattleshipInterface obj   = null;
	private String temp_name = "";
	boolean isFillingDone = false;
	boolean oponents_chance = false;
	int p1Counter = 0;
	int p2Counter = 0;
	int row = 0;
	int col = 0;
	int counter = 0;
	String message = "";
	TextField msg = new TextField();
	TextField wait_msg = new TextField();
	TextField wait_msg1 = new TextField();

	public BattleshipClientV(){
		try{
			obj = (BattleshipInterface)Naming.lookup("//localhost:12345/BattleshipImplementationobj");
		}
		catch(Exception e){
			System.out.println("Exception in lookup"+e.getMessage());
			e.printStackTrace();
		}
		try{
			playerIdentity=obj.track();
			if(playerIdentity == 999){
				System.out.println("Only two players allowed to play!");
				System.exit(1);
			}
		}
		catch(Exception e){
			System.out.println("Exception in track"+e.getMessage());
			e.printStackTrace();
		}
	}




	public void start(Stage stage) throws Exception {
		BorderPane border = new BorderPane();

		msg.setText("All the messages will be displayed here!");
		msg.setEditable(false);
		wait_msg.setText("Wait related messages displayed here!");
		wait_msg.setEditable(false);
		wait_msg1.setText("Wait related messages displayed here!");
		wait_msg.setEditable(false);
		detach = new Button("Detach from Server");
		detach.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				enter.setDisable(true);
				enter1.setDisable(true);
				carrier.setDisable(true);
				battleship.setDisable(true);
				cruiser.setDisable(true);
				destroyer.setDisable(true);
				msg.setText("Detached from server!Close the window to close the game!");

			}
		});
		VBox msg_box = new VBox();
		msg_box.getChildren().addAll(msg,wait_msg,wait_msg1,detach);
		msg_box.setSpacing(5);
		msg_box.setPadding(new Insets(0, 20, 10, 20)); 
		border.setTop(msg_box);

		name = new TextField();
		name.setPromptText( "Enter your name here" );
		name.setPrefColumnCount( 20 );
		enter = new Button("Enter");

		enter.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {

			public void handle(ActionEvent event) {
				temp_name = name.getText();
				if(temp_name.equals("")){
					msg.setText("You have not entered your name!");
				}
				else{
					try {
						System.out.println("temp_na: "+temp_name);
						obj.initializePlayer(playerIdentity, temp_name);
						msg.setText("Player entered : "+temp_name);
						enter.setDisable(true);
					} catch (RemoteException e) {
						System.out.println("Exception in player initialization "+e.getMessage());
						e.printStackTrace();
					}
				}

			}
		});

		FlowPane entryForm = new FlowPane(Orientation.HORIZONTAL, 30, 30);
		//entryForm.setAlignment(Pos.BASELINE_LEFT);
		entryForm.getChildren().addAll(new Label( "Name"), name, enter );
		//	FlowPane entryForm = new FlowPane( new Label( "Name"), name, enter );

		entryForm.setPadding(new Insets(10, 10, 10, 10));
		entryForm.setVgap(5);
		entryForm.setHgap(5);
		border.setLeft(entryForm);
		border.setRight(this.fleetArrangementPane());
		border.setBottom(this.PlayGame());
		Scene scene = new Scene(border);
		if(playerIdentity == 1){
			stage = new Stage();
			stage.setTitle( "Player 1" );
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();

		}
		else if(playerIdentity == 2){
			stage = new Stage();
			stage.setTitle( "Player 2" );
			stage.setScene(scene);
			stage.sizeToScene();
			stage.show();
		}
		else{
			System.out.println("Only two players allowed to play!");
		}
	}


	private Pane fleetArrangementPane(){

		VBox right = new VBox();
		Label alabel = new Label( "Enter the locations for your fleets: " );
		row1 = new TextField();
		col1 = new TextField();
		or1 = new TextField();
		row1.setPrefColumnCount( 2 );
		col1.setPrefColumnCount( 2 );
		or1.setPrefColumnCount( 2 );
		carrier = new Button("Carr");
		carrier.setMaxWidth(Double.MAX_VALUE);
		carrier.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(temp_name.equals("")){
					msg.setText("You have not initialized the oceans yet!");
				}
				else{
					int temp_row = Integer.parseInt(row1.getText());
					int temp_col = Integer.parseInt(col1.getText());
					String temp_or = or1.getText();
					try{
						isFillingDone = obj.fleetArrangement("carrier",temp_row,temp_col,temp_or,playerIdentity);
					}
					catch(Exception e){
						System.out.println("Exception in fleeArrangement call "+e.getMessage());
						e.printStackTrace();
					}
					if(isFillingDone == false){
						try {
							message = obj.errMessage();
							row1.clear();
							col1.clear();
							or1.clear();
							msg.setText(message);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					else{
						msg.setText("Carrier succesfully placed!");
						carrier.setDisable(true);
						try {
							obj.printOcean(playerIdentity);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		});
		FlowPane fp1 = new FlowPane(new Label( "Row " ),row1,new Label( "Col " ),col1,new Label( "Orien." ),or1,carrier);
		fp1.setHgap(10);
		row2 = new TextField();
		col2 = new TextField();
		or2 = new TextField();
		row2.setPrefColumnCount( 2 );
		col2.setPrefColumnCount( 2 );
		or2.setPrefColumnCount( 2 );
		battleship = new Button("Batt");
		battleship.setMaxWidth(Double.MAX_VALUE);
		battleship.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(temp_name.equals("")){
					msg.setText("You have not initialized the oceans yet!");
				}
				else{
					int temp_row = Integer.parseInt(row2.getText());
					int temp_col = Integer.parseInt(col2.getText());
					String temp_or = or2.getText();
					try{
						isFillingDone = obj.fleetArrangement("battleship",temp_row,temp_col,temp_or,playerIdentity);
					}
					catch(Exception e){
						System.out.println("Exception in fleeArrangement call "+e.getMessage());
						e.printStackTrace();
					}
					if(isFillingDone == false){
						try {
							message = obj.errMessage();
							row2.clear();
							col2.clear();
							or2.clear();
							msg.setText(message);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					else{
						msg.setText("Battleship succesfully placed!");
						battleship.setDisable(true);
					}

				}

			}

		});
		FlowPane fp2 = new FlowPane(new Label( "Row " ),row2,new Label( "Col " ),col2,new Label( "Orien." ),or2,battleship);
		fp2.setHgap(10);
		row3 = new TextField();
		col3 = new TextField();
		or3 = new TextField();
		row3.setPrefColumnCount( 2 );
		col3.setPrefColumnCount( 2 );
		or3.setPrefColumnCount( 2 );
		cruiser = new Button("Crui");
		cruiser.setMaxWidth(Double.MAX_VALUE);
		cruiser.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(temp_name.equals("")){
					msg.setText("You have not initialized the oceans yet!");
				}
				else{
					int temp_row = Integer.parseInt(row3.getText());
					int temp_col = Integer.parseInt(col3.getText());
					String temp_or = or3.getText();
					try{
						isFillingDone = obj.fleetArrangement("cruiser",temp_row,temp_col,temp_or,playerIdentity);
					}
					catch(Exception e){
						System.out.println("Exception in fleeArrangement call "+e.getMessage());
						e.printStackTrace();
					}
					if(isFillingDone == false){
						try {
							message = obj.errMessage();
							row3.clear();
							col3.clear();
							or3.clear();
							msg.setText(message);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					else{
						msg.setText("cruiser succesfully placed!");
						cruiser.setDisable(true);
					}

				}

			}

		});
		FlowPane fp3 = new FlowPane(new Label( "Row " ),row3,new Label( "Col " ),col3,new Label( "Orien." ),or3,cruiser);
		fp3.setHgap(10);
		row4 = new TextField();
		col4 = new TextField();
		or4 = new TextField();
		row4.setPrefColumnCount( 2 );
		col4.setPrefColumnCount( 2 );
		or4.setPrefColumnCount( 2 );
		destroyer = new Button("Dest");
		destroyer.setMaxWidth(Double.MAX_VALUE);
		destroyer.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				if(temp_name.equals("")){
					msg.setText("You have not initialized the oceans yet!");
				}
				else{
					int temp_row = Integer.parseInt(row4.getText());
					int temp_col = Integer.parseInt(col4.getText());
					String temp_or = or4.getText();
					try{
						isFillingDone = obj.fleetArrangement("destroyer",temp_row,temp_col,temp_or,playerIdentity);
					}
					catch(Exception e){
						System.out.println("Exception in fleeArrangement call "+e.getMessage());
						e.printStackTrace();
					}
					if(isFillingDone == false){
						try {
							message = obj.errMessage();
							row4.clear();
							col4.clear();
							or4.clear();
							msg.setText(message);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}
					else{
						msg.setText("destroyer succesfully placed!");
						destroyer.setDisable(true);
						try {
							obj.printOcean(playerIdentity);
						} catch (RemoteException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				}

			}

		});
		FlowPane fp4 = new FlowPane(new Label( "Row " ),row4,new Label( "Col " ),col4,new Label( "Orien." ),or4,destroyer);
		fp4.setHgap(10);
		right.getChildren().addAll(alabel,fp1,fp2,fp3,fp4);
		right.setSpacing(10);
		right.setPadding(new Insets(0, 20, 10, 20)); 
		return right;

	}

	private Pane PlayGame(){
		VBox right = new VBox();
		Label alabel = new Label( "<-----------Let's play the game!-----------> " );
		Label alabel1 = new Label( "Enter the location for your opponent's ship:  " );
		alabel.setTextFill(Color.RED);
		alabel.setFont(Font.font( "Palatino", FontPosture.ITALIC, 20 ) );
		alabel1.setTextFill(Color.BLUE);
		alabel1.setFont(Font.font( "Palatino", FontPosture.ITALIC, 20 ) );
		play_r = new TextField();
		play_c = new TextField();
		play_r.setPrefColumnCount( 2 );
		play_c.setPrefColumnCount( 2 );
		enter1 = new Button("Hit");
		enter1.addEventHandler(ActionEvent.ANY, new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				if(temp_name.equals("")){
					msg.setText("You have not initialized the oceans yet!");
				}
				else{
					int rowTemp = Integer.parseInt(play_r.getText());
					int colTemp = Integer.parseInt(play_c.getText());
					try {
						counter = obj.PlayGAme(playerIdentity,rowTemp,colTemp);
						if(counter>= 999){
							message = temp_name;
							System.out.println("Sorry "+message+", You Lost! Better luck next time!");						
							msg.setText("Sorry "+message+", You Lost! Better luck next time!");

							//System.exit(1);
							enter1.setDisable(true);
						}
						else if(counter > 13 && counter < 999){
							message = temp_name;
							System.out.println("Congrats "+message+"! you won" );
							msg.setText("Congrats "+message+"! you won" );
							enter1.setDisable(true);
						}
						else{
							System.out.println(obj.errMessage());
							message = obj.errMessage();
							msg.setText(message);
							oponents_chance = obj.oponentsChance();

							//----------------------------------------------------------------------------Alternate
							if(playerIdentity == 1 && oponents_chance == true){
								System.out.println("This is your oponent's chance! Please wait!");
								wait_msg.setText("If this is your oponent's turn! You will have to  wait!");
								//enter1.setDisable(true);

								while(oponents_chance){

									try{
										oponents_chance = obj.oponentsChance();
									}
									catch(Exception e){
										System.out.println("Exception in oponents_chance "+e.getMessage());
										e.printStackTrace();
									}

								}
								wait_msg1.setText("If this is your turn!You can enter !");
							}
							else if(playerIdentity == 2 && oponents_chance == false){

								System.out.println("This is your oponent's chance! Please wait!");
								wait_msg.setText("If this is your oponent's turn! You will have to  wait!");
								//enter1.setDisable(true);

								while(!oponents_chance){

									try{
										oponents_chance = obj.oponentsChance();
									}
									catch(Exception e){
										System.out.println("Exception in oponents_chance "+e.getMessage());
										e.printStackTrace();
									}

								}
								wait_msg1.setText("If this is your turn!You can enter !");


							}


							//-----------------------------------------------------------------------------Alternate    



						}

					} catch (RemoteException e) {

						e.printStackTrace();
					}


				}
			}
		});
		FlowPane fp = new FlowPane(new Label( "Row " ),play_r,new Label( "Col " ),play_c,enter1);
		fp.setHgap(10);
		fp.setAlignment(Pos.CENTER);
		right.getChildren().addAll(alabel,alabel1,fp);
		right.setSpacing(5);
		right.setPadding(new Insets(0, 20, 10, 20)); 
		right.setAlignment(Pos.CENTER);

		return right;

	}
	public static void main(String[] args){
		//BattleshipClientV BCV = new BattleshipClientV();
		Application.launch();
	}
}
