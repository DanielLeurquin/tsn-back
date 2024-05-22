package com.isep.tsn.config.jwt;

import lombok.Data;

@Data
public class TokenPayload {
    String userId;
    String role;
}
