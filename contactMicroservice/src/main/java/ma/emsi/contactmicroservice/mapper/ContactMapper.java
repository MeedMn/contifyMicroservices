package ma.emsi.contactmicroservice.mapper;

import lombok.NoArgsConstructor;
import ma.emsi.contactmicroservice.dto.ContactRequest;
import ma.emsi.contactmicroservice.dto.ContactResponse;
import ma.emsi.contactmicroservice.dto.TagResponse;
import ma.emsi.contactmicroservice.entity.Contact;
import ma.emsi.contactmicroservice.exceptions.NotFoundException;
import ma.emsi.contactmicroservice.external.TagService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
@NoArgsConstructor
public class ContactMapper {
    TagService tagService;
    public ContactMapper(TagService tagService) {
        this.tagService = tagService;
    }
    private List<TagResponse> fromIdToTags(List<Long> tagsId){
        if(tagsId !=null) {
            List<TagResponse> tags = new ArrayList<>();
            for (Long t : tagsId) {
                TagResponse tag = tagService.getById(t)
                        .orElseThrow(() -> new NotFoundException("Tag doesn't exist with the ID: " + t));
                tags.add(tag);
            }
            return tags;
        }
        return Collections.emptyList();
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
