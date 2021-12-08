package com.szatkowskiartur.user;

import com.szatkowskiartur.portfolio.Portfolio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        user.setPortfolio(new Portfolio(user));

        User createdUser = userService.createUser(user);
        createdUser.setPassword("");

        return new ResponseEntity<>(mapper.map(createdUser, UserDTO.class), HttpStatus.CREATED);

    }

}
