package ma.emsi.contactMicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.contactMicroservice.dto.ContactRequest;
import ma.emsi.contactMicroservice.dto.ContactResponse;
import ma.emsi.contactMicroservice.dto.TagResponse;
import ma.emsi.contactMicroservice.exceptions.NotFoundException;
import ma.emsi.contactMicroservice.external.TagService;
import ma.emsi.contactMicroservice.mapper.ContactMapper;
import ma.emsi.contactMicroservice.entity.Contact;
import ma.emsi.contactMicroservice.repository.ContactRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    public ContactResponse create(ContactRequest contactRequest){
        Contact contact = contactMapper.ContactRequestToContact(contactRequest);
        contactRepository.save(contact);
        return contactMapper.ContactToContactResponse(contact);
    }
    public ContactResponse update(Long id,ContactRequest contactRequest){
        return contactRepository.findById(id)
                .map(c->{
                    c.setFullName(contactRequest.getFullName());
                    c.setEmail(contactRequest.getEmail());
                    c.setPhoneNumber(contactRequest.getPhoneNumber());
                    c.setAddress(contactRequest.getAddress());
                    c.setImagePath(contactRequest.getImagePath());
                    contactRepository.save(c);
                    return contactMapper.ContactToContactResponse(c);
                }).orElseThrow(()->new NotFoundException("Contact Not Found"));
    }
    public String delete(Long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact Not found with this ID : " + id));
        contactRepository.delete(contact);
        return "Contact Deleted" ;
    }
    public void addToFavorites(Long id){
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact Not found with this ID : " + id));
        contact.setFavorite(!contact.isFavorite());
        contactRepository.save(contact);
    }
    public List<ContactResponse> getAll(){
        List<Contact> contacts = contactRepository.findAll();
        return contacts.stream().map(contactMapper::ContactToContactResponse).toList();
    }
    public List<ContactResponse> getAllFavorites(){
        List<Contact> contacts = contactRepository.findContactsByFavoriteIs(true);
        return contacts.stream().map(contactMapper::ContactToContactResponse).toList();
    }
    public ContactResponse getById(long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
        return contactMapper.ContactToContactResponse(contact);
    }
    public List<ContactResponse> getByTag(Long tag){
        List<Contact> contacts = contactRepository.findContactsByTag(tag);
        return contacts.stream().map(contactMapper::ContactToContactResponse).toList();
    }
    public String affectTagToContact(Long idContact,List<TagResponse> tags) throws NotFoundException{
        var contact = contactRepository.findById(idContact)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
        if(tags != null && !tags.isEmpty()){
            contact.getTags().addAll(tags.stream().map(t->t.getId()).toList());
            contactRepository.save(contact);
            return "Tags affected to Contact";
        }else {
            throw new NotFoundException("Tag is not in database");
        }
    }
}
