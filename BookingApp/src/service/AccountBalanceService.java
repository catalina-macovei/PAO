package service;
import daoservices.AccountBalanceRepositoryService;
import model.*;

import java.util.Scanner;
public class AccountBalanceService {
    private AccountBalanceRepositoryService dbService;

    public AccountBalanceService () {
        this.dbService = new AccountBalanceRepositoryService();
    }

    public void create(Scanner scanner, User user) {
        System.out.println("Enter the sum of money to deposit:");
        double amount = scanner.nextDouble();

        AccountBalance accountBalance = new AccountBalance(amount);
        user.setAccountBalance(accountBalance);
        dbService.createAccountBalance(accountBalance);
    }

    public void read(Scanner scanner, int accountNumber) {
        AccountBalance accountBalance = dbService.getByAccountNr(accountNumber);
        System.out.println(accountBalance);
    }

    public void delete(Scanner scanner, int accountNumber) {
        dbService.deleteAccountBalance(dbService.getByAccountNr(accountNumber));
    }

    public void update(Scanner scanner, int accountNumber) {

        AccountBalance existingAccountBalance = dbService.getByAccountNr(accountNumber);
        if (existingAccountBalance != null) {
            System.out.println("Options update: 1 - DEPOSIT || 2 - WITHDRAW");
            int option = scanner.nextInt();

            switch (option) {
                case 1:
                    deposit(existingAccountBalance, scanner);
                    break;
                case 2:
                    withdraw(existingAccountBalance, scanner);
                    break;
                default:
                    System.out.println("Wrong option!");
            }
        }
    }

    public void manageAccountBalance(Scanner scanner, User user) {
        AccountBalance accountBalance = user.getAccountBalance();
        if (accountBalance == null) {
            // If accountBalance is null, create a new AccountBalance for the user
            create(scanner, user);
            // Retrieve the updated accountBalance
            accountBalance = user.getAccountBalance();
        }
        // Now accountBalance should not be null
        update(scanner, accountBalance.getAccountNr());
    }
    private void deposit(AccountBalance accountBalance, Scanner scanner) {
        System.out.println("Amount to deposit: ");
        double amount = scanner.nextDouble();
        double currAmount = accountBalance.getAmount();
        accountBalance.setAmount(currAmount + amount);
        System.out.println("Deposit successful.");
    }

    private void withdraw(AccountBalance accountBalance, Scanner scanner) {
        System.out.println("Amount to withdraw: ");
        double amount = scanner.nextDouble();
        double currAmount = accountBalance.getAmount();
        if (currAmount >= amount) {
            accountBalance.setAmount(currAmount - amount);
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds!");
        }
    }

}
