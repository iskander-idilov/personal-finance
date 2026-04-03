package finance.ui;

import java.util.Scanner;

public class MainMenu {
    Scanner scanner = new Scanner(System.in);

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
                    System.out.print("1. Добавить доход");
                    break;

                case 2:
                    System.out.print("2. Добавить расход");
                    break;

                case 3:
                    System.out.print("3. Посмотреть баланс");
                    break;

                case 4:
                    System.out.print("4. Категории");
                    break;

                case 5:
                    System.out.print("5. Транзакции");
                    break;

                case 6:
                    System.out.print("Удачного дня!");
                    return;
            }
        }
    }
}
