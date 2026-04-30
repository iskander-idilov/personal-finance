package finance.controller;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TransactionController {
    @GetMapping("/transactions")
    public String transactions(Model model){
        return "transactions";
    }
}
