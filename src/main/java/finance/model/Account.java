package finance.model;

public class Account {
    private int id;
    private double balance;
    private User user;

    public Account(){
        this.id = 0;
        this.balance = 0.0;
        this.user = new User();
    }

    public Account(int id, double balance, User user){
        this.id = id;
        this.balance = balance;
        this.user = user;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public double getBalance(){return  balance;}
    public void setBalance(double balance){this.balance = balance;}

    public User getUser(){return user;}
    public void setUser(User user){this.user = user;}
}
