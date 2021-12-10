package com.szatkowskiartur.user;

import java.util.Optional;

public interface UserService {

    Optional<User> getUserByEmail (String email);

    public User createUser(User user);

    public boolean blockUser(Long id);

}
