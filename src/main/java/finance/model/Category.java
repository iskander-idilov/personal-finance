package finance.model;

import finance.interfaces.Identifiable;

public class Category implements Identifiable {
    private String name;
    private int id;
    private TransactionType type;

    public Category(){
        this.name = "Empty";
        this.id = 0;
        this.type = TransactionType.INCOME;
    }

    public Category(String name, int id, TransactionType type){
        this.name = name;
        this.id = id;
        this.type = type;
    }

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public TransactionType getType(){return type;}
    public void setType(TransactionType type){this.type = type;}
}
