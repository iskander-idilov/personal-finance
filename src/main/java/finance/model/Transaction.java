package finance.model;
import finance.interfaces.Identifiable;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Transaction implements Identifiable {
    private int id;
    private double amount;
    private LocalDateTime date;
    private TransactionType type;
    private Category category;
    private Account account;
}
