package com.akatsuki.gpsapp.models.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "location_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"location_id", "token_id", "access_right"})
})
public class LocationTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private LocationEntity location;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private TokenEntity token;

    @Column(name = "access_right")
    private String accessRight;

    public LocationTokenEntity() {

    }
}
