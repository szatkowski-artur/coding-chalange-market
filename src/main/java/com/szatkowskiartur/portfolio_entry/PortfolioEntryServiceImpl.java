package com.szatkowskiartur.portfolio_entry;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioEntryServiceImpl implements PortfolioEntryService {

    private final PortfolioEntryRepository portfolioEntryRepository;

    @Override
    public void savePortfolioEntry (PortfolioEntry portfolioEntry) {
        portfolioEntryRepository.save(portfolioEntry);
    }

    @Override
    public Optional<PortfolioEntry> getPortfolioEntryByPortfolioAndProduct(Long portfolioId, Long productId) {

        return portfolioEntryRepository.findByPortfolioAndProduct(portfolioId, productId);

    }

}
