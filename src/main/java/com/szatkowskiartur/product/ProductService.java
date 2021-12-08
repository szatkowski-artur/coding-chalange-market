package com.szatkowskiartur.product;

import java.util.List;
import java.util.Optional;

public interface ProductService {

    public List<Product> getAllProducts();

    public Optional<Product> getProductMetricsBySymbol(String symbol);

    public Optional<Product> getProductById (Long id);

}
