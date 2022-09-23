package model;

public class Price {
    private final String currency;
    private final Float value;

    public Price(String currency, Float value) {
        this.currency = currency;
        this.value = value;
    }

    public String getCurrency() {
        return currency;
    }

    public Float getValue() {
        return value;
    }

}
