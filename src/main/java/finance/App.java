package finance;
import finance.service.TransactionService;
import finance.ui.MainMenu;

public class App {
    public static void main(String[] args){
         TransactionService transactionService = new TransactionService();
         MainMenu menu = new MainMenu(transactionService);
         menu.show();


    }
}
