package com.digitus.homework.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.digitus.homework.model.LoggedStatus;
import com.digitus.homework.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

@Component
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {

    @Autowired
    private UserService userService;

    public CustomLogoutSuccessHandler() {
        super();
    }

    // API

    @Override
    public void onLogoutSuccess(final HttpServletRequest request, final HttpServletResponse response,
                                final Authentication authentication) throws IOException, ServletException {
        org.springframework.security.core.userdetails.User loggedInUser = ( org.springframework.security.core.userdetails.User)authentication.getPrincipal();
        com.digitus.homework.model.User userFromDb = userService.findUserByUserName(loggedInUser.getUsername());
        userFromDb.setLoggedIn(LoggedStatus.LOGGED_OUT.value);
        userService.saveUser(userFromDb);

        super.onLogoutSuccess(request, response, authentication);
    }

}
