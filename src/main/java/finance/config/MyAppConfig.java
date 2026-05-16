package finance.config;
import finance.model.Account;
import finance.model.Category;
import finance.model.TransactionType;
import finance.model.User;
import finance.repository.AccountRepository;
import finance.repository.CategoryRepository;
import finance.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

@Configuration
@EnableCaching
public class MyAppConfig {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;
    @PostConstruct
    public void init(){
        redisConnectionFactory.getConnection().serverCommands().flushDb();

        if (accountRepository.count() == 0){
            User user = User.builder()
                    .name("По умолчанию")
                    .build();
            userRepository.save(user);

            Account account = Account.builder()
                    .balance(0)
                    .user(user)
                    .build();

            accountRepository.save(account);
        }

        if (categoryRepository.count() == 0){
            Category category = Category.builder()
                    .name("Зарплата")
                    .type(TransactionType.INCOME)
                    .isDefault(true)
                    .icon("bi-cash")
                    .build();

            categoryRepository.save(category);

            categoryRepository.save(Category.builder()
                    .name("Фриланс")
                    .type(TransactionType.INCOME)
                    .isDefault(true)
                    .icon("bi-laptop")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Инвестиции")
                    .type(TransactionType.INCOME)
                    .isDefault(true)
                    .icon("bi-graph-up")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Кэшбек")
                    .type(TransactionType.INCOME)
                    .isDefault(true)
                    .icon("bi-percent")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Подарки")
                    .type(TransactionType.INCOME)
                    .isDefault(true)
                    .icon("bi-gift")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Еда")
                    .type(TransactionType.EXPENSE)
                    .isDefault(true)
                    .icon("bi-egg-fried")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Аренда")
                    .type(TransactionType.EXPENSE)
                    .isDefault(true)
                    .icon("bi-house")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Транспорт")
                    .type(TransactionType.EXPENSE)
                    .isDefault(true)
                    .icon("bi-car-front")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Подписки")
                    .type(TransactionType.EXPENSE)
                    .isDefault(true)
                    .icon("bi-collection-play")
                    .build());

            categoryRepository.save(Category.builder()
                    .name("Развлечения")
                    .type(TransactionType.EXPENSE)
                    .isDefault(true)
                    .icon("bi-controller")
                    .build());
        }
    }

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        GenericJacksonJsonRedisSerializer serializer = GenericJacksonJsonRedisSerializer.builder().build();

        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(serializer));

        return RedisCacheManager.builder(connectionFactory)
                .cacheDefaults(config)
                .build();
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;
}
