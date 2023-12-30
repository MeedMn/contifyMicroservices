package ma.emsi.contactMicroservice.mapper;

import ma.emsi.contactMicroservice.dto.ContactRequest;
import ma.emsi.contactMicroservice.dto.ContactResponse;
import ma.emsi.contactMicroservice.dto.TagResponse;
import ma.emsi.contactMicroservice.entity.Contact;
import ma.emsi.contactMicroservice.exceptions.NotFoundException;
import ma.emsi.contactMicroservice.external.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ContactMapper {
    @Autowired
    TagService tagService;
    public ContactMapper(){
    }
    private List<TagResponse> fromIdToTags(List<Long> tagsId){
        if(tagsId !=null) {
            List<TagResponse> tags = new ArrayList<>();
            for (Long t : tagsId) {
                if(tagService.getById(t).isPresent()) {
                    tags.add(tagService.getById(t).get());
                }else{
                    throw new NotFoundException("Tag doesnt exist with the ID : "+t);
                }
            }
            return tags;
        }
        return null;
    }
    public ContactResponse ContactToContactResponse(Contact contact){
        return ContactResponse.builder()
                .id(contact.getId())
                .fullName(contact.getFullName())
                .email(contact.getEmail())
                .phoneNumber(contact.getPhoneNumber())
                .Address(contact.getAddress())
                .imagePath(contact.getImagePath())
                .tags(fromIdToTags(contact.getTags()))
                .favorite(contact.isFavorite())
                .build();
    }
    public Contact ContactRequestToContact(ContactRequest contactRequest){
        return Contact.builder()
                .fullName(contactRequest.getFullName())
                .email(contactRequest.getEmail())
                .phoneNumber(contactRequest.getPhoneNumber())
                .Address(contactRequest.getAddress())
                .imagePath(contactRequest.getImagePath())
                .build();
    }
}
