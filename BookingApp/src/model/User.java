package model;

public class User {
    private String name;
    private String email;
    private String password;
    private AccountBalance accountBalance;

    public User() {
    }
    public User(String name) {
        this.name = name;
    }
    public User(String name, String email, String password, AccountBalance accountBalance) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.accountBalance = accountBalance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public AccountBalance getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(AccountBalance accountBalance) {
        this.accountBalance = accountBalance;
    }
}
