public record DebitCard( String userName, double limitAmount) implements BankCard{
    private static double spentAmount = 0;

    public DebitCard(String userName, double limitAmount) {
        this.userName = userName;
        this.limitAmount = limitAmount;
    }

    public static double getSpentAmount() {
        return spentAmount;
    }

    public static void setSpentAmount(double spentAmount) {
        DebitCard.spentAmount = spentAmount;
    }

    @Override
    public void doTransaction(double amount){
        spentAmount += amount;
        if (spentAmount > limitAmount)
        {
            System.out.println("Not enough money!");
            return;
        }
        System.out.println("Utilizator " + userName + " a cheltuit " + amount);
        System.out.println("Suma totala cheltuita cu carduri de debit este " + spentAmount);
    }
}
