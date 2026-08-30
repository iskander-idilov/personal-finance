package finance.entity;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(precision = 19, scale = 2)
    private BigDecimal balance;
    @ManyToOne
    private User user;
}
