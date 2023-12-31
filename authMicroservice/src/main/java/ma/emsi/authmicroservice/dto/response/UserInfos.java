package ma.emsi.authmicroservice.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfos {
    private Long id;
    private String email;
    private String fullName;
    private String address;
    private String phoneNumber;
}
