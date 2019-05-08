package StockControlApp;

import javax.swing.*;
import java.awt.*;

public class deneme extends JFrame {



    public deneme(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setTitle("Akyol Soft ~ Stock Control System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // The background is setted up
        JLabel background=new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/background-images-for-login-form.jpg"));
        background.setLayout(new FlowLayout());
        add(background);

        JPanel main = new JPanel();


    }

    public static void main(String[] args) {

        new deneme();
    }
}
