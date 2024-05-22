package com.isep.tsn.controller;

import com.isep.tsn.config.jwt.JwtTokenUtil;
import com.isep.tsn.config.jwt.TokenSet;
import com.isep.tsn.dal.model.dto.RefreshTokenDto;
import com.isep.tsn.dal.model.dto.UserLoginDto;
import com.isep.tsn.dal.model.dto.UserRegisterDto;
import com.isep.tsn.dal.postgres.repository.UserRepository;
import com.isep.tsn.exceptions.BusinessException;
import com.isep.tsn.exceptions.http.HttpUnauthorizedException;
import com.isep.tsn.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Autowired
    SecurityService securityService;
    @Autowired
    UserRepository userRepository;


    @PostMapping()
    public TokenSet auth(@RequestBody UserLoginDto dto) {
        var userOpt = userRepository.findById(dto.getId());
        if(userOpt.isEmpty()){
            throw new BusinessException("User not found");
        }
        var user = userOpt.get();

        if(!user.getPassword().equals(dto.getPassword())){
            throw new BusinessException("Invalid password");
        }

        var tokenSet =  securityService.logUser(user);
        return tokenSet;
    }

    @PostMapping("/register")
    public TokenSet register(@RequestBody UserRegisterDto dto){
        return securityService.register(dto);
    }


    @PostMapping("/refresh")
    public TokenSet getRefreshedTokens(@CookieValue(value = "refresh-token", defaultValue = "") String refreshToken, @RequestBody RefreshTokenDto dto) {
        if (refreshToken.equals("") && (dto.getRefreshToken() == null || dto.getRefreshToken().equals("")))
            throw new HttpUnauthorizedException("refresh_token_expired");

        return securityService.refreshUserToken(dto.getRefreshToken() != null && !dto.getRefreshToken().equals("") ? dto.getRefreshToken() : refreshToken);
    }
}


