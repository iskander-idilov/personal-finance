package finance.service;
import java.util.ArrayList;
import finance.model.Category;

public class CategoryService extends BaseService<Category> {
    private int nextId = 1;

    public CategoryService(){
        this.items = new ArrayList<>();
    }

    public CategoryService(ArrayList<Category> categories){
        this.items = categories;
    }

    public ArrayList<Category> getCategories(){return items;}
    public void setCategories(ArrayList<Category> categories){this.items = categories;}

    public void addCategory(Category category){
        category.setId(nextId);
        nextId++;
        items.add(category);
    }

    public void removeCategory(Category category){
        items.remove(category);
    }
}
