package au.edu.sydney.soft3202.task3.model;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase {
    private static final String dbName = "saveData.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

    public String setupDB() {
        String error = null;
        String createUsersTableSQL =
                """
                CREATE TABLE IF NOT EXISTS users (
                    id integer PRIMARY KEY,
                    name text NOT NULL
                );
                """;

        String createSaveDataTableSQL =
                """
                CREATE TABLE IF NOT EXISTS saves (
                    name text NOT NULL,
                    state text NOT NULL,
                    user_id integer NOT NULL,
                    PRIMARY KEY (name, user_id),
                    FOREIGN KEY (user_id)
                        REFERENCES users (id)
                            ON DELETE CASCADE
                );
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             Statement statement = conn.createStatement()) {
            statement.execute(createUsersTableSQL);
            statement.execute(createSaveDataTableSQL);

//            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }

    public int addUser(String username) {
        String addUser =
                """
                INSERT INTO users(name) VALUES
                    (?)
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(addUser)) {
            preparedStatement.setString(1, username);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }

        return getLastId();
    }

    public int getLastId() {
        int id = -1;

        String lastId =
                """
                SELECT count(id)
                FROM users
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(lastId)) {
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                id = results.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return -1;
        }
        return id;
    }

    public String addSave(String saveName, String state, int id) {
        String error = null;
        String addSave =
                """
                INSERT INTO saves(name, state, user_id) VALUES
                    (?, ?, ?)
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(addSave)) {
            preparedStatement.setString(1, saveName);
            preparedStatement.setString(2, state);
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }

    public List<String> getUserSaveNames(int id) {
        List<String> names = new ArrayList<>();

        String getNames =
                """
                SELECT s.name
                FROM saves AS s
                INNER JOIN users AS u ON s.user_id = u.id
                WHERE u.id = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(getNames)) {
            preparedStatement.setInt(1, id);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                names.add(results.getString("name"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            names.clear();
            names.add(null);
            names.add(e.getMessage());
        }

        return names;
    }

    public List<String> getState(String name, int id) {
        List<String> state = new ArrayList<>();

        String getState =
                """
                SELECT s.state
                FROM saves AS s
                INNER JOIN users AS u ON s.user_id = u.id
                WHERE u.id = ? AND s.name = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(getState)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                state.add(results.getString("state"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            state.clear();
            state.add(null);
            state.add(e.getMessage());
        }

        return state;
    }

    public String updateState(String saveName, String state, int id) {
        String error = null;
        String updateState =
                """
                UPDATE saves
                SET state = ?
                WHERE user_id = ? AND name = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(updateState)) {
            preparedStatement.setString(1, state);
            preparedStatement.setInt(2, id);
            preparedStatement.setString(3, saveName);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }
}
