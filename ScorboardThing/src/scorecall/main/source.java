package main;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.servlet.ServletException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;

public class source {


    public static String getSomeBoard(String boardName) {

        ArrayList<UserHelper> users = new ArrayList<>();
        Connection connection = null;
        try {

            connection = DbConnect.connection();

        } catch (ServletException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement("SELECT * FROM Scoreboard");
            ResultSet resultSet = preparedStatement.executeQuery();
            int cnt = 0;
            while (resultSet.next() && cnt < 100) {
                String userID = resultSet.getString("userID");
                long boardNameTypeValue = resultSet.getLong(boardName);
                users.add(new UserHelper(userID, boardNameTypeValue));
                cnt++;
            }
            Collections.sort(users);
            connection.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(users);
    }

    public static boolean deleteUser(String username) {
        boolean flag = false;
        {
            Connection connection = null;
            try {
                connection = DbConnect.connection();
            } catch (ServletException e) {
                e.printStackTrace();
            }

            PreparedStatement preparedStatement = null;

            if(!checkIfExists(username)){
                return false;
            }

            try {

                preparedStatement = connection.prepareStatement("DELETE FROM Scoreboard WHERE userID = ?");
                preparedStatement.setString(1, username);
                preparedStatement.executeUpdate();
                connection.close();
                flag = true;
            } catch (SQLException ex) {
                System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
            }

        }
        return flag;
    }

    private static void addNewUserToDataBase(User user) {

        Connection connection = null;
        try {

            connection = DbConnect.connection();

        } catch (ServletException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement("INSERT INTO Scoreboard(userID, score, wins, kills, timePlayed)VALUES(?,?,?,?,?)");

            preparedStatement.setString(1, user.userID);
            preparedStatement.setDouble(2, user.score);
            preparedStatement.setDouble(3, user.wins);
            preparedStatement.setDouble(4, user.kills);
            preparedStatement.setDouble(5, user.timePlayed);

            preparedStatement.executeUpdate();

            connection.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
    }

    public static boolean checkIfExists(String username) {

        Connection connection = null;
        try {
            connection = DbConnect.connection();
        } catch (ServletException e) {
            e.printStackTrace();
        }

        PreparedStatement preparedStatement = null;

        try {

            preparedStatement = connection.prepareStatement("SELECT userID FROM Scoreboard WHERE userID = ?");
            preparedStatement.setString(1, username);
            ResultSet resultSet = preparedStatement.executeQuery();
            String id="";
            if(resultSet.next()) {
                 id = resultSet.getString("userID");
            }
            connection.close();

            if (id.equals(username)) {
                return true;
            }
        } catch (SQLException ex) {
            System.out.println("Check failed (SQL CREATE STATEMENT FAILED");
        }
        return false;
    }


    public static boolean registerGame(User user) {
        boolean flag = true;
        Connection connection = null;
        try {

            connection = DbConnect.connection();

        } catch (ServletException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
        }
        PreparedStatement preparedStatement = null;

        try {


            if (checkIfExists(user.userID)) {
                preparedStatement = connection.prepareStatement("SELECT * FROM Scoreboard WHERE userID = ?");
                preparedStatement.setString(1, user.userID);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                String id = resultSet.getString("userID");
                double score = resultSet.getDouble("score") + user.score;
                double wins = resultSet.getDouble("wins") + user.wins;
                double kills = resultSet.getDouble("kills") + user.kills;
                double timePlayed = resultSet.getDouble("timePlayed") + user.timePlayed;
                preparedStatement = connection.prepareStatement("UPDATE Scoreboard SET score = ? , wins = ? , kills = ? , timePlayed = ? WHERE userID = ?");
                preparedStatement.setDouble(1, score);
                preparedStatement.setDouble(2, wins);
                preparedStatement.setDouble(3, kills);
                preparedStatement.setDouble(4, timePlayed);
                preparedStatement.setString(5, id);
                preparedStatement.executeUpdate();
            } else {
                preparedStatement = connection.prepareStatement("INSERT INTO Scoreboard(userID, score, wins, kills, timePlayed)VALUES(?,?,?,?,?)");

                preparedStatement.setString(1, user.userID);
                preparedStatement.setDouble(2, user.score);
                preparedStatement.setDouble(3, user.wins);
                preparedStatement.setDouble(4, user.kills);
                preparedStatement.setDouble(5, user.timePlayed);
                preparedStatement.executeUpdate();

            }

            connection.close();
        } catch (SQLException ex) {
            System.out.println("SQL Error: " + ex.getMessage());
            flag = false;
        }
        return flag;
    }
}