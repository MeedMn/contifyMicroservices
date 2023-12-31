package ma.emsi.contactmicroservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import ma.emsi.contactmicroservice.dto.ContactRequest;
import ma.emsi.contactmicroservice.dto.ContactResponse;
import ma.emsi.contactmicroservice.dto.TagResponse;
import ma.emsi.contactmicroservice.exceptions.NotFoundException;
import ma.emsi.contactmicroservice.mapper.ContactMapper;
import ma.emsi.contactmicroservice.entity.Contact;
import ma.emsi.contactmicroservice.repository.ContactRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ContactService {

    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    public ContactResponse create(ContactRequest contactRequest){
        Contact contact = contactMapper.contactRequestToContact(contactRequest);
        contactRepository.save(contact);
        return contactMapper.contactToContactResponse(contact);
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
                    return contactMapper.contactToContactResponse(c);
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
        return contacts.stream().map(contactMapper::contactToContactResponse).toList();
    }
    public List<ContactResponse> getAllFavorites(){
        List<Contact> contacts = contactRepository.findContactsByFavoriteIs(true);
        return contacts.stream().map(contactMapper::contactToContactResponse).toList();
    }
    public ContactResponse getById(long id) {
        Contact contact = contactRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Contact not found"));
        return contactMapper.contactToContactResponse(contact);
    }
    public List<ContactResponse> getByTag(Long tag){
        List<Contact> contacts = contactRepository.findContactsByTag(tag);
        return contacts.stream().map(contactMapper::contactToContactResponse).toList();
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
