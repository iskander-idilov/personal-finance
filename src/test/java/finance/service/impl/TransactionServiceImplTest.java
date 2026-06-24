package finance.service.impl;
import finance.entity.Account;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.repository.AccountRepository;
import finance.repository.TransactionRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Test
    void addTransaction_income_increasesBalance() {
        // 1. Подготовка: создаём счёт с начальным балансом
        Account account = Account.builder()
                .balance(1000.0)
                .build();

        // 2. Говорим моку: когда вызовут accountRepository.findAll(),
        // вернуть список с этим одним счётом
        when(accountRepository.findAll()).thenReturn(List.of(account));

        // 3. Создаём транзакцию типа INCOME на сумму 500
        Transaction transaction = Transaction.builder()
                .type(TransactionType.INCOME)
                .amount(500.0)
                .build();

        // 4. Вызываем реальный метод, который тестируем
        transactionService.addTransaction(transaction);

        // 5. Проверяем — баланс должен стать 1500 (1000 + 500)
        assertEquals(1500.0, account.getBalance());
    }

    @Test
    void removeTransaction_expense_increasesBalance(){
         Account account = Account.builder()
                 .balance(1000.0)
                 .build();

         when(accountRepository.findAll()).thenReturn(List.of(account));

        Transaction transaction = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(500.0)
                .build();

        transactionService.removeTransaction(transaction.getId(), transaction);

        assertEquals(1500.0, account.getBalance());
    }
}
