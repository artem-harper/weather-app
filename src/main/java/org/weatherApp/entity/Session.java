package org.weatherApp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "sessions")
public class Session implements BaseEntity{

    @Id
    private UUID id;

    @ManyToOne
    private User user;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;
}
