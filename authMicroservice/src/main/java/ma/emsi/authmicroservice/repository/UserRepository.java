package ma.emsi.authmicroservice.repository;

import ma.emsi.authmicroservice.entity.User;
import ma.emsi.authmicroservice.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    List<User> findUsersByAuthority_Role(Role role);
}
