package com.szatkowskiartur.product;

import lombok.Data;

@Data
public class ProductDTO {

    private Long id;
    private String name;
    private ProductType type;
    private Float valueUsd;

}
