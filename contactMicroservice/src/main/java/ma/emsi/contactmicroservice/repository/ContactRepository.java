package ma.emsi.contactmicroservice.repository;

import ma.emsi.contactmicroservice.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public   interface ContactRepository extends JpaRepository<Contact, Long> {
    List<Contact> findContactsByFavoriteIs(boolean favorite);
    @Query("SELECT contact FROM Contact contact WHERE :tag MEMBER OF contact.tags")
    List<Contact> findContactsByTag(@Param("tag") Long tag);
}
