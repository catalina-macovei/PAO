package dao;
import model.AccountBalance;
import java.util.ArrayList;
import java.util.List;
public class AccountBalanceDao {
    private static List<AccountBalance> accbalances = new ArrayList<>();

    public AccountBalance read(int accountNr) {
        for (AccountBalance accbalance : accbalances) {
            if (accbalance.getAccountNr() == accountNr) {
                return accbalance;
            }
        }
        return null; // account balance with given account number is not found
    }

    public void delete(AccountBalance accbalance) {
        accbalances.remove(accbalance);
    }

    public void create(AccountBalance accbalance) {
        accbalances.add(accbalance);
    }

}
