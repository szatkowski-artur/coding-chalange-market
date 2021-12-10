package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotEnoughCreditException;
import com.szatkowskiartur.exception.NotEnoughProductAmountException;
import com.szatkowskiartur.exception.NotFoundException;
import com.szatkowskiartur.portfolio_entry.PortfolioEntry;
import com.szatkowskiartur.portfolio_entry.PortfolioEntryServiceImpl;
import com.szatkowskiartur.product.Product;
import com.szatkowskiartur.product.ProductServiceImpl;
import com.szatkowskiartur.user.UserRepository;

import static com.szatkowskiartur.utlis.Utils.roundToTwoDecimalPlaces;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;
    private final ProductServiceImpl productService;
    private final PortfolioEntryServiceImpl portfolioEntryService;




    @Override
    public Optional<Portfolio> getPortfolioForUser (Long userId) {

        return portfolioRepository.findByOwner(userRepository.findById(userId).get());
    }




    @Override
    public Optional<Portfolio> getPortfolioById (Long id) {
        return portfolioRepository.findById(id);
    }




    public void sellProduct (Long userId, Long productId, Float amount) throws NotEnoughProductAmountException {

        Product product = getProductByIdCheckIfExist(productId);
        Portfolio portfolio = getPortfolioForUserCheckIfExist(userId);

        Float price = calculatePurchaseValue(product, amount);

        Optional<PortfolioEntry> portfolioEntryDb = getPortfolioEntryFromPortfolio(portfolio, product);

        if (portfolioEntryDb.isEmpty())
            throw new NotFoundException("This product is not present in this portfolio");
        else {

            PortfolioEntry entry = portfolioEntryDb.get();
            checkIfEnoughAmountForSell(entry, amount);

            entry.setAmount(entry.getAmount() - amount);
            portfolioEntryService.savePortfolioEntry(entry);

            portfolio.setCreditUsd(portfolio.getCreditUsd() + price);
            portfolioRepository.save(portfolio);

        }


    }




    @Override
    public void buyProduct (Long userId, Long productId, Float amount) throws NotFoundException, NotEnoughCreditException {

        Product product = getProductByIdCheckIfExist(productId);
        Portfolio portfolio = getPortfolioForUserCheckIfExist(userId);

        Float price = calculatePurchaseValue(product, amount);
        checkIfEnoughCreditForPurchase(price, portfolio);

        Optional<PortfolioEntry> portfolioEntryDb = getPortfolioEntryFromPortfolio(portfolio, product);

        PortfolioEntry entry;

        if (portfolioEntryDb.isPresent()) {

            entry = portfolioEntryDb.get();
            entry.setAmount(entry.getAmount() + amount);

        } else
            entry = createPortfolioEntry(portfolio, product, amount);


        portfolioEntryService.savePortfolioEntry(entry);

        portfolio.setCreditUsd(roundToTwoDecimalPlaces(portfolio.getCreditUsd() - price));
        portfolioRepository.save(portfolio);

    }




    private Portfolio getPortfolioForUserCheckIfExist (Long userId) {

        Optional<Portfolio> portfolioDb = getPortfolioForUser(userId);
        checkIfRecordExist(portfolioDb, "Portfolio");
        return portfolioDb.get();

    }




    private Product getProductByIdCheckIfExist (Long productId) {

        Optional<Product> productDb = productService.getProductById(productId);
        checkIfRecordExist(productDb, "Product");
        return productDb.get();

    }




    private void checkIfRecordExist (Optional<? extends Object> record, String recordName) throws NotFoundException {

        if (record.isEmpty())
            throw new NotFoundException(String.format("%s not found", recordName));
    }




    private PortfolioEntry createPortfolioEntry (Portfolio portfolio, Product product, Float amount) {

        PortfolioEntry entry = new PortfolioEntry();
        entry.setPortfolio(portfolio);
        entry.setProduct(product);
        entry.setAmount(amount);

        return entry;
    }




    private void checkIfEnoughCreditForPurchase (Float purchaseValue, Portfolio portfolio) throws NotEnoughCreditException {
        if (purchaseValue > portfolio.getCreditUsd())
            throw new NotEnoughCreditException("There is not enough credit in this portfolio to buy this product");
    }




    private void checkIfEnoughAmountForSell (PortfolioEntry entry, Float amount) throws NotEnoughProductAmountException {
        if (entry.getAmount() < amount)
            throw new NotEnoughProductAmountException("There is not enough product to sell this amount");
    }




    private Float calculatePurchaseValue (Product product, Float amount) {
        return product.getValueUsd() * amount;
    }




    private Optional<PortfolioEntry> getPortfolioEntryFromPortfolio (Portfolio portfolio, Product product) {

        return portfolio.getProducts().stream().filter(entry -> entry.getProduct().equals(product)).findAny();

    }


}
