package com.goodmortician.notes.web;

import com.goodmortician.notes.auth.AuthenticateService;
import com.goodmortician.notes.web.dto.JwtRequest;
import com.goodmortician.notes.web.dto.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping (value = "/auth")
public class AuthenticateResource {
    private final AuthenticateService authenticateService;

    public AuthenticateResource(AuthenticateService authenticateService) {
        this.authenticateService = authenticateService;
    }

    @PostMapping (value = "/authenticate")
    public ResponseEntity <JwtResponse> createAuthenticationToken (@RequestBody JwtRequest authenticationRequest){
        String token = authenticateService.authenticate(authenticationRequest);
        return ResponseEntity.ok(new JwtResponse("Bearer " + token));
    }
}
