package StockControlApp;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

        public static Connection con;

        public static void openConnection() {

            try{

                Class.forName("com.mysql.jdbc.Driver");
                con=DriverManager.getConnection("jdbc:mysql://localhost:3306/StockControl?useSSL=false","root","78523");
                //JOptionPane.showMessageDialog(null, "Database connection is opened successfully");
                System.out.println("=== Database connection is opened successfully ===");

            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Connection Failed", "Error",JOptionPane.ERROR_MESSAGE);
                System.out.print(e.getMessage());
                e.printStackTrace();
            }
        }

        public static void closeConnection(){

            try{

                System.out.println("=== Database connection is closed successfully ===");
                con.close();
                con = null;
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null, "Connection not closed", "Error",JOptionPane.ERROR_MESSAGE);
                System.out.print(e.getMessage());
                //e.printStackTrace();
            }

        }

}



