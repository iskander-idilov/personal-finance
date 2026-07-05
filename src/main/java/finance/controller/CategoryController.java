package finance.controller;
import finance.entity.Category;
import finance.entity.TransactionType;
import finance.repository.TransactionRepository;
import finance.security.CurrentUserProvider;
import finance.service.impl.CategoryServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryServiceImpl categoryService;
    private final TransactionRepository transactionRepository;
    private final CurrentUserProvider currentUserProvider;

    private boolean isOwnedByCurrentUser(Category category) {
        return category.getUser() != null
                && Objects.equals(category.getUser().getId(), currentUserProvider.getCurrentUser().getId());
    }
    @GetMapping("/categories")
    public String categories(Model model,
                             @RequestParam(required = false) String error){
        model.addAttribute("categories", categoryService.getCategories());
        if (error != null) model.addAttribute("error", error);
        return "categories";
    }

    @PostMapping("/categories")
    public String addCategory(@RequestParam String name, @RequestParam TransactionType type, Model model){
        if (name == null || name.trim().isEmpty()){
            model.addAttribute("error", "Название категории не может быть пустым");
            model.addAttribute("categories", categoryService.getCategories());
            return "categories";
        }
        categoryService.addCategory(name, type);
        return "redirect:/categories";
    }

    @PostMapping("/categories/delete")
    public String deleteCategory(@RequestParam Long id){
        Category category = categoryService.findById(id);

        if (category == null || category.isDefault() || !isOwnedByCurrentUser(category)){
            return "redirect:/categories";
        }
        categoryService.removeCategory(category);
        return "redirect:/categories";
    }

    @PostMapping("/categories/edit")
    public String editCategory(@RequestParam Long id, @RequestParam String name,
                               @RequestParam TransactionType type, RedirectAttributes ra){
        Category category = categoryService.findById(id);

        if (category == null || category.isDefault() || !isOwnedByCurrentUser(category)){
            return "redirect:/categories";
        }

        if (category.getType() != type && transactionRepository.existsByCategory_Id(id)){
            ra.addAttribute("error",
                "Нельзя изменить тип категории «" + category.getName() + "»: по ней уже есть транзакции.");
            return "redirect:/categories";
        }

        category.setName(name);
        category.setType(type);
        categoryService.save(category);

        return "redirect:/categories";
    }
}
