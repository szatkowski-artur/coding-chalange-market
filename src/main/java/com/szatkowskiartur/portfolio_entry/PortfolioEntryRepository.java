package com.szatkowskiartur.portfolio_entry;

import com.szatkowskiartur.portfolio.Portfolio;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PortfolioEntryRepository extends CrudRepository<PortfolioEntry, Long> {

    List<PortfolioEntry> findAllByPortfolio(Portfolio portfolio);


}
