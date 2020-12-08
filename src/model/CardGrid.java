package model;


public class CardGrid {

   // public Card[][] tableauCard;
    private int MAX_ROW, MAX_COL, numOfCardsDisplay, numOfTest;

    public CardGrid(int MAX_ROW, int MAX_COL,int nbTest ) {
        //tableauCard = new Card[MAX_ROW][MAX_COL];
        this.MAX_ROW = MAX_ROW;
        this.MAX_COL = MAX_COL;

        numOfCardsDisplay = 0;
        numOfTest = nbTest;
    }

    public int getNumOfTest() {
        return numOfTest;
    }

    public void testDecrease(){
        numOfTest -= 1;
    }

    public void testPenality(){
        numOfTest -= 2;
    }

    public boolean isLoser(){
        return numOfTest == 0;
    }

    public void pairFind(){
        numOfCardsDisplay +=1;
    }

    public boolean isWinner(){
        return (MAX_ROW*MAX_COL)/2 == numOfCardsDisplay;
    }

}
