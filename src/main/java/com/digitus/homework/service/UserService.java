package com.digitus.homework.service;

import com.digitus.homework.model.Role;
import com.digitus.homework.model.User;
import com.digitus.homework.repository.RoleRepository;
import com.digitus.homework.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

@Service
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User findUserByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Transactional
    public User saveUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Role userRole = roleRepository.findByRole("USER");
        user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
        return userRepository.save(user);
    }

    @Transactional
    public User updateUser(User dbUser) {
        return userRepository.save(dbUser);
    }

    @Transactional
    public Long getNumberOfUsers(){
        return userRepository.count();
    }

    @Transactional
    public Long getNumberOfUsersByStatus(int status){
        long count = userRepository.countByLoggedStatus(status);
        return count;
    }

    public Integer countNewUsersIn24Hours(){
        return userRepository.countNewUsersIn24Hours();
    }


}