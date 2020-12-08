package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.Random;

public class CardGridView {

    private StackPane stack;
    private String mode, level;
    private int MAX_ROW;
    private int MAX_COL;
    private int CARD_WIDTH;
    private int CARD_HEIGHT;
    private int numOfCards;
    private int numOfPairs;
    private GridPane grid;
    private int clickCount = 2;
    private CardForm selected = null;
    private CardForm[][] cardForm;
    private ArrayList<Integer> cartAList;

    public CardGridView(String mode, String level) {
        this.mode = mode;
        this.level = level;
        cartAList = new ArrayList();
    }

    public GridPane createGrid() {
        grid = new GridPane();
        cardForm = new CardForm[MAX_ROW][MAX_COL];

        grid.getChildren().clear();
        grid.setAlignment(Pos.CENTER);
        grid.setPadding(new Insets(10));
        grid.setHgap(20);
        grid.setVgap(15);


        numOfCards = MAX_COL*MAX_ROW;
        numOfPairs = ((MAX_COL*MAX_ROW)/2);

        char c = 'A';

        if (level.equals("MONSTER")){
            int index = 100;
            initTable(index, c);
            melange();
            displayCards();
        }
        else {
            int index = 0;
            initTable(index, c);
            melange();
            displayCards();
        }

        return grid;
    }

    private void initTable(int index, char c) {
        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COL; j++) {

                if (mode.equals("ALPHABET")){ //niveau alphabet
                    if (level.equals("MONSTER")){
                        if (index == (100-numOfPairs)){
                            index = 100;
                            c = 'A';
                        }
                        cardForm[i][j] = new CardForm(c+"", CARD_WIDTH, CARD_HEIGHT);
                        --index;
                        ++c;
                    }
                    else {
                        if (index == numOfPairs){
                            index = 0;
                            c = 'A';
                        }
                        cardForm[i][j] = new CardForm(c+"", CARD_WIDTH, CARD_HEIGHT);
                        ++index;
                        ++c;
                    }
                }
                else{ //niveau couleur et numero
                    if (level.equals("MONSTER")){
                        if (index == (100-numOfPairs)){
                            index = 100;
                        }
                        cardForm[i][j] = new CardForm(index+"", CARD_WIDTH, CARD_HEIGHT);
                        --index;
                    }
                    else {
                        if (index == numOfPairs){
                            index = 0;
                        }
                        cardForm[i][j] = new CardForm(index+"", CARD_WIDTH, CARD_HEIGHT);
                        ++index;
                    }

                }

            }
        }

    }

    private void melange() {

        Random random = new Random();

        for (int i = cardForm.length - 1; i > 0; i--) {
            for (int j = cardForm[i].length - 1; j > 0; j--) {
                int m = random.nextInt(i + 1);
                int n = random.nextInt(j + 1);

                CardForm temp = cardForm[i][j];
                cardForm[i][j] = cardForm[m][n];
                cardForm[m][n] = temp;
            }
        }
    }

    private void displayCards() {

        for (int i = 0; i < MAX_ROW; i++) {
            for (int j = 0; j < MAX_COL; j++) {

                grid.add(cardForm[i][j], j, i);

            }
        }
    }

    public CardForm[][] getCardForm() {
        return cardForm;
    }

    public int getMAX_COL() {
        return MAX_COL;
    }

    public int getMAX_ROW() {
        return MAX_ROW;
    }

    public void setMAX_COL(int MAX_COL) {
        this.MAX_COL = MAX_COL;
    }

    public void setMAX_ROW(int MAX_ROW) {
        this.MAX_ROW = MAX_ROW;
    }

    public void setCARD_HEIGHT(int CARD_HEIGHT) {
        this.CARD_HEIGHT = CARD_HEIGHT;
    }

    public void setCARD_WIDTH(int CARD_WIDTH) {
        this.CARD_WIDTH = CARD_WIDTH;
    }

    public int getCARD_WIDTH() {
        return CARD_WIDTH;
    }

    public int getCARD_HEIGHT() {
        return CARD_HEIGHT;
    }
}
