package com.szatkowskiartur.portfolio;

import java.util.Optional;

public interface PortfolioService {
    Optional<Portfolio> getPortfolioByUser (Long userId);
}
