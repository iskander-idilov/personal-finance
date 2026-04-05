package finance.model;

import finance.interfaces.Identifiable;

public class Transaction implements Identifiable {
    private int id;
    private double amount;
    private String date;
    private TransactionType type;
    private Category category;
    private Account account;

    public Transaction(){
        this.id = 0;
        this.amount = 0.0;
        this.date = "Empty";
        this.type = TransactionType.INCOME;
        this.category = new Category();
        this.account = new Account();
    }

    public Transaction(int id, double amount, String date, TransactionType type, Category category, Account account){
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.type = type;
        this.category = category;
        this.account = account;
    }

    public int getId(){return  id;}
    public void setId(int id){this.id = id;}

    public double getAmount(){return amount;}
    public void setAmount(double amount){this.amount = amount;}

    public String getDate(){return date;}
    public void setDate(String date){this.date = date;}

    public TransactionType getType(){return type;}
    public void setType(TransactionType type){this.type = type;}

    public Category getCategory(){return category;}
    public void setCategory(Category category){this.category = category;}

    public Account getAccount(){return account;}
    public void setAccount(Account account){this.account = account;}
}
