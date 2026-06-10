package finance.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockDTO {
    private String ticker;
    private String price;
    private String change;
    private String changePercent;
    private boolean positive;
}
