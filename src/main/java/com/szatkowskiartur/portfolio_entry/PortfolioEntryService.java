package com.szatkowskiartur.portfolio_entry;

import java.util.Optional;

public interface PortfolioEntryService {

    void savePortfolioEntry (PortfolioEntry portfolioEntry);

    Optional<PortfolioEntry> getPortfolioEntryByPortfolioAndProduct (Long portfolioId, Long productId);
}
