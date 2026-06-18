package finance.service;
import finance.dto.NewsDTO;
import java.util.List;

public interface NewsService {
    List<NewsDTO> getNews();
    void refreshNews();
}
