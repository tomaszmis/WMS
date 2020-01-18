package com.wms.api.service;

import com.wms.api.repository.RoleRepository;
import com.wms.api.repository.UserRepository;
import com.wms.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoleId(2);
        userRepository.save(user);
        return user;
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }
}
