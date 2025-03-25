package uta.ec.finance_manager.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.impl.CustomUserDetailsService;
import uta.ec.finance_manager.util.JwtUtil;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public UserDto registerUser(@RequestBody SaveUserDto request) {
        request.setPassword(passwordEncoder.encode(request.getPassword()));

        User user = modelMapper.map(request, User.class);


        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @PostMapping("/login")
    public String loginUser(@RequestBody AuthenticationRequest authenticationRequest) throws Exception {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        return jwtUtil.generateToken(userDetails);
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello, World!";
    }
}