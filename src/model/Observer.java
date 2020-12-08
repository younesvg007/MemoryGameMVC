package model;

public interface Observer {

    //permet de recevoir l'observable pour notifier à la vu : consequence
    void notifyFromObservable(String playerName, int score, int numOfTest, String resultat, String niveau);
}
