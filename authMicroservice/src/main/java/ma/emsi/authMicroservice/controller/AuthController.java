package ma.emsi.authMicroservice.controller;

import jakarta.validation.Valid;
import ma.emsi.authMicroservice.config.JwtUtils;
import ma.emsi.authMicroservice.dto.request.LoginRequest;
import ma.emsi.authMicroservice.dto.request.SignupRequest;
import ma.emsi.authMicroservice.dto.response.JwtResponse;
import ma.emsi.authMicroservice.dto.response.MessageResponse;
import ma.emsi.authMicroservice.dto.response.UserInfos;
import ma.emsi.authMicroservice.entity.Authority;
import ma.emsi.authMicroservice.entity.User;
import ma.emsi.authMicroservice.entity.enums.Role;
import ma.emsi.authMicroservice.repository.RoleRepository;
import ma.emsi.authMicroservice.repository.UserRepository;
import ma.emsi.authMicroservice.service.UserDetailsImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    AuthenticationManager authenticationManager;
    UserRepository userRepository;
    RoleRepository roleRepository;
    PasswordEncoder encoder;
    JwtUtils jwtUtils;

    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String role = userDetails.getAuthority().getAuthority();
        User user = userRepository.findById(userDetails.getId()).get();
        UserInfos userInfos = new UserInfos(user.getId(),user.getEmail(),user.getFullName(), user.getAddress(),user.getPhoneNumber());
        return ResponseEntity.ok(new JwtResponse(jwt,
                userInfos,
                userDetails.getUsername(),
                role));
    }
    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        User user = new User(true,signUpRequest.getFullName(),signUpRequest.getAddress(),signUpRequest.getPhoneNumber(),encoder.encode(signUpRequest.getPassword()),signUpRequest.getEmail());
        Authority userRole = roleRepository.findByAuthority(Role.USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        user.setAuthority(userRole);
        userRepository.save(user);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
