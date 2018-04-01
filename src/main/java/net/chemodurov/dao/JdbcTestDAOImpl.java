package net.chemodurov.dao;

import net.chemodurov.model.Test;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class JdbcTestDAOImpl implements TestDAO {
    private Connection connection;
    private Statement statement;
    private PreparedStatement preparedStatement;
    private static Logger logger = Logger.getLogger(JdbcTestDAOImpl.class.getName());

    public JdbcTestDAOImpl() {
        String dbFileName = "db.sqlite";
        try {
            Class.forName("org.sqlite.JDBC");
            logger.log(Level.INFO, "JDBC driver successful registered.");
        } catch (ClassNotFoundException e) {
            logger.log(Level.WARNING, "Error register JDBC driver.");
            throw new RuntimeException("Error register JDBC driver", e);
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFileName);
            if (connection != null) {
                logger.log(Level.INFO, "connection successful");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public Long getNumberOfRows() {
        String sql = "SELECT COUNT(FIELD) FROM TEST;";
        ResultSet resultSet = null;
        Long n = null;
        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql);
            if (resultSet != null) {
                logger.log(Level.INFO, "Number of rows resultSet is not null.");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "resultSet error.");
            e.printStackTrace();
        }
        try {
            n = Long.parseLong(resultSet.getString(1));
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQL error. Can't get Number of rows: " + e);
            e.printStackTrace();
        }
        if (n != null) {
            logger.log(Level.INFO, "number of rows will be successful returned. Number of rows: " + n);
        }
        return n;
    }

    public void addManyValues(String values) {
        String sql = "INSERT INTO TEST (FIELD) VALUES " + values + ";";
        try {
            preparedStatement = connection.prepareStatement(sql);
            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                logger.log(Level.INFO, "Many values was successful added. Number of rows: " + rows);
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQL error. Can't add many values: " + e);
            e.printStackTrace();
        }
    }

    public void deleteTable() {
        String sql = "DROP TABLE IF EXISTS TEST;";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "Test Table was successful deleted.");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "Delete table error. no such table: TEST. " + e);
        }
    }

    public void createTable() {
        String sql = "CREATE TABLE TEST (FIELD INT(11) PRIMARY KEY);";
        try {
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            logger.log(Level.INFO, "Test Table was successful created.");
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQL error. Can't create test table: " + e);
            e.printStackTrace();
        }

    }

    public ArrayList<Test> getAllField() {
        String sql = "SELECT * FROM TEST;";
        ArrayList<Test> tests = null;
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            tests = new ArrayList<>();
            if (resultSet != null) {
                while (resultSet.next()) {
                    tests.add(new Test(resultSet.getInt("field")));
                }
            } else {
                throw new Exception("Test table is empty!");
            }
            if (!tests.isEmpty()) {
                logger.log(Level.INFO, "List with Test is not empty. This list will be returned.");
            }
        } catch (SQLException e) {
            logger.log(Level.WARNING, "SQL error. Can't create test table: " + e);
            e.printStackTrace();
        } catch (Exception e) {
            logger.log(Level.WARNING, "Test table is empty!");
            e.printStackTrace();
        }
        return tests;
    }
}
