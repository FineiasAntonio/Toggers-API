package com.toggersapi.ToggersAPICore.Configuration;

import com.toggersapi.ToggersAPICore.Domain.AuthService;
import com.toggersapi.ToggersAPICore.Infrastructure.Entities.User;
import com.toggersapi.ToggersAPICore.Infrastructure.Repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.NoSuchElementException;

@Configuration
public class FilterConfig extends OncePerRequestFilter {

    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, IOException {

        if (request.getRequestURL().toString().contains("register")){
            filterChain.doFilter(request, response);
            return;
        }

        String header = request.getHeader("Authorization");
        String token = (header == null) ? null : header.split(" ")[1];

        if(token != null){
            User user = userRepository.findByEmail(authService.validateToken(token)).orElseThrow(() -> new NoSuchElementException("Email not found"));

            UsernamePasswordAuthenticationToken auth = UsernamePasswordAuthenticationToken.authenticated(user, null, user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(auth);
        }
        filterChain.doFilter(request, response);
    }

}
