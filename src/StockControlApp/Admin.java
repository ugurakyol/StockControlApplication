package StockControlApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    private static PreparedStatement st;
    private Database connection = new Database();

    private JLabel title = new JLabel("Admin Panel");
    private java.util.List<Users> UsersList = new ArrayList<>();
    private java.util.List<Product> ListOfProduct = new ArrayList<>();

    private JButton btnBack;
    private JButton StocksBtn;
    private JButton UsersBtn;

    JTextField UserIDText = new JTextField(10);
    JTextField UserNameText = new JTextField(10);
    JTextField UserSurnameText = new JTextField(10);
    JTextField PasswordText = new JTextField(10);
    JTextField PasswordConfirmText = new JTextField(10);
    JButton SignUpBtn = new JButton("Sign Up");
    JButton CleanFormBtn = new JButton(" Clear ");

    private JPanel panel1;
    private JPanel right;
    private JPanel left;
    private JPanel top;
    private JPanel bottom;
    private JPanel Center;
    private JPanel Users;
    private JPanel Stocks;
    private JPanel UserForm;
    private JPanel UsersPanel;



    Admin(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        setSize(screenSize.width, screenSize.height);
        setTitle("Akyol Soft ~ Stock Control System");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        // The background is setted up
        JLabel background=new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/background-images-for-login-form.jpg"));
        background.setLayout(new FlowLayout());
        add(background);
        panel1.setVisible(true);
        background.add(panel1);
        panel1.setPreferredSize(new Dimension(screenSize.width, screenSize.height));
        title.setFont(new Font("Serif", Font.PLAIN, 24));
        title.setForeground(Color.WHITE);
        Stocks.setVisible(false);

        btnBack.setPreferredSize(new Dimension(100,20));
        btnBack.setBackground(new java.awt.Color(189,51,51));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this::actionPerformed);

        StocksBtn.setPreferredSize(new Dimension(100,20));
        StocksBtn.setBackground(new java.awt.Color(94, 189, 86));
        StocksBtn.setForeground(Color.WHITE);
        StocksBtn.addActionListener(this::actionPerformed);

        UsersBtn.setPreferredSize(new Dimension(100,20));
        UsersBtn.setBackground(new java.awt.Color(94, 189, 86));
        UsersBtn.setForeground(Color.WHITE);
        UsersBtn.addActionListener(this::actionPerformed);
        UsersBtn.setVisible(false);

        SignUpBtn.setPreferredSize(new Dimension(125,40));
        SignUpBtn.setBackground(new java.awt.Color(94, 189, 86));
        SignUpBtn.setForeground(Color.WHITE);
        SignUpBtn.addActionListener(this::actionPerformed);

        CleanFormBtn.setPreferredSize(new Dimension(125,40));
        CleanFormBtn.setBackground(new java.awt.Color(189,51,51));
        CleanFormBtn.setForeground(Color.WHITE);
        CleanFormBtn.addActionListener(this::actionPerformed);

        JPanel Form = new JPanel();
        Form.setBackground(new Color(0,0,0,50));
        Form.setLayout(new GridLayout(7,1));

        JPanel UserName = new JPanel();
        UserName.setBackground(new Color(0,0,0,0));
        UserName.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel NameLabel = new JLabel("USER NAME");
        NameLabel.setForeground(Color.WHITE);
        UserName.add(NameLabel);
        UserName.add(UserNameText);

        JPanel UserSurname = new JPanel();
        UserSurname.setBackground(new Color(0, 50, 67,50));
        UserSurname.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel SurnameLabel = new JLabel("USER SURNAME");
        SurnameLabel.setForeground(Color.WHITE);
        UserSurname.add(SurnameLabel);
        UserSurname.add(UserSurnameText);

        JPanel Password = new JPanel();
        Password.setBackground(new Color(0,0,0,0));
        Password.setLayout(new FlowLayout(FlowLayout.RIGHT));
        JLabel PasswordLabel = new JLabel("PASSWORD");
        PasswordLabel.setForeground(Color.WHITE);
        Password.add(PasswordLabel);
        Password.add(PasswordText);

        JPanel PasswordConfirm = new JPanel();
        PasswordConfirm.setBackground(new Color(0, 50, 67,50));
        PasswordConfirm.setLayout(new FlowLayout(FlowLayout.CENTER));
        JLabel ConfirmLabel = new JLabel("CONFIRM PASSWORD");
        ConfirmLabel.setForeground(Color.WHITE);
        PasswordConfirm.add(ConfirmLabel);
        PasswordConfirm.add(PasswordConfirmText);

        JPanel FormButtons = new JPanel();
        FormButtons.setBackground(new Color(0,0,0,0));
        FormButtons.add(SignUpBtn);
        FormButtons.add(CleanFormBtn);

        Form.add(new JLabel(""));
        Form.add(UserName);
        Form.add(UserSurname);
        Form.add(Password);
        Form.add(PasswordConfirm);
        Form.add(FormButtons);
        UserForm.setLayout(new FlowLayout());
        UserForm.add(Form);
        BringUsers();



    }

    public void actionPerformed(ActionEvent e){

        if(e.getSource() == btnBack) {
            Login log = new Login();
            log.setVisible(true);
            this.dispose();
        }
        if(e.getSource() == UsersBtn) {
            //Users.removeAll();
            Users.setVisible(true);
            StocksBtn.setVisible(true);
            UsersBtn.setVisible(false);
            Stocks.setVisible(false);
        }
        if(e.getSource() == StocksBtn) {

            Stocks.removeAll();
            Users.setVisible(false);
            StocksBtn.setVisible(false);
            UsersBtn.setVisible(true);
            Stocks.setVisible(true);
            BringStocks();
        }
        if(e.getSource() == SignUpBtn) {

            if(UserNameText.getText().isEmpty()){ JOptionPane.showMessageDialog(null,"User name is necessary!");}
            else if(UserSurnameText.getText().isEmpty()){ JOptionPane.showMessageDialog(null,"User surname is necessary!");}
            else if(PasswordText.getText().isEmpty()){ JOptionPane.showMessageDialog(null,"Password is not valid");}
            else if(PasswordConfirmText.getText().isEmpty()){ JOptionPane.showMessageDialog(null,"Confirm is not valid");}
            else if(!(PasswordText.getText().equals(PasswordConfirmText.getText()))){ JOptionPane.showMessageDialog(null,"Password is not confirmed");}
            else {

                CheckUser checkUser = new CheckUser();
                if(!(checkUser.IsUserAvailabel(UserNameText.getText()))){

                    try {

                        st = Database.getCon().prepareStatement("INSERT INTO Users (Name, Surname, Password) VALUES (?, ?, ?)");
                        st.setString(1,UserNameText.getText());
                        st.setString(2,UserSurnameText.getText());
                        st.setString(3,PasswordText.getText());
                        st.executeUpdate();

                        JOptionPane.showMessageDialog(null,"The account is created successfully");

                    }catch(SQLException e1){
                        JOptionPane.showMessageDialog(null, "Could not record to database. Please, try again.", "Error", JOptionPane.ERROR_MESSAGE);
                        System.out.print(e1.getMessage());
                        e1.printStackTrace();
                    }


                }


            }


        }
        if(e.getSource() == CleanFormBtn) {

            UserIDText.setText("");
            UserNameText.setText("");
            UserSurnameText.setText("");
            PasswordText.setText("");
            PasswordConfirmText.setText("");
        }
    }

    private void BringUsers(){

        try{

            Statement statement = Database.getCon().createStatement();
            ResultSet rsNumberOfRecord = statement.executeQuery("select count(*) from Users");

            int rows =0;
            while (rsNumberOfRecord.next()){
                rows = rsNumberOfRecord.getInt(1);
            }

            System.out.println("===========" + rows +" Records are founded. ============");
            rsNumberOfRecord.close();

            UsersPanel.setLayout(new GridLayout(rows+2,1));
            JPanel [] RowPanel = new JPanel[rows];

            ResultSet rs=statement.executeQuery("select * from Users");

            int i=0;
            while(rs.next()){

                UsersList.add(new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
                System.out.println(rs);

                RowPanel[i]= new JPanel();
                RowPanel[i].setBackground(new Color(255, 255, 255,100));
                UsersPanel.add(RowPanel[i]);

                RowPanel[i].add(new JLabel((i+1)+ " . "));
                RowPanel[i].add(new JLabel("User ID: " + rs.getInt(1)));
                RowPanel[i].add(new JLabel(rs.getString(2) + " "));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getString(3))));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getString(4))));
                i = i+1;

            }
            System.out.println("==================================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void BringStocks(){

        try{

            Statement statement = Database.getCon().createStatement();
            ResultSet rsNumberOfRecord = statement.executeQuery("select count(*) from Products");

            int rows =0;
            while (rsNumberOfRecord.next()){
                rows = rsNumberOfRecord.getInt(1);
            }

            System.out.println("===========" + rows +" Records are founded. ============");
            rsNumberOfRecord.close();

            JPanel [] RowPanel = new JPanel[rows];

            ResultSet rs=statement.executeQuery("select * from Products");

            Stocks.setLayout(new GridLayout(rows+2,1));

            int i=0;
            while(rs.next()){

                ListOfProduct.add(new Product(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
                System.out.println(rs);

                RowPanel[i]= new JPanel();
                RowPanel[i].setBackground(new Color(255, 255, 255,100));
                Stocks.add(RowPanel[i]);

                RowPanel[i].add(new JLabel("Product ID: " + rs.getInt(1)));
                RowPanel[i].add(new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/"+(i+1)+".png")));
                //RowPanel[i].add(new JLabel(""+(i+1)+".jpg here"));
                RowPanel[i].add(new JLabel(rs.getString(2) + " "));
                RowPanel[i].add(new JLabel(rs.getInt(3) + " Tl "));
                RowPanel[i].add(new JLabel(rs.getInt(4) + " Left" ));
                i = i+1;

            }
            System.out.println("==================================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}



