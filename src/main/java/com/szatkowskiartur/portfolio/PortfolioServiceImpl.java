package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotEnoughCreditException;
import com.szatkowskiartur.exception.NotFoundException;
import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.portfolio_entry.PortfolioEntryServiceImpl;
import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.product.ProductRepository;
import com.szatkowskiartur.product.ProductServiceImpl;
import com.szatkowskiartur.user.User;
import com.szatkowskiartur.user.UserRepository;
import static com.szatkowskiartur.utlis.Utils.roundToTwoDecimalPlaces;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final ProductServiceImpl productService;
    private final PortfolioEntryServiceImpl portfolioEntryService;




    @Override
    public Optional<Portfolio> getPortfolioByUser (Long userId) {

        return portfolioRepository.findByOwner(userRepository.findById(userId).get());
    }




    public Optional<Portfolio> getPortfolioById (Long id) {
        return portfolioRepository.findById(id);
    }




    public void buyProduct (Long portfolioId, Long productId, Float amount) throws NotFoundException, NotEnoughCreditException {

        Optional<Portfolio> portfolioDb = getPortfolioById(portfolioId);

        checkIfRecordExist(portfolioDb, "Portfolio");

        Optional<Product> productDb = productService.getProductById(productId);

        checkIfRecordExist(productDb, "Product");

        Product product = productDb.get();
        Portfolio portfolio = portfolioDb.get();

        Float price = calculatePurchaseValue(product, amount);

        checkIfEnoughCreditForPurchase(price, portfolio);

        Optional<PortfolioEntry> portfolioEntryOpt = getPortfolioEntryForProductId(portfolio, productId);

        PortfolioEntry entry;

        if (portfolioEntryOpt.isPresent()) {

            entry = portfolioEntryOpt.get();
            entry.setAmount(entry.getAmount() + amount);

        } else {

            entry = new PortfolioEntry();
            entry.setPortfolio(portfolio);
            entry.setProduct(product);
            entry.setAmount(amount);

        }

        portfolioEntryService.addPortfolioEntry(entry);

        portfolio.setCreditUsd(roundToTwoDecimalPlaces(portfolio.getCreditUsd() - price));
        portfolioRepository.save(portfolio);

    }




    private void checkIfEnoughCreditForPurchase (Float purchaseValue, Portfolio portfolio) throws NotEnoughCreditException {
        if (purchaseValue > portfolio.getCreditUsd())
            throw new NotEnoughCreditException("There is not enough credit in this portfolio to buy this product");
    }




    private Float calculatePurchaseValue (Product product, Float amount) {
        return product.getValueUsd() * amount;
    }




    private Optional<PortfolioEntry> getPortfolioEntryForProductId (Portfolio portfolio, Long productId) {

        return portfolio.getProducts().stream().filter(entry -> entry.getProduct().getId() == productId).findAny();

    }




    private void checkIfRecordExist (Optional<? extends Object> record, String recordName) throws NotFoundException {

        if (record.isEmpty())
            throw new NotFoundException(String.format("%s not found", recordName));
    }

}
