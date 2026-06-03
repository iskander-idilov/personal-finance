package finance.service.impl;
import finance.entity.Transaction;
import finance.repository.TransactionRepository;
import finance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
    public Transaction findById(Long id){return transactionRepository.findById(id).orElse(null);}
    public void removeTransaction(Long id){transactionRepository.deleteById(id);}
    public void save(Transaction transaction){transactionRepository.save(transaction);}
    public List<Transaction> search(String search){return transactionRepository.search(search);}
}
