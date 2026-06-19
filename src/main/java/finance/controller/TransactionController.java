package finance.controller;
import finance.dto.TransactionDTO;
import finance.entity.Account;
import finance.entity.Category;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.service.impl.AccountServiceImpl;
import finance.service.impl.CategoryServiceImpl;
import finance.service.impl.TransactionServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class TransactionController {
    private final TransactionServiceImpl transactionService;
    private final CategoryServiceImpl categoryService;
    private final AccountServiceImpl accountService;

    private static final int PAGE_SIZE = 10;

    @GetMapping("/transactions")
    public String transactions(Model model, @RequestParam(defaultValue = "0") int page) {
        List<TransactionDTO> all = transactionService.getTransactions();
        addPaginatedAttributes(model, all, page);
        model.addAttribute("categories", categoryService.getCategories());
        return "transactions";
    }

    private void addPaginatedAttributes(Model model, List<TransactionDTO> all, int page) {
        int totalPages = Math.max(1, (int) Math.ceil((double) all.size() / PAGE_SIZE));
        int safePage = Math.max(0, Math.min(page, totalPages - 1));
        int from = safePage * PAGE_SIZE;
        int to = Math.min(from + PAGE_SIZE, all.size());
        model.addAttribute("transactions", all.subList(from, to));
        model.addAttribute("currentPage", safePage);
        model.addAttribute("totalPages", totalPages);
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

    @PostMapping("transactions/delete")
    public String deleteTransaction(@RequestParam Long id) {
        Transaction transaction = transactionService.findById(id);

        if (transaction == null) {
            return "redirect:/transactions";
        } else {
            transactionService.removeTransaction(id, transaction);
        }

        return "redirect:/transactions";
    }

    @PostMapping("transactions/edit")
    public String editTransaction(@RequestParam Long id,
                                  @RequestParam double amount,
                                  @RequestParam TransactionType type,
                                  @RequestParam Long categoryId){

        Transaction transaction = transactionService.findById(id);
        Category category = categoryService.findById(categoryId);

        if (transaction == null){
            return "redirect:/transactions";
        } else {
            transaction.setAmount(amount);
            transaction.setType(type);
            transaction.setCategory(category);

            transactionService.save(transaction);
        }

        return "redirect:/transactions";
    }

    @GetMapping("/transactions/search")
    public String search(Model model,
                         @RequestParam String search,
                         @RequestParam(defaultValue = "0") int page) {
        List<TransactionDTO> results = transactionService.search(search);
        addPaginatedAttributes(model, results, page);
        model.addAttribute("categories", categoryService.getCategories());
        model.addAttribute("search", search);
        return "transactions";
    }
}
