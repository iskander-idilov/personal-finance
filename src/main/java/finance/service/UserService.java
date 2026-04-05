package finance.service;
import finance.model.User;
import java.util.ArrayList;

public class UserService extends BaseService<User> {
    private ArrayList<User> users = new ArrayList<>();

    public UserService(){
        this.users = new ArrayList<>();
    }

    public UserService(ArrayList<User> users){
        this.users = users;
    }

    public ArrayList<User> getUser(){return users;}
    public void setUser(ArrayList<User> users){this.users = users;}

    public void addUser(User user){
        users.add(user);
    }

    public void removeUser(User user){
        users.remove(user);
    }
}
