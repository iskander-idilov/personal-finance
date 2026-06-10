package finance.service.impl;
import finance.dto.TransactionDTO;
import finance.dto.TransactionMapperMS;
import finance.entity.Account;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.repository.AccountRepository;
import finance.repository.TransactionRepository;
import finance.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;

    @Autowired
    private TransactionMapperMS mapper;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<TransactionDTO> getTransactions(){return transactionRepository.findAll().stream().map(mapper::toDto).collect(Collectors.toList());}
    @Override
    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);

        Account account = accountRepository.findAll().get(0);

        if (transaction.getType() == TransactionType.INCOME){
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        accountRepository.save(account);
    }
    @Override
    public Transaction findById(Long id){return transactionRepository.findById(id).orElse(null);}
    @Override
    public void removeTransaction(Long id, Transaction transaction){
        transactionRepository.deleteById(id);

        Account account = accountRepository.findAll().get(0);

        if (transaction.getType() == TransactionType.INCOME){
            account.setBalance(account.getBalance() - transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() + transaction.getAmount());
        }

        accountRepository.save(account);
    }
    @Override
    public void save(Transaction transaction){transactionRepository.save(transaction);}
    @Override
    public List<Transaction> search(String search){return transactionRepository.search(search);}
    @Override
    public double getTotalIncome() {return transactionRepository.findAll().stream().filter(t -> t.getType() == TransactionType.INCOME).mapToDouble(Transaction::getAmount).sum();}
    @Override
    public double getTotalExpense() {return transactionRepository.findAll().stream().filter(t -> t.getType() == TransactionType.EXPENSE).mapToDouble(Transaction::getAmount).sum();}
    @Override
    public Map<String, Double> getExpensesByCategory() {
        return getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        TransactionDTO::getCategoryName,
                        Collectors.summingDouble(TransactionDTO::getAmount)
                ));
    }

}
