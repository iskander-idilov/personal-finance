package finance.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO implements Serializable {
    private String ticker;
    private String price;
    private String change;
    private String changePercent;
    private boolean positive;
}
