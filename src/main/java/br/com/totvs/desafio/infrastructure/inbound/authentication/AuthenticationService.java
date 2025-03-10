package br.com.totvs.desafio.infrastructure.inbound.authentication;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.totvs.desafio.infrastructure.inbound.authentication.request.UserCredentialsDTO;
import br.com.totvs.desafio.infrastructure.inbound.authentication.response.TokenDTO;
import br.com.totvs.desafio.infrastructure.outbound.authentication.UserRepository;
import br.com.totvs.desafio.infrastructure.security.jwt.JwtTokenProvider;

@Service
public class AuthenticationService {
    private final JwtTokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;

    public AuthenticationService(JwtTokenProvider tokenProvider, AuthenticationManager authenticationManager,
            UserRepository userRepository) {
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
    }

    public TokenDTO signIn(UserCredentialsDTO credentials) {
        try {
            var username = credentials.username();
            var password = credentials.password();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            var user = userRepository.findByUsername(username);

            if (user == null) {
                throw new UsernameNotFoundException("Username " + username + "not found");
            }

            return tokenProvider.createAccessToken(username, user.getRoles());
        } catch (Exception e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

    public TokenDTO refreshToken(String username,String refreshToken) {
        var user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("Username " + username + " not found");
        }

        return tokenProvider.refreshToken(refreshToken);
    }
}