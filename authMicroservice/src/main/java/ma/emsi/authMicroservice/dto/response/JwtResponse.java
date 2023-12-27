package ma.emsi.authMicroservice.dto.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class JwtResponse {
    private String accessToken;
    private String type = "Bearer";
    private UserInfos userInfos;
    private String authority;
    public JwtResponse(String accessToken, UserInfos userInfos, String email, String authority) {
        this.accessToken = accessToken;
        this.userInfos=userInfos;
        this.authority = authority;
    }
}
