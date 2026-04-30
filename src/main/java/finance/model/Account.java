package finance.model;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Account {
    private int id;
    private double balance;
    private User user;
}
