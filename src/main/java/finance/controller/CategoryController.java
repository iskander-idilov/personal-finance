package finance.controller;
import finance.model.Category;
import finance.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;
    @GetMapping("/categories")
    public String categories(Model model){
        model.addAttribute("categories", categoryService.getCategories());

        return "categories";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String name, Model model){
        if (name == null || name.trim().isEmpty()){
            model.addAttribute("error", "Название категории не может быть пустым");
            model.addAttribute("categories", categoryService.getCategories());
            return "categories";
        }
        categoryService.addCategory(name);
        return "redirect:/categories";
    }

    @PostMapping("/categories/delete")
    public String deleteCategory(@RequestParam Long id){
        Category category = categoryService.findById(id);
        categoryService.removeCategory(category);

        return "redirect:/categories";
    }

    @PostMapping("/categories/edit")
    public String editCategory(@RequestParam Long id, @RequestParam String name){
        Category category = categoryService.findById(id);
        category.setName(name);

        return "redirect:/categories";
    }
}
