package finance.service;
import finance.model.Transaction;
import java.util.ArrayList;

public class TransactionService extends BaseService<Transaction> {
    private int nextId = 1;

    public TransactionService(){
        this.items = new ArrayList<>();
    }

    public TransactionService(ArrayList<Transaction> transactions){
        this.items = transactions;
    }

    public ArrayList<Transaction> getTransactions(){
        return items;
    }

    public void addTransaction(Transaction transaction){
        transaction.setId(nextId);
        nextId++;
        items.add(transaction);
    }

    public void removeTransaction(Transaction transaction){
        items.remove(transaction);
    }
}
