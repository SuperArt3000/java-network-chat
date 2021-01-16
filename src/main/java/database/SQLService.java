package database;

import sound.MakeSound;

import javax.swing.*;
import java.sql.*;

/**
 * @author Zurbaevi Nika
 */
public class SQLService {
    public static final SQLService INSTANCE = new SQLService();
    private static final String URL_CONNECTION_DATABASE = "jdbc:sqlite:usersDatabase.db";
    private static final String DRIVER = "org.sqlite.JDBC";
    private static Connection connection;

    private static PreparedStatement preparedStatementGetNicknameByLoginAndPassword;
    private static PreparedStatement preparedStatementGetNickname;
    private static PreparedStatement preparedStatementRegistration;
    private static PreparedStatement preparedStatementChangeNick;

    private SQLService() {
        loadConnection();
        loadDriver();
        prepareAllStatements();
    }

    public static SQLService getINSTANCE() {
        return SQLService.INSTANCE;
    }

    private static void loadConnection() {
        try {
            connection = DriverManager.getConnection(URL_CONNECTION_DATABASE);
        } catch (SQLException e) {
            errorDialogWindow(e.getMessage());
            System.exit(0);
        }
    }

    private static void loadDriver() {
        try {
            Class.forName(DRIVER);
        } catch (Exception e) {
            errorDialogWindow(e.getMessage());
            System.exit(0);
        }
    }

    private static void prepareAllStatements() {
        try {
            preparedStatementGetNicknameByLoginAndPassword = connection.prepareStatement("SELECT Username FROM users WHERE Username = ? AND Password = ?;");
            preparedStatementGetNickname = connection.prepareStatement("SELECT Username FROM users WHERE Username = ?");
            preparedStatementRegistration = connection.prepareStatement("INSERT INTO users (Username, Password) VALUES (?, ?);");
            preparedStatementChangeNick = connection.prepareStatement("UPDATE users SET Username = ? WHERE Username = ?;");
        } catch (SQLException e) {
            errorDialogWindow(e.getMessage());
            System.exit(0);
        }
    }

    public static String getNickname(String nickname) {
        String nick = null;
        try {
            preparedStatementGetNickname.setString(1, nickname);
            ResultSet resultSet = preparedStatementGetNickname.executeQuery();
            if (resultSet.next()) {
                nick = resultSet.getString(1);
            }
            resultSet.close();
        } catch (SQLException e) {
            errorDialogWindow(e.getMessage());
        }
        return nick;
    }

    public static String getNicknameByLoginAndPassword(String login, String password) {
        String nick = null;
        try {
            preparedStatementGetNicknameByLoginAndPassword.setString(1, login);
            preparedStatementGetNicknameByLoginAndPassword.setString(2, password);
            ResultSet rs = preparedStatementGetNicknameByLoginAndPassword.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            errorDialogWindow(e.getMessage());
        }
        return nick;
    }

    public static boolean registration(String nickname, String password) {
        try {
            preparedStatementRegistration.setString(1, nickname);
            preparedStatementRegistration.setString(2, password);
            preparedStatementRegistration.executeUpdate();
            return true;
        } catch (SQLException e) {
            errorDialogWindow(e.getMessage());
            return false;
        }
    }

    public static boolean changeNick(String oldNickname, String newNickname) {
        try {
            preparedStatementChangeNick.setString(1, newNickname);
            preparedStatementChangeNick.setString(2, oldNickname);
            preparedStatementChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void closeConnection() {
        try {
            preparedStatementRegistration.close();
            preparedStatementGetNicknameByLoginAndPassword.close();
            preparedStatementChangeNick.close();
            connection.close();
            connection = null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void errorDialogWindow(String text) {
        MakeSound.playSound("failed.wav");
        JOptionPane.showMessageDialog(null, text, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
