package com.example.careerVista.Jwt;

import com.example.careerVista.service.CandidateService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    //Extract Token->Compare token username with database username via userDetails->Create an Authentication token->Keep authentication token in SecurityContextHolder

    private final com.example.careerVista.Jwt.JwtUtil jwtUtil;

    private final CandidateDetailsService candidateDetailsService;



    private String identity = null;
    Claims claims = null;
    private String username = null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/candidate/signUp | /candidate/logIn | /company/signUp | /company/logIn")) {
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(token);//Token Username
                claims = jwtUtil.extractAllClaims(token);
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = candidateDetailsService.loadUserByUsername(username);//Database username
                jwtUtil.getAuthentication(token);
                if (jwtUtil.validateToken(token, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                            (username, null, userDetails.getAuthorities());
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
                identity = username;

            }
            username = null;
            filterChain.doFilter(request, response);

        }
    }

    public boolean isCompany() {
        return "Candidate".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return "Company".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {

//        return username;
        return identity;
    }
}
