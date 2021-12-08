package com.szatkowskiartur.product.elements;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;


public class CoinPlan implements Plan {

    private final String baseUriV1 = "https://data.messari.io/api/v1/";




    @Override
    public Optional<Float> getProductPrice(String symbol) {


        try {
            ResponseEntity<String> response = getCoinDataFromApi(symbol);
            return Optional.of(getPriceFromJson(getBodyFromResponse(response)));
        } catch (HttpClientErrorException e) {
            return Optional.empty();
        }

    }




    public ResponseEntity<String> getCoinDataFromApi(String coinSymbol) throws HttpClientErrorException{

            RestTemplate rest = new RestTemplate();
            return rest.getForEntity(baseUriV1 + String.format("assets/%s/metrics", coinSymbol), String.class);
    }




    public JsonNode getBodyFromResponse(ResponseEntity<String> response) {

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readTree(response.getBody());
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return null;

    }




    public boolean checkIfResponseCorrect(ResponseEntity<String> response) {
        return response.getStatusCodeValue() == 200;
    }




    public Float getPriceFromJson(JsonNode body) {
        return body.path("data")
                .path("market_data")
                .path("price_usd")
                .floatValue();
    }

}
