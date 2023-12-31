package ma.emsi.tagmicroservice.controller;

import lombok.RequiredArgsConstructor;
import ma.emsi.tagmicroservice.dto.TagRequest;
import ma.emsi.tagmicroservice.dto.TagResponse;
import ma.emsi.tagmicroservice.exceptions.NotFoundException;
import ma.emsi.tagmicroservice.service.TagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tag")
@RequiredArgsConstructor
@CrossOrigin
public class TagController {
    private final TagService tagService;
    @PostMapping("/addTag")
    public ResponseEntity<TagResponse> addTag(@RequestBody TagRequest tagRequest){
        return new ResponseEntity<>(tagService.create(tagRequest) , HttpStatus.OK) ;
    }
    @DeleteMapping("/deleteTagById/{id}")
    public ResponseEntity<String> deleteTag(@PathVariable Long id){
        try{
            return ResponseEntity.ok(tagService.delete(id));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PutMapping("/updateTagById/{id}")
    public ResponseEntity<TagResponse> updateTag(@PathVariable Long id,@RequestBody TagRequest tagRequest){
        try{
            return ResponseEntity.ok(tagService.update(id , tagRequest));
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllTags")
    public ResponseEntity<List<TagResponse>> getAllTags(){
        return ResponseEntity.ok(tagService.getAll());
    }
    @GetMapping("/getTagById/{id}")
    public ResponseEntity<TagResponse>  getTagById(@PathVariable Long id){
        try{
            TagResponse tagResponse = tagService.getById(id);
            return new ResponseEntity<>(tagResponse, HttpStatus.OK);
        }catch (NotFoundException e){
            return  new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<TagResponse>> isInTags(@RequestParam List<String> tags){
        return ResponseEntity.ok(tagService.getByName(tags));
    }

}
