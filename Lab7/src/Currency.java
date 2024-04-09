public enum Currency {
    USD("United States", "US"),
    RON("Romania", "RO"),
    EUR("European Union", "EU"),
    GBP("United Kingdom", "GB"),
    TRY("Turkey", "TUR");

    private final String countryName;
    private final String countrySymbol;

    Currency(String countryName, String countrySymbol) {
        this.countryName = countryName;
        this.countrySymbol = countrySymbol;
    }

    public String getCountryName() {
        return countryName;
    }

    public String getCountrySymbol() {
        return countrySymbol;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "countryName='" + countryName + '\'' +
                ", countrySymbol='" + countrySymbol + '\'' +
                '}';
    }
}
