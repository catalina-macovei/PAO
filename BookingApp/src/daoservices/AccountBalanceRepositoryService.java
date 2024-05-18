package daoservices;

import dao.AccountBalanceDao;
import model.AccountBalance;
import model.User;
import utils.FileManagement;

import java.sql.SQLException;

import static utils.Constants.AUDIT_FILE;

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
            FileManagement.scriereFisierChar(AUDIT_FILE, "update account balance id=" + acc.getAccountNr() );
        }
    }

    public int getAccountId(User user) throws SQLException {
        return accountBalanceDao.getAccountId(user);
    }
}
