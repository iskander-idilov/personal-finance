package finance.repository;
import finance.entity.Account;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<Transaction> findWithFilters(Account account, TransactionType type, Long categoryId);
}
