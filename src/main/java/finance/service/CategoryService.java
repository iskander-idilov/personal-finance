package finance.service;

import finance.entity.Category;

import java.util.List;

public interface CategoryService {
    List<Category> getCategories();
    void addCategory(String name);
    void removeCategory(Category category);
    Category findById(Long id);
    void save(Category category);
}
