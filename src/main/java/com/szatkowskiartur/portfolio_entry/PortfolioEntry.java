package com.szatkowskiartur.portfolio_entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.product.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
//todo Change to correct implementation https://stackoverflow.com/questions/23837561/jpa-2-0-many-to-many-with-extra-column
@Entity
@Data
@EqualsAndHashCode (of = "id")
public class PortfolioEntry {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn (name = "portfolio_id")
    @JsonIgnore
    private Portfolio portfolio;

    @ManyToOne (fetch = FetchType.EAGER)
    @JoinColumn (name = "product_id")
    private Product product;

    private Float amount;


}
