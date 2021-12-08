package com.szatkowskiartur.product;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.product.elements.ChoosePlanFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.Optional;

@Entity
@Data
@EqualsAndHashCode(of = "symbol")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String symbol;

    @Column(nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ProductType type;

    @Transient
    private Float valueUsd;




    public Product(String symbol, String name, ProductType type) throws CoinMetricsApiUnavailable {
        this.symbol = symbol;
        this.name = name;
        this.type = type;

        setValueUsd();

    }




    @PostLoad
    public void setValueUsd() throws CoinMetricsApiUnavailable {

        Optional<Float> coinPriceResponse = ChoosePlanFactory.choosePlan(this.type).getProductPrice(this.symbol);

        if (coinPriceResponse.isPresent())
            this.valueUsd = coinPriceResponse.get();
        else
            throw new CoinMetricsApiUnavailable("Coin metrics are currently unavailable");


    }




    public Product() {

    }
}
