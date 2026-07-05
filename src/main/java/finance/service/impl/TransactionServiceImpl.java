package finance.service.impl;
import finance.dto.TransactionDTO;
import finance.dto.TransactionMapperMS;
import finance.entity.Account;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.repository.AccountRepository;
import finance.repository.TransactionRepository;
import finance.security.CurrentUserProvider;
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
    @Autowired
    private CurrentUserProvider currentUserProvider;

    private Account currentAccount() {
        return accountRepository.findByUser(currentUserProvider.getCurrentUser())
                .orElseThrow(() -> new IllegalStateException("Account not found for current user"));
    }

    @Override
    public List<TransactionDTO> getTransactions(){return transactionRepository.findByAccount(currentAccount()).stream().map(mapper::toDto).collect(Collectors.toList());}
    @Override
    public void addTransaction(Transaction transaction){
        transactionRepository.save(transaction);

        Account account = currentAccount();

        if (transaction.getType() == TransactionType.INCOME){
            account.setBalance(account.getBalance() + transaction.getAmount());
        } else {
            account.setBalance(account.getBalance() - transaction.getAmount());
        }

        accountRepository.save(account);
    }
    @Override
    public Transaction findById(Long id){
        Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null || !transaction.getAccount().getId().equals(currentAccount().getId())) {
            return null;
        }
        return transaction;
    }
    @Override
    public void removeTransaction(Long id, Transaction transaction){
        transactionRepository.deleteById(id);

        Account account = currentAccount();

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
    public List<TransactionDTO> search(String search){return transactionRepository.search(currentAccount(), search).stream().map(mapper::toDto).collect(Collectors.toList());}
    @Override
    public double getTotalIncome() {return transactionRepository.findByAccount(currentAccount()).stream().filter(t -> t.getType() == TransactionType.INCOME).mapToDouble(Transaction::getAmount).sum();}
    @Override
    public double getTotalExpense() {return transactionRepository.findByAccount(currentAccount()).stream().filter(t -> t.getType() == TransactionType.EXPENSE).mapToDouble(Transaction::getAmount).sum();}
    @Override
    public Map<String, Double> getExpensesByCategory() {
        return getTransactions().stream()
                .filter(t -> t.getType() == TransactionType.EXPENSE)
                .collect(Collectors.groupingBy(
                        TransactionDTO::getCategoryName,
                        Collectors.summingDouble(TransactionDTO::getAmount)
                ));
    }
    @Override
    public List<TransactionDTO> findWithFilters(TransactionType type, Long categoryId) {return transactionRepository.findWithFilters(currentAccount(), type, categoryId).stream().map(mapper::toDto).collect(Collectors.toList());}

}
