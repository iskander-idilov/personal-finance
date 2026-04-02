package finance.service;
import finance.model.Transaction;
import java.util.ArrayList;

public class TransactionService {
    private ArrayList<Transaction> transactions = new ArrayList<>();

    public TransactionService(){
        this.transactions = new ArrayList<>();
    }

    public TransactionService(ArrayList<Transaction> transactions){
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction){
        transactions.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        transactions.remove(transaction);
    }

    public Transaction findById(int id){
        for (Transaction transaction : transactions){
            if (transaction.getId() == id){
                return transaction;
            }
        }
        return null;
    }
}
