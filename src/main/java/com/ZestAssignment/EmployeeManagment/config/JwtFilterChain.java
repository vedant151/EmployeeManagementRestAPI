package com.ZestAssignment.EmployeeManagment.config;

import com.ZestAssignment.EmployeeManagment.Exception.TokenExpired;
import com.ZestAssignment.EmployeeManagment.Services.JwtHelper;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilterChain extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws IOException, ServletException {

        try{

            if(request.getServletPath().contains("/api/login") ||
                    request.getServletPath().contains("/api/signup") ||
                    request.getServletPath().contains("/api/logout") ||
                    request.getServletPath().contains("/api/home")
            ){
                filterChain.doFilter(request, response);
                return;
            }

            // extract the jwt token from header
            String token = request.getHeader("Authorization").substring(7);

            // extract userEmail and role value from jwt token
            String email = JwtHelper.extractClaims(token).getSubject();
            String role = JwtHelper.extractRoleFromToken(token);

            // save the user using usernameAndPasswordAuthentication
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority(role));

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email, null, authorities);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // 4. Register auth in SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

        } catch (ExpiredJwtException e){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
            throw new TokenExpired("Token expired");
//            return;
        }catch (JwtException ex){
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Token");
            throw new TokenExpired("Token expired");
//            return;
        }

        filterChain.doFilter(request, response);

    }


}
