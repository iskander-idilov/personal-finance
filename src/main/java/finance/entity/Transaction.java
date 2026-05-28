package finance.entity;
import finance.interfaces.Identifiable;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction implements Identifiable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double amount;
    private LocalDateTime date;
    private TransactionType type;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Account account;
}
