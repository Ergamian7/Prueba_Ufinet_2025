package com.managment.api.user.security;

import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
    private String secretKey = "bXlTdXBlclNlY3JldEtleVdpdGgzMkNoYXJhY3RlcnNMZW5ndGgxMjM="; // Debes cambiar esto por una clave secreta
                                                                             // más segura
    private long validityInMilliseconds = 3600000; // 1 hora

    /**
     * Genera un JWT para un usuario.
     *
     * @param username Nombre de usuario.
     * @return El JWT generado.
     */
    public String generateToken(String username) {
        // Configurar la fecha de expiración del token
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + validityInMilliseconds);

        // Crear el JWT 
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .compact();
    }

    /**
     * Valida un JWT.
     *
     * @param token El token JWT.
     * @return true si el token es válido, false si no.
     */
    public boolean validateToken(String token) {
        try {
            // Verificar si el token es válido
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token); // Lanzará una excepción si el token no es válido
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // En caso de cualquier error (token expirado, no válido, etc.), se retorna
            // false
            return false;
        }
    }

    /**
     * Extrae el nombre de usuario (subject) desde el JWT.
     *
     * @param token El token JWT.
     * @return El nombre de usuario contenido en el JWT.
     */
    public String getUsernameFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject(); // El subject es el nombre de usuario
    }

    /**
     * Extrae la fecha de expiración del JWT.
     *
     * @param token El token JWT.
     * @return La fecha de expiración.
     */
    public Date getExpirationDateFromToken(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration();
    }

    /**
     * Verifica si el token ha expirado.
     *
     * @param token El token JWT.
     * @return true si el token ha expirado, false en caso contrario.
     */
    public boolean isTokenExpired(String token) {
        Date expirationDate = getExpirationDateFromToken(token);
        return expirationDate.before(new Date());
    }
}


