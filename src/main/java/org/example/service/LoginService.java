package org.example.service;

import org.example.entity.User;

public interface LoginService {
    boolean loginAuthenticate(User user);
}