package com.leonardo.mainapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
//Essa classe é o "porteiro"
public class FilterPerRequest extends OncePerRequestFilter {

    @Autowired
    TokenService tokenService;

    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        var token = recoverToken(request);
        if(token != null){
            String login = tokenService.validate(token);

            //Crio um "carteira" que concentra algumas informações do usuário
            var concentredUser = new UsernamePasswordAuthenticationToken(login, null, getAuthoritiesFromToken(token));
            //Preciso informar ao contexto que 'fulano' com role 'tal' está credenciado
            SecurityContextHolder.getContext().setAuthentication(concentredUser);
        }
        //'Passando pra frente' a requisição
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request){
        var authorization = request.getHeader("Authorization");
        if(authorization == null) return null;

        String token = authorization.replace("Bearer ","");
        return token;
    }

    private Collection<? extends GrantedAuthority> getAuthoritiesFromToken(String token) {
        DecodedJWT decodedJWT = JWT.decode(token);
        List<String> roles = decodedJWT.getClaim("roles").asList(String.class);

        if (roles == null) {
            return List.of();
        }

        return roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
