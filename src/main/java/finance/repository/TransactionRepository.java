package finance.repository;
import finance.entity.Transaction;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface TransactionRepository extends JpaRepository <Transaction, Long> {

    @Query("SELECT t FROM Transaction t WHERE " +
            "LOWER(t.category.name) LIKE LOWER(CONCAT('%', :search, '%')) OR " +
            "CAST(t.type AS string) LIKE UPPER(CONCAT('%', :search, '%')) OR " +
            "CAST(t.amount AS string) LIKE CONCAT('%', :search, '%')")
    List<Transaction> search(@Param("search") String search);
}
