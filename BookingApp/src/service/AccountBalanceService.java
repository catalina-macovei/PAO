package service;
import daoservices.AccountBalanceRepositoryService;
import model.*;
import utils.FileManagement;

import java.sql.SQLException;
import java.util.Scanner;

import static utils.Constants.AUDIT_FILE;

public class AccountBalanceService {
    private AccountBalanceRepositoryService dbService;

    public AccountBalanceService () throws SQLException {
        this.dbService = new AccountBalanceRepositoryService();
    }

    public void create(Scanner scanner, User user) throws SQLException {
        System.out.println("Enter the sum of money to deposit:");
        double amount = scanner.nextDouble();

        AccountBalance accountBalance = new AccountBalance(amount);
        user.setAccountBalance(accountBalance);
        dbService.createAccountBalance(accountBalance);
        FileManagement.scriereFisierChar(AUDIT_FILE, "created account balance for user: " + user.getName() + " amount " + amount);
    }

    public void read(Scanner scanner, int accountNumber) throws SQLException {
        AccountBalance accountBalance = dbService.getByAccountNr(accountNumber);
        FileManagement.scriereFisierChar(AUDIT_FILE, "read account balance: " + accountBalance.getAmount() + " id=" + accountNumber);
        System.out.println(accountBalance);
    }

    public void delete(Scanner scanner, int accountNumber) throws SQLException {
        dbService.deleteAccountBalance(dbService.getByAccountNr(accountNumber));
        FileManagement.scriereFisierChar(AUDIT_FILE, "deleted account balance: " + accountNumber);
    }

    public void update(Scanner scanner, int accountNumber) throws SQLException {

        AccountBalance existingAccountBalance = dbService.getByAccountNr(accountNumber);
        FileManagement.scriereFisierChar(AUDIT_FILE, "read account balance id: " + accountNumber + " amount: " + existingAccountBalance.getAmount() );
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

    public void manageAccountBalance(Scanner scanner, User user) throws SQLException {
        AccountBalance accountBalance = user.getAccountBalance();
        if (accountBalance == null) {
            // If accountBalance is null, create a new AccountBalance for the user
            create(scanner, user);
            accountBalance = user.getAccountBalance();
        }
        //now accountBalance should not be null
        update(scanner, dbService.getAccountId(user));
    }
    private void deposit(AccountBalance accountBalance, Scanner scanner) throws SQLException {
        System.out.println("Amount to deposit: ");
        double amount = scanner.nextDouble();
        double currAmount = accountBalance.getAmount();
        accountBalance.setAmount(currAmount + amount);
        dbService.update(accountBalance);
        FileManagement.scriereFisierChar(AUDIT_FILE, "update: deposit into account balance id=" + accountBalance.getAccountNr() +" ->current amount: " + accountBalance.getAmount());
        System.out.println("Deposit successful.");
    }

    private void withdraw(AccountBalance accountBalance, Scanner scanner) throws SQLException {
        System.out.println("Amount to withdraw: ");
        double amount = scanner.nextDouble();
        double currAmount = accountBalance.getAmount();
        if (currAmount >= amount) {
            accountBalance.setAmount(currAmount - amount);
            dbService.update(accountBalance);
            FileManagement.scriereFisierChar(AUDIT_FILE, "update: widraw from account balance id=" + accountBalance.getAccountNr() +" ->current amount: " + accountBalance.getAmount());
            System.out.println("Withdrawal successful.");
        } else {
            System.out.println("Insufficient funds!");
        }
    }
    public static void main(String[] args) {
        try {
            AccountBalanceService accountBalanceService = new AccountBalanceService();
            Scanner scanner = new Scanner(System.in);

            // Assuming you have a User object available for testing
            User testUser = new User();

            // Test create
            System.out.println("Testing create operation...");
            accountBalanceService.create(scanner, testUser);

            // Test read
            System.out.println("Enter account number to read:");
            int accountNumberToRead = scanner.nextInt();
            accountBalanceService.read(scanner, accountNumberToRead);

            // Test update
            System.out.println("Enter account number to update:");
            int accountNumberToUpdate = scanner.nextInt();
            accountBalanceService.update(scanner, accountNumberToUpdate);

            // Test delete
            System.out.println("Enter account number to delete:");
            int accountNumberToDelete = scanner.nextInt();
            accountBalanceService.delete(scanner, accountNumberToDelete);

            // Close the scanner to prevent resource leak
            scanner.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
