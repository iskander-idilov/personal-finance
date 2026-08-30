package finance.service.impl;
import finance.entity.Account;
import finance.entity.Category;
import finance.entity.Transaction;
import finance.entity.TransactionType;
import finance.entity.User;
import finance.repository.AccountRepository;
import finance.repository.TransactionRepository;
import finance.security.CurrentUserProvider;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceImplTest {
    @Mock
    TransactionRepository transactionRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CurrentUserProvider currentUserProvider;

    @InjectMocks
    private TransactionServiceImpl transactionService;

    private void mockCurrentAccount(Account account) {
        User user = User.builder().build();
        when(currentUserProvider.getCurrentUser()).thenReturn(user);
        when(accountRepository.findByUser(user)).thenReturn(Optional.of(account));
    }

    @Test
    void addTransaction_income_increasesBalance() {
        Account account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .build();
        mockCurrentAccount(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.INCOME)
                .amount(new BigDecimal("500.00"))
                .build();

        transactionService.addTransaction(transaction);

        assertEquals(new BigDecimal("1500.00"), account.getBalance());
    }

    @Test
    void addTransaction_expense_decreasesBalance() {
        Account account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .build();
        mockCurrentAccount(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("500.00"))
                .build();

        transactionService.addTransaction(transaction);

        assertEquals(new BigDecimal("500.00"), account.getBalance());
    }

    @Test
    void removeTransaction_expense_increasesBalance(){
        Account account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .build();
        mockCurrentAccount(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("500.00"))
                .build();

        transactionService.removeTransaction(transaction.getId(), transaction);

        assertEquals(new BigDecimal("1500.00"), account.getBalance());
    }

    @Test
    void removeTransaction_income_decreasesBalance(){
        Account account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .build();
        mockCurrentAccount(account);

        Transaction transaction = Transaction.builder()
                .type(TransactionType.INCOME)
                .amount(new BigDecimal("500.00"))
                .build();

        transactionService.removeTransaction(transaction.getId(), transaction);

        assertEquals(new BigDecimal("500.00"), account.getBalance());
    }

    @Test
    void updateTransaction_changesAmountAndType_recalculatesBalance() {
        Account account = Account.builder()
                .balance(new BigDecimal("1000.00"))
                .build();
        mockCurrentAccount(account);

        // Existing expense of 200 already applied to the 1000 balance.
        Transaction transaction = Transaction.builder()
                .type(TransactionType.EXPENSE)
                .amount(new BigDecimal("200.00"))
                .build();

        Category newCategory = Category.builder().name("Food").build();

        // Edit to an income of 300: undo the old expense (+200), apply new income (+300).
        transactionService.updateTransaction(transaction, new BigDecimal("300.00"), TransactionType.INCOME, newCategory);

        assertEquals(new BigDecimal("1500.00"), account.getBalance());
        assertEquals(new BigDecimal("300.00"), transaction.getAmount());
        assertEquals(TransactionType.INCOME, transaction.getType());
        assertEquals(newCategory, transaction.getCategory());
    }
}
