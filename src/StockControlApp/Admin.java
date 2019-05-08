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
    private Database database = new Database();

    private JLabel title = new JLabel("Admin Panel");
    private java.util.List<Users> UsersList = new ArrayList<>();
    private java.util.List<Product> ListOfProduct = new ArrayList<>();

    private JButton btnBack;
    private JButton StocksBtn;
    private JButton UsersBtn;
    private JButton [] RowButton;
    private JTextField[] rowQuantitive = new JTextField[12];

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
                        UsersPanel.removeAll();
                        BringUsers();
                        Users.updateUI();

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
            RowButton = new JButton[rows];
            System.out.println("===========" + rows +" Records are founded. ============");
            rsNumberOfRecord.close();

            JPanel ShowUserMain = new JPanel();
            ShowUserMain.setBackground(new Color(255, 255, 255,0));
            JScrollPane scrollPane = new JScrollPane(ShowUserMain,ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
                    ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            scrollPane.setBackground(new Color(255, 255, 255,0));
            //CenterShowroom.setLayout(new GridLayout(rows+2,1));
            ShowUserMain.setLayout(new GridLayout(rows+2,1));

            UsersPanel.setLayout(new GridLayout(rows+2,1));
            UsersPanel.add(scrollPane);
            JPanel [] RowPanel = new JPanel[rows];

            ResultSet rs=statement.executeQuery("select * from Users");

            int i=0;
            while(rs.next()){

                UsersList.add(new Users(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4)));
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getString(3)+" "+rs.getString(4));
                System.out.println(rs);

                RowPanel[i]= new JPanel();
                RowPanel[i].setBackground(new Color(255, 255, 255,100));
                ShowUserMain.add(RowPanel[i]);

                RowPanel[i].add(new JLabel((i+1)+ " . "));
                RowPanel[i].add(new JLabel("User ID: " + rs.getInt(1)));
                RowPanel[i].add(new JLabel(rs.getString(2) + " "));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getString(3))));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getString(4))));

                RowButton[i] = new JButton("Remove user");
                RowPanel[i].add(RowButton[i]);
                RowButton[i].addActionListener(this::actionUserPerformed);
                RowButton[i].putClientProperty("id", i);

                i = i+1;

            }
            System.out.println("==================================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void BringStocks(){

        try{

            ResultSet rsNumberOfRecord = database.NumberOfRecord();

            int rows =0;
            while (rsNumberOfRecord.next()){
                rows = rsNumberOfRecord.getInt(1);
            }

            System.out.println("===========" + rows +" Records are founded. ============");
            rsNumberOfRecord.close();

            for (int i = 0; i <12; i++) {
                rowQuantitive[i] = new JTextField("1",2);
            }
            RowButton = new JButton[rows];

            JPanel [] RowPanel = new JPanel[rows];

            ResultSet rs = database.ProductList();

            Stocks.removeAll();
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

                rowQuantitive[i].setText("1");
                RowPanel[i].add(rowQuantitive[i]);
                RowButton[i] = new JButton("Add to stock");
                RowPanel[i].add(RowButton[i]);
                RowButton[i].addActionListener(this::actionRowsPerformed);
                RowButton[i].putClientProperty("id", i);

                i = i+1;

            }
            System.out.println("==================================================");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void actionUserPerformed(ActionEvent event){

        JButton button = (JButton)event.getSource();
        Object property = button.getClientProperty("id");

        if (property instanceof Integer) {

            int i = ((Integer)property);

            if(UsersList.get(i).getUserID()==1){

                JOptionPane.showMessageDialog(null,"You are root user. You can not remove yourself!",
                        "Big mistake",JOptionPane.ERROR_MESSAGE);

            }else{

                int input = JOptionPane.showConfirmDialog(null,
                        "Would you like remove "+ UsersList.get(i).getName() +" from the system ?",
                        "Select an Option...",JOptionPane.YES_NO_OPTION);

                if( input == 0 ){


                    try{

                        String query = "DELETE FROM Users WHERE UserID = ?;";
                        PreparedStatement preparedStmt = Database.getCon().prepareStatement(query);
                        preparedStmt.setInt   (1, UsersList.get(i).getUserID());
                        preparedStmt.executeUpdate();

                        System.out.println("===== The User is Removed From The System =======");
                        System.out.println(
                                UsersList.get(i).getUserID()
                                        +" "+ UsersList.get(i).getName()
                                        +" "+ UsersList.get(i).getSurName());
                        System.out.println("==================================================");

                        UsersList.remove(i);


                        JOptionPane.showMessageDialog(null,"The user remove process took place successfully.");

                        UsersPanel.removeAll();
                        BringUsers();
                        Users.updateUI();

                    }catch (SQLException e1){

                        e1.printStackTrace();
                    }

                }

            }



        }
    }

    private void actionRowsPerformed(ActionEvent event){

        JButton button = (JButton)event.getSource();
        Object property = button.getClientProperty("id");

        if (property instanceof Integer) {

            int i = ((Integer)property);

            if(rowQuantitive[i].getText().matches("[0-9]+") ){

                if (Integer.valueOf(rowQuantitive[i].getText()) > 0){



                    int input = JOptionPane.showConfirmDialog(null,
                            "Would you like add "+Integer.parseInt(rowQuantitive[i].getText())+" "+ ListOfProduct.get(i).getProductName()+" to stock ?",
                            "Select an Option...",JOptionPane.YES_NO_OPTION);

                    if( input == 0 ){


                        try{

                            String query = "update Products set ProductLeft = ? where ProductID = ?";
                            PreparedStatement preparedStmt = Database.getCon().prepareStatement(query);
                            int left;

                            left = ListOfProduct.get(i).getProductQuantitative()+Integer.valueOf(rowQuantitive[i].getText());
                            preparedStmt.setInt   (1, left );
                            preparedStmt.setInt   (2, ListOfProduct.get(i).getProductID());
                            preparedStmt.executeUpdate();

                            System.out.println("======== New Product is Added To Stock ==========");
                            System.out.println(
                                ListOfProduct.get(i).getProductID()
                                        +" "+ ListOfProduct.get(i).getProductName()
                                        +" "+ ListOfProduct.get(i).getProductPrice()
                                        +" "+ Integer.valueOf(rowQuantitive[i].getText()));
                            System.out.println("==================================================");

                            ListOfProduct.set(i,new Product(
                                    ListOfProduct.get(i).getProductID(),
                                    ListOfProduct.get(i).getProductName(),
                                    ListOfProduct.get(i).getProductPrice(),
                                    ListOfProduct.get(i).getProductQuantitative() + Integer.valueOf(rowQuantitive[i].getText())
                            ));


                            JOptionPane.showMessageDialog(null,"The enter stock process took place successfully.");

                            Stocks.removeAll();
                            BringStocks();
                            Stocks.updateUI();

                        }catch (SQLException e1){

                            e1.printStackTrace();
                        }

                    }




                }else{

                    JOptionPane.showMessageDialog(
                            null,
                            "You can not add less then " + 0,
                            "Insufficient stock",JOptionPane.ERROR_MESSAGE);
                    rowQuantitive[i].setText("1");
                }
            }else {
                JOptionPane.showMessageDialog(null,"Please, type valid value!","Information Message",JOptionPane.INFORMATION_MESSAGE);
                rowQuantitive[i].setText("1");
            }

        }
    }

}



