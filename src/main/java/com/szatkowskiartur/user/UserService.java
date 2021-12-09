package com.szatkowskiartur.user;

public interface UserService {

    public User createUser(User user);

    public boolean blockUser(Long id);

}
