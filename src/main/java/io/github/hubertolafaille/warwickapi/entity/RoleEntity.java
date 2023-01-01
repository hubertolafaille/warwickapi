package io.github.hubertolafaille.warwickapi.entity;

import io.github.hubertolafaille.warwickapi.enumeration.RoleEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Entity
@Table(name = "role",
        uniqueConstraints = {@UniqueConstraint(columnNames = "name")}
)
@Data
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RoleEnum name;

}
