package com.szatkowskiartur.user;

import com.szatkowskiartur.portfolio.Portfolio;
import com.szatkowskiartur.portfolio.PortfolioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PortfolioRepository portfolioRepository;


    public User createUser(User user) {

        portfolioRepository.save(new Portfolio(user));

        return userRepository.save(user);
    }




    @Override
    public boolean blockUser(Long id) {

        final boolean[] userExist = {false};
        Optional<User> userDb = userRepository.findById(id);

        userDb.ifPresent(user -> {
            userExist[0] = true;
            user.setActive(false);
            userRepository.save(user);
        });

        return userExist[0];

    }


}
