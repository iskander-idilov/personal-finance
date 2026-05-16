package finance.service;
import finance.model.Transaction;
import finance.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TransactionService{
    @Autowired
    TransactionRepository transactionRepository;
    public List<Transaction> getTransactions(){
        return transactionRepository.findAll();
    }

    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }

}
