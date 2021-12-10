package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotEnoughCreditException;
import com.szatkowskiartur.exception.NotFoundException;

import java.util.Optional;

public interface PortfolioService {
    Optional<Portfolio> getPortfolioForUser (Long userId);

    Optional<Portfolio> getPortfolioById (Long id);

    void buyProduct (Long userId, Long productId, Float amount) throws NotFoundException, NotEnoughCreditException;
}
