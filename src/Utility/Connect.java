package Utility;

import java.sql.*;

public class Connect {
    private Statement stmt;
    private PreparedStatement pstmt;
    private Connection conn;

    public Connect(){
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost/examoop" , "root","");
            stmt =conn.createStatement();
            System.out.println("Connection is made");
            // Do something with the Connection

        } catch (SQLException | ClassNotFoundException ex) {
            // handle any errors
            ex.printStackTrace();
            System.out.println("Connection is error");
        }
    }

    public ResultSet executedQuery(String query){
        ResultSet rs = null;
        try {
            rs = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("executedQuery is error");
        }
        return rs;
    }

    public ResultSet executeUpdate(String query) {
        try {
            stmt.executeUpdate(query);
            System.out.println("executedUpdate is success");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("executedUpdate is error");
        }
        return null;
    }

}
