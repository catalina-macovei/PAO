public record CreditCard(String userName) implements BankCard{
    private static double spentAmount = 0;

    public CreditCard(String userName) {
        this.userName = userName;
    }

    public static double getSpentAmount() {
        return spentAmount;
    }

    public static void setSpentAmount(double spentAmount) {
        CreditCard.spentAmount = spentAmount;
    }

    @Override
    public void doTransaction(double amount){
        spentAmount += amount;
        System.out.println("Utilizator " + userName + " a cheltuit " + amount);
        System.out.println("Suma totala cheltuita cu carduri de credit este " + spentAmount);

    }

}
