
public class ConversieCurrencyImpl implements ConversieCalcul{
    @Override
    public double conversieValutaToEur(double valoare, Currency currency) {
        // return valoare * cursValutar;
        if (currency == Currency.EUR) {
            return valoare;
        } else if (currency == Currency.RON) {
            return valoare * 0.20;
        } else if (currency == Currency.GBP) {
            return valoare * 1.17;
        } else if (currency == Currency.USD) {
            return valoare * 0.92;
        } else if (currency == Currency.TRY) {
            return valoare * 0.029;
        }
        return valoare;
    }

    // Implementarea metodei pentru calcularea ratei
    @Override
    public double calculRata() {
        return 0;
    }
}

