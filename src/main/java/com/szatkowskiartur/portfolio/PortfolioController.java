package com.szatkowskiartur.portfolio;

import com.szatkowskiartur.exception.NotEnoughCreditException;
import com.szatkowskiartur.exception.NotEnoughProductAmountException;
import com.szatkowskiartur.exception.NotFoundException;
import com.szatkowskiartur.user.User;
import com.szatkowskiartur.user.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.szatkowskiartur.utlis.Utils.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping ("/portfolio")
public class PortfolioController {

    public final PortfolioServiceImpl portfolioService;
    public final ModelMapper mapper;
    private final UserService userService;




    @GetMapping
    public ResponseEntity<PortfolioDTO> getPortfolioForUser (Principal principal) {


        Optional<Portfolio> portfolioDb = portfolioService.getPortfolioForUser(
                getCurrentSessionUserId(principal));

        if (portfolioDb.isEmpty())
            throw new NotFoundException("This user does not have an active portfolio");


        return new ResponseEntity<>(mapper.map(portfolioDb.get(), PortfolioDTO.class), HttpStatus.OK);

    }




    @PostMapping ("/sell")
    public ResponseEntity<String> sellProduct (Principal principal, @RequestParam Long productId, @RequestParam Float amount) throws NotEnoughProductAmountException {

        portfolioService.sellProduct(getCurrentSessionUserId(principal), productId, amount);

        return new ResponseEntity<>(createJsonWithMessage("Sell successful"), HttpStatus.OK);
    }




    @PostMapping ("/buy")
    public ResponseEntity<String> buyProduct (Principal principal, @RequestParam Long productId, @RequestParam Float amount) throws NotEnoughCreditException {

        portfolioService.buyProduct(getCurrentSessionUserId(principal), productId, amount);

        return new ResponseEntity<>(createJsonWithMessage("Purchase successful"), HttpStatus.OK);

    }




    private Long getCurrentSessionUserId (Principal principal) {
        Optional<User> userDb = userService.getUserByEmail(principal.getName());

        if (userDb.isEmpty())
            throw new NotFoundException("User does not exist");

        return userDb.get().getId();
    }




    @ExceptionHandler (NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException (NotFoundException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(createJsonWithMessage(exception.getMessage()));

    }




    @ExceptionHandler (NotEnoughCreditException.class)
    public ResponseEntity<String> handleNotEnoughCreditException (NotEnoughCreditException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(createJsonWithMessage(exception.getMessage()));

    }




    @ExceptionHandler (NotEnoughProductAmountException.class)
    public ResponseEntity<String> handleNotEnoughAmountException (NotEnoughProductAmountException exception) {

        return ResponseEntity
                .status(HttpStatus.NOT_ACCEPTABLE)
                .body(createJsonWithMessage(exception.getMessage()));

    }

}
