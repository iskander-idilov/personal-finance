package finance.service;
import finance.dto.TransactionDTO;
import finance.entity.Transaction;
import finance.entity.TransactionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    void addTransaction(Transaction transaction);
    Transaction findById(Long id);
    void removeTransaction(Long id, Transaction transaction);
    void updateTransaction(Transaction transaction, BigDecimal newAmount, TransactionType newType, finance.entity.Category newCategory);
    void save(Transaction transaction);
    List<TransactionDTO> search(String search);
    BigDecimal getTotalIncome();
    BigDecimal getTotalExpense();
    Map<String, BigDecimal> getExpensesByCategory();
    List<TransactionDTO> findWithFilters(TransactionType type, Long categoryId);
}
