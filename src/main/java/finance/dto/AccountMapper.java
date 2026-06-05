package finance.dto;
import finance.entity.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {
    public AccountDTO toDto(Account account){
        AccountDTO dto = new AccountDTO();

        dto.setId(account.getId());
        dto.setBalance(account.getBalance());

        return dto;
    }
}
