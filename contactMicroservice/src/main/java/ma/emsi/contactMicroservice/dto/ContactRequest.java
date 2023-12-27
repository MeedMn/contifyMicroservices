package ma.emsi.contactMicroservice.dto;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactRequest {
    private String fullName ;
    private  String email ;
    private String phoneNumber ;
    private String Address;
    private  String imagePath;
}
