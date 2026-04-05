package finance.model;

import finance.interfaces.Identifiable;

public class User implements Identifiable {
    private int id;
    private String name;

    public User(){
        this.id = 0;
        this.name = "Empty";
    }

    public User(int id, String name){
        this.id = id;
        this.name = name;
    }

    public int getId(){return id;}
    public void setId(int id){this.id = id;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}
}
