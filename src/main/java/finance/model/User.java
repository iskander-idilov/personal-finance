package finance.model;
import finance.interfaces.Identifiable;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements Identifiable {
    private int id;
    private String name;
}
