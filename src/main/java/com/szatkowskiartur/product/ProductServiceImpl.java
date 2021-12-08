package com.szatkowskiartur.product;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;




    public List<Product> getAllProducts() {

        return (List<Product>) productRepository.findAll();

    }




    public Optional<Product> getProductMetricsBySymbol(String symbol) {
        return productRepository.findBySymbol(symbol);
    }




    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }


}
