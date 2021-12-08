package com.szatkowskiartur.product.elements;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public class StockPlan implements Plan {

    @Override
    public Optional<Float> getProductPrice(String symbol) {
        return null;
    }




    @Override
    public boolean checkIfResponseCorrect(ResponseEntity<String> response) {
        return false;
    }
}
