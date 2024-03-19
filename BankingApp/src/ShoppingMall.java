public class ShoppingMall {
    BankCard bankCard;
    public ShoppingMall(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public void setBankCard(BankCard bankCard) {
        this.bankCard = bankCard;
    }

    public void achizitie(double amount){
        bankCard.doTransaction(amount);
    }

}
