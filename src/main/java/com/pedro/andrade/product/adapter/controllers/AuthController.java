package com.pedro.andrade.product.adapter.controllers;

import com.pedro.andrade.product.application.dtos.security.LoginDTO;
import com.pedro.andrade.product.application.dtos.security.LoginReponseDTO;
import com.pedro.andrade.product.application.dtos.security.RegisterDTO;
import com.pedro.andrade.product.domain.models.User;
import com.pedro.andrade.product.infrastructure.repositories.UserRepository;
import com.pedro.andrade.product.infrastructure.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid LoginDTO authenticationDto) {
        var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDto.email(), authenticationDto.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User) auth.getPrincipal());

        return ResponseEntity.ok(new LoginReponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody @Valid RegisterDTO registerDto) {
        if (this.userRepository.findByEmail(registerDto.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDto.password());
        User newUser = new User(registerDto.email(), encryptedPassword, registerDto.role());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
