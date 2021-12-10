package com.szatkowskiartur.portfolio_entry;

import com.szatkowskiartur.portfolio.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PortfolioEntryRepository extends CrudRepository<PortfolioEntry, Long> {

    List<PortfolioEntry> findAllByPortfolio(Portfolio portfolio);

    Optional<PortfolioEntry> findByPortfolioAndProduct(Long portfolioId, Long productId);


}
