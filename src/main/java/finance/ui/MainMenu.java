package finance.ui;
import finance.model.Account;
import finance.model.Category;
import finance.model.Transaction;
import finance.model.TransactionType;
import finance.service.CategoryService;
import finance.service.StorageService;
import finance.service.TransactionService;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Scanner;

public class MainMenu {
    Scanner scanner = new Scanner(System.in);
    private TransactionService transactionService;
    private CategoryService categoryService;
    private  StorageService storageService;

    public MainMenu(TransactionService transactionService, CategoryService categoryService, StorageService storageService) {
        this.transactionService = transactionService;
        this.categoryService = categoryService;
        this.storageService = storageService;
    }


    public void show() {
        while (true) {
            System.out.println("Меню:");
            System.out.println("1. Добавить доход");
            System.out.println("2. Добавить расход");
            System.out.println("3. Посмотреть баланс");
            System.out.println("4. Категории");
            System.out.println("5. Транзакции");
            System.out.println("6. Выйти");

            int input = scanner.nextInt();


            switch (input) {
                case 1:
                    System.out.println("Введите сумму");
                    double amount = scanner.nextDouble();
                    Transaction transaction = new Transaction(1, amount, LocalDateTime.now(), TransactionType.INCOME, new Category(), new Account());
                    transactionService.addTransaction(transaction);
                    System.out.println("Доход успешно добавлен");
                    break;

                case 2:
                    System.out.println("Введите сумму");
                    double expenseAmount = scanner.nextDouble();
                    Transaction expenseTransaction = new Transaction(1, expenseAmount, LocalDateTime.now(), TransactionType.EXPENSE, new Category(), new Account());
                    transactionService.addTransaction(expenseTransaction);
                    System.out.println("Расход успешно добавлен");
                    break;

                case 3:
                    double balance = 0;
                    for (Transaction tx : transactionService.getTransactions()) {
                        if (tx.getType() == TransactionType.INCOME) {
                            balance += tx.getAmount();
                        } else {
                            balance -= tx.getAmount();
                        }
                    }
                    System.out.println("Ваш баланс составляет: " + balance);
                    break;

                case 4:
                    System.out.println("1. Добавить категорию");
                    System.out.println("2. Удалить категорию");
                    System.out.println("3. Показать все категории");
                    System.out.println("4. Выйти в меню");
                    int inputCategory = scanner.nextInt();

                    switch (inputCategory) {
                        case 1:
                            System.out.println("Введите название категории");
                            String nameOfCategory = scanner.next();
                            Category addCategory = new Category(nameOfCategory, 1, TransactionType.INCOME);
                            categoryService.addCategory(addCategory);
                            System.out.println("Категория добавлена");
                            break;

                        case 2:
                            for (Category category : categoryService.getCategories()) {
                                System.out.println(category.getId() + ") " + category.getName());
                            }
                            System.out.println("Какую категорию хотите удалить?");
                            int choiceCategory = scanner.nextInt();

                            Category toDelete = categoryService.findById(choiceCategory);

                            if (toDelete != null) {
                                categoryService.removeCategory(toDelete);
                                System.out.println("Категория удалена");
                            }

                            break;

                        case 3:
                            for (Category category : categoryService.getCategories()) {
                                System.out.println(category.getId() + ") " + category.getName());
                            }
                            break;

                        case 4:
                            System.out.println("Возврат в главное меню");
                            break;
                    }
                    break;


                case 5:
                    System.out.println("1. Изменить сумму");
                    System.out.println("2. Удалить транзакцию");
                    System.out.println("3. История транзакций");
                    System.out.println("4. Выйти в меню");

                    int choiceTransactions = scanner.nextInt();

                    switch (choiceTransactions){
                        case 1:
                            for (Transaction tx : transactionService.getTransactions()){
                                System.out.println(tx.getId() + ") " + tx.getAmount());
                            }

                            System.out.println("Выберите транзакцию");
                            int choiceTransaction = scanner.nextInt();
                            Transaction toChange = transactionService.findById(choiceTransaction);

                            if (toChange != null){
                                System.out.println("Введите новую сумму");
                                double newAmount = scanner.nextDouble();
                                toChange.setAmount(newAmount);
                                System.out.println("Сумма изменена");
                            }
                            break;

                        case 2:
                            for (Transaction tx : transactionService.getTransactions()){
                                System.out.println(tx.getId() + ") " + tx.getAmount());
                            }

                            System.out.println("Какую транзакцию хотите удалить?");
                            int choiceTrx = scanner.nextInt();

                            Transaction toDelete = transactionService.findById(choiceTrx);

                            if (toDelete != null) {
                                transactionService.removeTransaction(toDelete);
                                System.out.println("транзакция удалена");
                            }

                            break;

                        case 3:
                            Collections.sort(transactionService.getTransactions(), (Transaction a, Transaction b) -> a.getAmount() > b.getAmount() ? 1 : -1);
                            for (Transaction tx : transactionService.getTransactions()){
                                System.out.println(tx.getAmount() + " | " + tx.getType() + " | " + tx.getCategory().getName() + " | " + tx.getDate());
                            }
                            break;

                        case 4:
                            System.out.println("Возврат в главное меню");
                            break;
                    }
                    break;

                case 6:
                    storageService.exportToCSV(transactionService.getTransactions());
                    System.out.println("Удачного дня!");
                    return;
            }
        }
    }
}
