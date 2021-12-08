package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@EqualsAndHashCode (of = "id")
public class Portfolio {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User owner;

    @OneToMany (fetch = FetchType.EAGER, mappedBy = "portfolio")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private List<PortfolioEntry> products;

    //todo Check if sums correctly, might not give zero value
    @Transient
    private Double totalValue;


    public Portfolio(Long id, User owner, List<PortfolioEntry> products) {
        this.id = id;
        this.owner = owner;
        this.products = products;
        this.totalValue = products.stream()
                .mapToDouble(entry -> entry.getProduct().getValueUsd() * entry.getAmount())
                .sum();
    }

    public Portfolio(User owner) {
        this.owner = owner;
    }



    public Portfolio() {

    }
}
