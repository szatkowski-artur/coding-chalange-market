package com.szatkowskiartur.product;

import com.szatkowskiartur.product.elements.ChoosePlanFactory;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

@Entity
@Data
@EqualsAndHashCode (of = "id")
public class Product {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private String symbol;

    private String name;

    @Enumerated (EnumType.STRING)
    private ProductType type;

    @Transient
    private Float value = ChoosePlanFactory.choosePlan(type).getPrice(symbol);


}
