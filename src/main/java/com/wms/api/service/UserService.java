package com.wms.api.service;

import com.wms.api.model.User;

public interface UserService {
    User save(User user);
    User findByLogin(String login);
}
