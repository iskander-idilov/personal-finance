package finance.config;
import finance.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewsCacheWarmup {
    private final NewsService newsService;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {newsService.refreshNews();}
}
