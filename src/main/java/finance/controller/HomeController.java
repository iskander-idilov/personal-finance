package finance.controller;
import finance.service.AccountService;
import finance.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final AccountService accountService;
    private final TransactionService transactionService;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("account", accountService.getAccountDto());
        model.addAttribute("transactions" , transactionService.getTransactions());
        model.addAttribute("totalIncome", transactionService.getTotalIncome());
        model.addAttribute("totalExpense", transactionService.getTotalExpense());

        return "home";
    }
}
