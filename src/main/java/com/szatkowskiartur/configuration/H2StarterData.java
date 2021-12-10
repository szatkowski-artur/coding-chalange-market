package com.szatkowskiartur.configuration;

import com.szatkowskiartur.exception.CoinMetricsApiUnavailable;
import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.portfolio.PortfolioRepository;
import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.portfolio_entry.PortfolioEntryRepository;
import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.product.ProductRepository;
import com.szatkowskiartur.product.ProductType;
import com.szatkowskiartur.role.Role;
import com.szatkowskiartur.role.RoleRepository;
import com.szatkowskiartur.user.User;
import com.szatkowskiartur.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
//todo try with CommandLineRunner
@Component
@RequiredArgsConstructor
public class H2StarterData implements ApplicationRunner {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;
    private final ProductRepository productRepository;
    private final PortfolioEntryRepository portfolioEntryRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;




    @Override
    @Transactional
    public void run(ApplicationArguments args) {

        Product btc = createProduct("btc");
        Product eth = createProduct("eth");

        Role userRole = createRole("USER");
        Role adminRole = createRole("ADMIN");

        User user1 = createUser("1", userRole);
        Portfolio portfolio1 = createPortfolio(user1);
        createPortfolioEntry(portfolio1, btc, 0.0001234F);
        createPortfolioEntry(portfolio1, eth, 0.0890F);

        User user2 = createUser("2", userRole);
        Portfolio portfolio2 = createPortfolio(user2);
        createPortfolioEntry(portfolio2, eth, 5.98731F);

        User admin = createUser("_admin", adminRole);
        admin.getRoles().add(userRole);
        userRepository.save(admin);

    }


    public Role createRole (String name) {

        Role role = new Role();
        role.setName("ROLE_" + name);
        return roleRepository.save(role);

    }




    public void createPortfolioEntry(Portfolio portfolio, Product product, Float amount) {

        PortfolioEntry portfolioEntry = new PortfolioEntry();

        portfolioEntry.setPortfolio(portfolio);
        portfolioEntry.setProduct(product);
        portfolioEntry.setAmount(amount);

        portfolioEntryRepository.save(portfolioEntry);

    }




    public Portfolio createPortfolio(User user) {

        Portfolio portfolio = new Portfolio(user);
        portfolio.setCreditUsd(1000.0);

        return portfolioRepository.save(portfolio);

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




    public User createUser(String variable, Role role) {

        User user = new User();


        user.setName(String.format("user%s", variable));
        user.setSurname(String.format("surname%s", variable));
        user.setBirthday(LocalDate.of(1977, 2, 23));
        user.setEmail(String.format("email%s@email.com", variable));
        user.setPassword(encoder.encode("password"));
        user.setActive(true);
        user.getRoles().add(role);
        return userRepository.save(user);


    }

}
