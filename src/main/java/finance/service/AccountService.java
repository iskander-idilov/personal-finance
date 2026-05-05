package finance.service;
import finance.model.Account;
import finance.model.User;
import finance.repository.AccountRepository;
import finance.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init(){
        if (accountRepository.count() == 0){
            User user = User.builder()
                    .name("Default")
                    .build();
            userRepository.save(user);

            Account account = Account.builder()
                    .balance(0)
                    .user(user)
                    .build();

            accountRepository.save(account);
        }
    }
}
