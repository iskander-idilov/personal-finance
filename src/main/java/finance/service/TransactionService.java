package finance.service;

import finance.dto.TransactionDTO;
import finance.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<TransactionDTO> getTransactions();
    void addTransaction(Transaction transaction);
    Transaction findById(Long id);
    void removeTransaction(Long id);
    void save(Transaction transaction);
    List<Transaction> search(String search);
}
