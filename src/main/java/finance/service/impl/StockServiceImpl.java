package finance.service.impl;
import finance.dto.StockDTO;
import finance.service.StockService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class StockServiceImpl implements StockService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    private final List<String> tickers = List.of("AAPL", "GOOGL", "MSFT", "TSLA", "AMZN");

    private volatile List<StockDTO> cachedStocks = Collections.emptyList();
    private final AtomicBoolean loading = new AtomicBoolean(false);

    @Override
    public List<StockDTO> getStocks() {
        return cachedStocks;
    }

    @Override
    public void refreshStocks() {
        if (!loading.compareAndSet(false, true)) {
            return;
        }
        try {
            System.out.println("[StockService] Starting refresh...");
            List<StockDTO> stocks = new ArrayList<>();
            for (String ticker : tickers) {
                String url = "https://finnhub.io/api/v1/quote?symbol=" + ticker + "&token=" + apiKey;

                Map response = restTemplate.getForObject(url, Map.class);
                System.out.println(ticker + " -> " + response);

                if (response == null || !response.containsKey("c")) {
                    System.err.println("[StockService] No data for " + ticker);
                    continue;
                }

                double price = ((Number) response.get("c")).doubleValue();
                double change = ((Number) response.get("d")).doubleValue();
                double changePercent = ((Number) response.get("dp")).doubleValue();

                if (price == 0) {
                    System.err.println("[StockService] Zero price for " + ticker + ", skipping");
                    continue;
                }

                String priceStr = String.format("%.2f", price);
                String changeStr = String.format("%.2f", change);
                String changePercentStr = String.format("%.2f%%", changePercent);
                boolean positive = change >= 0;

                stocks.add(new StockDTO(ticker, priceStr, changeStr, changePercentStr, positive));
            }
            cachedStocks = Collections.unmodifiableList(stocks);
            System.out.println("[StockService] Refresh done, loaded: " + stocks.size() + " stocks");
        } catch (Exception e) {
            System.err.println("[StockService] Refresh failed: " + e.getMessage());
            e.printStackTrace();
        } finally {
            loading.set(false);
        }
    }
}