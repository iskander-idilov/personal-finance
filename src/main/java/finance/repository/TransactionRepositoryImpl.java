package finance.repository;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TransactionRepositoryImpl implements TransactionRepositoryCustom {

    @Autowired
    private EntityManager entityManager;

    @Override
    public List<Transaction> findWithFilters(TransactionType type, Long categoryId) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Transaction> query = cb.createQuery(Transaction.class);
        Root<Transaction> root = query.from(Transaction.class);

        List<Predicate> predicates = new ArrayList<>();

        if (type != null) {
            predicates.add(cb.equal(root.get("type"), type));
        }

        if (categoryId != null) {
            predicates.add(cb.equal(root.get("category").get("id"), categoryId));
        }

        query.where(predicates.toArray(new Predicate[0]));

        return entityManager.createQuery(query).getResultList();
    }
}
