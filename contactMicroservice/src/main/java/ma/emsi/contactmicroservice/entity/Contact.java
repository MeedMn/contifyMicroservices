package ma.emsi.contactmicroservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "contact")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  long id ;
    private String fullName ;
    private  String email ;
    private String phoneNumber ;
    private String Address;
    private  String imagePath;
    @ElementCollection
    private List<Long> tags;
    @Column(columnDefinition = "boolean default false")
    private boolean favorite;
}
