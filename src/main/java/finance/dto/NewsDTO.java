package finance.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewsDTO {
    private String headline;
    private String summary;
    private String url;
    private String image;
    private String datetime;
}
