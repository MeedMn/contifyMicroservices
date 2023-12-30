package ma.emsi.authMicroservice.entity;

import jakarta.persistence.*;
import lombok.*;
import ma.emsi.authMicroservice.entity.enums.Role;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "authority")
public class Authority {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Role role;

}

