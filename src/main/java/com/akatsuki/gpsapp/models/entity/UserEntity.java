package com.akatsuki.gpsapp.models.entity;
import com.akatsuki.gpsapp.models.entity.TokenEntity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import org.hibernate.sql.Update;

import java.util.List;

@Entity
@Data
public class UserEntity {

    @Id
    @NonNull
    private String userName;

    private String firstName;

    private String lastName;

    private String email;

    private String pwd;

    @OneToMany(
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            },
            fetch = FetchType.EAGER)
    @JoinColumn(name = "token_id")
    private List<TokenEntity> tokens;

    public UserEntity() {

    }
}
