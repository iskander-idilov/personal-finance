package finance.service;
import finance.model.Account;
import finance.model.Category;
import finance.model.Transaction;
import finance.model.TransactionType;

import java.io.FileWriter;
import java.util.ArrayList;

public class StorageService {
    public void exportToCSV(ArrayList<Transaction> transactions){
        try{
            FileWriter writer = new FileWriter("transactions.csv");

            for (Transaction transaction : transactions){
                writer.write(transaction.getId() + ", " + transaction.getAmount() + ", " + transaction.getDate() + ", " + transaction.getType() + ", " + transaction.getCategory().getName() + ", " + transaction.getAccount().getBalance() + "\n");
            }
            writer.close();
        } catch (Exception e){
           e.printStackTrace();
        }
    }
}
