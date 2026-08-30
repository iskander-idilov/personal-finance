package finance.dto;
import finance.entity.TransactionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDTO {
    private Long id;
    private Long categoryId;
    private BigDecimal amount;
    private String categoryName;
    private LocalDateTime date;
    private TransactionType type;

}
