package spring.security.security.Filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import spring.security.security.Service.CustomUserDetailsService;
import spring.security.security.Utility.JwtUtil;

import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        //Here we will check fro header to get authorization and from there it checks for Bearer
        String authHeader=request.getHeader("Authorization");
        String token=null;
        String userName=null;
        if(authHeader !=null && authHeader.startsWith("Bearer ")){

            //Here from 7th position the token placement starts( 6 character in Bearer plus one extra space )
            token=authHeader.substring(7);

            userName=jwtUtil.extractToken(token);

            if(userName !=null && SecurityContextHolder.getContext().getAuthentication()==null){

                UserDetails userDetails=customUserDetailsService.loadUserByUsername(userName);

                if(jwtUtil.validateToken(userName,userDetails,token)){

                    UsernamePasswordAuthenticationToken authToken=new
                            UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));//extra info to set security
                    // context holder to get request ip and session details for audit purpose if need
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        if(SecurityContextHolder.getContext().getAuthentication() !=null){
            System.out.println("Final Authentication: " + SecurityContextHolder.getContext().getAuthentication().getName());
        }

        filterChain.doFilter(request,response);

    }
}
