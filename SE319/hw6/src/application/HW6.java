package application;
	
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class HW6 extends Application {
	
	//create all images for each button
	private Stage mainStage = new Stage();
	private Image xFile = new Image("file:/Users/jakegude/Desktop/Projects/Eclipse/319hw6/x.jpg");
	private Image oFile = new Image("file:/Users/jakegude/Desktop/Projects/Eclipse/319hw6/o.jpg");
	private ImageView x1 = new ImageView(xFile);
	private ImageView o1 = new ImageView(oFile);
	private ImageView x2 = new ImageView(xFile);
	private ImageView o2 = new ImageView(oFile);
	private ImageView x3 = new ImageView(xFile);
	private ImageView o3 = new ImageView(oFile);
	private ImageView x4 = new ImageView(xFile);
	private ImageView o4 = new ImageView(oFile);
	private ImageView x5 = new ImageView(xFile);
	private ImageView o5 = new ImageView(oFile);
	private ImageView x6 = new ImageView(xFile);
	private ImageView o6 = new ImageView(oFile);
	private ImageView x7 = new ImageView(xFile);
	private ImageView o7 = new ImageView(oFile);
	private ImageView x8 = new ImageView(xFile);
	private ImageView o8 = new ImageView(oFile);
	private ImageView x9 = new ImageView(xFile);
	private ImageView o9 = new ImageView(oFile);
	//create labels and variables to know whos turn/winner
	private int whosTurn = 0;
	private Label turns = new Label();
	private boolean [] xTRUE = new boolean [9];
	private boolean [] oTRUE = new boolean [9];
	private BooleanProperty gameOver = new SimpleBooleanProperty(false);

	@Override
	public void start(Stage primaryStage) {		
		mainStage = primaryStage;
		//create all buttons/border and grid panes
		BorderPane border = new BorderPane();
		GridPane board = new GridPane();
		Button btn1 = new Button();
		Button btn2 = new Button();
		Button btn3 = new Button();
		Button btn4 = new Button();
		Button btn5 = new Button();
		Button btn6 = new Button();
		Button btn7 = new Button();
		Button btn8 = new Button();
		Button btn9 = new Button();
		Button restart = new Button("Restart");		
			
		//add each button to border pane
		board.add(btn1, 0, 0);
		board.add(btn2, 1, 0);
		board.add(btn3, 2, 0);
		board.add(btn4, 0, 1);
		board.add(btn5, 1, 1);
		board.add(btn6, 2, 1);
		board.add(btn7, 0, 2);
		board.add(btn8, 1, 2);
		board.add(btn9, 2, 2);
		board.add(restart, 3, 3);
		
		//set alignment and scale of border
		board.setAlignment(Pos.CENTER);
		board.setScaleX(2);
		board.setScaleY(2);
		turns.setText("X's Turn");
		border.setBottom(turns);
		restart.setVisible(false);
		initialize();
		
		ChangeListener<Boolean> listen = new ChangeListener<Boolean>() {
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if (oldValue == false) restart.setVisible(true);
			}
		};
		gameOver.addListener(listen);
		
		btn1.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[0] && !oTRUE[0])) {
            		xTRUE[0] = true;
            		x1.setFitHeight(25);
            		x1.setFitWidth(25);
            		btn1.setPadding(new Insets(0.0));
            		btn1.setGraphic(x1);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[0] && !oTRUE[0])) {
            		oTRUE[0] = true;
            		o1.setFitHeight(25);
            		o1.setFitWidth(25);
            		btn1.setPadding(new Insets(0.0));
            		btn1.setGraphic(o1);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn2.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get()&& (!xTRUE[1] && !oTRUE[1])) {
            		xTRUE[1] = true;
            		x2.setFitHeight(25);
            		x2.setFitWidth(25);
            		btn2.setPadding(new Insets(0.0));
            		btn2.setGraphic(x2);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get()&& (!xTRUE[1] && !oTRUE[1])) {
            		oTRUE[1] = true;
            		o2.setFitHeight(25);
            		o2.setFitWidth(25);
            		btn2.setPadding(new Insets(0.0));
            		btn2.setGraphic(o2);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn3.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[2] && !oTRUE[2])) {
            		xTRUE[2] = true;
            		x3.setFitHeight(25);
            		x3.setFitWidth(25);
            		btn3.setPadding(new Insets(0.0));
            		btn3.setGraphic(x3);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[2] && !oTRUE[2])) {
            		oTRUE[2] = true;
            		o3.setFitHeight(25);
            		o3.setFitWidth(25);
            		btn3.setPadding(new Insets(0.0));
            		btn3.setGraphic(o3);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn4.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[3] && !oTRUE[3])) {
            		xTRUE[3] = true;
            		x4.setFitHeight(25);
            		x4.setFitWidth(25);
            		btn4.setPadding(new Insets(0.0));
            		btn4.setGraphic(x4);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[3] && !oTRUE[3])) {
            		oTRUE[3] = true;
            		o4.setFitHeight(25);
            		o4.setFitWidth(25);
            		btn4.setPadding(new Insets(0.0));
            		btn4.setGraphic(o4);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn5.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[4] && !oTRUE[4])) {
            		xTRUE[4] = true;
            		x5.setFitHeight(25);
            		x5.setFitWidth(25);
            		btn5.setPadding(new Insets(0.0));
            		btn5.setGraphic(x5);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[4] && !oTRUE[4])) {
            		oTRUE[4] = true;
            		o5.setFitHeight(25);
            		o5.setFitWidth(25);
            		btn5.setPadding(new Insets(0.0));
            		btn5.setGraphic(o5);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn6.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() &&(!xTRUE[5] && !oTRUE[5])) {
            		xTRUE[5] = true;
            		x6.setFitHeight(25);
            		x6.setFitWidth(25);
            		btn6.setPadding(new Insets(0.0));
            		btn6.setGraphic(x6);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[5] && !oTRUE[5])) {
            		oTRUE[5] = true;
            		o6.setFitHeight(25);
            		o6.setFitWidth(25);
            		btn6.setPadding(new Insets(0.0));
            		btn6.setGraphic(o6);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn7.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[6] && !oTRUE[6])) {
            		xTRUE[6] = true;
            		x7.setFitHeight(25);
            		x7.setFitWidth(25);
            		btn7.setPadding(new Insets(0.0));
            		btn7.setGraphic(x7);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[6] && !oTRUE[6])) {
            		oTRUE[6] = true;
            		o7.setFitHeight(25);
            		o7.setFitWidth(25);
            		btn7.setPadding(new Insets(0.0));
            		btn7.setGraphic(o7);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn8.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[7] && !oTRUE[7])) {
            		xTRUE[7] = true;
            		x8.setFitHeight(25);
            		x8.setFitWidth(25);
            		btn8.setPadding(new Insets(0.0));
            		btn8.setGraphic(x8);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[7] && !oTRUE[7])) {
            		oTRUE[7] = true;
            		o8.setFitHeight(25);
            		o8.setFitWidth(25);
            		btn8.setPadding(new Insets(0.0));
            		btn8.setGraphic(o8);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		btn9.setOnAction(new EventHandler<ActionEvent>() { 
            public void handle(ActionEvent e) { 
            	if (whosTurn % 2 == 0 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[8] && !oTRUE[8])) {
            		xTRUE[8] = true;
            		x9.setFitHeight(25);
            		x9.setFitWidth(25);
            		btn9.setPadding(new Insets(0.0));
            		btn9.setGraphic(x9);
            		turns.setText("O's Turn");
        			border.setBottom(turns);
            	} else if (whosTurn % 2 == 1 && whosTurn <= 8 && !gameOver.get() && (!xTRUE[8] && !oTRUE[8])) {
            		oTRUE[8] = true;
            		o9.setFitHeight(25);
            		o9.setFitWidth(25);
            		btn9.setPadding(new Insets(0.0));
            		btn9.setGraphic(o9);
            		turns.setText("X's Turn");
        			border.setBottom(turns);
            	}
            	whosTurn++;
            	findWinner();
            } 
        });
		
		restart.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				mainStage = new Stage();
				initialize();
				start(mainStage);
			}
		});
		
		if (whosTurn >= 8 || gameOver.get()) {
			findWinner();			
//			listen.changed(restart.visibleProperty(), (Boolean) false, (Boolean) true);
		}
		
		border.setCenter(board);
		
		Scene scene = new Scene(border, 500, 500);
		mainStage.setTitle("Tic-Tac-Toe: 319_Hw6");
		mainStage.setScene(scene);
		mainStage.show();
	}
	
	public void findWinner() {
		if (xTRUE[0] && xTRUE[1] && xTRUE[2] || xTRUE[0] && xTRUE[3] && xTRUE[6] ||
				xTRUE[3] && xTRUE[4] && xTRUE[5] || xTRUE[1] && xTRUE[4] && xTRUE[7] ||
				xTRUE[6] && xTRUE[7] && xTRUE[8] || xTRUE[2] && xTRUE[5] && xTRUE[8] ||
				xTRUE[0] && xTRUE[4] && xTRUE[8] || xTRUE[2] && xTRUE[4] && xTRUE[6] )
			{
				turns .setText("X Wins!");
				gameOver.set(true);
			} else if (oTRUE[0] && oTRUE[1] && oTRUE[2] || oTRUE[0] && oTRUE[3] && oTRUE[6] ||
							oTRUE[3] && oTRUE[4] && oTRUE[5] || oTRUE[1] && oTRUE[4] && oTRUE[7] ||
							oTRUE[6] && oTRUE[7] && oTRUE[8] || oTRUE[2] && oTRUE[5] && oTRUE[8] ||
							oTRUE[0] && oTRUE[4] && oTRUE[8] || oTRUE[2] && oTRUE[4] && oTRUE[6] )
			{
				turns.setText("O Wins!");
				gameOver.set(true);
			} else if (whosTurn >= 8) {
				turns.setText("Draw!");
				gameOver.set(true);
			}
	}
	
	public void initialize() {
		whosTurn = 0;
		gameOver.set(false);
		for (int i = 0; i < 9; i++) {
			xTRUE[i] = false;
			oTRUE[i] = false;
		}
	}
	
	public void restart(Stage s) {
		s.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}