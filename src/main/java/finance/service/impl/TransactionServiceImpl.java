package finance.service.impl;
import finance.dto.TransactionDTO;
import finance.dto.TransactionMapper;
import finance.entity.Transaction;
import finance.repository.TransactionRepository;
import finance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapper mapper;
    @Override
    public List<TransactionDTO> getTransactions(){return transactionRepository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());}
    @Override
    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);
    }
    @Override
    public Transaction findById(Long id){return transactionRepository.findById(id).orElse(null);}
    @Override
    public void removeTransaction(Long id){transactionRepository.deleteById(id);}
    @Override
    public void save(Transaction transaction){transactionRepository.save(transaction);}
    @Override
    public List<Transaction> search(String search){return transactionRepository.search(search);}
}
