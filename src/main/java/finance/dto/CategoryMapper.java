package finance.dto;
import finance.entity.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper {
    public CategoryDTO toDto(Category category){
        CategoryDTO dto = new CategoryDTO();

        dto.setId(category.getId());
        dto.setType(category.getType());
        dto.setName(category.getName());
        dto.setDefault(category.isDefault());
        dto.setIcon(category.getIcon());

        return dto;
    }
}
