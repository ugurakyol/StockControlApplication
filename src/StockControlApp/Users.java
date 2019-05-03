package StockControlApp;

public class Users {

    private int UserID;
    private String Name;
    private String SurName;
    private String Password;

    public Users(int UserID, String Name, String SurName, String Password){

        this.UserID = UserID;
        this.Name = Name;
        this.SurName = SurName;
        this.Password = Password;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int userID) {
        UserID = userID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurName() {
        return SurName;
    }

    public void setSurName(String surName) {
        SurName = surName;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
