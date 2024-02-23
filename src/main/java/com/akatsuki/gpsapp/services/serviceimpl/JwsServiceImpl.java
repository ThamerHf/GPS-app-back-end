package com.akatsuki.gpsapp.services.serviceimpl;

import com.akatsuki.gpsapp.models.entity.TokenEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import com.akatsuki.gpsapp.models.enums.Rights;
import com.akatsuki.gpsapp.models.enums.TokenType;
import com.akatsuki.gpsapp.repository.JwsTokenRepository;
import com.akatsuki.gpsapp.services.service.JwsService;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class JwsServiceImpl implements JwsService {

    @Value("${jws.secretkey}")
    private String secretKey;

    @Value("${jws.share.expiration}")
    private int jwsExpiration;

    private final JwsTokenRepository jwsTokenRepository;

    public JwsServiceImpl(JwsTokenRepository jwsTokenRepository) {
        this.jwsTokenRepository = jwsTokenRepository;
    }

    @Override
    public String extractUserName(String jws) {

        return this.extractClaim(jws, Claims::getSubject);
    }

    public String extractTokenId(String jws) {

        return this.extractClaim(jws, Claims::getId);
    }

    @Override
    public boolean isTokenValid(String jws, String userName) {
        String userNameExtracted = this.extractUserName(jws);
        return userNameExtracted.equals(userName) && !this.isExpired(jws);
    }

    public boolean isExpired(String jws) {
        return this.extractExpiration(jws).before(new Date());
    }

    private Date extractExpiration(String jws) {
        return this.extractClaim(jws, Claims::getExpiration);
    }

    @Override
    public String generateAuthToken(UserEntity user) {
        return this.generateAuthToken(new HashMap<>(), user);
    }


    @Override
    public String generateAuthToken(Map<String, Object> extraClaims, UserEntity user) {
        TokenEntity token = createToken(TokenType.AUTHENTIFICATION, user);
        String jws = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwsExpiration))
                .setId(token.getTokenId())
                .signWith(getSignKey())
                .compact();
        token.setToken(jws);
        token.setRights(Rights.ADMIN);
        this.jwsTokenRepository.save(token);

        return jws;
    }

    public String generateShareAnonymousToken(Map<String, Object> extraClaims, UserEntity user) {
        TokenEntity token = createToken(TokenType.SHARE, user);
        String jws = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(user.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwsExpiration))
                .setId(token.getTokenId())
                .compact();
        token.setToken(jws);
        this.jwsTokenRepository.save(token);

        return jws;
    }

    private TokenEntity createToken(TokenType tokenType, UserEntity user) {
        TokenEntity tokenToSave = TokenEntity
                .builder()
                .tokenType(tokenType)
                .user(user)
                .isBlackListed(false)
                .build();

        log.info("hello " + user.getUsername());

        return this.jwsTokenRepository.save(tokenToSave);
    }


    /* TODO clone guest token on click URL
    public String CloneTokenWithRights(String tokenToClone, UserDetails userDetails) {
        TokenEntity tokenToSave = TokenEntity
                .builder()
                .tokenType(TokenType.SHARE)
                .dest(Role.GUEST)
                .build();
        TokenEntity token = this.jwsTokenRepository.save(tokenToSave);

        String jws = Jwts.builder()
                .setClaims(extraClaims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + jwsExpiration))
                .setId(token.getTokenId())
                .compact();
        token.setToken(jws);
        this.jwsTokenRepository.save(token);

        return jws;
    }*/
    public <T> T extractClaim(String jws, Function<Claims, T> claimsFunction) {
        final Claims claims = this.extractClaims(jws);
        return claimsFunction.apply(claims);
    }

    @Override
    public void blackListOldTokensIfExist(String jws,String userName) {
        Optional<List<TokenEntity>> tokenList = this.jwsTokenRepository.findOtherNotBlackListedTokens(jws,userName);
        if(tokenList.isPresent()) {
            if(!tokenList.get().isEmpty()) {
                tokenList.get().stream()
                        .map(this::blackList)
                        .collect(Collectors.toList());
            }
        }
    }

    @Override
    public void blackListAllToken(String userName) {
        Optional<List<TokenEntity>> tokenList = this.jwsTokenRepository.findAllNotBlackListedToken(userName);
        if(tokenList.isPresent()) {
            if(!tokenList.get().isEmpty()) {
                tokenList.get().stream()
                        .map(this::blackList)
                        .collect(Collectors.toList());
            }
        }
    }

    private TokenEntity blackList(TokenEntity token) {
        token.setIsBlackListed(true);
        return this.jwsTokenRepository.save(token);
    }
    private Claims extractClaims(String jws) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(jws)
                .getBody();
    }

    private Key getSignKey() {
        byte[] keyBytes = Base64.getDecoder().decode(this.secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
