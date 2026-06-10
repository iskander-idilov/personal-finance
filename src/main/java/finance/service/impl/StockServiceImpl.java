package finance.service.impl;
import finance.dto.StockDTO;
import finance.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class StockServiceImpl implements StockService {

    @Value("${alphavantage.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final List<String> tickers = List.of("AAPL", "GOOGL", "MSFT", "TSLA", "AMZN");

    @Override
    public List<StockDTO> getStocks() {
        List<StockDTO> stocks = new ArrayList<>();

        for (String ticker : tickers) {
            String url = "https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol="
                    + ticker + "&apikey=" + apiKey;

            Map response = restTemplate.getForObject(url, Map.class);
            Map<String, String> quote = (Map<String, String>) response.get("Global Quote");

            if (quote != null && !quote.isEmpty()) {
                String price = quote.get("05. price");
                String change = quote.get("09. change");
                String changePercent = quote.get("10. change percent");
                boolean positive = !change.startsWith("-");

                stocks.add(new StockDTO(ticker, price, change, changePercent, positive));
            }
        }

        return stocks;
    }
}