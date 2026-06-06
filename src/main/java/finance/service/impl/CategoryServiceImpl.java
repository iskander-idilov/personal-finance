package finance.service.impl;
import java.util.List;
import java.util.stream.Collectors;
import finance.dto.CategoryDTO;
import finance.dto.CategoryMapperMS;
import finance.entity.Category;
import finance.entity.TransactionType;
import finance.repository.CategoryRepository;
import finance.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryMapperMS mapper;

    @Cacheable("categories")
    @Override
    public List<CategoryDTO> getCategories(){return categoryRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());}
    @CacheEvict(value = "categories", allEntries = true)
    @Override
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
    @Override
    public void removeCategory(Category category){
        categoryRepository.delete(category);
    }
    @Override
    public Category findById(Long id){
        return categoryRepository.findById(id).orElse(null);
    }
    @CacheEvict(value = "categories", allEntries = true)
    @Override
    public void save(Category category){
        categoryRepository.save(category);
    }
}
