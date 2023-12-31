package ma.emsi.authmicroservice.dto.request;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {
    @NotBlank
    private String email;
    private String authority;
    private String fullName;
    private String address;
    private String phoneNumber;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
}
