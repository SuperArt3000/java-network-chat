package database;

import java.sql.*;

public class SQLService {
    private static Connection connection;
    private static PreparedStatement preparedStatementGetNicknameByLoginAndPassword;
    private static PreparedStatement preparedStatementGetNickname;
    private static PreparedStatement preparedStatementRegistration;
    private static PreparedStatement preparedStatementChangeNick;

    private static final String urlConnectionDatabase = "jdbc:sqlite:usersDatabase.db";

    public static boolean connect() {
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(urlConnectionDatabase);
            prepareAllStatements();
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void prepareAllStatements() throws SQLException {
        preparedStatementGetNicknameByLoginAndPassword = connection.prepareStatement("SELECT Username FROM users WHERE Username = ? AND Password = ?;");
        preparedStatementGetNickname = connection.prepareStatement("SELECT Username FROM users WHERE Username = ?");
        preparedStatementRegistration = connection.prepareStatement("INSERT INTO users (Username, Password) VALUES (?, ?);");
        preparedStatementChangeNick = connection.prepareStatement("UPDATE users SET Username = ? WHERE Username = ?;");
    }

    public static String getNickname(String nickname) {
        String nick = null;
        try {
            preparedStatementGetNickname.setString(1, nickname);
            ResultSet rs = preparedStatementGetNickname.executeQuery();
            if (rs.next()) {
                nick = rs.getString(1);
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
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
            e.printStackTrace();
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
            e.printStackTrace();
            return false;
        }
    }

    public static boolean changeNick(String oldNick, String newNick) {
        try {
            preparedStatementChangeNick.setString(1, newNick);
            preparedStatementChangeNick.setString(2, oldNick);
            preparedStatementChangeNick.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void disconnect() {
        try {
            preparedStatementRegistration.close();
            preparedStatementGetNicknameByLoginAndPassword.close();
            preparedStatementChangeNick.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
