package com.revup.auth.service.port.out;


import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;

public interface TokenGenerator {
    Tokens generate(TokenInfo userPrincipal);
}
