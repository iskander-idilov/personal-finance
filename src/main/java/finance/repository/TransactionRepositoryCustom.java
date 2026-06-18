package finance.repository;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import java.util.List;

public interface TransactionRepositoryCustom {
    List<Transaction> findWithFilters(TransactionType type, Long categoryId);
}
