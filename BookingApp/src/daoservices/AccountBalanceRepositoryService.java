package daoservices;

import dao.AccountBalanceDao;
import model.AccountBalance;

public class AccountBalanceRepositoryService {
    private AccountBalanceDao accountBalanceDao;
    public AccountBalanceRepositoryService() {
        this.accountBalanceDao = new AccountBalanceDao();
    }
    public AccountBalance getByAccountNr(int accountNr) {
        AccountBalance accountBalance = accountBalanceDao.read(accountNr);
        if (accountBalance != null) {
            System.out.println(accountBalance);
        } else {
            System.out.println("No account balance found for account number: " + accountNr);
        }
        return accountBalance;
    }
    public void createAccountBalance(AccountBalance accountBalance) {
        accountBalanceDao.create(accountBalance);
    }

    public void deleteAccountBalance(AccountBalance accountBalance) {
        accountBalanceDao.delete(accountBalance);
    }
}
