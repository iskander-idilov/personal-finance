package finance.controller;
import finance.model.Account;
import finance.model.Category;
import finance.model.Transaction;
import finance.model.TransactionType;
import finance.repository.CategoryRepository;
import finance.service.AccountService;
import finance.service.CategoryService;
import finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionService transactionService;
    private final CategoryService categoryService;
    private final AccountService accountService;

    @GetMapping("/transactions")
    public String transactions(Model model){
        model.addAttribute("transactions", transactionService.getTransactions());
        model.addAttribute("categories", categoryService.getCategories());

        return "transactions";
    }

    @PostMapping("/transactions")
    public String transactions(@RequestParam double amount, @RequestParam TransactionType type, @RequestParam Long categoryId) {
        Category category = categoryService.findById(categoryId);
        Account account = accountService.getAccount();

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(type)
                .date(LocalDateTime.now())
                .category(category)
                .account(account)
                .build();

        transactionService.addTransaction(transaction);

        return "redirect:/transactions";
    }
}
