package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Player;

public class RankingView {
    private Rectangle centerBG;
    private HBox rootLayout, loginHBox;
    private VBox centerVBox;
    private Text titleTxt;
    private ListView<String> listView;

    public RankingView() {

    }

    private Parent createContent() {
        // init fenetre
        Pane root = new Pane();
        root.setPrefSize(700, 600);

        Region background = new Region();
        background.setPrefSize(700, 600);
        background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

        rootLayout = new HBox(5);
        rootLayout.setPadding(new Insets(5, 5, 5, 5));
        centerBG = new Rectangle(680, 555);
        centerBG.setArcWidth(50);
        centerBG.setArcHeight(50);
        centerBG.setFill(Color.ORANGE);

        centerVBox = new VBox(50);
        centerVBox.setAlignment(Pos.TOP_CENTER);

        loginHBox = new HBox();
        loginHBox.setAlignment(Pos.TOP_CENTER);

        titleTxt = new Text("World Ranking");
        titleTxt.setFont(Font.font("Tahoma", FontWeight.NORMAL, 45));
        titleTxt.setFill(Color.BLACK);
        loginHBox.getChildren().addAll(titleTxt);

        // Create a ListView
        listView = new ListView<>();
        listView.setMaxWidth(300);

        centerVBox.getChildren().addAll(loginHBox, listView);

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

    public ListView<String> getListView() {
        return listView;
    }

}
