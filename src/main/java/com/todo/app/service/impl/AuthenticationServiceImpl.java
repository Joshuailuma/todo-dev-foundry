package com.todo.app.service.impl;

import com.todo.app.configuration.appConfig.JwtService;
import com.todo.app.entity.Token;
import com.todo.app.entity.User;
import com.todo.app.exception.ApiError;
import com.todo.app.mapper.RegisteredUserMapper;
import com.todo.app.model.*;
import com.todo.app.repository.TokenRepository;
import com.todo.app.repository.UserRepository;
import com.todo.app.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.todo.app.utils.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RegisteredUserMapper registeredUserMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final TokenRepository tokenRepository;

    @Override
    public AppResponse register(RegistrationRequest request) {

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .build();

        try {
        userRepository.save(user);
        } catch (Exception e) {
            throw new ApiError(REGISTRATION_FAILED, HttpStatus.BAD_REQUEST);
        }

        RegisteredUser userMapper = registeredUserMapper.toRegisteredUser(user);
        return AppResponse.builder()
                .message(REGISTRATION_SUCCESSFUL)
                .result(userMapper).build();
    }

    @Override
    public AppResponse login(LoginRequest loginRequest) {

        User userFromDb = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new ApiError("User not found with email: " + loginRequest.getEmail(),
                HttpStatus.NOT_FOUND));
        if (!passwordEncoder.matches(loginRequest.getPassword(), userFromDb.getPassword())) {
            throw new ApiError(INCORRECT_PASSWORD, HttpStatus.BAD_REQUEST);
        }

      Authentication authInfo = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()));
        log.info("Information is " +authInfo);

        String jwtToken = jwtService.generateToken(userFromDb);

        revokeAllToken(userFromDb);

        saveUserToken(userFromDb, jwtToken);

        Long expiryTime = jwtService.getJwtExpiration();
        AuthenticationResponse authenticationResponse = AuthenticationResponse
                .builder()
                .token(jwtToken)
                .expiresAt(expiryTime)
                .build();

        return new AppResponse(LOGIN_SUCCESSFULL, authenticationResponse);
    }

    private void saveUserToken(User savedUser, String jwtToken) {
        Token token = Token.builder()
                .token(jwtToken)
                .users(savedUser)
                .isExpired(false)
                .isRevoked(false)
                .build();
        tokenRepository.save(token);
    }

    private void revokeAllToken(User user) {
        List<Token> tokenList = tokenRepository.findAllValidTokenByUser(user.getId());
        if (tokenList.isEmpty()) {
            return;
        }
        for (Token token : tokenList) {
            token.setRevoked(true);
            token.setExpired(true);
            tokenRepository.saveAll(tokenList);
        }
    }
}
