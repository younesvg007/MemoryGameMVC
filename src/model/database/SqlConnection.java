package model.database;

import java.sql.*;

public class SqlConnection {
    Connection connection = null;

    //permet de creer la connexion vers la bdd
    public static Connection DbConnector(){
        try {
            //https://bitbucket.org/xerial/sqlite-jdbc/downloads/
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:MemoryGame.db");

            if(!isExist(connect,"Users")){
                Statement smt = connect.createStatement();
                smt.execute("create table Users (id INTEGER primary key AUTOINCREMENT," +
                        "username Text," +
                        "password Text ," +
                        "pointTotal Integer );");
            }

            return connect;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    //verifie si la tabe exist
    public static boolean isExist(Connection connection, String nameTable) throws SQLException {
        boolean existe;

        DatabaseMetaData dmd = connection.getMetaData();
        ResultSet tables = dmd.getTables(connection.getCatalog(),null,nameTable,null);
        existe = tables.next();
        tables.close();

        return existe;
    }

/*public static Connection DbConnector(){
        try {
            //https://bitbucket.org/xerial/sqlite-jdbc/downloads/
            Class.forName("org.sqlite.JDBC");
            Connection connect = DriverManager.getConnection("jdbc:sqlite:MemoryGame.db");
            return connect;
        }
        catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }*/
}
