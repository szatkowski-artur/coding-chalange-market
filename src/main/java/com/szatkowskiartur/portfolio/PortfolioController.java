package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotEnoughCreditException;
import com.szatkowskiartur.exception.NotFoundException;
import com.szatkowskiartur.user.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import static com.szatkowskiartur.utlis.Utils.*;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("/portfolio")
public class PortfolioController {

    public final PortfolioServiceImpl portfolioService;
    public final ModelMapper mapper;


    @GetMapping("/id/{id}")
    public ResponseEntity<PortfolioDTO> getportfolioById(@PathVariable Long id) {

        Optional<Portfolio> portfolioDb = portfolioService.getPortfolioById(id);

        if (portfolioDb.isEmpty())
            throw new NotFoundException("Portfolio not found");


        return new ResponseEntity<>(mapper.map(portfolioDb.get(), PortfolioDTO.class), HttpStatus.OK);

    }




    @GetMapping("/{id}")
    public ResponseEntity<PortfolioDTO> getPortfolioForUser(@PathVariable Long id) {

        //todo Set up security
//        User user = (User) SecurityContextHolder.getContext().getAuthentication().getDetails();


        Optional<Portfolio> portfolioDb = portfolioService.getPortfolioByUser(id);

        if (portfolioDb.isEmpty())
            throw new NotFoundException("This user does not have an active portfolio");


        return new ResponseEntity<>(mapper.map(portfolioDb.get(), PortfolioDTO.class), HttpStatus.OK);

    }


    @PostMapping("/{id}/buy")
    public ResponseEntity<String> buyProduct(@PathVariable Long id, @RequestParam Long productId, @RequestParam Float amount) throws NotEnoughCreditException {

        portfolioService.buyProduct(id, productId, amount);

        return new ResponseEntity<>(createJsonWithMessage("Purchase successful"), HttpStatus.OK);

    }




    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createJsonWithMessage(exception.getMessage()));

    }

    @ExceptionHandler(NotEnoughCreditException.class)
    public ResponseEntity<String> handleNotEnoughCredit(NotEnoughCreditException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(createJsonWithMessage(exception.getMessage()));

    }

}
