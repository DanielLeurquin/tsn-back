package com.isep.tsn.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.isep.tsn.config.jwt.JwtTokenUtil;
import com.isep.tsn.config.jwt.TokenPayload;
import com.isep.tsn.config.jwt.TokenSet;
import com.isep.tsn.dal.model.dto.UserRegisterDto;
import com.isep.tsn.dal.model.enums.Role;
import com.isep.tsn.dal.model.postgres.User;
import com.isep.tsn.dal.postgres.repository.UserRepository;
import com.isep.tsn.exceptions.BusinessException;
import com.isep.tsn.mapper.UserMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityService {

    final private JwtTokenUtil jwtTokenUtil;
    final private HttpServletResponse response;
    @Value("${cors.insecure}")
    boolean corsInsecure;
    @Autowired
    UserRepository userRepository;
    @Value("${jwt.refresh-token-duration}")
    private int refreshTokenDuration;

    /**
     * Check if user has one of the roles listed
     *
     * @return
     */

    static public boolean isUserAnonymous() {
        // by default spring security set the principal as "anonymousUser"
        return SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()
                .equals("anonymousUser");
    }

    public String getLoggedId() {
        if (!isUserAnonymous()) {
            return ((TokenPayload) SecurityContextHolder.getContext()
                    .getAuthentication()
                    .getPrincipal()).getUserId();
        }
        return null;
    }


    public boolean userHasRole(String role) {
        return ((TokenPayload) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal()).getRole()
                .equals(role);
    }

    public TokenSet logUser(User user) {
        TokenSet set = jwtTokenUtil.generateTokenSet(user);
        return setRefreshTokenCookie(set);
    }

    public TokenSet refreshUserToken(String refreshToken) {
        try {
            TokenSet set = jwtTokenUtil.refreshWithToken(refreshToken);
            return setRefreshTokenCookie(set);
        } catch (JWTVerificationException e) {
            throw new BusinessException("refresh_token_invalid");
        }
    }

    private TokenSet setRefreshTokenCookie(TokenSet set) {
        Cookie cRefreshToken = new Cookie("refresh-token", set.getRefreshToken());
        cRefreshToken.setMaxAge(refreshTokenDuration);
        cRefreshToken.setPath("/auth/refresh");
        cRefreshToken.setSecure(corsInsecure);

        response.addCookie(cRefreshToken);

        return set;
    }

    public boolean validStudentPerformAction(String objectStudentCode) {
        return (userHasRole(Role.ROLE_ADMIN.toString()) ||
                userHasRole(Role.ROLE_SUPER_ADMIN.toString()) ||
                objectStudentCode.equals(getLoggedId()));

    }


    public TokenSet register(UserRegisterDto dto) {
        var existingUser = userRepository.findById(dto.getId());
        if(existingUser.isPresent()){
            throw new BusinessException("User already exists");
        }

        var user = UserMapper.instance().convertToEntity(dto);
        user.setRole(Role.ROLE_USER);
        user = userRepository.save(user);
        return logUser(user);
    }

}
