package StockControlApp;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class CheckUser {

        private static PreparedStatement st;
        private Database connection = new Database();

        public Boolean IsUserAvailabel(String username) {

            Boolean isAvailabel = false;

            try {

                st = Database.getCon().prepareStatement("SELECT * FROM Users WHERE Name= ?");
                st.setString(1,username);

                ResultSet result = st.executeQuery();

                if(result.next()){

                    isAvailabel = true;
                    JOptionPane.showMessageDialog(null,result.getString("Name") + " is exist. You can not create account with same name.","Error", JOptionPane.ERROR_MESSAGE );

                } else {

                    isAvailabel = false;

                }

                result.close();
                st.close();

            }catch(Exception e){
                JOptionPane.showMessageDialog(null, "Records cannot return from the database", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.print(e.getMessage());
                e.printStackTrace();
            }

            return isAvailabel;
        }


}
