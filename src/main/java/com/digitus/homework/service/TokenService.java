package com.digitus.homework.service;

import com.digitus.homework.model.Token;
import com.digitus.homework.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
public class TokenService {

    @Autowired
    private TokenRepository tokenRepository;

    @Transactional
    public void saveToken(Token token) {

        tokenRepository.save(token);
    }

    @Transactional
    public void deleteToken(Long id) {

        tokenRepository.deleteById(id);
    }

    @Transactional
    public Optional<Token> findToken(String token) {

        return tokenRepository.findByToken(token);
    }

    @Transactional
    public Integer countExpiredConfirmations(){
        return tokenRepository.countExpiredConfirmations();
    }
}
