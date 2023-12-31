package ma.emsi.contactmicroservice;

import ma.emsi.contactmicroservice.dto.ContactRequest;
import ma.emsi.contactmicroservice.dto.ContactResponse;
import ma.emsi.contactmicroservice.dto.TagResponse;
import ma.emsi.contactmicroservice.entity.Contact;
import ma.emsi.contactmicroservice.exceptions.NotFoundException;
import ma.emsi.contactmicroservice.mapper.ContactMapper;
import ma.emsi.contactmicroservice.repository.ContactRepository;
import ma.emsi.contactmicroservice.service.ContactService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContactTest {

    @InjectMocks
    private ContactService contactService;

    @Mock
    private ContactRepository contactRepository;

    @Mock
    private ContactMapper contactMapper;

    private Contact contact1;
    private Contact contact2;
    private ContactRequest contactRequest1;
    private ContactRequest contactRequest2;
    private ContactResponse contactResponse1;
    private ContactResponse contactResponse2;
    private TagResponse tagResponse1;
    private TagResponse tagResponse2;

    @BeforeEach
    void setUp() {
        tagResponse1 = new TagResponse(1L, "Friend");
        tagResponse2 = new TagResponse(2L, "Family");
        contact1 = new Contact(1L, "Mohamed", "mohamed@example.com", "1234567890", "Marrakech", "mohamed.jpg", List.of(1L),false);
        contact2 = new Contact(2L, "Ismail", "Ismail@example.com", "0987654321", "Marrakech", "Ismail.jpg",  List.of(2L),true);
        contactRequest1 = new ContactRequest("Mohamed", "Mohamed@example.com", "1234567890", "Marrakech", "Mohamed.jpg");
        contactRequest2 = new ContactRequest("Ismail", "Ismail@example.com", "0987654321", "Marrakech", "Ismail.jpg");
        contactResponse1 = new ContactResponse(1L, "Mohamed", "Mohamed@example.com", "1234567890", "Marrakech", "Mohamed.jpg", List.of(tagResponse1),false);
        contactResponse2 = new ContactResponse(2L, "Ismail", "Ismail@example.com", "0987654321", "Marrakech", "Ismail.jpg", List.of(tagResponse2),true);
    }

    @Test
    void testCreate() {
        when(contactMapper.contactRequestToContact(contactRequest1)).thenReturn(contact1);
        when(contactMapper.contactToContactResponse(contact1)).thenReturn(contactResponse1);
        ContactResponse result = contactService.create(contactRequest1);
        verify(contactRepository, times(1)).save(contact1);

        assertEquals(contactResponse1, result);
    }

    @Test
    void testUpdate() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.of(contact1));
        when(contactMapper.contactToContactResponse(contact1)).thenReturn(contactResponse1);
        ContactResponse result = contactService.update(1L, contactRequest2);
        verify(contactRepository, times(1)).save(contact1);

        assertEquals(contactResponse1, result);

        assertEquals(contactRequest2.getFullName(), contact1.getFullName());

        assertEquals(contactRequest2.getEmail(), contact1.getEmail());

        assertEquals(contactRequest2.getPhoneNumber(), contact1.getPhoneNumber());

        assertEquals(contactRequest2.getAddress(), contact1.getAddress());

        assertEquals(contactRequest2.getImagePath(), contact1.getImagePath());
    }

    @Test
    void testUpdateNotFound() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        assertThrows(NotFoundException.class, () -> contactService.update(1L, contactRequest2));

        verify(contactRepository, never()).save(any());
    }

    @Test
    void testDelete() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.of(contact1));

        String result = contactService.delete(1L);

        verify(contactRepository, times(1)).deleteById(1L);

        assertEquals("Contact Deleted", result);
    }

    @Test
    void testDeleteNotFound() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(NotFoundException.class, () -> contactService.delete(1L));
        verify(contactRepository, never()).deleteById(any());
    }

    @Test
    void testAddToFavorites() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.of(contact1));
        contactService.addToFavorites(1L);
        verify(contactRepository, times(1)).save(contact1);

        assertEquals(true, contact1.isFavorite());
    }

    @Test
    void testAddToFavoritesNotFound() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.empty());
        assertThrows(NotFoundException.class, () -> contactService.addToFavorites(1L));
        verify(contactRepository, never()).save(any());
    }

    @Test
    void testGetAll() {
        when(contactRepository.findAll()).thenReturn(List.of(contact1, contact2));
        when(contactMapper.contactToContactResponse(contact1)).thenReturn(contactResponse1);
        when(contactMapper.contactToContactResponse(contact2)).thenReturn(contactResponse2);
        List<ContactResponse> result = contactService.getAll();

        assertEquals(List.of(contactResponse1, contactResponse2), result);
    }

    @Test
    void testGetAllFavorites() {
        when(contactRepository.findContactsByFavoriteIs(true)).thenReturn(List.of(contact2));
        when(contactMapper.contactToContactResponse(contact2)).thenReturn(contactResponse2);
        List<ContactResponse> result = contactService.getAllFavorites();
        assertEquals(List.of(contactResponse2), result);
    }

    @Test
    void testGetById() {
        when(contactRepository.findById(1L)).thenReturn(java.util.Optional.of(contact1));
        when(contactMapper.contactToContactResponse(contact1)).thenReturn(contactResponse1);
        ContactResponse result = contactService.getById(1L);
        assertEquals(contactResponse1, result);
    }
}