package StockControlApp;

import javax.swing.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginCheck {

    private static PreparedStatement st;
    private Database connection = new Database();

    public String LoginCheck(String username, String password) {

        String loginType = "Fault";

        try {

            st = Database.getCon().prepareStatement(connection.getLoginCheck());
            st.setString(1,username);
            st.setString(2,password);

            ResultSet result = st.executeQuery();

            if(result.next()){

                if (result.getString("Name").equals("root")){

                    loginType = "Admin";

                } else {

                    loginType = "User";

                }

            }

            result.close();
            st.close();

        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Records cannot return from the database", "Error", JOptionPane.ERROR_MESSAGE);
            System.out.print(e.getMessage());
            e.printStackTrace();
        }

        return loginType;

    }
}
