package ma.emsi.authmicroservice.repository;

import ma.emsi.authmicroservice.entity.Authority;
import ma.emsi.authmicroservice.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(Role role);
}

