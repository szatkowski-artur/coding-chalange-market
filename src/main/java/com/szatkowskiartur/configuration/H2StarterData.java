package com.szatkowskiartur.configuration;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.product.ProductRepository;
import com.szatkowskiartur.product.ProductType;
import com.szatkowskiartur.user.User;
import com.szatkowskiartur.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class H2StarterData implements ApplicationRunner {

    private final UserRepository userRepository;
    private final ProductRepository productRepository;


    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        createUser("1");
        createUser("2");

        createProduct("eee");
        createProduct("eth");
    }

    public void createProduct(String variable) {

        Product product = null;

        try {
            product = new Product(variable.toUpperCase(), variable, ProductType.COIN);
            productRepository.save(product);
        } catch (CoinMetricsApiUnavailable coinMetricsApiUnavailable) {
            coinMetricsApiUnavailable.printStackTrace();
        }


    }

    public void createUser (String variable) {

        User user = new User();
        Portfolio portfolio = new Portfolio(user);

        user.setName(String.format("user%s", variable));
        user.setSurname(String.format("surname%s", variable));
        user.setBirthday(LocalDate.of(1977,2, 23));
        user.setEmail(String.format("email%s@email.com", variable));
        user.setPassword(String.format("password%s", variable));
        user.setPortfolio(portfolio);

        userRepository.save(user);

    }

}
