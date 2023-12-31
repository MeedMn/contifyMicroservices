package ma.emsi.authmicroservice.service;

import ma.emsi.authmicroservice.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class UserDetailsImpl implements UserDetails {
  private static final long serialVersionUID = 1L;
  private Long id;
  private boolean enabled;
  private String email;
  private String password;
  private GrantedAuthority authority;

  public UserDetailsImpl(Long id, boolean enabled, String email, String password, GrantedAuthority authority) {
    this.id = id;
    this.enabled = enabled;
    this.email = email;
    this.password = password;
    this.authority = authority;
  }

  public static UserDetailsImpl build(User user) {
    GrantedAuthority authority = new SimpleGrantedAuthority(user.getAuthority().getRole().name());

    return new UserDetailsImpl(
            user.getId(),
            user.isEnabled(),
            user.getEmail(),
            user.getPassword(),
            authority);
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return List.of(authority);
  }

  public GrantedAuthority getAuthority(){
    return authority;
  }
  public Long getId() {
    return id;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public String getUsername() {
    return email;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return enabled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserDetailsImpl user = (UserDetailsImpl) o;
    return Objects.equals(id, user.id);
  }
}