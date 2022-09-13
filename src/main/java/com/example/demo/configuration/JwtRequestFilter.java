package com.example.demo.configuration;

import com.example.demo.messages.UserDTO;
import com.example.demo.model.Role;
import com.example.demo.service.IUserCompanyMapService;
import com.example.demo.service.IUserRoleMapService;
import com.example.demo.service.IUserService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.internal.util.collections.IdentitySet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtil;


    @Autowired
    IUserService iUserService;
    @Autowired
    IUserRoleMapService iUserCompanyMapService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException, ExpiredJwtException {
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
        try {
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                username = jwtUtil.extractUsername(jwt);
            }
            log.info("danger header 9 : " +SecurityContextHolder.getContext().getAuthentication());

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                UserDTO userDetails = iUserService.getUserByName(username);
                List<String> list=iUserCompanyMapService.getRoleMappings(username);
                Set<SimpleGrantedAuthority> set=new HashSet<>();
              list.stream().filter(x->{
                  set.add(new SimpleGrantedAuthority(x.toUpperCase()));
                  return true;
              }).collect(Collectors.toSet());
                if (jwtUtil.validateToken(jwt, userDetails)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null,set);
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
        }catch (Exception n){
            response.addHeader("jwt token expired","jwt token expired");
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        filterChain.doFilter(request, response);
    }

    public String  getJWTfromRequest(HttpServletRequest request){
        String bearerToken=request.getHeader("Authorization");
        if(StringUtils.hasText(bearerToken)&&bearerToken.startsWith("Bearer"))
            return bearerToken.substring(7,bearerToken.length());
        return null;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return ((request.getMethod().matches("POST")&&request.getRequestURI().matches("/user"))
        ||(request.getMethod().matches("POST")&&request.getRequestURI().matches("/login"))
        ||(request.getMethod().matches("GET")&&request.getRequestURI().matches("/actuator/.*"))
        ||(request.getMethod().matches("POST")&&request.getRequestURI().matches("/user/user-role/.*"))
        ||(request.getMethod().matches("POST")&&request.getRequestURI().matches("/roles"))
                ||(request.getRequestURI().matches("/swagger-ui/.*")));
    }
}