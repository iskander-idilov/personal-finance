package finance.service;
import finance.model.Account;
import finance.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account getAccount() {
        return accountRepository.findAll().get(0);
    }
}
