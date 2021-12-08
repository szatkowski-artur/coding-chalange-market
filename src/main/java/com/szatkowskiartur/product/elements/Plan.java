package com.szatkowskiartur.product.elements;

import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface Plan {

    Optional<Float> getProductPrice(String symbol);

    boolean checkIfResponseCorrect(ResponseEntity<String> response);

}
