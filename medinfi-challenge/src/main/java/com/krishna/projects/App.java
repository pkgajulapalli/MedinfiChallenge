package com.krishna.projects;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class App 
{
    public static void main( String[] args )
    {
        try {
            createEmployeeTable();
        } catch ( Exception e ) {
            System.out.println("Table exists");
        } finally {
            addEmployees();
            printEmployees();
        }
    }

    static void createEmployeeTable() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "CREATE TABLE EMPLOYEE " +
                    "(ID INT PRIMARY KEY     NOT NULL," +
                    " NAME           TEXT    NOT NULL, " +
                    " AGE            INT     NOT NULL, " +
                    " SALARY         REAL)";
            stmt.executeUpdate(sql);
            stmt.close();
            c.close();
            System.out.println("Table created successfully");
        } catch ( Exception e ) {
            System.out.println("EMPLOYEE TABLE EXISTS");
        }
    }

    static void addEmployees() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (1, 'Paul', 32, 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (2, 'Allen', 25, 15000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (3, 'Teddy', 23, 20000.00 );";
            stmt.executeUpdate(sql);

            sql = "INSERT INTO EMPLOYEE (ID,NAME,AGE,SALARY) " +
                    "VALUES (4, 'Mark', 25, 65000.00 );";
            stmt.executeUpdate(sql);

            stmt.close();
            c.commit();
            c.close();
        } catch ( Exception e ) {

        }
        System.out.println("Records created successfully");
    }

    static void printEmployees() {
        Connection c = null;
        Statement stmt = null;

        try {
            Class.forName("org.sqlite.JDBC");
            c = DriverManager.getConnection("jdbc:sqlite:company.db");
            c.setAutoCommit(false);
            System.out.println("Opened database successfully");

            stmt = c.createStatement();
            String sql = "DELETE from EMPLOYEE where ID=2;";
            stmt.executeUpdate(sql);
            c.commit();

            ResultSet rs = stmt.executeQuery( "SELECT * FROM EMPLOYEE;" );

            while ( rs.next() ) {
                int id = rs.getInt("id");
                String  name = rs.getString("name");
                int age  = rs.getInt("age");
                float salary = rs.getFloat("salary");

                System.out.println( "ID = " + id );
                System.out.println( "NAME = " + name );
                System.out.println( "AGE = " + age );
                System.out.println( "SALARY = " + salary );
                System.out.println();
            }
            rs.close();
            stmt.close();
            c.close();
        } catch ( Exception e ) {
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
            System.exit(0);
        }
    }
}
