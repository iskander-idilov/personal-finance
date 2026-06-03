package finance.service.impl;
import finance.entity.Account;
import finance.repository.AccountRepository;
import finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    public Account getAccount() {
        return accountRepository.findAll().get(0);
    }
}
