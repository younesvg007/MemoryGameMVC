package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Position;

public class LoginView {

    private Rectangle centerBG;
    private HBox rootLayout, loginHBox;
    private VBox centerVBox;
    private Text welcomeTxt;
    private TextField userLoginTxt, pwdLoginTxt;
    private GridPane gridLogin, gridConfirme;
    private Label userLoginLabel, pwdLoginLabel;
    private Button loginBtn, registerBtn;
    private Position position;

    public LoginView() {

    }

    private Parent createContent() {
        // init fenetre
        Pane root = new Pane();
        root.setPrefSize(700, 600);

        position = new Position<>(Pos.CENTER, Pos.TOP_CENTER);

        Region background = new Region();
        background.setPrefSize(700, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        centerBG = new Rectangle(680, 555);
        centerBG.setArcWidth(50);
        centerBG.setArcHeight(50);
        centerBG.setFill(Color.LIGHTSEAGREEN);

        centerVBox = new VBox(50);
        centerVBox.setAlignment((Pos) position.getX());


        loginHBox = new HBox();
        loginHBox.setAlignment((Pos) position.getX());

        //title
        //welcomeTxt = new Text("Welcome to MemoGame");
        welcomeTxt = FactoryView.getText("Welcome to MemoGame");
        welcomeTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 45));
        welcomeTxt.setFill(Color.MAROON);
        loginHBox.getChildren().addAll(welcomeTxt);

        gridLogin = new GridPane();
        gridLogin.setAlignment((Pos) position.getX());
        gridLogin.setVgap(10);
        gridLogin.setHgap(30);
        gridLogin.setPadding(new Insets(20));

        //username
        userLoginLabel = FactoryView.getLabel("Username");
        userLoginLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        gridLogin.add(userLoginLabel, 0, 0);

        //userLoginTxt = new TextField();
        userLoginTxt = FactoryView.getTextField();
        userLoginTxt.setPromptText("username");
        gridLogin.add(userLoginTxt, 1, 0);

        //Password
        pwdLoginLabel = FactoryView.getLabel("Password");
        pwdLoginLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        gridLogin.add(pwdLoginLabel, 0, 1);

        pwdLoginTxt = FactoryView.getTextField();
        pwdLoginTxt.setPromptText("password");
        gridLogin.add(pwdLoginTxt, 1, 1);

        // Sign in

        gridConfirme = new GridPane();
        gridConfirme.setAlignment((Pos) position.getX());
        gridConfirme.setVgap(10);
        gridConfirme.setHgap(30);
        gridConfirme.setPadding(new Insets(20));

        loginBtn = FactoryView.getButton("Connexion");
        loginBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL,20));
        loginBtn.setMaxWidth(150);
        gridConfirme.add(loginBtn, 0, 0);

        registerBtn = FactoryView.getButton("Inscription");
        registerBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL,20));
        registerBtn.setMaxWidth(150);
        gridConfirme.add(registerBtn, 1, 0);

        centerVBox.getChildren().addAll(loginHBox, gridLogin, gridConfirme);

        // ADD BOTH STACKS TO ROOT LAYOUT

        rootLayout.getChildren().addAll(new StackPane(centerBG, centerVBox));
        root.getChildren().addAll(background, rootLayout);

        return root;
    }

    public void setUpScene(Stage window){
        window.setScene(new Scene(createContent()));
        window.setWidth(700);
        window.setHeight(600);
        window.setResizable(false);
        window.setTitle("Authentification");
        window.show();
    }

    public Button getLoginBtn() {
        return loginBtn;
    }

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public TextField getUserLoginTxt() {
        return userLoginTxt;
    }

    public TextField getPwdLoginTxt() {
        return pwdLoginTxt;
    }
}
