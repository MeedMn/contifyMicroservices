package ma.emsi.authMicroservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@ToString
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private boolean enabled;
    private String fullName;
    private String address;
    private String phoneNumber;
    private String password;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "authority_id", nullable = false)
    private Authority authority;
    @Column(unique = true)
    private String email;
    public User(boolean enabled, String fullName, String address, String phoneNumber, String password, String email) {
        this.enabled = enabled;
        this.fullName = fullName;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.email = email;
    }


}

