package StockControlApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Login extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;

    JLabel Clickonit = new JLabel("Enter as a guest");
    JLabel username = new JLabel("Username");
    JLabel password = new JLabel("Password ");
    JTextField usernameText = new JTextField(10);
    JPasswordField passwordText = new JPasswordField(10);
    JButton btnLogin = new JButton("Login");
    JButton btnClean = new JButton("Clear");

    JPanel panelHeader = new JPanel();
    JPanel panelLeft = new JPanel();
    JPanel panelCenter = new JPanel();
    JPanel panelRight = new JPanel();
    JPanel panelBottom = new JPanel();

    public Login() { initComponents(); }

    public void initComponents(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(380, 420);
        setTitle("Akyol Soft ~ Stock Control System");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        JLabel background=new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/background-images-for-login-form.jpg"));
        add(background);
        background.setLayout(new BorderLayout());

        btnLogin.setPreferredSize(new Dimension(75,20));
        btnLogin.setBackground(new Color(118, 210, 126));
        btnLogin.setForeground(Color.WHITE);
        btnClean.setPreferredSize(new Dimension(75,20));
        btnClean.setBackground(new Color(242, 38, 19));
        btnClean.setForeground(Color.WHITE);

        panelHeader.setPreferredSize(new Dimension(400,100));
        panelHeader.setBackground(new Color(0,0,0,0));

        panelLeft.setPreferredSize(new Dimension(75,200));
        panelLeft.setBackground(new Color(0,0,0,0));

        panelCenter.setPreferredSize(new Dimension(250,200));
        panelCenter.setBackground(new Color(0,0,0,50));
        panelCenter.setLayout(new FlowLayout());

        panelRight.setPreferredSize(new Dimension(75,200));
        panelRight.setBackground(new Color(0,0,0,0));

        panelBottom.setPreferredSize(new Dimension(400,100));
        panelBottom.setBackground(new Color(0,0,0,0));
        JLabel welcome = new JLabel("           Login Panel          ");
        welcome.setForeground(new Color(40, 183, 245,255));
        username.setForeground(new Color(40, 183, 245,255));
        password.setForeground(new Color(40, 183, 245,255));

        panelCenter.add(new JLabel("<html><body><br><br><br></body></html>"));
        panelCenter.add(welcome);
        panelCenter.add(username);
        panelCenter.add(usernameText);
        panelCenter.add(password);
        panelCenter.add(passwordText);
        panelCenter.add(btnLogin);
        panelCenter.add(btnClean);

        Clickonit.setForeground(new Color(242, 128, 44, 255));
        panelCenter.add(Clickonit);

        background.add(panelHeader,BorderLayout.NORTH);
        background.add(panelLeft,BorderLayout.WEST);
        background.add(panelCenter,BorderLayout.CENTER);
        background.add(panelRight,BorderLayout.EAST);
        background.add(panelBottom,BorderLayout.SOUTH);

        setResizable(false);
        setVisible(true);

        btnLogin.addActionListener(this);
        btnClean.addActionListener(this);

        Clickonit.addMouseListener(new MouseAdapter()   {

            @Override
            public void mouseClicked(MouseEvent e)
            {
                Showroom showroom = new Showroom();
                showroom.setVisible(true);
                dispose();
            }
        });
    }

    public static void main(String[] args) {

        Database database = new Database();
        new Login();
        database.openConnection();

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if(e.getSource() == btnLogin) {
            String username = usernameText.getText();
            String password = passwordText.getText();

            LoginCheck function = new LoginCheck();

            if (function.LoginCheck(username,password) == "User"){

                this.dispose();
                Showroom showroom = new Showroom();
                showroom.setVisible(true);

            }else if(function.LoginCheck(username,password) == "Admin"){

                this.dispose();
                Admin admin = new Admin();
                admin.setVisible(true);

            }else {

                JOptionPane.showMessageDialog(null, "Your username or password are incorrect","Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        else if(e.getSource() == btnClean) {
            usernameText.setText("");
            passwordText.setText("");
        }
    }
}
