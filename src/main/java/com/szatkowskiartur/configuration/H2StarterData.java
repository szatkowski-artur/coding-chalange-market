package com.szatkowskiartur.configuration;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.portfolio.PortfolioRepository;
import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.portfolio_entry.PortfolioEntryRepository;
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
    private final PortfolioRepository portfolioRepository;
    private final ProductRepository productRepository;
    private final PortfolioEntryRepository portfolioEntryRepository;




    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {

        Product btc = createProduct("btc");
        Product eth = createProduct("eth");

        User user1 = createUser("1");
        Portfolio portfolio1 = createPortfolio(user1);
        createPortfolioEntry(portfolio1, btc, 0.0001234F);
        createPortfolioEntry(portfolio1, eth, 0.0890F);

        User user2 = createUser("2");
        Portfolio portfolio2 = createPortfolio(user2);
        createPortfolioEntry(portfolio2, eth, 5.98731F);


    }




    public void createPortfolioEntry(Portfolio portfolio, Product product, Float amount) {

        PortfolioEntry portfolioEntry = new PortfolioEntry();

        portfolioEntry.setPortfolio(portfolio);
        portfolioEntry.setProduct(product);
        portfolioEntry.setAmount(amount);

        portfolioEntryRepository.save(portfolioEntry);

    }




    public Portfolio createPortfolio(User user) {

        return portfolioRepository.save(new Portfolio(user));

    }




    public Product createProduct(String variable) {

        Product product = null;

        try {
            product = new Product(variable.toUpperCase(), variable, ProductType.COIN);
            return productRepository.save(product);
        } catch (CoinMetricsApiUnavailable coinMetricsApiUnavailable) {
            coinMetricsApiUnavailable.printStackTrace();
        }

        return product;
    }




    public User createUser(String variable) {

        User user = new User();


        user.setName(String.format("user%s", variable));
        user.setSurname(String.format("surname%s", variable));
        user.setBirthday(LocalDate.of(1977, 2, 23));
        user.setEmail(String.format("email%s@email.com", variable));
        user.setPassword(String.format("password%s", variable));
        user.setActive(true);
        return userRepository.save(user);


    }

}
