package com.akatsuki.gpsapp.models.entity;

import com.akatsuki.gpsapp.models.enums.Rights;
import com.akatsuki.gpsapp.models.enums.Role;
import com.akatsuki.gpsapp.models.enums.TokenType;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@Builder
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

    @Enumerated(EnumType.STRING)
    private TokenType tokenType;

    private Boolean isBlackListed;

    @Enumerated(EnumType.STRING)
    private Role dest;

    @Enumerated(EnumType.STRING)
    private Rights rights;

    @ManyToMany(
            mappedBy = "tokens"
    )
    private List<LocationEntity> locations = new ArrayList<>();

    @ManyToMany(
            mappedBy = "tokens"
    )
    private List<TagEntity> tags = new ArrayList<>();
}
