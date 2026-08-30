package finance.config;
import finance.entity.Account;
import finance.entity.Category;
import finance.entity.TransactionType;
import finance.entity.User;
import finance.repository.AccountRepository;
import finance.repository.CategoryRepository;
import finance.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@org.springframework.scheduling.annotation.EnableAsync
public class MyAppConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new CurrentUriInterceptor());
    }
    @Autowired
    AccountRepository accountRepository;

    @PostConstruct
    public void init(){
        if (accountRepository.count() == 0){
            User user = User.builder()
                    .name("По умолчанию")
                    .build();
            userRepository.save(user);

            Account account = Account.builder()
                    .balance(java.math.BigDecimal.ZERO)
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

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UserRepository userRepository;
}
