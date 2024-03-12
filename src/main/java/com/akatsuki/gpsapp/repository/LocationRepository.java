package com.akatsuki.gpsapp.repository;

import com.akatsuki.gpsapp.models.entity.LocationEntity;
import com.akatsuki.gpsapp.models.entity.TagEntity;
import com.akatsuki.gpsapp.models.entity.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationRepository extends CrudRepository<LocationEntity, String> {

    @Query(value = "select * from Location natural join " +
            "Token as tk natural join Tag as tg" +
            " where tk.dest =:userName and" +
            "tg.tag_id not in (select tag_id from Tag natural join " +
            "Token WHERE Token.dest =:userName);",
            nativeQuery = true)
    List<LocationEntity> findOrphanLocationsByUserName(String userName);

    @Query(value = "select * from Location natural join " +
            "Tag as tg natural join Token as tk " +
            "where tg.tag_id=:tagId and (tk.owner=:userName or tk.dest=:userName);",
            nativeQuery = true)
    List<LocationEntity> findLocationEntitiesByUserNameAndTagId(String userName, Long tagId);

    @Query(value = "select * from Location as loc natural join " +
            "Tag as tg" +
            "where tg.tag_id=:tagId and loc.USER_NAME=:userName;",
            nativeQuery = true)
    public List<LocationEntity> findByUser_UserNameAndTagId(String user, Long tagId);

    public Optional<LocationEntity> findByLocationId(Long locationId);

    public List<LocationEntity> findByUser(UserEntity user);

}
