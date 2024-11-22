package com.revup.auth.service;


import com.revup.auth.dto.token.TokenInfo;
import com.revup.auth.dto.token.Tokens;

public interface TokenGenerator {
    Tokens generate(TokenInfo userPrincipal);
}
