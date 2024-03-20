package com.akatsuki.gpsapp.models.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tag")
public class TagEntity {

    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "tag_id")
    private Long tagId;

    private String tag;

    @ManyToMany(
            mappedBy = "tags"
    )
    private List<LocationEntity> locations = new ArrayList<>();

    @ManyToMany(
            fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
            }
    )
    @JoinTable(
            name = "tag_token",
            joinColumns = @JoinColumn(name = "location_id"),
            inverseJoinColumns = @JoinColumn(name = "token_id")
    )
    private List<TokenEntity> tokens = new ArrayList<>();

    public TagEntity() {

    }

    public TagEntity(String tag) {
        this.tag = tag;
    }
}
