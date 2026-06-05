package finance.service;

import finance.dto.CategoryDTO;
import finance.entity.Category;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getCategories();
    void addCategory(String name);
    void removeCategory(Category category);
    Category findById(Long id);
    void save(Category category);
}
