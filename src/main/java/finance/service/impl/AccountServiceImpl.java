package finance.service.impl;
import finance.dto.AccountDTO;
import finance.dto.AccountMapper;
import finance.entity.Account;
import finance.repository.AccountRepository;
import finance.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountMapper mapper;
    @Override
    public Account getAccount() {return accountRepository.findAll().get(0);}
    @Override
    public AccountDTO getAccountDto(){return mapper.toDto(accountRepository.findAll().get(0));}
}
