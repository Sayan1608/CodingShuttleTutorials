package com.codingshuttle.securityapp.filters;

import com.codingshuttle.securityapp.entities.User;
import com.codingshuttle.securityapp.services.JwtService;
import com.codingshuttle.securityapp.services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserService userService;

    @Autowired
    private HandlerExceptionResolver handlerExceptionResolver;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            final String requestToken = request.getHeader("Authorization");

            if(requestToken == null || !requestToken.startsWith("Bearer ")){
                filterChain.doFilter(request,response);
                return;
            }

            String jwtToken = requestToken.split("Bearer ")[1];

            Long userIdFromToken = jwtService.getUserIdFromToken(jwtToken);

            if(userIdFromToken != null && SecurityContextHolder.getContext().getAuthentication() == null){
                User user = userService.getUserById(userIdFromToken);
                UsernamePasswordAuthenticationToken authentication =
                        new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
                authentication.setDetails(
                        new WebAuthenticationDetailsSource().buildDetails(request)
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request,response);
        } catch (Exception e) {
           handlerExceptionResolver.resolveException(request,response,null,e);
        }

    }
}
