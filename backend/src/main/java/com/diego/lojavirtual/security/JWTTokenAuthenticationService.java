package com.diego.lojavirtual.security;

import com.diego.lojavirtual.ApplicationContextLoad;
import com.diego.lojavirtual.model.Usuario;
import com.diego.lojavirtual.repository.UsuarioRepository;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
@Component
public class JWTTokenAuthenticationService {

    private static final long EXPIRATION_TIME = 172800000; // 2 dias
    private static final String SECRET = "Secret pass";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, String username) throws Exception {
        String JWT = Jwts.builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();

        String token = TOKEN_PREFIX + " " + JWT;

        response.addHeader(HEADER_STRING, token);

        liberacaoCors(response);

        response.getWriter().write("{\"Authorization\": \"" + token + "\"}");


    }

    public Authentication getAuthentication(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String token = request.getHeader(HEADER_STRING);

        try {

            if (token != null) {

                String tokenLimpo = token.replace(TOKEN_PREFIX, "").trim();

                // descompacta o token e retorna o usuário pai
                String user = Jwts.parser()
                        .setSigningKey(SECRET)
                        .parseClaimsJws(tokenLimpo)
                        .getBody().getSubject();

                if (user != null) {
                    Usuario usuario = ApplicationContextLoad
                            .getApplicationContext()
                            .getBean(UsuarioRepository.class)
                            .findUserByLogin(user);

                    if (usuario != null) {

                        return new UsernamePasswordAuthenticationToken(
                                usuario.getLogin(),
                                usuario.getSenha(),
                                usuario.getAuthorities());
                    }
                }
            }
        } catch (SignatureException e) {
            response.getWriter().write("Token inválido!");
        } catch (ExpiredJwtException e) {
            response.getWriter().write("Token expirado. Efetue o login novamente.");
        }
        finally {
            liberacaoCors(response);
        }
        return null;
    }

    private void liberacaoCors(HttpServletResponse response) {
        if (response.getHeader("Access-Control-Allow-Origin") == null) {
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
        if (response.getHeader("Access-Control-Allow-Headers") == null) {
            response.addHeader("Access-Control-Allow-Headers", "*");
        }
        if (response.getHeader("Access-Control-Request-Headers") == null) {
            response.addHeader("Access-Control-Request-Headers", "*");
        }
        if (response.getHeader("Access-Control-Allow-Methods") == null) {
            response.addHeader("Access-Control-Allow-Methods", "*");
        }
    }
}
