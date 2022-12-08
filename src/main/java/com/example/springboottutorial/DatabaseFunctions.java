package com.example.springboottutorial;

import java.sql.*;

/**
 * @author Steven F. Daniel
 */

public class DatabaseFunctions {

    public Connection makeConnection(String fileName) throws Exception {
        String url = "jdbc:sqlite:db/" + fileName;

        // See if we can at lease attempt to get a connection to our database
        Connection conn = DriverManager.getConnection(url);
        DatabaseMetaData meta = conn.getMetaData();

        System.out.println("The driver name is " + meta.getDriverName());
        System.out.println("Connection to SQLite has been established.");

        // Success, so return the connection object
        return conn;
    }

    /**
     * Get all rows in the Customers table
     */
    void selectAll(Connection conn) {
        String sql = "SELECT CustomerId, FirstName, LastName FROM customers";
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt("CustomerId") + "\t" +
                        rs.getString("FirstName") + "\t" +
                        rs.getString("LastName"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Gets a specific row from a particular table
     */
    void selectFromTable(Connection conn) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ArtistId, Name FROM artists");

            // loop through the result set
            while (rs.next()) {
                System.out.println(rs.getInt(1) + "\t" +
                        rs.getString(2));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Create a new table in the test database
     */
    void createNewTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = """
                CREATE TABLE IF NOT EXISTS warehouses (
                	id integer PRIMARY KEY,
                	name text NOT NULL,
                	capacity real
                );""";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Insert a new row into the warehouses table
     */
    void insert(Connection conn, String name, double capacity) {
        String sql = "INSERT INTO warehouses(name,capacity) VALUES(?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, name);
            stmt.setDouble(2, capacity);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Drops the warehouses table in the test database
     */
    void dropDatabaseTable(Connection conn) {

        // SQL statement for creating a new table
        String sql = """
                DROP TABLE IF EXISTS warehouses;""";

        try {
            Statement stmt = conn.createStatement();
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}