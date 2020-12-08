package controller;

import model.database.SqlConnection;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.CardGrid;
import model.Player;
import model.Observable;
import model.Observer;
import view.*;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Controller implements Observable, Runnable {

    private Connection connection;
    private int NUM_OF_TEST;
    private BoardView boardView;
    private CardGridView cardGridView;
    private SettingView settingView;
    private LoginView loginView;
    private RegisterView registerView;
    private RankingView rankingView;
    private boolean isHelpActived;
    private boolean isConnected = false;
    private String result;
    private String mode = "", level = "", color;
    private Stage stage;
    private Player player;
    private int score;
    private static Controller instance;
    private CardForm firstValue;
    private CardGrid cardGrid;

    private List<Observer> observers;

    public Controller(Stage primaryStage) {
        this.stage = primaryStage;
        observers = new ArrayList<>(); //Instanciation de la liste des observers dans le constructeur
        boardView = new BoardView(primaryStage); //Instanciation de la interface principale

        initField();
    }

    //initialisation du jeu
    private void initField() {
        checkConnection();

        loginView = new LoginView();
        registerView = new RegisterView();
        settingView = new SettingView();
        rankingView = new RankingView();
        firstValue = null;
        isHelpActived = false;
        boardView.getBtnHelp().disableProperty().bind(new SimpleBooleanProperty(true));

        buildGame();
        this.subscribe(boardView); // permet d'ajouter cette classe en tant que observer
        settingGame();
        loginGame();
        helpGame();
        rankingGame();
        exitGame();
    }

    // methode controllant le paramètre
    private void rankingGame() {
        boardView.getBtnRanking().setOnAction(actionEvent -> {
            if (!isConnected) { //pas de connexion => ALERT cONNEXION fIRST
                Alert alert9 = new Alert(Alert.AlertType.WARNING);
                dialogAlert(alert9, "Connexion First", "You have to login first !!!");
            } else {
                rankingView.setUpScene(stage);//affichage de la fenetre classement
                player.getAllUser(rankingView.getListView()); //recuperer tout les scores des joueurs du jeu
            }
        });
    }

    //Init Connexion vers la Base de données
    private void checkConnection() {
        connection = SqlConnection.DbConnector();
        if (connection == null) {
            System.out.println("Connection Not Successful");
            System.exit(1);
        } else {
            System.out.println("Connection Successful");
        }
    }

    //Design Pattern Singleton permet d'instancier q'une seule fois. => securité, economie de la memoire
    public static Controller getInstance(Stage primaryStage) {
        if (instance == null) {
            instance = new Controller(primaryStage);
            new Thread(instance).start();
        }
        return instance;
    }

    //methode permettant de construire d'implementer le jeu
    private void buildGame() {
        boardView.getBtnPlay().setOnAction(actionEvent -> {
            if (!isConnected) {
                Alert alert0 = new Alert(Alert.AlertType.WARNING);
                dialogAlert(alert0, "Connexion First", "You have to login first !!!");
            } else {
                //initialisation du systeme des scores à nul
                score = 0;
                result = "";
                color = "";

                //creation d'un tableau specifique de carte selon le niveau
                switch (level) {
                    case "INTERMEDIATE":
                        cardGridView = createNewCardGridView(cardGridView, mode, level, 4,4,110,70);
                        NUM_OF_TEST = 30;
                        cardGrid = new CardGrid(cardGridView.getMAX_ROW(), cardGridView.getMAX_COL(), NUM_OF_TEST); //model cardgrid
                        break;
                    case "ADVANCED":
                        cardGridView = createNewCardGridView(cardGridView, mode, level, 4,5,110,70);
                        NUM_OF_TEST = 35;
                        cardGrid = new CardGrid(cardGridView.getMAX_ROW(), cardGridView.getMAX_COL(), NUM_OF_TEST);
                        break;
                    case "MONSTER":
                        cardGridView = createNewCardGridView(cardGridView, mode, level, 4,7,110,70);
                        NUM_OF_TEST = 45;
                        cardGrid = new CardGrid(cardGridView.getMAX_ROW(), cardGridView.getMAX_COL(), NUM_OF_TEST);
                        break;
                    default:
                        cardGridView = createNewCardGridView(cardGridView, mode, level, 3,4,140,100);
                        NUM_OF_TEST = 25;
                        cardGrid = new CardGrid(cardGridView.getMAX_ROW(), cardGridView.getMAX_COL(), NUM_OF_TEST);
                        break;
                }

                System.out.println(player.getName() + " connecté");

                //reconstruction du jeu
                boardView.getRightVBox().getChildren().clear();
                boardView.getRightVBox().getChildren().addAll(boardView.getInfosGame(), boardView.getPlayerScore(), cardGridView.createGrid(), boardView.getResult());

                //notification des donnes du jeu tel que le nom, le score, le nombre d'essai restant, le resultat du jeu, le niveau
                notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);

                //evenement lors des click dans le tableau de cartes
                clickCardGrid();

                boardView.getBtnHelp().disableProperty().bind(new SimpleBooleanProperty(false)); //activation des propriétes du jeu bonton aide
                boardView.getBtnPlay().setText("PLAY AGAIN");
                boardView.getBtnPlay().disableProperty().bind(new SimpleBooleanProperty(true)); //Désactivation des propriétes du jeu bonton Play
            }

        });
    }

    private CardGridView createNewCardGridView(CardGridView cardGridViewA, String mode, String level, int row, int col, int height, int width){
        cardGridViewA = new CardGridView(mode, level);
        cardGridViewA.setMAX_ROW(row);
        cardGridViewA.setMAX_COL(col);
        cardGridViewA.setCARD_HEIGHT(height);
        cardGridViewA.setCARD_WIDTH(width);

        return cardGridViewA;
    }

    //Authentification d'un utilisateur
    private void loginGame() {
        boardView.getBtnConnexion().setOnAction(actionEvent -> {
            //affichage de la fenetre
            loginView.setUpScene(stage);

            //authentification
            loginView.getLoginBtn().setOnAction(actionEvent1 -> {
                validationUser();
            });

            //Inscription
            loginView.getRegisterBtn().setOnAction(actionEvent1 -> {
                registerView.setUpScene(stage);
                registerView.getRegisterBtn().setOnAction(actionEvent2 -> {
                    registrationUser();
                });
            });
        });
    }

    // methode qui permet l'Inscription dune user
    private void registrationUser() {
        String usernameRegister = registerView.getUserRegisterTxt().getText().trim();
        String passwordRegister = registerView.getPwdRegisterTxt().getText().trim();


        if (usernameRegister.isEmpty() || passwordRegister.isEmpty()) { //champs remplis?
            Alert alert1 = new Alert(Alert.AlertType.WARNING);
            dialogAlert(alert1, "Validate Fields", "Please Enter all fields");
        } else {

            player = new Player(connection, usernameRegister, passwordRegister); //model Player

            boolean isExist = player.checkUserNameInDB(); //appel à la methode contenant la requete qui verifie si le compte existe dan la BDD
            if (isExist) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert(alert2, "Registration Impossible", "Please enter new User");
            } else {

                player.insertPlayerDB(); //appel à la methode contenant la requete qui insert le nouveau compte dan la BDD
                boardView = new BoardView(stage);
                initField();
                isConnected = true;
            }
        }

    }

    //methode pour creer un alert dialog
    private void dialogAlert(Alert alerte, String tilte, String msg) {
        alerte.setTitle(tilte);
        alerte.setHeaderText(null);
        alerte.setContentText(msg);
        alerte.showAndWait();
    }

    // methode qui permet le connexion dune user
    private void validationUser() {

        String usernameLogin = loginView.getUserLoginTxt().getText().trim();
        String passwordLogin = loginView.getPwdLoginTxt().getText().trim();

        if (usernameLogin.isEmpty() || passwordLogin.isEmpty()) {
            Alert alert3 = new Alert(Alert.AlertType.WARNING);
            dialogAlert(alert3, "Validate Fields", "Please Enter all fields");

        } else {

            player = new Player(connection, usernameLogin, passwordLogin);
            boolean isExist = player.checkUserIsExist();
            if (!isExist) {
                Alert alert2 = new Alert(Alert.AlertType.WARNING);
                dialogAlert(alert2, "Authentitfication Impossible", "Please enter a existing User");
            } else {

                boardView = new BoardView(stage);
                initField();
                isConnected = true;
            }
        }

    }

    //methode controllant l'aide du jeu
    private void helpGame() {
        boardView.getBtnHelp().setOnAction(actionEvent -> {
            if (!isHelpActived) {
                display();
                isHelpActived = true;
                cardGrid.testPenality(); // penalité du jeu
            } else {
                disappearance();
                isHelpActived = false;
                notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);
                boardView.getBtnHelp().disableProperty().bind(new SimpleBooleanProperty(true));////Désactivation des propriétes du jeu bonton Play
            }
        });
    }

    //Disparition des cartes
    private void disappearance() {
        for (int i = 0; i < cardGridView.getMAX_ROW(); i++) {
            for (int j = 0; j < cardGridView.getMAX_COL(); j++) {
                cardGridView.getCardForm()[i][j].close();
            }
        }
    }

    //affichage des cartes
    private void display() {
        for (int i = 0; i < cardGridView.getMAX_ROW(); i++) {
            for (int j = 0; j < cardGridView.getMAX_COL(); j++) {
                cardGridView.getCardForm()[i][j].open();
            }
        }
    }

    //methode controllant les parametres du jeu
    private void settingGame() {
        boardView.getBtnParam().setOnAction(actionEvent -> {

                    if (!isConnected) {
                        Alert alert7 = new Alert(Alert.AlertType.WARNING);
                        dialogAlert(alert7, "Connexion First", "You have to login first !!!");
                    } else {
                        settingView.setUpScene(stage);

                        //recupere la valeur de radio button de mode
                        settingView.getModeGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                            public void changed(ObservableValue<? extends Toggle> ob,
                                                Toggle o, Toggle n) {

                                RadioButton rb = (RadioButton) settingView.getModeGroup().getSelectedToggle();

                                if (rb != null) {
                                    mode = rb.getText();

                                }
                            }
                        });

                        //recupere la valeur de radio button de niveau
                        settingView.getLevelGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                            @Override
                            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                                RadioButton rb = (RadioButton) settingView.getLevelGroup().getSelectedToggle();

                                if (rb != null) {
                                    level = rb.getText();
                                }
                            }
                        });

                        //recupere la valeur de radio button de couleur
                        settingView.getBackgroundGroup().selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
                            @Override
                            public void changed(ObservableValue<? extends Toggle> observableValue, Toggle toggle, Toggle t1) {
                                RadioButton rb = (RadioButton) settingView.getBackgroundGroup().getSelectedToggle();

                                if (rb != null) {
                                    color = rb.getText();
                                }
                            }
                        });

                        settingView.getSaveBtn().setOnAction(actionEvent1 -> {

                            boardView = new BoardView(stage);
                            initField();

                            //permet de changer la couleur du plateau
                            switch (color) {
                                case "GREEN":
                                    boardView.getRightBG().setFill(Color.GREEN);
                                    break;
                                case "ORANGE":
                                    boardView.getRightBG().setFill(Color.ORANGE);
                                    break;
                                case "LIGHTBLUE":
                                    boardView.getRightBG().setFill(Color.LIGHTSEAGREEN);
                                    break;
                                default:
                                    boardView.getRightBG().setFill(Color.GREEN);
                                    break;
                            }

                        });

                    }
                }

        );
    }

    //methode controllant la sortie du jeu
    private void exitGame() {
        boardView.getBtnExit().setOnAction(actionEvent -> {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Exit");
            alert.setHeaderText(null);
            alert.setContentText("Are you ok to Exit the game?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK)
                Platform.exit();

        });
    }

    // methode gerant evenement sur le plateau de cartes
    private void clickCardGrid() {
        for (int i = 0; i < cardGridView.getMAX_ROW(); i++) {
            for (int j = 0; j < cardGridView.getMAX_COL(); j++) {
                cardGridView.getCardForm()[i][j].setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent event) {
                        if (!cardGrid.isLoser()) {
                            CardForm cd = (CardForm) event.getSource();
                            checkIfSameValue(cd);
                        } else {
                            result = "LOSER";
                            boardView.getBtnPlay().disableProperty().bind(new SimpleBooleanProperty(false));
                            boardView.getBtnHelp().disableProperty().bind(new SimpleBooleanProperty(true));
                            notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);
                        }
                    }
                });
            }
        }
    }

    //verification si les cartes cliqué ont la meme valeur
    private void checkIfSameValue(CardForm cd) {

        if (cd.isOpen())
            return;

        if (firstValue == null) {
            firstValue = cd;

            firstValue.open();
        } else {
            cd.open(() -> {
                if (!firstValue.hasSameValue(cd)) {
                    cd.close();
                    firstValue.close();
                } else {
                    ++score;
                    cardGrid.pairFind();
                    notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);
                    if (cardGrid.isWinner()) {
                        result = "WINNER";
                        boardView.getBtnPlay().disableProperty().bind(new SimpleBooleanProperty(false));
                        boardView.getBtnHelp().disableProperty().bind(new SimpleBooleanProperty(true));
                        notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);
                        player.setScore(score);
                        System.out.println(player.getScore());
                        player.insertScorePlayerDB();

                    }
                }

                firstValue = null;
            });

        }

        cardGrid.testDecrease();
        notifyObserver(player.getName(), score, cardGrid.getNumOfTest(), result, level);
    }


    @Override
    public void subscribe(Observer observer) {
        if (observer != null && !observers.contains(observer)) { // gerer doublons + pas etre nul
            observers.add(observer);
        }
    }

    @Override
    public void unsubscribe(Observer observer) {
        if (observer != null && observers.contains(observer)) {
            observers.remove(observer);
        }
    }

    @Override
    public void notifyObserver(String playerName, int score, int numOfTest, String resultat, String niveau) {
        for (Observer observer : observers) {
            observer.notifyFromObservable(playerName, score, numOfTest, resultat, niveau);
        }
    }

    @Override
    public void run() {

    }
}
