package com.szatkowskiartur.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;


    public User createUser(User user) {
        return userRepository.save(user);
    }




}
