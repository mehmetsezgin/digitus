package com.digitus.homework.service;

import com.digitus.homework.model.Role;
import com.digitus.homework.model.Token;
import com.digitus.homework.model.User;
import com.digitus.homework.model.UserStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userService.findUserByUserName(userName);

        if(user == null){
            throw new BadCredentialsException("Username not found");
        }

        if(user.getActive() == 0){
            throw new DisabledException("User is disabled");
        }

        List<GrantedAuthority> authorities = getUserAuthority(user.getRoles());
        return buildUserForAuthentication(user, authorities);
    }

    private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
        Set<GrantedAuthority> roles = new HashSet<GrantedAuthority>();
        for (Role role : userRoles) {
            roles.add(new SimpleGrantedAuthority(role.getRole()));
        }
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>(roles);
        return grantedAuthorities;
    }

    private UserDetails buildUserForAuthentication(User user, List<GrantedAuthority> authorities) {
        boolean status = user.getActive() == 0 ? false : true;
        return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
                status, true, true, true, authorities);
    }

    public void registerUser(User user) {
        //at registration step user is disabled.Email confirmation will enable it
        user.setActive(UserStatus.INACTIVE.value);
        userService.saveUser(user);

        Token confirmationToken = new Token(user);
        tokenService.saveToken(confirmationToken);
        emailService.sendEmail(user.getEmail(), confirmationToken.getToken());

    }

    public void confirmUser(Token confirmationToken) {
        User dbUser = confirmationToken.getUser();
        dbUser.setActive(UserStatus.ACTIVE.value);
        dbUser.setRegistrationCompleteTime(LocalDateTime.now());
        userService.updateUser(dbUser);
        tokenService.deleteToken(confirmationToken.getId());
    }
}
