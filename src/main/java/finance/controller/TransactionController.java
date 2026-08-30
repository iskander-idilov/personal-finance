package finance.controller;
import com.opencsv.CSVWriter;
import finance.dto.TransactionDTO;
import finance.entity.Account;
import finance.entity.Category;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.service.impl.AccountServiceImpl;
import finance.service.impl.CategoryServiceImpl;
import finance.service.impl.TransactionServiceImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    public String transactions(@RequestParam BigDecimal amount, @RequestParam TransactionType type, @RequestParam Long categoryId) {
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
                                  @RequestParam BigDecimal amount,
                                  @RequestParam TransactionType type,
                                  @RequestParam Long categoryId){

        Transaction transaction = transactionService.findById(id);
        Category category = categoryService.findById(categoryId);

        if (transaction == null){
            return "redirect:/transactions";
        } else {
            transactionService.updateTransaction(transaction, amount, type, category);
        }

        return "redirect:/transactions";
    }

    @GetMapping("/transactions/export")
    public void exportCsv(@RequestParam(defaultValue = "all") String period,
                          HttpServletResponse response) throws IOException {
        List<TransactionDTO> transactions = transactionService.getTransactions();

        if ("week".equals(period)) {
            LocalDateTime weekAgo = LocalDateTime.now().minusWeeks(1);
            transactions = transactions.stream()
                    .filter(t -> t.getDate() != null && t.getDate().isAfter(weekAgo))
                    .toList();
        }

        String filename = "week".equals(period) ? "transactions_week.csv" : "transactions_all.csv";
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + filename + "\"");

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm");
        var out = response.getOutputStream();
        out.write(new byte[]{(byte)0xEF, (byte)0xBB, (byte)0xBF});
        try (CSVWriter writer = new CSVWriter(new java.io.OutputStreamWriter(out, java.nio.charset.StandardCharsets.UTF_8))) {
            writer.writeNext(new String[]{"Дата", "Тип", "Категория", "Сумма (₸)"});
            for (TransactionDTO t : transactions) {
                writer.writeNext(new String[]{
                        t.getDate() != null ? t.getDate().format(fmt) : "",
                        t.getType() == TransactionType.INCOME ? "Доход" : "Расход",
                        t.getCategoryName() != null ? t.getCategoryName() : "",
                        String.valueOf(t.getAmount())
                });
            }
        }
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
