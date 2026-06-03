package finance.service;

import finance.entity.Transaction;

import java.util.List;

public interface TransactionService {
    List<Transaction> getTransactions();
    void addTransaction(Transaction transaction);
    Transaction findById(Long id);
    public void removeTransaction(Long id);
    public void save(Transaction transaction);
    public List<Transaction> search(String search);
}
