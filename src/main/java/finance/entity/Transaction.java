package finance.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 19, scale = 2)
    private BigDecimal amount;
    private LocalDateTime date;
    private TransactionType type;
    @ManyToOne
    private Category category;
    @ManyToOne
    private Account account;
}
