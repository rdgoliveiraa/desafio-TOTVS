package br.com.totvs.desafio.infrastructure.inbound.authentication;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.totvs.desafio.infrastructure.inbound.authentication.request.UserCredentialsDTO;
import br.com.totvs.desafio.infrastructure.inbound.authentication.response.TokenDTO;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/signin")
    public ResponseEntity<TokenDTO> signin(@RequestBody  UserCredentialsDTO credenciais) {
        return ResponseEntity.ok(authenticationService.signIn(credenciais));
    }

    @PutMapping("/refresh/{username}")
    public ResponseEntity<TokenDTO> refreshToken(@PathVariable("username") String username, @RequestHeader("Authorization") String refreshToken) {
        return ResponseEntity.ok(authenticationService.refreshToken(username, refreshToken));
    }

}