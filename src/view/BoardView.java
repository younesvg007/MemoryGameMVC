package view;

import javafx.scene.text.FontWeight;
import model.Position;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Observer;

public class BoardView extends Parent implements Observer {

    private Rectangle leftBG, rightBG;
    private Button btnPlay, btnConnexion, btnParam, btnRanking, btnHelp, btnExit;
    private Text playerScore, numOfTest, niveau, result;
    private VBox rightVBox, leftVBox, loginLeftVbox, menuVBox;
    private HBox rootLayout, infosGame;
    private Pane root;
    private Region background;
    private Position position;
    private Scene scene;

    public BoardView(Stage stagePrimary) {
        createScene(stagePrimary);
    }

    public Parent createContent() {
        //Interface complete
        root = new Pane();
        position = new Position<>(Pos.CENTER, Pos.TOP_CENTER);
        root.setPrefSize(1100, 820);

        background = new Region();
        background.setPrefSize(1100, 820);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));

        //partie Left

        leftBG = new Rectangle(270, 775);
        leftBG.setArcWidth(50);
        leftBG.setArcHeight(50);
        leftBG.setFill(Color.GRAY);

        //Partie Right

        rightBG = new Rectangle(800, 775);
        rightBG.setArcWidth(50);
        rightBG.setArcHeight(50);
        rightBG.setFill(Color.GREEN);

        // Component LEFT

        leftVBox = new VBox(20);
        leftVBox.setAlignment((Pos) position.getX());

        //init buttons

        btnConnexion = FactoryView.getButton("LOGIN");
        btnPlay = FactoryView.getButton("PLAY");
        btnParam = FactoryView.getButton("SETTINGS");
        btnHelp = FactoryView.getButton("HELP");
        btnRanking = FactoryView.getButton("RANKING");
        btnExit = FactoryView.getButton("EXIT");

        btnConnexion.setFont(Font.font("Tahoma", FontWeight.BOLD,25));
        btnConnexion.setMaxWidth(200);
        btnConnexion.setTextFill(Color.ROYALBLUE);

        btnPlay.setFont(Font.font("Tahoma", FontWeight.BOLD,25));
        btnPlay.setMaxWidth(200);
        btnPlay.setTextFill(Color.GREEN);

        btnParam.setFont(Font.font("Tahoma", FontWeight.NORMAL,25));
        btnParam.setMaxWidth(200);

        btnHelp.setFont(Font.font("Tahoma", FontWeight.NORMAL,25));
        btnHelp.setMaxWidth(200);

        btnRanking.setFont(Font.font("Tahoma", FontWeight.NORMAL,25));
        btnRanking.setMaxWidth(200);

        btnExit.setFont(Font.font("Tahoma", FontWeight.BOLD,25));
        btnExit.setMaxWidth(200);
        btnExit.setTextFill(Color.RED);

        loginLeftVbox= new VBox(20, btnConnexion);
        loginLeftVbox.setAlignment((Pos) position.getX());
        loginLeftVbox.setVgrow(btnConnexion, Priority.ALWAYS);

        leftVBox.getChildren().addAll(btnPlay, btnHelp, btnRanking, btnParam, btnExit);
        menuVBox = new VBox();
        menuVBox.setAlignment((Pos) position.getX());
        menuVBox.setSpacing(220);
        menuVBox.getChildren().addAll(loginLeftVbox, leftVBox);

        // component RIGHT

        rightVBox = new VBox(50);
        rightVBox.setAlignment((Pos) position.getY());

        niveau = FactoryView.getText("Niveau: ");
        playerScore = FactoryView.getText("Player: ");
        result = FactoryView.getText("Result: ");
        numOfTest = FactoryView.getText("Number of Test: ");

        numOfTest.setFill(Color.BLACK);
        numOfTest.setFont(Font.font("Tahoma", FontWeight.NORMAL,30));

        niveau.setFill(Color.BLACK);
        niveau.setFont(Font.font("Tahoma", FontWeight.NORMAL,30));

        playerScore.setFill(Color.MAROON);
        playerScore.setFont(Font.font("Tahoma", FontWeight.NORMAL,30));

        result.setFill(Color.BLACK);
        result.setFont(Font.font("Tahoma", FontWeight.BOLD,30));

        //mettre de l'espace entre les Text
        Region region1 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        infosGame = new HBox(30, numOfTest, region1, niveau);

        rightVBox.getChildren().addAll(infosGame, playerScore);

        // ADD BOTH STACKS TO ROOT LAYOUT

        rootLayout.getChildren().addAll(new StackPane(leftBG, menuVBox), new StackPane(rightBG, rightVBox));
        root.getChildren().addAll(background, rootLayout);

        return root;
    }

    void createScene(Stage primaryStage){
        scene = new Scene(createContent());
        primaryStage.setScene(scene);
        primaryStage.setWidth(1100);
        primaryStage.setHeight(820);
        primaryStage.setResizable(false);
        primaryStage.setTitle("Memory Card Game");
        primaryStage.show();
    }



    public Button getBtnPlay() {
        return btnPlay;
    }

    public Button getBtnParam() {
        return btnParam;
    }

    public Button getBtnRanking() {
        return btnRanking;
    }

    public Button getBtnHelp() {
        return btnHelp;
    }

    public Button getBtnExit() {
        return btnExit;
    }

    public VBox getRightVBox() {
        return rightVBox;
    }

    public Rectangle getRightBG() {
        return rightBG;
    }

    public Text getPlayerScore() {
        return playerScore;
    }

    public HBox getInfosGame() {
        return infosGame;
    }

    public Text getNumOfTest() {
        return numOfTest;
    }

    public Text getResult() {
        return result;
    }

    public Button getBtnConnexion() {
        return btnConnexion;
    }

    @Override
    public void notifyFromObservable(String playerName, int score, int numOfTests, String resultat, String level) { // permet de modifier dan le vue

        playerScore.setText(playerName+" has "+score +" points");

        numOfTest.setText("Number of tests : "+numOfTests);

        result.setText("You are The "+resultat);

        niveau.setText("Level : "+level);
    }
}
