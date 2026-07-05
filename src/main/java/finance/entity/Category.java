package finance.entity;
import jakarta.persistence.*;
import lombok.*;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Category implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private TransactionType type;
    private boolean isDefault;
    private String icon;
    @ManyToOne
    private User user;
}
