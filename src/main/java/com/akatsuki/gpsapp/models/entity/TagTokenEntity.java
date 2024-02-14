package com.akatsuki.gpsapp.models.entity;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "tag_token", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"tag_id", "token_id", "access_right"})
})
public class TagTokenEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tag_id")
    private TagEntity tag;

    @ManyToOne
    @JoinColumn(name = "token_id")
    private TokenEntity token;

    @Column(name = "access_right")
    private String accessRight;

}
