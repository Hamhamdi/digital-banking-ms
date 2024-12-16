package net.hamdi.securityservice.controller;

import net.hamdi.securityservice.dto.TokenDetails;
import net.hamdi.securityservice.service.TokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/api/tokens")
public class TokenIntrospection {

    private final TokenService tokenService;

    public TokenIntrospection(TokenService tokenService) {
        this.tokenService = tokenService;
    }


    @GetMapping("/introspect")
    public ResponseEntity<TokenDetails> introspect(@RequestParam String token) {
        TokenDetails details = tokenService.introspectToken(token);
        return ResponseEntity.ok(details);
    }
}
