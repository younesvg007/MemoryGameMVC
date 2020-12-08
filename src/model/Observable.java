package model;

public interface Observable{

    //permet d'insrcrire
    void subscribe(Observer obs);

    //permet de desinscrire
    void unsubscribe(Observer obs);

    // methode pour chekem
    void notifyObserver(String playerName, int score, int numOfTest, String resultat, String niveau);

}
