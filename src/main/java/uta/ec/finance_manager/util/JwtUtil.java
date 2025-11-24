package uta.ec.finance_manager.util;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * Utilidad para la gestión de tokens JWT (JSON Web Token).
 * <p>
 * Esta clase proporciona métodos para generar, validar y extraer información
 * de tokens JWT utilizados en la autenticación de usuarios.
 * Los tokens tienen una validez de 10 horas desde su creación.
 * </p>
 * 
 * @author Finance Manager Team
 * @version 1.0
 * @since 2024
 */
@Service
public class JwtUtil {
    /**
     * Clave secreta utilizada para firmar los tokens JWT.
     */
    private String SECRET_KEY = "secret";

    /**
     * Extrae el nombre de usuario (email) del token JWT.
     * 
     * @param token token JWT del cual extraer el nombre de usuario
     * @return nombre de usuario contenido en el token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Extrae la fecha de expiración del token JWT.
     * 
     * @param token token JWT del cual extraer la fecha de expiración
     * @return fecha de expiración del token
     */
    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token JWT.
     * 
     * @param <T> tipo del claim a extraer
     * @param token token JWT del cual extraer el claim
     * @param claimsResolver función para resolver el claim deseado
     * @return valor del claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    /**
     * Extrae todos los claims del token JWT.
     * 
     * @param token token JWT a parsear
     * @return todos los claims contenidos en el token
     */
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    /**
     * Verifica si el token JWT ha expirado.
     * 
     * @param token token JWT a verificar
     * @return true si el token ha expirado, false en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Genera un nuevo token JWT para un usuario.
     * 
     * @param userDetails detalles del usuario para quien generar el token
     * @return token JWT generado
     */
    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, userDetails.getUsername());
    }

    /**
     * Crea un token JWT con claims específicos.
     * <p>
     * El token tiene una validez de 10 horas desde su creación.
     * </p>
     * 
     * @param claims mapa de claims a incluir en el token
     * @param subject sujeto del token (generalmente el nombre de usuario)
     * @return token JWT creado y firmado
     */
    private String createToken(Map<String, Object> claims, String subject) {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    /**
     * Valida un token JWT contra los detalles de un usuario.
     * <p>
     * Verifica que el nombre de usuario en el token coincida con el usuario
     * proporcionado y que el token no haya expirado.
     * </p>
     * 
     * @param token token JWT a validar
     * @param userDetails detalles del usuario contra el cual validar
     * @return true si el token es válido, false en caso contrario
     */
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
