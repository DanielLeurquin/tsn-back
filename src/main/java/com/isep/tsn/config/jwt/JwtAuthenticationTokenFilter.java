package com.isep.tsn.config.jwt;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.isep.tsn.exceptions.BusinessException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;


@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
  final private Logger LOG = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
    String token = request.getHeader("Authorization");
    if (token != null) {
      DecodedJWT jwt;
      if (token.startsWith("Bearer ")) {
        token = token.substring(7);
        try {
          LOG.debug("decoding token");
          jwt = jwtTokenUtil.decodeToken(token);
        } catch (JWTVerificationException e) {
         throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"token expired, refresh the token");
        }
      } else {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED,"Authentication schema not found");
      }
      if (jwt != null && SecurityContextHolder.getContext().getAuthentication() == null) {
        JwtAuthenticationToken authentication = new JwtAuthenticationToken(jwt);
        authentication.setAuthenticated(true);
        response.setHeader("Authorization", "Bearer " + token);
        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    }else {
      throw new BusinessException("No token found");
    }

    chain.doFilter(request, response);
  }

}
