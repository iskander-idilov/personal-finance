package finance.service.impl;
import finance.dto.AccountDTO;
import finance.dto.AccountMapperMS;
import finance.entity.Account;
import finance.repository.AccountRepository;
import finance.security.CurrentUserProvider;
import finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountMapperMS mapper;

    @Autowired
    CurrentUserProvider currentUserProvider;

    @Override
    public Account getAccount() {
        return accountRepository.findByUser(currentUserProvider.getCurrentUser())
                .orElseThrow(() -> new IllegalStateException("Account not found for current user"));
    }
    @Override
    public AccountDTO getAccountDto(){return mapper.toDto(getAccount());}
}
