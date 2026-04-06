package finance.ui;

import finance.model.Account;
import finance.model.Category;
import finance.model.Transaction;
import finance.model.TransactionType;
import finance.service.TransactionService;

import java.util.Scanner;

public class MainMenu {
    Scanner scanner = new Scanner(System.in);
    private TransactionService transactionService;

    public MainMenu(TransactionService transactionService){
        this.transactionService = transactionService;
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
                    Transaction transaction = new Transaction(1, amount, "2026-06-03", TransactionType.INCOME, new Category(), new Account());
                    transactionService.addTransaction(transaction);
                    System.out.println("Доход успешно добавлен");
                    break;

                case 2:
                    System.out.println("Введите сумму");
                    break;

                case 3:
                    System.out.println("3. Посмотреть баланс");
                    break;

                case 4:
                    System.out.println("4. Категории");
                    break;

                case 5:
                    System.out.println("5. Транзакции");
                    break;

                case 6:
                    System.out.println("Удачного дня!");
                    return;
            }
        }
    }
}
