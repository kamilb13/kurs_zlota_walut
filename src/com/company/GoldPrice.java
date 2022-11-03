package com.company;

import java.math.BigDecimal;
import java.time.LocalDate;

public class GoldPrice {

    LocalDate date;
    BigDecimal price;

    public GoldPrice(LocalDate date, BigDecimal price) {
        this.date = date;
        this.price = price;
    }

    @Override
    public String toString() {
        return "GoldPrice{" +
                "date=" + date +
                ", price=" + price +
                '}';
    }
}
