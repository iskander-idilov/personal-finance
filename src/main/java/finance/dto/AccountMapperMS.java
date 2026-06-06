package finance.dto;
import finance.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapperMS {
    AccountDTO toDto(Account account);
}
