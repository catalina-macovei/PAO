public class Tranzactie extends ConversieCurrencyImpl implements Cloneable {
    private int id;
    private Payment payment;
    private Currency currency;
    private double amount;
    private String refNmb;

    public Tranzactie(int id, Payment payment) {
        this.id = id;
        this.payment = payment;
        this.amount = conversieValutaToEur(payment.getAmount(), payment.getCurrency());
        this.refNmb = String.valueOf(this.id);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getRefNmb() {
        return refNmb;
    }

    public void setRefNmb(String refNmb) {
        this.refNmb = refNmb;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Tranzactie clone = (Tranzactie) super.clone();
        clone.setPayment((Payment) clone.getPayment().clone());
        return clone;
    }

    @Override
    public String toString() {
        return "Tranzactie{" +
                "id=" + id +
                ", payment=" + payment +
                ", currency=" + currency +
                ", amount=" + amount +
                ", refNmb='" + refNmb + '\'' +
                '}';
    }
}
