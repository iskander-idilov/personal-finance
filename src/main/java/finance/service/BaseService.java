package finance.service;
import finance.interfaces.Identifiable;
import java.util.ArrayList;

//Это абстрактный класс для хранение объектов любого типа и поисках их по ID
public abstract class BaseService<T extends Identifiable> {
    protected ArrayList<T> items;

    public BaseService(){
        this.items = new ArrayList<>();
    }

    public BaseService(ArrayList<T> items){
        this.items = items;
    }

    public T findById(int id){
         for (T item : items){
             if(item.getId() == id){
                 return item;
             }
         }
         return null;
    }
}
