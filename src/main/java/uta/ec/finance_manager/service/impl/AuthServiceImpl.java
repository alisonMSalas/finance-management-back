package uta.ec.finance_manager.service.impl;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.repository.UserRepository;
import uta.ec.finance_manager.service.AuthService;
import uta.ec.finance_manager.util.JwtUtil;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;

    @Override
    public UserDto register(SaveUserDto request) {
        User existantUser = userRepository.findByEmail(request.getEmail());

        if (existantUser != null) {
            throw new ResponseStatusException(
                    HttpStatus.PRECONDITION_FAILED,
                    "Ya existe un usuario registrado con el correo " + request.getEmail());
        }

        request.setPassword(passwordEncoder.encode(request.getPassword()));
        User user = modelMapper.map(request, User.class);

        return modelMapper.map(userRepository.save(user), UserDto.class);
    }

    @Override
    public String login(AuthenticationRequest authenticationRequest) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getEmail(), authenticationRequest.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getEmail());

        return jwtUtil.generateToken(userDetails);
    }
}
