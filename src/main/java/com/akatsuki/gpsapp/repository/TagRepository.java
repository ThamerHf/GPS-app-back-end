package com.akatsuki.gpsapp.repository;

import com.akatsuki.gpsapp.models.entity.TagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<TagEntity, Long> {

    public Optional<TagEntity> findByTagId(Long tagId);

    public Optional<TagEntity> findByTag(String tag);

    @Query(value = "select * from TagEntity natural join " +
            "Token as t where t.dest =:userName;",
            nativeQuery = true)
    List<TagEntity> findTagsSharedWithUser(String userName);

    @Query(value = "select * from TAG natural join " +
            "LOCATION as l where l.USER_NAME =:userName;",
            nativeQuery = true)
    List<TagEntity> getOwnedTags(String userName);


}
