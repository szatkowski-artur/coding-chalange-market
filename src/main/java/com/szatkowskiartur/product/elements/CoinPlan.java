package com.szatkowskiartur.product.elements;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

public class CoinPlan implements Plan {

    private RestTemplate rest = new RestTemplate();

    @SneakyThrows
    @Override
    public Float getPrice(String symbol) {
        ResponseEntity<String> response = rest.getForEntity("https://data.messari.io/api/v1/assets/btc/metrics", String.class);
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode data = root.path("data");
        JsonNode marketData = data.path("market_data");
        JsonNode price = marketData.path("price_usd");

        return price.floatValue();
    }

}
