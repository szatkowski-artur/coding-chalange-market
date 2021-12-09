package com.szatkowskiartur.product;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.exception.NotFoundException;

import static com.szatkowskiartur.utlis.Utils.*;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@RequestMapping("product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductServiceImpl productService;
    private final ModelMapper mapper;




    @GetMapping
    public ResponseEntity<List<String>> getAllProduct() {
        return new ResponseEntity<>(
                productService.getAllProducts().stream().map(Product::getSymbol).collect(Collectors.toList()),
                HttpStatus.OK);
    }




    @GetMapping("/metrics")
    public ResponseEntity<List<ProductDTO>> getAllProductsMetrics() {

        List<ProductDTO> products = new ArrayList<>();
        mapper.map(productService.getAllProducts(), products);

        return new ResponseEntity<>(products, HttpStatus.OK);
    }




    @GetMapping("/{symbol}/metrics")
    public ResponseEntity<ProductDTO> getProductMetricsById(@PathVariable String symbol) {

        Optional<Product> productDatabase = productService.getProductMetricsBySymbol(symbol.toUpperCase());
        if (productDatabase.isEmpty())
            throw new NotFoundException("Product with given symbol does not exist");

        return new ResponseEntity<>(mapper.map(productDatabase.get(), ProductDTO.class), HttpStatus.FOUND);
    }




    @GetMapping("/id/{id}/metrics")
    public ResponseEntity<ProductDTO> getProductMetricsById(@PathVariable Long id) {

        Optional<Product> productDatabase = productService.getProductById(id);
        if (productDatabase.isEmpty())
            throw new NotFoundException("Product with given id does not exist");

        return new ResponseEntity<>(mapper.map(productDatabase.get(), ProductDTO.class), HttpStatus.OK);
    }




    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createJsonWithMessage(exception.getMessage()));

    }




    @ExceptionHandler(CoinMetricsApiUnavailable.class)
    public ResponseEntity<String> handleCoinMetricsApiUnavailable(CoinMetricsApiUnavailable exception) {

        return ResponseEntity
                .status(HttpStatus.SERVICE_UNAVAILABLE)
                .body(createJsonWithMessage("Coin metrics service is currently unavailable"));
    }


}
