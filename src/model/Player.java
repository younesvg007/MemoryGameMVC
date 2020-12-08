package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;

import java.sql.*;

public class Player{

    private int idPlayer, score;
    private String userName, passwords;
    private Connection connection;


    public Player(Connection connection, String userName, String passwords) {
        this.connection = connection;
        this.userName = userName;
        this.passwords = passwords;
    }

    public String getName() {
        return userName;
    }

    public void setName(String userName) {
        this.userName = userName;
    }

    public String getPasswords() {
        return passwords;
    }

    public void setPasswords(String passwords) {
        this.passwords = passwords;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    //insertion new joueur
    public void insertPlayerDB() {
        int status;
        Statement statement;
        String sql = "INSERT INTO Users (username, password, pointTotal)" +
                " VALUES('" + userName + "','" + passwords + "','" + 0 + "') ";
        try {
            statement = connection.createStatement();
            status = statement.executeUpdate(sql);

            if (status > 0) {
                System.out.println("user registered");
            } else {
                System.out.println("Not registered");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println(e.getMessage());

        }
    }

    //verification de l'existance du compte du joueur
    public boolean checkUserIsExist() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passwords);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("User exist");
                return true;
            }else{
                System.out.println("user doesnt exist");
                return false;
            }
        }
        catch(SQLException e){
            return false;
        }
    }

    //verification de l'existance du nom du joueur
    public boolean checkUserNameInDB() {
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM Users WHERE username = ? OR password = ?";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, passwords);

            resultSet = preparedStatement.executeQuery();
            if (resultSet.next()){
                System.out.println("User exist in DB");
                return true;
            }else{
                System.out.println("user doesnt exist in DB");
                return false;
            }
        }
        catch(SQLException e){
            return false;
        }
    }
    //Mise a jour du score du joueur
    public boolean insertScorePlayerDB() {
        PreparedStatement preparedStatement = null;
        int status;
        String query = "UPDATE Users SET pointTotal = pointTotal + ? WHERE username = ?";
        try
        {
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, score);
            preparedStatement.setString(2, userName);

            status = preparedStatement.executeUpdate();
            if (status > 0){
                System.out.println("score added");
                return true;
            }else{
                System.out.println(" Not added");
                return false;
            }
        }
        catch(SQLException e){
            return false;
        }
    }

    //recup tout les joueurs
    public void getAllUser(ListView listView) {

        String sql = "SELECT username, pointTotal FROM Users ORDER BY pointTotal DESC";
        try{
            Statement stmt  = connection.createStatement();
            ResultSet rs    = stmt.executeQuery(sql);
            int index = 1;
            // loop through the result set
            while (rs.next()) {

                listView.getItems().add(
                                index + "\t\t" +
                                rs.getString("username") + "\t\t\t" +
                                rs.getInt("pointTotal") + "\t\t\t"
                );
                ++index;
            }

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

}
