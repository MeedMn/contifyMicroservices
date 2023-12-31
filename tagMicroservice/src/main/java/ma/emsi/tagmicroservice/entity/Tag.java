package ma.emsi.tagmicroservice.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tag")
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
}
