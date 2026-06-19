package finance.service.impl;
import finance.dto.NewsDTO;
import finance.service.NewsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@Slf4j
public class NewsServiceImpl implements NewsService {

    @Value("${finnhub.api.key}")
    private String apiKey;

    @Value("${finnhub.news.limit:20}")
    private int newsLimit;

    private final RestTemplate restTemplate = new RestTemplate();

    // Кэш новостей в памяти
    private volatile List<NewsDTO> cachedNews = Collections.emptyList();
    private final AtomicBoolean loading = new AtomicBoolean(false);

    @Override
    public List<NewsDTO> getNews() {
        return cachedNews;
    }

    @Override
    public void refreshNews() {
        if (!loading.compareAndSet(false, true)) {
            return;
        }
        try {
            String url = "https://finnhub.io/api/v1/news?category=general&token=" + apiKey;
            List response = restTemplate.getForObject(url, List.class);
            List<NewsDTO> news = new ArrayList<>();

            for (Object item : response) {
                Map article = (Map) item;
                String title = (String) article.get("headline");
                String description = (String) article.get("summary");
                String articleUrl = (String) article.get("url");
                String image = (String) article.get("image");
                long timestamp = ((Number) article.get("datetime")).longValue();
                String publishedAt = java.time.Instant.ofEpochSecond(timestamp)
                        .atZone(java.time.ZoneId.of("Asia/Almaty"))
                        .format(java.time.format.DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm"));

                news.add(new NewsDTO(title, description, articleUrl, image, publishedAt));
            }

            cachedNews = Collections.unmodifiableList(news.stream().limit(newsLimit).collect(java.util.stream.Collectors.toList()));
            log.info("Loaded {} articles", news.size());
        } catch (Exception e) {
            log.error("Failed: {}", e.getMessage(), e);
        } finally {
            loading.set(false);
        }
    }
}