public class Application {
    public static void main(String[] args) {
        BankCard debitCard = new DebitCard("Catalina Macovei", 6000);
        ShoppingMall shoppingMall = new ShoppingMall(debitCard);;

        // Utilizarea unui card de debit
        System.out.println("Utilizarea unui card de debit:");
        shoppingMall.achizitie(3000);
        shoppingMall.achizitie(2000);
        shoppingMall.achizitie(5000);

        // Utilizarea unui card de credit
        System.out.println("\nUtilizarea unui card de credit:");
        BankCard creditCard = new CreditCard("Alibaba");
        shoppingMall.setBankCard(creditCard);
        shoppingMall.achizitie(4000);
    }
}
