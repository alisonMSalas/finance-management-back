package uta.ec.finance_manager.util;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import uta.ec.finance_manager.entity.User;
import uta.ec.finance_manager.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    public Integer getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Necesita autenticación");
        }

        Object principal = authentication.getPrincipal();
        String userEmail;
        if (principal instanceof UserDetails) {
            userEmail = ((UserDetails) principal).getUsername();
        } else {
            userEmail = principal.toString();
        }

        User user = userRepository.findByEmail(userEmail);

        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No existe el usuario");
        }

        return user.getId();
    }
}
