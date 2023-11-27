package com.login_example;

import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DAO {
    static String dbPath = Paths.get("db/user_db.sqlite").toString();

    /**
     * Connect to the test.sqlite database
     *
     * @return the Connection object
     */
    private static Connection connect() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + dbPath;
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }

    /**
     * Connect to a sample database
     *
     * @param fileName the database file name
     */
    public static void createNewDatabase() {

        String url = "jdbc:sqlite:" + dbPath;

        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                DatabaseMetaData meta = conn.getMetaData();
                System.out.println("The driver name is " + meta.getDriverName());
                System.out.println("A new database has been created.");
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     *
     */
    public static void createNewTable() {
        // SQLite connection string
        String url = "jdbc:sqlite:" + dbPath;

        // SQL statement for creating a new table
        String sql = "CREATE TABLE IF NOT EXISTS users (\n"
                + "	id integer PRIMARY KEY AUTOINCREMENT,\n"
                + "	name text NOT NULL,\n"
                + "	password text NOT NULL\n"
                + ");";

        try (Connection conn = DriverManager.getConnection(url);
                Statement stmt = conn.createStatement()) {
            // create a new table
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * retrieve a row from the users table based on name and password
     *
     * @param name
     * @param password
     */
    /* TODO: check if username already exists and block operation if so */
    public static int insert(String name, String password) {
        String sql = "INSERT INTO users(name, password) VALUES(?,?);";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            pstmt.executeUpdate();

            /* closing connection */
            try {
                if (conn != null) {
                    conn.close();
                }
                return 1;
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                return 0;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return 0;
        }
    }

    /**
     * Insert a new row into the users table
     *
     * @param name
     * @param password
     */
    public static String[] select(String name, String password) {
        String sql = "SELECT name, password FROM users WHERE name = ? AND password = ?";

        try (Connection conn = connect();
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();

            // loop through the result set
            rs.next();
            String fetched_name = rs.getString("name");
            String fetched_password = rs.getString("password");
            String[] result = new String[] { fetched_name, fetched_password };
            return result;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    /**
     * select all rows in the users table
     */
    public static String selectAll() {
        String sql = "SELECT name, password FROM users";
        StringBuilder result = new StringBuilder();
        try (Connection conn = connect();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // loop through the result set
            while (rs.next()) {
                String currentRow = "Name: " + rs.getString("name") + "\n" +
                        "Password: " + rs.getString("password") + "\n\n";
                System.out.println(currentRow);
                result.append(currentRow);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return result.toString();
    }
}