package finance.service;
import finance.dto.AccountDTO;
import finance.entity.Account;

public interface AccountService {
    Account getAccount();
    AccountDTO getAccountDto();
}
