package org.weatherApp.entity;

import jakarta.persistence.*;
import lombok.*;

@Builder
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "USERS")
public class User implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String password;
}
