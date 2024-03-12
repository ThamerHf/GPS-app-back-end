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

    @Query(value = "select * from TagEntity natural join " +
            "Token as t where t.dest =:userName;",
            nativeQuery = true)
    List<TagEntity> findTagsSharedWithUser(String userName);

    @Query(value = "select * from TagEntity natural join " +
            "Token as t where t.owner =:userName;",
            nativeQuery = true)
    List<TagEntity> findTagsByUserName(String userName);
}
