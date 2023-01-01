package io.github.hubertolafaille.warwickapi.entity;

import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "role")
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    private RoleEnum name;

}
