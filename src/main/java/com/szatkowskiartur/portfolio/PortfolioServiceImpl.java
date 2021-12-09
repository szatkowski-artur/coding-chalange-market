package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.user.User;
import com.szatkowskiartur.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PortfolioServiceImpl implements PortfolioService {

    private final PortfolioRepository portfolioRepository;
    private final UserRepository userRepository;

    public Optional<Portfolio> getPortfolioByUser(Long userId) {

        return portfolioRepository.findByOwner(userRepository.findById(userId).get());
    }

}
