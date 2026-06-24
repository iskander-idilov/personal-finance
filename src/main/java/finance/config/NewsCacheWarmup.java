package finance.config;
import finance.service.NewsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Profile("!test")
public class NewsCacheWarmup {
    private final NewsService newsService;

    @Async
    @EventListener(ApplicationReadyEvent.class)
    public void warmUp() {newsService.refreshNews();}
}
