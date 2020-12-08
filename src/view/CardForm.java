package view;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

public class CardForm extends StackPane {

    private Text valueText = new Text();
    private Rectangle rect = new Rectangle();
    private int CARD_WIDTH;
    private int CARD_HEIGHT;
    private String value;

    public CardForm(String value, int CARD_WIDTH, int CARD_HEIGHT) {
        //this.niveau = niveau;
        this.value = value;
        this.CARD_WIDTH = CARD_WIDTH;
        this.CARD_HEIGHT = CARD_HEIGHT;
        initField(value);
        close();
    }

    //Configuartion de la carte sur le plateu de jeu
    private void initField(String value) {
        rect.setWidth(CARD_WIDTH);
        rect.setHeight(CARD_HEIGHT);
        rect.setArcWidth(20);
        rect.setArcHeight(20);
        rect.setFill(Color.WHITE);
        rect.setStroke(Color.BLACK);
        valueText.setFont(Font.font(50));
        valueText.setText(value+"");
        valueText.setFill(Color.BLUE);

        setAlignment(Pos.CENTER);
        getChildren().addAll(rect, valueText);
    }

    //
    public boolean isOpen() {
        return valueText.getOpacity() == 1;
    }

    //affichage de la carte
    public void open(Runnable action) {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), valueText);
        ft.setToValue(1);
        ft.setOnFinished(e -> action.run());
        ft.play();
    }
    public void open() {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.5), valueText);
        ft.setToValue(1);
        ft.play();
    }

    //Disparition de la carte
    public void close() {
        FadeTransition ft = new FadeTransition(Duration.seconds(0.3), valueText);
        ft.setToValue(0);
        ft.play();
    }

    //si les cartes ont meme valeur
    public boolean hasSameValue(CardForm other) {
        return valueText.getText().equals(other.valueText.getText());
    }

    public String getValue() {
        return value;
    }
}
