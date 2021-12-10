package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping ("/admin/portfolio")
@Secured ("ROLE_ADMIN")
@RequiredArgsConstructor
public class PortfolioAdminController {

    private final PortfolioServiceImpl portfolioService;
    private final ModelMapper mapper;




    @GetMapping ("/id/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioById (@PathVariable Long id) {

        Optional<Portfolio> portfolioDb = portfolioService.getPortfolioById(id);

        //todo Change all methods to optional.map().orElse()
        if (portfolioDb.isEmpty())
            throw new NotFoundException("Portfolio not found");


        return new ResponseEntity<>(mapper.map(portfolioDb.get(), PortfolioDTO.class), HttpStatus.OK);

    }




    @GetMapping ("/user/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioByUserId (@PathVariable Long id) {

        Optional<Portfolio> portfolioDb = portfolioService.getPortfolioForUser(id);

        if (portfolioDb.isEmpty())
            throw new NotFoundException("Portfolio not found");


        return new ResponseEntity<>(mapper.map(portfolioDb.get(), PortfolioDTO.class), HttpStatus.OK);

    }


}
