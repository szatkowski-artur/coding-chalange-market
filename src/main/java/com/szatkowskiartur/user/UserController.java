package com.szatkowskiartur.user;

import com.szatkowskiartur.portfolio.Portfolio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import static com.szatkowskiartur.utlis.Utils.*;

import javax.validation.Valid;


@RestController
@RequestMapping(path = "user")
@RequiredArgsConstructor
public class UserController {

    private final PasswordEncoder passwordEncoder;
    private final UserServiceImpl userService;




    @PostMapping(path = "/add")
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserDTO userDTO) {

        ModelMapper mapper = new ModelMapper();
        User user = mapper.map(userDTO, User.class);

        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
//        user.setPortfolio(new Portfolio(user));
        user.setActive(true);

        User createdUser = userService.createUser(user);
        createdUser.setPassword("");

        return new ResponseEntity<>(mapper.map(createdUser, UserDTO.class), HttpStatus.CREATED);

    }




    @PostMapping(path = "/block/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<String> blockUser(@PathVariable Long id) {

        if (userService.blockUser(id))
            return new ResponseEntity<>(createJsonWithMessage("User blocked"), HttpStatus.OK);
        else
            return new ResponseEntity<>(createJsonWithMessage("User does not exist"), HttpStatus.NOT_FOUND);


    }

}
