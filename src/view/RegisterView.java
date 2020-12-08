package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Position;

public class RegisterView {

    private Rectangle centerBG;
    private HBox rootLayout, hBoxRegister;
    private VBox centerVBox;
    private Text titleTxt;
    private TextField userRegisterTxt, pwdRegisterTxt;
    private GridPane gridRegister;
    private Label userRegisterLabel, pwdRegisterLabel;
    private Button registerBtn;
    private Position position;

    public RegisterView() {

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

        hBoxRegister = new HBox();
        hBoxRegister.setAlignment((Pos) position.getX());

        titleTxt = FactoryView.getText("Registration");
        titleTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 45));
        titleTxt.setFill(Color.MAROON);
        hBoxRegister.getChildren().addAll(titleTxt);

        gridRegister = new GridPane();
        gridRegister.setAlignment((Pos) position.getX());
        gridRegister.setVgap(10);
        gridRegister.setHgap(30);
        gridRegister.setPadding(new Insets(20));

        //username
        userRegisterLabel = FactoryView.getLabel("Username");
        userRegisterLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        gridRegister.add(userRegisterLabel, 0, 0);

        userRegisterTxt = FactoryView.getTextField();
        userRegisterTxt.setPromptText("username");
        gridRegister.add(userRegisterTxt, 1, 0);

        //Password
        pwdRegisterLabel = FactoryView.getLabel("Password");
        pwdRegisterLabel.setFont(Font.font("Tahoma", FontWeight.LIGHT, 25));
        gridRegister.add(pwdRegisterLabel, 0, 1);

        pwdRegisterTxt = FactoryView.getTextField();
        pwdRegisterTxt.setPromptText("password");
        gridRegister.add(pwdRegisterTxt, 1, 1);

        // Sign in

        registerBtn = FactoryView.getButton("Sign Up");
        registerBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL,20));
        registerBtn.setMaxWidth(150);
        registerBtn.setAlignment((Pos) position.getX());

        centerVBox.getChildren().addAll(hBoxRegister, gridRegister, registerBtn);

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
        window.setTitle("Registration");
        window.show();
    }

    public Button getRegisterBtn() {
        return registerBtn;
    }

    public TextField getUserRegisterTxt() {
        return userRegisterTxt;
    }

    public TextField getPwdRegisterTxt() {
        return pwdRegisterTxt;
    }
}
