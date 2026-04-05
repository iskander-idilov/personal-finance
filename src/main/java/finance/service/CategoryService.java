package finance.service;
import java.util.ArrayList;
import finance.model.Category;

public class CategoryService extends BaseService<Category> {
    private ArrayList<Category> categories = new ArrayList<>();

    public CategoryService(){
        this.categories = new ArrayList<>();
    }

    public CategoryService(ArrayList<Category> categories){
        this.categories = categories;
    }

    public ArrayList<Category> getCategories(){return categories;}
    public void setCategories(ArrayList<Category> categories){this.categories = categories;}

    public void addCategory(Category category){
          categories.add(category);
    }

    public void removeCategory(Category category){
        categories.remove(category);
    }
}
