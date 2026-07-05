package finance.repository;
import finance.entity.Account;
import finance.entity.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransactionRepository extends JpaRepository <Transaction, Long>, TransactionRepositoryCustom {

    boolean existsByCategory_Id(Long categoryId);

    List<Transaction> findByAccount(Account account);

    @Query("SELECT t FROM Transaction t WHERE t.account = :account AND (" +
            "LOWER(t.category.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(t.type AS string) LIKE UPPER(CONCAT('%', :search, '%')) OR " +
            "CAST(t.amount AS string) LIKE CONCAT('%', :search, '%'))")
    List<Transaction> search(@Param("account") Account account, @Param("search") String search);
}
