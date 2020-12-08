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
import javafx.stage.Stage;
import model.Position;

public class SettingView {

    private Rectangle centerBG;
    private HBox rootLayout, modeBox, niveauBox, backGroundBox, saveBox;
    private VBox centerVBox;
    private Label modeLabel, levelLabel, backgroundLabel;
    private ToggleGroup modeGroup, levelGroup, backgroundGroup;
    private RadioButton chiffreBtn, alphabetBtn, level1Btn, level2Btn, level3Btn, level4Btn, color1Btn, color2Btn, color3Btn;
    private Button saveBtn;
    private Position position;

    public SettingView() {
        modeBox = new HBox();
        niveauBox = new HBox();
        backGroundBox = new HBox();
        saveBox = new HBox();
    }

    private Parent createContent() {
        // init fenetre
        Pane root = new Pane();
        root.setPrefSize(700, 600);
        position = new Position<>(Pos.TOP_LEFT, Pos.BASELINE_LEFT);

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

        // genre
        //modeLabel = new Label("Mode: ");
        modeLabel = FactoryView.getLabel("Mode: ");
        modeLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,20));


        modeGroup = new ToggleGroup();

        chiffreBtn = new RadioButton("CHIFFRE");
        chiffreBtn.setPadding(new Insets(20));//espace
        chiffreBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        chiffreBtn.setToggleGroup(modeGroup);

        alphabetBtn = new RadioButton("ALPHABET");
        alphabetBtn.setPadding(new Insets(15));//espace
        alphabetBtn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        alphabetBtn.setToggleGroup(modeGroup);

        modeBox.setAlignment((Pos) position.getY());
        modeBox.setPadding(new Insets(20)); // espace
        modeBox.setSpacing(5);
        modeBox.getChildren().addAll(modeLabel, chiffreBtn, alphabetBtn);

        // Niveau
        levelLabel = FactoryView.getLabel("Niveau: ");
        levelLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,20));


        levelGroup = new ToggleGroup();

        level1Btn = new RadioButton("EASY");
        level1Btn.setPadding(new Insets(20));//espace
        level1Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        level1Btn.setToggleGroup(levelGroup);

        level2Btn = new RadioButton("INTERMEDIATE");
        level2Btn.setPadding(new Insets(15));//espace
        level2Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        level2Btn.setToggleGroup(levelGroup);

        level3Btn = new RadioButton("ADVANCED");
        level3Btn.setPadding(new Insets(15));//espace
        level3Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        level3Btn.setToggleGroup(levelGroup);

        level4Btn = new RadioButton("MONSTER");
        level4Btn.setPadding(new Insets(15));//espace
        level4Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        level4Btn.setToggleGroup(levelGroup);

        niveauBox.setAlignment((Pos) position.getY());
        niveauBox.setPadding(new Insets(20)); // espace
        niveauBox.setSpacing(5);
        niveauBox.getChildren().addAll(levelLabel, level1Btn, level2Btn, level3Btn, level4Btn);

        // Background

        backgroundLabel = FactoryView.getLabel("Background: ");
        backgroundLabel.setFont(Font.font("Tahoma", FontWeight.BOLD,20));


        backgroundGroup = new ToggleGroup();

        color1Btn = new RadioButton("GREEN");
        color1Btn.setPadding(new Insets(20));//espace
        color1Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        color1Btn.setToggleGroup(backgroundGroup);

        color2Btn = new RadioButton("ORANGE");
        color2Btn.setPadding(new Insets(15));//espace
        color2Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        color2Btn.setToggleGroup(backgroundGroup);

        color3Btn = new RadioButton("LIGHTBLUE");
        color3Btn.setPadding(new Insets(15));//espace
        color3Btn.setFont(Font.font("Tahoma", FontWeight.NORMAL,15));
        color3Btn.setToggleGroup(backgroundGroup);

        backGroundBox.setAlignment((Pos) position.getY());
        backGroundBox.setPadding(new Insets(20)); // espace
        backGroundBox.setSpacing(5);
        backGroundBox.getChildren().addAll(backgroundLabel, color1Btn, color2Btn, color3Btn);

        // Save

        saveBtn = FactoryView.getButton("ENREGISTRER");
        saveBtn.setFont(Font.font("Tahoma", FontWeight.BOLD,25));
        saveBtn.setMaxWidth(240);
        saveBtn.setTextFill(Color.GREEN);
        saveBox.setAlignment(Pos.CENTER);
        saveBox.getChildren().addAll(saveBtn);

        //add all hbox to stack
        centerVBox.getChildren().addAll(modeBox, niveauBox, backGroundBox, saveBox);

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
        window.setTitle("Settings");
        window.show();
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public ToggleGroup getModeGroup() {
        return modeGroup;
    }

    public ToggleGroup getLevelGroup() {
        return levelGroup;
    }

    public ToggleGroup getBackgroundGroup() {
        return backgroundGroup;
    }

    public HBox getModeBox() {
        return modeBox;
    }

    public HBox getNiveauBox() {
        return niveauBox;
    }

    public VBox getCenterVBox() {
        return centerVBox;
    }

    public Rectangle getCenterBG() {
        return centerBG;
    }
}
