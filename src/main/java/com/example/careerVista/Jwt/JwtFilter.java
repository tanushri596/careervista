package com.example.careerVista.Jwt;

import com.example.careerVista.service.CandidateService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;


@Slf4j
@Service
public class JwtFilter extends OncePerRequestFilter {
    //Extract Token->Compare token username with database username via userDetails->Create an Authentication token->Keep authentication token in SecurityContextHolder
    @Autowired
    private com.example.careerVista.Jwt.JwtUtil jwtUtil;
    @Autowired
    private  CandidateDetailsService candidateDetailsService;
    @Autowired
    private CompanyDetailsService companyDetailsService;



    private String identity = null;
    Claims claims = null;
    String role;

    private String username = null;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().matches("/candidate/signUp") || request.getServletPath().matches("/candidate/logIn") || request.getServletPath().matches("/company/logIn") || request.getServletPath().matches("/company/signUp")
                ) {
           // System.out.println("Hi there");
            filterChain.doFilter(request, response);
        } else {
            String authorizationHeader = request.getHeader("Authorization");
          //  System.out.println(authorizationHeader);
            String token = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {

                token = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(token);//Token Username
                claims = jwtUtil.extractAllClaims(token);

            }

            if(token == null)
            {
                //System.out.println("Hello from there");
            }

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null  && isUser()) {
               // System.out.println("IF");
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
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null && isCompany()) {
               // System.out.println("IF2");
                UserDetails compDetails = companyDetailsService.loadUserByUsername(username);//Database username
                jwtUtil.getAuthentication(token);
                if (jwtUtil.validateToken(token, compDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
                            (username, null, compDetails.getAuthorities());
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
        return "Company".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isUser() {
        return "Candidate".equalsIgnoreCase((String) claims.get("role"));
    }

    public boolean isAdmin() {
        return "Admin".equalsIgnoreCase((String) claims.get("role"));
    }

    public String getCurrentUser() {

//        return username;
        return identity;
    }
}
