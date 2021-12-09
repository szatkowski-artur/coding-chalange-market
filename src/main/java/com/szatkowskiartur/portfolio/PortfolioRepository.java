package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PortfolioRepository extends CrudRepository<Portfolio, Long> {

    Optional<Portfolio> findByOwner (User user);

}
