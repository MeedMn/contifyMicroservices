package ma.emsi.authMicroservice.repository;

import ma.emsi.authMicroservice.entity.Authority;
import ma.emsi.authMicroservice.entity.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByAuthority(Role authority);
}

