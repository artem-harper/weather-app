package org.weatherApp.entity;

import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "users")
public class User implements BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String login;

    private String Password;
}
