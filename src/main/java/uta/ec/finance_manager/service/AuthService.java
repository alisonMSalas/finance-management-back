package uta.ec.finance_manager.service;

import io.jsonwebtoken.Jwt;
import uta.ec.finance_manager.config.auth.AuthenticationRequest;
import uta.ec.finance_manager.dto.SaveUserDto;
import uta.ec.finance_manager.dto.UserDto;

public interface AuthService {
    UserDto register(SaveUserDto request);

    String login(AuthenticationRequest authenticationRequest);
}
