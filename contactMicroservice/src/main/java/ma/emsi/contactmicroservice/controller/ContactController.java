package ma.emsi.contactmicroservice.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.contactmicroservice.dto.ContactRequest;
import ma.emsi.contactmicroservice.dto.ContactResponse;
import ma.emsi.contactmicroservice.dto.TagResponse;
import ma.emsi.contactmicroservice.exceptions.NotFoundException;
import ma.emsi.contactmicroservice.service.ContactService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/contact")
@RequiredArgsConstructor
@CrossOrigin
public class ContactController {
    private final ContactService contactService;
    @PostMapping("/addContact")
    public ResponseEntity<ContactResponse> addContact(@RequestBody ContactRequest contactRequest){
        return new ResponseEntity<>(contactService.create(contactRequest) , HttpStatus.OK) ;
    }
    @DeleteMapping("/deleteContactById/{id}")
    public ResponseEntity<String> deleteContact(@PathVariable Long id){
        try{
            return ResponseEntity.ok(contactService.delete(id));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/updateContactById/{id}")
    public ResponseEntity<ContactResponse> updateContact(@PathVariable Long id,@RequestBody ContactRequest contactRequest){
        try{
            return ResponseEntity.ok(contactService.update(id , contactRequest));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllContacts")
    public ResponseEntity<List<ContactResponse>> getAllContacts(){
        return ResponseEntity.ok(contactService.getAll());
    }

    @GetMapping("/getAllFavorites")
    public ResponseEntity<List<ContactResponse>> getAllFavorites(){
        return ResponseEntity.ok(contactService.getAllFavorites());
    }
    @GetMapping("/getContactById/{id}")
    public ResponseEntity<ContactResponse>  getContactById(@PathVariable Long id){
        try{
            ContactResponse contactResponse = contactService.getById(id);
            return new ResponseEntity<>(contactResponse, HttpStatus.OK);
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getContactsByTag/{tag}")
    public ResponseEntity<List<ContactResponse>> getContactsByTag(@PathVariable Long tag){
        try{
            return ResponseEntity.ok(contactService.getByTag(tag));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/favorite/{id}")
    public ResponseEntity<String> addToFavorite(@PathVariable Long id){
        try{
            contactService.addToFavorites(id);
            return ResponseEntity.ok("Added to favorite successfully");
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/affecteTagToContact/{idContact}")
    public ResponseEntity<String> affecteTagToContact(@PathVariable Long idContact,@RequestBody List<TagResponse> tags){
        try{
            return ResponseEntity.ok(contactService.affectTagToContact(idContact,tags));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(e.getMessage(),HttpStatus.NOT_FOUND);
        }
    }
}
