package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.user.User;
import com.szatkowskiartur.utlis.Utils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode (of = "id")
@ToString (of = "id")
public class Portfolio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "user_id")
    private User owner;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "portfolio", cascade = CascadeType.REMOVE)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private List<PortfolioEntry> products;

    private Double creditUsd = 0.0;

    @Transient
    private Double totalValue = 0.0;




    public Portfolio (Long id, User owner, List<PortfolioEntry> products) {
        this.id = id;
        this.owner = owner;
        this.products = products;
        setTotalValue();
    }




    @PostLoad
    public void setTotalValue () {

        Double sum = products.stream()
                .mapToDouble(entry -> {

                    Product product = entry.getProduct();

                    try {
                        product.setValueUsd();
                    } catch (CoinMetricsApiUnavailable e) {
                        e.printStackTrace();
                    }

                    return product.getValueUsd() * entry.getAmount();
                })
                .sum();

        totalValue = Utils.roundToTwoDecimalPlaces(sum);
    }




    public Portfolio (User owner) {
        this.owner = owner;
    }




    public Portfolio () {
    }
}
