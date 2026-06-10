package finance.service;
import finance.dto.TransactionDTO;
import finance.entity.Transaction;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    void addTransaction(Transaction transaction);
    Transaction findById(Long id);
    void removeTransaction(Long id, Transaction transaction);
    void save(Transaction transaction);
    List<Transaction> search(String search);
    double getTotalIncome();
    double getTotalExpense();
    Map<String, Double> getExpensesByCategory();
}
