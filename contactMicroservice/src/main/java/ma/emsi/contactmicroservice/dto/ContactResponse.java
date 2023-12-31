package ma.emsi.contactmicroservice.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ContactResponse {
    private  long id ;
    private String fullName ;
    private  String email ;
    private String phoneNumber ;
    private String address;
    private  String imagePath;
    private List<TagResponse> tags;
    private boolean favorite;
}
