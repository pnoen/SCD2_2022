package oxforddictionaries.model.request;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Database which uses SQLite and prevent the user from making repeated requests to the api.
 */
public class SqlDatabase {
    private static final String dbName = "OxfordDictionary.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

    /**
     * Creates the entry and lemma tables if they don't exist. Return null if there are no errors.
     * @return error message
     */
    public String setupDB() {
        String error = null;
        String createEntriesTableSQL =
                """
                CREATE TABLE IF NOT EXISTS entries (
                    uri text PRIMARY KEY,
                    json text NOT NULL,
                    code integer NOT NULL
                );
                """;

        String createLemmasTableSQL =
                """
                CREATE TABLE IF NOT EXISTS lemmas (
                    uri text PRIMARY KEY,
                    json text NOT NULL,
                    code integer NOT NULL
                );
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             Statement statement = conn.createStatement()) {
            statement.execute(createEntriesTableSQL);
            statement.execute(createLemmasTableSQL);

//            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }

    /**
     * Adds the entry to the database. Return null if there are no errors.
     * @param uri uri
     * @param json json
     * @param code status code
     * @return error message
     */
    public String addEntry(String uri, String json, int code) {
        String addEntry =
                """
                INSERT INTO entries(uri, json, code) VALUES
                    (?, ?, ?)
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(addEntry)) {
            preparedStatement.setString(1, uri);
            preparedStatement.setString(2, json);
            preparedStatement.setInt(3, code);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return null;
    }

    /**
     * Adds the lemma to the database. Return null if there are no errors.
     * @param uri uri
     * @param json json
     * @param code status code
     * @return error message
     */
    public String addLemma(String uri, String json, int code) {
        String addLemma =
                """
                INSERT INTO lemmas(uri, json, code) VALUES
                    (?, ?, ?)
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(addLemma)) {
            preparedStatement.setString(1, uri);
            preparedStatement.setString(2, json);
            preparedStatement.setInt(3, code);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return e.getMessage();
        }

        return null;
    }

    /**
     * Gets the entry from the database.
     * @param uri uri
     * @return list of results or errors
     */
    public List<String> getEntry(String uri) {
        List<String> entry = new ArrayList<>();

        String getEntry =
                """
                SELECT *
                FROM entries
                WHERE uri = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(getEntry)) {
            preparedStatement.setString(1, uri);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                entry.add(String.valueOf(results.getInt("code")));
                entry.add(results.getString("json"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            entry.clear();
            entry.add(e.getMessage());
        }

        return entry;
    }

    /**
     * Gets the lemma from the database.
     * @param uri uri
     * @return list of results or errors
     */
    public List<String> getLemma(String uri) {
        List<String> lemma = new ArrayList<>();

        String getLemma =
                """
                SELECT *
                FROM lemmas
                WHERE uri = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(getLemma)) {
            preparedStatement.setString(1, uri);
            ResultSet results = preparedStatement.executeQuery();

            while (results.next()) {
                lemma.add(String.valueOf(results.getInt("code")));
                lemma.add(results.getString("json"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            lemma.clear();
            lemma.add(e.getMessage());
        }

        return lemma;
    }

    /**
     * Drops the tables and recreates them. Return null if there are no errors.
     * @return error message
     */
    public String clearDatabase() {
        String error = null;
        String dropEntriesTableSQL =
                """
                DROP TABLE entries;
                """;

        String dropLemmasTableSQL =
                """
                DROP TABLE lemmas;
                """;

        String createEntriesTableSQL =
                """
                CREATE TABLE IF NOT EXISTS entries (
                    uri text PRIMARY KEY,
                    json text NOT NULL,
                    code integer NOT NULL
                );
                """;

        String createLemmasTableSQL =
                """
                CREATE TABLE IF NOT EXISTS lemmas (
                    uri text PRIMARY KEY,
                    json text NOT NULL,
                    code integer NOT NULL
                );
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             Statement statement = conn.createStatement()) {
            statement.execute(dropEntriesTableSQL);
            statement.execute(dropLemmasTableSQL);
            statement.execute(createEntriesTableSQL);
            statement.execute(createLemmasTableSQL);

//            System.out.println("Created tables");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }

    /**
     * Updates the entry in the table with the new data
     * @param uri uri
     * @param json json
     * @param code status code
     * @return error message
     */
    public String updateEntry(String uri, String json, int code) {
        String error = null;
        String updateState =
                """
                UPDATE entries
                SET json = ?, code = ?
                WHERE uri = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(updateState)) {
            preparedStatement.setString(1, json);
            preparedStatement.setInt(2, code);
            preparedStatement.setString(3, uri);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }

    /**
     * Updates the lemma in the table with the new data
     * @param uri uri
     * @param json json
     * @param code status code
     * @return error message
     */
    public String updateLemma(String uri, String json, int code) {
        String error = null;
        String updateState =
                """
                UPDATE lemmas
                SET json = ?, code = ?
                WHERE uri = ?
                """;

        SQLiteConfig config = new SQLiteConfig();
        config.enforceForeignKeys(true);

        try (Connection conn = DriverManager.getConnection(dbURL, config.toProperties());
             PreparedStatement preparedStatement = conn.prepareStatement(updateState)) {
            preparedStatement.setString(1, json);
            preparedStatement.setInt(2, code);
            preparedStatement.setString(3, uri);
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            error = e.getMessage();
        }
        return error;
    }


}
