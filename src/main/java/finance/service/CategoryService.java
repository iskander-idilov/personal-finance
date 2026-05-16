package finance.service;
import java.util.List;
import finance.model.Category;
import finance.model.TransactionType;
import finance.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Cacheable("categories")
    public List<Category> getCategories(){
        return categoryRepository.findAll();
    }
    @CacheEvict(value = "categories", allEntries = true)
    public void addCategory(String name){
        if (name != null && !name.trim().isEmpty()){
            Category category = Category.builder()
                    .name(name)
                    .type(TransactionType.INCOME)
                    .build();
            categoryRepository.save(category);
        }
    }
    @CacheEvict(value = "categories", allEntries = true)
    public void removeCategory(Category category){
        categoryRepository.delete(category);
    }

    public Category findById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    @CacheEvict(value = "categories", allEntries = true)
    public void save(Category category){
        categoryRepository.save(category);
    }
}
