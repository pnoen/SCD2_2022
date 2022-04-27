package oxfordDictionaries.model.request;

import org.sqlite.SQLiteConfig;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SQLDatabase {
    private static final String dbName = "OxfordDictionary.db";
    private static final String dbURL = "jdbc:sqlite:" + dbName;

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


}
