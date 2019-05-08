package StockControlApp;

import javax.swing.*;
import java.sql.*;

public class Database {

        private static Connection con;
        private Statement statement = null;
        private ResultSet NumberOfRecord = null;
        private ResultSet ProductList = null;
        private String update = "update Products set ProductLeft = ? where ProductID = ?";
        private String LoginCheck = "SELECT * FROM Users WHERE Name= ? AND Password= ?";


    public static void openConnection() {

            try{

                Class.forName("com.mysql.jdbc.Driver");
                setCon(DriverManager.getConnection("jdbc:mysql://localhost:3306/StockControl?useSSL=false","root","78523"));
                //JOptionPane.showMessageDialog(null, "Database connection is opened successfully");
                System.out.println("=== Database connection is opened successfully ===");

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Connection Failed", "Error",JOptionPane.ERROR_MESSAGE);
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }

    public static Connection getCon() {
        return con;
    }

    public static void setCon(Connection con) {
        Database.con = con;
    }

    public ResultSet NumberOfRecord(){

        try {

            statement = getCon().createStatement();
            NumberOfRecord = statement.executeQuery("select count(*) from Products");
            return NumberOfRecord;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ResultSet ProductList(){

        try {

            statement = getCon().createStatement();
            ProductList = statement.executeQuery("select * from Products");
            return ProductList;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

        public static void closeConnection(){

            try{

                System.out.println("=== Database connection is closed successfully ===");
                getCon().close();
                setCon(null);
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Connection not closed", "Error",JOptionPane.ERROR_MESSAGE);
                System.out.print(e.getMessage());
                //e.printStackTrace();
            }

        }

    public String getUpdate() {
        return update;
    }

    public void setUpdate(String update) {
        this.update = update;
    }

    public String getLoginCheck() {
        return LoginCheck;
    }

    public void setLoginCheck(String loginCheck) {
        LoginCheck = loginCheck;
    }
}



