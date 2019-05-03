package StockControlApp;

public class Product {

    private int ProductID;
    private String ProductName;
    private int ProductPrice;
    private int ProductQuantitative;

    public Product (int ProductID, String ProductName, int ProductPrice, int ProductQuantitative){

        this.ProductID = ProductID;
        this.ProductName = ProductName;
        this.ProductPrice = ProductPrice;
        this.ProductQuantitative = ProductQuantitative;

    }

    public int getProductID() {
        return ProductID;
    }

    public void setProductID(int productID) {
        ProductID = productID;
    }

    public String getProductName() {
        return ProductName;
    }

    public void setProductName(String productName) {
        ProductName = productName;
    }

    public int getProductPrice() {
        return ProductPrice;
    }

    public void setProductPrice(int productPrice) {
        ProductPrice = productPrice;
    }

    public int getProductQuantitative() {
        return ProductQuantitative;
    }

    public void setProductQuantitative(int productQuantitative) {
        ProductQuantitative = productQuantitative;
    }
}
