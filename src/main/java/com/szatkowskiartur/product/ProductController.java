package com.szatkowskiartur.product;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;




    @GetMapping
    public ResponseEntity<List<String>> getAllProduct() {
        return new ResponseEntity<>(
                productService.getAllProducts().stream().map(Product::getSymbol).collect(Collectors.toList()),
                HttpStatus.OK);
    }




    @GetMapping("/metrics")
    public ResponseEntity<List<Product>> getAllProductsMetrics() {
        return new ResponseEntity<>(productService.getAllProducts(), HttpStatus.OK);
    }




    @GetMapping("/{symbol}/metrics")
    public ResponseEntity<Product> getProductMetricsBySymbol(@PathVariable String symbol) {

        Optional<Product> productDatabase = productService.getProductMetricsBySymbol(symbol.toUpperCase());
        if (productDatabase.isEmpty())
            throw new NotFoundException("Product with given symbol does not exist");

        return new ResponseEntity<>(productDatabase.get(), HttpStatus.FOUND);
    }




    @GetMapping("/id/{id}/metrics")
    public ResponseEntity<Product> getProductMetricsBySymbol(@PathVariable Long id) {

        Optional<Product> productDatabase = productService.getProductById(id);
        if (productDatabase.isEmpty())
            throw new NotFoundException("Product with given id does not exist");

        return new ResponseEntity<>(productDatabase.get(), HttpStatus.OK);
    }




    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createJsonWithMessage(exception.getMessage()));

    }




    @ExceptionHandler(CoinMetricsApiUnavailable.class)
    public ResponseEntity handleCoinMetricsApiUnavailable(CoinMetricsApiUnavailable exception) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createJsonWithMessage("Coin metrics service is currently unavailable"));
    }




    @SneakyThrows
    private String createJsonWithMessage(String message) {

        ObjectMapper mapper = new ObjectMapper();
        ObjectNode root = mapper.createObjectNode();
        root.put("message", message);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(root);
    }


}
