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

/**
 * Utilidad para obtener información del usuario autenticado.
 * <p>
 * Esta clase proporciona métodos para extraer información del usuario
 * que está actualmente autenticado en el sistema mediante el contexto
 * de seguridad de Spring Security.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Service
@RequiredArgsConstructor
public class UserUtil {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    /**
     * Obtiene el identificador del usuario actualmente autenticado.
     * <p>
     * Extrae el email del usuario del contexto de seguridad de Spring,
     * busca el usuario en la base de datos y retorna su identificador.
     * </p>
     * 
     * @return identificador del usuario autenticado
     * @throws ResponseStatusException si no hay autenticación o el usuario no existe
     */
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
