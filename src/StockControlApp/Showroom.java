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


public class Showroom extends JFrame implements ActionListener {

    private static final long serialVersionUID = 1L;
    // Database connection and statement are defined on following lines
    private static PreparedStatement st;
    private Database connection = new Database();
    // The panels of frame are defined on following lines
    private JPanel panel1;
    private JPanel top;
    private JPanel right;
    private JPanel left;
    private JPanel bottom;
    private JPanel Center;
    private JPanel CenterShowroom;
    private JPanel CenterBasket;
    //private JScrollPane CenterBasket;
    // General button of frame are defined on following lines
    private JButton btnBack;
    private JButton ShowBasketBtn;
    private JButton ShowroomBtn;
    private JButton PayButton = new JButton("PAY");
    // RowButton array is Created for each line of product that stored in the database on following line
    private JButton [] RowButton;
    // Five array variables are created for instant value
    private int rows;
    private int [] RowId;
    private String [] RowName;
    private int [] RowPrice;
    Font ProductFont = new Font("Ubuntu", Font.BOLD, 13);
    private JTextField[] rowQuantitive = new JTextField[12];
    // We created Arraylist for show basket flexibly on following line
    private java.util.List<Product> BasketList = new ArrayList<>();
    // We created Arraylist for show basket flexibly on following line
    private java.util.List<Product> ListOfProduct = new ArrayList<>();
    // This variable is created for show total price of basket
    private int TotalPriceOfBasket = 0;
    ScrollBar scrollBar = new ScrollBar();

    Showroom(){
        // Showroom Class's Frame is initialize
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
        btnBack.setPreferredSize(new Dimension(100,20));
        btnBack.setBackground(new java.awt.Color(189,51,51));
        btnBack.setForeground(Color.WHITE);
        btnBack.addActionListener(this::actionPerformed);

        ShowBasketBtn.setPreferredSize(new Dimension(100,20));
        ShowBasketBtn.setBackground(new java.awt.Color(94, 189, 86));
        ShowBasketBtn.setForeground(Color.WHITE);
        ShowBasketBtn.addActionListener(this::actionPerformed);

        ShowroomBtn.setPreferredSize(new Dimension(100,20));
        ShowroomBtn.setBackground(new java.awt.Color(94, 189, 86));
        ShowroomBtn.setForeground(Color.WHITE);
        ShowroomBtn.addActionListener(this::actionPerformed);
        ShowroomBtn.setVisible(false);

        PayButton.setPreferredSize(new Dimension(100,20));
        PayButton.setBackground(new java.awt.Color(94, 189, 86));
        PayButton.setForeground(Color.WHITE);
        PayButton.addActionListener(this::actionPerformed);
        PayButton.setVisible(false);

        Center.setBackground(new Color(0,50,67,255));
        CenterShowroom.setBackground(new Color(0,50,67,255));

        BringRecords();

    }

        public void actionPerformed(ActionEvent e){

            if(e.getSource() == btnBack) {
                Login log = new Login();
                log.setVisible(true);
                this.dispose();
            }
            if(e.getSource() == ShowroomBtn) {
                CenterShowroom.removeAll();
                BringRecords();
                CenterShowroom.setVisible(true);
                ShowBasketBtn.setVisible(true);
                ShowroomBtn.setVisible(false);
                CenterBasket.setVisible(false);
            }
            if(e.getSource() == ShowBasketBtn) {
                CenterShowroom.setVisible(false);
                ShowBasketBtn.setVisible(false);
                ShowroomBtn.setVisible(true);
                CenterBasket.setVisible(true);
                Basket();
            }
            if(e.getSource() == PayButton) {

                CenterShowroom.removeAll();
                try{

                    String query = "update Products set ProductLeft = ? where ProductID = ?";
                    PreparedStatement preparedStmt = connection.con.prepareStatement(query);
                    int left;
                    for (Product eachProduct:BasketList){

                        left = ListOfProduct.get(eachProduct.getProductID()).getProductQuantitative() - eachProduct.getProductQuantitative();
                        System.out.println("Left is " + left + " New value of Quantative " + eachProduct.getProductID());
                        preparedStmt.setInt   (1, left );
                        preparedStmt.setInt   (2, eachProduct.getProductID());
                        preparedStmt.executeUpdate();

                    }
                    BasketList.clear();
                    JOptionPane.showMessageDialog(null,"The payment process took place successfully.");

                }catch (SQLException e1){

                    e1.printStackTrace();
                }

                BringRecords();
                CenterShowroom.setVisible(true);
                ShowBasketBtn.setVisible(true);
                ShowroomBtn.setVisible(false);
                CenterBasket.setVisible(false);

            }
        }

    public void actionRowsPerformed(ActionEvent event){

        JButton button = (JButton)event.getSource();
        Object property = button.getClientProperty("id");

        if (property instanceof Integer) {

            int i = ((Integer)property);

            if(rowQuantitive[i].getText().matches("[0-9]+")){

            if (Integer.valueOf(rowQuantitive[i].getText()) <= ListOfProduct.get(i).getProductQuantitative()){



                    int input = JOptionPane.showConfirmDialog(null,
                            "Would you like add "+Integer.parseInt(rowQuantitive[i].getText())+" "+ ListOfProduct.get(i).getProductName()+" to basket ?",
                            "Select an Option...",JOptionPane.YES_NO_OPTION);

                    if( input == 0 ){

                        System.out.println("======== New Product is Added To Basket ==========");
                        System.out.println(
                                ListOfProduct.get(i).getProductID()
                                        +" "+ ListOfProduct.get(i).getProductName()
                                        +" "+ ListOfProduct.get(i).getProductPrice()
                                        +" "+ Integer.valueOf(rowQuantitive[i].getText()));
                        System.out.println("==================================================");

                        BasketList.add(new Product(
                                ListOfProduct.get(i).getProductID(),
                                ListOfProduct.get(i).getProductName(),
                                ListOfProduct.get(i).getProductPrice(),
                                Integer.valueOf(rowQuantitive[i].getText())));

                        ListOfProduct.set(i,new Product(
                                ListOfProduct.get(i).getProductID(),
                                ListOfProduct.get(i).getProductName(),
                                ListOfProduct.get(i).getProductPrice(),
                                ListOfProduct.get(i).getProductQuantitative() - Integer.valueOf(rowQuantitive[i].getText())
                        ));
                        JOptionPane.showMessageDialog(null,"You added " + ListOfProduct.get(i).getProductName());
                    }




            }else{

                JOptionPane.showMessageDialog(
                        null,
                        "You can not buy more then " + ListOfProduct.get(i).getProductQuantitative(),
                        "Insufficient stock",JOptionPane.ERROR_MESSAGE);
                rowQuantitive[i].setText(String.valueOf(ListOfProduct.get(i).getProductQuantitative()));
            }
        }else {
            JOptionPane.showMessageDialog(null,"Please, type numeric value!","Information Message",JOptionPane.INFORMATION_MESSAGE);
            rowQuantitive[i].setText("1");
        }

        }
    }

    public void Basket(){

        CenterBasket.removeAll();
        TotalPriceOfBasket=0;
        CenterBasket.setLayout(new GridLayout(12,1));
        JPanel [] RowPanel = new JPanel[12];

        int i=0;
        if (!(BasketList.isEmpty())){

            //if(!isBasketShown) {

                for (Product eachProduct : BasketList) {

                    System.out.println(eachProduct.getProductID() + " " + eachProduct.getProductName() + " " + eachProduct.getProductPrice() + " " + eachProduct.getProductQuantitative());

                    RowPanel[i] = new JPanel();
                    RowPanel[i].setBackground(new Color(255, 255, 255, 100));
                    CenterBasket.add(RowPanel[i]);
                    RowPanel[i].add(new JLabel("Product ID: " + eachProduct.getProductID()));
                    RowPanel[i].add(new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/" + (i+1) + ".png")));

                    //RowPanel[i].add(new JLabel(""+eachProduct.getProductID()+".jpg here"));
                    RowPanel[i].add(new JLabel(eachProduct.getProductName()));
                    RowPanel[i].add(new JLabel(eachProduct.getProductPrice() + " Tl"));
                    RowPanel[i].add(new JLabel(eachProduct.getProductQuantitative() + " Peaces"));
                    TotalPriceOfBasket = TotalPriceOfBasket + (eachProduct.getProductPrice() * eachProduct.getProductQuantitative());
                    i = i + 1;

                }

                RowPanel[i + 1] = new JPanel();
                RowPanel[i + 1].setBackground(new Color(255, 255, 255, 100));
                CenterBasket.add(RowPanel[i + 1]);
                JLabel TotalPrice = new JLabel("TOTAL YOU NEED TO PAY : " + TotalPriceOfBasket);
                TotalPrice.setForeground(new Color(99, 189, 86));
                TotalPrice.setSize(20, 200);
                RowPanel[i + 1].add(TotalPrice);
                PayButton.setVisible(true);
                RowPanel[i + 1].add(PayButton);
            //}

        }else{

            JOptionPane.showMessageDialog(null,
                    "Your basket is empty.\nHave a nice shopping",
                    "Basket information",JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public void BringRecords(){

        try{

            Statement statement = connection.con.createStatement();
            ResultSet rsNumberOfRecord = statement.executeQuery("select count(*) from Products");

            rows =0;
            while (rsNumberOfRecord.next()){
                rows = rsNumberOfRecord.getInt(1);
            }

            System.out.println("===========" + rows +" Records are founded. ============");
            rsNumberOfRecord.close();


            RowId = new int[rows];
            RowName = new String[rows];
            RowPrice = new int[rows];
            for (int i = 0; i <12; i++) {
                rowQuantitive[i] = new JTextField("1",2);
            }
            RowButton = new JButton[rows];
            JLabel[] product = new JLabel[rows];

            CenterShowroom.setLayout(new GridLayout(rows+2,1));
            JPanel [] RowPanel = new JPanel[rows];

            ResultSet rs=statement.executeQuery("select * from Products");

            int i=0;
            while(rs.next()){

                ListOfProduct.add(new Product(rs.getInt(1),rs.getString(2),rs.getInt(3),rs.getInt(4)));
                System.out.println(rs.getInt(1)+" "+rs.getString(2)+" "+rs.getInt(3)+" "+rs.getInt(4));
                System.out.println(rs);

                RowPanel[i]= new JPanel();
                RowPanel[i].setBackground(new Color(255, 255, 255,100));
                CenterShowroom.add(RowPanel[i]);
                /*
                product[i] = new JLabel("Product ID: " + String.valueOf(rs.getInt(1))
                        +"     "+(i+1)+".jpg here"+ "     "+rs.getString(2)
                        + "     "+String.valueOf(rs.getInt(3)) + " Tl     "
                        +String.valueOf(rs.getInt(4)) + " Left     " );
                product[i].setFont(ProductFont);
                RowPanel[i].add(product[i]);
                */
                RowPanel[i].add(new JLabel("Product ID: " + String.valueOf(rs.getInt(1))));
                RowPanel[i].add(new JLabel(new ImageIcon("/home/akyol/Documents/Stock Control Application/src/Images/"+(i+1)+".png")));
                //RowPanel[i].add(new JLabel(""+(i+1)+".jpg here"));
                RowPanel[i].add(new JLabel(rs.getString(2) + " "));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getInt(3)) + " Tl "));
                RowPanel[i].add(new JLabel(String.valueOf(rs.getInt(4)) + " Left" ));



                rowQuantitive[i].setText("1");
                RowPanel[i].add(rowQuantitive[i]);
                RowButton[i] = new JButton("Add to basket");
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
}


