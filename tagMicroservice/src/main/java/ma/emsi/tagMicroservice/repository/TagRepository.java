package ma.emsi.tagMicroservice.repository;

import ma.emsi.tagMicroservice.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameIn(List<String> name);
}
