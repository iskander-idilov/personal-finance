package finance.model;
import finance.interfaces.Identifiable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Identifiable {
    private String name;
    private int id;
    private TransactionType type;
}
