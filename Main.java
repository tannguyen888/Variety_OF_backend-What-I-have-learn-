package com.example;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static String url = "jdbc:mysql://localhost:3306/testDb?serverTimezone=UTC";
    static String user = "root";
    static String password = "";

    public static void main(String[] args) {
        Connection conn = connectToDatabase(url, user, password);
        if (conn == null)
            return;

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Add user");
            System.out.println("2. Get users");
            System.out.println("3. Update user");
            System.out.println("4. Delete user");
            System.out.println("5. Exit");

            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine(); // clear buffer

            switch (choice) {
                case 1:
                    try {
                        handleAddUser(conn, sc);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 2:
                    try {
                        handleGetUser(conn);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 3:
                    try {
                        handleUpdateUser(conn, sc);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case 4:
                    try {
                        handleDeleteUser(conn, sc);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    try {
                        conn.close();
                    } catch (SQLException e) {
                        System.out.println("Error closing connection: " + e.getMessage());
                    }
                    return; // Thoát khỏi chương trình hoàn toàn
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static Connection connectToDatabase(String url, String user, String password) {
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            System.out.println(" Connected to MySQL!");
            return connection;
        } catch (Exception e) {
            System.out.println(" DB Error: " + e.getMessage());
            return null;
        }
    }

    // CREATE
    private static void handleAddUser(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter name: ");
        String name = sc.nextLine();

        System.out.print("Enter email: ");
        String email = sc.nextLine();

        String query = "INSERT INTO mockUserList (name, email) VALUES (?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setString(2, email);
        stmt.executeUpdate();

        System.out.println(" User added!");
    }

    // READ
    private static void handleGetUser(Connection conn) throws SQLException {
        String query = "SELECT * FROM mockUserList";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        while (rs.next()) {
            System.out.println(
                    rs.getInt("id") + " | " +
                            rs.getString("name") + " | " +
                            rs.getString("email"));
        }
    }

    // UPDATE
    private static void handleUpdateUser(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter new name: ");
        String name = sc.nextLine();

        String query = "UPDATE mockUserList SET name=? WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, name);
        stmt.setInt(2, id);
        stmt.executeUpdate();

        System.out.println(" Updated!");
    }

    // DELETE
    private static void handleDeleteUser(Connection conn, Scanner sc) throws SQLException {
        System.out.print("Enter ID: ");
        int id = sc.nextInt();

        String query = "DELETE FROM mockUserList WHERE id=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, id);
        stmt.executeUpdate();

        System.out.println(" Deleted!");
    }
}