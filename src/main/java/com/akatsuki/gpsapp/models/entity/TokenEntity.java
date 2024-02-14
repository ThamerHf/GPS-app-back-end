package com.akatsuki.gpsapp.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "token")
@AllArgsConstructor
@NoArgsConstructor
public class TokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @NonNull
    @Column(name = "token_id")
    private String tokenId;

    private String token;

    private String tokenType;

    private Boolean isBlackListed;

    private String owner;

    private String dest;

    private Boolean isLatest;

    @ManyToMany(
            mappedBy = "tokens"
    )
    private List<LocationEntity> locations = new ArrayList<>();

    @ManyToMany(
            mappedBy = "tokens"
    )
    private List<TagEntity> tags = new ArrayList<>();
}
