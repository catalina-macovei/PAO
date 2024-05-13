package daoservices;

import dao.AccountBalanceDao;
import model.AccountBalance;

import java.sql.SQLException;

public class AccountBalanceRepositoryService {
    private AccountBalanceDao accountBalanceDao = AccountBalanceDao.getInstance();
    public AccountBalanceRepositoryService() throws SQLException {
    }
    public AccountBalance getByAccountNr(int accountNr) throws SQLException {
        AccountBalance accountBalance = accountBalanceDao.read(String.valueOf(accountNr));
        if (accountBalance != null) {
            System.out.println(accountBalance);
        } else {
            System.out.println("No account balance found for account number: " + accountNr);
        }
        return accountBalance;
    }
    public void createAccountBalance(AccountBalance accountBalance) throws SQLException {
        accountBalanceDao.add(accountBalance);
    }

    public void deleteAccountBalance(AccountBalance accountBalance) throws SQLException {
        accountBalanceDao.delete(accountBalance);
    }

    public void update(AccountBalance acc) throws SQLException {
        if (acc != null) {
            accountBalanceDao.update(acc);
        }
    }
}
