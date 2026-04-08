package finance;
import finance.service.CategoryService;
import finance.service.TransactionService;
import finance.ui.MainMenu;

public class App {
    public static void main(String[] args){
         TransactionService transactionService = new TransactionService();
         CategoryService categoryService = new CategoryService();
         MainMenu menu = new MainMenu(transactionService, categoryService);
         menu.show();


    }
}
