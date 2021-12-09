package com.szatkowskiartur.portfolio_entry;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.product.Product;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Fetch;

import javax.persistence.*;

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
