package finance.dto;
import finance.entity.Transaction;
import org.springframework.stereotype.Component;

@Component
public class TransactionMapper {
    public TransactionDTO toDTO(Transaction transaction){
        TransactionDTO dto = new TransactionDTO();

        dto.setAmount(transaction.getAmount());
        dto.setType(transaction.getType());
        dto.setId(transaction.getId());
        dto.setDate(transaction.getDate());
        dto.setCategoryName(transaction.getCategory().getName());
        dto.setCategoryId(transaction.getCategory().getId());

        return dto;
    }
}
