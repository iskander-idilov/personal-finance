package finance.dto;
import finance.entity.Category;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoryMapperMS {
    CategoryDTO toDto(Category category);
}
