package com.akatsuki.gpsapp.repository;

import com.akatsuki.gpsapp.models.entity.TokenEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface JwsTokenRepository extends CrudRepository<TokenEntity, String> {

  @Query(value = "Select * from token as t natural join user_entity  " +
          "where t.token_type = 'AUTHENTIFICATION' " +
          "and  t.token != :jwsToken " +
          "and user_entity.user_name=:userName " +
          "and not t.is_black_listed;",
          nativeQuery = true)
  Optional<List<TokenEntity>> findOtherNotBlackListedTokens(String jwsToken,String userName);

  @Query(value = "Select * from token as t natural join user_entity  " +
          "where t.token_type = 'AUTHENTIFICATION' " +
          "and user_entity.user_name=:userName " +
          "and not t.is_black_listed;",
          nativeQuery = true)
  Optional<List<TokenEntity>> findAllNotBlackListedToken(String userName);

}
